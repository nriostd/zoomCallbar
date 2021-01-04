package com.talkdesk.ZoomMiddleware.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Document(collection = "agents")
public class Agent {

  @Id
  private String zoomId;
  private String name;
  private String presence;
  private String phone;
  private String associatedAccountId;
  private String email;
  private String extension;

  public Agent(){

  }

  public Agent(String name, String email, String zoomId, String presence){
    this.name = name;
    this.email = email;
    this.zoomId = zoomId;
    this.presence = presence;
  }

  //
  // public void setNumber(String number){
  //   this.phone = number;
  // }
  // public String getNumber(){
  //   return phone;
  // }
  //
  // public void setPresence(String presence){
  //   this.presence = presence;
  // }
  // public String getPresence(){
  //   return presence;
  // }
  //
  // public void setName(String name){
  //   this.name = name;
  // }
  // public String getName(){
  //   return name;
  // }
  //
  // public void setZoomId(String zoomId){
  //   this.zoomId = zoomId;
  // }
  // public String getZoomId(){
  //   return zoomId;
  // }

  @Override
  public String toString() {
    return String.format("Agent[zoomId=%s, name='%s', presence='%s', phone='%s', associatedAccountId='%s', email='%s', extension='%s']", zoomId, name, presence, phone, associatedAccountId, email, extension);
  }

}
