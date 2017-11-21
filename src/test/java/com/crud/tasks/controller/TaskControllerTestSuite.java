package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TrelloMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskControllerTestSuite {
    @Autowired
    TaskController taskController;

    @Test
    public void testGetTasks() {
        //Given
        taskController.createTask(new TaskDto(1, "getTasks_test", "testing getTasks"));
        //When
        List<TaskDto> taskDtoList = taskController.getTasks();
        //Then
        assertEquals("getTasks_test", taskDtoList.get(taskDtoList.size()-1).getTitle());
        assertEquals("testing getTasks", taskDtoList.get(taskDtoList.size()-1).getContent());
        //cleanup
        taskController.deleteTask(taskDtoList.get(taskDtoList.size()-1).getId());
    }
    @Test
    public void testGetTask() {
        //Given
        List<TaskDto> testTaskDtos = new ArrayList<>();
        taskController.createTask(new TaskDto(1, "getTask_test", "testing getTask"));
        //When
        try {
            testTaskDtos.add(taskController.getTask(taskController.getTasks().get(taskController.getTasks().size() - 1).getId()));
        } catch (TaskNotFoundException e) {
            //do nothing
        }
        //Then
        assertEquals("getTask_test", testTaskDtos.get(0).getTitle());
        assertEquals("testing getTask", testTaskDtos.get(0).getContent());
        //cleanup
        taskController.deleteTask(testTaskDtos.get(0).getId());
    }

    @Test
    public void testGetEmptyTask() {
        //Given
        List<TaskDto> testTaskDtos = new ArrayList<>();
        //When
        try {
            testTaskDtos.add(taskController.getTask((long) 100));
        } catch (TaskNotFoundException e) {
            //do nothing
        }
        //Then
        assertEquals(0, testTaskDtos.size());
    }
}