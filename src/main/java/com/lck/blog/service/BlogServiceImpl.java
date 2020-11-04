package com.lck.blog.service;

import com.lck.blog.NotFoundException;
import com.lck.blog.dao.BlogRepository;
import com.lck.blog.po.Blog;
import com.lck.blog.po.Type;
import com.lck.blog.util.MarkdownUtils;
import com.lck.blog.util.MyBeanUtils;
import com.lck.blog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;


@Service
public class BlogServiceImpl implements BlogService {


    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Blog getBlog(Long id) {
        //使用findOne方法傳遞一個主鍵,查詢出一個對象返回
        return blogRepository.findOne(id);
    }

    //增刪改查都放在事務裡面
    @Transactional
    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogRepository.findOne(id);
        if (blog == null) {
            throw new NotFoundException("該頁面不存在");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));

        blogRepository.updateViews(id);
        return b;
    }


    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                //根據標題來查詢,如果標題不為空(非空判斷),!""因為是字串查詢
                if (!"".equals(blog.getTitle()) && blog.getTitle() != null) {
                    //like查詢條件表達式(模糊查詢)
                    predicates.add(cb.like(root.<String>get("title"), "%"+blog.getTitle()+"%"));
                }
                //根據typeid來查詢,不用!""是因為查詢long類型(typeid),cb.equal是否相等的判斷
                if (blog.getTypeId() != null) {
                    //root.<Type>get("type")等於拿到一個Type類型的type對象,get("id")指拿到blog對象裡面的type裡的id
                    predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                //根據是否推薦來查詢,所以是boolean類型 不選是false,選擇是true
                if (blog.isRecommend()) {
                    //,cb.equal是否相等的判斷,
                    predicates.add(cb.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }
                //cq.where方法相當於SQL的where語句,裡面的參數要傳遞一個條件的陣列 所以要用toArray把list轉成陣列 ,陣列裡面指定大小predicates.size()
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }
    //toPredicate方法是用來處理動態查詢的條件,Root<Blog> root代表要查詢的對象(Blog) 把Blog映射成一個Root,可以從中獲取表的字段,屬性,屬性值
    //CriteriaQuery是一個查詢條件的容器,可以放置查詢條件
    //CriteriaBuilder用來設置具體某個條件的表達式 EX:條件相等以及模糊條件
    //建立一個List放置組合條件

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join join = root.join("tags");
                return cb.equal(join.get("id"),tagId);
            }
        },pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepository.findByQuery(query,pageable);
    }

    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC,"updateTime");
        Pageable pageable = new PageRequest(0, size, sort);
        return blogRepository.findTop(pageable);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogRepository.findGroupYear();
        Map<String, List<Blog>> map = new HashMap<>();
        for (String year : years) {
            map.put(year, blogRepository.findByYear(year));
        }
        return map;
    }

    @Override
    public Long countBlog() {
        return blogRepository.count();
    }


    //增刪改查都放在事務裡面
    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        //如果找不到ID代表是新增
        if (blog.getId() == null) {
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);//瀏覽次數
            //否則只要更新時間就好
        } else {
            blog.setUpdateTime(new Date());
        }
        return blogRepository.save(blog);
    }

    //增刪改查都放在事務裡面
    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        //先查詢
        Blog b = blogRepository.findOne(id);
        //如果查出的對象為空拋出異常
        if (b == null) {
            throw new NotFoundException("該頁面不存在");
        }
        //如果存在,把查詢出來的對象複製給b,並且過濾屬性值為空的陣列
        BeanUtils.copyProperties(blog,b, MyBeanUtils.getNullPropertyNames(blog));
        b.setUpdateTime(new Date());
        return blogRepository.save(b);
    }

    //增刪改查都放在事務裡面
    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.delete(id);
    }
}
