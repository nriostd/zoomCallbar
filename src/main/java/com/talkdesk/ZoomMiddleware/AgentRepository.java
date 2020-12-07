package com.talkdesk.ZoomMiddleware;

import com.talkdesk.ZoomMiddleware.model.Agent;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AgentRepository extends MongoRepository<Agent, String> {

  public Agent findByZoomId(String zoomId);
}
