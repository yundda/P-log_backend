package com.example.plog.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

@Service
public class S3Service {
    @Value("${cloud.aws.s3.bucket}") private String bucketName;
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
    } catch (IOException e) {
      throw new RuntimeException("S3 업로드 실패", e);
    }
    return s3.getUrl(bucketName, filename).toString();
    }
}
