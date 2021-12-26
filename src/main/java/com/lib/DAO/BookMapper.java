package com.lib.DAO;

import com.lib.bean.Book;

import java.util.List;

public interface BookMapper {
    List<Book> selectBook(Book book);
    Boolean insertBook(Book book);
    Integer getBookMaxId();
}
