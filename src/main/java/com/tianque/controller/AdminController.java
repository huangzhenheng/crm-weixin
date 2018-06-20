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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Maps;
import com.tianque.dao.RoleMapper;
import com.tianque.dto.DataTablesResult;
import com.tianque.dto.JSONResult;
import com.tianque.exception.NotFoundException;
import com.tianque.pojo.User;
import com.tianque.service.UserService;
import com.tianque.util.ToUtf;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Inject
    private UserService userService;
    @Inject
    private RoleMapper roleMapper;


    @RequestMapping(method = RequestMethod.GET)
    public String findAllUser(Model model) {
        model.addAttribute("role", roleMapper.findAllRole());
        return "admin/users";
    }

    /**
     * 使用DataTables显示所有用户数据
     */
    @RequestMapping(value = "/data.json", method = RequestMethod.GET)
    @ResponseBody
    public DataTablesResult load(HttpServletRequest request) {

        String draw = request.getParameter("draw");
        String start = request.getParameter("start"); //当前页偏移量
        String length = request.getParameter("length"); //每页显示多少条数据
        String keyword = request.getParameter("search[value]");
        keyword = ToUtf.toUTF8(keyword);

        Map<String, Object> param = Maps.newHashMap();
        param.put("start", start);
        param.put("length", length);
        param.put("keyword", keyword);


        List<User> users = userService.findAllUser(param);
        Long count = userService.count();
        Long filterCount = userService.findUserCountByParam(param);
        return new DataTablesResult<>(draw, users, count, filterCount);
    }

    /**
     * 检查用户名是否已被占用
     */
    @RequestMapping(value = "/checkuser", method = RequestMethod.GET)
    @ResponseBody
    public String checkUser(@RequestHeader("X-Requested-With") String xRequestedWith,
                            String username) {
        if ("XMLHttpRequest".equals(xRequestedWith)) {
            User user = userService.findUserByName(username);
            if (user == null) {
                return "true";
            }
            return "false";
        } else {
            throw new NotFoundException();
        }
    }

    /**
     * 添加新用户
     */
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    @ResponseBody
	public String saveUsers(User user, Integer[] roleId) {
		userService.saveUser(user, roleId);
        return "success";
    }


    @RequestMapping(value = "/{id:\\d+}.json", method = RequestMethod.GET)
    @ResponseBody
    public JSONResult showUser(@PathVariable Integer id) {
        User user = userService.findUserById(id);
        if (user == null) {
            return new JSONResult("找不到" + id + "对应的用户");
        } else {
            return new JSONResult(user);
        }
    }

    /**
     * 修改用户信息
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public String editBook(User user) {
        userService.updateUser(user);
        return "success";
    }


    /**
     * 重置密码
     */
    @RequestMapping(value = "/{id:\\d+}/resetpassword", method = RequestMethod.GET)
    @ResponseBody
    public String resetPassword(@PathVariable Integer id) {
        userService.resetPw(id);
        return "success";
    }

    /**
     * 批量导出用户人员信息
     *
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/export")
    public void exportUsers(HttpServletResponse response) throws IOException {

        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment;fileName=\"data.xls\"");

        Workbook workbook = new HSSFWorkbook();

        String sheetName = WorkbookUtil.createSafeSheetName("员工表");
        Sheet sheet = workbook.createSheet(sheetName);

        Row row0 = sheet.createRow(0);//第一行

        Cell cell0 = row0.createCell(0);//第一行的第一个单元格
        cell0.setCellValue("用户名");

        row0.createCell(1).setCellValue("真是姓名");
        row0.createCell(2).setCellValue("微信");
        row0.createCell(3).setCellValue("创建时间");

        List<User> userList = userService.findAllUsers();

        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            Row tempRow = sheet.createRow(i + 1);
            tempRow.createCell(0).setCellValue(user.getUsername());
            tempRow.createCell(1).setCellValue(user.getRealname());
            tempRow.createCell(2).setCellValue(user.getWeixin());
            tempRow.createCell(3).setCellValue(user.getCreatetime());
        }

        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();

    }

    /**
     * 批量添加用户
     * @param
     * @throws IOException
     */
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public String importUsers(MultipartFile file, HttpServletRequest request) throws IOException {

        Workbook workbook = new HSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            User user = new User();
            for (int j = 0; j < row.getLastCellNum(); j++) {
                Cell cell = row.getCell(j);
                if (j == 0) {
                    user.setUsername(cell.getStringCellValue());
                } else if (j == 1) {
                    user.setRealname(cell.getStringCellValue());
                } else if (j == 2) {
                    user.setWeixin(cell.getStringCellValue());
                }else if (j == 3) {
					// user.setRoleid(new
					// Double(cell.getNumericCellValue()).intValue());
                }

            }
			// userService.saveUser(user);
        }
        return "redirect:/admin";
    }

//    @RequestMapping(value = "/export")
//    public void export(HttpServletResponse response) throws IOException {
//
//    List<User> userList = userService.findAllUsers();
//
//    response.setContentType("text/plain;charset=GBK");
//    response.addHeader("Content-Disposition","attachment;fileName=\"data.csv\"");
//
//    PrintWriter out = response.getWriter();
//    out.print("用户名,真是姓名,微信,创建时间\r\n");
//    for(User user : userList) {
//        out.print(user.getUsername().toString()+","+user.getRealname().toString()+","+user.getWeixin().toString()+","+user.getCreatetime().toString()+"\r\n");
//    }
//
//    out.flush();
//    out.close();
//    }


}
