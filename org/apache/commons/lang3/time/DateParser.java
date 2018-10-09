package org.apache.commons.lang3.time;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public abstract interface DateParser
{
  public abstract Date parse(String paramString)
    throws ParseException;
  
  public abstract Date parse(String paramString, ParsePosition paramParsePosition);
  
  public abstract String getPattern();
  
  public abstract TimeZone getTimeZone();
  
  public abstract Locale getLocale();
  
  public abstract Object parseObject(String paramString)
    throws ParseException;
  
  public abstract Object parseObject(String paramString, ParsePosition paramParsePosition);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\time\DateParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */