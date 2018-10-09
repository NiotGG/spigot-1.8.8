package com.avaje.ebeaninternal.server.ddl;

import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;

public abstract interface BeanVisitor
{
  public abstract void visitBegin();
  
  public abstract boolean visitBean(BeanDescriptor<?> paramBeanDescriptor);
  
  public abstract PropertyVisitor visitProperty(BeanProperty paramBeanProperty);
  
  public abstract void visitBeanEnd(BeanDescriptor<?> paramBeanDescriptor);
  
  public abstract void visitEnd();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\ddl\BeanVisitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */