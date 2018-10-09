package org.apache.logging.log4j.core.appender.db.nosql;

public abstract interface NoSQLObject<W>
{
  public abstract void set(String paramString, Object paramObject);
  
  public abstract void set(String paramString, NoSQLObject<W> paramNoSQLObject);
  
  public abstract void set(String paramString, Object[] paramArrayOfObject);
  
  public abstract void set(String paramString, NoSQLObject<W>[] paramArrayOfNoSQLObject);
  
  public abstract W unwrap();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\nosql\NoSQLObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */