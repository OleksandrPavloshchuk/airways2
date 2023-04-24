package probe.airways2.domain;

import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Flight {

    @Id
    @Length(min = 3, max = 10)
    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String id;

    @ManyToOne
    @NotNull
    private Airport startAirport;

    @ManyToOne
    @NotNull
    private Airport endAirport;

    @Column(precision = 10, scale = 2)
    @NotNull
    private BigDecimal income;

    @Column(precision = 10, scale = 2)
    @NotNull
    private BigDecimal expenses;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date departureTime;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date arrivalTime;

    @Column
    private boolean mandatory;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    public Airport getStartAirport() {
        return startAirport;
    }

    public void setStartAirport(Airport startAirport) {
        this.startAirport = startAirport;
    }

    public Airport getEndAirport() {
        return endAirport;
    }

    public void setEndAirport(Airport endAirport) {
        this.endAirport = endAirport;
    }

}
