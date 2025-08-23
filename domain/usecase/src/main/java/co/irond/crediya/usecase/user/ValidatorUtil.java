package co.irond.crediya.usecase.user;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class ValidatorUtil {

    private static final String EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    public static boolean validateNullOrEmpty(String value) {
        return value != null && !value.isBlank();
    }

    public static boolean validateEmail(String email) {
        if (!validateNullOrEmpty(email)) {
            return false;
        }

        return Pattern.compile(EMAIL_REGEX).matcher(email).matches();
    }

    public static boolean validateMoney(BigDecimal money, BigDecimal min, BigDecimal max) {
        return money != null && money.compareTo(min) >= 0 && money.compareTo(max) <= 0;
    }

    public static boolean validateMoney(BigDecimal money) {
        return validateMoney(money, new BigDecimal(0), new BigDecimal(15000000));
    }

}
