package com.talentcard.web.service.impl;

import com.talentcard.common.config.FilePathConfig;
import com.talentcard.common.utils.FileUtil;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IFileService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-06-10 10:03
 * @description
 */
@Service
public class FileServiceImpl implements IFileService {
    @Autowired
    private FilePathConfig filePathConfig;

    @Override
    public ResultVO upload(MultipartFile multipartFile, String projectName) {
        String directory = projectName + "/";
        String url = FileUtil.uploadFile
                (multipartFile, filePathConfig.getLocalBasePath(), filePathConfig.getProjectDir(),
                        directory, projectName);
        if (url == null || StringUtils.isEmpty(url)) {
            return new ResultVO(2810, "文件上传失败！");
        }
        return new ResultVO(1000, url);
    }
}
