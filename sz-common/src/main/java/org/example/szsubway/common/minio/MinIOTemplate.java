package org.example.szsubway.common.minio;

import io.minio.*;
import lombok.SneakyThrows;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @author fengyadong
 * @date 2022/5/25 16:48
 * @Description
 */
@Component
public class MinIOTemplate {

    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucket:sz_subway}")
    private String defaultBucket;

    /**
     * 上传文件
     *
     * @param fileData         文件
     * @param originalFileName 文件名
     * @return 文件路径
     */
    @SneakyThrows
    public String upload(String originalFileName, byte[] fileData) {
        return upload(originalFileName, fileData, defaultBucket);
    }

    /**
     * 上传文件
     *
     * @param fileData         文件
     * @param originalFileName 文件名
     * @param bucket           指定存储桶，如果桶不存在自动创建
     * @return 文件路径
     */
    @SneakyThrows
    public String upload(String originalFileName, byte[] fileData, String bucket) {
        boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
        if (!isExist) {
            makeupBucket(bucket);
        }
        String fileSavePath = MinIOUtils.getFolder() + "/" +
                MinIOUtils.genFileName(originalFileName);
        try (InputStream in = new ByteArrayInputStream(fileData)) {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(defaultBucket)
                    .object(fileSavePath).stream(in, -1, 10485760)
                    .build());
            return fileSavePath;
        }
    }

    /**
     * 上传文件
     *
     * @param inputStream      输入
     * @param originalFileName 文件名
     * @return 文件路径
     */
    @SneakyThrows
    public String upload(String originalFileName, InputStream inputStream) {
        return upload(originalFileName, inputStream, defaultBucket);
    }

    /**
     * 上传文件
     *
     * @param inputStream      输入
     * @param originalFileName 文件名
     * @param bucket           指定存储桶，如果桶不存在自动创建
     * @return 文件路径
     */
    @SneakyThrows
    public String upload(String originalFileName, InputStream inputStream, String bucket) {
        boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
        if (!isExist) {
            makeupBucket(bucket);
        }
        String fileSavePath = MinIOUtils.getFolder() + "/" +
                MinIOUtils.genFileName(originalFileName);
        try (InputStream in = new BufferedInputStream(inputStream)) {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(defaultBucket)
                    .object(fileSavePath).stream(in, -1, 10485760)
                    .build());
            return fileSavePath;
        }
    }

    /**
     * 下载文件
     *
     * @param path 文件的存储路径
     * @return 文件
     */
    @SneakyThrows
    public byte[] download(String path) {
        return download(path, defaultBucket);
    }

    /**
     * 下载文件
     *
     * @param path   文件的存储路径
     * @param bucket 文件桶
     * @return 文件
     */
    @SneakyThrows
    public byte[] download(String path, String bucket) {
        try (InputStream in = minioClient.getObject(GetObjectArgs.builder()
                .bucket(bucket)
                .object(path)
                .build())) {
            byte[] temp = IOUtils.toByteArray(in);
            in.close();
            return temp;
        }
    }

    /**
     * 下载文件
     *
     * @param path 文件的存储路径
     * @return 文件流
     */
    @SneakyThrows
    public InputStream downloadStream(String path) {
        return downloadStream(path, defaultBucket);
    }

    /**
     * 下载文件
     *
     * @param path   文件的存储路径
     * @param bucket 文件桶
     * @return 文件流
     */
    @SneakyThrows
    public InputStream downloadStream(String path, String bucket) {
        return minioClient.getObject(GetObjectArgs.builder()
                .bucket(bucket)
                .object(path)
                .build());
    }

    /**
     * 删除文件
     *
     * @param path 文件的存储路径
     */
    @SneakyThrows
    public void delete(String path) {
        delete(path, defaultBucket);
    }

    /**
     * 删除文件
     *
     * @param path   文件的存储路径
     * @param bucket 文件桶
     */
    @SneakyThrows
    public void delete(String path, String bucket) {
        minioClient.removeObject(RemoveObjectArgs.builder()
                .bucket(bucket)
                .object(path).build());
    }

    /**
     * 创建文件桶
     *
     * @param bucket 文件桶名字
     */
    @SneakyThrows
    protected void makeupBucket(String bucket) {
        minioClient.makeBucket(
                MakeBucketArgs.builder()
                        .bucket(bucket)
                        .build()
        );
    }

}

