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
import org.springframework.transaction.annotation.Transactional;

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

    public List<Book> selectBookByCondition(Book book) {
        return bookDAO.selectBook(book);
    }

    public List<Book> selectAllBook() {
        return bookDAO.selectBook(new Book());
    }

    @Transactional
    public Boolean insertBook(Book book) {

        Integer maxId = bookDAO.getBookMaxId();
        maxId = maxId == null ? 0 : maxId;
        book.setId(maxId + 1);
        BookIndex bookIndex = bookIndexDAO.selectBookIndex(book);
        AuthorIndex authorIndex = authorIndexDAO.selectAuthorIndex(book);
        PressIndex pressIndex = pressIndexDAO.selectPressIndex(book);

        if (bookIndex == null) {
            bookIndex = new BookIndex();
            book.setP1(0);
            bookIndex.setName(book.getName());
            bookIndex.setP(book.getId());
            bookIndex.setLength(1);
            if (!bookIndexDAO.insertBookIndex(bookIndex)) {
                throw new RuntimeException("insert bookIndex false");
            }
        } else {
            book.setP1(bookIndex.getP());
            bookIndex.setP(book.getId());
            bookIndex.setLength(bookIndex.getLength() + 1);
            if (!bookIndexDAO.updateBookIndex(bookIndex)) {
                throw new RuntimeException("update bookIndex false");
            }
        }

        if (authorIndex == null) {
            authorIndex = new AuthorIndex();
            book.setP2(0);
            authorIndex.setAuthor(book.getAuthor());
            authorIndex.setP(book.getId());
            authorIndex.setLength(1);
            if (!authorIndexDAO.insertAuthorIndex(authorIndex)) {
                throw new RuntimeException("insert authorIndex false");
            }
        } else {
            book.setP2(authorIndex.getP());
            authorIndex.setP(book.getId());
            authorIndex.setLength(authorIndex.getLength() + 1);
            if (!authorIndexDAO.updateAuthorIndex(authorIndex)) {
                throw new RuntimeException("update authorIndex false");
            }
        }

        if (pressIndex == null) {
            pressIndex = new PressIndex();
            book.setP3(0);
            pressIndex.setPress(book.getPress());
            pressIndex.setP(book.getId());
            pressIndex.setLength(1);
            if (!pressIndexDAO.insertPressIndex(pressIndex)) {
                throw new RuntimeException("insert pressIndex false");
            }
        } else {
            book.setP3(pressIndex.getP());
            pressIndex.setP(book.getId());
            pressIndex.setLength(pressIndex.getLength() + 1);
            if (!pressIndexDAO.updatePressIndex(pressIndex)) {
                throw new RuntimeException("update pressIndex false");
            }
        }

        if (!bookDAO.insertBook(book)) {

            throw new RuntimeException("insert book false");
        }

        return true;
    }

    @Transactional
    public Boolean deleteBook(String num) {
        Book book = bookDAO.selectBookByNum(num);
        System.out.println(book);

        BookIndex bookIndex = bookIndexDAO.selectBookIndex(book);
        AuthorIndex authorIndex = authorIndexDAO.selectAuthorIndex(book);
        PressIndex pressIndex = pressIndexDAO.selectPressIndex(book);

//        该名称的书只有一本
        if (bookIndex.getLength() == 1) {
            if (!bookIndexDAO.deleteBookIndex(bookIndex)) {
                throw new RuntimeException("insert into bookIndex false");
            }
        } else {
            Book sufNameBook = bookDAO.selectBookByP1(book.getId());//得到书名链表中该书的后一本书
            if (sufNameBook != null) {
                sufNameBook.setP1(book.getP1());//改变后一本书的指针
                if (!bookDAO.updateBook(sufNameBook)) {
                    throw new RuntimeException("update book false");
                }
            } else {    //后一结点在链表的最后
                bookIndex.setP(book.getP1());
            }

            bookIndex.setLength(bookIndex.getLength() - 1);
            if (!bookIndexDAO.updateBookIndex(bookIndex)) {
                throw new RuntimeException("update bookIndex false");
            }
        }


        //该作者的书只有一本
        if (authorIndex.getLength() == 1) {
            if (!authorIndexDAO.deleteAuthorIndex(authorIndex)) {
                throw new RuntimeException("delete authorIndex false");
            }
        } else {
            Book sufAuthorBook = bookDAO.selectBookByP2(book.getId());//得到作者链表中该书的后一本书
            if (sufAuthorBook != null) {
                sufAuthorBook.setP2(book.getP2());//改变后一本书的指针
                if (!bookDAO.updateBook(sufAuthorBook)) {
                    throw new RuntimeException("update book false");
                }
            } else {    //后一结点在链表的最后
                authorIndex.setP(book.getP2());
            }

            authorIndex.setLength(authorIndex.getLength() - 1);
            if (!authorIndexDAO.updateAuthorIndex(authorIndex)) {
                throw new RuntimeException("update authorIndex false");
            }

        }

        //该出版社的书只有一本
        if (pressIndex.getLength() == 1) {
            if (!pressIndexDAO.deletePressIndex(pressIndex)) {
                throw new RuntimeException("delete pressIndex false");
            }
        } else {
            Book sufPressBook = bookDAO.selectBookByP3(book.getId());//得到出版社链表中该书的后一本书
            if (sufPressBook != null) {
                sufPressBook.setP3(book.getP3());//改变后一本书的指针
                if (!bookDAO.updateBook(sufPressBook)) {
                    throw new RuntimeException("update book false");
                }
            } else {    //后一结点在链表的最后
                pressIndex.setP(book.getP3());
            }

            pressIndex.setLength(pressIndex.getLength() - 1);
            if (!pressIndexDAO.updatePressIndex(pressIndex)) {
                throw new RuntimeException("update pressIndex false");
            }

        }
        if (!bookDAO.deleteBook(book)) {
            throw new RuntimeException("delete book false");
        }

        return true;
    }

    @Transactional
    public Boolean updateBook(Book newData) {
        //得到旧数据
        Book oldData = bookDAO.selectBookById(newData.getId());

        //根据旧数据得到的索引
        BookIndex oldBookIndex = bookIndexDAO.selectBookIndex(oldData);
        AuthorIndex oldAuthorIndex = authorIndexDAO.selectAuthorIndex(oldData);
        PressIndex oldPressIndex = pressIndexDAO.selectPressIndex(oldData);
        //根据新数据得到的索引
        BookIndex newBookIndex = bookIndexDAO.selectBookIndex(newData);
        AuthorIndex newAuthorIndex = authorIndexDAO.selectAuthorIndex(newData);
        PressIndex newPressIndex = pressIndexDAO.selectPressIndex(newData);

        //改变了书名
        if (!oldData.getName().equals(newData.getName())) {
            //先删除旧的
            //该名称的书只有一本
            if (oldBookIndex.getLength() == 1) {
                if (!bookIndexDAO.deleteBookIndex(oldBookIndex)) {
                    throw new RuntimeException("insert into bookIndex false");
                }
            } else {
                Book sufNameBook = bookDAO.selectBookByP1(oldData.getId());//得到书名链表中该书的后一本书
                if (sufNameBook != null) {
                    sufNameBook.setP1(oldData.getP1());//改变后一本书的指针
                    if (!bookDAO.updateBook(sufNameBook)) {
                        throw new RuntimeException("update book false");
                    }
                } else {    //后一结点在链表的最后
                    oldBookIndex.setP(oldData.getP1());
                }

                oldBookIndex.setLength(oldBookIndex.getLength() - 1);
                if (!bookIndexDAO.updateBookIndex(oldBookIndex)) {
                    throw new RuntimeException("update bookIndex false");
                }
            }
            //再添加新的
            if (newBookIndex == null) {
                newBookIndex = new BookIndex();
                newData.setP1(0);
                newBookIndex.setName(newData.getName());
                newBookIndex.setP(newData.getId());
                newBookIndex.setLength(1);
                if (!bookIndexDAO.insertBookIndex(newBookIndex)) {
                    throw new RuntimeException("insert newBookIndex false");
                }
            } else {
                newData.setP1(newBookIndex.getP());
                newBookIndex.setP(newData.getId());
                newBookIndex.setLength(newBookIndex.getLength() + 1);
                if (!bookIndexDAO.updateBookIndex(newBookIndex)) {
                    throw new RuntimeException("update newBookIndex false");
                }
            }
        }

        //改变了作者
        if (!oldData.getAuthor().equals(newData.getAuthor())) {
            //先删除旧的
            //该作者的书只有一本
            if (oldAuthorIndex.getLength() == 1) {
                if (!authorIndexDAO.deleteAuthorIndex(oldAuthorIndex)) {
                    throw new RuntimeException("delete authorIndex false");
                }
            } else {
                Book sufAuthorBook = bookDAO.selectBookByP2(oldData.getId());//得到作者链表中该书的后一本书
                if (sufAuthorBook != null) {
                    sufAuthorBook.setP2(oldData.getP2());//改变后一本书的指针
                    if (!bookDAO.updateBook(sufAuthorBook)) {
                        throw new RuntimeException("update book false");
                    }
                } else {    //后一结点在链表的最后
                    oldAuthorIndex.setP(oldData.getP2());
                }

                oldAuthorIndex.setLength(oldAuthorIndex.getLength() - 1);
                if (!authorIndexDAO.updateAuthorIndex(oldAuthorIndex)) {
                    throw new RuntimeException("update authorIndex false");
                }

            }
            //再添加新的
            if (newAuthorIndex == null) {
                newAuthorIndex = new AuthorIndex();
                newData.setP2(0);
                newAuthorIndex.setAuthor(newData.getAuthor());
                newAuthorIndex.setP(newData.getId());
                newAuthorIndex.setLength(1);
                if (!authorIndexDAO.insertAuthorIndex(newAuthorIndex)) {
                    throw new RuntimeException("insert newAuthorIndex false");
                }
            } else {
                newData.setP2(newAuthorIndex.getP());
                newAuthorIndex.setP(newData.getId());
                newAuthorIndex.setLength(newAuthorIndex.getLength() + 1);
                if (!authorIndexDAO.updateAuthorIndex(newAuthorIndex)) {
                    throw new RuntimeException("update authorIndex false");
                }
            }

        }

        //改变了出版社
        if (!oldData.getPress().equals(newData.getPress())) {
            //先删除旧的
            //该出版社的书只有一本
            if (oldPressIndex.getLength() == 1) {
                if (!pressIndexDAO.deletePressIndex(oldPressIndex)) {
                    throw new RuntimeException("delete oldPressIndex false");
                }
            } else {
                Book sufPressBook = bookDAO.selectBookByP3(oldData.getId());//得到出版社链表中该书的后一本书
                if (sufPressBook != null) {
                    sufPressBook.setP3(oldData.getP3());//改变后一本书的指针
                    if (!bookDAO.updateBook(sufPressBook)) {
                        throw new RuntimeException("update oldData false");
                    }
                } else {    //后一结点在链表的最后
                    oldPressIndex.setP(oldData.getP3());
                }

                oldPressIndex.setLength(oldPressIndex.getLength() - 1);
                if (!pressIndexDAO.updatePressIndex(oldPressIndex)) {
                    throw new RuntimeException("update oldPressIndex false");
                }

            }
            if (!bookDAO.deleteBook(oldData)) {
                throw new RuntimeException("delete oldData false");
            }
            //再添加新的
            if (newPressIndex == null) {
                newPressIndex = new PressIndex();
                newData.setP3(0);
                newPressIndex.setPress(newData.getPress());
                newPressIndex.setP(newData.getId());
                newPressIndex.setLength(1);
                if (!pressIndexDAO.insertPressIndex(newPressIndex)) {
                    throw new RuntimeException("insert newPressIndex false");
                }
            } else {
                newData.setP3(newPressIndex.getP());
                newPressIndex.setP(newData.getId());
                newPressIndex.setLength(newPressIndex.getLength() + 1);
                if (!pressIndexDAO.updatePressIndex(newPressIndex)) {
                    throw new RuntimeException("update newPressIndex false");
                }
            }
        }

        if (!bookDAO.updateBook(newData)){
            throw new RuntimeException("update book false");
        }
        return true;
    }
}