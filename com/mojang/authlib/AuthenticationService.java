package com.mojang.authlib;

import com.mojang.authlib.minecraft.MinecraftSessionService;

public abstract interface AuthenticationService
{
  public abstract UserAuthentication createUserAuthentication(Agent paramAgent);
  
  public abstract MinecraftSessionService createMinecraftSessionService();
  
  public abstract GameProfileRepository createProfileRepository();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\mojang\authlib\AuthenticationService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */