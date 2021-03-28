package info.jab.microservices.model;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.BDDAssertions.then;

public class JwtRequestTest {

    @Test
    public void given_instanciate_loginRequest_with_good_values_when_validate_then_ok() {

        //Given
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        //When
        JwtRequest loginRequest = new JwtRequest("DEMO", "DEMO");
        Set<ConstraintViolation<JwtRequest>> violations = validator.validate(loginRequest);

        //Then
        then(violations.size()).isEqualTo(0);
    }

    @Test
    public void given_instanciate_loginRequest_with_bad_values_when_validate_then_ok() {

        //Given
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        //When
        JwtRequest loginRequest = new JwtRequest("", "");
        Set<ConstraintViolation<JwtRequest>> violations = validator.validate(loginRequest);

        //Then
        then(violations.size()).isGreaterThan(0);
    }

    @Test
    public void given_instanciate_loginRequest_with_null_values_when_validate_then_ok() {

        //Given
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        //When
        JwtRequest loginRequest = new JwtRequest();
        Set<ConstraintViolation<JwtRequest>> violations = validator.validate(loginRequest);

        //Then
        then(violations.size()).isGreaterThan(0);
    }
}