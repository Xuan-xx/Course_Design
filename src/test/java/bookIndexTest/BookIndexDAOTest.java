package bookIndexTest;

import com.lib.DAO.BookIndexMapper;
import com.lib.bean.Book;
import com.lib.bean.BookIndex;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BookIndexDAOTest {
    @Autowired
    private BookIndexMapper bookIndexDAO;

    @Test
    public void testBookIndexDAO() throws Exception {
        Book book = new Book();
        book.setName("j");
        BookIndex bookIndex = bookIndexDAO.selectBookIndex(book);
        System.out.println(bookIndex);

    }
}
