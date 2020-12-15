package com.talkdesk.ZoomMiddleware;

import org.springframework.beans.factory.annotation.Autowired;
import com.talkdesk.ZoomMiddleware.model.Agent;
import com.talkdesk.ZoomMiddleware.model.Notification;
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

  @Autowired
  private static AgentRepository repo;

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

  public static JSONObject store_agents_request(String url, String next_page_token){
    String final_url = url;
    if(next_page_token != ""){
      final_url = url + "&next_page_token=" + next_page_token;
    }
    String token = ZoomService.create_token();
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", String.format("Bearer %s", token));
    HttpEntity entity = new HttpEntity(headers);
    ResponseEntity<String> response = restTemplate.exchange(final_url, HttpMethod.GET, entity, String.class);
    JSONObject response_json = new JSONObject(response.getBody());
    return response_json;
  }

  public static List<Agent> store_agents(){
    List<Agent> agents = new ArrayList<Agent>();
    String url = "https://api.zoom.us/v2/contacts?page_size=2&query_presence_status=true";

    String next_token = "";
    do {
      JSONObject response_json = ZoomService.store_agents_request(url, next_token);
      next_token = response_json.getString("next_page_token");
      JSONArray ja_contacts = response_json.getJSONArray("contacts");
      int contacts_len = ja_contacts.length();

      for(int i = 0; i < contacts_len; i++){
        JSONObject contact = new JSONObject();
        contact = ja_contacts.getJSONObject(i);
        String name = contact.getString("first_name") + " " + contact.getString("last_name");
        String id = contact.getString("id");
        String presence = contact.getString("presence_status");

        Agent a = new Agent(name, id, presence);

        if( contact.has("direct_numbers") ){
          //not every contact has a direct number
          //the value holds an array of Strings, but should only have one string in the array
          JSONArray j_arr = contact.getJSONArray("direct_numbers");
          if( j_arr.length() > 0){
            String phone = j_arr.getString(0);
            a.setPhone(phone);
          }
        }

        agents.add(a);
      }
    } while(next_token != "");

    return agents;
  }

  public static List<Agent> retrieve_all_agents(){
    List<Agent> agents = repo.findAll();
    return agents;
  }


  public static Agent process_notification(Notification notification){

  }

  public static Agent update_presence(Payload payload){
    String notification_zoomID = notification.getPayload().getObject().getId();
    String notification_status = notification.getPayload().getObject().getPresenceStatus();
    Agent agent_to_update = repo.findByZoomId(notification_zoomID);
    agent_to_update.setPresence(notification_status);
    System.out.println("--Agent Presence Updated--");
    System.out.println(agent_to_update);
    repo.save(agent_to_update);
    return agent_to_update;
  }

  public static Agent user_created(Payload payload){

  }

  public static Agent user_deleted(Payload payload){

  }

  public static Agent user_updated(Payload payload){
    
  }


}
