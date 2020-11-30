package com.talkdesk.ZoomMiddleware;

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
		ZoomService.get_agents();

		//Save a couple of agents
		Agent Nick = new Agent("Nick", "123", "Active");
		Nick.addNumber("+14089668696");
		Nick.updatePresence("Offline");
		Agent Mike = new Agent("Mike", "111", "Offline");
		repo.save(Nick);
		repo.save(Mike);

		//fetch all agents
		System.out.println("Agents found with findAll():");
		System.out.println("-------------------------------");
		for (Agent agent : repo.findAll()){
			System.out.println(agent);
		}
		System.out.println();

		//fetch an individual customer

		System.out.println("Agent found with findByZoomId('123'):");
		System.out.println("-------------------------------------");
		System.out.println(repo.findByZoomId("123"));

	}

}
