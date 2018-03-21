package com.megatron.picserver.utils;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

import java.io.IOException;

public class UploadUtil {


    //设置好账号的ACCESS_KEY和SECRET_KEY

    String ACCESS_KEY = "tvi_fAgTUoouSoT0sU2tDXIinMeoWbwj16wGHZnB";

    String SECRET_KEY = "xb78tcmx9N7xWRp9mV9xUW8QRPDaVrZD6qybdLrG";

    //要上传的空间

    String bucketname = "logme-img";

    //上传到七牛后保存的文件名

    String key = "my-java.jpg";

    //上传文件的路径

    String FilePath = "C:\\Users\\haoyanZhou\\Pictures\\2017\\2017-09-10\\CSC_0384.JPG";



    //密钥配置

    Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);



    ///////////////////////指定上传的Zone的信息//////////////////

    //第一种方式: 指定具体的要上传的zone

    //注：该具体指定的方式和以下自动识别的方式选择其一即可

    //要上传的空间(bucket)的存储区域为华东时

    // Zone z = Zone.zone0();

    //要上传的空间(bucket)的存储区域为华北时

    // Zone z = Zone.zone1();

    //要上传的空间(bucket)的存储区域为华南时

    // Zone z = Zone.zone2();



    //第二种方式: 自动识别要上传的空间(bucket)的存储区域是华东、华北、华南。

    Zone z = Zone.autoZone();

    Configuration c = new Configuration(z);



    //创建上传对象

    UploadManager uploadManager = new UploadManager(c);



    public static void main(String args[]) throws IOException {

        //new UploadUtil().upload();

    }



    //简单上传，使用默认策略，只需要设置上传的空间名就可以了

    public String getUpToken() {

        return auth.uploadToken(bucketname);

    }



    public String upload(String path, String fileName) throws IOException {

        try {

            //调用put方法上传

            Response res = uploadManager.put(path, fileName, getUpToken());

            //打印返回的信息

            System.out.println(res.bodyString());
            return res.bodyString();

        } catch (QiniuException e) {

            Response r = e.response;

            // 请求失败时打印的异常的信息

            System.out.println(r.toString());
            return r.toString();

        }

    }
}
