package org.fourstack.cards.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@ToString
public abstract class BaseEntity {
    @Column(updatable = false, nullable = false)
    @CreatedBy
    private String createdBy;

    @Column(updatable = false, nullable = false)
    @CreatedDate
    private LocalDateTime creationTimestamp;

    @Column(insertable = false)
    @LastModifiedBy
    private String updatedBy;

    @Column(insertable = false)
    @LastModifiedDate
    private LocalDateTime updatedTimestamp;
}
