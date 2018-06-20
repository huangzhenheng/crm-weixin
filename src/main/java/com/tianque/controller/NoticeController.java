package com.tianque.controller;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Maps;
import com.tianque.dto.DataTablesResult;
import com.tianque.exception.NotFoundException;
import com.tianque.pojo.Notice;
import com.tianque.service.NoticeService;

@Controller
@RequestMapping("/notice")
public class NoticeController {

    @Inject
    private NoticeService noticeService;

    /**
     * 跳转到公告列表
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String list() {
        return "notice/list";
    }

    /**
     * Ajax调用，下载公告列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/load", method = RequestMethod.GET)
    @ResponseBody
    public DataTablesResult load(HttpServletRequest request) {
        String start = request.getParameter("start");
        String length = request.getParameter("length");
        String draw = request.getParameter("draw");

        Map<String,Object> param = Maps.newHashMap();
        param.put("start",start);
        param.put("length",length);

        List<Notice> noticeList = noticeService.findByParam(param);
        Long count = noticeService.count();

        return new DataTablesResult(draw,noticeList,count,count);
    }

    /**
     * 跳转到发表公告的页面
     * @return
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newNotice() {
        return "notice/new";
    }

    /**
     * 保存公告，并重定向到公告列表
     * @param notice
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String newNotice(Notice notice, RedirectAttributes redirectAttributes) {
        noticeService.saveNotices(notice);
        redirectAttributes.addFlashAttribute("message", "发表成功");
        return "redirect:/notice";
    }

    /**
     * 根据 id 查看特定公告
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/show/{id:\\d+}", method = RequestMethod.GET)
    public String showNotice(@PathVariable Integer id,Model model) {

        Notice notice = noticeService.findById(id);
        if(notice == null) {
            throw new NotFoundException();
        }
        model.addAttribute("notice",notice);
        return "notice/show";
    }


    /**
     * 发表新公告中的文件上传
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/img/upload",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> upload(MultipartFile file,HttpServletRequest request) throws IOException {


        Map<String, Object> result = Maps.newHashMap();

        if(!file.isEmpty()) {

            String path = noticeService.saveImage(file.getInputStream(),file.getOriginalFilename());

            result.put("success", true);
            result.put("file_path", path);
        } else {
            result.put("success", false);
            result.put("msg", "请选择文件");
        }
        return result;
    }
}
