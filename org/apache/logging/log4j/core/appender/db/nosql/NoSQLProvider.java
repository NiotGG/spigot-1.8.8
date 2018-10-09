package org.apache.logging.log4j.core.appender.db.nosql;

public abstract interface NoSQLProvider<C extends NoSQLConnection<?, ? extends NoSQLObject<?>>>
{
  public abstract C getConnection();
  
  public abstract String toString();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\nosql\NoSQLProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */