package com.lsz.depot.framework.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lsz.depot.core.utils.ThreadStore;
import com.lsz.depot.core.utils.UuidMd5;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
public class BaseNoInfoPO implements Serializable {
    private static final long serialVersionUID = 0x23161221;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id;

    @JsonIgnore
    @Column(nullable = false, updatable = false)
    private LocalDateTime createDate;

    @PrePersist
    public void prePersist() {

        setCreateDate(LocalDateTime.now());

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        if (this.id == null && id != null) {
            this.id = id;
        }
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        if (createDate != null) {
            this.createDate = createDate;
        }
    }
}