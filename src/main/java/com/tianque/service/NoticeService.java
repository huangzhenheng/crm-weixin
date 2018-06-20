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

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;

import com.tianque.dao.NoticeMapper;
import com.tianque.pojo.Notice;
import com.tianque.util.ShiroUtil;

@Named
public class NoticeService {

    @Inject
    private NoticeMapper noticeMapper;

    @Value("${imagePath}")
    private String imageSavePath;


    /**
     * 保存公告
     * @param notice
     */
    public void saveNotices(Notice notice){
        notice.setUserid(ShiroUtil.getCurrentUserID());
        notice.setRealname(ShiroUtil.getCurrentRealName());
        noticeMapper.saveNotice(notice);
    }

    /**
     * 查找所有公告
     * @return
     */
    public List<Notice> findAll() {
        return noticeMapper.findAllNotice();
    }

    /**
     * 通过id查找公告
     * @param id
     * @return
     */
    public Notice findById(Integer id) {
        return noticeMapper.findNoticeById(id);
    }

    /**
     * 统计公告条数
     * @return
     */
    public Long count() {
        return noticeMapper.count();
    }

    /**
     * 根据分页参数，查找公告
     * @param param
     * @return
     */
    public List<Notice> findByParam(Map<String, Object> param) {
        return noticeMapper.findByParam(param);
    }


    public String saveImage(InputStream inputStream, String originalFilename) throws IOException {

        File dir = new File(imageSavePath);
        if (!dir.exists()) {
            dir.mkdir();
        }

        String extName = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFileName = UUID.randomUUID().toString()+extName;

        FileOutputStream outputStream = new FileOutputStream(new File(dir,newFileName));
        IOUtils.copy(inputStream,outputStream);

        outputStream.flush();
        outputStream.close();
        inputStream.close();

        return "/preview/"+newFileName;
    }
}
