package com.changgou.file.controller;

import com.changgou.file.FastDFSFile;
import com.changgou.file.util.FileUtils;
import org.csource.fastdfs.StorageClient;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @Author: Deng
 * @date: 2020-05-07 23:15
 * @description:
 */
@RestController
public class UploadController {

    @PostMapping("upload")
    public String upload(MultipartFile file) {
        //根据文件的流,
        //
        if (file.isEmpty()) {
            return null;
        }
        try {
            FastDFSFile fastDFSFile = new FastDFSFile(file.getOriginalFilename(),
                    file.getBytes(),
                    StringUtils.getFilenameExtension(file.getOriginalFilename()));
            String[] myUpload = FileUtils.myUpload(fastDFSFile);
            return "http://192.168.211.132:8080/" + myUpload[0] + "/" + myUpload[1];
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
