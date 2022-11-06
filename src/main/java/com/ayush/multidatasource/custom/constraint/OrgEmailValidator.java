package com.ayush.multidatasource.custom.constraint;

import com.ayush.multidatasource.custom.validation.OrgEmail;
import com.ayush.multidatasource.model.Student;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrgEmailValidator  implements ConstraintValidator<OrgEmail, Student> {
    private String checkAgainst;

    @Override
    public void initialize(OrgEmail constraintAnnotation) {
        checkAgainst=constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Student student, ConstraintValidatorContext constraintValidatorContext) {
       if (student==null || student.getName()==null || student.getName().isEmpty()){
           return false;
       }
       if (student.getEmail().equals(checkAgainst)){
           return true;
       }
       return false;
    }
}
