package com.example.producer.service;

import com.example.producer.model.Picture;
import com.example.producer.repository.PictureRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PictureServiceImpl implements PictureService {

  @Autowired
  private PictureRepository pictureRepository;

  @Override
  public List<Picture> getAll() {
    return pictureRepository.findAll();
  }

  @Override
  public Picture findById(int id) {
    return pictureRepository.findById(id).get();
  }
}
