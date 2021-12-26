package pressIndexTest;

import com.lib.DAO.PressIndexMapper;
import com.lib.bean.Book;
import com.lib.bean.PressIndex;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PressIndexTest {
    @Autowired
    private PressIndexMapper pressIndexDAO;

    @Test
    public void pressIndexTest(){
        Book book = new Book();
        book.setPress("j");
        PressIndex pressIndex = pressIndexDAO.selectPressIndex(book);
        System.out.println(pressIndex);
    }
}
