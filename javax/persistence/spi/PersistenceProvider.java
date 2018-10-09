package javax.persistence.spi;

import java.util.Map;
import javax.persistence.EntityManagerFactory;

public abstract interface PersistenceProvider
{
  public abstract EntityManagerFactory createEntityManagerFactory(String paramString, Map paramMap);
  
  public abstract EntityManagerFactory createContainerEntityManagerFactory(PersistenceUnitInfo paramPersistenceUnitInfo, Map paramMap);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\javax\persistence\spi\PersistenceProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */