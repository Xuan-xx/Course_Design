package bookTest;

import com.lib.bean.Book;
import com.lib.server.BookServer;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BookServerTest extends TestBean{
    @Autowired
    private BookServer bookServer;
    @Test
    public void bookServerTest(){

        Book book1 = new Book("1021", "数据库", "李小云", "人民邮电", "021",8,0);
        Book book2 = new Book("1014", "数据结构", "刘晓阳", "中国科学", "013", 6, 0);
        Book book3 = new Book("1106", "操作系统", "许海平", "人民邮电", "024", 7, 0);
//        bookServer.insertBook(book1);
//        bookServer.insertBook(book2);
//        bookServer.insertBook(book3);
        bookServer.deleteBook("2105");
    }

    @Test
    public void updateTest(){
        Book book1 = new Book("1021", "数据结构", "李小云", "人民邮电", "021",1,0);
        book1.setId(1);
        bookServer.updateBook(book1);
    }
}
