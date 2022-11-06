package com.ayush.multidatasource.custom.validation;

import com.ayush.multidatasource.custom.constraint.OrgEmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = OrgEmailValidator.class)
public @interface OrgEmail {
    String message() default "Must be ${validatedValue} mail.";
    String value();

    //represents group of constraints
    public Class<?>[] groups() default {};
    //represents additional information about annotation
    public Class<? extends Payload>[] payload() default {};
}
