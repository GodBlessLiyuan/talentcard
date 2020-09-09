package com.talentcard.web.service;

import javax.servlet.http.HttpSession;

public interface ILogService {
    void insertActionRecord(HttpSession session, String firstMenu, String secondMenuj, String detail,String ...params );
    void insertActionRecord(long userId, String userName, String firstMenu, String secondMenu, String detail, String... params);
}
