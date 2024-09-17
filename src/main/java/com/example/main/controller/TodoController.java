package com.example.main.controller;

import com.example.main.dtos.ApiResponseDto;
import com.example.main.modals.Task;
import com.example.main.modals.UserMod;
import com.example.main.services.TodoService;
import com.example.main.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.security.core.Authentication;


@Controller
@RequestMapping("/todo-list")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String viewTodoList(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        ApiResponseDto<?> userResponse = userService.getUserByEmail(email).getBody();
        assert userResponse != null;
        UserMod user = (UserMod) userResponse.getResponse();

        ApiResponseDto<?> tasksResponse = todoService.getTasksByUserId((long) user.getUserID()).getBody();
        assert tasksResponse != null;

        List<Task> tasks = (List<Task>) tasksResponse.getResponse();

        model.addAttribute("tasks", tasks);

        return "todo-list";
    }


    @PostMapping("/create")
    public String createTask(@ModelAttribute Task task) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        ApiResponseDto<?> userResponse = userService.getUserByEmail(email).getBody();
        assert userResponse != null;
        UserMod user = (UserMod) userResponse.getResponse();

        task.setUser(user);

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
