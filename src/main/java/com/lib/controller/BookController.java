package com.lib.controller;

import com.lib.bean.Book;
import com.lib.server.BookServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BookController {
    @Autowired
    private BookServer bookServer;

    @GetMapping("/bookSelect")
    public String selectBook(Book book, Model model) {
        List<Book> bookList = null;
        try {
            bookList = bookServer.selectBookByCondition(book);
        } catch (Exception e) {
            return "status/error";
        }
        model.addAttribute("bookList", bookList);
        return "book/bookList";
    }
}


