package kr.co.mhnt.multi.user.entity;

import jakarta.persistence.*;
import kr.co.mhnt.multi.common.entity.TrackingEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "tb_user_m")
@FilterDef(name = "tenantFilter", parameters = @ParamDef(name = "tenantId", type = String.class))
@Filter(name = "tenantFilter", condition = "tenant_id = :tenantId")
public class User extends TrackingEntity {

    public static final int MAX_USERNAME_LENGTH = 255;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

//    @TenantId
    @Column(name = "tenant_id")
    private String tenantId;

    @Column(nullable = false, length = MAX_USERNAME_LENGTH)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String roles;

    @Column(nullable = false)
    private String mobile;



    public User(String username, String password, String roles, String mobile) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.mobile = mobile;
    }
}
