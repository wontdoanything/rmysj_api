package minio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rmysj.api.Application;
import org.rmysj.api.commons.util.FileUtil;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.FileCopyUtils;

/**
 * FileUploader
 *
 * @author bxgms
 * @email fyc8729@163.com
 * @date 2020/9/22
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class FileUploader {

    @Test
    public void testPut() throws NoSuchAlgorithmException, IOException, InvalidKeyException{
        try {
            // 使用MinIO服务的URL，端口，Access key和Secret key创建一个MinioClient对象
            MinioClient minioClient = new MinioClient("http://127.0.0.1:10000", "AKIAIOSFODNN7EXAMPLE", "wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY");
            String buckettName = "test";
            String objectFileName = "BVoAAIhQb.jpg";
            String localFilePath = "/Users/fangyechao/Downloads/BVoAAIhQb.jpg";
            String contentType = "image/jpeg";
            File file = new File(localFilePath);
            // 检查存储桶是否已经存在
            boolean isExist = minioClient.bucketExists(buckettName);
            if(isExist) {
                System.out.println("Bucket already exists.");
            } else {
                // 创建一个名为asiatrip的存储桶，用于存储照片的zip文件。
                minioClient.makeBucket(buckettName);
            }

            // 使用putObject上传一个文件到存储桶中。
            PutObjectArgs putObjectArgs =  PutObjectArgs.builder().bucket(buckettName).object(objectFileName).stream(
                    new FileInputStream(file), file.length(), -1)
                    .contentType(contentType)
                    .build();

            minioClient.putObject(putObjectArgs);
            System.out.println(localFilePath + " is successfully uploaded as " + objectFileName + " to `" + buckettName + "` bucket.");

            //
        } catch(MinioException e) {
            System.out.println("Error occurred: " + e);
        }
    }

    @Test
    public void testGet() throws NoSuchAlgorithmException, IOException, InvalidKeyException{
        try {
            // 使用MinIO服务的URL，端口，Access key和Secret key创建一个MinioClient对象
            MinioClient minioClient = new MinioClient("http://127.0.0.1:10000", "AKIAIOSFODNN7EXAMPLE", "wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY");
            String buckettName = "test";
            String objectFileName = "BVoAAIhQb.jpg";
            String localFilePath = "/Users/fangyechao/Downloads/BVoAAIhQb_download.jpg";
            String contentType = "image/jpeg";
            File file = new File(localFilePath);
            // 检查存储桶是否已经存在
            boolean isExist = minioClient.bucketExists(buckettName);
            if(isExist) {
                System.out.println("Bucket already exists.");
            }

            InputStream stream =
                    minioClient.getObject(
                            GetObjectArgs.builder().bucket(buckettName).object(objectFileName).build());
            FileCopyUtils.copy(FileCopyUtils.copyToByteArray(stream),new File(localFilePath));


//            byte[] buf = new byte[16384];
//            int bytesRead;
//            while ((bytesRead = stream.read(buf, 0, buf.length)) >= 0) {
//                System.out.println(new String(buf, 0, bytesRead, StandardCharsets.UTF_8));
//            }
            // Close the input stream.
//            stream.close();


            System.out.println(objectFileName + " from `" + buckettName + "` bucket is successfully download as " + localFilePath + ".");

            //
        } catch(MinioException e) {
            System.out.println("Error occurred: " + e);
        }
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, InvalidKeyException {
//        testGet();
    }
}
