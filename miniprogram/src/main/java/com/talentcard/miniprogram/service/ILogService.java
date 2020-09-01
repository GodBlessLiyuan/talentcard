package com.talentcard.miniprogram.service;

import javax.servlet.http.HttpSession;

public interface ILogService {
    void insertActionRecord(HttpSession session, String firstMenu, String secondMenuj, String detail,String ...params );
}
