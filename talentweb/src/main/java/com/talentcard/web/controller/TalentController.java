package com.talentcard.web.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.dto.BatchCertificateDTO;
import com.talentcard.web.service.ITalentService;
import com.talentcard.web.utils.BatchCertificateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/4/16 9:14
 * @description: 人才管理
 * @version: 1.0
 */
@RequestMapping("talent")
@RestController
public class TalentController {
    @Autowired
    private ITalentService iTalentService;

    /**
     * 普通用户查询
     *
     * @param pageNum
     * @param pageSize
     * @param start
     * @param end
     * @param name
     * @param sex
     * @param educ
     * @param title
     * @param quality
     * @param card
     * @param honour
     * @return
     */
    @RequestMapping("query")
    public ResultVO query(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                          @RequestParam(value = "start", defaultValue = "") String start,
                          @RequestParam(value = "end", defaultValue = "") String end,
                          @RequestParam(value = "name", defaultValue = "") String name,
                          @RequestParam(value = "sex", defaultValue = "") Byte sex,
                          @RequestParam(value = "educ", defaultValue = "") Integer educ,
                          @RequestParam(value = "title", defaultValue = "") Integer title,
                          @RequestParam(value = "quality", defaultValue = "") Integer quality,
                          @RequestParam(value = "card", defaultValue = "") String card,
                          @RequestParam(value = "honour", defaultValue = "") Long honour) {

        Map<String, Object> reqMap = new HashMap<>(10);
        if (!"".equals(end)) {
            end = end + " 23:59:59";
        }
        reqMap.put("start", start);
        reqMap.put("end", end);
        reqMap.put("name", name.replaceAll("%", "\\\\%"));
        reqMap.put("sex", sex);
        reqMap.put("educ", educ);
        reqMap.put("title", title);
        reqMap.put("quality", quality);
        reqMap.put("card", card);
        reqMap.put("honour", honour);

        return iTalentService.query(pageNum, pageSize, reqMap);
    }

    /**
     * 普通用户；查看详情
     *
     * @param tid
     * @return
     */
    @RequestMapping("detail")
    public ResultVO detail(@RequestParam(value = "tid") Long tid) {
        return iTalentService.detail(tid);
    }

    /**
     * 认证人才查询
     *
     * @param pageNum
     * @param pageSize
     * @param start
     * @param end
     * @param name
     * @param sex
     * @param educ
     * @param title
     * @param quality
     * @param card
     * @param category
     * @param honour
     * @return
     */
    @RequestMapping("queryCert")
    public ResultVO queryCert(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                              @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                              @RequestParam(value = "start", defaultValue = "") String start,
                              @RequestParam(value = "end", defaultValue = "") String end,
                              @RequestParam(value = "name", defaultValue = "") String name,
                              @RequestParam(value = "sex", defaultValue = "") Byte sex,
                              @RequestParam(value = "educ", defaultValue = "")  Integer educ,
                              @RequestParam(value = "title", defaultValue = "") Integer title,
                              @RequestParam(value = "quality", defaultValue = "") Integer quality,
                              @RequestParam(value = "card", defaultValue = "") String card,
                              @RequestParam(value = "category", defaultValue = "") String category,
                              @RequestParam(value = "honour", defaultValue = "") Long honour) {
        Map<String, Object> reqMap = new HashMap<>(10);
        if (!StringUtils.isEmpty(start)) {
            start = start + " 00:00:00";
        }
        if (!StringUtils.isEmpty(end)) {
            end = end + " 23:59:59";
        }
        reqMap.put("start", start);
        reqMap.put("end", end);
        reqMap.put("name", name.replaceAll("%", "\\\\%"));
        reqMap.put("sex", sex);
        reqMap.put("educ", educ);
        reqMap.put("title", title);
        reqMap.put("quality", quality);
        reqMap.put("card", card);
        reqMap.put("category", category);
        reqMap.put("honour", honour);

        return iTalentService.queryCert(pageNum, pageSize, reqMap);
    }

    /**
     * 批量认证
     *
     * @param httpSession
     * @param cardId
     * @param talentCategory
     * @param talentHonour
     * @param file
     * @return
     * @throws InterruptedException
     */
    @RequestMapping("batchCertificate")
    public ResultVO batchCertificate(HttpSession httpSession,
                                     @RequestParam(value = "cardId") Long cardId,
                                     @RequestParam(value = "talentCategory", required = false, defaultValue = "") String talentCategory,
                                     @RequestParam(value = "talentHonour") Long talentHonour,
                                     @RequestParam(value = "file") MultipartFile file) throws InterruptedException {
        BatchCertificateDTO batchCertificateDTO = iTalentService.readCertificateFile(httpSession, file);
        if (batchCertificateDTO == null || batchCertificateDTO.getResultStatus() != 1000) {
            return new ResultVO(2800);
        }
        batchCertificateDTO.setTalentCategory(talentCategory);
        batchCertificateDTO.setTalentHonour(talentHonour);
        batchCertificateDTO.setCardId(cardId);
        iTalentService.batchCertificate(batchCertificateDTO);
        return new ResultVO(1000);
    }

    /**
     * 批量认证查询
     *
     * @param pageNum
     * @param pageSize
     * @param fileName
     * @param startTime
     * @param endTime
     * @param status
     * @return
     */
    @RequestMapping("findBatchCertificate")
    public ResultVO findBatchCertificate(
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "fileName", required = false, defaultValue = "") String fileName,
            @RequestParam(value = "startTime", required = false, defaultValue = "") String startTime,
            @RequestParam(value = "endTime", required = false, defaultValue = "") String endTime,
            @RequestParam(value = "status", required = false, defaultValue = "0") Integer status) {
        HashMap<String, Object> hashMap = new HashMap<>(4);
        if (!startTime.equals("")) {
            startTime = startTime + " 00:00:00";
        }
        if (!endTime.equals("")) {
            endTime = endTime + " 23:59:59";
        }

        hashMap.put("fileName", fileName);
        hashMap.put("startTime", startTime);
        hashMap.put("endTime", endTime);
        hashMap.put("status", status);
        return iTalentService.findBatchCertificate(pageNum, pageSize, hashMap);
    }
}
