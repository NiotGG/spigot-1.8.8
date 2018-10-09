/*    */ package org.bukkit;
/*    */ 
/*    */ import java.util.Date;
/*    */ 
/*    */ public abstract interface BanList { public abstract BanEntry getBanEntry(String paramString);
/*    */   
/*    */   public abstract BanEntry addBan(String paramString1, String paramString2, Date paramDate, String paramString3);
/*    */   
/*    */   public abstract java.util.Set<BanEntry> getBanEntries();
/*    */   
/*    */   public abstract boolean isBanned(String paramString);
/*    */   
/*    */   public abstract void pardon(String paramString);
/*    */   
/* 15 */   public static enum Type { NAME, 
/*    */     
/*    */ 
/*    */ 
/* 19 */     IP;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\BanList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */