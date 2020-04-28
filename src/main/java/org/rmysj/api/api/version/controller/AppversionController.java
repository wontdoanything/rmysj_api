package org.rmysj.api.api.version.controller;

import com.alibaba.fastjson.JSON;
import org.rmysj.api.api.version.domain.DefenderAppversion;
import org.rmysj.api.api.version.domain.DefenderAppversionCriteria;
import org.rmysj.api.api.version.domain.DefenderAppversionResp;
import org.rmysj.api.api.version.service.DefenderAppversionService;
import org.rmysj.api.commons.controller.BaseController;
import org.rmysj.api.commons.util.StringUtils;
import org.rmysj.api.commons.util.VersionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rmysj on 2018/1/18 下午3:07.
 */
@RestController
@RequestMapping(value = "/api/rmysj")
public class AppversionController extends BaseController {

    @Autowired
    private DefenderAppversionService appversionService;

    @Value("${resource.url}")
    private String resourceUrl;

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    @SuppressWarnings("serial")
    @RequestMapping(value = "/check_versionByH5", method = {RequestMethod.POST,RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> checkVersionByH5(String appType, String version, String appid) {
        log.debug("REST request to /api/rmysj/checkVersion  appType:version>>>"+appType+":"+version );
        Map<String,Object> map = new HashMap<String,Object>(){
            {put(STATUS,OK);put(DESC,"success");}
        };
        DefenderAppversionCriteria vcia = new DefenderAppversionCriteria();

        if("2".equals(appType)){
            //增量更新,需比对当前版本有无增量包
            vcia.createCriteria().andAppTypeEqualTo(appType)
                    .andLatestEqualTo("1").andVersionWgtuEqualTo(version);
        }else{
            vcia.createCriteria().andAppTypeEqualTo(appType)
                    .andLatestEqualTo("1");
        }
        List<DefenderAppversion> versions = appversionService.search(vcia);

        if (versions != null && versions.size()>0) {
            map.put("version", new DefenderAppversionResp(versions.get(0),resourceUrl));
        }else{
            map.put(STATUS, WARN);
            map.put(DESC, "空");
        }
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @SuppressWarnings("serial")
    @RequestMapping(value = "/check_version", method = {RequestMethod.POST,RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> checkVersion(@RequestParam(required = true) String appType,
                                          @RequestParam(required = true) String versionNo,
                                          @RequestParam(required = false) String versionCode) {
        log.debug("REST request to /api/rmysj/check_version >>> " +
                "appType:"+appType
                +",versionNo:"+versionNo
                + ",versionCode:" + versionCode);
        Map<String,Object> map = new HashMap<String,Object>(){
            {put(STATUS,OK);put(DESC,"success");}
        };
        if (StringUtils.isNotBlank(appType) && StringUtils.isNotBlank(versionNo)) {

            DefenderAppversionCriteria vcia = new DefenderAppversionCriteria();
            vcia.createCriteria().andAppTypeEqualTo(appType)
                    .andLatestEqualTo("1");
            vcia.setOrderByClause(" create_date desc");
            List<DefenderAppversion> versions = appversionService.search(vcia);
            DefenderAppversionResp appversionResp = new DefenderAppversionResp();
            if (versions != null && versions.size() > 0) {
                DefenderAppversion lastVersion =  versions.get(0);
                appversionResp = new DefenderAppversionResp(versions.get(0),resourceUrl);
                if (VersionUtils.compareVersion(lastVersion.getVersionNo(),versionNo) > 0) {
                    appversionResp.setIsUpgrade("1");
                }else {
                    appversionResp.setIsUpgrade("0");
                }
                map.put("version", appversionResp);
            }else{
                appversionResp.setIsUpgrade("0");
            }
            map.put("version", appversionResp);
        }else {
            map.put(STATUS, WARN);
            map.put(DESC, "内部参数错误");
        }
        log.debug("REST response to /api/rmysj/check_version : " +
                "{ map:"+ JSON.parse(JSON.toJSONString(map))  + " }"  );
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
