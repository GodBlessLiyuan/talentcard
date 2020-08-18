package com.talentcard.common.bo;

import com.talentcard.common.pojo.PoTypePO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @ Author     ：AnHongxu.
 * @ Date       ：Created in 20:28 2020/8/12
 * @ Description：政策类型业务BO
 * @ Modified By：
 * @ Version:     1.0
 */
@Slf4j
@Data
public class PolicyTypeBO extends PoTypePO {
    /**
     * 政策类型互斥id
     */
    private List<String> excludeIds;
    /**
     * 互斥政策类型名称
     */
    private List<String> excludeNames;

}
