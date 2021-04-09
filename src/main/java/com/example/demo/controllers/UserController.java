package com.example.demo.controllers;

import com.example.demo.entity.Role;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;
import java.util.Set;


@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //Все пользователи
    @GetMapping()
    public String showUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());

        return "user/users";
    }

    //Добавление роли Оператор для указанного пользователя
    @GetMapping("/{id}/add")
    public String setRole(@PathVariable("id") Long id) {
        Set<Role> roles = userService.getUserPoId(id).get().getRoles();
        Optional<Role> role = roles.stream().filter(item -> item.getName().equals("ROLE_OPERATOR")).findFirst();

        if(!role.isPresent()) {
            userService.setRole(id);
        }

        return "redirect:/users";
    }
}
