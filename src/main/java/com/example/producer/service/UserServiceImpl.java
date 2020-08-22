package com.example.producer.service;

import com.example.producer.exceptions.UserNotFoundException;
import com.example.producer.model.DTO.UserDTO;
import com.example.producer.model.User;
import com.example.producer.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository repository;
  private ModelMapper modelMapper = new ModelMapper();

  public List<UserDTO> findAll() {
    return repository.findAll().stream()
        .map(task -> modelMapper.map(task, UserDTO.class))
        .collect(Collectors.toList());
  }

  @Transactional
  public UserDTO create(UserDTO user) {
    User newUser = modelMapper.map(user, User.class);
    return modelMapper.map(repository.saveAndFlush(newUser), UserDTO.class);
  }

  @Transactional
  public UserDTO update(Integer id, UserDTO user) {
    User entity = getOneSafe(id);
    entity.setId(id);
    entity.setFirstName(user.getFirstName());
    entity.setLastName(user.getLastName());
    entity.setEmail(user.getEmail());
    return modelMapper.map(entity, UserDTO.class);
  }

  @Transactional
  public void delete(Integer id) {
    User user = getOneSafe(id);
    repository.delete(user);
  }

  @Transactional
  public UserDTO findById(Integer id) {
    return modelMapper
        .map(repository.findById(id).orElseThrow(UserNotFoundException::new), UserDTO.class);
  }

  @Override
  public UserDTO findLast() {
    return modelMapper.map(repository.findFirstByOrderByIdDesc(), UserDTO.class);
  }

  private User getOneSafe(Integer id) {
    if (repository.existsById(id)) {
      return repository.getOne(id);
    } else {
      throw new UserNotFoundException();
    }
  }
}
