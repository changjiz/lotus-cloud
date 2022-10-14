package com.lotus.framework.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: changjiz
 * @create: 2019-10-08 16:31
 **/
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = -4319379533348826292L;

    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    @BaseIdJson
    protected Long id;
    /**
     * 创建者
     */
    @BaseIdJson
    protected Long creator;
    /**
     * 创建时间
     */
    protected Date gmtCreated;
    /**
     * 修改者
     */
    @BaseIdJson
    protected Long modifier;
    /**
     * 修改时间
     */
    protected Date gmtModified;
    /**
     * 是否删除
     * 标记逻辑删除属性 // value 默认未删除，deval 删除了
     */
    @TableLogic(value = "0", delval = "1")
    @JsonIgnore
    protected Integer isDeleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Long getModifier() {
        return modifier;
    }

    public void setModifier(Long modifier) {
        this.modifier = modifier;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }


}
