package com.talentcard.web;

import com.talentcard.common.mapper.AuthorityMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = WebApplication.class)
public class WebApplicationTest {

    //authority
    @Autowired
    private AuthorityMapper authorityMapper;
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testQuery() {
        String name = authorityMapper.queryByAuthorityId(1);
        System.out.println("1123");
        System.out.println(name);
    }


    @After
    public void tearDown() throws Exception {

    }
}