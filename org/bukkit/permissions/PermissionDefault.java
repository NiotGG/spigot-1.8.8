/*    */ package org.bukkit.permissions;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum PermissionDefault
/*    */ {
/* 10 */   TRUE(new String[] { "true" }), 
/* 11 */   FALSE(new String[] { "false" }), 
/* 12 */   OP(new String[] { "op", "isop", "operator", "isoperator", "admin", "isadmin" }), 
/* 13 */   NOT_OP(new String[] { "!op", "notop", "!operator", "notoperator", "!admin", "notadmin" });
/*    */   
/*    */   private final String[] names;
/*    */   private static final Map<String, PermissionDefault> lookup;
/*    */   
/*    */   private PermissionDefault(String... names) {
/* 19 */     this.names = names;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean getValue(boolean op)
/*    */   {
/* 30 */     switch (this) {
/*    */     case FALSE: 
/* 32 */       return true;
/*    */     case NOT_OP: 
/* 34 */       return false;
/*    */     case OP: 
/* 36 */       return op;
/*    */     case TRUE: 
/* 38 */       return !op;
/*    */     }
/* 40 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static PermissionDefault getByName(String name)
/*    */   {
/* 51 */     return (PermissionDefault)lookup.get(name.toLowerCase().replaceAll("[^a-z!]", ""));
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 56 */     return this.names[0];
/*    */   }
/*    */   
/*    */   static
/*    */   {
/* 16 */     lookup = new HashMap();
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     PermissionDefault[] arrayOfPermissionDefault;
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 60 */     int i = (arrayOfPermissionDefault = values()).length; for (int j = 0; j < i; j++) { PermissionDefault value = arrayOfPermissionDefault[j];
/* 61 */       String[] arrayOfString; int k = (arrayOfString = value.names).length; for (int m = 0; m < k; m++) { String name = arrayOfString[m];
/* 62 */         lookup.put(name, value);
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\permissions\PermissionDefault.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */