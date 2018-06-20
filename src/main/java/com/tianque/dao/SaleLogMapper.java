package com.tianque.dao;

import java.util.List;

import com.tianque.pojo.SaleLog;

public interface SaleLogMapper {
    void saveLog(SaleLog saleLog);

    List<SaleLog> findBySaleid(Integer id);

    void delLogBySaleid(Integer id);
}
