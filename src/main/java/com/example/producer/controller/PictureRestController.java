package com.example.producer.controller;

import com.example.producer.model.RestPicture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/picture")
public class PictureRestController {

  @GetMapping
  public RestPicture getPicture(@RequestParam int id) {
    return new RestPicture(id, "/picture/test?id=" + id);
  }


}
