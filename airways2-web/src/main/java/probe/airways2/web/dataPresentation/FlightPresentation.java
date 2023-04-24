package probe.airways2.web.dataPresentation;

import probe.airways2.web.validation.ValidArrivalTime;
import probe.airways2.web.validation.ValidEndAirport;

import java.math.BigDecimal;

@ValidEndAirport
@ValidArrivalTime
public class FlightPresentation {
  private String id;
  private String startAirport;
  private String endAirport;
  private BigDecimal income;
  private BigDecimal expenses;
  private String departureTime;
  private String arrivalTime;
  private boolean mandatory;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getStartAirport() {
    return startAirport;
  }

  public void setStartAirport(String startAirport) {
    this.startAirport = startAirport;
  }

  public String getEndAirport() {
    return endAirport;
  }

  public void setEndAirport(String endAirport) {
    this.endAirport = endAirport;
  }

  public BigDecimal getIncome() {
    return income;
  }

  public void setIncome(BigDecimal income) {
    this.income = income;
  }

  public BigDecimal getExpenses() {
    return expenses;
  }

  public void setExpenses(BigDecimal expenses) {
    this.expenses = expenses;
  }

  public String getDepartureTime() {
    return departureTime;
  }

  public void setDepartureTime(String departureTime) {
    this.departureTime = departureTime;
  }

  public String getArrivalTime() {
    return arrivalTime;
  }

  public void setArrivalTime(String arrivalTime) {
    this.arrivalTime = arrivalTime;
  }

  public boolean isMandatory() {
    return mandatory;
  }

  public void setMandatory(boolean mandatory) {
    this.mandatory = mandatory;
  }
}
