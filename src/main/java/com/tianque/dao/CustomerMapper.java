package com.tianque.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tianque.pojo.Customer;

public interface CustomerMapper {

    void save(Customer customer);

    List<Customer> findByByParams(Map<String, Object> params);

    Long count();

    List<Customer> findByType(String customerTypeCompany);


    Long countByStr(Map<String, Object> params);

    Customer findCustomerById(Integer id);

    List<Customer> findByCompanyId(Integer id);

    void update(Customer customer);

    void delCustomerById(Integer id);

    List<Customer> findAll();

    List<Customer> findAllByUserid(Integer userid);

    void openUpdate(Customer customer);

    void moveUpdate(Customer customer);

    Long addCustCount(@Param("start") String start, @Param("end") String end,@Param("userid") Integer userid);
}
