package com.example.producer.service;

import com.example.producer.model.Picture;
import java.util.List;

public interface PictureService {


  public List<Picture> getAll();

  public Picture findById(int id);
}
