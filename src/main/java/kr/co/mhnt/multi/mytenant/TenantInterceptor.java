package kr.co.mhnt.multi.mytenant;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
@RequiredArgsConstructor
public class TenantInterceptor implements HandlerInterceptor {

    @PersistenceContext
    private EntityManager entityManager;

//    private final TenantIdentifierResolver tenantIdentifierResolver;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String tenantId = request.getHeader("X-TenantID");
        log.info("### tenantId = {}", tenantId);
        if (tenantId != null) {
            TenantContext2.setCurrentTenant(tenantId);
//            tenantIdentifierResolver.setCurrentTenant(tenantId);
//            Session session = entityManager.unwrap(Session.class);
//            session.enableFilter("tenantFilter").setParameter("tenantId", tenantId);

            // 추가한 코드
//            Filter filter = session.getEnabledFilter("tenantFilter");
//            if (filter != null) {
//                log.info("### filter name = {}", filter.getName());
//            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex){
//        tenantIdentifierResolver.setCurrentTenant("public");
    }
}
