package com.example.demo.service;

import com.example.demo.entity.Request;
import com.example.demo.repository.RequestRepository;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;


@Service
public class RequestService {
    private final RequestRepository requestRepository;

    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    //Заявки со статусом Отправлено
    public List<Request> findAllRequest(String status){
        if(!requestRepository.findAllByStatus(status).isEmpty()) {
            return requestRepository.findAllByStatus("Отправлено");
        } else {
            return new ArrayList<>();
        }
    }

    //Заявки текущего пользователя
    public List<Request> findUser(String name){
        if(!requestRepository.findAllByUserName(name).isEmpty()) {
            return requestRepository.findAllByUserName(name);
        } else {
            return new ArrayList<>();
        }
    }

    //Найти заявку по Id
    public Request findOne(Long id) {
        return requestRepository.findById(id).get();
    }

    //Создание новой заявки
    public void createRequest(Request request, String name) {
        request.setUserName(name);

        requestRepository.save(request);
    }

    public void updateRequest(Long id, Request request, String status) {
        Request currentRequest = findOne(id);
        currentRequest.setTag(request.getTag());
        currentRequest.setStatus(status);
        currentRequest.setMessage(request.getMessage());

        createRequest(currentRequest, request.getUserName());
    }

    public void deleteRequest(Long id) {
        requestRepository.deleteById(id);
    }
}
