package com.talentcard.common.utils;

import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: xiahui
 * @date: Created in 2020/4/15 11:34
 * @description: 导出工具
 * @version: 1.0
 */
public class ExportUtil {

    /**
     * 构建 XSSFWorkbook
     *
     * @param head
     * @param titles
     * @param contents
     * @return
     */
    public static XSSFWorkbook buildXSSFWorkbook(String head, String[] titles, String[][] contents) {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("sheet1");
        XSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);

        // 行号
        int r = 0;
        if (null != head && null != titles) {
            XSSFCell cell = sheet.createRow(r).createCell(0);
            cell.setCellValue(head);
            cell.setCellStyle(style);
            sheet.addMergedRegion(new CellRangeAddress(r, r, 0, titles.length - 1));
            r++;
        }

        if (null != titles) {
            XSSFRow row = sheet.createRow(r);
            for (int i = 0; i < titles.length; i++) {
                XSSFCell cell = row.createCell(i);
                cell.setCellValue(titles[i]);
                cell.setCellStyle(style);
            }
            r++;
        }

        if (null != contents) {
            for (String[] content : contents) {
                XSSFRow row = sheet.createRow(r);
                for (int i = 0; i < content.length; i++) {
                    XSSFCell cell = row.createCell(i);
                    cell.setCellValue(content[i]);
                    cell.setCellStyle(style);
                }
                r++;
            }
        }

        return wb;
    }

    /**
     * 导出Excel表格
     *
     * @param head
     * @param titles
     * @param contents
     * @param res
     */
    public static void exportExcel(String head, String[] titles, String[][] contents, HttpServletResponse res) {
        XSSFWorkbook wb = buildXSSFWorkbook(head, titles, contents);
        sendToClient(wb, res);
    }

    /**
     * 发送响应流数据到前端
     */
    public static void sendToClient(XSSFWorkbook wb, HttpServletResponse res) {
        // 获取当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateNowStr = sdf.format(new Date());
        // Excel文件名
        String filename = dateNowStr + ".xlsx";

        //响应到前端
        try {
            setResponseHeader(res, filename);
            OutputStream os = res.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 响应流设置
     */
    private static void setResponseHeader(HttpServletResponse response, String filename) {
        try {
            try {
                filename = new String(filename.getBytes("gb2312"), "ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + filename);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
