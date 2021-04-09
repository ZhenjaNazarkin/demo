package com.example.demo.controllers;

import com.example.demo.entity.Request;
import com.example.demo.service.RequestService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;

@Controller
@RequestMapping("/requests")
public class RequestController {
    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    //Заявки Пользователя, Оператора
    @GetMapping()
    public String showRequest(Model model, Principal principal, Authentication authentication) {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean authorized = authorities.contains(new SimpleGrantedAuthority("ROLE_OPERATOR"));

        if(authorized) {
            model.addAttribute("requests", requestService.findAllRequest("Отправлено"));
        } else {
            model.addAttribute("requests", requestService.findUser(principal.getName()));
        }

        return "request/requests";
    }

    //Получение заявки по id
    @GetMapping("/{id}")
    public String getRequest(@PathVariable("id") Long id, Model model) {
        model.addAttribute("request", requestService.findOne(id));
        return "request/show_request";
    }

    @GetMapping("/new")
    public String newRequest(@ModelAttribute("request") Request request) {
        return "request/new_request";
    }

    //Добавление новой заявки
    @PostMapping()
    public String createRequest(@ModelAttribute("request") Request request, Principal principal) {
        request.setStatus("Черновик");
        requestService.createRequest(request, principal.getName());

        return "redirect:/requests";
    }

    @GetMapping("/{id}/edit")
    public String editRequest(@PathVariable("id") Long id, Model model) {
        model.addAttribute("request", requestService.findOne(id));
        return "request/edit_request";
    }

    //Редактирование заявки
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("request") Request request, @PathVariable("id") Long id, Principal principal) {
        request.setUserName(principal.getName());
        requestService.updateRequest(id, request, "Черновик");

        return "redirect:/requests";
    }

    //Удаление заявки
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        requestService.deleteRequest(id);

        return "redirect:/requests";
    }

    //Отправление заявки Оператору
    @GetMapping("/{id}/send")
    public String sendRequest(@PathVariable("id") Long id) {
        Request request = requestService.findOne(id);
        requestService.updateRequest(id, request, "Отправлено");

        return "redirect:/requests";
    }

    //Одобрение заявки Оператором
    @GetMapping("/{id}/accept")
    public String acceptRequest(@PathVariable("id") Long id) {
        Request request = requestService.findOne(id);
        requestService.updateRequest(id, request, "Принято");

        return "redirect:/requests";
    }

    //Отклонение заявки Оператором
    @GetMapping("/{id}/rejected")
    public String rejectedRequest(@PathVariable("id") Long id) {
        Request request = requestService.findOne(id);
        requestService.updateRequest(id, request, "Отклонено");

        return "redirect:/requests";
    }
}
