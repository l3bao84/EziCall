package ATP.Project.EziCall.util;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class DataValidation {

    private final static String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    private final static String PHONE_REGEX = "^[0-9]{10}$";

    public boolean isValidDataCustomer(String email, String phone) {
        boolean isValidEmail = Pattern.compile(EMAIL_REGEX).matcher(email).matches();
        boolean isValidPhone = Pattern.compile(PHONE_REGEX).matcher(phone).matches();

        return (isValidEmail && isValidPhone);
    }
}
