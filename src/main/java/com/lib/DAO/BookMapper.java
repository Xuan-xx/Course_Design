package com.lib.DAO;

import com.lib.bean.Book;

import java.util.List;

public interface BookMapper {
    List<Book> selectBook(Book book);
    Book selectBookByNum(String num);
    Book selectBookById(Integer id);
    Book selectBookByP1(Integer p1);
    Book selectBookByP2(Integer p2);
    Book selectBookByP3(Integer p3);
    List<Book> selectBookByLending();
    Integer getBookMaxId();
    Boolean insertBook(Book book);
    Boolean deleteBook(Book book);
    Boolean updateBook(Book book);
    Boolean updateBookLending(Book book);
}
