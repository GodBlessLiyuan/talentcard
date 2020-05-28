package com.talentcard.web.controller;

import com.talentcard.web.BaseTest;
import com.talentcard.web.WebApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author: velve
 * @date: Created in 2020/5/27 20:14
 * @description: TODO
 * @version: 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@AutoConfigureMockMvc
public class CardControllerTest extends BaseTest {

    @Test
    public void add() {
    }

    @Test
    public void edit() {
    }

    @Test
    public void query() {
    }

    @Test
    public void findOne() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void findSeniorCard() {
    }

    @Test
    public void queryCardIdName() {
    }
}