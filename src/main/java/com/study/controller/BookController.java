package com.study.controller;

import com.study.service.BookService;
import jakarta.annotation.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class BookController {
    @Resource
    BookService service;

    @GetMapping("/books")
    public String books(Model model){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("nickname",user.getUsername());
        model.addAttribute("book_list",service.getBookList().keySet());
        model.addAttribute("book_list_status",new ArrayList<>(service.getBookList().values()));
        return "books";
    }
}
