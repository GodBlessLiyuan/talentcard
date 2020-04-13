package com.talentcard.common.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author xiahui
 * @version 1.0
 * @date Created in 2020/2/21 16:39
 * @description
 */
public class FileUtil {

    /**
     * 文件上传处理
     *
     * @param file 文件信息
     * @param dir  file存放文件目录
     * @return 文件路径
     * @throws IOException
     */
    public static String uploadFile(MultipartFile file, String rootPath, String projectDir, String dir, String moduleName) {
        String fileName = FileUtil.buildFileName(file, moduleName);
        File targetFile = new File(rootPath + projectDir + dir);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }

        String filePath = rootPath + projectDir + dir + fileName;
        BufferedOutputStream stream = null;
        try {
            stream = new BufferedOutputStream(new FileOutputStream(filePath));
            stream.write(file.getBytes());
            stream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return projectDir + dir + fileName;
    }

    /**
     * 构建文件名
     *
     * @param file
     * @param moduleName
     * @return
     */
    private static String buildFileName(MultipartFile file, String moduleName) {
        String ext = file.getOriginalFilename().split("\\.")[file.getOriginalFilename().split("\\.").length - 1];
        return moduleName + System.currentTimeMillis() + RandomUtil.getRandomString(8) + "." + ext;
    }
}
