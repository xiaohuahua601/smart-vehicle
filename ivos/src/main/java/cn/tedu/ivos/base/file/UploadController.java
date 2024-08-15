package cn.tedu.ivos.base.file;

import cn.tedu.ivos.base.response.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    public JsonResult upload(MultipartFile file){
        //1.得到原始文件名 比如 mwzz.png
        String filename = file.getOriginalFilename();
        //2.获取原始文件的后缀名 比如.png
        String substring = filename.substring(filename.lastIndexOf("."));
        //3.得到唯一的文件名 UUID.randomUUID()得到一个唯一的随机字符串
        //比如:e59a1458-3dd4-415e-83d5-c45403062e87.png
        filename = UUID.randomUUID()+substring;
        //todo  存图片
        return JsonResult.ok();
    }
}
