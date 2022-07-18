package com.javatechie.crud.example.entity;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Audited
@Data
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditingEntity extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;


    @CreatedDate
    @Column(name = "created_date", nullable = false)
//    @JsonIgnore
    private Instant createdDate = Instant.now();

   

    @LastModifiedDate
    @Column(name = "last_modified_date")
//    @JsonIgnore
    private Instant lastModifiedDate = Instant.now();


}
