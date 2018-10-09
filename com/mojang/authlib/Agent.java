/*    */ package com.mojang.authlib;
/*    */ 
/*    */ public class Agent {
/*  4 */   public static final Agent MINECRAFT = new Agent("Minecraft", 1);
/*  5 */   public static final Agent SCROLLS = new Agent("Scrolls", 1);
/*    */   private final String name;
/*    */   private final int version;
/*    */   
/*    */   public Agent(String paramString, int paramInt)
/*    */   {
/* 11 */     this.name = paramString;
/* 12 */     this.version = paramInt;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 16 */     return this.name;
/*    */   }
/*    */   
/*    */   public int getVersion() {
/* 20 */     return this.version;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 25 */     return "Agent{name='" + this.name + '\'' + ", version=" + this.version + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\mojang\authlib\Agent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */