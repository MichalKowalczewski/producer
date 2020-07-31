package com.example.producer;

import com.example.producer.model.DTO.TaskDTO;
import com.example.producer.model.DTO.UserDTO;
import com.example.producer.service.TaskServiceImpl;
import com.example.producer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class ProducerApplication  {
	public static void main(String[] args) {
		SpringApplication.run(ProducerApplication.class, args);
	}

	@Component
	@Order(0)
	class MyApplicationListener implements ApplicationListener<ApplicationReadyEvent> {

		@Autowired
		TaskServiceImpl taskService;

		@Override
		public void onApplicationEvent(ApplicationReadyEvent event) {
			if(taskService.checkIfTableIsEmpty()){
				taskService.create(new TaskDTO("test", true));
				taskService.create(new TaskDTO("test2", false,  20, new UserDTO("Jan", "Kowalski", "jk@wp.pl")));
			}
		}
	}

}
