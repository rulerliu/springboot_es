package com.mayikt.controller;

import com.mayikt.bean.PageVo;
import com.mayikt.bean.TestBean;
import com.mayikt.dao.TestDao;
import com.mayikt.service.TestService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/testes")
public class TestController {

    @Autowired
    TestService testService;
    @Autowired
    TestDao testDao;

    @RequestMapping("findAll")
    public Iterable<TestBean> findAll() {
        return testService.findAll();
    }

    @RequestMapping("getAll1")
    public List<TestBean> getAll1(@RequestParam String name,
                                  @RequestParam int pageindex,
                                  @RequestParam int pageSize) {
        QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
        Iterable<TestBean> iterable = testDao.search(queryBuilder);
        List<TestBean> list = new ArrayList<>();
        iterable.forEach(e -> list.add(e));
        return list;
    }

    @RequestMapping("listPage")
    public PageVo<TestBean> listPage(@RequestParam(required = false) String name,
                           @RequestParam int pageIndex,
                           @RequestParam int pageSize) {
        //检索条件
        BoolQueryBuilder bqb = QueryBuilders.boolQuery();
        if (!StringUtils.isEmpty(name)) {
            bqb.must(QueryBuilders.matchPhraseQuery("name", name));
        }
        //排序条件
        FieldSortBuilder fsb = SortBuilders.fieldSort("id").order(SortOrder.ASC);
        //分页条件
        pageIndex = pageIndex == 0 ? 1 : pageIndex;
        pageSize = pageSize == 0 ? 10 : pageSize;
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
        //构建查询
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(bqb)
                .withSort(fsb)
                .withPageable(pageable)
                .build();
        Page<TestBean> searchResponse = testDao.search(query);
        return PageVo.pageResult(searchResponse);
    }

    @RequestMapping("saveList")
    public String saveList() {
        List<TestBean> list = null;
        testService.save(list);
        return "success";
    }

    @RequestMapping("save")
    public String save() {
        testService.save();
        return "success";
    }

    @RequestMapping("update")
    public String update() {
        testService.update();
        return "success";
    }

    @RequestMapping("getById")
    public TestBean getById(Long id) {
        return testService.getById(id);
    }

    @RequestMapping("deleteById")
    public String deleteById(Long id) {
        testService.deleteById(id);
        return "success";
    }

    @RequestMapping("findByName")
    public List<TestBean> findByName(String name) {
        return testService.findByName(name);
    }

    @RequestMapping("getByName")
    public List<TestBean> getByName(String name) {
        List<TestBean> list = new ArrayList<>();
        QueryBuilder queryBuilder = QueryBuilders.matchQuery("name", name); // 会分词
        Iterable<TestBean> iterable = testDao.search(queryBuilder);
        iterable.forEach(e -> list.add(e));
        return list;
    }

    @RequestMapping("getByNamePhrase")
    public List<TestBean> getByNamePhrase(String name) {
        List<TestBean> list = new ArrayList<>();
        QueryBuilder queryBuilder = QueryBuilders.matchPhraseQuery("name", name); // 不会分词
        Iterable<TestBean> iterable = testDao.search(queryBuilder);
        iterable.forEach(e->list.add(e));
        return list;
    }

    @RequestMapping("findByNameOrDesc")
    public List<TestBean> findByNameOrDesc(String text) {
        return testService.findByNameOrDesc(text);
    }

}