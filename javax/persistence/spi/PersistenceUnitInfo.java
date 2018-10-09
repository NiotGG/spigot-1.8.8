package javax.persistence.spi;

import java.net.URL;
import java.util.List;
import java.util.Properties;
import javax.sql.DataSource;

public abstract interface PersistenceUnitInfo
{
  public abstract String getPersistenceUnitName();
  
  public abstract String getPersistenceProviderClassName();
  
  public abstract PersistenceUnitTransactionType getTransactionType();
  
  public abstract DataSource getJtaDataSource();
  
  public abstract DataSource getNonJtaDataSource();
  
  public abstract List<String> getMappingFileNames();
  
  public abstract List<URL> getJarFileUrls();
  
  public abstract URL getPersistenceUnitRootUrl();
  
  public abstract List<String> getManagedClassNames();
  
  public abstract boolean excludeUnlistedClasses();
  
  public abstract Properties getProperties();
  
  public abstract ClassLoader getClassLoader();
  
  public abstract void addTransformer(ClassTransformer paramClassTransformer);
  
  public abstract ClassLoader getNewTempClassLoader();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\javax\persistence\spi\PersistenceUnitInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */