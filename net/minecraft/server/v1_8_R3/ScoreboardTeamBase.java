/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Collection;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ public abstract class ScoreboardTeamBase
/*    */ {
/*    */   public boolean isAlly(ScoreboardTeamBase paramScoreboardTeamBase)
/*    */   {
/* 12 */     if (paramScoreboardTeamBase == null) {
/* 13 */       return false;
/*    */     }
/* 15 */     if (this == paramScoreboardTeamBase) {
/* 16 */       return true;
/*    */     }
/* 18 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */   public abstract String getName();
/*    */   
/*    */ 
/*    */   public abstract String getFormattedName(String paramString);
/*    */   
/*    */   public abstract boolean allowFriendlyFire();
/*    */   
/*    */   public abstract Collection<String> getPlayerNameSet();
/*    */   
/*    */   public abstract EnumNameTagVisibility j();
/*    */   
/*    */   public static enum EnumNameTagVisibility
/*    */   {
/*    */     private static Map<String, EnumNameTagVisibility> g;
/*    */     public final String e;
/*    */     public final int f;
/*    */     
/*    */     static
/*    */     {
/* 41 */       g = Maps.newHashMap();
/*    */       
/*    */ 
/* 44 */       for (EnumNameTagVisibility localEnumNameTagVisibility : values()) {
/* 45 */         g.put(localEnumNameTagVisibility.e, localEnumNameTagVisibility);
/*    */       }
/*    */     }
/*    */     
/*    */     public static String[] a() {
/* 50 */       return (String[])g.keySet().toArray(new String[g.size()]);
/*    */     }
/*    */     
/*    */     public static EnumNameTagVisibility a(String paramString) {
/* 54 */       return (EnumNameTagVisibility)g.get(paramString);
/*    */     }
/*    */     
/*    */ 
/*    */ 
/*    */     private EnumNameTagVisibility(String paramString, int paramInt)
/*    */     {
/* 61 */       this.e = paramString;
/* 62 */       this.f = paramInt;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ScoreboardTeamBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */