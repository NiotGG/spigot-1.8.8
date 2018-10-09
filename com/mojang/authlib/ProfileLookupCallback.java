package com.mojang.authlib;

public abstract interface ProfileLookupCallback
{
  public abstract void onProfileLookupSucceeded(GameProfile paramGameProfile);
  
  public abstract void onProfileLookupFailed(GameProfile paramGameProfile, Exception paramException);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\mojang\authlib\ProfileLookupCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */