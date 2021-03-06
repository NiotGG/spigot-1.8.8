package org.apache.commons.lang3.time;

import java.text.FieldPosition;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public abstract interface DatePrinter
{
  public abstract String format(long paramLong);
  
  public abstract String format(Date paramDate);
  
  public abstract String format(Calendar paramCalendar);
  
  public abstract StringBuffer format(long paramLong, StringBuffer paramStringBuffer);
  
  public abstract StringBuffer format(Date paramDate, StringBuffer paramStringBuffer);
  
  public abstract StringBuffer format(Calendar paramCalendar, StringBuffer paramStringBuffer);
  
  public abstract String getPattern();
  
  public abstract TimeZone getTimeZone();
  
  public abstract Locale getLocale();
  
  public abstract StringBuffer format(Object paramObject, StringBuffer paramStringBuffer, FieldPosition paramFieldPosition);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\time\DatePrinter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */