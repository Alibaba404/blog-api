package cn.aikuiba.controller;

import cn.aikuiba.resp.R;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * MinIO文件服务器
 * Created by 蛮小满Sama at 2023/11/21 15:50
 *
 * @description
 */
@RestController
@RequestMapping("/minio")
public class MinIoController {

    private static final String ACCESSKEY = "admin";
    private static final String SECREKEY = "1Tsource2021.cn";
    private static final String ADDRESS = "http://minio.java.itsource.cn";
    private static final String BUCKET = "java0820";

    //上传文件
    @PostMapping
    public R<String> upload(@RequestPart("file") MultipartFile file) {
        try {
            // 构建一个Minio客户端
            MinioClient minioClient = MinioClient.builder()
                    //创建容器时指定的账号
                    .credentials(MinIoController.ACCESSKEY, MinIoController.SECREKEY)
                    //上传地址
                    .endpoint(MinIoController.ADDRESS).build();

            //处理文件名
            String oName = file.getOriginalFilename();
            String fileName = System.currentTimeMillis() + oName.substring(oName.lastIndexOf("."));
            PutObjectArgs putObjectArgs = PutObjectArgs.builder().object(fileName)    //文件名
                    .bucket(MinIoController.BUCKET)  //存储目录名
                    .stream(file.getInputStream(), file.getSize(), -1).build(); //文件流，以及大小，-1代表不分片
            //执行上传
            minioClient.putObject(putObjectArgs);
            return R.success(fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(2002, "上传失败");
        }
    }

    //删除文件
    @DeleteMapping("/{name}")
    public R<String> delete(@PathVariable("name") String name) {
        try {
            // 构建一个Minio客户端
            MinioClient minioClient = MinioClient.builder()
                    //创建容器时指定的账号
                    .credentials(MinIoController.ACCESSKEY, MinIoController.SECREKEY)
                    //上传地址
                    .endpoint(MinIoController.ADDRESS).build();

            minioClient.removeObject(MinIoController.BUCKET, name);
            return R.success();
        } catch (Exception e) {
            return R.failure(2003, "删除失败");
        }
    }
}
