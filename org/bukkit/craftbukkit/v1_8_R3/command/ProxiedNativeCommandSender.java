/*     */ package org.bukkit.craftbukkit.v1_8_R3.command;
/*     */ 
/*     */ import java.util.Set;
/*     */ import net.minecraft.server.v1_8_R3.ICommandListener;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.command.ProxiedCommandSender;
/*     */ import org.bukkit.permissions.Permission;
/*     */ import org.bukkit.permissions.PermissionAttachment;
/*     */ import org.bukkit.permissions.PermissionAttachmentInfo;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ public class ProxiedNativeCommandSender
/*     */   implements ProxiedCommandSender
/*     */ {
/*     */   private final ICommandListener orig;
/*     */   private final CommandSender caller;
/*     */   private final CommandSender callee;
/*     */   
/*     */   public ProxiedNativeCommandSender(ICommandListener orig, CommandSender caller, CommandSender callee)
/*     */   {
/*  22 */     this.orig = orig;
/*  23 */     this.caller = caller;
/*  24 */     this.callee = callee;
/*     */   }
/*     */   
/*     */   public ICommandListener getHandle() {
/*  28 */     return this.orig;
/*     */   }
/*     */   
/*     */   public CommandSender getCaller()
/*     */   {
/*  33 */     return this.caller;
/*     */   }
/*     */   
/*     */   public CommandSender getCallee()
/*     */   {
/*  38 */     return this.callee;
/*     */   }
/*     */   
/*     */   public void sendMessage(String message)
/*     */   {
/*  43 */     getCaller().sendMessage(message);
/*     */   }
/*     */   
/*     */   public void sendMessage(String[] messages)
/*     */   {
/*  48 */     getCaller().sendMessage(messages);
/*     */   }
/*     */   
/*     */   public Server getServer()
/*     */   {
/*  53 */     return getCallee().getServer();
/*     */   }
/*     */   
/*     */   public String getName()
/*     */   {
/*  58 */     return getCallee().getName();
/*     */   }
/*     */   
/*     */   public boolean isPermissionSet(String name)
/*     */   {
/*  63 */     return getCaller().isPermissionSet(name);
/*     */   }
/*     */   
/*     */   public boolean isPermissionSet(Permission perm)
/*     */   {
/*  68 */     return getCaller().isPermissionSet(perm);
/*     */   }
/*     */   
/*     */   public boolean hasPermission(String name)
/*     */   {
/*  73 */     return getCaller().hasPermission(name);
/*     */   }
/*     */   
/*     */   public boolean hasPermission(Permission perm)
/*     */   {
/*  78 */     return getCaller().hasPermission(perm);
/*     */   }
/*     */   
/*     */   public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value)
/*     */   {
/*  83 */     return getCaller().addAttachment(plugin, name, value);
/*     */   }
/*     */   
/*     */   public PermissionAttachment addAttachment(Plugin plugin)
/*     */   {
/*  88 */     return getCaller().addAttachment(plugin);
/*     */   }
/*     */   
/*     */   public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value, int ticks)
/*     */   {
/*  93 */     return getCaller().addAttachment(plugin, name, value, ticks);
/*     */   }
/*     */   
/*     */   public PermissionAttachment addAttachment(Plugin plugin, int ticks)
/*     */   {
/*  98 */     return getCaller().addAttachment(plugin, ticks);
/*     */   }
/*     */   
/*     */   public void removeAttachment(PermissionAttachment attachment)
/*     */   {
/* 103 */     getCaller().removeAttachment(attachment);
/*     */   }
/*     */   
/*     */   public void recalculatePermissions()
/*     */   {
/* 108 */     getCaller().recalculatePermissions();
/*     */   }
/*     */   
/*     */   public Set<PermissionAttachmentInfo> getEffectivePermissions()
/*     */   {
/* 113 */     return getCaller().getEffectivePermissions();
/*     */   }
/*     */   
/*     */   public boolean isOp()
/*     */   {
/* 118 */     return getCaller().isOp();
/*     */   }
/*     */   
/*     */   public void setOp(boolean value)
/*     */   {
/* 123 */     getCaller().setOp(value);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\command\ProxiedNativeCommandSender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */