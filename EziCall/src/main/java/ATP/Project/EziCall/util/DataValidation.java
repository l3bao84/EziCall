package ATP.Project.EziCall.util;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class DataValidation {

    private final static String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    private final static String PHONE_REGEX = "^[0-9]{10}$";

    private final static String DATE_REGEX = "^[0-9]{1,2}\\/[0-9]{1,2}\\/[0-9]{4}$";

    public boolean isValidData(String username, String password) {
        //boolean isValidEmail = Pattern.compile(EMAIL_REGEX).matcher(email).matches();
        //boolean isValidPhone = Pattern.compile(PHONE_REGEX).matcher(phone).matches();
        //boolean isValidDate = Pattern.compile(DATE_REGEX).matcher(date).matches();

        boolean isValidUsername = username.matches("\\S{8,15}");

        boolean isValidPassword = password.matches("\\S{8,}");

        return (isValidUsername && isValidPassword);
    }

    public boolean isValidDataCustomer(String email, String phone) {
        boolean isValidEmail = Pattern.compile(EMAIL_REGEX).matcher(email).matches();
        boolean isValidPhone = Pattern.compile(PHONE_REGEX).matcher(phone).matches();

        return (isValidEmail && isValidPhone);
    }
}
