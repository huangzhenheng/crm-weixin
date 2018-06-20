package com.tianque.service;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import com.tianque.dao.DocumentMapper;
import com.tianque.pojo.Document;
import com.tianque.pojo.User;
import com.tianque.util.ShiroUtil;

@Named
public class DocumentService {

    @Inject
    private DocumentMapper documentMapper;
    @Value("${imagePath}")
    private String savePath;


    /**
     * 通过父 id 查找文件
     * @param fid
     * @return
     */
    public List<Document> findByFid(Integer fid) {

        return documentMapper.findByFileFid(fid);
    }

    /**
     * 保存新建文件夹
     * @param name 文件夹名称
     * @param fid  文件所在的父id
     */
    public void saveDir(String name, Integer fid) {
        Document document = new Document();
        document.setName(name);
        document.setFid(fid);

		document.setCreateuser(new User(ShiroUtil.getCurrentUserID()));
        document.setType(Document.TYPE_DIR);
        documentMapper.save(document);
    }

    /**
     * 获取文件的父id，供返回上一级使用
     * @param fid
     * @return
     */
    public Integer findFid(Integer fid) {

        Document document = documentMapper.findfid(fid);
        if (document != null){
            return document.getFid();
        }else {
            return 0;
        }
    }

    /**
     * 通过id查找文件
     * @param id
     * @return
     */
    public Document findByid(Integer id) {
        return documentMapper.findfid(id);
    }


    /**
     * 保存上传的文件
     * @param inputStream 文件输入流
     * @param originalFilename 文件的原始名字
     * @param contentType 文件的MIME类型
     * @param size 文件的大小
     * @param fid  文件的父id
     */
    @Transactional
    public void saveFile(InputStream inputStream, String originalFilename, String contentType, long size, Integer fid) {

		// String md5 = null;

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
			// md5 = DigestUtils.md5Hex(inputStream);
            FileOutputStream outputStream = new FileOutputStream(new File(dir,newFileName));
            IOUtils.copy(inputStream,outputStream);
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("文件上传失败",e);
        }

        Document document = new Document();
        document.setName(originalFilename);
        document.setFid(fid);
        document.setType(Document.TYPE_DOC);
		document.setCreateuser(new User(ShiroUtil.getCurrentUserID()));
        document.setContexttype(contentType);
        document.setSize(FileUtils.byteCountToDisplaySize(size));
        document.setFilename(newFileName);
		// document.setMd5(md5);
		documentMapper.save(document);
    }

	public void delFileByID(Integer id) {
		documentMapper.delFileByID(id);
	}
}
