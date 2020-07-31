package com.example.producer

import com.example.producer.controller.UserController
import com.example.producer.exceptions.UserNotFoundException;
import com.example.producer.model.DTO.UserDTO
import com.example.producer.service.UserService
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType
import spock.lang.Specification;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerSpec extends Specification {

    @Autowired
    private MockMvc mvc
    @Autowired
    private UserService userService
    @Autowired
    private UserController userController;


    private static UserDTO dto = new UserDTO(2,'Adam', 'Nowak', 'an@wp.pl')
    private static List<UserDTO> dtos = [new UserDTO(1,'Jan', 'Kowalski', 'jk@wp.pl'), dto]
    private ObjectMapper objectMapper = new ObjectMapper()

    def "should add new User to the database"(){
        given:
        def request = objectMapper.writeValueAsString(dto)
        expect:
        mvc.perform(post('/api/users')
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        and:
        def user = userService.findLast()
        user.firstName == dto.firstName
        user.lastName == dto.lastName
        user.email == dto.email
    }

    def "should return list of all Users"(){
        when:
        def result = mvc.perform(get('/api/users')).andReturn()
        def values = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<UserDTO>>() {})
        then:
        values.containsAll(dtos)
    }

    def "should return specific task"(){
        when:
        def result = mvc.perform(get('/api/users/'+id)).andReturn()
        then:
        objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<UserDTO>(){}) == test

        where:
        id | test
        1 | dtos.get(0)
        2 | dtos.get(1)
    }

    def "should update specific task"(){
        given:
        def request = objectMapper.writeValueAsString(user)
        when:
        def result = mvc.perform(put('/api/users/'+id)
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
        then:
        result.getResponse().status == 200
        userController.getById(id) == user
        where:
        id | user
        1 | new UserDTO(1, "Adam", "Nowak", "an@wp.pl" )
        2 | new UserDTO(2, "Jan", "Kowalski", "jk@wp.pl" )
    }

    def "should delete specific task"(){
        expect:
        mvc.perform(delete('/api/users/'+1)).andExpect(status().isOk())
        when:
        userController.delete(4)
        then:
        def ex = thrown(UserNotFoundException)
        ex.getMessage() == "Could not find searched User"
    }
}
