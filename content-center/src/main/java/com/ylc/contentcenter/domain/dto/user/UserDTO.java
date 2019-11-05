package com.ylc.contentcenter.domain.dto.user;

import lombok.Data;

import java.util.Date;

/**
 * @Description: TODO
 * @Date: 2019/9/15
 */
@Data
public class UserDTO {

    private Integer id;

    /**
     * 微信id
     */
    private String wxId;

    /**
     * 微信昵称
     */
    private String wxNickname;

    /**
     * 角色
     */
    private String roles;

    /**
     * 头像地址
     */
    private String avatarUrl;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */

    private Date updateTime;

    /**
     * 积分
     */
    private Integer bonus;
}
