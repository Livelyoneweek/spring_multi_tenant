//package kr.co.mhnt.multi.tenant;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.Table;
//import jakarta.persistence.metamodel.EntityType;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//
///**
// * 엔티티 생성 서비스
// *
// * 매우 민감하므로 함부로 사용하지 말것.
// */
//@Service
//public class TenantService {
//	static final Logger log = LoggerFactory.getLogger(TenantService.class);
//
//	@Autowired
//	private EntityManager entityManager;
//
//	@Autowired
//	private SchemaGenerator schemaGenerator;
//
//	/**
//	 * 스키마가 정의되지 않은 엔티티 리스트 목록 조회
//	 *
//	 * @return
//	 */
//	public List<Class<?>> getSiteEntities() {
//		Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
//
//		List<Class<?>> siteEntities = new ArrayList<>();
//
//		for (EntityType<?> entityType : entities) {
//			Class<?> entityClass = entityType.getJavaType();
//			if (entityClass.isAnnotationPresent(Table.class)) {
//				Table tableAnnotation = entityClass.getAnnotation(Table.class);
//				String tableSchema = tableAnnotation.schema();
//
//				if (tableSchema != null && !tableSchema.isEmpty()) {
//					log.info("Entity: " + entityClass.getSimpleName() + " - Schema: " + tableSchema);
//				} else {
//					log.info("Entity: " + entityClass.getSimpleName() + " - No schema specified");
//					siteEntities.add(entityClass);
//				}
//			} else {
//				log.info("Entity: " + entityClass.getSimpleName() + " - No @Table annotation");
//			}
//		}
//
//		return siteEntities;
//	}
//
//	/**
//	 * 엔티티 자동생성
//	 *
//	 * 자동생성은 update 방식으로 진행함.
//	 *
//	 * @param schema
//	 */
//	@Transactional
//	public void createEntities(String schema) {
//		List<Class<?>> entities = getSiteEntities();
//		schemaGenerator.createEntities(schema, entities);
//	}
//
//	/**
//	 * 스키마 생성
//	 *
//	 * 주의사항. 스키마 생성 후 엔티티 생성을 호출할 경우 정상 동작하지 않음. createEntities는 개별 API로 호출할 것
//	 *
//	 * @param schemaName
//	 */
//	@Transactional
//	public void checkAndCreateSchema(String schemaName) {
//		// 스키마 존재 여부 확인 쿼리 실행
//		String schemaCheckQuery = "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = :schemaName";
//		boolean schemaExists = !entityManager.createNativeQuery(schemaCheckQuery).setParameter("schemaName", schemaName)
//				.getResultList().isEmpty();
//
//		if (!schemaExists) {
//			// 스키마가 없으면 생성 쿼리 실행
//			String createSchemaQuery = "CREATE SCHEMA " + schemaName;
//			entityManager.createNativeQuery(createSchemaQuery).executeUpdate();
//		}
//
//	}
//
//}
