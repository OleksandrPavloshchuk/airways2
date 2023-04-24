package probe.airways2.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import probe.airways2.domain.Flight;
import probe.airways2.web.dataPresentation.FlightPresentation;
import probe.airways2.web.repositories.AirportRepository;
import probe.airways2.web.repositories.FlightRepository;
import probe.airways2.web.utils.TimestampConvertor;

import static probe.airways2.web.utils.TimestampConvertor.toDate;

@Controller
@RequestMapping("/flight")
public class FlightController extends ControllerBase<Flight, FlightPresentation> {

  @Autowired
  private FlightRepository flightRepository;

  @Autowired
  private AirportRepository airportRepository;

  @Override
  protected CrudRepository<Flight, String> getMainRepository() {
    return flightRepository;
  }

  @Override
  protected FlightPresentation createEntityPresentation() {
    return new FlightPresentation();
  }

  @Override
  protected String getListView() {
    return "flight";
  }

  @Override
  protected String getEditView() {
    return "editFlight";
  }

  @Override
  protected String getDeleteView() {
    return "deleteFlight";
  }

  @Override
  protected String getId(FlightPresentation flightPresentation) {
    return flightPresentation.getId();
  }

  @Override
  protected FlightPresentation toEntityPresentation(Flight entity) {
    final FlightPresentation result = new FlightPresentation();
    result.setId(entity.getId());
    result.setExpenses(entity.getExpenses());
    result.setIncome(entity.getIncome());
    result.setMandatory(entity.isMandatory());
    result.setStartAirport(entity.getStartAirport().getCode());
    result.setEndAirport(entity.getEndAirport().getCode());
    result.setArrivalTime(TimestampConvertor.toString(entity.getArrivalTime()));
    result.setDepartureTime(TimestampConvertor.toString(entity.getDepartureTime()));
    return result;
  }

  @Override
  protected void extendModel(ModelAndView modelAndView) {
    modelAndView.addObject("airports", airportRepository.findAll());
  }

  @Override
  protected Flight toEntity(FlightPresentation flightPresentation) {
    final Flight result = new Flight();
    result.setId(flightPresentation.getId());
    result.setIncome(flightPresentation.getIncome());
    result.setExpenses(flightPresentation.getExpenses());
    result.setMandatory(flightPresentation.isMandatory());
    result.setArrivalTime(toDate(flightPresentation.getArrivalTime()));
    result.setDepartureTime(toDate(flightPresentation.getDepartureTime()));
    result.setStartAirport(airportRepository.findById(flightPresentation.getStartAirport()).orElse(null));
    result.setEndAirport(airportRepository.findById(flightPresentation.getEndAirport()).orElse(null));
    return result;
  }

}
