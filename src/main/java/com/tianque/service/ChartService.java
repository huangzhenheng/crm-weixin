package com.tianque.service;


import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import com.tianque.dao.CustomerMapper;
import com.tianque.dao.SaleMapper;
import com.tianque.util.ShiroUtil;

@Named
public class ChartService {

    @Inject
    private SaleMapper saleMapper;
    @Inject
    private CustomerMapper customerMapper;

    public List<Map<String,Object>> loadPieData(String start, String end) {

        DateTime now = DateTime.now();
        if(StringUtils.isEmpty(start)) {
            start = now.dayOfMonth().withMinimumValue().toString("yyyy-MM-dd");
        }
        if(StringUtils.isEmpty(end)) {
            end = now.toString("yyyy-MM-dd");
        }
        return saleMapper.countProgress(start,end);
    }


    /**
     * 统计增长的客户数量
     * @param start
     * @param end
     * @return
     */
    public Long addCustCount(String start, String end) {

        DateTime now = DateTime.now();
        if(StringUtils.isEmpty(start)) {
            start = now.dayOfMonth().withMinimumValue().toString("yyyy-MM-dd");
        }
        if(StringUtils.isEmpty(end)) {
            end = now.toString("yyyy-MM-dd");
        }
        //是不是经理
        return customerMapper.addCustCount(start,end, ShiroUtil.getCurrentUserID());
    }


    /**
     * 统计成功的交易次数
     * @param start
     * @param end
     * @return
     */
    public Long successCount(String start, String end) {

        DateTime now = DateTime.now();
        if(StringUtils.isEmpty(start)) {
            start = now.dayOfMonth().withMinimumValue().toString("yyyy-MM-dd");
        }
        if(StringUtils.isEmpty(end)) {
            end = now.toString("yyyy-MM-dd");
        }

        return saleMapper.successCount(start,end);
    }


    /**
     * 统计完成的交易额
     * @param start
     * @param end
     * @return
     */
    public Float saleMoney(String start, String end) {

        DateTime now = DateTime.now();
        if(StringUtils.isEmpty(start)) {
            start = now.dayOfMonth().withMinimumValue().toString("yyyy-MM-dd");
        }
        if(StringUtils.isEmpty(end)) {
            end = now.toString("yyyy-MM-dd");
        }

        return saleMapper.saleMoney(start,end);
    }


    public List<Map<String, Object>> loadBarData(String start, String end) {
        DateTime now = DateTime.now();
        if(StringUtils.isEmpty(start)) {
            start = now.dayOfMonth().withMinimumValue().toString("yyyy-MM-dd");
        }
        if(StringUtils.isEmpty(end)) {
            end = now.toString("yyyy-MM-dd");
        }
        return saleMapper.totalUserMoney(start,end);
    }
}
