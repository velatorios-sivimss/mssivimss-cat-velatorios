package com.imss.sivimss.catvelatorios.model.validation;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static javax.validation.Validation.buildDefaultValidatorFactory;

public class BeanValidator {
    public static <T> List<String> validate(final T object) {
        Validator validator;
        List<String> errores = new ArrayList<>();
        try (ValidatorFactory validatorFactory = buildDefaultValidatorFactory()) {
            validator = validatorFactory.getValidator();
            Set<ConstraintViolation<T>> violations = validator.validate(object);
            // todo - esto puede ser un map con el campo y el mensaje
            violations.forEach(violation -> errores.add(violation.getPropertyPath().toString()));
        }
        return errores;
    }
}
