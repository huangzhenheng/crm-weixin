package com.tianque.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.tianque.dao.CustomerMapper;
import com.tianque.dao.SaleFileMapper;
import com.tianque.dao.SaleLogMapper;
import com.tianque.dao.SaleMapper;
import com.tianque.exception.NotFoundException;
import com.tianque.pojo.Sale;
import com.tianque.pojo.SaleFile;
import com.tianque.pojo.SaleLog;
import com.tianque.util.ShiroUtil;

@Named
public class SaleService {

    @Inject
    private SaleMapper saleMapper;
    @Inject
    private CustomerMapper customerMapper;
    @Inject
    private SaleLogMapper saleLogMapper;
    @Inject
    private SaleFileMapper saleFileMapper;
    @Value("${imagePath}")
    private String savePath;

    /**
     * 根据当前用户身份查找机会列表
     * @param params
     * @return
     */
    public List<Sale> findCustomerByParams(Map<String, Object> params) {

        if(ShiroUtil.isEmployee()) {
            params.put("userid",ShiroUtil.getCurrentUserID());
        }
        return saleMapper.findByByParams(params);
    }

    /**
     * 获取销售机会的总数量
     * @return
     */
    public Long count() {
        if(ShiroUtil.isEmployee()) {
            Map<String,Object> params = Maps.newHashMap();
            params.put("userid",ShiroUtil.getCurrentUserID());
            return saleMapper.countByStr(params);
        }
        return saleMapper.count();
    }

    /**
     * 根据参数，获取销售机会的总数量
     * @return
     */
    public Long countByParam(Map<String, Object> params) {

        if(ShiroUtil.isEmployee()) {
            params.put("userid",ShiroUtil.getCurrentUserID());
        }
        return saleMapper.countByStr(params);
    }


    public Sale findSaleById(Integer id) {
        return saleMapper.findOneById(id);
    }

    /**
     * 删除销售机会
     * @param id
     */

    @Transactional
    public void delSale(Integer id) {

        saleLogMapper.delLogBySaleid(id);
        saleFileMapper.delFileBySaleid(id);

        saleMapper.del(id);
    }


    /**
     * 新增销售机会
     * @param sale
     */
    @Transactional
    public void saveSales(Sale sale) {

		saleMapper.save(sale);

        //添加跟进日志
        SaleLog saleLog = new SaleLog();
        saleLog.setType(SaleLog.LOG_TYPE_AUTO);
        saleLog.setContext(ShiroUtil.getCurrentRealName() + " 创建了该销售机会");
        saleLog.setSalesid(sale.getId());
        saleLogMapper.saveLog(saleLog);
    }


    /**
     *  修改机会进度，并添加跟进日志
     * @param sale
     */
    @Transactional
    public void updateProgress(Sale sale) {

        Sale sal = saleMapper.findOneById(sale.getId());
        if (sal==null){
            throw new NotFoundException();
        }
        if (sale.getProgress().equals("完成交易")){
            sal.setSuccesstime(DateTime.now().toString("yyyy-MM-dd"));
        }else {
            sal.setSuccesstime(null);
        }
        sal.setProgress(sale.getProgress());
        sal.setLasttime(DateTime.now().toString("yyyy-MM-dd"));
        saleMapper.updatProgress(sal);

        //添加跟进日志
        SaleLog saleLog = new SaleLog();
        saleLog.setType(SaleLog.LOG_TYPE_AUTO);
        saleLog.setContext(ShiroUtil.getCurrentRealName() + " 更改进度为：" + sale.getProgress());
        saleLog.setSalesid(sale.getId());
        saleLogMapper.saveLog(saleLog);
    }


    public List<SaleFile> findFileBySaleId(Integer id) {
        return saleFileMapper.findFileBySaleid(id);
    }

    /**
     * 保存机会文件
     * @param inputStream
     * @param originalFilename
     * @param contentType
     * @param size
     * @param saleid
     */
    @Transactional
    public void saveFile(InputStream inputStream, String originalFilename, String contentType, long size, Integer saleid) {

        File dir = new File(savePath);
        if (!dir.exists()) {
            dir.mkdir();
        }

        String extName = "";
        if(originalFilename.lastIndexOf(".") != -1) {
            extName = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String newFileName = UUID.randomUUID().toString() + extName;
        try {
            FileOutputStream outputStream = new FileOutputStream(new File(dir,newFileName));
            IOUtils.copy(inputStream,outputStream);
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("文件上传失败",e);
        }

        SaleFile saleFile = new SaleFile();
        saleFile.setName(originalFilename);
        saleFile.setSalesid(saleid);
        saleFile.setContenttype(contentType);
        saleFile.setSize(FileUtils.byteCountToDisplaySize(size));
        saleFile.setSavename(newFileName);
        saleFileMapper.saveFile(saleFile);
    }

    /**
     * 查找机会文件
     * @param id
     * @return
     */
    public SaleFile findFileByid(Integer id) {
        return saleFileMapper.findOneById(id);
    }

    public List<SaleLog> findSaleLogBySaleId(Integer id) {
        return saleLogMapper.findBySaleid(id);
    }

    /**
     * 保存机会记录
     * @param saleLog
     */
    @Transactional
    public void saveSaleLog(SaleLog saleLog) {

        saleLog.setType(SaleLog.LOG_TYPE_INPUT);
        saleLogMapper.saveLog(saleLog);

        //修改机会的最后跟进时间
        Sale sale = saleMapper.findOneById(saleLog.getSalesid());
        sale.setLasttime(DateTime.now().toString("yyyy-MM-dd"));
        saleMapper.updateLasttime(sale);
    }
}
