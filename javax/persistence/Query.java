package javax.persistence;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public abstract interface Query
{
  public abstract List getResultList();
  
  public abstract Object getSingleResult();
  
  public abstract int executeUpdate();
  
  public abstract Query setMaxResults(int paramInt);
  
  public abstract Query setFirstResult(int paramInt);
  
  public abstract Query setHint(String paramString, Object paramObject);
  
  public abstract Query setParameter(String paramString, Object paramObject);
  
  public abstract Query setParameter(String paramString, Date paramDate, TemporalType paramTemporalType);
  
  public abstract Query setParameter(String paramString, Calendar paramCalendar, TemporalType paramTemporalType);
  
  public abstract Query setParameter(int paramInt, Object paramObject);
  
  public abstract Query setParameter(int paramInt, Date paramDate, TemporalType paramTemporalType);
  
  public abstract Query setParameter(int paramInt, Calendar paramCalendar, TemporalType paramTemporalType);
  
  public abstract Query setFlushMode(FlushModeType paramFlushModeType);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\javax\persistence\Query.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */