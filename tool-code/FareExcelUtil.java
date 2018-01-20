package cn.sibat.subway.fare.common.excel;

import cn.sibat.subway.fare.dto.StationExcelDTO;
import cn.sibat.subway.fare.util.Constants;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 利用开源组件POI3.0.2动态导出EXCEL文档
 *
 * @param 代表任意一个符合javabean风格的类 注意这里为了简单起见，boolean型的属性xxx的get器方式为getXxx(),而不是isXxx()
 *                                  byte[]表jpg格式的图片数据
 * @version v1.0
 */
public class FareExcelUtil {
    public static final String IS_NUMBER = "^([-]{0,1}[0-9]+){1}(([.]{1}[0-9]+)|([0-9]*))";
    public static final String IS_INTEGER = "^([-]{0,1}[0-9]+){1}[0-9]{0,}";
    public static Pattern IS_NUMBER_PATTERN = Pattern.compile(IS_NUMBER);
    public static Pattern IS_INTEGER_PATTERN = Pattern.compile(IS_INTEGER);


//    public void exportExcel(Collection<T> dataset, OutputStream out) {
//        exportExcel("导出EXCEL文档", null, dataset, out, "yyyy-MM-dd");
//    }

    public void exportExcel(String title, List<String> headers, List<List<StationExcelDTO>> dataset, OutputStream out) {
        exportExcel(title, headers, dataset, out, "yyyy-MM-dd", "traffic-flow");
    }

//    public void exportExcel(String[] headers, Collection<T> dataset,OutputStream out, String pattern) {
//        exportExcel("导出EXCEL文档", headers, dataset, out, pattern);
//    }

    /**
     * 生成票价值的颜色
     *
     * @param workbook
     * @param foregroundColor
     * @return
     */
    public HSSFCellStyle dataForegroundColorStyle(HSSFWorkbook workbook, short foregroundColor) {
        // 生成并设置另一个样式
        HSSFCellStyle style2 = workbook.createCellStyle();
        //生成背景色
        style2.setFillForegroundColor(foregroundColor);

        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成另一个字体
        HSSFFont font2 = workbook.createFont();
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式
        style2.setFont(font2);

        return style2;
    }

