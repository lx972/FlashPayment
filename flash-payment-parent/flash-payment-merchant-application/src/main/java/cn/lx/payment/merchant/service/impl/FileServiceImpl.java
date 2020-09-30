package cn.lx.payment.merchant.service.impl;

import cn.lx.payment.domain.BusinessException;
import cn.lx.payment.domain.CommonErrorCode;
import cn.lx.payment.merchant.service.IFileService;
import cn.lx.payment.util.QiniuUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * 文件上传
 *
 * @Author Administrator
 * @date 16:36
 */
@Service
public class FileServiceImpl implements IFileService {

    @Value("${qiniu.accessKey}")
    private String accessKey;

    @Value("${qiniu.secretKey}")
    private String secretKey;

    @Value("${qiniu.bucket}")
    private  String bucket;

    @Value("${qiniu.realmName}")
    private  String realmName;

    /**
     * 证件照上传
     *
     * @param file
     * @return
     */
    @Override
    public String upload(MultipartFile file) {

        String originalFilename = file.getOriginalFilename();
        //扩展名
        String stripx = originalFilename.substring(originalFilename.lastIndexOf("."));
        //新文件名
        String filename= UUID.randomUUID().toString()+stripx;
        byte[] bytes=null;
        try {
           bytes = file.getBytes();
        } catch (IOException e) {
           throw new BusinessException(CommonErrorCode.E_100106);
        }
        QiniuUtil.upload(accessKey,secretKey,bucket,filename,bytes);
        return realmName+"/"+filename;
    }
}
