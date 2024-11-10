package kr.co.mhnt.multi.common.aop;

import jakarta.persistence.EntityManager;
import kr.co.mhnt.multi.mytenant.TenantContext2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
public class TenantFilterAspect {

    private final EntityManager entityManager;

    @Around("""
        @within(org.springframework.transaction.annotation.Transactional)
        || @annotation(org.springframework.transaction.annotation.Transactional)
        && execution(* kr.co.mhnt.multi..*(..))
        """)
    public Object applyTenantFilter(ProceedingJoinPoint joinPoint) throws Throwable {
        Session session = entityManager.unwrap(Session.class);
        String tenantId = TenantContext2.getCurrentTenant();
        log.info("### TenantFilterAspect tenantId = {}", tenantId);
        if (tenantId != null) {
            session.enableFilter("tenantFilter").setParameter("tenantId", tenantId);
        }
        try {
            return joinPoint.proceed();
        } finally {
            session.disableFilter("tenantFilter"); // 명시 목적
        }
    }
}
