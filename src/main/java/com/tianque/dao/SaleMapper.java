package com.tianque.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tianque.pojo.Sale;

public interface SaleMapper {
    List<Sale> findByByParams(Map<String, Object> params);

    Long countByStr(Map<String, Object> params);

    Long count();

    Sale findOneById(Integer id);

    void del(Integer id);

    void save(Sale sale);

    void updatProgress(Sale sale);

    void updateLasttime(Sale sale);

    List<Sale> findByCustId(Integer custid);

    List<Map<String,Object>> countProgress(@Param("start") String start,@Param("end") String end);

	List<Map<String, Object>> countProgress1();

    Long successCount(@Param("start") String start,@Param("end") String end);

    Float saleMoney(@Param("start") String start,@Param("end") String end);

    List<Map<String,Object>> totalUserMoney(@Param("start") String start,@Param("end") String end);
}
