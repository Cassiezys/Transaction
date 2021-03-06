package com.cassiezys.transaction.controller;

import com.cassiezys.transaction.dto.CommentDTO;
import com.cassiezys.transaction.dto.CreateCommentDTO;
import com.cassiezys.transaction.dto.ResultDTO;
import com.cassiezys.transaction.enums.CommentTypeEnum;
import com.cassiezys.transaction.exception.ErrorCodeEnumImp;
import com.cassiezys.transaction.model.Comment;
import com.cassiezys.transaction.model.User;
import com.cassiezys.transaction.service.CommentService;
import com.cassiezys.transaction.service.NotificationService;
import com.cassiezys.transaction.service.OperateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Copyright，2020
 * Author:曾念念
 * Description:ajax评论
 */
@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private OperateService operateService;
    @Autowired
    private NotificationService notificationService;

    /**
     * 写评论
     * @param createCommentDTO 前端传递到后端的数据
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object doComment(@RequestBody CreateCommentDTO createCommentDTO,
                     HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            return ResultDTO.errorOf(ErrorCodeEnumImp.NO_LOGIN);
        }
        if (createCommentDTO == null || StringUtils.isBlank(createCommentDTO.getContent())){
            return ResultDTO.errorOf(ErrorCodeEnumImp.COTENT_IS_EMPTY);
        }
        Comment comment = new Comment();
        BeanUtils.copyProperties(createCommentDTO, comment);
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0);
        comment.setCommentCount(0);
        commentService.addComment(comment,user);


        return ResultDTO.successOf();
    }

    /**
     * 找到二级评论
     * @param id parentId = commentid
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/comment/{id}")
    public ResultDTO<List<CommentDTO>> commentList(@PathVariable(name = "id")Long id){
        List<CommentDTO> commentdtos = commentService.findByTargetId(null,id, CommentTypeEnum.COMMENT);
        return ResultDTO.successOf(commentdtos);
    }

    /**
     * 点赞评论
     * @param cid 评论id
     * @param status 评论是点赞还是不点赞的状态
     * @param request
     * @return
     */
    @ResponseBody
    @GetMapping("/comment/modifyThumbs/{cid}/{status}")
    public Object modifyThumbs(@PathVariable(name = "cid")Long cid,
                              @PathVariable(name = "status")int status,
                              HttpServletRequest request){
        User user=(User)request.getSession().getAttribute("user");
        if(user==null){
            return ResultDTO.errorOf(ErrorCodeEnumImp.NO_LOGIN);
        }
        operateService.modifyThumbs(cid,user,status);
        return ResultDTO.successOf();
    }
}
