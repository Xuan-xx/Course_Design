package com.lib.controller;

import com.lib.DAO.ReaderMapper;
import com.lib.bean.Reader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ReaderController {
    @Autowired
    private ReaderMapper readerDAO;

    @GetMapping("/readerList")
    public String allReader(Model model) {
        List<Reader> readerList = readerDAO.selectReader(new Reader());
        model.addAttribute("readerList", readerList);
        return "reader/readerList";
    }

    @GetMapping("/reader")
    public String selectReader(Reader reader, Model model) {
        List<Reader> readerList = readerDAO.selectReader(reader);
        model.addAttribute("readerList", readerList);
        return "reader/readerList";
    }

    @PostMapping("/reader")
    public String insertReader(Reader reader) {
        if (readerDAO.insertReader(reader)){
            return  "status/success";
        }else{
            return  "status/error";
        }
    }

    @DeleteMapping("/reader/{id}")
    public String deleteReader(@PathVariable("id")String  id){
        if (readerDAO.deleteReader(id)){
            return "status/success";
        }else{
            return "status/error";
        }
    }

    @GetMapping("/reader/{id}")
    public String jmpUpdateReader(@PathVariable("id")String id,Model model){
        Reader reader = readerDAO.selectOneReader(id);
        model.addAttribute("oldReader",reader);
        return "reader/updateReader";
    }

    @PutMapping("/reader/{oldId}")
    public String updateReader(@PathVariable("oldId")String oldId, Reader reader){
        System.out.println(oldId);
        System.out.println(reader);
        Map<String,Object> param = new HashMap<>();
        param.put("oldId",oldId);
        param.put("reader",reader);
        if (readerDAO.updateReader(param)){
            return "status/success";
        }else{
            return "status/error";
        }
    }
}
