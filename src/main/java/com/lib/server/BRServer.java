package com.lib.server;

import com.lib.DAO.BRMapper;
import com.lib.DAO.BookMapper;
import com.lib.bean.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class BRServer {
    @Autowired
    private BRMapper BRDAO;
    @Autowired
    private BookMapper bookDAO;

    @Transactional
    public Boolean borrow(String rId,String bNum){
        Map<String,Object> map = new HashMap<>();
        map.put("rId",rId);
        map.put("bNum",bNum);
//        一个人只能借一本书一次
        if (BRDAO.selectBR(map)!=null){
            return false;
        }

        if (!BRDAO.insertBR(map)){
            return false;
        }

        Book book =  bookDAO.selectBookByNum(bNum);
        book.setLending(book.getLending()+1);
        if(!bookDAO.updateBookLending(book)){
            return false;
        }

        return true;
    }

    @Transactional
    public Boolean returnBook(String rId,String bNum){
        Map<String,Object> map = new HashMap<>();
        map.put("rId",rId);
        map.put("bNum",bNum);
        LocalDateTime date = LocalDateTime.now();
        map.put("rDate",date);
        if (!BRDAO.updateBR(map)){
            return false;
        }
        return true;
    }
}
