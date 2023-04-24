package probe.airways2.web.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EndAirportValidator.class)
@Documented
public @interface ValidEndAirport {
  String message() default "End airport and start airport should be different.";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
