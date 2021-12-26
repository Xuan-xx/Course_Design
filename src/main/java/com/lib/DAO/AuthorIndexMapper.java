package com.lib.DAO;

import com.lib.bean.AuthorIndex;
import com.lib.bean.Book;

public interface AuthorIndexMapper {
    AuthorIndex selectAuthorIndex(Book book);
    Boolean insertAuthorIndex(AuthorIndex authorIndex);
    Boolean updateAuthorIndex(AuthorIndex authorIndex);
}
