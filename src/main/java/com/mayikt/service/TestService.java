package com.mayikt.service;

import com.mayikt.bean.TestBean;

import java.util.List;


/**
 * @author: pyfysf
 * <p>
 * @qq: 337081267
 * <p>
 * @CSDN: http://blog.csdn.net/pyfysf
 * <p>
 * @blog: http://wintp.top
 * <p>
 * @email: pyfysf@163.com
 * <p>
 * @time: 2019/9/17
 */
public interface TestService {
    Iterable<TestBean> findAll();

    void save(List<TestBean> list);

    void save();

    void update();

    List<TestBean> findByName(String text);

	List<TestBean> findByNameOrDesc(String text);

    TestBean getById(Long id);

    void deleteById(Long id);

    List<TestBean> listPage();
}