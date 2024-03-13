package ATP.Project.EziCall.service;

import ATP.Project.EziCall.exception.InvalidFormatException;
import ATP.Project.EziCall.exception.RegistrationFailedException;
import ATP.Project.EziCall.exception.UserNotFoundException;
import ATP.Project.EziCall.models.Gender;
import ATP.Project.EziCall.models.Role;
import ATP.Project.EziCall.models.User;
import ATP.Project.EziCall.repository.UserRepository;
import ATP.Project.EziCall.requests.UserRequest;
import ATP.Project.EziCall.response.UserResponse;
import ATP.Project.EziCall.util.DataValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DataValidation dataValidation;

    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private void validateUserData(UserRequest request) throws InvalidFormatException, RegistrationFailedException {
        if (!dataValidation.isValidData(request.getEmail(), request.getPhonenumber(), request.getUsername(), request.getPassword(), request.getBirthDate())) {
            throw new InvalidFormatException("Vui lòng nhập đúng định dạng");
        }
        userRepository.findByEmail(request.getEmail())
                .ifPresent(s -> {
                    throw new RegistrationFailedException("Email này đã tồn tại");
                });
    }

    private List<UserResponse> convertToUserResponse(List<User> employees) {
        List<UserResponse> userResponses = new ArrayList<>();
        for(User employee:employees) {
            UserResponse userResponse = new UserResponse(employee.getFirstname(),
                    employee.getLastname(),
                    employee.getEmail(),
                    employee.getPhonenumber(),
                    employee.getBirthDate().toString(),
                    employee.getGender().toString(),
                    userRepository.findLatestActivity(employee.getUserId())
                    );

            userResponses.add(userResponse);
        }

        return userResponses;
    }

    public User register(UserRequest request, Role role)  {

        validateUserData(request);

        User user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .username(request.getUsername())
                .phonenumber(request.getPhonenumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .birthDate(LocalDate.parse(request.getBirthDate(), DATE_TIME_FORMATTER))
                .gender(Gender.valueOf(request.getGender().toUpperCase()))
                .role(role).build();

        return userRepository.save(user);
    }

    public User updateEmployee(Long id, UserRequest request) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Không tồn tại nhân viên có id: " + id));

        validateUserData(request);

        existingUser.setFirstname(request.getFirstname());
        existingUser.setLastname(request.getLastname());
        existingUser.setEmail(request.getEmail());
        existingUser.setUsername(request.getUsername());
        existingUser.setPhonenumber(request.getPhonenumber());
        existingUser.setPassword(passwordEncoder.encode(request.getPassword()));
        existingUser.setBirthDate(LocalDate.parse(request.getBirthDate(), DATE_TIME_FORMATTER));
        existingUser.setGender(Gender.valueOf(request.getGender().toUpperCase()));

        return userRepository.save(existingUser);
    }

    public void deleteEmployee(Long id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Không tồn tại nhân viên có id: " + id));

        userRepository.delete(existingUser);
    }

    public List<UserResponse> getAll() {
        List<User> employees = userRepository.findAllEmployee(Role.EMPLOYEE);

        return convertToUserResponse(employees);
    }

    public UserResponse getEmployee(Long id) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Không tồn tại nhân viên có id: " + id));

        return new UserResponse(existingUser.getFirstname(),
                existingUser.getLastname(),
                existingUser.getEmail(),
                existingUser.getPhonenumber(),
                existingUser.getBirthDate().toString(),
                existingUser.getGender().toString(),
                userRepository.findLatestActivity(existingUser.getUserId())
        );
    }

    public List<UserResponse> findByName(String name) {
        List<User> employees = userRepository.findEmployeeByName(name, Role.EMPLOYEE);

        return convertToUserResponse(employees);
    }

    public List<UserResponse> findEmpOnline() {
        List<User> employees = userRepository.findEmployeeOnline();

        return convertToUserResponse(employees);
    }
}
