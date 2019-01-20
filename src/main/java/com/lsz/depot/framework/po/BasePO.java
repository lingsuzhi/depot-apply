package com.lsz.depot.framework.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lsz.depot.core.utils.ThreadStore;
import com.lsz.depot.core.utils.UuidMd5;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
public class BasePO implements Serializable {
    private static final long serialVersionUID = 0x23161228;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, length = 22, updatable = false)
    private String uuid;

    @JsonIgnore
    @Column(nullable = false, length = 30, updatable = false)
    private String createBy;

    @JsonIgnore
    @Column(nullable = false, updatable = false)
    private LocalDateTime createDate;

    @JsonIgnore
    @Column(nullable = false, length = 30)
    private String updateBy;

    @JsonIgnore
    @Column(nullable = false)
    private LocalDateTime updateDate;

    public BasePO() {
        setCreateBy(ThreadStore.getUserId());
        setUpdateBy(ThreadStore.getUserId());
    }

    @PreUpdate
    public void preUpdate() {
        setUpdateBy(ThreadStore.getUserId());
        setUpdateDate(LocalDateTime.now());
    }

    @PrePersist
    public void prePersist() {
        if (this.uuid == null){
            setUuid(UuidMd5.uuidWith22Bit());
        }
        setCreateBy(ThreadStore.getUserId());
        setCreateDate(LocalDateTime.now());
        setUpdateDate(LocalDateTime.now());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        if (this.id == null && id != null) {
            this.id = id;
        }
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        if (createBy != null) {
            this.createBy = createBy;
        }
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setCreateDate(LocalDateTime createDate) {
        if (createDate != null) {
            this.createDate = createDate;
        }
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        if (updateBy != null) {
            this.updateBy = updateBy;
        }
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        if (updateDate != null) {
            this.updateDate = updateDate;
        }
    }
}