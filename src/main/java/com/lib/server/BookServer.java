package com.lib.server;

import com.lib.DAO.AuthorIndexMapper;
import com.lib.DAO.BookIndexMapper;
import com.lib.DAO.BookMapper;
import com.lib.DAO.PressIndexMapper;
import com.lib.bean.AuthorIndex;
import com.lib.bean.Book;
import com.lib.bean.BookIndex;
import com.lib.bean.PressIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServer {
    @Autowired
    private BookMapper bookDAO;
    @Autowired
    private BookIndexMapper bookIndexDAO;
    @Autowired
    private AuthorIndexMapper authorIndexDAO;
    @Autowired
    private PressIndexMapper pressIndexDAO;

    public List<Book> selectAllBook() {

        return bookDAO.selectBook(new Book());

    }

    public List<Book> selectBookByCondition(Book book){
        return bookDAO.selectBook(book);
    }

    public Boolean insertBook(Book book) {
        book.setId(bookDAO.getBookMaxId()+1);
        BookIndex bookIndex = bookIndexDAO.selectBookIndex(book);
        AuthorIndex authorIndex = authorIndexDAO.selectAuthorIndex(book);
        PressIndex pressIndex = pressIndexDAO.selectPressIndex(book);

        if (bookIndex == null) {
            bookIndex = new BookIndex();
            book.setP1(0);
            bookIndex.setName(book.getName());
            bookIndex.setP(book.getId());
            bookIndex.setLength(1);
            bookIndexDAO.insertBookIndex(bookIndex);
        } else {
            book.setP1(bookIndex.getP());
            bookIndex.setP(book.getId());
            bookIndex.setLength(bookIndex.getLength() + 1);
            bookIndexDAO.updateBookIndex(bookIndex);
        }

        if (authorIndex == null) {
            authorIndex = new AuthorIndex();
            book.setP2(0);
            authorIndex.setAuthor(book.getAuthor());
            authorIndex.setP(book.getId());
            authorIndex.setLength(1);
            authorIndexDAO.insertAuthorIndex(authorIndex);
        } else {
            book.setP2(authorIndex.getP());
            authorIndex.setP(book.getId());
            authorIndex.setLength(authorIndex.getLength() + 1);
            authorIndexDAO.updateAuthorIndex(authorIndex);
        }

        if (pressIndex == null) {
            pressIndex = new PressIndex();
            book.setP3(0);
            pressIndex.setPress(book.getPress());
            pressIndex.setP(book.getId());
            pressIndex.setLength(1);
            pressIndexDAO.insertPressIndex(pressIndex);
        } else {
            book.setP3(pressIndex.getP());
            pressIndex.setP(book.getId());
            pressIndex.setLength(pressIndex.getLength() + 1);
            pressIndexDAO.updatePressIndex(pressIndex);
        }

        return bookDAO.insertBook(book);
    }
}
