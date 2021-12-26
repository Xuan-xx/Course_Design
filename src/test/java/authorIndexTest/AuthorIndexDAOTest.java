package authorIndexTest;

import com.lib.DAO.AuthorIndexMapper;
import com.lib.bean.AuthorIndex;
import com.lib.bean.Book;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class AuthorIndexDAOTest {
    @Autowired
    private AuthorIndexMapper authorIndexDAO;
    @Test
    public void authorIndexTest(){
        Book book = new Book();
        book.setAuthor("j");
        AuthorIndex authorIndex = authorIndexDAO.selectAuthorIndex(book);
        System.out.println(authorIndex);
    }

}
