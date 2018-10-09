package com.avaje.ebean.config.ldap;

import javax.naming.directory.DirContext;

public abstract interface LdapContextFactory
{
  public abstract DirContext createContext();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\ldap\LdapContextFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */