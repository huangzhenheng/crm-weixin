package com.tianque.dao;

import java.util.List;

import com.tianque.pojo.Document;

public interface DocumentMapper {

    void save(Document document);

    List<Document> findByFileFid(Integer fid);

    Document findfid(Integer fid);

	void delFileByID(Integer id);
}
