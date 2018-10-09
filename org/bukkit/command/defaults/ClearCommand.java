/*     */ package org.bukkit.command.defaults;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ @Deprecated
/*     */ public class ClearCommand extends VanillaCommand
/*     */ {
/*     */   private static List<String> materials;
/*     */   
/*     */   static
/*     */   {
/*  21 */     ArrayList<String> materialList = new ArrayList();
/*  22 */     Material[] arrayOfMaterial; int i = (arrayOfMaterial = Material.values()).length; for (int j = 0; j < i; j++) { Material material = arrayOfMaterial[j];
/*  23 */       materialList.add(material.name());
/*     */     }
/*  25 */     Collections.sort(materialList);
/*  26 */     materials = ImmutableList.copyOf(materialList);
/*     */   }
/*     */   
/*     */   public ClearCommand() {
/*  30 */     super("clear");
/*  31 */     this.description = "Clears the player's inventory. Can specify item and data filters too.";
/*  32 */     this.usageMessage = "/clear <player> [item] [data]";
/*  33 */     setPermission("bukkit.command.clear");
/*     */   }
/*     */   
/*     */   public boolean execute(CommandSender sender, String currentAlias, String[] args)
/*     */   {
/*  38 */     if (!testPermission(sender)) { return true;
/*     */     }
/*  40 */     Player player = null;
/*  41 */     if (args.length > 0) {
/*  42 */       player = Bukkit.getPlayer(args[0]);
/*  43 */     } else if ((sender instanceof Player)) {
/*  44 */       player = (Player)sender;
/*     */     }
/*     */     
/*  47 */     if (player != null) {
/*     */       int id;
/*     */       int id;
/*  50 */       if ((args.length > 1) && (!args[1].equals("-1"))) {
/*  51 */         Material material = Material.matchMaterial(args[1]);
/*  52 */         if (material == null) {
/*  53 */           sender.sendMessage(ChatColor.RED + "There's no item called " + args[1]);
/*  54 */           return false;
/*     */         }
/*     */         
/*  57 */         id = material.getId();
/*     */       } else {
/*  59 */         id = -1;
/*     */       }
/*     */       
/*  62 */       int data = args.length >= 3 ? getInteger(sender, args[2], 0) : -1;
/*  63 */       int count = player.getInventory().clear(id, data);
/*     */       
/*  65 */       org.bukkit.command.Command.broadcastCommandMessage(sender, "Cleared the inventory of " + player.getDisplayName() + ", removing " + count + " items");
/*  66 */     } else if (args.length == 0) {
/*  67 */       sender.sendMessage(ChatColor.RED + "Please provide a player!");
/*     */     } else {
/*  69 */       sender.sendMessage(ChatColor.RED + "Can't find player " + args[0]);
/*     */     }
/*     */     
/*  72 */     return true;
/*     */   }
/*     */   
/*     */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException
/*     */   {
/*  77 */     Validate.notNull(sender, "Sender cannot be null");
/*  78 */     Validate.notNull(args, "Arguments cannot be null");
/*  79 */     Validate.notNull(alias, "Alias cannot be null");
/*     */     
/*  81 */     if (args.length == 1) {
/*  82 */       return super.tabComplete(sender, alias, args);
/*     */     }
/*  84 */     if (args.length == 2) {
/*  85 */       String arg = args[1];
/*  86 */       List<String> materials = materials;
/*  87 */       List<String> completion = null;
/*     */       
/*  89 */       int size = materials.size();
/*  90 */       int i = Collections.binarySearch(materials, arg, String.CASE_INSENSITIVE_ORDER);
/*     */       
/*  92 */       if (i < 0) {}
/*     */       
/*  94 */       for (i = -1 - i; 
/*     */           
/*     */ 
/*  97 */           i < size; i++) {
/*  98 */         String material = (String)materials.get(i);
/*  99 */         if (!org.bukkit.util.StringUtil.startsWithIgnoreCase(material, arg)) break;
/* 100 */         if (completion == null) {
/* 101 */           completion = new ArrayList();
/*     */         }
/* 103 */         completion.add(material);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 109 */       if (completion != null) {
/* 110 */         return completion;
/*     */       }
/*     */     }
/* 113 */     return ImmutableList.of();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\ClearCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */