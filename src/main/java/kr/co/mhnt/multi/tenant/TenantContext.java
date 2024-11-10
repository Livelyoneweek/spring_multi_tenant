//package kr.co.mhnt.multi.tenant;
//
//public class TenantContext {
//
//	private static final ThreadLocal<String> CURRENT_TENANT = new ThreadLocal<>();
//
//	/**
//	 * 요청 클라이언트 테넌트 설정
//	 *
//	 * 필터와 인터셉터 둘다 존재할 경우 필터에서 적용하는 것을 권장
//	 * @param tenantId
//	 */
//	public static void setCurrentTenant(String tenantId) {
//		CURRENT_TENANT.set(tenantId);
//	}
//
//	/**
//	 * Req에 대한 현재 테넌트 ID 조회
//	 *
//	 *
//	 * @return
//	 */
//	public static String getCurrentTenant() {
//		return CURRENT_TENANT.get();
//	}
//
//	/**
//	 *
//	 * 반드시 HandlerInterceptor.afterCompletion 에서 호출해야 함
//	 */
//	public static void clear() {
//		CURRENT_TENANT.remove();
//	}
//}
