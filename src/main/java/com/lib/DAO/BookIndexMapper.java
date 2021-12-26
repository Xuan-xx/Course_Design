package com.lib.DAO;

import com.lib.bean.Book;
import com.lib.bean.BookIndex;

public interface BookIndexMapper {
    BookIndex selectBookIndex(Book book);
    Boolean insertBookIndex(BookIndex bookIndex);
    Boolean updateBookIndex(BookIndex bookIndex);
}
