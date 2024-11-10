package kr.co.mhnt.multi.user.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.mhnt.multi.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "[01] 유저 API", description = "유저 API")
public class UserController {

    private final UserService userService;

    @Operation(summary = "username 조회", description = """
            ```
            username 조회
            ```
            """)
    @GetMapping("/api/user/{id}")
    public String findUserName(@Parameter(name = "id") @PathVariable(value = "id") Long userId) {
        log.info("### UserController.findUserName");
        String userName = userService.findUserName(userId);
        log.info("result = {}", userName);
        return userName;
    }
}
