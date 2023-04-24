package probe.airways2.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import probe.airways2.calculation.Calculator;
import probe.airways2.domain.Flight;
import probe.airways2.web.repositories.FlightRepository;

@Controller
@RequestMapping("/")
public class CalculationController {

  @Autowired
  private FlightRepository flightRepository;

  @GetMapping
  public String calculate(Model model) {

    final Iterable<Flight> flights = flightRepository.findAll();

    final Calculator calculator = new Calculator(flights);
    calculator.perform();

    model.addAttribute("cycles", calculator.getRoutes());
    model.addAttribute("mandatoryFlightsWithoutCycles", calculator.getMandatoryFlightsWithoutRoutes());
    return "calculation";
  }

}
