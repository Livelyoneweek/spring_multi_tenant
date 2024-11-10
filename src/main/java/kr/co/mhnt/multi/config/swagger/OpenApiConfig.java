package kr.co.mhnt.multi.config.swagger;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.core.jackson.TypeNameResolver;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

import java.util.Arrays;
import java.util.Collections;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class OpenApiConfig {

	private final ObjectMapper objectMapper;

	@Bean
	OpenAPI openAPI(@Value("${springdoc.version}") String springdocVersion) {
		Server localServer = new Server().url("http://localhost:30081");

		Info info = new Info().title("UAE API Document").version(springdocVersion)
				.description("""
						# API Document for MY multi tenant API Service
						""");

		return new OpenAPI().info(info).servers(Arrays.asList(localServer));
	}

	/**
	 * Spring Doc Swagger 의 Inner Class resolve 를 위한 설정
	 */
	@PostConstruct
	public void initialize() {
		TypeNameResolver innerClassAwareTypeNameResolver = new TypeNameResolver() {
			@Override
			public String getNameOfClass(Class<?> cls) {
				return cls.getName()
						.substring(cls.getName().lastIndexOf('.') + 1)
						.replace("$", ".");
			}
		};

		ModelConverters.getInstance().addConverter(new ModelResolver(objectMapper, innerClassAwareTypeNameResolver));
	}

	/**
	 * DisableSecurity 어노테이션있을시 스웨거 시큐리티 설정 삭제
	 * @see <a href="https://devnm.tistory.com/26">
	 * [스프링] spring swagger api 하나만 인증 풀기</a>
	 * @return
	 */
	@Bean
	public OperationCustomizer customize() {
		return (Operation operation, HandlerMethod handlerMethod) -> {
			DisableSwaggerSecurity methodAnnotation =
					handlerMethod.getMethodAnnotation(DisableSwaggerSecurity.class);
			// DisableSecurity 어노테이션있을시 스웨거 시큐리티 설정 삭제
			if (methodAnnotation != null) {
				operation.setSecurity(Collections.emptyList());
			}
			// 예제코드 의미가 없어보여서 안보이게함
//            operation.getResponses().remove("200");
			operation.getResponses().remove("400");
			operation.getResponses().remove("401");
			operation.getResponses().remove("402");
			operation.getResponses().remove("403");
			operation.getResponses().remove("404");
			operation.getResponses().remove("500");
			return operation;
		};
	}
}
