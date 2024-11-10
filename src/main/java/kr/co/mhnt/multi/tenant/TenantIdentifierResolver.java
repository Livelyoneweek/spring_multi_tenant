//package kr.co.mhnt.multi.tenant;
//
//import org.apache.commons.lang3.StringUtils;
//import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
//
//public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver<String> {
//
//	static final String DEFAULT_TENANT = "public";
//
//	@Override
//	public String resolveCurrentTenantIdentifier() {
//		// 현재 테넌트 ID를 세션이나 컨텍스트에서 가져옴
//		String tenantId = TenantContext.getCurrentTenant();
//		return StringUtils.isEmpty(tenantId) ? DEFAULT_TENANT : tenantId;
//	}
//
//	@Override
//	public boolean validateExistingCurrentSessions() {
//		return true;
//	}
//}