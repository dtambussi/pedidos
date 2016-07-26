package utils

import org.joda.time.{LocalDate, DateTimeZone, DateTime}

object DateUtils {

  def now: DateTime = DateTime.now(DateTimeZone.UTC)

  def isToday(date: DateTime): Boolean = new LocalDate().equals(date.toLocalDate)

}
