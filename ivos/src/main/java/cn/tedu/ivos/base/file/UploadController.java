package cn.tedu.ivos.base.file;

import cn.tedu.ivos.base.response.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 可以复用
 */
@RestController
@RequestMapping("/v1/file")
@Slf4j
public class UploadController {

    @Value("${file-path}")
    private String dirPath;
    //上传---前端把file发到后端---》存到我们的指定路径
    @PostMapping("/upload")
//    二进制 ----》文件
    public JsonResult upload(MultipartFile file) throws IOException {
        //1.得到原始文件名 比如 mwzz.png
        String filename = file.getOriginalFilename();
        //2.获取原始文件的后缀名 比如.png
        String substring = filename.substring(filename.lastIndexOf("."));
        //3.得到唯一的文件名 UUID.randomUUID()得到一个唯一的随机字符串
        //比如:e59a1458-3dd4-415e-83d5-c45403062e87.png
        filename = UUID.randomUUID()+substring;
        //4.指定一个磁盘路径用来保存图片,注意这个路径得自己电脑上有!
        //5.为了检索,我们可以设置一个自定义日期文件夹路径分层存储图片
        //可以以 年/月/日 的方式存,比如: /2024/05/17
        SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/");
        //6.按照当前实际日期生成上述格式的日期路径
        String datePath = sdf.format(new Date());
//        可以创建 文件  也可以创建 文件夹
        //7.创建一个封装了上述文件路径的File对象 D:/files/2024/05/17
        File dirFile = new File(dirPath + datePath);
        //8.判断上述文件夹是否存在,如果不存在则创建
        if (!dirFile.exists()){
            //创建多层文件夹
            dirFile.mkdirs();
        }
        //9.准备一个完整的路径 文件夹路径+日期路径+文件名
//        d:/files/2024/08/16/aaa.png
        String filePath = dirPath+datePath+filename;
        //10.把文件保存在指定的路径下 会有异常抛出!
        file.transferTo(new File(filePath));
        //11.把日期路径 + 图片名 响应给前端,前端需要拿到这个数据回显图片
        return JsonResult.ok(datePath+filename);
    }
    //删除图片
    @PostMapping("/remove")
    public JsonResult remove(String imgUrl){
        log.debug("待删除的图片路径:{}",imgUrl);
        //拼接完整路径,创建对应的Java对象删除磁盘上的图片文件
        new File(dirPath+imgUrl).delete();
        return JsonResult.ok();
    }
}
