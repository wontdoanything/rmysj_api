package test;

import com.google.common.collect.Maps;
import org.rmysj.api.commons.util.constant.HttpCodeEnum;
import org.junit.Test;
import sun.misc.BASE64Decoder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * Created by rmysj on 2017/7/4 上午9:52.
 */
public class Base64Test {


    @Test
    public void  base64Decoder(){
        String str = "M4zRC5HId9MCaW9rjUfvrA9EF9cSxLa2M5bDw2/b7l4S3ibViNLQmmWpaKSlurqxkFgCW1AiXgREi /uDOdQK5Ry9N3sPqfH8hnj0lbqY37Bvf7BL9hrc9/EJkuDQohzi/W3ESP3cl97GqT8irsQSAcEEnrEYI+0 zmKrjsTAfYytXuVFvkG1Jm6t1WXuD5RdW2FWxTKpxEet3ppce3LdyhQVHQlFqk/Arl9CBVJLCQVJOFt M4zO7xEcbtj1xVI1bdFNdw+8diebi3TBRY+ft0D7OLChs9tzrmcMpX40WNuR6rrAzTdBy2qQSktAgm ODOPLs5xvD8bvDCcJVL5vz4FmzLyQw9JdlDCj8M9MYeRDQK6Hu/fdZqDQZMALuqT8DKdUBAxkO0 VEAYdRw==";
        BASE64Decoder decode=new BASE64Decoder();
        String result="";

        byte[] b;
        try {
            b = decode.decodeBuffer(str);
            result=new String(b,"utf-8");
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 将base64字符解码保存文件
     * @param base64Code
     * @param targetPath
     * @throws Exception
     */
    @Test
    public void decoderBase64File()
            throws Exception {
        String base64Code = "M5SiXu9M9p+addKuOCQ3vje2NDimtUQblW/DsH4enmQnsyhRM3cLCDCGJLCDWpHw8MrAhkRgShBQtZYTLTd2dPefNaFngJJwXfHhejrrQrPOIMKmQFcMeNsIjfZ8FTwVDRvmXZzfHRHVr5UEFzEKpyIMZusTdREywkEObz03FtDjefHjz9wSw5Fxvb3MormZqL3ToUhiJ6uo3GNN3FFGmKvKCa0ak/Br7TKQgwBEK4GRGsNcaB0y9W9uXyukgfc2CgaFu1hf1ntE0dGkSH1l6i02iu9k/sQ+39gbipTYxh+rrCovoyBXdBOqoiYnxzXWbThP2/NdcrgvUDsd6/mbjAJDnaDt5cKiPdBqVpRCDVW68g/m6tlJE7jpl8r6UXoMgm4rAghdglfM1w==";
        byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
        String targetPath = "/Users/rmysj/Downloads/aa";
        FileOutputStream out = new FileOutputStream(targetPath);
        out.write(buffer);
        out.close();

    }

    public static void main(String[] args) {
        Map<String,Object> map = Maps.newHashMap();
        map.put("1", HttpCodeEnum.DISPER_UN_ONLINE.getCode());
        map.put("2", HttpCodeEnum.DISPER_UN_ONLINE.getDesc());
        System.out.println(map);
    }
}
