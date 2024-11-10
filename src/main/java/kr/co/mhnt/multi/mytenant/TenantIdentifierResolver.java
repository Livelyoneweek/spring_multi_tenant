//package kr.co.mhnt.multi.mytenant;
//
//import org.hibernate.cfg.AvailableSettings;
//import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
//import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//
//@Component
//class TenantIdentifierResolver implements CurrentTenantIdentifierResolver, HibernatePropertiesCustomizer {
//
//    private String currentTenant = "public";
//
//    public void setCurrentTenant(String tenant) {
//        currentTenant = tenant;
//    }
//
//    @Override
//    public String resolveCurrentTenantIdentifier() {
//        return currentTenant;
//    }
//
//    @Override
//    public @org.checkerframework.checker.nullness.qual.UnknownKeyFor @org.checkerframework.checker.nullness.qual.NonNull @org.checkerframework.checker.initialization.qual.Initialized boolean validateExistingCurrentSessions() {
//        return false;
//    }
//
//    @Override
//    public @org.checkerframework.checker.nullness.qual.UnknownKeyFor @org.checkerframework.checker.nullness.qual.NonNull @org.checkerframework.checker.initialization.qual.Initialized boolean isRoot(Object tenantId) {
//        return CurrentTenantIdentifierResolver.super.isRoot(tenantId);
//    }
//
//    @Override
//    public void customize(Map<String, Object> hibernateProperties) {
//        hibernateProperties.put(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER, this);
//    }
//
//    // empty overrides skipped for brevity
//}
