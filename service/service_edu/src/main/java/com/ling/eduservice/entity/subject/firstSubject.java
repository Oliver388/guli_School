package com.ling.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

//一级分类
@Data
public class firstSubject {
    private String id;
    private String title;

    //一级分类有多个二级分类
    private List<secondSubject> children = new ArrayList<>();
}
