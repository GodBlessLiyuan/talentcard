package com.talentcard.miniprogram.service;

import com.talentcard.common.vo.ResultVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: xiahui
 * @date: Created in 2020/6/10 10:31
 * @description: 小程序文件
 * @version: 1.0
 */
public interface IFileService {
    ResultVO update(HttpServletRequest req);
}
