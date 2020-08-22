package com.example.producer.controller;

import com.example.producer.model.Picture;
import com.example.producer.service.PictureService;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/picture")
public class PictureController {

  @Autowired
  private PictureService pictureService;

  @GetMapping(value = "/all")
  public String showAll(Model model) {
    List<Picture> pictures = pictureService.getAll();
    model.addAttribute("pictures", pictures);
    pictures.forEach(System.out::println);
    return "pictures";
  }

  @GetMapping(value = "/test")
  public void display(@RequestParam int id, HttpServletResponse response) {
    Picture pictures = pictureService.findById(id);
    Blob image = pictures.getImage();

    response.setContentType("image/jpeg");
    try (ServletOutputStream outputStream = response.getOutputStream()) {
      outputStream.write(image.getBinaryStream().readAllBytes());
    } catch (IOException | SQLException e) {
      e.printStackTrace();
    }
  }

}
