package cn.sibat.boot.server.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author nanphonfy(南风zsr)
 * @date 2018/1/13
 */
public interface ReportService {
    /**
     * 根据上传文件，解析，入库
     * @param file 前端上传的Excel文件
     * @return
     */
    boolean importExcel(MultipartFile file);

    ResponseEntity<byte[]> downloadTemplateExcel();
}
