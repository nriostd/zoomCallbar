package com.talkdesk.ZoomMiddleware;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.springframework.util.*;
import org.json.*;
import java.util.*;
import java.lang.*;

public class ZoomService {

  public static String get_token(){



    return "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOm51bGwsImlzcyI6IkI3VDhmcWVKUkplVzBUZFRDQkxCZnciLCJleHAiOjE2NDA5MDg0NDAsImlhdCI6MTYwNDA5MzEzOH0.QH0tIjbMaJpZ8_QbuckbqScHtTuI6cEGrILflTE1JXk";
  }

  public static List<Agent> get_agents(){
    List<Agent> agents = new ArrayList<Agent>();

    String token = ZoomService.get_token();

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
            a.addNumber(phone);
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
