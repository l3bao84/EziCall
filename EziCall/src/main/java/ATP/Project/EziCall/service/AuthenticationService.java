package ATP.Project.EziCall.service;

import ATP.Project.EziCall.exception.AuthenticationFailedException;
import ATP.Project.EziCall.models.Role;
import ATP.Project.EziCall.models.User;
import ATP.Project.EziCall.models.UserActivityLog;
import ATP.Project.EziCall.repository.UserActitityLogRepository;
import ATP.Project.EziCall.repository.UserRepository;
import ATP.Project.EziCall.requests.AuthenticationRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserActitityLogRepository userActitityLogRepository;
    private final UserRepository userRepository;
    private final RedisService redisService;
    private final UserService userService;

    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager, JwtService jwtService, UserActitityLogRepository userActitityLogRepository, UserRepository userRepository, RedisService redisService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userActitityLogRepository = userActitityLogRepository;
        this.userRepository = userRepository;
        this.redisService = redisService;
        this.userService = userService;
    }

    public String authenticate(AuthenticationRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = (User) authentication.getPrincipal();

            if(user.getRole().equals(Role.SUPPORTER) || user.getRole().equals(Role.MARKETING)) {
                userActitityLogRepository.save(new UserActivityLog(user, "ONLINE", LocalDateTime.now()));
            }

            String token = jwtService.generateToken(user);

            return token;
        } catch (AuthenticationException e) {
            throw new AuthenticationFailedException("Tên đăng nhập hoặc mật khẩu không hợp lệ", e);
        }
    }

    public void logout(HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        redisService.storeToken(token, 3600);

        Optional<User> optionalUser = userRepository.findByUsername(userService.getAuthenticatedUsername());
        if(optionalUser.isPresent() && (optionalUser.get().getRole().equals(Role.SUPPORTER) || optionalUser.get().getRole().equals(Role.MARKETING))) {

            User user = optionalUser.get();

            LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
            UserActivityLog log = userActitityLogRepository.findTopByUserIdAndStatusAndTimestampBetweenOrderByTimestampDesc(user.getUserId(), "ONLINE",startOfDay, LocalDateTime.now());

            if (log != null) {
                log.setStatus("OFFLINE");
                log.setTimestamp(LocalDateTime.now());
                userActitityLogRepository.save(log);
            }
        }
    }
}
