package com.changgou.file.util;

import com.changgou.file.FastDFSFile;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.core.io.ClassPathResource;

/**
 * @Author: Deng
 * @date: 2020-05-07 23:23
 * @description:
 */
public class FileUtils {
    static {
        ClassPathResource resource = new ClassPathResource("fdfs_client.conf");
        try {
            ClientGlobal.init(resource.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static StorageClient getClient() throws Exception {
        //2.加载配置文件

        //3.创建trackerclient 对象
        TrackerClient trackerClient = new TrackerClient();

        //4.创建trackerServer对象
        TrackerServer trackerServer = trackerClient.getConnection();

        //5.创建storageServer对象
        StorageServer storageServer = null;

        //6.创建storageClient对象
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        return storageClient;
    }

    public static String[] myUpload(FastDFSFile fastDFSFile) throws Exception {
        StorageClient client = getClient();
        NameValuePair[] nameValuePairs = new NameValuePair[]{
                new NameValuePair(fastDFSFile.getAuthor()),
                new NameValuePair(fastDFSFile.getExt())
        };
        String[] jpgs = client.upload_file(fastDFSFile.getContent(), fastDFSFile.getExt(), nameValuePairs);
        return jpgs;
    }

}
