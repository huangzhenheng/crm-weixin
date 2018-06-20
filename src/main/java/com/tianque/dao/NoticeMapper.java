package com.tianque.dao;

import java.util.List;
import java.util.Map;

import com.tianque.pojo.Notice;

public interface NoticeMapper {

    void saveNotice(Notice notice);

    List<Notice> findAllNotice();

    Notice findNoticeById(Integer id);

    Long count();

    List<Notice> findByParam(Map<String, Object> param);
}
