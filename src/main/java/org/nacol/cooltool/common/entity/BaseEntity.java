package org.nacol.cooltool.common.entity;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;


@Getter
@Accessors(chain = true)
public class BaseEntity<T> {

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private Long id;

    private Long tenantId;

    private String createUserId;

    private String createUserName;

    private LocalDateTime createTime;

    private String modifyUserId;

    private String modifyUserName;

    private LocalDateTime modifyTime;

    public Long getId() {
        return id;
    }

    public T setId(Long id) {
        this.id = id;
        return (T)this;
    }

    public T setTenantId(Long tenantId) {
        this.tenantId = tenantId;
        return (T)this;
    }

    public T setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
        return (T)this;
    }

    public T setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
        return (T)this;
    }

    public T setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return (T)this;
    }

    public T setModifyUserId(String modifyUserId) {
        this.modifyUserId = modifyUserId;
        return (T)this;
    }

    public T setModifyUserName(String modifyUserName) {
        this.modifyUserName = modifyUserName;
        return (T)this;
    }

    public T setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
        return (T)this;
    }

    public String toJSONString(){
        return JSON.toJSONString(this);
    }

}
