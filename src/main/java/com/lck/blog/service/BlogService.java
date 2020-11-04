package com.lck.blog.service;

import com.lck.blog.po.Blog;
import com.lck.blog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;


public interface BlogService {

    //根據id來查詢一個文章
    Blog getBlog(Long id);

    Blog getAndConvert(Long id);

    //返回一個Blog類型的Page對象 傳遞pageable和查詢的參數
    Page<Blog> listBlog(Pageable pageable,BlogQuery blog);

    Page<Blog> listBlog(Pageable pageable);

    Page<Blog> listBlog(Long tagId,Pageable pageable);

    Page<Blog> listBlog(String query,Pageable pageable);

    List<Blog> listRecommendBlogTop(Integer size);

    Map<String,List<Blog>> archiveBlog();

    Long countBlog();

    //新增一個文章
    Blog saveBlog(Blog blog);

    //修改一個文章
    Blog updateBlog(Long id,Blog blog);

    //刪除一個文章 定義一個主鍵來刪除一個文章
    void deleteBlog(Long id);
}
