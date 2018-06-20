package com.tianque.dao;

import java.util.List;

import com.tianque.pojo.SaleFile;

public interface SaleFileMapper {
    List<SaleFile> findFileBySaleid(Integer id);

    void saveFile(SaleFile saleFile);

    SaleFile findOneById(Integer id);

    void delFileBySaleid(Integer id);
}
