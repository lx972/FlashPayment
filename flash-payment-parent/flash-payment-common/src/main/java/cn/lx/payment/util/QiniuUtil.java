package cn.lx.payment.util;

import cn.lx.payment.domain.BusinessException;
import cn.lx.payment.domain.CommonErrorCode;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

/**
 * cn.lx.payment.util
 *
 * @Author Administrator
 * @date 16:58
 */
public class QiniuUtil {


    /**
     * 文件上传
     * @param accessKey
     * @param secretKey
     * @param bucket
     * @param filename
     * @param uploadBytes
     */
    public static void upload(String accessKey,String secretKey,String bucket,String filename,byte[] uploadBytes){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region2());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        /*String accessKey = "your access key";
        String secretKey = "your secret key";
        String bucket = "your bucket name";*/
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        //String key = null;
        //byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8");
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(uploadBytes, filename, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            //System.out.println(putRet.key);
            //System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            throw new BusinessException(CommonErrorCode.E_100106);
        }
    }
}
