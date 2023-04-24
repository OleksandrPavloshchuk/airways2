package probe.airways2.web.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimestampConvertor {

  private static final String PATTERN = "dd/MM/yyyy hh:mm";

  public static String toString(Date date) {
    return new SimpleDateFormat(PATTERN).format(date);
  }

  public static Date toDate(String str) {
    try {
      return new SimpleDateFormat(PATTERN).parse(str);
    } catch (ParseException e) {
      // TODO process exception
      e.printStackTrace();
      return null;
    }
  }
}
