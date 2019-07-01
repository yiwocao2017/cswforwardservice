/**
 * @Title ExcelUtil.java 
 * @Package com.xnjr.app.util 
 * @Description 
 * @author xieyj  
 * @date 2015年12月9日 下午7:03:41 
 * @version V1.0   
 */
package com.cdkj.service.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

/** 
 * @author: xieyj 
 * @since: 2015年12月9日 下午7:03:41 
 * @history:
 */
public class ExcelUtil<T> {
    /**
     * 创建excel文档
     * @param sheetName 表格名称
     * @param columnNames 列名
     * @param dataset 数据
     * @return 
     * @create: 2015年12月12日 下午1:43:07 xieyj
     * @history:
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public HSSFWorkbook createWorkBook(String sheetName, String[] columnNames,
            Collection<T> dataset) {
        // 创建excel工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建第一个sheet（页），并命名
        HSSFSheet sheet = workbook.createSheet(sheetName);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 20);

        // 创建两种单元格格式
        HSSFCellStyle cs = workbook.createCellStyle();
        HSSFFont f = workbook.createFont();
        // 创建第一种字体样式（用于列名）
        f.setFontHeightInPoints((short) 10);
        f.setColor(HSSFColor.BLACK.index);
        f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

        // 设置第一种单元格的样式（用于列名）
        cs.setFont(f);
        cs.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cs.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cs.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cs.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cs.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        HSSFCellStyle cs2 = workbook.createCellStyle();
        HSSFFont f2 = workbook.createFont();
        // 创建第二种字体样式（用于值）
        f2.setFontHeightInPoints((short) 10);
        f2.setColor(HSSFColor.BLACK.index);

        // 设置第二种单元格的样式（用于值）
        cs2.setFont(f2);
        cs2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cs2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cs2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cs2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cs2.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        // 创建第一行
        HSSFRow colRow = sheet.createRow(0);
        // 设置列名
        for (int i = 0; i < columnNames.length; i++) {
            HSSFCell cell = colRow.createCell((short) i);
            cell.setEncoding(HSSFCell.ENCODING_UTF_16);
            cell.setCellValue(columnNames[i]);
            cell.setCellStyle(cs);
        }

        // 遍历集合数据，产生数据行
        Iterator<T> it = dataset.iterator();
        int index = 0;
        while (it.hasNext()) {
            index++;
            HSSFRow row = sheet.createRow(index);
            T t = (T) it.next();
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
            Field[] fields = t.getClass().getDeclaredFields();
            for (short i = 0; i < fields.length; i++) {
                Field field = fields[i];
                String fieldName = field.getName();
                String getMethodName = "get"
                        + fieldName.substring(0, 1).toUpperCase()
                        + fieldName.substring(1);
                Class tCls = t.getClass();
                Method getMethod;
                try {
                    getMethod = tCls.getMethod(getMethodName, new Class[] {});
                    Object value;
                    value = getMethod.invoke(t, new Object[] {});
                    // 判断值的类型后进行强制类型转换
                    String textValue = null;
                    if (value instanceof Date) {
                        Date date = (Date) value;
                        textValue = DateUtil.dateToStr(date,
                            DateUtil.DATA_TIME_PATTERN_1);
                    } else if (value instanceof Long) {
                        Long longVal = (Long) value;
                        textValue = CalculationUtil.diviFormate(longVal);
                    }

                    else {
                        if (value != null) {
                            // 其它数据类型都当作字符串简单处理
                            textValue = value.toString();
                        }
                    }

                    // 给单元格赋值
                    HSSFCell cell = row.createCell(i);
                    cell.setCellStyle(cs2);
                    cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                    cell.setCellValue(textValue);
                } catch (SecurityException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
                    // 清理资源
                }
            }
        }
        return workbook;
    }

    public void generateExcel(String fileName, String sheetName,
            String[] columnNames, Collection<T> dataset,
            HttpServletResponse response) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            createWorkBook(sheetName, columnNames, dataset).write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        // 设置response参数，可以打开下载页面
        // response.setContentType("application/binary;charset=ISO8859_1");
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename="
                + new String((fileName + ".xls").getBytes(), "ISO8859-1"));
        response.setCharacterEncoding("ISO8859-1");
        ServletOutputStream out = response.getOutputStream();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            // Simple read/write loop.
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (final IOException e) {
            throw e;
        } finally {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
        }
    }
}
