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

import com.entity.ErshoutushuEntity;
import com.service.ErshoutushuService;
import com.utils.R;
import com.alibaba.fastjson.JSON;

@RunWith(MockitoJUnitRunner.class)
public class ErshoutushuControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ErshoutushuService ershoutushuService;

    @InjectMocks
    private ErshoutushuController ershoutushuController;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(ershoutushuController).build();
    }

    @Test
    public void testSave() throws Exception {
        // 测试用例1: 保存二手图书信息
        ErshoutushuEntity entity = new ErshoutushuEntity();
        entity.setTushumingcheng("测试图书");
        
        when(ershoutushuService.insert(any(ErshoutushuEntity.class))).thenReturn(true);

        mockMvc.perform(post("/ershoutushu/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(entity)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0));
    }

    @Test
    public void testInfo() throws Exception {
        // 测试用例2: 获取图书详情
        ErshoutushuEntity entity = new ErshoutushuEntity();
        entity.setId(1L);
        entity.setTushumingcheng("测试图书");
        entity.setClicknum(0);

        when(ershoutushuService.selectById(1L)).thenReturn(entity);

        mockMvc.perform(get("/ershoutushu/info/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.tushumingcheng").value("测试图书"));
    }

    @Test
    public void testDelete() throws Exception {
        // 测试用例3: 删除图书
        Long[] ids = {1L, 2L};
        when(ershoutushuService.deleteBatchIds(anyList())).thenReturn(true);

        mockMvc.perform(post("/ershoutushu/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(ids)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0));
    }
} 