package bookTest;

import com.lib.DAO.BookMapper;
import com.lib.bean.Book;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class bookDAOTest {
    @Autowired
    private BookMapper bookDAO;

    @Test
    public void  testSelect() throws IOException {
//        Book book = new Book();
//        book.setNum("1014");
//        book.setAuthor("李小云");
//        book.setName("数据结构");
//        book.setPress("人民邮电");
//        List<Book> bookList = bookDAO.selectBook(book);
//        for (Book b : bookList) {
//            System.out.println(b);
//        }
        System.out.println(bookDAO.getBookMaxId());
    }

    @Test
    public void testInsert(){
        Book book = new Book("0109","程序设计","刘晓阳",
                "清华大学","035",7,0,5,2,6);
        bookDAO.insertBook(book);
    }
}
