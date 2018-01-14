package cn.sibat.boot.server.controller;

import cn.sibat.boot.server.service.ReportService;
import cn.sibat.boot.server.util.FastJsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 该类用于模拟下载模板报表、上传模板报表并做入库
 *
 * @author nanphonfy(南风zsr)
 * @date 2018/1/13
 */
@RestController
@RequestMapping("/excels")
public class ReportController extends AbstractController {
    @Autowired
    private ReportService reportService;

    /**
     * https://www.cnblogs.com/shimh/p/6094410.html
     * localhost:8997/ebase-server/excels/upload
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public String importExcel(@RequestParam(value="filename") MultipartFile file) {
        //上传、解析、导入
        boolean flag=reportService.importExcel(file);
        if(flag==true){
            return FastJsonUtil.ok();
        }
        return FastJsonUtil.error("录入失败");
    }

    /**
     * localhost:8997/ebase-server/excels/download
     */
    @RequestMapping(value = "download")
    public ResponseEntity<byte[]> downloadTemplateExcel() {
        return reportService.downloadTemplateExcel();
    }
}
