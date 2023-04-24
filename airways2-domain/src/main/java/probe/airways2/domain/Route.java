package probe.airways2.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class Route {

    private final List<Flight> flights = new ArrayList<>();
    private final String tag;

    public Route(Route src, Flight flight) {
        flights.addAll(src.getFlights());
        flights.add(flight);
        tag = src.getTag();
    }

    public Route(Flight flight) {
        flights.add(flight);
        this.tag = String.format("from: %s, departure time: %s",
          flight.getStartAirport().getCode(),
          flight.getDepartureTime()
        );
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public String getTag() {
        return tag;
    }

    public boolean contains(Flight flight) {
        return flights.contains(flight);
    }

    public boolean containsMandatory() {
        return flights.stream().anyMatch(Flight::isMandatory);
    }

    public Stream<Flight> getMandatory() {
        return flights.stream().filter(Flight::isMandatory);
    }

    public Flight getLast() {
        return flights.get(flights.size() - 1);
    }

    public boolean containsBeforeLast() {
        return flights.size() >= 2;
    }

    public Flight getBeforeLast() {
        return flights.get(flights.size() - 2);
    }

    public Airport getReturnPoint() {
        return flights.get(0).getStartAirport();
    }

    public BigDecimal getExpenses(Function<Long, BigDecimal> waitTimeExpensesCalculator) {
        BigDecimal result = BigDecimal.ZERO;
        for (int i = 0; i < flights.size(); i++) {
            final BigDecimal flightExpenses = flights.get(i).getExpenses();
            result = result.add(flightExpenses);
            final BigDecimal waitTimeExpenses = waitTimeExpensesCalculator.apply(getWaitTime(i));
            result = result.add( waitTimeExpenses);
        }
        return result;
    }

    private long getWaitTime(int index) {
        if (index == 0) {
            return 0L;
        }
        final Date depTime = flights.get(index).getDepartureTime();
        final Date arrTime = flights.get(index-1).getArrivalTime();
        return depTime.getTime() - arrTime.getTime();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Route{");
        sb.append("flights=").append(flights);
        sb.append(", tag='").append(tag).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
