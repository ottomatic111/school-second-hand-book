package com.service.impl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.dao.ErshoutushuDao;
import com.entity.ErshoutushuEntity;
import com.utils.PageUtils;
import com.utils.Query;

@RunWith(MockitoJUnitRunner.class)
public class ErshoutushuServiceImplTest {

    @Mock
    private ErshoutushuDao ershoutushuDao;

    @InjectMocks
    private ErshoutushuServiceImpl ershoutushuService;

    private ErshoutushuEntity testEntity;

    @Before
    public void setup() {
        testEntity = new ErshoutushuEntity();
        testEntity.setId(1L);
        testEntity.setTushumingcheng("测试图书");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testQueryPage() {
        // 测试用例1: 分页查询
        Map<String, Object> params = new HashMap<>();
        params.put("page", "1");
        params.put("limit", "10");

        // 准备测试数据
        List<ErshoutushuEntity> list = new ArrayList<>();
        list.add(testEntity);

        // 创建一个Page对象并设置数据
        Page<ErshoutushuEntity> page = new Page<>(1, 10);
        page.setRecords(list);
        page.setTotal(list.size());

        // Mock selectPage方法
        when(ershoutushuDao.selectPage(any(Page.class), any(Wrapper.class))).thenAnswer(invocation -> {
            Page<ErshoutushuEntity> argPage = invocation.getArgument(0);
            argPage.setRecords(list);
            argPage.setTotal(list.size());
            return argPage;
        });

        // 执行测试
        PageUtils pageUtils = ershoutushuService.queryPage(params);

        // 验证结果
        assertNotNull(pageUtils);
        assertEquals(1, pageUtils.getList().size());
        assertEquals(1, pageUtils.getTotal());

        // 验证方法调用
        verify(ershoutushuDao).selectPage(any(Page.class), any(Wrapper.class));
    }

    @Test
    public void testSelectById() {
        // 测试用例2: 根据ID查询
        when(ershoutushuDao.selectById(1L)).thenReturn(testEntity);
        
        ErshoutushuEntity result = ershoutushuService.selectById(1L);
        assertNotNull(result);
        assertEquals("测试图书", result.getTushumingcheng());
        System.out.println("测试通过: 成功查询到图书《" + result.getTushumingcheng() + "》");
    }

    @Test
    public void testInsert() {
        // 测试用例3: 插入数据
        when(ershoutushuDao.insert(any(ErshoutushuEntity.class))).thenReturn(1);
        
        boolean result = ershoutushuService.insert(testEntity);
        assertTrue(result);
        verify(ershoutushuDao).insert(testEntity);
    }
} 