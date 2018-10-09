package com.mojang.authlib;

import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.properties.PropertyMap;
import java.util.Map;

public abstract interface UserAuthentication
{
  public abstract boolean canLogIn();
  
  public abstract void logIn()
    throws AuthenticationException;
  
  public abstract void logOut();
  
  public abstract boolean isLoggedIn();
  
  public abstract boolean canPlayOnline();
  
  public abstract GameProfile[] getAvailableProfiles();
  
  public abstract GameProfile getSelectedProfile();
  
  public abstract void selectGameProfile(GameProfile paramGameProfile)
    throws AuthenticationException;
  
  public abstract void loadFromStorage(Map<String, Object> paramMap);
  
  public abstract Map<String, Object> saveForStorage();
  
  public abstract void setUsername(String paramString);
  
  public abstract void setPassword(String paramString);
  
  public abstract String getAuthenticatedToken();
  
  public abstract String getUserID();
  
  public abstract PropertyMap getUserProperties();
  
  public abstract UserType getUserType();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\mojang\authlib\UserAuthentication.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */