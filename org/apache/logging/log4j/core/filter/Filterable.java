package org.apache.logging.log4j.core.filter;

import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;

public abstract interface Filterable
{
  public abstract void addFilter(Filter paramFilter);
  
  public abstract void removeFilter(Filter paramFilter);
  
  public abstract Filter getFilter();
  
  public abstract boolean hasFilter();
  
  public abstract boolean isFiltered(LogEvent paramLogEvent);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\filter\Filterable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */