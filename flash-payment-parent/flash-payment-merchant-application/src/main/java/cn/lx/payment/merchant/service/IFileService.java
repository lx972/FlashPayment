package cn.lx.payment.merchant.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * cn.lx.payment.merchant.service
 *
 * @Author Administrator
 * @date 16:36
 */
public interface IFileService {


    /**
     * 证件照上传
     * @param file
     * @return
     */
    String upload(MultipartFile file);
}
