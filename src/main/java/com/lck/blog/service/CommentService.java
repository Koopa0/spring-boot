package com.lck.blog.service;

import com.lck.blog.po.Comment;

import java.util.List;


public interface CommentService {

    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);


}
