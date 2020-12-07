package com.talkdesk.ZoomMiddleware;

import com.talkdesk.ZoomMiddleware.model.Agent;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.springframework.util.*;
import org.json.*;
import java.util.*;
import java.lang.*;
import java.io.*;
import java.security.*;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class ZoomService {

  private static String hmacEncode(String data, String key) throws Exception {
    Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
    SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(), "HmacSHA256");
    sha256_HMAC.init(secret_key);

    return Base64.getUrlEncoder().withoutPadding().encodeToString(sha256_HMAC.doFinal(data.getBytes()));
  }


  public static String create_token(){
    String HARDCODED_KEY = "B7T8fqeJRJeW0TdTCBLBfw";
    String HARDCODED_SECRET = "uGKFA2zQF0BJNR6uX2dDdgVeaaTNwwUWNcsD";
    String JWT_HEADER = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
    String base64_JWT_HEADER = Base64.getUrlEncoder().withoutPadding().encodeToString(JWT_HEADER.getBytes());
    long timeSecs = (System.currentTimeMillis() / 1000) + 120; //jwt token expires 2 minutes from now

    String JWT_PAYLOAD = "{\"iss\": \"" + HARDCODED_KEY + "\",\"exp\": \"" + String.valueOf(timeSecs) + "\"}";
    String base64_JWT_PAYLOAD = Base64.getUrlEncoder().withoutPadding().encodeToString(JWT_PAYLOAD.getBytes());

    try {
          String base64_JWT_SIGNATURE = hmacEncode(base64_JWT_HEADER + "." + base64_JWT_PAYLOAD, HARDCODED_SECRET);
          System.out.println(base64_JWT_HEADER + "." + base64_JWT_PAYLOAD + "." + base64_JWT_SIGNATURE);
          return base64_JWT_HEADER + "." + base64_JWT_PAYLOAD + "." + base64_JWT_SIGNATURE;
       } catch (Exception e) {
          throw new RuntimeException("Unable to generate a JWT token.");
       }
  }

  public static List<Agent> get_agents(){
    List<Agent> agents = new ArrayList<Agent>();

    String token = ZoomService.create_token();

    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", String.format("Bearer %s", token));
    HttpEntity entity = new HttpEntity(headers);
    String url = "https://api.zoom.us/v2/contacts?page_size=25&query_presence_status=true";

    String next_token = "";
    do {
      //TODO need to find a way to remove that token below
      if (next_token != ""){
        url = url + "&next_page_token=" + next_token;
      }
      ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
      JSONObject response_json = new JSONObject(response.getBody());
      String rem = "&next_page_token=" + next_token;
      url = url.replace(rem, "");
      System.out.println("Response JSON is: ");
      System.out.println(response_json);
      next_token = response_json.getString("next_page_token");
      JSONArray ja_contacts = response_json.getJSONArray("contacts");
      int contacts_len = ja_contacts.length();

      for(int i = 0; i < contacts_len; i++){
        JSONObject contact = new JSONObject();
        contact = ja_contacts.getJSONObject(i);
        String name = contact.getString("first_name") + contact.getString("last_name");
        String id = contact.getString("id");
        String presence = contact.getString("presence_status");

        Agent a = new Agent(name, id, presence);

        if( contact.has("direct_numbers") ){
          //not every contact has a direct number
          //the value holds an array of Strings, but should only have one string in the array
          JSONArray j_arr = contact.getJSONArray("direct_numbers");
          if( j_arr.length() > 0){
            String phone = j_arr.getString(0);
            a.setNumber(phone);
          }
        }

        agents.add(a);
      }
    } while(next_token != "");

    for (Agent agent : agents){
			System.out.println(agent);
		}

    return agents;
  }


  public static Agent update_presence(Agent a, String new_presence){



    return a;
  }




}
