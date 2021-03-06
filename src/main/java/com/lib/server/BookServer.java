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

    public List<Book> selectBookByLending(){
        return bookDAO.selectBookByLending();
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

//        ???????????????????????????
        if (bookIndex.getLength() == 1) {
            if (!bookIndexDAO.deleteBookIndex(bookIndex)) {
                throw new RuntimeException("insert into bookIndex false");
            }
        } else {
            Book sufNameBook = bookDAO.selectBookByP1(book.getId());//??????????????????????????????????????????
            if (sufNameBook != null) {
                sufNameBook.setP1(book.getP1());//???????????????????????????
                if (!bookDAO.updateBook(sufNameBook)) {
                    throw new RuntimeException("update book false");
                }
            } else {    //??????????????????????????????
                bookIndex.setP(book.getP1());
            }

            bookIndex.setLength(bookIndex.getLength() - 1);
            if (!bookIndexDAO.updateBookIndex(bookIndex)) {
                throw new RuntimeException("update bookIndex false");
            }
        }


        //???????????????????????????
        if (authorIndex.getLength() == 1) {
            if (!authorIndexDAO.deleteAuthorIndex(authorIndex)) {
                throw new RuntimeException("delete authorIndex false");
            }
        } else {
            Book sufAuthorBook = bookDAO.selectBookByP2(book.getId());//??????????????????????????????????????????
            if (sufAuthorBook != null) {
                sufAuthorBook.setP2(book.getP2());//???????????????????????????
                if (!bookDAO.updateBook(sufAuthorBook)) {
                    throw new RuntimeException("update book false");
                }
            } else {    //??????????????????????????????
                authorIndex.setP(book.getP2());
            }

            authorIndex.setLength(authorIndex.getLength() - 1);
            if (!authorIndexDAO.updateAuthorIndex(authorIndex)) {
                throw new RuntimeException("update authorIndex false");
            }

        }

        //??????????????????????????????
        if (pressIndex.getLength() == 1) {
            if (!pressIndexDAO.deletePressIndex(pressIndex)) {
                throw new RuntimeException("delete pressIndex false");
            }
        } else {
            Book sufPressBook = bookDAO.selectBookByP3(book.getId());//?????????????????????????????????????????????
            if (sufPressBook != null) {
                sufPressBook.setP3(book.getP3());//???????????????????????????
                if (!bookDAO.updateBook(sufPressBook)) {
                    throw new RuntimeException("update book false");
                }
            } else {    //??????????????????????????????
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
        //???????????????
        Book oldData = bookDAO.selectBookById(newData.getId());

        //??????????????????????????????
        BookIndex oldBookIndex = bookIndexDAO.selectBookIndex(oldData);
        AuthorIndex oldAuthorIndex = authorIndexDAO.selectAuthorIndex(oldData);
        PressIndex oldPressIndex = pressIndexDAO.selectPressIndex(oldData);
        //??????????????????????????????
        BookIndex newBookIndex = bookIndexDAO.selectBookIndex(newData);
        AuthorIndex newAuthorIndex = authorIndexDAO.selectAuthorIndex(newData);
        PressIndex newPressIndex = pressIndexDAO.selectPressIndex(newData);

        //???????????????
        if (!oldData.getName().equals(newData.getName())) {
            //???????????????
            //???????????????????????????
            if (oldBookIndex.getLength() == 1) {
                if (!bookIndexDAO.deleteBookIndex(oldBookIndex)) {
                    throw new RuntimeException("insert into bookIndex false");
                }
            } else {
                Book sufNameBook = bookDAO.selectBookByP1(oldData.getId());//??????????????????????????????????????????
                if (sufNameBook != null) {
                    sufNameBook.setP1(oldData.getP1());//???????????????????????????
                    if (!bookDAO.updateBook(sufNameBook)) {
                        throw new RuntimeException("update book false");
                    }
                } else {    //??????????????????????????????
                    oldBookIndex.setP(oldData.getP1());
                }

                oldBookIndex.setLength(oldBookIndex.getLength() - 1);
                if (!bookIndexDAO.updateBookIndex(oldBookIndex)) {
                    throw new RuntimeException("update bookIndex false");
                }
            }
            //???????????????
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

        //???????????????
        if (!oldData.getAuthor().equals(newData.getAuthor())) {
            //???????????????
            //???????????????????????????
            if (oldAuthorIndex.getLength() == 1) {
                if (!authorIndexDAO.deleteAuthorIndex(oldAuthorIndex)) {
                    throw new RuntimeException("delete authorIndex false");
                }
            } else {
                Book sufAuthorBook = bookDAO.selectBookByP2(oldData.getId());//??????????????????????????????????????????
                if (sufAuthorBook != null) {
                    sufAuthorBook.setP2(oldData.getP2());//???????????????????????????
                    if (!bookDAO.updateBook(sufAuthorBook)) {
                        throw new RuntimeException("update book false");
                    }
                } else {    //??????????????????????????????
                    oldAuthorIndex.setP(oldData.getP2());
                }

                oldAuthorIndex.setLength(oldAuthorIndex.getLength() - 1);
                if (!authorIndexDAO.updateAuthorIndex(oldAuthorIndex)) {
                    throw new RuntimeException("update authorIndex false");
                }

            }
            //???????????????
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

        //??????????????????
        if (!oldData.getPress().equals(newData.getPress())) {
            //???????????????
            //??????????????????????????????
            if (oldPressIndex.getLength() == 1) {
                if (!pressIndexDAO.deletePressIndex(oldPressIndex)) {
                    throw new RuntimeException("delete oldPressIndex false");
                }
            } else {
                Book sufPressBook = bookDAO.selectBookByP3(oldData.getId());//?????????????????????????????????????????????
                if (sufPressBook != null) {
                    sufPressBook.setP3(oldData.getP3());//???????????????????????????
                    if (!bookDAO.updateBook(sufPressBook)) {
                        throw new RuntimeException("update oldData false");
                    }
                } else {    //??????????????????????????????
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
            //???????????????
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

        System.err.println(newData);
        if (!bookDAO.updateBook(newData)){
            throw new RuntimeException("update book false");
        }
        return true;
    }
}