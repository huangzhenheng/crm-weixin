package com.tianque.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Maps;
import com.tianque.exception.NotFoundException;
import com.tianque.pojo.Sale;
import com.tianque.pojo.SaleFile;
import com.tianque.pojo.SaleLog;
import com.tianque.pojo.Task;
import com.tianque.service.SaleService;
import com.tianque.service.TaskService;
import com.tianque.util.ShiroUtil;
import com.tianque.util.ToUtf;

@Controller
@RequestMapping("/sales")
public class SalesController {

    @Inject
    private SaleService saleService;

    @Value("${imagePath}")
    private String savePath;
    @Inject
    private TaskService taskService;


    /**
     * 去销售机会列表页
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String list() {
        return "sales/list";
    }


    /**
     * 下载销售机会列表
     *
     * @param request
     * @return
     */

    @RequestMapping(value = "/load", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> load(HttpServletRequest request) {

        String draw = request.getParameter("draw");
        String start = request.getParameter("start");
        String length = request.getParameter("length");

        String name = request.getParameter("name");
        String progress = request.getParameter("progress");

        String startDate = request.getParameter("startdate");
        String endDate = request.getParameter("enddate");
        name = ToUtf.toUTF8(name);
        progress = ToUtf.toUTF8(progress);


        Map<String, Object> params = Maps.newHashMap();
        params.put("start", start);
        params.put("length", length);

        params.put("name", name);
        params.put("progress", progress);
        params.put("startDate", startDate);
        params.put("endDate", endDate);


        List<Sale> saleList = saleService.findCustomerByParams(params);
        Long count = saleService.count();
        Long filterCount = saleService.countByParam(params);

        Map<String, Object> result = Maps.newHashMap();
        result.put("draw", draw);
        result.put("recordsTotal", count);
        result.put("recordsFiltered", filterCount);
        result.put("data", saleList);
        return result;
    }

//
//    @RequestMapping(value = "/load",method = RequestMethod.GET)
//    @ResponseBody
//    public DataTablesResult<Sale> load(HttpServletRequest request) {
//        String draw = request.getParameter("draw");
//        String start = request.getParameter("start");
//        String length = request.getParameter("length");
//
//
//        String name = request.getParameter("name");
//        String progress = request.getParameter("progress");
//        String lasttime = request.getParameter("lasttime");
//        name = ToUtf.toUTF8(name);
//
//
//        Map<String,Object> params = Maps.newHashMap();
//        params.put("start",start);
//        params.put("length",length);
//
//        params.put("name",name);
//        params.put("progress",progress);
//        params.put("lasttime",lasttime);
//
//        List<Sale> saleList = saleService.findCustomerByParams(params);
//        Long count = saleService.count();
//        Long filterCount = saleService.countByParam(params);
//
//        return new DataTablesResult<>(draw,saleList,count,filterCount);
//    }


    /**
     * 显示销售机会的具体信息
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/{id:\\d+}", method = RequestMethod.GET)
    public String viewCustomer(@PathVariable Integer id, Model model) {

        Sale sale = saleService.findSaleById(id);

        if (sale == null) {
            throw new NotFoundException();
        }
		// if (!sale.getUserid().equals(ShiroUtil.getCurrentUserID()) &&
		// !ShiroUtil.isManager()) {
		// return "/error/403";
		// }
        List<SaleFile> saleFileList = saleService.findFileBySaleId(id);
        List<SaleLog> saleLogList = saleService.findSaleLogBySaleId(id);
        List<Task> taskList = taskService.findTaskByUserIdAndSaleid(id,ShiroUtil.getCurrentUserID());
        model.addAttribute("taskList", taskList);
        model.addAttribute("saleFileList", saleFileList);
        model.addAttribute("saleLogList", saleLogList);
        model.addAttribute("sale", sale);

        return "sales/view";
    }

    /**
     * 删除销售机会
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/del/{id:\\d+}", method = RequestMethod.GET)
    public String del(@PathVariable Integer id) {

        saleService.delSale(id);
        // TODO 同时还要删除对应 待办事项，相关资料，跟进记录
        return "redirect:/sales";
    }


    /**
     * 新增销售机会
     */
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    @ResponseBody
    public String saveSale(Sale sale) {
        saleService.saveSales(sale);
        return "success";
    }

    /**
     * 修改机会进度
     *
     * @param sale
     * @return
     */
    @RequestMapping(value = "/progress", method = RequestMethod.POST)
    public String editProgress(Sale sale) {
        saleService.updateProgress(sale);
        return "redirect:/sales/" + sale.getId();
    }

    /**
     * 上传该销售机会的相关文档
     *
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/saveSaleFile", method = RequestMethod.POST)
    @ResponseBody
    public String saveFile(MultipartFile file, Integer saleid) throws IOException {

        if (file.isEmpty()) {
            throw new NotFoundException();
        } else {
            saleService.saveFile(file.getInputStream(), file.getOriginalFilename(), file.getContentType(), file.getSize(), saleid);
        }
        return "success";
    }


    /**
     * 销售经济会的关联文档下载
     */
    @RequestMapping(value = "/download/{id:\\d+}", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable Integer id) throws Exception {

        // 第一步，根据传过来的 文件id查找出文件，并判断其是否为 null
        SaleFile saleFile = saleService.findFileByid(id);
        if (saleFile == null) {
            throw new NotFoundException();
        }

        // 第二步，根据查找出来文件的保存名字，获取文件的储存地址
        File file = new File(savePath, saleFile.getSavename());
        if (!file.exists()) {
            throw new NotFoundException();
        }

        //  第三步，根据文件的储存地址创建文件输入流，同时获得文件的原始名字
        FileInputStream inputStream = new FileInputStream(file);
        String fileName = saleFile.getName();
        fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");

        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(saleFile.getContenttype()))
                .contentLength(file.length())
                .header("Content-Disposition", "attachment;filename=\"" + fileName + "\"")
                .body(new InputStreamResource(inputStream));
    }


    /**
     * 新增跟进记录
     */
    @RequestMapping(value = "/log/new", method = RequestMethod.POST)
    public String saveSaleLog(SaleLog saleLog) {
        saleService.saveSaleLog(saleLog);
        return "redirect:/sales/" + saleLog.getSalesid();
    }

    @RequestMapping(value = "/task/new",method = RequestMethod.POST)
    public String newTask(Task task, String hour, String min) {
        taskService.saveTask(task,hour,min);
        return "redirect:/sales/"+task.getSalesid();
    }
}
