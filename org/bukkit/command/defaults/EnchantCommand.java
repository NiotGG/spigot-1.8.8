/*     */ package org.bukkit.command.defaults;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.apache.commons.lang.WordUtils;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.util.StringUtil;
/*     */ 
/*     */ @Deprecated
/*     */ public class EnchantCommand extends VanillaCommand
/*     */ {
/*  24 */   private static final List<String> ENCHANTMENT_NAMES = new ArrayList();
/*     */   
/*     */   public EnchantCommand() {
/*  27 */     super("enchant");
/*  28 */     this.description = "Adds enchantments to the item the player is currently holding. Specify 0 for the level to remove an enchantment. Specify force to ignore normal enchantment restrictions";
/*  29 */     this.usageMessage = "/enchant <player> <enchantment> [level|max|0] [force]";
/*  30 */     setPermission("bukkit.command.enchant");
/*     */   }
/*     */   
/*     */   public boolean execute(CommandSender sender, String commandLabel, String[] args)
/*     */   {
/*  35 */     if (!testPermission(sender)) return true;
/*  36 */     if (args.length < 2) {
/*  37 */       sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/*  38 */       return false;
/*     */     }
/*     */     
/*  41 */     boolean force = false;
/*  42 */     if (args.length > 2) {
/*  43 */       force = args[2].equalsIgnoreCase("force");
/*     */     }
/*     */     
/*  46 */     Player player = Bukkit.getPlayerExact(args[0]);
/*  47 */     if (player == null) {
/*  48 */       sender.sendMessage("Can't find player " + args[0]);
/*     */     } else {
/*  50 */       ItemStack item = player.getItemInHand();
/*  51 */       if (item.getType() == Material.AIR) {
/*  52 */         sender.sendMessage("The player isn't holding an item");
/*     */       } else {
/*  54 */         String itemName = item.getType().toString().replaceAll("_", " ");
/*  55 */         itemName = WordUtils.capitalizeFully(itemName);
/*     */         
/*  57 */         Enchantment enchantment = getEnchantment(args[1].toUpperCase());
/*  58 */         if (enchantment == null) {
/*  59 */           sender.sendMessage(String.format("Enchantment does not exist: %s", new Object[] { args[1] }));
/*     */         } else {
/*  61 */           String enchantmentName = enchantment.getName().replaceAll("_", " ");
/*  62 */           enchantmentName = WordUtils.capitalizeFully(enchantmentName);
/*     */           
/*  64 */           if ((!force) && (!enchantment.canEnchantItem(item))) {
/*  65 */             sender.sendMessage(String.format("%s cannot be applied to %s", new Object[] { enchantmentName, itemName }));
/*     */           } else {
/*  67 */             int level = 1;
/*  68 */             if (args.length > 2) {
/*  69 */               Integer integer = getInteger(args[2]);
/*  70 */               int minLevel = enchantment.getStartLevel();
/*  71 */               int maxLevel = force ? 32767 : enchantment.getMaxLevel();
/*     */               
/*  73 */               if (integer != null) {
/*  74 */                 if (integer.intValue() == 0) {
/*  75 */                   item.removeEnchantment(enchantment);
/*  76 */                   Command.broadcastCommandMessage(sender, String.format("Removed %s on %s's %s", new Object[] { enchantmentName, player.getName(), itemName }));
/*  77 */                   return true;
/*     */                 }
/*     */                 
/*  80 */                 if ((integer.intValue() < minLevel) || (integer.intValue() > maxLevel)) {
/*  81 */                   sender.sendMessage(String.format("Level for enchantment %s must be between %d and %d", new Object[] { enchantmentName, Integer.valueOf(minLevel), Integer.valueOf(maxLevel) }));
/*  82 */                   sender.sendMessage("Specify 0 for level to remove an enchantment");
/*  83 */                   return true;
/*     */                 }
/*     */                 
/*  86 */                 level = integer.intValue();
/*     */               }
/*     */               
/*  89 */               if ("max".equals(args[2])) {
/*  90 */                 level = maxLevel;
/*     */               }
/*     */             }
/*     */             
/*  94 */             Map<Enchantment, Integer> enchantments = item.getEnchantments();
/*  95 */             boolean conflicts = false;
/*     */             
/*  97 */             if ((!force) && (!enchantments.isEmpty())) {
/*  98 */               for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
/*  99 */                 Enchantment enchant = (Enchantment)entry.getKey();
/*     */                 
/* 101 */                 if ((!enchant.equals(enchantment)) && 
/* 102 */                   (enchant.conflictsWith(enchantment))) {
/* 103 */                   sender.sendMessage(String.format("Can't apply the enchantment %s on an item with the enchantment %s", new Object[] { enchantmentName, WordUtils.capitalizeFully(enchant.getName().replaceAll("_", " ")) }));
/* 104 */                   conflicts = true;
/* 105 */                   break;
/*     */                 }
/*     */               }
/*     */             }
/*     */             
/* 110 */             if (!conflicts) {
/* 111 */               item.addUnsafeEnchantment(enchantment, level);
/*     */               
/* 113 */               Command.broadcastCommandMessage(sender, String.format("Applied %s (Lvl %d) on %s's %s", new Object[] { enchantmentName, Integer.valueOf(level), player.getName(), itemName }), false);
/* 114 */               sender.sendMessage(String.format("Enchanting succeeded, applied %s (Lvl %d) onto your %s", new Object[] { enchantmentName, Integer.valueOf(level), itemName }));
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 120 */     return true;
/*     */   }
/*     */   
/*     */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException
/*     */   {
/* 125 */     Validate.notNull(sender, "Sender cannot be null");
/* 126 */     Validate.notNull(args, "Arguments cannot be null");
/* 127 */     Validate.notNull(alias, "Alias cannot be null");
/*     */     
/* 129 */     if (args.length == 1) {
/* 130 */       return super.tabComplete(sender, alias, args);
/*     */     }
/*     */     
/* 133 */     if (args.length == 2) {
/* 134 */       return (List)StringUtil.copyPartialMatches(args[1], ENCHANTMENT_NAMES, new ArrayList(ENCHANTMENT_NAMES.size()));
/*     */     }
/*     */     
/* 137 */     if (((args.length == 3) || (args.length == 4)) && 
/* 138 */       (!args[(args.length - 2)].equalsIgnoreCase("force"))) {
/* 139 */       return ImmutableList.of("force");
/*     */     }
/*     */     
/*     */ 
/* 143 */     return ImmutableList.of();
/*     */   }
/*     */   
/*     */   private Enchantment getEnchantment(String lookup) {
/* 147 */     Enchantment enchantment = Enchantment.getByName(lookup);
/*     */     
/* 149 */     if (enchantment == null) {
/* 150 */       Integer id = getInteger(lookup);
/* 151 */       if (id != null) {
/* 152 */         enchantment = Enchantment.getById(id.intValue());
/*     */       }
/*     */     }
/*     */     
/* 156 */     return enchantment;
/*     */   }
/*     */   
/*     */   public static void buildEnchantments() {
/* 160 */     if (!ENCHANTMENT_NAMES.isEmpty()) {
/* 161 */       throw new IllegalStateException("Enchantments have already been built!");
/*     */     }
/*     */     Enchantment[] arrayOfEnchantment;
/* 164 */     int i = (arrayOfEnchantment = Enchantment.values()).length; for (int j = 0; j < i; j++) { Enchantment enchantment = arrayOfEnchantment[j];
/* 165 */       ENCHANTMENT_NAMES.add(enchantment.getName());
/*     */     }
/*     */     
/* 168 */     Collections.sort(ENCHANTMENT_NAMES);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\EnchantCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */