package ATP.Project.EziCall.service;

import ATP.Project.EziCall.exception.ObjectNotFoundException;
import ATP.Project.EziCall.models.Role;
import ATP.Project.EziCall.models.User;
import ATP.Project.EziCall.repository.UserRepository;
import ATP.Project.EziCall.requests.UserRequest;
import ATP.Project.EziCall.DTO.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");


    public String getAuthenticatedUsername() {
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
        return username;
    }

    public User getUserByUsername() {
        return userRepository.findByUsername(getAuthenticatedUsername()).get();
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User register(UserRequest request)  {

        User user = User.builder()
                .fullname(request.getFullname())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.valueOf(request.getRole()))
                .build();


        return userRepository.save(user);
    }

    public User updateEmployee(String id, UserRequest request) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Không tồn tại nhân viên có id: " + id));


        existingUser.setUsername(request.getUsername());
        existingUser.setPassword(passwordEncoder.encode(request.getPassword()));
        existingUser.setRole(Role.valueOf(request.getRole()));

        return userRepository.save(existingUser);
    }

    public void deleteEmployee(String id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Không tồn tại nhân viên có id: " + id));

        userRepository.delete(existingUser);
    }

    public String calculateOnlTime(String timestamp) {
        int spaceIndex = timestamp.indexOf(" ");
        String timestampString = timestamp.substring(spaceIndex + 1);

        LocalDateTime startTime = LocalDateTime.parse(timestampString, DATE_TIME_FORMATTER);
        Duration duration = Duration.between(startTime, LocalDateTime.now());

        return timestamp.replace(timestampString,
                "for " + duration.getSeconds()/3600 + ":" +
                        (duration.getSeconds() % 3600) / 60 + ":" +
                        duration.getSeconds() % 60
        );
    }

    public List<EmployeeDTO> getAll() {
        return userRepository.getEmployees();
    }

    public List<EmployeeDTO> filterByRole(String role) {
        return userRepository.filterUserByRole(Role.valueOf(role));
    }

    public EmployeeDTO getEmployee(String id) {

        EmployeeDTO existingUser = userRepository.getEmployee(id)
                .orElseThrow(() -> new ObjectNotFoundException("Không tồn tại nhân viên có id: " + id));
        return existingUser;
    }

    public List<EmployeeDTO> findEmployee(String name, String username, String role, String id) {
        Role rol = null;
        if (role != null && !role.isEmpty()) {
            try {
                rol = Role.valueOf(role);
            } catch (IllegalArgumentException e) {

            }
        }
        return userRepository.findEmployee(name, username, rol, id);
    }

    public List<EmployeeDTO> findEmpOnline() {
        List<EmployeeDTO> employeeDTOS = userRepository.findEmployeeOnline();

        for(EmployeeDTO employeeDTO:employeeDTOS) {
            employeeDTO.setActivityStatus(calculateOnlTime(employeeDTO.getActivityStatus()));
        }
        return employeeDTOS;
    }

}
