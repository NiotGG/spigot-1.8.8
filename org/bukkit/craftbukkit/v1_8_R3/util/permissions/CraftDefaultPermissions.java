/*    */ package org.bukkit.craftbukkit.v1_8_R3.util.permissions;
/*    */ 
/*    */ import org.bukkit.permissions.Permission;
/*    */ import org.bukkit.util.permissions.DefaultPermissions;
/*    */ 
/*    */ public final class CraftDefaultPermissions
/*    */ {
/*    */   private static final String ROOT = "minecraft";
/*    */   
/*    */   public static void registerCorePermissions()
/*    */   {
/* 12 */     Permission parent = DefaultPermissions.registerPermission("minecraft", "Gives the user the ability to use all vanilla utilities and commands");
/* 13 */     CommandPermissions.registerPermissions(parent);
/* 14 */     parent.recalculatePermissibles();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\util\permissions\CraftDefaultPermissions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */