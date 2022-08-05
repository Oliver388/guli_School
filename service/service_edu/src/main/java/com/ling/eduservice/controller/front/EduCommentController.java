package com.ling.eduservice.controller.front;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ling.commonutils.JwtUtils;
import com.ling.commonutils.R;
import com.ling.commonutils.vo.UcenterMemberVo;
import com.ling.eduservice.client.UcenterClient;
import com.ling.eduservice.entity.EduComment;
import com.ling.eduservice.service.EduCommentService;
import com.ling.servicebase.exceptionhandler.GuliException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Oliver
 * @since 2022-08-04
 */
@RestController
@RequestMapping("/eduservice/comment")
@CrossOrigin
public class EduCommentController {

    @Autowired
    private EduCommentService commentService;

    @Autowired
    private UcenterClient ucenterClient;

    //根据课程id分页查询课程评论的方法
    @ApiOperation(value = "评论分页列表")
    @GetMapping("{page}/{limit}")
    public R getCommentPage(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
            String courseId){
        Page<EduComment> pageParam = new Page<>(page, limit);

        LambdaQueryWrapper<EduComment> wrapper = new LambdaQueryWrapper<>();

        //判断课程id是否为空
        if (!StringUtils.isEmpty(courseId)){
            wrapper.eq(EduComment::getCourseId,courseId);
        }

        //按最新排序
        wrapper.orderByDesc(EduComment::getGmtCreate);

        //数据会被封装到pageParam中
        commentService.page(pageParam,wrapper);

        List<EduComment> list = pageParam.getRecords();

        Map<String, Object> map = new HashMap<>();
        map.put("items", list);
        map.put("current", pageParam.getCurrent());
        map.put("pages", pageParam.getPages());
        map.put("size", pageParam.getSize());
        map.put("total", pageParam.getTotal());
        map.put("hasNext", pageParam.hasNext());
        map.put("hasPrevious", pageParam.hasPrevious());
        return R.ok().data(map);
    }

    //添加评论
    @ApiOperation(value = "添加评论")
    @PostMapping("auth/save")
    public R save(@RequestBody EduComment comment, HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);

        //判断用户是否登录
        if (StringUtils.isEmpty(memberId)){
            throw new GuliException(20001,"请先登录");
        }
        comment.setMemberId(memberId);

        //远程调用ucenter根据用户id获取用户信息
        UcenterMemberVo memberVo = ucenterClient.getMemberInfoById(memberId);

        comment.setAvatar(memberVo.getAvatar());
        comment.setNickname(memberVo.getNickname());

        //保存评论
        commentService.save(comment);

        return R.ok();

    }

}

