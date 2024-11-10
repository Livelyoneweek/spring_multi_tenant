package kr.co.mhnt.multi.common.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@ToString
@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass
@Getter @Setter
public abstract class TrackingEntity {

    @Comment("생성자")
    @CreatedBy
    private UUID createdBy;

    @Comment("생성일자")
    @CreatedDate
    private LocalDateTime createdDate;

    @Comment("수정자")
    @LastModifiedBy
    private UUID lastModifiedBy;

    @Comment("수정일자")
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    public static final Sort sortCreateDateAsc = Sort.by(Sort.Direction.ASC, "createdDate");
    public static final Sort sortCreatedByAsc = Sort.by(Sort.Direction.ASC, "createdBy");
    public static final Sort sortLastModifiedDateAsc = Sort.by(Sort.Direction.ASC, "lastModifiedDate");
    public static final Sort sortLastModifiedByAsc = Sort.by(Sort.Direction.ASC, "lastModifiedBy");

}
