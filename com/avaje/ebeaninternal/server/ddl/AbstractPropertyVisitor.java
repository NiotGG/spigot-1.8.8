package com.avaje.ebeaninternal.server.ddl;

import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyCompound;

public abstract class AbstractPropertyVisitor
  implements PropertyVisitor
{
  public void visitEmbedded(BeanPropertyAssocOne<?> p) {}
  
  public void visitEmbeddedScalar(BeanProperty p, BeanPropertyAssocOne<?> embedded) {}
  
  public void visitMany(BeanPropertyAssocMany<?> p) {}
  
  public void visitOneExported(BeanPropertyAssocOne<?> p) {}
  
  public void visitOneImported(BeanPropertyAssocOne<?> p) {}
  
  public void visitScalar(BeanProperty p) {}
  
  public void visitCompound(BeanPropertyCompound p) {}
  
  public void visitCompoundScalar(BeanPropertyCompound compound, BeanProperty p) {}
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\ddl\AbstractPropertyVisitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */