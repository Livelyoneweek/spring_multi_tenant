//package kr.co.mhnt.multi.tenant;
//
//import kr.co.mhnt.multi.MultiApplication;
//import kr.co.mhnt.multi.mytenant.TenantIdentifierResolver;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.Map;
//
//// @Configuration
//public class HibernateConfig {
//	@Value("${spring.jpa.hibernate.ddl-auto:validate}")
//	private String ddlAuto;
//
////	@Bean
//	LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
//		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
//
//		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//		em.setDataSource(dataSource);
//		em.setPackagesToScan(MultiApplication.class.getPackageName()); // 엔티티 패키지 설정
//		em.setJpaVendorAdapter(adapter);
//
//		Map<String, Object> properties = new HashMap<>();
//		// 스키마 로 멀티테넌시 설정
//		properties.put("hibernate.multiTenancy", "SCHEMA");
//		properties.put("hibernate.multi_tenant_connection_provider",
//				new SchemaBasedMultiTenantConnectionProvider(dataSource));
//		properties.put("hibernate.hbm2ddl.auto", ddlAuto);
//		properties.put("hibernate.tenant_identifier_resolver", new TenantIdentifierResolver());
//		properties.put("hibernate.physical_naming_strategy",
//				"org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");
//		properties.put("hibernate.implicit_naming_strategy",
//				"org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy");
//
//		em.setJpaPropertyMap(properties);
//		return em;
//	}
//}