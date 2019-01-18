package com.lsz.depot.apply.api;

import com.lsz.core.common.ResponseInfo;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;

/**
 * 初始化
 */
@RestController
@RequestMapping(value = "/api")
public class UploadApi {
    public static final String ImgDir = "E:\\img\\";

    private static String getKzm(String fileName){
        int indexOf = fileName.lastIndexOf(".");
        if (indexOf == -1){
            return "";
        }
        return fileName.substring(indexOf);
    }

    @RequestMapping("/fileUpload/{dir}/{name}")
    public ResponseInfo<String> fileUpload(@RequestParam("file") MultipartFile file, @PathVariable String dir, @PathVariable String name) {
        String fileName = new Date().getTime() + "--" + name + getKzm(file.getOriginalFilename());
        try {
            //获取输出流
            OutputStream os = new FileOutputStream(ImgDir + dir + File.separator + fileName);
            //获取输入流 CommonsMultipartFile 中可以直接得到文件的流
            InputStream is = file.getInputStream();
            int temp;
            //一个一个字节的读取并写入
            while ((temp = is.read()) != (-1)) {
                os.write(temp);
            }

            os.flush();
            os.close();
            is.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return ResponseInfo.assertion("/api/uploadImg/" + dir + "/" + fileName);
    }

    @RequestMapping(value="/uploadImg/{dir}/{fileName}")
    public void  download(HttpServletRequest request,
                          HttpServletResponse response,
                                           @PathVariable String dir, @PathVariable String fileName)throws Exception {
        //下载文件路径
        String path = ImgDir;
        File file = new File(path + File.separator + dir + File.separator + fileName);
//        HttpHeaders headers = new HttpHeaders();
//        //下载显示的文件名，解决中文名称乱码问题
//        String downloadFielName = new String(fileName.getBytes("UTF-8"),"iso-8859-1");
//        //通知浏览器以attachment（下载方式）打开图片
//        headers.setContentDispositionFormData("attachment", downloadFielName);
//        //application/octet-stream ： 二进制流数据（最常见的文件下载）。
//        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

 FileInputStream inputStream = new FileInputStream(file);
  byte[] data = new byte[(int)file.length()];
  int length = inputStream.read(data);
  inputStream.close();


  response.setContentType("image/png");


  OutputStream stream = response.getOutputStream();
  stream.write(data);
  stream.flush();
  stream.close();
    }
}
