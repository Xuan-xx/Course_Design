package com.lib.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(Exception.class)
    public String handlerException(Exception ex,Model model){
        model.addAttribute("ex",ex);
        return "status/error";
    }
}
