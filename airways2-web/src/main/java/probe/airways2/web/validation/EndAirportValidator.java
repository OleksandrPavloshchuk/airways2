package probe.airways2.web.validation;

import probe.airways2.web.dataPresentation.FlightPresentation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EndAirportValidator implements ConstraintValidator<ValidEndAirport, FlightPresentation> {

  @Override
  public boolean isValid(FlightPresentation value, ConstraintValidatorContext context) {
    return !value.getEndAirport().equals(value.getStartAirport());
  }
}
