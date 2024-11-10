package kr.co.mhnt.multi.config.swagger;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

/**
 * (스웨거에서 멀티파트 데이터 요청 시, 내부 에러를 회피하기 위해 추가)
 * MediaType.APPLICATION_OCTET_STREAM 유형의 데이터를 ObjectMapper 객체로 변환하라고 명시하는 목적
 * 쓰기에는 해당 컨버터는 동작하지 않음(내부 컨버터에 의존)
 */
@Component
public class MultipartJackson2HttpMessageConverter extends AbstractJackson2HttpMessageConverter {

    public MultipartJackson2HttpMessageConverter(ObjectMapper objectMapper) {
        super(objectMapper, MediaType.APPLICATION_OCTET_STREAM);
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return false;
    }

    @Override
    public boolean canWrite(Type type, Class<?> clazz, MediaType mediaType) {
        return false;
    }

    @Override
    protected boolean canWrite(MediaType mediaType) {
        return false;
    }
}
