package com.example.project.entity;

import com.example.project.util.LocalDateTimeDeserializer;
import com.example.project.util.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen")
    @Column(name = "id")
    private Long id;

    @CreatedDate
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createTime;

    @CreatedBy
    @Column(name = "created_by", updatable =  false)
    private String createdBy;

    @LastModifiedDate
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column(name = "modified_at")
    private LocalDateTime modifiedTime;

    @LastModifiedBy
    @Column(name = "modified_by")
    private String modifiedBy;

    @PrePersist
    public void onCreate() {
        this.createTime = LocalDateTime.now();
        this.modifiedTime = LocalDateTime.now();
        String userDetails = SecurityContextHolder.getContext().getAuthentication().getName();
        this.createdBy = userDetails;
        this.modifiedBy = userDetails;
    }

    @PreUpdate
    public void onUpdate() {
        this.modifiedTime = LocalDateTime.now();
        try {
            this.modifiedBy = SecurityContextHolder.getContext().getAuthentication().getName();
        } catch (NullPointerException ex) {
            this.modifiedBy = "bot";
        }
    }
}
