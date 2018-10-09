/*    */ package org.bukkit.util.permissions;
/*    */ 
/*    */ import org.bukkit.permissions.Permission;
/*    */ import org.bukkit.permissions.PermissionDefault;
/*    */ 
/*    */ public final class CommandPermissions
/*    */ {
/*    */   private static final String ROOT = "bukkit.command";
/*    */   private static final String PREFIX = "bukkit.command.";
/*    */   
/*    */   public static Permission registerPermissions(Permission parent)
/*    */   {
/* 13 */     Permission commands = DefaultPermissions.registerPermission("bukkit.command", "Gives the user the ability to use all CraftBukkit commands", parent);
/*    */     
/* 15 */     DefaultPermissions.registerPermission("bukkit.command.help", "Allows the user to view the vanilla help menu", PermissionDefault.TRUE, commands);
/* 16 */     DefaultPermissions.registerPermission("bukkit.command.plugins", "Allows the user to view the list of plugins running on this server", PermissionDefault.TRUE, commands);
/* 17 */     DefaultPermissions.registerPermission("bukkit.command.reload", "Allows the user to reload the server settings", PermissionDefault.OP, commands);
/* 18 */     DefaultPermissions.registerPermission("bukkit.command.version", "Allows the user to view the version of the server", PermissionDefault.TRUE, commands);
/*    */     
/* 20 */     commands.recalculatePermissibles();
/* 21 */     return commands;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\util\permissions\CommandPermissions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */