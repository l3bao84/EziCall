package ATP.Project.EziCall.util;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class DataValidation {

    private final static String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    private final static String PHONE_REGEX = "^[0-9]{10}$";

    private final static String DATE_REGEX = "^[0-9]{1,2}\\/[0-9]{1,2}\\/[0-9]{4}$";

    public boolean isValidData(String email, String phone, String username, String password, String date) {
        boolean isValidEmail = Pattern.compile(EMAIL_REGEX).matcher(email).matches();
        boolean isValidPhone = Pattern.compile(PHONE_REGEX).matcher(phone).matches();
        boolean isValidDate = Pattern.compile(DATE_REGEX).matcher(date).matches();
        boolean isValidUsername = false;
        boolean isValidPassword = false;

        if(!(username.length() < 10)) {
            isValidUsername = true;
        }

        if(!(password.length() < 8)) {
            isValidPassword = true;
        }

        return (isValidEmail && isValidPhone && isValidUsername && isValidPassword && isValidDate);
    }

    public boolean isValidDataCustomer(String email, String phone) {
        boolean isValidEmail = Pattern.compile(EMAIL_REGEX).matcher(email).matches();
        boolean isValidPhone = Pattern.compile(PHONE_REGEX).matcher(phone).matches();

        return (isValidEmail && isValidPhone);
    }
}
