package com.brs.gridge.domain.validation;

import com.brs.gridge.domain.entity.User;
import com.brs.gridge.domain.vo.LoginType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserLoginValidator implements ConstraintValidator<ValidUserLogin, User> {

    @Override
    public void initialize(ValidUserLogin constraintAnnotation) {
    }

    @Override
    public boolean isValid(User user, ConstraintValidatorContext context) {
        if (user == null) {
            return true;
        }

        boolean isValid = true;
        context.disableDefaultConstraintViolation();

        if (LoginType.LOCAL.equals(user.getLoginType())) {
            if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
                context.buildConstraintViolationWithTemplate("Local login requires password")
                        .addPropertyNode("password")
                        .addConstraintViolation();
                isValid = false;
            }
            if (!user.getSocialAccounts().isEmpty()) {
                context.buildConstraintViolationWithTemplate("Local login cannot have social accounts")
                        .addPropertyNode("socialAccounts")
                        .addConstraintViolation();
                isValid = false;
            }
        } else if (LoginType.SOCIAL.equals(user.getLoginType())) {
            if (user.getPassword() != null && !user.getPassword().trim().isEmpty()) {
                context.buildConstraintViolationWithTemplate("Social login should not have password")
                        .addPropertyNode("password")
                        .addConstraintViolation();
                isValid = false;
            }
            if (user.getSocialAccounts().isEmpty()) {
                context.buildConstraintViolationWithTemplate("Social login requires at least one social account")
                        .addPropertyNode("socialAccounts")
                        .addConstraintViolation();
                isValid = false;
            }
        }

        return isValid;
    }
}
