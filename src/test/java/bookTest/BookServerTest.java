package bookTest;

import com.lib.bean.Book;
import com.lib.server.BookServer;
import org.junit.Test;

public class BookServerTest {
    @Test
    public void bookServerTest(){
        BookServer bookServer = new BookServer();

        Book book1 = new Book("1021", "数据库", "李小云", "人民邮电", "021",8,0);
        Book book2 = new Book("1014", "数据结构", "刘晓阳", "中国科学", "013", 6, 0);
        Book book3 = new Book("1106", "操作系统", "许海平", "人民邮电", "024", 7, 0);
//        bookServer.insertBook(book1);
//        bookServer.insertBook(book2);

        bookServer.insertBook(book3);
    }
}
