package com.lib.controller;

import com.lib.DAO.BRMapper;
import com.lib.DAO.ReaderMapper;
import com.lib.bean.Book;
import com.lib.bean.Reader;
import com.lib.server.BRServer;
import com.lib.server.BookServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BRController {

    @Autowired
    private BookServer bookServer;
    @Autowired
    private ReaderMapper readerDAO;
    @Autowired
    private BRServer BRDAO;

    @GetMapping("/toBorrow")
    public String jmpBorrow(Model model){
        List<Book> bookList = bookServer.selectBookByLending();
        List<Reader> readerList = readerDAO.selectReader(new Reader());
        model.addAttribute("bookList",bookList);
        model.addAttribute("readerList",readerList);
        return "b_r/borrow";
    }

    @PostMapping("/borrow")
    public String borrowBook(@RequestParam("r_id")String rId,@RequestParam("b_num")String bNum){
        if (BRDAO.borrow(rId,bNum)){
            return "status/success";
        }else{
            return "status/error";
        }
    }

    @PutMapping("/return")
    public String returnBook(@RequestParam("r_id")String rId,@RequestParam("b_num")String bNum){
        if (BRDAO.returnBook(rId,bNum)){
            return "status/success";
        }else{
            return "status/error";
        }
    }
}
