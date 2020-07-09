package com.talentcard.front.controller;

import com.talentcard.front.FrontApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

/**
 * @author: velve
 * @date: Created in 2020/7/9 17:43
 * @description: TODO
 * @version: 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FrontApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@AutoConfigureMockMvc
public class InsertCertificationControllerTest extends BaseTest{

    @Test
    public void addEducation() {
    }

    @Test
    public void addProfQuality() {
    }

    @Test
    public void addProfTitle() {
    }

    @Test
    public void addTalentHonour() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void editBasicInfo() {
    }

    @Test
    public void editPhone() {
    }

    @Test
    public void findOneDetail() {
    }

    @Test
    public void findInsertCertificationTimes() {
    }

    @Test
    public void findResultByInsertCertId() {
    }
}