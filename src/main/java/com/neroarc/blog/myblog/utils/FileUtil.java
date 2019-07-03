package com.neroarc.blog.myblog.utils;

import com.aliyun.oss.OSSClient;
import com.neroarc.blog.myblog.constant.OSSClientConstants;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Base64;


/**
 * @author: fjx
 * @date: 2018/9/13 14:40
 * Descripe:上传图片
 */
public class FileUtil {
    /**
     * 上传文件到阿里云OSS
     * @param file 文件流
     * @return 返回文件URL
     */
    public String uploadFile(File file, String subcatalog){

        //初始化OSSClient
        OSSClient ossClient = AliYunOSSClientUtil.getOSSClient();

        String md5Key = AliYunOSSClientUtil.uploadObject2OSS(ossClient, file, OSSClientConstants.BACKET_NAME,
                OSSClientConstants.FOLDER + subcatalog + "/");
        String url = AliYunOSSClientUtil.getUrl(ossClient, md5Key);
        String picUrl = "https://" + OSSClientConstants.BACKET_NAME + "." + OSSClientConstants.ENDPOINT +
                "/" + OSSClientConstants.FOLDER + subcatalog + "/" + file.getName();

        //删除临时生成的文件
        File deleteFile = new File(file.toURI());
        deleteFile.delete();

        return picUrl;

    }


    /**
     * base64字符转换成file
     * @param base64 图片字符串
     * @return file
     */
    public File base64ToFile(String destPath,String base64, String fileName) {
        File file = null;
        //创建文件目录
        String filePath=destPath;
        File  dir=new File(filePath);
        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdirs();
        }
        BufferedOutputStream bos = null;
        java.io.FileOutputStream fos = null;
        try {
            byte[] bytes = Base64.getDecoder().decode(base64);
            file=new File(filePath+"/"+fileName);
            fos = new java.io.FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }


    /**
     * MultipartFile类型文件转File
     * @return File类型文件
     */
    public File multipartFileToFile(MultipartFile multipartFile, String filePath, String fileName){
        File f = null;
        File dir = new File(filePath);
        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdirs();
        }
        if("".equals(multipartFile) || multipartFile.getSize() <= 0){
            multipartFile = null;
        } else {
            try {
                InputStream ins = multipartFile.getInputStream();
                f = new File(filePath + fileName);
                OutputStream os = new FileOutputStream(f);
                int bytesRead = 0;
                byte[] buffer = new byte[8192];
                while ((bytesRead = ins.read(buffer, 0, 8192)) != -1){
                    os.write(buffer, 0, bytesRead);
                }
                os.close();
                ins.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return f;
    }


}
