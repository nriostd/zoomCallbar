package com.talkdesk.ZoomMiddleware;

import org.springframework.data.annotation.Id;

public class Agent {

  @Id
  public String id;

  public String name;
  public String presence;
  public String zoomId;
  public String phone;

  public Agent(){}

  public Agent(String name, String zoomId, String presence){
    this.name = name;
    this.zoomId = zoomId;
    this.presence = presence;
  }

  public void addNumber(String number){
    this.phone = number;
  }

  public void updatePresence(String presence){
    this.presence = presence;
  }

  @Override
  public String toString() {
    return String.format("Agent[id=%s, name='%s', presence='%s', zoomId ='%s', phone='%s']", id, name, presence, zoomId, phone);
  }

}
