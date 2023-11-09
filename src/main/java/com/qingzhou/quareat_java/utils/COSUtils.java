package com.qingzhou.quareat_java.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;

//import com.qcloud.cos.COSClient;
//import com.qcloud.cos.ClientConfig;
//import com.qcloud.cos.auth.BasicCOSCredentials;
//import com.qcloud.cos.auth.COSCredentials;
//import com.qcloud.cos.model.AccessControlList;
//import com.qcloud.cos.model.CannedAccessControlList;
//import com.qcloud.cos.model.PutObjectRequest;
//import com.qcloud.cos.model.PutObjectResult;
//import com.qcloud.cos.region.Region;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.plaf.synth.Region;
import java.io.*;
import java.util.Objects;

@Slf4j
@Component
public class COSUtils {
    @Value("${spring.tengxun.SecretId}")
    private String secretId;

    @Value("${spring.tengxun.SecretKey}")
    private String secretKey;

    @Value("${spring.tengxun.region}")
    private String region;

    @Value("${spring.tengxun.bucketName}")
    private String bucketName;

    @Value("${spring.tengxun.url}")
    private String path;

    @Autowired
    private StringUtils stringUtils;


//    /**
//     * 上传文件请求
//     *
//     * @param file
//     * @return
//     */
//    public String handleFileUpload(MultipartFile file){
//        log.info("====================上传COS文件====================");
//        String key = "";
//
//        //检验文件格式是否正确
//        if (!checkFile(file)) {
//            return "";
//        }
//        log.info("检验文件格式是否正确：{}", checkFile(file));
//
//        // 创建临时文件
//        File tempFile = null;
//        try {
//            tempFile = createTempFile(file);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        log.info("创建临时文件：{}", tempFile);
//
//        // 指定上传到COS上的对象键（文件名）
//        key = getFileKey(Objects.requireNonNull(file.getOriginalFilename()));
//
//        String[] splitArray = key.split("\\."); // 注意需要使用转义字符 \
//
//        key = StringUtils.generateRandomString(splitArray[0]) + "." + splitArray[1];
//
//        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, tempFile);
//        log.info("指定上传到COS上的对象键（文件名）：bucketName：{}，key：{}，tempFile：{}", bucketName, key, tempFile);
//
//        // 初始化COS客户端
//        COSClient cosClient = initCOSClient();
//        log.info("初始化COS客户端：{}", cosClient);
//
//        // 上传文件到COS
//        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
//        log.info("上传文件到COS：{}", putObjectResult.getDateStr());
//
//        // 设置预定义 ACL
//        // 设置私有读（Object 的权限默认集成 Bucket的）
//        cosClient.setObjectAcl(bucketName, key, CannedAccessControlList.PublicRead);
//       log.info("设置值私有读：bucketName：{}，key：{}，权限：{}", bucketName, key, CannedAccessControlList.PublicRead);
//
//        AccessControlList accessControlList = cosClient.getObjectAcl(bucketName, key);
//        // 将文件权限转换为预设 ACL, 可选值为：Private, PublicRead, Default
//        CannedAccessControlList cannedAccessControlList = accessControlList.getCannedAccessControl();
//        log.info("私有标识对象键key：{}", key);
//        log.info("文件权限：{}",cannedAccessControlList);
//        log.info("访问控制列表：{}",accessControlList);
//
//        // 删除临时文件
//        boolean delete = tempFile.delete();
//        log.info("删除临时文件：{}", delete);
//        log.info("图片路径url：{}", path + "/" + key);
//        log.info("====================上传COS文件====================");
//        return path + "/" + key;
//    }
//
//    /**
//     * 初始化COS客户端
//     *
//     * @return
//     */
//    private COSClient initCOSClient() {
//        COSCredentials cred = new BasicCOSCredentials("AKIDieKjrrgEUTBchW4MN6Z0IK5aoHhxMZZR", "f6JGxQi3D6i4C5k9HCqDKBylMOM8gz2n");
//        Region region = new Region("ap-beijing");
//        ClientConfig clientConfig = new ClientConfig(region);
//        // 生成 cos 客户端。
//        return new COSClient(cred, clientConfig);
//    }


    /**
     * 生成文件键（带路径和时间戳的文件名）
     *
     * @param originalFileName
     * @return
     */
    private String getFileKey(String originalFileName) {
        String filePath = "";
        //1.获取后缀名 2.去除文件后缀 替换所有特殊字符
        String fileType = originalFileName.substring(originalFileName.lastIndexOf("."));
        String fileStr = StrUtil.removeSuffix(originalFileName, fileType).replaceAll("[^0-9a-zA-Z\\u4e00-\\u9fa5]", "_");
        filePath += new DateTime().toString("yyyyMMddHHmmss") + "_" + fileStr + fileType;
        return filePath;
    }

    /**
     * 创建临时文件
     *
     * @param file
     * @return
     * @throws IOException
     */
    private File createTempFile(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        File tempFile = File.createTempFile("temp", null);
        try (OutputStream outputStream = new FileOutputStream(tempFile)) {
            int bytesRead;
            byte[] buffer = new byte[8192];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
        return tempFile;
    }

    /**
     * 检验文件格式
     *
     * @param file
     * @return
     */
    private Boolean checkFile(MultipartFile file) {
        // 判断文件是否为空
        if (file == null) {
            return false;
        }

        // 获取文件名
        String originalFilename = file.getOriginalFilename();
        if ("".equals(originalFilename) || originalFilename == null) {
            return false;
        }

        //判断文件格式
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image")) {
            return false;
        }

        //判断文件大小
        long fileSize = file.getSize();
        long maxSize = 5 * 1024 * 1024;
        if (fileSize > maxSize) {
            return false;
        }

        return true;
    }
}
