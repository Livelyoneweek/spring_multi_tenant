package kr.co.mhnt.multi.user.repository;

import kr.co.mhnt.multi.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select user.username from User user where user.userId = :userId")
    String findUserName(@Param(value = "userId") Long userId);
}
