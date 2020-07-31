package com.example.producer

import com.example.producer.controller.TaskController
import com.example.producer.exceptions.TaskNotFoundException
import com.example.producer.model.DTO.TaskDTO
import com.example.producer.model.DTO.UserDTO
import com.example.producer.service.TaskService
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.MediaType
import spock.lang.Unroll

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerSpec extends Specification {

    @Autowired
    TaskController taskController
    @Autowired
    TaskService taskService;
    @Autowired
    MockMvc mvc

    private static TaskDTO dto = new TaskDTO(1,"test",true, null, null)
    private static List<TaskDTO> dtos = [dto, new TaskDTO(2,"test2", false,  20, new UserDTO(1, "Jan", "Kowalski", "jk@wp.pl"))]
    private ObjectMapper objectMapper = new ObjectMapper()


    def "should return list of Tasks"(){
        when:
        def result = mvc.perform(get('/api/tasks')).andReturn()
        def values  = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<TaskDTO>>() {})
        then:
        values.get(1).user == dtos.get(1).user
    }

    @Unroll
    def "should return specific task"(){
        when:
        def result = mvc.perform(get('/api/tasks/'+id)).andReturn()
        then:
        objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<TaskDTO>(){}) == test

        where:
        id | test
        1 | dtos.get(0)
        2 | dtos.get(1)
    }

    def "should add new Task to the database"(){
        given:
        def dto = new TaskDTO("spec")
        def request = objectMapper.writeValueAsString(dto)
        expect:
        mvc.perform(post('/api/tasks')
        .content(request)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        and:
        def task = taskService.findLast()
        task.description == dto.description
        task.completionPercentage == dto.completionPercentage
        task.completed == dto.completed
        if (task.user != null) {
            task.user.firstName == dto.user.firstName
            task.user.lastName == dto.user.lastName
            task.user.email == dto.user.email
        }
    }

    @Unroll
    def "should update Task from database"(){
        given:
        def request = objectMapper.writeValueAsString(task)
        when:
        def result = mvc.perform(put('/api/tasks/'+id)
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
        then:
        result.getResponse().status == 200

        taskController.getById(id) == taskFromDb
        where:
        id | task  | taskFromDb
        1 | new TaskDTO(1, "testChanged", false, 10, new UserDTO("firstName", "lastName", "email@email.com")) | new TaskDTO(1, "testChanged", false, 10, new UserDTO(2, "firstName", "lastName", "email@email.com"))
        2 | new TaskDTO(2, "testChanged2", true) | new TaskDTO(2, "testChanged2", true)
    }

    def "should delete task with id=3"(){
        expect:
        mvc.perform(delete('/api/tasks/'+3)).andExpect(status().isOk())
        when:
        taskController.delete(4)
        then:
        def ex = thrown(TaskNotFoundException)
        ex.getMessage() == "Could not find searched Task"
    }

    def "when context is loaded then all expected beans are created"() {
        expect: "the WebController is created"
        taskService
        taskController
    }

    def "should retrun task 2"(){
        given:
        taskController
        when:
        TaskDTO result = taskController.getById(1)
        then:
        result.getDescription() == "testChanged"
        !result.isCompleted()
        when:
        taskController.getById(3)
        then:
        def ex = thrown(TaskNotFoundException)
        ex.getMessage() == "Could not find searched Task"
    }

    def "should check if object is corret"(){
        given:
        def stubedService = Stub(TaskService.class)
        stubedService.findAll() >> [new TaskDTO(0, "sprawdzam", true),
                                  new TaskDTO(1, "test", false),
                                  new TaskDTO(2, "jakies dane", true)]
        when:
        def list = stubedService.findAll()
        then:
        list.get(0).getId() == 0
        list.get(0).getDescription() == "sprawdzam"
        list.get(0).isCompleted()
    }
}
