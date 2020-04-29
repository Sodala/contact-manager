package com.github.mcordemans.contactmanager.resources.company.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CompanyCreationValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCompanyModificationResource {
    String message() default "{error.company}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
