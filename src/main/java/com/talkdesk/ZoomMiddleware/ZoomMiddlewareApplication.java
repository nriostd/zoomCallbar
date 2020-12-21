package com.talkdesk.ZoomMiddleware;

import com.talkdesk.ZoomMiddleware.model.Agent;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ZoomMiddlewareApplication implements CommandLineRunner{

	@Autowired
	private AgentRepository repo;

	public static void main(String[] args) {
		SpringApplication.run(ZoomMiddlewareApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		repo.deleteAll();

		//Call to test get agents method
		List<Agent> agents = ZoomService.store_agents();
		System.out.println("Agents returned from store_agents()");
		System.out.println("-------------------------------");
		for (Agent a : agents){
			System.out.println(a);
			repo.save(a);
		}
		System.out.println();

		//Save a couple of agents
		Agent Nick = new Agent("Nick", "n@test.com", "123", "Active");
		Nick.setPhone("+14089668696");
		Nick.setAssociatedAccountId("112244");
		Nick.setPresence("Offline");
		Agent Mike = new Agent("Mike", "m@test.com", "111", "Offline");
		repo.save(Nick);
		repo.save(Mike);

		//fetch all agents
		System.out.println("Agents found with findAll():");
		System.out.println("-------------------------------");
		for (Agent agent : repo.findAll()){
			System.out.println(agent);
		}
		System.out.println();

		//fetch customers using custom repo methods

		System.out.println("Agent found with findByZoomId('123'):");
		System.out.println("-------------------------------------");
		System.out.println(repo.findByZoomId("123"));
		System.out.println();

		System.out.println("Agent found with findByAssociatedAccountId('112244'):");
		System.out.println("-------------------------------------");
		System.out.println(repo.findByAssociatedAccountId("112244"));

		System.out.println("Agent found with findByEmail('n@test.com'):");
		System.out.println("-------------------------------------");
		System.out.println(repo.findByAssociatedAccountId("n@test.com"));

		String token = ZoomService.create_token();
		System.out.println(token);

	}

}
