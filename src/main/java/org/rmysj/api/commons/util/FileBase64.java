package org.rmysj.api.commons.util;

import org.apache.commons.codec.binary.Base64;

import java.io.*;

/**
 * Created by rmysj on 2017/9/7 上午11:15.
 */
public class FileBase64 {
    /**
     * 文件转base64字符串
     * @param file
     * @return
     */
    public static String fileToBase64(File file) {
        String base64 = null;
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            byte[] bytes = new byte[in.available()];
            int length = in.read(bytes);
            base64 = Base64.encodeBase64String(bytes);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return base64;
    }

    /**
     * base64字符串转文件
     * @param base64
     * @return
     */
    public static File base64ToFile(String fileName, String base64) {
        File file = null;
        FileOutputStream out = null;
        try {
            // 解码，然后将字节转换为文件
            file = new File(fileName);
            if (!file.exists())
                file.createNewFile();
            byte[] bytes = Base64.decodeBase64(base64);// 将字符串转换为byte数组
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            byte[] buffer = new byte[1024];
            out = new FileOutputStream(file);
            int bytesum = 0;
            int byteread = 0;
            while ((byteread = in.read(buffer)) != -1) {
                bytesum += byteread;
                out.write(buffer, 0, byteread); // 文件写操作
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (out!= null) {
                    out.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return file;
    }

    public static void main(String[] args) {
        String filename = "/Users/rmysj/ideaProjects.rmysj.api/target/test.arm";
        String armCodes = "speex-wb;7";
        String base64 = "M4zRC5HId9MCaW9rjUfvrA9EF9cSxLa2M5bDw2/b7l4S3ibViNLQmmWpaKSlurqxkFgCW1AiXgREi /uDOdQK5Ry9N3sPqfH8hnj0lbqY37Bvf7BL9hrc9/EJkuDQohzi/W3ESP3cl97GqT8irsQSAcEEnrEYI+0 zmKrjsTAfYytXuVFvkG1Jm6t1WXuD5RdW2FWxTKpxEet3ppce3LdyhQVHQlFqk/Arl9CBVJLCQVJOFt M4zO7xEcbtj1xVI1bdFNdw+8diebi3TBRY+ft0D7OLChs9tzrmcMpX40WNuR6rrAzTdBy2qQSktAgm ODOPLs5xvD8bvDCcJVL5vz4FmzLyQw9JdlDCj8M9MYeRDQK6Hu/fdZqDQZMALuqT8DKdUBAxkO0 VEAYdRw==";
        base64ToFile(filename,base64);
    }
}
