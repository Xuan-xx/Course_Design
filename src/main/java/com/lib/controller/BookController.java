package com.lib.controller;

import com.lib.DAO.BookMapper;
import com.lib.bean.Book;
import com.lib.server.BookServer;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BookController {
    @Autowired
    private BookServer bookServer;
    @Autowired
    private BookMapper bookDAO;

    @GetMapping("/bookList")
    public String allBook(Model model){
        List<Book> bookList = bookServer.selectAllBook();
        model.addAttribute("bookList",bookList);
        return "book/bookList";
    }

    @GetMapping("/book")
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

    @PostMapping("/book")
    public String insertBook(Book book){
        try{
//            book.setLending(0);
            if (bookServer.insertBook(book)) {
                return "status/success";
            }else{
                return "status/error";
            }
        }catch (Exception e) {
            return "status/error";
        }
    }

    @DeleteMapping("/book/{num}")
    public String deleteBook(@PathVariable("num")String num){
        try {
            if (bookServer.deleteBook(num)){
                return "status/success";
            }else{
                return "status/error";
            }
        } catch (Exception e) {
            return "status/error";
        }
    }

    @GetMapping("/book/{num}")
    public String jmpUpdateBook(@PathVariable("num")String num,Model model){
        Book oldBook = bookDAO.selectBookByNum(num);
        model.addAttribute("book",oldBook);
        return "book/updateBook";
    }

    @PutMapping("/book")
    public String updateBook(Book newData){
        try {
            if (bookServer.updateBook(newData)) {
                return "status/success";
            }else{
                return "status/error";
            }
        } catch (Exception e) {
            return "status/error";
        }
    }
}


