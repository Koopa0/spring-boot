package com.lck.blog.service;

import com.lck.blog.NotFoundException;
import com.lck.blog.dao.TypeRepository;
import com.lck.blog.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeRepository typeRepository;

    //增刪改查都放在事務裡面
    @Transactional
    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    //增刪改查都放在事務裡面
    @Transactional
    @Override
    public Type getType(Long id) {
        //根據id來查詢Type
        return typeRepository.findOne(id);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }

    //增刪改查都放在事務裡面
    @Transactional
    @Override
    //分頁查詢
    public Page<Type> listType(Pageable pageable) {
        //springboot中的JPA封裝好的方法 傳遞參數pageable時會給一個封裝Page<Type>對象
        return typeRepository.findAll(pageable);
    }

    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }


    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC,"blogs.size");
        Pageable pageable = new PageRequest(0,size,sort);
        return typeRepository.findTop(pageable);
    }

    //增刪改查都放在事務裡面
    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type t = typeRepository.findOne(id);
        if (t == null) {
            throw new NotFoundException("不存在該類型");
        }
        //把type的值複製到t裡面
        BeanUtils.copyProperties(type,t);
        return typeRepository.save(t);
    }


    //增刪改查都放在事務裡面
    @Transactional
    @Override
    public void deleteType(Long id) {
        //根據id來刪除
        typeRepository.delete(id);
    }
}
