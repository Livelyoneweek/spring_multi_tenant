package kr.co.mhnt.multi.test;

import kr.co.mhnt.multi.mytenant.TenantContext;
import kr.co.mhnt.multi.user.entity.User;
import kr.co.mhnt.multi.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @Transactional
    @Rollback(false)
    public void testUserCreationTenant1() {

        // Set tenant context to tenant1
        TenantContext.setCurrentTenant("tenant1");

        // Create new user

        String username = "username1";
        String password = "password1";
        String roles = "roles1";
        String mobile = "mobile1";

        User user = new User(username,password,roles,mobile);
        User save = userRepository.save(user);

        // Check that the user was saved correctly
        User savedUser = userRepository.findById(save.getUserId()).get();
        assertEquals("username1", savedUser.getUsername());

        // Clear tenant context after operation
        TenantContext.clear();
    }
}