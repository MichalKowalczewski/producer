package com.example.producer.controller;

import com.example.producer.model.DTO.TaskDTO;
import com.example.producer.model.DTO.UserDTO;
import com.example.producer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public UserDTO getById(@PathVariable Integer id){
        return userService.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public UserDTO create(@Valid @RequestBody UserDTO user){
        return userService.create(user);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<UserDTO> findAll(){
        return userService.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public UserDTO update(@PathVariable Integer id, @RequestBody UserDTO dto) {
        return userService.update(id, dto);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id) {
        userService.delete(id);
    }
}
