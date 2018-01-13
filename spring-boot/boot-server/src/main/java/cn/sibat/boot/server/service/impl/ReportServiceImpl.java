package cn.sibat.boot.server.service.impl;

import cn.sibat.boot.server.dao.CreditorDao;
import cn.sibat.boot.server.entity.Creditor;
import cn.sibat.boot.server.exception.ExcelDataFormatException;
import cn.sibat.boot.server.exception.RRException;
import cn.sibat.boot.server.service.ReportService;
import cn.sibat.boot.server.util.ConfigurableConstants;
import cn.sibat.boot.server.util.excel.ClaimsType;
import cn.sibat.boot.server.util.excel.ConstantUtil;
import cn.sibat.boot.server.util.excel.util.DataFormatUtilInterface;
import cn.sibat.boot.server.util.excel.util.DataFormatUtilInterfaceImpl;
import cn.sibat.boot.server.util.excel.util.ExceiUtils;
import cn.sibat.boot.server.util.excel.util.ExcelRowResultHandler;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static cn.sibat.boot.server.util.excel.ConstantUtil.REGULAR_EXCEL_SEPARATOR;

/**
 * @author nanphonfy(南风zsr)
 * @date 2018/1/13
 */
@Service
public class ReportServiceImpl extends AbstractService implements ReportService{

    @Autowired
    private CreditorDao creditorDao;

    private DataFormatUtilInterface formatUtil= new DataFormatUtilInterfaceImpl();

    @Override
    public boolean importExcel(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RRException("上传文件不能为空");
        }

        String uploadPath = ConfigurableConstants.getProperty(ConfigurableConstants.EXCEL_TMP_PATH);
        //存放Excel的目录
        File parentFile = new File(uploadPath);
        String filename = System.currentTimeMillis() + file.getOriginalFilename();
        //parent pathname,child pathname string，打开新文件
        File newFlie = new File(parentFile, filename);

