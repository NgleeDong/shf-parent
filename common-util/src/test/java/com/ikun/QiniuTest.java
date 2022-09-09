package com.ikun;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.Test;


public class QiniuTest {

    /**
     * 测试向七牛云上传图片
     */
    @Test
    public void test() {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
//        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        //...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "7DhTuI4tmbbbCj_NTjM7TsY0HsKCocDkWEgCrJV3";
        String secretKey = "O_X0QLo7Wjg2wELxlJoPOeBZJsE0mWZj_rxmkqs4";
        //空间的名字
        String bucket = "shf-dev";
        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "C:/Users/dongle/Pictures/timg (1).jpg";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null; //设置文件的名字

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }

    }
}
