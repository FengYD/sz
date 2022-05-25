package org.example.szsubway.model.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author fengyadong
 * @date 2022/5/25 17:26
 * @Description
 */
@Data
public class BaseDomain {

    private Long id;

    private Integer isDelete;

    private Date createTime;

    private Date updateTime;

}
