package com.lib.DAO;

import com.lib.bean.Reader;

import java.util.List;
import java.util.Map;

public interface ReaderMapper {
    List<Reader> selectReader(Reader reader);
    Reader selectOneReader(String id);
    Boolean insertReader(Reader reader);
    Boolean updateReader(Map<String,Object> map);
    Boolean deleteReader(String id);
}
