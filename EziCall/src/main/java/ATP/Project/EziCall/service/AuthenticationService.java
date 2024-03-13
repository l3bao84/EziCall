package ATP.Project.EziCall.service;

import ATP.Project.EziCall.exception.AuthenticationFailedException;
import ATP.Project.EziCall.models.Role;
import ATP.Project.EziCall.models.User;
import ATP.Project.EziCall.models.UserActivityLog;
import ATP.Project.EziCall.repository.UserActitityLogRepository;
import ATP.Project.EziCall.repository.UserRepository;
import ATP.Project.EziCall.requests.AuthenticationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserActitityLogRepository userActitityLogRepository;

    @Autowired
    private UserRepository userRepository;

    public String authenticate(AuthenticationRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = (User) authentication.getPrincipal();

            if(user.getRole().equals(Role.EMPLOYEE)) {
                userActitityLogRepository.save(new UserActivityLog(user, "ONLINE", LocalDateTime.now()));
            }

            String token = jwtService.generateToken(user);

            return token;
        } catch (AuthenticationException e) {
            throw new AuthenticationFailedException("Authentication failed", e);
        }
    }

    public UserActivityLog logout() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;

        if (authentication != null) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            } else {
                username = principal.toString();
            }
        }

        Optional<User> optionalUser = userRepository.findByUsername(username);
        if(optionalUser.isPresent() && optionalUser.get().getRole().equals(Role.EMPLOYEE)) {

            User user = optionalUser.get();

            LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
            UserActivityLog log = userActitityLogRepository.findTopByUserIdAndStatusAndTimestampBetweenOrderByTimestampDesc(user.getUserId(), "Online",startOfDay, LocalDateTime.now());

            if (log != null) {
                log.setStatus("OFFLINE");
                log.setTimestamp(LocalDateTime.now());
                return userActitityLogRepository.save(log);
            }
        }
        return null;
    }
}
