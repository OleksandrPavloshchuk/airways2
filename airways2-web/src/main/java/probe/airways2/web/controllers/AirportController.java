package probe.airways2.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import probe.airways2.domain.Airport;
import probe.airways2.web.repositories.AirportRepository;

@Controller
@RequestMapping("/airport")
public class AirportController extends ControllerBase<Airport, Airport> {

  @Autowired
  private AirportRepository airportRepository;

  @Override
  protected CrudRepository<Airport, String> getMainRepository() {
    return airportRepository;
  }

  @Override
  protected Airport createEntityPresentation() {
    return new Airport();
  }

  @Override
  protected String getListView() {
    return "airport";
  }

  @Override
  protected String getEditView() {
    return "editAirport";
  }

  @Override
  protected String getDeleteView() {
    return "deleteAirport";
  }

  @Override
  protected String getId(Airport object) {
    return object.getCode();
  }

  @Override
  protected Airport toEntity(Airport airport) {
    return airport;
  }

  @Override
  protected Airport toEntityPresentation(Airport entity) {
    return entity;
  }

}
