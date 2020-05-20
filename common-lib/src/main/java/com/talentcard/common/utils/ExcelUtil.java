package com.talentcard.common.utils;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: xiahui
 * @date: Created in 2020/5/20 9:02
 * @description: Excel 工具
 * @version: 1.0
 */
public class ExcelUtil<T> {

    /**
     * 表头是否符合规范
     *
     * @param row
     * @param title
     * @return
     */
    public static boolean isNormTitle(XSSFRow row, String[] title) {
        for (int i = 0; i < title.length; i++) {
            if (!title[i].equals(ExcelUtil.getStringValue(row, i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * 获取String类型数据
     *
     * @param row
     * @param index
     * @return
     */
    public static String getStringValue(XSSFRow row, int index) {
        XSSFCell cell = row.getCell(index);
        if (null == cell) {
            return null;
        }
        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue().trim();
    }

    /**
     * 生成Excel文件
     *
     * @param head
     * @param title
     * @param content
     * @return
     */
    public static XSSFWorkbook buildExcel(String head, String[] title, String[][] content) {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        XSSFSheet sheet = ExcelUtil.buildSheet(wb, style, head, title);

        if (null != content && content.length != 0) {
            for (int i = 0; i < content.length; i++) {
                String[] data = content[i];
                XSSFRow row = sheet.createRow(i + 2);
                for (int j = 0; j < data.length; j++) {
                    XSSFCell cell = row.createCell(j);
                    cell.setCellValue(data[j]);
                    cell.setCellStyle(style);
                }
            }
        }

        return wb;
    }

    /**
     * 生成基础的sheet
     *
     * @param wb
     * @param head
     * @param title
     * @return
     */
    public static XSSFSheet buildSheet(XSSFWorkbook wb, XSSFCellStyle style, String head, String[] title) {
        XSSFSheet sheet = wb.createSheet("批量认证数据");

        XSSFCell cell0 = sheet.createRow(0).createCell(0);
        cell0.setCellValue(head);
        cell0.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, title.length - 1));

        XSSFRow row1 = sheet.createRow(1);
        for (int i = 0; i < title.length; i++) {
            XSSFCell cell = row1.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        return sheet;
    }

    /**
     * 存储Excel数据
     *
     * @param wb
     */
    public static String save(XSSFWorkbook wb, String rootPath, String projectDir, String dir, String name) {
        //Excel文件名
        String filename = buildFileName(name);
        File targetFile = new File(rootPath + projectDir + dir);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }

        String filePath = rootPath + projectDir + dir + filename;
        BufferedOutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(filePath));
            wb.write(os);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return projectDir + dir + filename;
    }

    /**
     * 构建文件名称
     *
     * @return
     */
    private static String buildFileName(String name) {
        //获取当前时间
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateNowStr = sdf.format(date);
        //Excel文件名
        return name + dateNowStr + ".xlsx";
    }
}
