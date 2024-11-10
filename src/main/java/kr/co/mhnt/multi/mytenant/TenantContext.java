package kr.co.mhnt.multi.mytenant;

import java.util.Optional;

public class TenantContext {

    private static final ThreadLocal<String> currentTenant = new ThreadLocal<>();

    public static void setCurrentTenant(String tenant) {
        currentTenant.set(tenant);
    }

    public static String getCurrentTenant() {
        return Optional.ofNullable(currentTenant.get()).orElse("public");
    }

    public static void clear() {
        currentTenant.remove();
    }
}
