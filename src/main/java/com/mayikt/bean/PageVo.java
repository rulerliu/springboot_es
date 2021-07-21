package com.mayikt.bean;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class PageVo<T> {
    private Integer current;
    private Integer size;
    private Long total;
    private Integer totalPage;
    private List<T> data;

    public static PageVo pageResult(Page page) {
        PageVo pageVo = new PageVo();
        pageVo.setCurrent(page.getNumber() + 1);
        pageVo.setSize(page.getSize());
        pageVo.setTotal(page.getTotalElements());
        pageVo.setTotalPage(page.getTotalPages());
        pageVo.setData(page.getContent());
        return pageVo;
    }
}