        try {
            // 0.common-io下的工具类:根据上传文件，实例化新的文件(file是字节文件，需转换)
            FileUtils.copyInputStreamToFile(file.getInputStream(), newFlie);
            // 1.获取文件excel，将内容读出，封装成List<Creditor>,调用ExcelUtils工具
            ExceiUtils<Creditor> eu = new ExceiUtils<Creditor>();
            try {
                List<Creditor> list = eu.getEntity(newFlie, new ExcelRowResultHandler<Creditor>() {
                    @Override
                    public Creditor invoke(List<Object> data) {
                        // 将每一行封装成一个CreditorModel对象.
                        Creditor creditor = new Creditor();
                        if (data.get(0) != null) {
                            // 债权合同编号
                            creditor.setContractNo(data.get(0).toString());
                        } else {
                            throw new ExcelDataFormatException("{" + 0 + "}");
                        }
                        // 债务人名称
                        creditor.setDebtorsName(data.get(1).toString().replaceAll(REGULAR_EXCEL_SEPARATOR, " "));
                        // 身份证号
                        if (data.get(2) != null) {
                            String data2 = data.get(2).toString().replaceAll(REGULAR_EXCEL_SEPARATOR, " ");
                            String[] art = data2.split(" ");
                            for (int i = 0; i < art.length; i++) {
                                String str = art[i];
                                /*if (!RegValidationUtil.validateIdCard(str)) {
                                    throw new ExcelDataFormatException("{" + 2 + "}");
                                }*/
                            }
                            creditor.setDebtorsId(data2);// 债务人身份证编号
                        } else {
                            throw new ExcelDataFormatException("{" + 2 + "}");
                        }
                        creditor.setLoanPurpose(data.get(3).toString()); // 借款用途
                        creditor.setLoanType(data.get(4).toString());// 借款类型

                        ////////////////////////////////////////////////////////////////////////////////////////////
                        //这边会有异常
                        //Excel导入的时候日期格式会变成double式的String数据处理
                        Date date6 = HSSFDateUtil.getJavaDate(Double.parseDouble(String.valueOf(data.get(6))));
                        Date date7 = HSSFDateUtil.getJavaDate(Double.parseDouble(String.valueOf(data.get(7))));
                        Date date15 = HSSFDateUtil.getJavaDate(Double.parseDouble(String.valueOf(data.get(15))));
                        ////////////////////////////////////////////////////////////////////////////////////////////

                        creditor.setLoanPeriod(formatUtil.formatToInt(data.get(5), 5)); // 原始期限月
                        creditor.setLoanStartDate(formatUtil.format(date6.getTime(), 6));// 原始借款开始日期
                        creditor.setLoanEndDate(formatUtil.format(date7.getTime(), 7));// 原始贷款到期日期
                        // 还款方式
                        if (ConstantUtil.EqualInstallmentsOfPrincipalAndInterest.equals(data.get(8))) {// 等额本息
                            creditor.setRepaymentStyle(11601);
                        } else if (ConstantUtil.MonthlyInterestAndPrincipalMaturity.equals(data.get(8))) {// 按月付息到月还本
                            creditor.setRepaymentStyle(11602);
                        } else if (ConstantUtil.ExpirationTimeRepayment.equals(data.get(8))) {// 到期一次性还款
                            creditor.setRepaymentStyle(11603);
                        } else {
                            throw new ExcelDataFormatException("在单元格{" + 8 + "}类型不存在");
                        }
                        creditor.setRepaymenDate(data.get(9).toString());// 每月还款日
                        creditor.setRepaymenMoney(formatUtil.formatToDouble(data.get(10), 10));// 月还款金额
                        creditor.setDebtMoney(formatUtil.formatToDouble(data.get(11), 11));// 债权金额
                        creditor.setDebtMonthRate(formatUtil.formatToDouble(data.get(12), 12));// 债权月利率
                        creditor.setDebtTransferredMoney(formatUtil.formatToDouble(data.get(13), 13));// 债权转入金额
                        creditor.setDebtTransferredPeriod(formatUtil.formatToInt(data.get(14), 14));// 债权转入期限
                        creditor.setDebtRansferOutDate(formatUtil.format(date15.getTime(), 15));// 债权转出日期
                        creditor.setCreditor(data.get(16).toString());// 债权人

                        // 债权转入日期 原始开始日期+期限
                        Date startDate = formatUtil.format(date6, 6); // 原始开始日期
                        int add = formatUtil.formatToInt(data.get(14), 14);// 期限
                        Calendar c = Calendar.getInstance();
                        c.setTime(startDate);
                        c.add(Calendar.MONTH, add);
                        creditor.setDebtTransferredDate(c.getTime());

                        Date da = new Date();
//                        creditor.setDebtNo("ZQNO" + RandomNumberUtil.randomNumber(da));// 债权编号
                        creditor.setMatchedMoney(Double.valueOf("0"));// 已匹配金额
                        creditor.setDebtStatus(ClaimsType.UNCHECKDE); // 债权状态
                        creditor.setMatchedStatus(ClaimsType.UNMATCH);// 债权匹配状态
                        creditor.setBorrowerId(1); // 借款人id
//                        creditor.setDebtType(FrontStatusConstants.NULL_SELECT_OUTACCOUNT); // 标的类型
                        creditor.setAvailablePeriod(creditor.getDebtTransferredPeriod());// 可用期限
                        creditor.setAvailableMoney(creditor.getDebtTransferredMoney());// 可用金额
                        return creditor;
                    }
                });

                for (int i = 0; i < list.size(); i++) {
                    logger.info("{}",list.get(i));
                }
                //2.调用service完成save操作
                creditorDao.save(list);
                return true;
            } catch (InvalidFormatException e) {
                e.printStackTrace();
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }finally {
            //总是会删除失败，未解决
            /*if(newFlie.exists()){
                logger.info("enter...");
                boolean kk=FileUtils.deleteQuietly(newFlie);
                logger.info("{}",kk);
            }*/
        }
    }

    @Override
    public ResponseEntity<byte[]> downloadTemplateExcel() {
        //下载文件路径
        String parentPath = ConfigurableConstants.getProperty(ConfigurableConstants.EXCEL_TEMPLATE_PATH);
        String filename = ConfigurableConstants.getProperty(ConfigurableConstants.EXCEL_TEMPLATE_EXCEL);
        File file = new File(parentPath,filename);

        try {
            //下载显示的文件名，解决中文名称乱码问题
            String downloadFielName = new String(filename.getBytes("UTF-8"),"iso-8859-1");

            HttpHeaders headers = new HttpHeaders();
            //通知浏览器以attachment（下载方式）
            headers.setContentDispositionFormData("attachment", System.currentTimeMillis() + downloadFielName);
            //application/octet-stream ： 二进制流数据（最常见的文件下载）。
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.CREATED);
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RRException(e.getMessage());
        }
    }
}
