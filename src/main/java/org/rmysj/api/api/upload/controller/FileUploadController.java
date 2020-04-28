package org.rmysj.api.api.upload.controller;

import com.alibaba.fastjson.JSONObject;
import org.rmysj.api.api.file.domain.FileInfo;
import org.rmysj.api.api.file.service.FileInfoService;
import org.rmysj.api.commons.controller.BaseController;
import org.rmysj.api.commons.util.IdGen;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/upload")
@Configuration
public class FileUploadController extends BaseController implements EnvironmentAware{
	
	
    private String baseDir;
    private String baseUrl;

    @Autowired
	private FileInfoService fileInfoService;
    
    
    @Override
    public void setEnvironment(Environment environment) {
    	baseDir = environment.getProperty("upload.baseDir");
    	baseUrl = environment.getProperty("upload.baseUrl");
        //System.out.println(baseDir);
    }
	
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public void provideUploadInfo(
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "md5", required = true) String md5,
			@RequestParam(value = "type", required = true) int type,
			@RequestParam("file") MultipartFile file,HttpServletResponse response,
			HttpServletRequest request,
			@RequestParam(value = "userCode", required = true) String userCode,
			@RequestParam(value = "token", required = true) String token
			) {
		 handleFileUpload(name,md5,type,file,response,request,userCode,token,null);
		//return "You can upload a file by posting to this same URL.";
	}

	/**
	 * 文件上传功能
	 * 改造如下：1.获取文件大小和MD5，比对上传的MD5
	 * 2.落库，如果库中有此MD5，直接返回给前台，无需在上传
	 * @param name
	 * @param file
	 * @param md5
	 * @param response
	 */
	@RequestMapping(value = "/cmsPost", method = {RequestMethod.POST,RequestMethod.GET})
	public void cmsPost(
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "md5", required = true) String md5,
			@RequestParam(value = "type", required = false, defaultValue = "9") int type,
			@RequestParam("file") MultipartFile file,HttpServletResponse response,
			HttpServletRequest request,
			@RequestParam(value = "fromId", required = false) String fromId
	) {
		response.setHeader("Access-Control-Allow-Origin","*");
		JSONObject json = new JSONObject();
		FileInfo resultFile = fileInfoService.getEntityByMd5(md5);
		if (resultFile != null) {
			json.put("fileId", resultFile.getId());
			json.put(STATUS, OK);
			json.put("baseUrl", baseUrl);
			json.put("filename", resultFile.getUrl());
		}else {
			if (!file.isEmpty()) {
				try {
					FileInfo fileInfo = new FileInfo();
					byte[] bytes = file.getBytes();
					String afterfix = "";
					if (type == 1) {
						if (name.contains(".")) {
							String[] fixs = name.split("\\.");
							afterfix = fixs[fixs.length - 1];
						}
					}else if (type == 2) {
						afterfix = "png";
					}else if (type == 3) {
						afterfix = "mp4";
					}else {
						if (name.contains(".")) {
							String[] fixs = name.split("\\.");
							afterfix = fixs[fixs.length - 1];
						}
					}
					String beforefix = IdGen.uuid();
					if (StringUtils.isBlank(name)) {
						name = IdGen.uuid();
						if(type != 9) {
							name += "." + afterfix;
						}else {
							name += "." + afterfix;
						}
					}else {
						name = file.getName();
					}
					fileInfo.setUserId(fromId);
					fileInfo.setName(name);
					fileInfo.setSize(file.getSize());
					String filename = beforefix + "." + afterfix;
					String fileFullName = baseDir + File.separator + filename;
					fileInfo.setPath(fileFullName);
					fileInfo.setType(String.valueOf(type));
					fileInfo.setUrl(filename);
					fileInfo.setMd5(md5);
					File uploadDesFile = new File(fileFullName);
					BufferedOutputStream stream = new BufferedOutputStream(
							new FileOutputStream(new File(fileFullName)));
					stream.write(bytes);
					stream.close();
					resultFile = fileInfoService.save(fileInfo);
					json.put("fileId",resultFile.getId());

//				BufferedImage bufferedImage = ImageIO.read(uploadDesFile);
//				int width = bufferedImage.getWidth();
//				int height = bufferedImage.getHeight();
//				if (width != 260 || height != 170) {
//					ImgCompress imgCom = new ImgCompress(fileFullName);
//					fileFullName = imgCom.resizeFix(260, 170);
//					if (fileFullName.contains(baseDir)) {
//						filename = fileFullName.substring(baseDir.length());
//					}
//				}
					json.put(STATUS, OK);
					json.put("baseUrl", baseUrl);
					json.put("filename", filename);
					json.put("fileUrl",baseUrl + "/" + filename);
				} catch (Exception e) {
					json.put(STATUS, WARN);
					json.put(DESC, e.getMessage());
				}
			} else {
				json.put(STATUS, WARN);
				json.put("desc", "file is empty");
			}
		}
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		//这句话的意思，是告诉servlet用UTF-8转码，而不是用默认的ISO8859
		response.setCharacterEncoding("UTF-8");
		String data = "中国";
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pw.write(json.toJSONString());
	}

	/**
	 * 文件上传功能
	 * 改造如下：1.获取文件大小和MD5，比对上传的MD5
	 * 2.落库，如果库中有此MD5，直接返回给前台，无需在上传
	 * @param name
	 * @param file
	 * @param md5
	 * @param response
	 */
	@RequestMapping(value = "/post", method = RequestMethod.POST)
	public void handleFileUpload(
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "md5", required = true) String md5,
			@RequestParam(value = "type", required = false, defaultValue = "9") int type,
			@RequestParam("file") MultipartFile file,HttpServletResponse response,
			HttpServletRequest request,
			@RequestParam(value = "userCode", required = true) String userCode,
			@RequestParam(value = "token", required = true) String token,
			@RequestParam(value = "fromId", required = false) String fromId
			) {
		Map<String, Object> map = new HashMap<String, Object>() {
			{put(STATUS, OK);put(DESC, SUCCESS);}
		};
		refreshTokenOper(map,request,response);
		String from_user_id = getUserKeysByToken(request).getId();
		cmsPost(name,md5,type,file,response,request,from_user_id);
	}

	public static void main(String[] args){
		String name = "xxxx.mp3.amr";
		String afterfix = "";
		if (name.contains(".")) {
			String[] fixs = name.split("\\.");
			afterfix = fixs[fixs.length - 1];
		}
		System.out.println(afterfix);
	}

}
