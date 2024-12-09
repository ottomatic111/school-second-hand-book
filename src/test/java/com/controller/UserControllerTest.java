package com.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.entity.UserEntity;
import com.service.UserService;
import com.alibaba.fastjson.JSON;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
    
    private MockMvc mockMvc;
    
    @Mock
    private UserService userService;
    
    @InjectMocks
    private UserController userController;
    
    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }
    
    @Test
    public void testLogin() throws Exception {
        // 准备测试数据
        UserEntity user = new UserEntity();
        user.setUsername("testUser");
        
        when(userService.login("testUser", "password123")).thenReturn(user);
        
        // 执行测试
        mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"testUser\",\"password\":\"password123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.username").value("testUser"));
    }
} 