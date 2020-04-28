package org.rmysj.api.api.file.service.impl;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.rmysj.api.api.file.dao.FileInfoMapper;
import org.rmysj.api.api.file.domain.FileForm;
import org.rmysj.api.api.file.domain.FileInfo;
import org.rmysj.api.api.file.domain.FileInfoCriteria;
import org.rmysj.api.api.file.domain.FileWrapper;
import org.rmysj.api.api.file.service.FileInfoService;
import org.rmysj.api.commons.controller.BaseController;
import org.rmysj.api.commons.dao.BaseDao;
import org.rmysj.api.commons.service.BaseServiceImpl;
import org.rmysj.api.commons.util.IdGen;
import org.rmysj.api.commons.util.NameUtil;
import org.rmysj.api.commons.util.StringUtils;
import org.rmysj.api.config.Glob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

@Service
@Transactional(readOnly = true)
public class FileInfoServiceImpl extends BaseServiceImpl<FileInfo, FileInfoCriteria> implements FileInfoService {

    private static long _OFFSET = 5*1024*1024;

    @Autowired
    private FileInfoMapper fileInfoDao;

    @Override
    protected BaseDao<FileInfo, FileInfoCriteria, String> getDao() {
        return fileInfoDao;
    }

    @Override
    public FileInfo getEntityByMd5(String md5) {
        FileInfoCriteria fileInfoCriteria = new FileInfoCriteria();
        fileInfoCriteria.or().andMd5EqualTo(md5).andDelFlagEqualTo("0");
        List<FileInfo> list =  fileInfoDao.selectByExample(fileInfoCriteria);
        if (list.size()  == 1) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public Map<String, Object> findByFileMd5(String md5) {
        FileInfo uploadFile = getEntityByMd5(md5);
        String dateStr = DateFormatUtils.format(new Date(),"yyyyMMdd");
        Map<String, Object> map = new HashMap<String, Object>() {
            {put(BaseController.STATUS, BaseController.OK);put(BaseController.DESC, BaseController.SUCCESS);}
        };
        if (uploadFile == null) {
            //没有上传过文件
            map.put("flag", 0);
            map.put("fileId", IdGen.uuid());
            map.put("date", dateStr);
            map.put("offset",0);
        } else {
            //上传过文件，判断文件现在还存在不存在
            File file = new File(Glob.getConfig("upload.baseDir") + uploadFile.getPath());

            if (file.exists()) {
                if ("1".equals(uploadFile.getStatus()) ) {
                    //文件只上传了一部分
                    map.put("flag", 1);
                    map.put("fileId", uploadFile.getId());
                    map.put("date", dateStr);
                    map.put("offset",uploadFile.getOffset());
                } else if ("2".equals(uploadFile.getStatus())) {
                    //文件早已上传完整
                    map.put("flag" , 2);
                    map.put("offset",uploadFile.getOffset());
                    map.put("fileId", uploadFile.getId());
                    map.put("date", dateStr);
                }
            } else {
                    map.put("flag", 0);
                    map.put("fileId", uploadFile.getId());
                    map.put("date", dateStr);
                    map.put("offset",0);
            }
        }
        return map;
    }

    @Override
    @Transactional(readOnly = false)
    public Map<String, Object> realUpload(FileForm form, MultipartFile multipartFile) throws Exception{
        String fileId = form.getUuid();
        String partMd5 = form.getPartMd5();
        String md5 = form.getMd5();
        long offset = form.getOffset();
        String fileName = form.getName();
        //文件分块大小
        String size = form.getSize();
        //文件总大小
        long totalSize = form.getTotalSize();
        String userId = form.getUserId();
        String type = form.getType();
        String suffix = NameUtil.getExtensionName(fileName);
        if(fileName.equalsIgnoreCase(suffix)) {
            suffix = form.getSuffix();
        }
        String saveDirectory = Glob.getConfig("upload.baseDir") + File.separator + fileId;
        String dbFilePath = StringUtils.isNotBlank(suffix) && StringUtils.isNotEmpty(suffix)?(File.separator + fileId + "." + suffix):(File.separator + fileId);
        String filePath = saveDirectory + dbFilePath;
        //验证路径是否存在，不存在则创建目录
        File path = new File(saveDirectory);
        if (!path.exists()) {
            path.mkdirs();
        }
        //文件分片位置
        File file = new File(saveDirectory, fileId + "_" + offset);

        //根据action不同执行不同操作. check:校验分片是否上传过; upload:直接上传分片
        Map<String, Object> map = new HashMap<String, Object>() {
            {put(BaseController.STATUS, BaseController.OK);put(BaseController.DESC, BaseController.SUCCESS);}
        };
            //分片上传过程中出错,有残余时需删除分块后,重新上传
            if (file.exists()) {
                file.delete();
            }
            File toTfile = new File(saveDirectory, fileId + "_" + offset);
//            if(!FileMd5Util.getFileMD5(multipartFileToFile(multipartFile)).equals(partMd5)) {
//                map.put("fileId", fileId);
//                map.put("flag", "2");
//                map.put("offset",offset);
//                return map;
//            }
            multipartFile.transferTo(toTfile);
            if(offset == 0l) {
                //文件第一个分片上传时记录到数据库
                FileInfo uploadFile = new FileInfo();
                uploadFile.setMd5(md5);
                String name = NameUtil.getFileNameNoEx(fileName);
                if (name.length() > 32) {
                    name = name.substring(0, 32);
                }
                uploadFile.setName(name);
                uploadFile.setSuffix(suffix);
                uploadFile.setId(fileId);
                uploadFile.setUrl(dbFilePath);
                uploadFile.setPath(filePath);
                uploadFile.setSize(Long.valueOf(totalSize));
                uploadFile.setStatus("1");
                offset += Long.valueOf(size);
                uploadFile.setOffset(offset);
                uploadFile.setUserId(userId);
                uploadFile.setType(type);
                uploadFile.setCreateDate(new Date());
                uploadFile.setUpdateDate(uploadFile.getCreateDate());
                uploadFile.setDelFlag("0");
                FileInfo orgFile = fileInfoDao.selectByPrimaryKey(fileId);
                if(orgFile == null) {
                    fileInfoDao.insertSelective(uploadFile);
                }else {
                    fileInfoDao.updateByPrimaryKey(uploadFile);
                }
            }else {
                FileInfo orgFile = fileInfoDao.selectByPrimaryKey(fileId);
                if(orgFile != null) {
                    offset += Long.valueOf(size);
                    orgFile.setOffset(offset);
                    fileInfoDao.updateByPrimaryKey(orgFile);
                }
            }
            map.put("flag", "1");
            map.put("fileId", fileId);
            map.put("offset", offset);
            if(offset != totalSize) {
                return map;
            }
        if (path.isDirectory()) {
            File[] fileArray = path.listFiles();
            if (fileArray != null) {
                int offsetleng= Integer.parseInt(size);
                FileWrapper[] fileWrappers = new FileWrapper[fileArray.length];
                for (int i=0; i<fileArray.length; i++) {
                    fileWrappers[i] = new FileWrapper(fileArray[i]);
                }
                Arrays.sort(fileWrappers);
                File [] sortedFiles = new File[fileArray.length];
                for (int i=0; i<fileArray.length; i++) {
                    sortedFiles[i] = fileWrappers[i].getFile();
                }
                if(offset >= totalSize) {
                    //分块全部上传完毕,合并
                    String finalFileName = StringUtils.isNotBlank(suffix) && StringUtils.isNotEmpty(suffix)?fileId + "." + suffix:fileId;
                    File newFile = new File(saveDirectory, finalFileName);
//                    FileOutputStream outputStream = new FileOutputStream(newFile, true);//文件追加写入
//                    byte[] byt = new byte[10 * 1024 * 1024];
//                    int len;
//                    log.debug("合成文件开始时间" + new Date());
//                    for (int i = 0; i < sortedFiles.length; i++) {
//                        FileInputStream temp  = new FileInputStream(new File(saveDirectory, sortedFiles[i].getName()));
//                        while ((len = temp.read(byt)) != -1) {
//                            outputStream.write(byt, 0, len);
//                        }
//                        temp.close();
//                    }
//                    //关闭流
//                    outputStream.close();
//                    outputStream.flush();
                    //用于写文件
                    RandomAccessFile raf_write = new RandomAccessFile(newFile, "rw");
                    //指针指向文件顶端
                    raf_write.seek(0);
                    //缓冲区
                    byte[] b = new byte[1024];
                    //分块列表
                    for (int i = 0; i < sortedFiles.length; i++) {
                        File chunkFile = new File(saveDirectory, sortedFiles[i].getName());
                        RandomAccessFile raf_read = new RandomAccessFile(chunkFile, "rw");
                        int len = -1;
                        while ((len = raf_read.read(b)) != -1) {
                            raf_write.write(b, 0, len);
                        }
                        raf_read.close();
                    }
                    raf_write.close();
                    log.debug("结束文件开始时间" + new Date());
                    //有几率检测出md5不一样，而且校验时间较长，建议改为校验文件大小
                    if(newFile.length() == totalSize) {
//                    if(FileMd5Util.getFileMD5(newFile).equals(md5)) {
                        //修改FileRes记录为上传成功
                        FileInfo uploadFile = new FileInfo();
                        uploadFile.setId(fileId);
                        uploadFile.setStatus("2");
                        uploadFile.setName(fileName);
                        uploadFile.setMd5(md5);
                        uploadFile.setSuffix(suffix);
                        dbFilePath = "/" + uploadFile.getId() + "/" + uploadFile.getId() + (StringUtils.isNotBlank(uploadFile.getSuffix())?("." + uploadFile.getSuffix()):"");
                        uploadFile.setUrl(dbFilePath);
                        uploadFile.setPath(filePath);
                        uploadFile.setSize(totalSize);
                        uploadFile.setOffset(Long.valueOf(totalSize));
                        uploadFile.setType(type);
                        uploadFile.setUserId(userId);
                        this.save(uploadFile);
                        map.put("fileId", fileId);
                        map.put("flag", "2");
                        map.put("offset",totalSize);
                        map.put("url",Glob.getConfig("upload.baseUrl") + dbFilePath);
                        for (int i = 0; i < sortedFiles.length; i++) {
                            File tmpFile = new File(saveDirectory, sortedFiles[i].getName());
                            tmpFile.delete();
                        }
                     }else {
                        log.debug("MD5校验不通过，删除合并后文件");
                        newFile.delete();
                        map.put("fileId", fileId);
                        map.put("flag", "1");
                        map.put("offset",0);
                    }
                    return map;
                }
            }
        }
        return map;
    }

    public static File multipartFileToFile(MultipartFile file) throws Exception {

        File toFile = null;
        if (file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File(file.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }

    //获取流文件
    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
