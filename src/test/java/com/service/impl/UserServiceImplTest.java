package com.service.impl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.dao.UserDao;
import com.entity.UserEntity;
import com.utils.MD5Util;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    
    @Mock
    private UserDao userDao;
    
    @InjectMocks
    private UserServiceImpl userService;
    
    @Test
    public void testLogin_Success() {
        // 准备测试数据
        String username = "testUser";
        String password = "password123";
        String encryptedPassword = MD5Util.encrypt(password);
        
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(encryptedPassword);
        
        when(userDao.selectByUsername(username)).thenReturn(user);
        
        // 执行测试
        UserEntity result = userService.login(username, password);
        
        // 验证结果
        assertNotNull(result);
        assertEquals(username, result.getUsername());
    }
    
    @Test
    public void testLogin_WrongPassword() {
        // 准备测试数据
        String username = "testUser";
        String password = "wrongPassword";
        
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(MD5Util.encrypt("correctPassword"));
        
        when(userDao.selectByUsername(username)).thenReturn(user);
        
        // 执行测试
        UserEntity result = userService.login(username, password);
        
        // 验证结果
        assertNull(result);
    }
    
    @Test
    public void testRegister_Success() {
        // 准备测试数据
        UserEntity user = new UserEntity();
        user.setUsername("newUser");
        user.setPassword("password123");
        
        when(userDao.selectByUsername("newUser")).thenReturn(null);
        when(userDao.insert(any(UserEntity.class))).thenReturn(1);
        
        // 执行测试
        boolean result = userService.register(user);
        
        // 验证结果
        assertTrue(result);
        verify(userDao).insert(any(UserEntity.class));
    }
} 