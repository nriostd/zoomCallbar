package com.talkdesk.ZoomMiddleware;

import com.talkdesk.ZoomMiddleware.model.Agent;
import com.talkdesk.ZoomMiddleware.model.Notification;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/bespoke/zoom")
public class Controller {


  @RequestMapping(value = "/agent", method = RequestMethod.GET)
    public ResponseEntity listAllAgents() {
        List<Agent> response = ZoomService.retrieve_all_agents();
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @RequestMapping(value = "/notify", method = RequestMethod.POST)
      public ResponseEntity handleNotifications(@RequestBody Notification notification) {
          Agent response = ZoomService.update_presence(notification);
          return new ResponseEntity<>(response, HttpStatus.OK);
      }
  }
