package com.changgou.test;

import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


/**
 * @Author: Deng
 * @date: 2020-05-07 20:58
 * @description:
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class TestFile {

    //上传图片
    @Test
    public void testUpload() throws Exception {

        StorageClient storageClient = getClient();

        String[] jpgs = storageClient.upload_appender_file
                ("C:\\Users\\Deng\\Desktop\\杂物\\图片\\QQ图片20200428160942.jpg",
                        "jpg", null);

        for (String jpg : jpgs) {
            System.out.println(jpg);
        }

    }
    //下载图片
    @Test
    public void testDownload() throws Exception {
        StorageClient storageClient = getClient();
        byte[] file = storageClient.download_file("group1", "M00/00/00/wKjThF60CHKELzgEAAAAAL6IeXI191.jpg");

        OutputStream os = new FileOutputStream("D:\\Develop\\IDEA\\changgou\\changgou-parent\\changgou-service\\changgou-service-file\\src\\main\\resources\\a.jpg");
        os.write(file);
    }

    //删除图片
    @Test
    public void testDelete() throws Exception {
        StorageClient storageClient = getClient();
        storageClient.delete_file("group1","M00/00/00/wKjThF60CHKELzgEAAAAAL6IeXI191.jpg");

    }
    private StorageClient getClient() throws IOException, MyException {
        //2.加载配置文件
        ClientGlobal.init("D:\\Develop\\IDEA\\changgou\\changgou-parent\\changgou-service\\changgou-service-file\\src\\main\\resources\\fdfs_client.conf");

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


}
