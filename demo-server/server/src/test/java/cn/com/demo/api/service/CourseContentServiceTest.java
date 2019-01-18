package cn.com.demo.api.service;

import cn.com.demo.BootApplicationTests;
import cn.com.demo.common.exception.AppException;
import cn.com.demo.api.entity.EnumConstant;
import cn.com.demo.api.mapper.EntryQuestionMapper;
import cn.com.demo.api.service.impl.EntryTestService;
import org.junit.Test;

import javax.annotation.Resource;

public class CourseContentServiceTest extends BootApplicationTests {

    @Resource
    private EntryTestService entryTestService;
    @Resource
    private EntryQuestionMapper entryQuestionMapper;

    @Test
    public void questionType() throws AppException {
        entryQuestionMapper.list(EnumConstant.ONE_TWO);
    }
}
