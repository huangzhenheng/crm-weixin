package com.tianque.service;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import com.tianque.dao.TaskMapper;
import com.tianque.pojo.Task;
import com.tianque.util.ShiroUtil;

@Named
public class TaskService {
    @Inject
    private TaskMapper taskMapper;


    /**
     * 添加待办任务
     * @param task
     * @param hour
     * @param min
     */
    public void saveTask(Task task, String hour, String min) {

        if(StringUtils.isNotEmpty(hour) && StringUtils.isNotEmpty(min)) {
            String reminderTime = task.getStart() + " "+hour + ":" + min + ":00";
            //TODO Quartz动态任务
            //yyyy-MM-dd HH:mm:ss 转换成DateTime对象
            DateTime dateTime = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime(reminderTime);
            //DateTime -> Cron

            task.setRemindertime(reminderTime);
        }
        task.setUserid(ShiroUtil.getCurrentUserID());
        taskMapper.save(task);
    }

    /**
     * 获取当前用户的所有任务
     * @return
     */
    public List<Task> findTaskByUserId(String start,String end) {
        return taskMapper.findByUserIdAndDateRanger(ShiroUtil.getCurrentUserID(),start,end);
    }

    /**
     * 获取当前用户已经超时的任务
     * @return
     */
    public List<Task> findTimeOutTasks() {

        String today = DateTime.now().toString("yyyy-MM-dd");
        return taskMapper.findTimeOutTask(ShiroUtil.getCurrentUserID(),today);
    }

    /**
     * 删除日程
     * @param id
     */
    public void delTask(Integer id) {
        taskMapper.del(id);
    }

    /**
     * 将日程设置为已完成
     * @param id
     */
    public Task doneTask(Integer id) {
        Task task = taskMapper.findById(id);
        task.setDone(true);
        task.setColor("#cccccc");
        taskMapper.update(task);
        return task;
    }

    public List<Task> findTaskByUserIdAndCustit(Integer id, Integer currentUserID) {
        return taskMapper.findTaskByUserIdAndCustIt(id,currentUserID);
    }

    public List<Task> findTaskByUserIdAndSaleid(Integer id, Integer currentUserID) {
        return taskMapper.findTaskByUserIdAndSaleIt(id,currentUserID);
    }

    public void updateTaskByid(Task task) {

        taskMapper.updateByid(task);
    }

    public Long countByTimeOut() {
        String today = DateTime.now().toString("yyyy-MM-dd");
        return taskMapper.countByTimeOutTask(ShiroUtil.getCurrentUserID(),today);
    }
}
