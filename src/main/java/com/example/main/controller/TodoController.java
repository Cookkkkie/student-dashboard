package com.example.main.controller;

import com.example.main.Exceptions.UserNotFoundException;
import com.example.main.Exceptions.UserServiceLogicException;
import com.example.main.dtos.ApiResponseDto;
import com.example.main.modals.Task;
import com.example.main.modals.UserMod;
import com.example.main.repository.UserRepository;
import com.example.main.services.TodoService;
import com.example.main.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import org.springframework.security.core.Authentication;


@Controller
@RequestMapping("/todo-list")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/")
    public String viewTodoList(Model model) throws UserNotFoundException, UserServiceLogicException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userID = auth.getName();

        ApiResponseDto<?> tasksResponse = todoService.getTasksByUserId(Long.valueOf(userID)).getBody();
        assert tasksResponse != null;

        List<Task> tasks = (List<Task>) tasksResponse.getResponse();

        LocalDate now = LocalDate.now();
        for (Task task : tasks) {
            boolean isOverdue = task.getDueDate().isBefore(now);
            task.setStatus(isOverdue);
        }

        model.addAttribute("tasks", tasks);
        return "todo-list";
    }


    @PostMapping("/create")
    public String createTask(@ModelAttribute Task task) throws UserNotFoundException, UserServiceLogicException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        UserMod user = userRepository.findByUserID(Long.valueOf(email)).get();
        task.setUser(user);


        LocalDate dueDate = task.getDueDate();
        System.out.println(dueDate);
        if (dueDate != null) {
            task.setDueDate(dueDate);
        }

        todoService.saveTask(task);
        return "redirect:/todo-list/";
    }


    @DeleteMapping("/delete/{id}")
    public String deleteTask(@PathVariable("id") Long id) {
        todoService.deleteTaskById(id);
        return "redirect:/todo-list/";
    }

    @GetMapping("/create")
    public String createTaskForm(Model model) {
        model.addAttribute("task", new Task());
        return "create-task";
    }
}
