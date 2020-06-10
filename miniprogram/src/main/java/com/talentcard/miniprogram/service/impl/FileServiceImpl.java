package com.talentcard.miniprogram.service.impl;

import com.talentcard.common.config.FilePathConfig;
import com.talentcard.common.utils.FileUtil;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.miniprogram.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: xiahui
 * @date: Created in 2020/6/10 10:31
 * @description: 小程序文件
 * @version: 1.0
 */
@Service
public class FileServiceImpl implements IFileService {
    @Autowired
    private FilePathConfig filePathConfig;

    @Override
    public ResultVO update(HttpServletRequest req) {
        MultipartFile file = ((MultipartHttpServletRequest) req).getFile("file");
        String picture = FileUtil.uploadFile(file, filePathConfig.getLocalBasePath(), filePathConfig.getProjectDir(), filePathConfig.getMiniDir(), "mini");
        return new ResultVO<>(1000, filePathConfig.getPublicBasePath() + picture);
    }
}
