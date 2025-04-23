package com.example.plog.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.plog.service.exceptions.FileUploadException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class S3Service {
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    private final AmazonS3 s3;

    public S3Service(AmazonS3 s3) {
    this.s3 = s3;
    }

    public String upload(MultipartFile file) {
    String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
    ObjectMetadata meta = new ObjectMetadata();
    meta.setContentLength(file.getSize());
    meta.setContentType(file.getContentType());
    try (InputStream is = file.getInputStream()) {
      s3.putObject(bucketName, filename, is, meta);
    } catch (Exception e) {
      log.error("S3 업로드 실패: {}", e.getMessage(), e);
      throw new FileUploadException("S3 업로드 실패");
    }
    return s3.getUrl(bucketName, filename).toString();
    }
}
