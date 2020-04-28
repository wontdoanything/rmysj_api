package org.rmysj.api.api.upload.controller;

import org.rmysj.api.api.file.domain.FileForm;
import org.rmysj.api.api.file.service.FileInfoService;
import org.rmysj.api.commons.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/bigFile")
public class BigFileUploadController extends BaseController implements EnvironmentAware {

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

    /**
     * /api/bigFile/requestUpload
     * post 参数md5，校验md5文件是否存在
     * 返回 {
     *     fileId 文件ID
     *     flag 0 没上传 1 部分上传 2完整上传
     *     offset long
     *     date 日期时间戳
     * }
     * @param form
     * @return
     */
    @PostMapping("/requestUpload")
    public Map<String, Object> isUpload(@Valid FileForm form,
                                        @RequestParam(value = "userCode", required = true) String userCode,
                                        @RequestParam(value = "token", required = true) String token,
                                        HttpServletResponse response,
                                        HttpServletRequest request
                                        ) {
        Map<String, Object> map = new HashMap<String, Object>() {
            {put(STATUS, OK);put(DESC, SUCCESS);}
        };
        return fileInfoService.findByFileMd5(form.getMd5());

    }

    /**
     * /api/bigFile/upload
     * post 上传
     * {
     *     md5
     *     uuid 上个接口返回的文件ID
     *     date 上个接口返回的
     *     name 文件名称
     *     size 文件大小
     *     data 对应块文件
     *     offset 偏移量
     *     userId
     *     type
     * }
     * 返回 {
     *     fileId 文件ID
     *     flag 0 没上传 1 部分上传 2完整上传
     *          offset 偏移量
     *
     * }
     * @param form
     * @return
     */
    @PostMapping("/upload")
    public Map<String, Object> upload(@Valid FileForm form,
                                      @RequestParam(value = "userCode", required = true) String userCode,
                                      @RequestParam(value = "token", required = true) String token,
                                      @RequestParam(value = "data", required = false) MultipartFile multipartFile) {
        Map<String, Object> map = new HashMap<String, Object>() {
            {put(STATUS, OK);put(DESC, SUCCESS);}
        };
        try {
            map = fileInfoService.realUpload(form, multipartFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
