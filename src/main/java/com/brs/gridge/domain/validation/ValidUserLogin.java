package com.brs.gridge.domain.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UserLoginValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUserLogin {
    String message() default "Invalid login configuration";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
