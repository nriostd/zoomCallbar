package com.talkdesk.ZoomMiddleware;

import com.talkdesk.ZoomMiddleware.model.Agent;
import com.talkdesk.ZoomMiddleware.model.Notification;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api")
public class Controller {

  @Autowired
	private AgentRepository repo;

  @RequestMapping(value = "/agent/", method = RequestMethod.GET)

    public ResponseEntity<List<Agent>> listAllAgents() {
        List<Agent> agents = repo.findAll();
        if (agents.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Agent>>(agents, HttpStatus.OK);
    }

    @RequestMapping(value = "/notify/", method = RequestMethod.POST)
      public ResponseEntity handleNotifications(@RequestBody Notification notification) {
          String notification_zoomID = notification.getPayload().getObject().getId();
          String notification_status = notification.getPayload().getObject().getPresenceStatus();
          Agent agent_to_update = repo.findByZoomId(notification_zoomID);
          agent_to_update.setPresence(notification_status);
          System.out.println(agent_to_update);
          repo.save(agent_to_update);
          return new ResponseEntity<>(agent_to_update, HttpStatus.OK);
      }
  }
