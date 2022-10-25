package com.jinsim.springboilerplate.global.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
// 공통 매핑 정보가 필요할 때 사용한다.
// JPA Entity 클래스들이 BaseTimeEntity를 상속할 경우, 필드들도 컬럼으로 인식하도록 설정
// @MappedSuperclass가 붙은 클래스는 엔티티도 아니고, 테이블 매핑도 안된다.
// 참고로 엔티티는 엔티티(@Entity)나 @MappedSuperclass로 지정한 클래스만 상속이 가능
@EntityListeners(AuditingEntityListener.class)
// BaseTimeEntity에 Auditing 기능을 포함시킨다.
// Audit이란, Spring Date JPA에서 시간에 대해 자동으로 값을 넣어주는 기능
public abstract class BaseTimeEntity {
    // 모든 Entity의 상위 클래스가 되어, createDate와 modifiedDate를 자동 관리한다.
    // 직접 생성해서 사용할 일이 없으므로 추상 클래스로 사용을 권장한다.


    @CreatedDate
    // Entity가 생성되어 저장될 때 시간이 자동 저장된다.
    @Column(updatable = false)
    // update 시에 Null 되는 경우 방지
    // @DateTimeFormat(pattern = "yyyy-MM-dd/HH:mm:ss")
    private LocalDateTime createDate;

    @LastModifiedDate
    // 조회한 Entity의 값을 변경할 때 시간이 자동 저장된다.
    private LocalDateTime modifiedDate;
}