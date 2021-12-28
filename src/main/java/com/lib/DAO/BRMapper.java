package com.lib.DAO;

import java.util.Map;

public interface BRMapper {
    Boolean selectBR(Map<String, Object> map);

    Boolean insertBR(Map<String, Object> map);

    Boolean updateBR(Map<String, Object> map);
}