    public void saveExcel(String title, List<List<StationExcelDTO>> result, List<String> headers, String docPath, String fileName) {
        OutputStream stream = null;
        // 文件所在上级路径
        String filePath = docPath + fileName;
        File excelParentPath = new File(docPath);
        File excelFile = new File(filePath);

        try {
            // 先判断excel所在目录存不存在，不存在则先创建一个
            if (!excelParentPath.exists()) {
                excelParentPath.mkdirs();
            }
            // 判断文件存不存在，没有则创建一个
            if (!excelFile.exists()) {
                excelFile.createNewFile();
            }
            stream = new FileOutputStream(filePath);

            exportExcel(title, headers, result, stream);

            stream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
     *
     * @param title   表格标题名
     * @param headers 表格属性列名数组
     * @param dataset 需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
     *                javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
     * @param out     与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     * @param pattern 如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
     */
    public void exportExcel(String title, List<String> headers, List<List<StationExcelDTO>> dataset, OutputStream out, String pattern, String author) {
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为8个字节
        sheet.setDefaultColumnWidth((short) 7);
        // 生成一个样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.VIOLET.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式
        style.setFont(font);

        //线路号->线路颜色样式
        Map<Integer, HSSFCellStyle> no2CellStyle = getHeaderCellStyles(workbook);

        // 声明一个画图的顶级管理器
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        // 定义注释的大小和位置,详见文档
        HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
        // 设置注释内容
        // comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
        // 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
        comment.setAuthor(author);

        // 产生表格标题行
        HSSFRow rowheader0 = sheet.createRow(0);
        HSSFRow rowheader1 = sheet.createRow(1);
        HSSFRow rowheader2 = sheet.createRow(2);

        //标题栏的名称
        List<String> header0 = new LinkedList<>();
        List<String> header1 = new LinkedList<>();
        List<String> header2 = new LinkedList<>();

        //初始化第0、1、2行的标题栏值
        initHeader012(header0,header1,header2,dataset);

        for (List<StationExcelDTO> line2Stations : dataset) {
            int index = 0;
            for (StationExcelDTO station : line2Stations) {
                header0.add(station.getLineNo() + "号线");
                header1.add(++index + "");
                header2.add(station.getStationName());
            }
        }

        //生成的样式给标题栏用
        HSSFCellStyle whiteStyle = dataForegroundColorStyle(workbook, HSSFColor.WHITE.index);
        for (int i = 0; i < 3; i++) {
            ////////////////////////////row0//////////////////////////////
            HSSFCell header0Cell = rowheader0.createCell(i);
            HSSFRichTextString text0 = new HSSFRichTextString(header0.get(i));
            header0Cell.setCellValue(text0);
            header0Cell.setCellStyle(whiteStyle);
            ////////////////////////////row1//////////////////////////////
            HSSFCell header1Cell = rowheader1.createCell(i);
            HSSFRichTextString text1 = new HSSFRichTextString(header1.get(i));
            Matcher matcher = IS_INTEGER_PATTERN.matcher(text1.toString());
            //数值
            if(matcher.matches()){
                header1Cell.setCellValue(Integer.parseInt(text1.toString()));
            }else{
                header1Cell.setCellValue(text1);
            }
            header1Cell.setCellStyle(whiteStyle);
            ////////////////////////////row2//////////////////////////////
            HSSFCell header2Cell = rowheader2.createCell(i);
            HSSFRichTextString text2 = new HSSFRichTextString(header2.get(i));
            header2Cell.setCellValue(text2);
            header2Cell.setCellStyle(whiteStyle);
        }

        //创建的列名
        int lineCount = 1;
        //前三列是属性，从第3列开始，才是值
        int columnCount = 3;
        for (List<StationExcelDTO> line2Stations : dataset) {
            //值为空的跳过
            if (CollectionUtils.isEmpty(line2Stations)) {
                continue;
            }

            //起始列名记录(因为前三列是属性)
            int startColumnCount = columnCount;
            for (StationExcelDTO obj : line2Stations) {
                ////////////////////////////row0//////////////////////////////
                HSSFCell header0Cell = rowheader0.createCell(columnCount);
                HSSFRichTextString text0 = new HSSFRichTextString(header0.get(columnCount));
                getStyleByLine(header0Cell, text0, lineCount, no2CellStyle, style);
                ////////////////////////////row1//////////////////////////////
                HSSFCell header1Cell = rowheader1.createCell(columnCount);
                HSSFRichTextString text1 = new HSSFRichTextString(header1.get(columnCount));
                getStyleByLine(header1Cell, text1, lineCount, no2CellStyle, style);
                ////////////////////////////row2//////////////////////////////
                HSSFCell header2Cell = rowheader2.createCell(columnCount);
                HSSFRichTextString text2 = new HSSFRichTextString(header2.get(columnCount));
                getStyleByLine(header2Cell, text2, lineCount, no2CellStyle, style);

                //列累加
                columnCount++;
            }
            // 合并单元格
            CellRangeAddress cra = new CellRangeAddress(0, 0, startColumnCount, columnCount - 1); // 起始行, 终止行, 起始列, 终止列
            sheet.addMergedRegion(cra);

            lineCount++;
        }


        //从第三列开始
        int rowCount=3;
        //创建的列名
        lineCount = 1;
        for (List<StationExcelDTO> lineList: dataset) {
            //值为空的跳过
            if (CollectionUtils.isEmpty(lineList)) {
                continue;
            }

            int stationIndex = 0;
            //起始行记录(因为前三行是表头名)
            int startRowCount = rowCount;
            for (StationExcelDTO obj : lineList) {
                HSSFRow row = sheet.createRow(rowCount);

                for (int i = 0; i < 3; i++) {
                    HSSFCell cell = row.createCell(i);
                    String textValue = "";
                    if (i == 0) {
                        textValue = obj.getLineNo() + "号线";
                    } else if (i == 1) {
                        textValue = ++stationIndex + "";
                    } else if (i == 2) {
                        textValue = obj.getStationName();
                    }
                    HSSFRichTextString richString = new HSSFRichTextString(textValue);
                    getStyleByLine(cell, richString, lineCount, no2CellStyle, style);
                }

                columnCount = 3;
                //票价记录的集合
                for (String str : obj.getDates()) {
                    // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
                    if (str != null) {
                        //变色处理
                           /* Pattern p = Pattern.compile(IS_NUMBER);
                            Matcher matcher = p.matcher(textValue);
                            if (matcher.matches()) {
                                // 是数字当作double处理
                                double num = Double.parseDouble(textValue);
                                HSSFRichTextString richString = new HSSFRichTextString(
                                        textValue);
                                //font3.setColor(HSSFColor.BLUE.index);

                                if (num >= 4) {
                                    cell.setCellStyle(style2);
                                } else if (num >= 3) {
                                    cell.setCellStyle(style3);
                                } else if (num >= 2) {
                                    cell.setCellStyle(style4);
                                } else if (num >= 1) {
                                    cell.setCellStyle(style5);
                                }

                                richString.applyFont(font3);

                                cell.setCellValue(num);
                            } */

                        HSSFCell cell = row.createCell(columnCount++);
                        HSSFRichTextString richString = new HSSFRichTextString(str);
                        cell.setCellStyle(whiteStyle);

                        Matcher matcher = IS_NUMBER_PATTERN.matcher(str);
                        //数值
                        if(matcher.matches()){
                            // 是数字当作double处理
                            double num = Double.parseDouble(str);
                            cell.setCellValue(num);
                        }
                        //文本
                        else{
                            cell.setCellValue(richString);
                        }
                    }
                }

                    /*} catch (SecurityException e) {
                        e.printStackTrace();
                    } finally {
                        // 清理资源
                    }
                }*/
                rowCount++;
            }//lineList
            // 合并单元格
            CellRangeAddress cra = new CellRangeAddress(startRowCount, rowCount - 1, 0, 0); // 起始行, 终止行, 起始列, 终止列
            sheet.addMergedRegion(cra);
            lineCount++;
        }//dataset
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param header0 车站数
     * @param header1 2
     * @param header2 线路号	站编号	站名
     * @param dataset
     */
    private void initHeader012(List<String> header0, List<String> header1, List<String> header2, List<List<StationExcelDTO>> dataset) {
        header0.add("车站数");
        header0.add("");
        header0.add("");

        int stationTotal = 0;
        for (List<StationExcelDTO> line2Stations : dataset) {
            stationTotal += line2Stations.size();
        }
        header1.add(stationTotal + "");
        header1.add("");
        header1.add("");

        header2.add("线路号");
        header2.add("站编号");
        header2.add("站名");
    }

    /**
     * 提供给标题栏，不同线路的单元格对应的颜色
     * @param workbook Excel文档实例
     * @return
     */
    private Map<Integer, HSSFCellStyle> getHeaderCellStyles(HSSFWorkbook workbook) {
        HSSFCellStyle styleLine1 = dataForegroundColorStyle(workbook, HSSFColor.LIME.index);
        HSSFCellStyle styleLine2 = dataForegroundColorStyle(workbook, HSSFColor.GOLD.index);
        HSSFCellStyle styleLine3 = dataForegroundColorStyle(workbook, HSSFColor.SKY_BLUE.index);
        HSSFCellStyle styleLine4 = dataForegroundColorStyle(workbook, HSSFColor.RED.index);
        HSSFCellStyle styleLine5 = dataForegroundColorStyle(workbook, HSSFColor.LAVENDER.index);
        HSSFCellStyle styleLine7 = dataForegroundColorStyle(workbook, HSSFColor.ROYAL_BLUE.index);
        HSSFCellStyle styleLine9 = dataForegroundColorStyle(workbook, HSSFColor.MAROON.index);
        HSSFCellStyle styleLine11 = dataForegroundColorStyle(workbook, HSSFColor.BROWN.index);

        Map<Integer, HSSFCellStyle> no2CellStyle = new HashMap<>();
        no2CellStyle.put(1, styleLine1);
        no2CellStyle.put(2, styleLine2);
        no2CellStyle.put(3, styleLine3);
        no2CellStyle.put(4, styleLine4);
        no2CellStyle.put(5, styleLine5);
        no2CellStyle.put(7, styleLine7);
        no2CellStyle.put(8, styleLine9);
        no2CellStyle.put(6, styleLine11);
        return no2CellStyle;
    }

    /**
     * @param headerCell   单元格
     * @param text         单元格内容
     * @param lineCount    线路号数
     * @param no2CellStyle 线路号->颜色样式
     * @param style        默认样式
     */
    private void getStyleByLine(HSSFCell headerCell, HSSFRichTextString text, int lineCount, Map<Integer, HSSFCellStyle> no2CellStyle, HSSFCellStyle style) {
        if (no2CellStyle.containsKey(lineCount)) {
            headerCell.setCellStyle(no2CellStyle.get(lineCount));
        } else {
            headerCell.setCellStyle(style);
        }
        Matcher matcher = IS_INTEGER_PATTERN.matcher(text.toString());
        //数值
        if(matcher.matches()){
            headerCell.setCellValue(Integer.parseInt(text.toString()));
        }else{
            headerCell.setCellValue(text);
        }
    }
}
