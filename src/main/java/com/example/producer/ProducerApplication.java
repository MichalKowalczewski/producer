package com.example.producer;

import com.example.producer.model.DTO.TaskDTO;
import com.example.producer.model.DTO.UserDTO;
import com.example.producer.model.Picture;
import com.example.producer.repository.PictureRepository;
import com.example.producer.service.TaskServiceImpl;
import com.example.producer.service.UserService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class ProducerApplication {

  public static void main(String[] args) {
    SpringApplication.run(ProducerApplication.class, args);
  }

  @Component
  @Order(0)
  class MyApplicationListener implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    TaskServiceImpl taskService;

    @Autowired
    UserService userService;

    @Autowired
    PictureRepository pictureRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
      userService.create(new UserDTO("Stefan", "Nowak", "sn@wp.pl"));
      userService.create(new UserDTO("Jan", "Kowalski", "jk@wp.pl"));


      if (taskService.checkIfTableIsEmpty()) {
        taskService.create(new TaskDTO("test", true));
        taskService
            .create(new TaskDTO("test2", false, 20, new UserDTO(2, "Jan", "Kowalski", "jk@wp.pl")));
        taskService
            .create(new TaskDTO("test3", false, 20, new UserDTO(1, "Stefan", "Nowak", "sn@wp.pl")));
      }
      saveImage("sda.png");
      saveImage("test.png");

    }

    private void saveImage(String url) {
      File file = new File(url);
      Blob image = null;
      try {
        FileInputStream inputStream = new FileInputStream(file);
        image = new SerialBlob(inputStream.readAllBytes());
      } catch (IOException | SQLException e) {
        e.printStackTrace();
      }
      Picture picture = new Picture(image);
      pictureRepository.save(picture);
    }
  }

}
