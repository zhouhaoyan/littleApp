package com.megatron.picserver.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by zhouhaoyan on 2017/9/14.
 */
public interface FileUploadService {

    boolean upload(MultipartFile file, String fileName, String savePath, Long userId) throws Exception;

}
