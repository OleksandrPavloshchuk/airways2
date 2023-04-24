package probe.airways2.web.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ArrivalTimeValidator.class)
@Documented
public @interface ValidArrivalTime {
  String message() default "Arrival time is earlier than departure time.";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
