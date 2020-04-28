package org.rmysj.api.commons.util;

import com.swetake.util.Qrcode;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

@Configuration
public class QRCodeEncoder{
	public String absolutePath;
	private Logger logger = Logger.getLogger(QRCodeEncoder.class);
	public String getQRcode(HttpServletRequest request,String fileNo) {
		try {
			absolutePath=request.getServletContext().getRealPath("/");logger.debug("absolutePath="+absolutePath);
			String relativepath=MD5andKL.MD5(fileNo) + ".png";
			String filePath = absolutePath + File.separator +relativepath;
			File file = new File(filePath);
            /*if(!file.getParentFile().getParentFile().exists())
                file.getParentFile().getParentFile().mkdirs();
            if(!file.getParentFile().exists())
            	file.getParentFile().mkdirs();*/
            
			if (file.exists()) {
				return relativepath;
			}else
				file.createNewFile();
			
			Qrcode qrcode = new Qrcode();  
	        qrcode.setQrcodeErrorCorrect('M');  
	        qrcode.setQrcodeEncodeMode('B');  
	        qrcode.setQrcodeVersion(9);  
	        byte[] bstr = fileNo.getBytes("UTF-8");  
	        BufferedImage bi = new BufferedImage(160, 160,BufferedImage.TYPE_BYTE_BINARY);  
	        Graphics2D g = bi.createGraphics();  
	        g.setBackground(Color.WHITE);   //背景颜色  
	        g.clearRect(0, 0, 160, 160);  
	        g.setColor(Color.BLACK);    //条码颜色  
	        if (bstr.length > 0 && bstr.length < 300) {  
	            boolean[][] b = qrcode.calQrcode(bstr);  
	            for (int i = 0; i < b.length; i++) {  
	                for (int j = 0; j < b.length; j++) {  
	                    if (b[j][i]) {  
	                        g.fillRect(j * 3 + 2, i * 3 + 2, 3, 3);  
	                    }  
	                }  
	            }  
	        }  
			g.dispose();
			bi.flush();
			ImageIO.write(bi, "png", file);
			return relativepath;
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
		}
		return null;
	}
}
