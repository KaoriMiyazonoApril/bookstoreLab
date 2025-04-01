package com.example.tomatomall.serviceImpl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class OssService {

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.access-key-id}")
    private String accessKeyId;

    @Value("${aliyun.oss.access-key-secret}")
    private String accessKeySecret;

    @Value("${aliyun.oss.bucket-name}")
    private String bucketName;

    public String upload(MultipartFile file, String folder) throws IOException {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            String fileName = generateFileName(folder, file.getOriginalFilename());
            ossClient.putObject(bucketName, fileName, file.getInputStream());
            return generateUrl(fileName);
        } catch (OSSException e) {
            throw new RuntimeException("OSS服务异常: " + e.getErrorMessage(), e);
        } finally {
            ossClient.shutdown();
        }
    }

    private String generateFileName(String folder, String originalName) {
        return folder + "/" + UUID.randomUUID() + "-" + originalName;
    }

    private String generateUrl(String fileName) {
        return "https://" + bucketName + "." + endpoint + "/" + fileName;
    }
}