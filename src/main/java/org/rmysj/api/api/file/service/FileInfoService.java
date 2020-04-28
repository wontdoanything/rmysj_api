package org.rmysj.api.api.file.service;

import org.rmysj.api.api.file.domain.FileForm;
import org.rmysj.api.api.file.domain.FileInfo;
import org.rmysj.api.api.file.domain.FileInfoCriteria;
import org.rmysj.api.commons.service.BaseService;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface FileInfoService extends BaseService<FileInfo, FileInfoCriteria> {

    FileInfo getEntityByMd5(String md5);

    Map<String, Object> findByFileMd5(String md5);

    Map<String, Object> realUpload(FileForm form, MultipartFile multipartFile) throws Exception;
}
