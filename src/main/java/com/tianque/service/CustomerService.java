package com.tianque.service;


import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.tianque.dao.CustomerMapper;
import com.tianque.pojo.Customer;
import com.tianque.pojo.User;
import com.tianque.util.ShiroUtil;
import com.tianque.util.ToUtf;

@Named
public class CustomerService {

    @Inject
    private CustomerMapper customerMapper;

    public void saveCustomer(Customer customer) {

		customer.setCreateUser(new User(ShiroUtil.getCurrentUserID()));
        customer.setPinyin(ToUtf.toPinyiin(customer.getCustname()));
        customerMapper.save(customer);
    }

    /**
     * 根据当前用户的身份去显示客户列表
     * @param params
     * @return
     */
    public List<Customer> findCustomerByParams(Map<String, Object> params) {

        if(ShiroUtil.isEmployee()) {
            params.put("userid",ShiroUtil.getCurrentUserID());
        }
       return customerMapper.findByByParams(params);
    }

    /**
     * 根据当前用户的身份去查询客户的数量
     * @return
     */
    public Long count() {
        if(ShiroUtil.isEmployee()) {
            Map<String,Object> params = Maps.newHashMap();
            params.put("userid",ShiroUtil.getCurrentUserID());
            return customerMapper.countByStr(params);
        }
        return customerMapper.count();
    }

    /**
     *根据当前用户的身份和搜索条件去查询客户的数量
     * @param params
     * @return
     */
    public Long countByParam(Map<String, Object> params) {
        if(ShiroUtil.isEmployee()) {
            params.put("userid",ShiroUtil.getCurrentUserID());
        }
        return customerMapper.countByStr(params);
    }

    /*
     *查找所有的公司
     */
    public List<Customer> findAllCompany() {
        return customerMapper.findByType(Customer.CUSTOMER_TYPE_COMPANY);
    }


    /**
     * 删除客户同时会删除关联的数据，所以要加事务
     * @param id
     */
    @Transactional
    public void delCustomer(Integer id) {

        Customer customer = customerMapper.findCustomerById(id);

        if(customer != null) {
            if(customer.getType().equals(Customer.CUSTOMER_TYPE_COMPANY)) {
                //被删除用户是公司，查找是否有关联客户，如果有，则将公司ID和名称设置为空
                List<Customer> customerList = customerMapper.findByCompanyId(id);
                for(Customer cust : customerList) {
                    cust.setCompanyid(null);
                    customerMapper.update(cust);
                }
            }

            //TODO 删除关联的项目
            //TODO 删除关联的代办事项

            customerMapper.delCustomerById(id);
        }
    }

    public Customer findCustomerById(Integer id) {
        return customerMapper.findCustomerById(id);
    }

    /**
     * 更新客户信息
     * @param customer
     */
    @Transactional
    public void updateCustomer(Customer customer) {

		// if(customer.getType().equals(Customer.CUSTOMER_TYPE_COMPANY)) {
		// //找到关联的客户，并修改名字
		// List<Customer> customerList =
		// customerMapper.findByCompanyId(customer.getId());
		// for(Customer cust : customerList) {
		// cust.setCompanyid(customer.getId());
		// cust.setCompanyname(customer.getCustname());
		// customerMapper.update(cust);
		// }
		// } else {
		// if(customer.getCompanyid() != null) {
		// Customer company =
		// customerMapper.findCustomerById(customer.getCompanyid());
		// customer.setCompanyname(company.getCustname());
		// }
		// }
		//
		// customer.setPinyin(ToUtf.toPinyiin(customer.getCustname()));
		// customerMapper.update(customer);
    }


    public List<Customer> findCustomerByCompanyId(Integer id) {
        return customerMapper.findByCompanyId(id);
    }

    public List<Customer> findAllCustomer() {
        return customerMapper.findAll();
    }

    public List<Customer> findAllCustomer(Integer id) {

        if(ShiroUtil.isEmployee()) {
            return customerMapper.findAllByUserid(id);
        }
        return customerMapper.findAll();
    }

    /**
     * 公开客户
     * @param customer
     */
    public void openCustomer(Customer customer) {
        customerMapper.openUpdate(customer);
    }

    /**
     * 转交客户
     * @param customer
     */
    public void move(Customer customer) {
        customerMapper.moveUpdate(customer);
    }


    /**
     * 电子名片
     * @param id
     * @return
     */
    public String makeMeCard(Integer id) {

        Customer customer = customerMapper.findCustomerById(id);

        StringBuilder mecard = new StringBuilder("MECARD:");
        if(StringUtils.isNotEmpty(customer.getCustname())) {
            mecard.append("N:"+customer.getCustname()+";");
        }
        if(StringUtils.isNotEmpty(customer.getTel())) {
            mecard.append("TEL:"+customer.getTel()+";");
        }
        if(StringUtils.isNotEmpty(customer.getEmail())) {
            mecard.append("EMAIL:"+customer.getEmail()+";");
        }
        if(StringUtils.isNotEmpty(customer.getAddress())) {
            mecard.append("ADR:"+customer.getAddress()+";");
        }
		// if(StringUtils.isNotEmpty(customer.getCompanyname())) {
		// mecard.append("ORG:"+customer.getCompanyname()+";");
		// }
        mecard.append(";");

        return mecard.toString();
    }


}
