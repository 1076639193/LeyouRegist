package com.leyou.upload.service;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public class UploadSerivce {
    @Autowired
    private FastFileStorageClient client;


    public String uploadImage(MultipartFile file) {
//        //创建file对象
//        File f=new File("D:\\upload");
//        if(!f.exists()){
//            //创建文件夹
//            f.mkdirs();
//        }
//        //保存图片
//        try {
//            file.transferTo(new File(f,file.getOriginalFilename()));
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//        return "http://image.leyou.com/"+file.getOriginalFilename();



//        StorePath storePath = this.storageClient.uploadFile(
//                new FileInputStream(file), file.length(), "png", null);

        String url=null;

        //获取浏览器传过来图片file a.png
        String originalFilename = file.getOriginalFilename();
        //获取图片后缀
        String ext=StringUtils.substringAfter(originalFilename,".");//png

        try {
            //上传
            StorePath storePath = client.uploadFile(file.getInputStream(),file.getSize(),ext,null);
            //group1/M00/00/00/wKjkZF4NmfqAAyCiAAAt90FJXPk485.png
            url= "http://image.leyou.com/"+storePath.getFullPath();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return  url;


    }
}
