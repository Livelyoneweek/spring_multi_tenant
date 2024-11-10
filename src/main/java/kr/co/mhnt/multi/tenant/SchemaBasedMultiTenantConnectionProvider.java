//package kr.co.mhnt.multi.tenant;
//
//import org.checkerframework.checker.initialization.qual.Initialized;
//import org.checkerframework.checker.nullness.qual.NonNull;
//import org.checkerframework.checker.nullness.qual.UnknownKeyFor;
//import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.sql.DataSource;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.sql.Statement;
//
///**
// * 역할
// * 1. 테넌트별 데이터베이스 스키마 관리: 각 테넌트에 대해 적절한 데이터베이스 연결을 제공하고, 해당 연결이 올바른 스키마를 사용하도록 설정합니다.
// * 2. Hibernate 와의 통합: Hibernate 의 멀티 테넌시 기능과 연동하여 ORM 작업이 테넌트별로 올바르게 수행되도록 합니다.
// */
//public class SchemaBasedMultiTenantConnectionProvider implements MultiTenantConnectionProvider<String> {
//
//	static final Logger log = LoggerFactory.getLogger(SchemaBasedMultiTenantConnectionProvider.class);
//	/**
//	 *
//	 */
//	private static final long serialVersionUID = -8881432131279715895L;
//
//	private final DataSource datasource;
//
//	public SchemaBasedMultiTenantConnectionProvider(DataSource dataSource) {
//		this.datasource = dataSource;
//	}
//
//	// 임의의 연결 획득: 테넌트에 종속되지 않은 일반적인 연결을 반환합니다.
//	@Override
//	public Connection getAnyConnection() throws SQLException {
//		return datasource.getConnection();
//	}
//
//	// 임의의 연결 해제: 사용한 연결을 닫습니다.
//	@Override
//	public void releaseAnyConnection(Connection connection) throws SQLException {
//		connection.close();
//	}
//
//	@Override
//	public Connection getConnection(String tenantIdentifier) throws SQLException {
//		final String currentTenant = TenantContext.getCurrentTenant();
//		if (!tenantIdentifier.equals(TenantContext.getCurrentTenant())) {
//			log.error("요청된 테넌트 ID가 현재 TenantContext와 일치하지 않습니다. tenantIdentifier: {}, currentTenant: {}",
//					tenantIdentifier, currentTenant);
//			throw new SQLException("Requested tenant ID does not match current TenantContext.");
//		}
//
//		Connection connection = getAnyConnection();
//		try (Statement stat = connection.createStatement()) {
//
//			final String tenantSchema = "SET search_path TO " + tenantIdentifier;
//			stat.execute(tenantSchema);
//			log.info("스키마 변경, Schema: {}", tenantIdentifier);
//		} catch (SQLException e) {
//			log.error("스키마 변경 오류, Schema: {}", tenantIdentifier);
//			throw new SQLException("Failed to set schema to " + tenantIdentifier, e);
//		}
//		return connection;
//
//	}
//
//	@Override
//	public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
//		try (Statement stat = connection.createStatement()) {
//			stat.execute("SET search_path TO " + TenantIdentifierResolver.DEFAULT_TENANT);
//			log.info("스키마 초기화, Schema: {}", tenantIdentifier);
//		} catch (SQLException e) {
//			log.error("스키마 변경 오류, Schema: {}", tenantIdentifier);
//			throw new SQLException("Failed to reset schema to public", e);
//		} finally {
//			releaseAnyConnection(connection);
//		}
//
//	}
//
//	@Override
//	public boolean supportsAggressiveRelease() {
//		return false;
//	}
//
//	@Override
//	public @UnknownKeyFor @NonNull @Initialized boolean isUnwrappableAs(
//			@UnknownKeyFor @NonNull @Initialized Class<@UnknownKeyFor @NonNull @Initialized ?> unwrapType) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public <T> @UnknownKeyFor @NonNull @Initialized T unwrap(
//			@UnknownKeyFor @NonNull @Initialized Class<@UnknownKeyFor @NonNull @Initialized T> unwrapType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//}