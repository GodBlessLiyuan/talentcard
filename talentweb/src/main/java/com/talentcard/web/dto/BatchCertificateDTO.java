package com.talentcard.web.dto;

import com.talentcard.common.pojo.BatchCertificatePO;
import lombok.Data;

import java.util.List;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-05-27 10:47
 * @description
 */
@Data
public class BatchCertificateDTO {
    private String fileName;
    private List<String> names;
    private List<String> idCards;
    private Long cardId;
    private String talentCategory;
    private Long talentHonour;
    private BatchCertificatePO batchCertificatePO;
    private Integer resultStatus;
}
