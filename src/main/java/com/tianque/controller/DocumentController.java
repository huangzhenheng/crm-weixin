package com.tianque.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.tianque.exception.NotFoundException;
import com.tianque.pojo.Document;
import com.tianque.service.DocumentService;

@Controller
@RequestMapping("/document")
public class DocumentController {

    @Inject
    private DocumentService documentService;
    @Value("${imagePath}")
    private String savePath;

    @RequestMapping(method = RequestMethod.GET)
    private String list(Model model, @RequestParam(required = false, defaultValue = "0") Integer fid) {

        List<Document> documents = documentService.findByFid(fid);
        model.addAttribute("documents", documents);
        model.addAttribute("fid", fid);
        fid = documentService.findFid(fid);
        model.addAttribute("url", "/document?fid=" + fid);
        return "document/list";
    }


    @RequestMapping(value = "/dir/new", method = RequestMethod.POST)
    public String saveDir(String name, Integer fid) {
        documentService.saveDir(name, fid);
        return "redirect:/document?fid=" + fid;
    }

    /**
     * 保存上传的文件
     * @param file
     * @param fid
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/savefile", method = RequestMethod.POST)
    @ResponseBody
    public String saveFile(MultipartFile file,Integer fid) throws IOException {

        if(file.isEmpty()) {
            throw new NotFoundException();
        } else {
            documentService.saveFile(file.getInputStream(),file.getOriginalFilename(),file.getContentType(),file.getSize(),fid);
        }
        return "success";
    }


    /**
     * 下载文件
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/download/{id:\\d+}", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable Integer id) throws Exception {

        // 第一步，根据传过来的 文件id查找出文件，并判断其是否为 null
        Document document = documentService.findByid(id);
        if(document == null) {
            throw new NotFoundException();
        }

        // 第二步，根据查找出来文件的保存名字，获取文件的储存地址
        File file = new File(savePath,document.getFilename());
        if(!file.exists()) {
            throw new NotFoundException();
        }

        //  第三步，根据文件的储存地址创建文件输入流，同时获得文件的原始名字
        FileInputStream inputStream = new FileInputStream(file);
        String fileName = document.getName();
        fileName = new String(fileName.getBytes("UTF-8"),"ISO8859-1");

        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(document.getContexttype()))
                .contentLength(file.length())
                .header("Content-Disposition","attachment;filename=\""+fileName+"\"")
                .body(new InputStreamResource(inputStream));
    }

	@RequestMapping(value = "/del/{fid:\\d+}/{id:\\d+}", method = RequestMethod.GET)
	public String delFile(@PathVariable Integer id, @PathVariable Integer fid) throws Exception {

		Document document = documentService.findByid(id);
		if (document != null) {
			documentService.delFileByID(id);
			deleteFile(savePath + "/" + document.getFilename());
		}
		return "redirect:/document?fid=" + fid;
	}


	private void deleteFile(String filePath) {
		File file = new File(filePath);
		if (file.isFile() && file.exists()) {
			file.delete();
		}
	}
}
