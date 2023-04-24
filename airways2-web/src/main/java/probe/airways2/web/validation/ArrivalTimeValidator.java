package probe.airways2.web.validation;

import probe.airways2.web.dataPresentation.FlightPresentation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ArrivalTimeValidator implements ConstraintValidator<ValidArrivalTime, FlightPresentation> {

  @Override
  public boolean isValid(FlightPresentation value, ConstraintValidatorContext context) {
    return value.getArrivalTime().compareTo(value.getDepartureTime()) > 0;
  }
}
