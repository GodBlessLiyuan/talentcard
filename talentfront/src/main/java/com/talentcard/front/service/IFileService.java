package com.talentcard.front.service;

import com.talentcard.common.vo.ResultVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-06-10 10:03
 * @description
 */
public interface IFileService {
    ResultVO upload(MultipartFile multipartFile, String directory);
}
