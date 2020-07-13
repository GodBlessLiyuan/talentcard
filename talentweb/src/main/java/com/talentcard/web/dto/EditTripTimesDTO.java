package com.talentcard.web.dto;

import lombok.Data;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-07-13 11:09
 * @description
 */
@Data
public class EditTripTimesDTO {
    Long [] cardId;
    Integer[] tripTimes;
}
