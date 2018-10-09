package javax.persistence;

public abstract interface EntityTransaction
{
  public abstract void begin();
  
  public abstract void commit();
  
  public abstract void rollback();
  
  public abstract void setRollbackOnly();
  
  public abstract boolean getRollbackOnly();
  
  public abstract boolean isActive();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\javax\persistence\EntityTransaction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */