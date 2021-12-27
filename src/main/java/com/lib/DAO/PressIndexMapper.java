package com.lib.DAO;

import com.lib.bean.Book;
import com.lib.bean.PressIndex;

public interface PressIndexMapper {
    PressIndex selectPressIndex(Book book);
    Boolean insertPressIndex(PressIndex pressIndex);
    Boolean updatePressIndex(PressIndex pressIndex);
    Boolean deletePressIndex(PressIndex pressIndex);
}
