package com.lck.blog.dao;

import com.lck.blog.po.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by limi on 2020/09/21.
 */
public interface TypeRepository extends JpaRepository<Type,Long> {

    //查詢Type
    Type findByName(String name);


    //@Query自定義查詢
    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);
}
