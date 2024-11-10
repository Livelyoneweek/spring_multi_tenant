package kr.co.mhnt.multi.mytenant;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

// 테넌트별로 별도 디비일때 사용
//public class TenantRoutingDataSource extends AbstractRoutingDataSource {
//    @Override
//    protected Object determineCurrentLookupKey() {
//        return TenantContext.getCurrentTenant();
//    }
//}
