package com.example.producer.service;

import com.example.producer.model.DTO.UserDTO;

import java.util.List;

public interface UserService {

  List<UserDTO> findAll();

  UserDTO create(UserDTO task);

  UserDTO update(Integer id, UserDTO task);

  void delete(Integer id);

  UserDTO findById(Integer id);

  UserDTO findLast();

}
