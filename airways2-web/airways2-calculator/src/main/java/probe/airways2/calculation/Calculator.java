package probe.airways2.calculation;

import probe.airways2.domain.Flight;
import probe.airways2.domain.Route;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Обчислювач потрібних циклів та обов'язкових рейсів поза циклами
 */
public class Calculator {

  private final List<Flight> allFlights = new ArrayList<>();
  private final List<Route> cycles = new ArrayList<>();
  private final List<Flight> mandatoryFlights = new ArrayList<>();
  private final List<Flight> mandatoryFlightsWithoutRoutes = new ArrayList<>();

  public Calculator(Iterable<Flight> allFlights) {
    if (allFlights == null) {
      throw new IllegalArgumentException("all flights list is null");
    }
    // Робляться копії списків для можливості видалення елементів в процесі роботи алгоритму
    allFlights.forEach( this.allFlights::add);
    this.allFlights.stream().filter(Flight::isMandatory).forEach(mandatoryFlights::add);
  }

  public List<Route> getRoutes() {
    return cycles;
  }

  public List<Flight> getMandatoryFlightsWithoutRoutes() {
    return mandatoryFlightsWithoutRoutes;
  }

  /**
   * Визначити цикли рейсів та обов'язкові рейси поза циклами
   */
  public void perform() {
    List<Route> allRoutes = new ArrayList<>();

    // Для всіх рейсів
    while (!allFlights.isEmpty()) {
      // Порядок перебору - від рейсів, що починають найраніше
      final Flight origin = getEarliestFlight();
      // Знайти цикли, що містять обов'язкові рейси
      getRoutesWithMandatoryFlights(origin).forEach(allRoutes::add);
      // Видалити рейс, для якого ми шукали цикли - він вже не потрібний
      allFlights.remove(origin);
    }

    // Для всіх обов'язкових рейсів
    while (!mandatoryFlights.isEmpty()) {
      // Порядок перебору - від рейсів із найбільшим прибутком
      final Flight mostValuable = getMandatoryFlightWithMaxIncome();
      // Знайти для такого рейсу найдешевший за витратами цикл
      final Optional<Route> cheapestOpt = getCheepestRoute(allRoutes, mostValuable);
      // Видалити рейс, для якого ми шукали цикли - він вже не потрібний
      mandatoryFlights.remove(mostValuable);
      if (cheapestOpt.isPresent()) {
        final Route cheapest = cheapestOpt.get();
        // Добавити знайдений найдешевший цикл до результату
        cycles.add(cheapest);
        // Видалити всі цикли, які мають рейси, спільні зі знайденим - рейс відбувається лише раз
        allRoutes = removeIntersectedRoutes(allRoutes, cheapest);
      } else {
        // Жодного циклу із таким рейсом не знайдено
        mandatoryFlightsWithoutRoutes.add(mostValuable);
      }
    }
  }

  /**
   * Визначити мінімальний за витратами цикл із allRoutes, що містить політ flight
   *
   * @param allRoutes
   * @param flight
   * @return
   */
  private Optional<Route> getCheepestRoute(List<Route> allRoutes, Flight flight) {
    return allRoutes.stream()
      .filter(route -> route.contains(flight))
      .min((r1, r2)->r1.getExpenses(Calculator::getWaitExpenses).compareTo(r2.getExpenses(Calculator::getWaitExpenses)));
  }

  /**
   * Видалити всі цикли, що містять рейси, спільні із даним
   */
  private List<Route> removeIntersectedRoutes(List<Route> list, Route baseRoute) {
    return list.stream()
      .filter(cycle -> cycle.getFlights().stream().noneMatch(flight -> baseRoute.contains(flight)))
      .collect(Collectors.toList());
  }

  /**
   * Цикли із обов'язковими рейсами, що починаються із рейсу origin
   *
   * @param origin
   * @return
   */
  private Stream<Route> getRoutesWithMandatoryFlights(Flight origin) {
    return detectRoutes(new Route(origin)).stream().filter(Route::containsMandatory);
  }

  /**
   * Рейс, що вилітає найраніше
   *
   * @return
   */
  private Flight getEarliestFlight() {
    return allFlights.stream()
      .min((f1, f2) -> f1.getDepartureTime().compareTo(f2.getDepartureTime()))
      .orElseThrow(() -> new NoSuchElementException());
  }

  /**
   * Обов'язковий рейс із найбільшим прибутком
   *
   * @return
   */
  private Flight getMandatoryFlightWithMaxIncome() {
    return mandatoryFlights.stream()
      .max((f1, f2) -> f1.getIncome().compareTo(f2.getIncome()))
      .orElseThrow(() -> new NoSuchElementException());
  }

  /**
   * Рекурсивний пошук циклів
   *
   * @param base ланцюжок для перевірки
   * @return
   */
  private List<Route> detectRoutes(Route base) {
    final Flight last = base.getLast();
    // Перевірити лише цикли, що мають хоча два рейси, отже, можуть бути замкненими
    if (base.containsBeforeLast()) {
      // Час відправлення останнього рейсу має бути після часу прибуття передостаннього:
      final Flight beforeLast = base.getBeforeLast();
      if (last.getDepartureTime().compareTo(beforeLast.getArrivalTime()) < 0) {
        // Далі ми рухатися по цьому ланцюжку не можемо - відсікаємо гілку
        return Collections.emptyList();
      }
      if (last.getEndAirport().equals(base.getReturnPoint())) { //!перевіряємо чи може бути циклом
        // Останній рейс у ланцюжку повертається у початкову точку - цикл знайдено:
        return Arrays.asList(base);
      }
    }
    // Продовжити рекурсивно для всіх сусідніх рейсів:
    return getNeighbours(last)
      .map(flight -> detectRoutes(new Route(base, flight))) //!це рекурсія
      .flatMap(List::stream)
      .collect(Collectors.toList());
  }

  /**
   * Рейси, які відправляються із пункту призначення рейсу flight
   *
   * @param flight
   * @return
   */
  private Stream<Flight> getNeighbours(Flight flight) {
    return allFlights.stream().filter(next -> next.getStartAirport().equals(flight.getEndAirport()));
  }

  /**
   * Втрати від очікування протягом часу time
   *
   * @param time
   * @return
   */
  private static BigDecimal getWaitExpenses(long time) {
    final double waitPrice = 0.3; // TODO треба поекспериментувати із різними коефіцієнтами
    return new BigDecimal(Math.round(time * waitPrice));
  }

}
