package com.tianque.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.tianque.dao.SaleMapper;
import com.tianque.dto.DataTablesResult;
import com.tianque.exception.NotFoundException;
import com.tianque.pojo.Customer;
import com.tianque.pojo.Sale;
import com.tianque.pojo.Task;
import com.tianque.pojo.User;
import com.tianque.service.CustomerService;
import com.tianque.service.TaskService;
import com.tianque.service.UserService;
import com.tianque.util.ShiroUtil;
import com.tianque.util.ToUtf;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Inject
    private CustomerService customerService;
    @Inject
    private UserService userService;
    @Inject
    private SaleMapper saleMapper;
    @Inject
    private TaskService taskService;


    /**
     * 去客户列表页
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    private String list(){
        return "customer/list";
    }

    /**
     * Ajax下载客户信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/load",method = RequestMethod.GET)
    @ResponseBody
    public DataTablesResult<Customer> load(HttpServletRequest request) {

        String draw = request.getParameter("draw");
        String start = request.getParameter("start");
        String length = request.getParameter("length");
        String keyword = request.getParameter("search[value]");
        keyword= ToUtf.toUTF8(keyword);

        Map<String,Object> params = Maps.newHashMap();
        params.put("start",start);
        params.put("length",length);
        params.put("keyword",keyword);


        List<Customer> customerList = customerService.findCustomerByParams(params);
        Long count = customerService.count();
        Long filterCount = customerService.countByParam(params);

        return new DataTablesResult<>(draw,customerList,count,filterCount);

    }

    /**
     * 获取公司列表
     * @return
     */
    @RequestMapping(value = "/companyList",method = RequestMethod.GET)
    @ResponseBody
    public List<Customer> showAllCompanyJson() {
        return customerService.findAllCompany();
    }

    /**
     * 获取客户列表
     * @return
     */
    @RequestMapping(value = "/customerList",method = RequestMethod.GET)
    @ResponseBody
    public List<Customer> showAllCustomer() {

        return customerService.findAllCustomer(ShiroUtil.getCurrentUserID());
    }
    /**
     * 保存客户信息
     * @param customer
     * @return
     */
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    @ResponseBody
    public String savecustomer(Customer customer) {
        customerService.saveCustomer(customer);
        return "success";
    }


    /**
     * 删除客户信息
     */
    @RequestMapping(value = "/del/{id:\\d+}",method = RequestMethod.GET)
    @ResponseBody
    public String del(@PathVariable Integer id) {
        customerService.delCustomer(id);
        return "success";
    }

    /**
     * 显示要修改的客户信息
     */
    @RequestMapping(value = "/edit/{id:\\d+}",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> editCustomer(@PathVariable Integer id) {

        Customer customer = customerService.findCustomerById(id);

        Map<String,Object> result = Maps.newHashMap();

        if(customer == null) {
            result.put("state","error");
            result.put("message","找不到对应的客户");
        } else {
            List<Customer> companyList = customerService.findAllCompany();
            result.put("state","success");
            result.put("customer",customer);
            result.put("companyList",companyList);
        }
        return result;
    }

    /**
     * 更新修改后的客户信息
     * @param customer
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public String update(Customer customer) {
        customerService.updateCustomer(customer);
        return "success";
    }

    /**
     * 显示客户具体信息
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/{id:\\d+}",method = RequestMethod.GET)
    public String viewCustomer(@PathVariable Integer id,Model model) {

        Customer customer = customerService.findCustomerById(id);

        if(customer == null) {
            throw new NotFoundException();
        }
		if (customer.getCreateUser() != null &&
				!customer.getCreateUser().getId().equals(ShiroUtil.getCurrentUserID()) &&
				!ShiroUtil.isManager()) {
			return "/error/403";
		}
        model.addAttribute("roleid",ShiroUtil.getCurrentRoleId());
        model.addAttribute("customer",customer);
		model.addAttribute("user", userService.findUserById(customer.getCreateUser().getId()));

        List<Sale> saleList = saleMapper.findByCustId(id);
        model.addAttribute("saleList", saleList);
        List<Task> taskList = taskService.findTaskByUserIdAndCustit(id,ShiroUtil.getCurrentUserID());
        model.addAttribute("taskList", taskList);
        if(customer.getType().equals(Customer.CUSTOMER_TYPE_COMPANY)) {
            List<Customer> customerList = customerService.findCustomerByCompanyId(id);
            model.addAttribute("customerList",customerList);
        }

        //加载所有员工
        List<User> userList = userService.findAllUsers();
        model.addAttribute("userList",userList);

        return "customer/view";
    }

    /**
     * 导出客户信息
     */
    @RequestMapping(value = "/export")
    public void exportUsers(HttpServletResponse response) throws IOException {

        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment;fileName=\"date.xls\"");

        Workbook workbook = new HSSFWorkbook();

        String sheetName = WorkbookUtil.createSafeSheetName("客户信息表");
        Sheet sheet = workbook.createSheet(sheetName);

        Row row0 = sheet.createRow(0);//第一行

        Cell cell0 = row0.createCell(0);//第一行的第一个单元格
        cell0.setCellValue("客户名称");

        row0.createCell(1).setCellValue("联系电话");
        row0.createCell(2).setCellValue("微信");
        row0.createCell(3).setCellValue("电子邮件");
        row0.createCell(4).setCellValue("等级");
        row0.createCell(5).setCellValue("地址");
        row0.createCell(6).setCellValue("备注");

        List<Customer> customers = customerService.findAllCustomer();

        for (int i = 0; i < customers.size(); i++) {
            Customer customer = customers.get(i);
            Row tempRow = sheet.createRow(i + 1);
            tempRow.createCell(0).setCellValue(customer.getCustname());
            tempRow.createCell(1).setCellValue(customer.getTel());
            tempRow.createCell(2).setCellValue(customer.getWeixin());
            tempRow.createCell(3).setCellValue(customer.getEmail());
            tempRow.createCell(4).setCellValue(customer.getLevel());
            tempRow.createCell(5).setCellValue(customer.getAddress());
            tempRow.createCell(6).setCellValue(customer.getRemark());
        }

        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();

    }

    /**
     * 公开客户
     * @param id
     * @return
     */
    @RequestMapping(value = "/open/{id:\\d+}",method = RequestMethod.GET)
    public String openCustomer(@PathVariable Integer id) {

        Customer customer = customerService.findCustomerById(id);

        if(customer == null) {
           throw new NotFoundException();
        }

        customerService.openCustomer(customer);
        return "redirect:/customer/"+id;
    }

    /**
     * 转交客户
     */
    @RequestMapping(value = "/move",method = RequestMethod.POST)
    public String moveCustomer(Integer id,Integer userid) {

        Customer customer = customerService.findCustomerById(id);

        if (customer==null){
            throw new NotFoundException();
        }

		// customer.setUserid(userid);
		// customer.setUsername(userService.findUserById(userid).getRealname());
		// customerService.move(customer);
        //判断是不是经理
        if(ShiroUtil.getCurrentRoleId()==2){
            return "redirect:/customer/"+id;
        }
        return "redirect:/customer";
    }


    @RequestMapping(value = "/qrcode/{id:\\d+}.png",method = RequestMethod.GET)
    public void makeQrCode(@PathVariable Integer id,HttpServletResponse response) throws IOException, WriterException {

        String mecard = customerService.makeMeCard(id);

        Map<EncodeHintType,String> hints = Maps.newHashMap();
        hints.put(EncodeHintType.CHARACTER_SET,"UTF-8");

        BitMatrix bitMatrix = new MultiFormatWriter().encode(mecard, BarcodeFormat.QR_CODE,150,150,hints);

        OutputStream outputStream = response.getOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix,"png",outputStream);
        outputStream.flush();
        outputStream.close();
    }



    @RequestMapping(value = "/task/new",method = RequestMethod.POST)
    public String newTask(Task task, String hour, String min) {
        taskService.saveTask(task,hour,min);
        return "redirect:/customer/"+task.getCustid();
    }
}
