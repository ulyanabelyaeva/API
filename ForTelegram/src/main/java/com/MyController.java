package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
@RequestMapping("/")
public class MyController {

    @Autowired
    private MessageService messageService;

    @GetMapping()
    public String getForm(){
        return "form";
    }

    @PostMapping()
    public String sendMessage(@RequestParam("message") String message){
        try {
            messageService.send(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "form";
    }
}
