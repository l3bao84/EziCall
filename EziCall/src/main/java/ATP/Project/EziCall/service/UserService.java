package ATP.Project.EziCall.service;

import ATP.Project.EziCall.DTO.EmployeeActivityDTO;
import ATP.Project.EziCall.DTO.EmployeeDTO;
import ATP.Project.EziCall.exception.EmptyListException;
import ATP.Project.EziCall.exception.ObjectNotFoundException;
import ATP.Project.EziCall.exception.UsernameAlreadyExistException;
import ATP.Project.EziCall.models.Role;
import ATP.Project.EziCall.models.User;
import ATP.Project.EziCall.repository.UserRepository;
import ATP.Project.EziCall.requests.UserRequest;
import ATP.Project.EziCall.DTO.EmployeeDetailDTO;
import ATP.Project.EziCall.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String getAuthenticatedUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return Optional.ofNullable(authentication)
                .map(Authentication::getPrincipal)
                .map(principal -> principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : principal.toString())
                .orElse(null);
    }

    public User getUserByUsername() {
        Optional<User> optionalUser = userRepository.findByUsername(getAuthenticatedUsername());
        User user = null;
        if(optionalUser.isPresent()) {
            user = optionalUser.get();
        }
        return user;
    }

    public User register(UserRequest request)  {

        if(userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistException(AppConstants.USERNAME_IS_ALREADY_EXIST);
        }

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
                .orElseThrow(() -> new ObjectNotFoundException(AppConstants.AGENT_IS_NOT_EXIST));

        if(userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistException(AppConstants.USERNAME_IS_ALREADY_EXIST);
        }

        existingUser.setUsername(request.getUsername());
        existingUser.setPassword(passwordEncoder.encode(request.getPassword()));
        existingUser.setRole(Role.valueOf(request.getRole()));

        return userRepository.save(existingUser);
    }

    public void deleteEmployee(String id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(AppConstants.AGENT_IS_NOT_EXIST));

        userRepository.delete(existingUser);
    }



    public List<EmployeeDTO> getAll() {
        return userRepository.getEmployees();
    }

    public List<EmployeeDTO> filterByRole(String role) {
        return userRepository.filterUserByRole(Role.valueOf(role));
    }

    public EmployeeDetailDTO getEmployee(String id) {

        return userRepository.getEmployee(id)
                .orElseThrow(() -> new ObjectNotFoundException(AppConstants.AGENT_IS_NOT_EXIST));
    }

    public List<EmployeeDTO> findEmployee(String name, String username, String role, String id) {
        Role rol = null;
        if (role != null && !role.isEmpty()) {
            try {
                rol = Role.valueOf(role);
            } catch (IllegalArgumentException e) {
                // Giới tính không hợp lệ sẽ bị bỏ qua và không gán giá trị cho gendr
            }
        }
        return userRepository.findEmployee(name, username, rol, id);
    }

    private StringBuilder calculateActivityTime(String acStatus, String timestamp) {

        LocalDateTime startTime = LocalDateTime.parse(timestamp, AppConstants.DATE_TIME_FORMATTER);
        Duration duration = Duration.between(startTime, LocalDateTime.now());

        StringBuilder activityTime = new StringBuilder();
        long day = duration.toDays();
        if(day != 0 && acStatus.equalsIgnoreCase("OFFLINE")) {
            activityTime.append(day + " ngày " + duration.toHours() % 24 + " giờ " + duration.toMinutes() % 60 + " phút trước");
        }else {
            activityTime.append(duration.toHours() % 24 + " giờ " + duration.toMinutes() % 60 + " phút");
        }

        return activityTime;
    }

    public List<EmployeeActivityDTO> getEmployeesActivities(String status) {
        if(userRepository.findAll().isEmpty()) {
            throw new EmptyListException(AppConstants.NO_DATA_LIST);
        }

        List<EmployeeActivityDTO> employeeActivityDTOS = userRepository.getEmployeesActivities(status);

        for (EmployeeActivityDTO employeeActivityDTO:employeeActivityDTOS) {
            if(employeeActivityDTO.getActivityStatus() != null) {
                int spaceIndex = employeeActivityDTO.getActivityStatus().indexOf(" ");

                String timestampString = employeeActivityDTO.getActivityStatus().substring(spaceIndex + 1);
                String acStatus = employeeActivityDTO.getActivityStatus().substring(0, spaceIndex).trim();

                employeeActivityDTO.setActivityStatus(acStatus);
                employeeActivityDTO.setActivityTime(
                        calculateActivityTime(
                                acStatus, timestampString
                        ).toString()
                );
            }
        }

        return employeeActivityDTOS;
    }

}
