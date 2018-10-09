package org.apache.logging.log4j.core.appender.db.nosql;

import java.io.Closeable;

public abstract interface NoSQLConnection<W, T extends NoSQLObject<W>>
  extends Closeable
{
  public abstract T createObject();
  
  public abstract T[] createList(int paramInt);
  
  public abstract void insertObject(NoSQLObject<W> paramNoSQLObject);
  
  public abstract void close();
  
  public abstract boolean isClosed();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\nosql\NoSQLConnection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */