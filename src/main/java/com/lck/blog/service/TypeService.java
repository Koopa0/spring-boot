package com.lck.blog.service;

import com.lck.blog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface TypeService {

    //保存Type
    Type saveType(Type type);

    //查詢Type
    Type getType(Long id);

    //通過名稱來查詢Type
    Type getTypeByName(String name);

    //分頁查詢Type
    Page<Type> listType(Pageable pageable);

    //返回一個List 有Type的所有數據
    List<Type> listType();

    List<Type> listTypeTop(Integer size);

    //修改Type 先根據id查詢 再用新的內容去修改
    Type updateType(Long id,Type type);

    //根據id刪除Type
    void deleteType(Long id);
}
