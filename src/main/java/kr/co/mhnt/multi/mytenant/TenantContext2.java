package kr.co.mhnt.multi.mytenant;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public class TenantContext2 {

    private static final String TENANT_KEY = "CURRENT_TENANT_ID";

    public static void setCurrentTenant(String tenantId) {
        RequestContextHolder.currentRequestAttributes().setAttribute(TENANT_KEY, tenantId, RequestAttributes.SCOPE_REQUEST);
    }

    public static String getCurrentTenant() {
        return (String) RequestContextHolder.currentRequestAttributes().getAttribute(TENANT_KEY, RequestAttributes.SCOPE_REQUEST);
    }

}
