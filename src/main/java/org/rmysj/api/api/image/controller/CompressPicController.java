package org.rmysj.api.api.image.controller;

import org.rmysj.api.commons.controller.BaseController;
import org.rmysj.api.commons.util.StringUtils;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.net.URL;

/**
 * Created by rmysj on 2018/4/9 下午4:54.
 */
@RestController
@RequestMapping(value = "/api/image")
public class CompressPicController extends BaseController {

    /**
     *
     * @param url
     * @param width 845
     * @param height 469
     */
    @RequestMapping(value = "/compressPic",method = {RequestMethod.POST,RequestMethod.GET},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void compressPic(String url,Integer width,Integer height,HttpServletResponse response){
        if (StringUtils.isNotBlank(url) && width != null && height != null) {
            response.setHeader("Content-Type","image/jped");
            try {
                byte[] compressBytes = compressPicThumbnails(url,width,height);
                response.getOutputStream().write(compressBytes);
                response.flushBuffer();
            } catch (Exception e)
            {

            }
        }
    }

    /*private byte[] compressPicJavaxs (String url,Integer width,Integer height) throws Exception{
        ByteArrayOutputStream baos = null;
            URL u = new URL(url);
            BufferedImage image = ImageIO.read(u);
            //convert BufferedImage to byte array
            baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", baos);
            baos.flush();
            byte[] urlImgBytes =  baos.toByteArray();
            byte[] compressBytes =  ImgUtil.compressPic(urlImgBytes,width.intValue(),height.intValue(),true);
            return compressBytes;
    }*/

    private byte[] compressPicThumbnails (String url,Integer width,Integer height) throws Exception{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        URL u = new URL(url);
        Thumbnails.of(u).forceSize(width.intValue(),height.intValue()).toOutputStream(out);
        return out.toByteArray();
    }

}
