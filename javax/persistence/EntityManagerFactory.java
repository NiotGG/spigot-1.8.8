package javax.persistence;

import java.util.Map;

public abstract interface EntityManagerFactory
{
  public abstract EntityManager createEntityManager();
  
  public abstract EntityManager createEntityManager(Map paramMap);
  
  public abstract void close();
  
  public abstract boolean isOpen();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\javax\persistence\EntityManagerFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */