package com.talentcard.miniprogram.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.miniprogram.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: xiahui
 * @date: Created in 2020/6/10 10:25
 * @description: 小程序文件
 * @version: 1.0
 */
@RequestMapping("file")
@RestController
public class FileController {
    @Autowired
    private IFileService fileService;

    @RequestMapping("upload")
    public ResultVO upload(HttpServletRequest req) {
        return fileService.update(req);
    }
}
