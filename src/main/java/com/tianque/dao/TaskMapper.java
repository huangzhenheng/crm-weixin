package com.tianque.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tianque.pojo.Task;

public interface TaskMapper {

    void save(Task task);

    List<Task> findByUserId(Integer userId);

    List<Task> findTimeOutTask(@Param("userId") Integer userID, @Param("today") String today);

    List<Task> findByUserIdAndDateRanger(@Param("userId") Integer currentUserID,@Param("start") String start,@Param("end") String end);

    void del(Integer id);

    Task findById(Integer id);

    void update(Task task);

    List<Task> findTaskByUserIdAndCustIt(@Param("custid")Integer id, @Param("currentUserID")Integer currentUserID);

    List<Task> findTaskByUserIdAndSaleIt(@Param("saleid")Integer saleid, @Param("currentUserID")Integer currentUserID);

    void updateByid(Task task);

    Long countByTimeOutTask(@Param("userId") Integer userID, @Param("today") String today);
}
