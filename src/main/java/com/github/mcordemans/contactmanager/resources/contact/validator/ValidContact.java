package com.github.mcordemans.contactmanager.resources.contact.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ContactModificationResourceValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidContact {
    String message() default "{error.contact}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
