//package kr.co.mhnt.multi.tenant;
//
//import org.hibernate.boot.MetadataSources;
//import org.hibernate.boot.registry.StandardServiceRegistry;
//import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
//import org.hibernate.boot.spi.MetadataImplementor;
//import org.hibernate.tool.schema.spi.SchemaManagementToolCoordinator;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.env.Environment;
//import org.springframework.stereotype.Component;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * 엔티티 생성 컴포넌트
// */
//@Component
//public class SchemaGenerator {
//
//	static final Logger log = LoggerFactory.getLogger(SchemaGenerator.class);
//
//	@Autowired
//	private Environment env;
//
//	/**
//	 *
//	 * @param schema        생성 대상 스키마
//	 * @param entityClasses 생성 대상 엔티티 목록
//	 */
//	public void createEntities(String schema, List<Class<?>> entityClasses) {
//		// 어플리케이션 설정을 따르지 않으므로 아래 설정값들은 적절하게 yml 설정에서 로드하는 것이 좋음
//
//		// 생성대상 스키마는 직접 고정해야 함.
//		String url = env.getProperty("spring.datasource.url") + "&currentSchema=" + schema;
//		log.info("엔티티 자동생성, datasource.url: {}", url);
//
//		String username = env.getProperty("spring.datasource.username");
//		String password = env.getProperty("spring.datasource.password");
//		String driver = env.getProperty("spring.datasource.driver-class-name");
//		log.info("엔티티 자동생성, datasource.driver-class-name: {}", driver);
//
//		String dialect = env.getProperty("spring.jpa.properties.hibernate.dialect");
//		if(dialect == null)
//		{
//			dialect = "org.hibernate.dialect.PostgreSQLDialect";
//			log.info("spring.jpa.properties.hibernate.dialect 설정 없음, 코드상에서 자동으로 추가 처리, dialect: {}", dialect);
//		}
//
//		// Hibernate 서비스 레지스트리 구성 - Spring 환경 설정 사용
//		StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
//				.applySetting("hibernate.hbm2ddl.auto", "update")
//				.applySetting("hibernate.connection.driver_class", driver).applySetting("hibernate.connection.url", url)
//				.applySetting("hibernate.connection.username", username)
//				.applySetting("hibernate.connection.password", password)
//				.applySetting("hibernate.connection.autocommit", true).applySetting("hibernate.show_sql", true)
//				.applySetting("hibernate.format_sql", true).applySetting("hibernate.use_sql_comments", true)
//				.applySetting("hibernate.physical_naming_strategy",
//						"org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy")
//				.applySetting("hibernate.implicit_naming_strategy",
//						"org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy")
//				// .applySetting("hibernate.dialect", dialect)
//				.build();
//
//		// 메타데이터 소스 생성 및 엔티티 추가
//		MetadataSources metadataSources = new MetadataSources(serviceRegistry);
//		for (Class<?> entityClass : entityClasses) {
//			metadataSources.addAnnotatedClass(entityClass);
//		}
//
//		MetadataImplementor metadata = (MetadataImplementor) metadataSources.buildMetadata();
//		// 구성 값 설정 (필요 시 추가 옵션 설정 가능)
//		Map<String, Object> configurationValues = new HashMap<>();
//		configurationValues.put("hibernate.hbm2ddl.auto", "update");
//		configurationValues.put("hibernate.connection.driver_class", driver);
//		configurationValues.put("hibernate.connection.url", url);
//		configurationValues.put("hibernate.connection.username", username);
//		configurationValues.put("hibernate.connection.password", password);
//		configurationValues.put("hibernate.dialect", dialect);
//		configurationValues.put("hibernate.physical_naming_strategy",
//				"org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");
//		configurationValues.put("hibernate.implicit_naming_strategy",
//				"org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy");
//		configurationValues.put("hibernate.show_sql", "true"); // SQL 로그 활성화
//		configurationValues.put("hibernate.format_sql", "true"); // SQL 포맷팅 활성화
//		configurationValues.put("hibernate.use_sql_comments", "true"); // SQL 주석 활성화 (선택 사항)
//		configurationValues.put("hibernate.connection.autocommit", "true");
//
//		// 스키마 생성 및 적용
//		SchemaManagementToolCoordinator.process(metadata, serviceRegistry, configurationValues, null);
//
//		// 레지스트리 정리
//		StandardServiceRegistryBuilder.destroy(serviceRegistry);
//	}
//}
