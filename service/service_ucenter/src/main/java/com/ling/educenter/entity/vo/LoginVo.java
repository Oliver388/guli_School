package com.ling.educenter.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


//登录对象
@Data
@ApiModel(value = "登陆对象",description = "登陆对象")
public class LoginVo {

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("密码")
    private String password;

}
