package com.talentcard.common.bo;

import com.talentcard.common.pojo.EditTalentRecordPO;
import lombok.Data;

/**
 * @author DNY-029
 */
@Data
public class EditTalentRecordBO extends EditTalentRecordPO {
    private String certType;

    private String userName;
}
