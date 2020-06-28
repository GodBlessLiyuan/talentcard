package com.talentcard.web.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-06-10 09:59
 * @description
 */
@RequestMapping("file")
@RestController
public class FileController {
    @Autowired
    IFileService iFileService;

    @RequestMapping("upload")
    public ResultVO upload(@RequestParam("file") MultipartFile multipartFile,
                           @RequestParam(value = "projectName", required = false, defaultValue = "temp") String projectName) {
        return iFileService.upload(multipartFile, projectName);
    }
}
