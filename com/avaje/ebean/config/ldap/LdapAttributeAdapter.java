package com.avaje.ebean.config.ldap;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;

public abstract interface LdapAttributeAdapter
{
  public abstract Object readAttribute(Attribute paramAttribute)
    throws NamingException;
  
  public abstract Attribute createAttribute(Object paramObject);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\ldap\LdapAttributeAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */