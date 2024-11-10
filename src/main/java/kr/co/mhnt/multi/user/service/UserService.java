package kr.co.mhnt.multi.user.service;

import jakarta.persistence.EntityManager;
import kr.co.mhnt.multi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final EntityManager entityManager;

    private final UserRepository userRepository;

    public String findUserName(Long userId) {
        log.info("### UserService.findUserName");

        Session session = entityManager.unwrap(Session.class);
//        session.enableFilter("tenantFilter").setParameter("tenantId", "a_multi_2");
        Filter filter = session.getEnabledFilter("tenantFilter");
        if (filter != null) {
            log.info("### filter name = {}", filter.getName());
        }

        return userRepository.findUserName(userId);
    }
}
