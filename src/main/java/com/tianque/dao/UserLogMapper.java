package com.tianque.dao;

import java.util.List;
import java.util.Map;

import com.tianque.pojo.UserLog;

public interface UserLogMapper {

    void saveUserLog(UserLog userLog);

    List<UserLog> findByParam(Map<String, Object> param);

    Long countByParam(Map<String, Object> param);
}
