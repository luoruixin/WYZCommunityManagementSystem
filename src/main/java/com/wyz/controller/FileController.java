package com.wyz.controller;

import cn.hutool.core.util.StrUtil;
import com.wyz.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${WYZCommunityManagementSystem.picturePath}")
    private String imgPath;
    @Value("${WYZCommunityManagementSystem.videoPath}")
    private String videoPath;

    //图片上传
    @PostMapping("/uploadImg")
    public R<String> upload(@RequestParam("file") MultipartFile file) {  //file这个名字不能随便起，必须和页面的name保持一致

        log.info(file.toString());

        //获取原始文件名
        String originalFilename = file.getOriginalFilename();

        //使用UUID重新生成文件名，防止文件名称重复造出文件覆盖
        String fileName = createNewImgName(originalFilename);  //这里的fileName是全路径名
        File dir=new File(imgPath);
        if(!dir.exists()){
            //目录不存在
            dir.mkdirs();
        }
        try {
            //将临时文件转存到指定位置
            file.transferTo(new File(imgPath+fileName));
        } catch (IOException e) {
            e.printStackTrace();
            return R.error("上传失败");
        }
        //最后需要向页面返回文件名称
        return R.success(fileName);
    }

    //图片下载
    @GetMapping("/downloadImg")
    public void download(String name, HttpServletResponse resp){
        //这里的name是全路径名
        try {
            //输入流，通过输入流读取文件内容
            FileInputStream fileInputStream=new FileInputStream(new File(imgPath+name));
            // 输出流，通过输出流将文件写回浏览器，在浏览器展示图片
            ServletOutputStream outputStream = resp.getOutputStream();

            resp.setContentType("image/jpeg");//告诉浏览器下面的流是一张图片

            //完成流的copy
            int len=0;
            byte[] bytes=new byte[1024];
            while ((len= fileInputStream.read(bytes))!=-1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }
            outputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    //视频上传
    @PostMapping("/uploadVideo")
    public R<String> uploadVideo(@RequestParam("file") MultipartFile file) {  //file这个名字不能随便起，必须和页面的name保持一致

        log.info(file.toString());

        //获取原始文件名
        String originalFilename = file.getOriginalFilename();

        //使用UUID重新生成文件名，防止文件名称重复造出文件覆盖
        String fileName = createNewVideoName(originalFilename);  //这里的fileName是全路径名
        File dir=new File(videoPath);
        if(!dir.exists()){
            //目录不存在
            dir.mkdirs();
        }
        try {
            //将临时文件转存到指定位置
            file.transferTo(new File(videoPath+fileName));
        } catch (IOException e) {
            e.printStackTrace();
            return R.error("上传失败");
        }
        //最后需要向页面返回文件名称
        return R.success(fileName);
    }
    //------------------------------------创建文件名的工具类----------------------------------------
    
    private String createNewImgName(String originalFilename) {
        // 获取后缀
        String suffix = StrUtil.subAfter(originalFilename, ".", true);
        // 生成目录
        String name = UUID.randomUUID().toString();
        int hash = name.hashCode();
        int d1 = hash & 0xF;
        int d2 = (hash >> 4) & 0xF;
        // 判断目录是否存在
        File dir = new File(imgPath, StrUtil.format("/{}/{}", d1, d2));
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // 生成文件名
        return StrUtil.format("/{}/{}/{}.{}", d1, d2, name, suffix);
    }

    private String createNewVideoName(String originalFilename) {
        // 获取后缀
        String suffix = StrUtil.subAfter(originalFilename, ".", true);
        // 生成目录
        String name = UUID.randomUUID().toString();
        int hash = name.hashCode();
        int d1 = hash & 0xF;
        int d2 = (hash >> 4) & 0xF;
        // 判断目录是否存在
        File dir = new File(videoPath, StrUtil.format("/{}/{}", d1, d2));
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // 生成文件名
        return StrUtil.format("/{}/{}/{}.{}", d1, d2, name, suffix);
    }
}
