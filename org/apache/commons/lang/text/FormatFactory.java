package org.apache.commons.lang.text;

import java.text.Format;
import java.util.Locale;

public abstract interface FormatFactory
{
  public abstract Format getFormat(String paramString1, String paramString2, Locale paramLocale);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang\text\FormatFactory.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */