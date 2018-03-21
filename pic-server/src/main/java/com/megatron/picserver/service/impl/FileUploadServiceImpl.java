package com.megatron.picserver.service.impl;

import com.megatron.picserver.service.FileUploadService;
import com.megatron.picserver.service.ResourcesService;
import com.megatron.picserver.utils.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhouhaoyan on 2017/9/14.
 */
@Service(value = "FileUploadService")
@Transactional
public class FileUploadServiceImpl implements FileUploadService {
    Logger logger = LoggerFactory.getLogger(FileUploadService.class);

    @Value("${IMAGE_PATH}")
    private String imagePath;

    @Autowired
    private ResourcesService resourcesService;

    @Override
    public boolean upload(MultipartFile file, String fileName, String imagePath, Long userId) throws Exception {
        logger.debug("开始上传文件");
        BufferedOutputStream stream = null;

        String  savePath = imagePath + userId;
        // 组返回信息
        Map<String, Object> resMap = new HashMap<String, Object>();

        if (!file.isEmpty()) {

            try {

                File tempDirect = new File(savePath);
                if (!tempDirect.exists()) {
                    tempDirect.mkdirs();
                }
                String originalFileName = fileName;
                // 生成随机文件名称
                String fname = originalFileName;

                String ext;
                if (fname.lastIndexOf(".") > 0) {
                    fileName = fname.substring(0, fname.lastIndexOf("."));
                    ext = fname.substring(fname.lastIndexOf(".") + 1, fname.length());
                } else {
                    ext = "";
                }
                fname = "." + ext;
                fname = System.currentTimeMillis() + RandomUtil.random(6) + fname;

                stream = new BufferedOutputStream(new FileOutputStream(savePath + fname));
                byte[] bytes = file.getBytes();
                stream.write(bytes, 0, bytes.length);

                resMap.put("url", savePath +"/"+ fname);
                resMap.put("fileName", fname);

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            } finally {
                try {
                    if (stream != null) {
                        stream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            return false;
        }



        return true;
    }

}
