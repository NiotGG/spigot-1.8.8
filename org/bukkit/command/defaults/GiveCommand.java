/*     */ package org.bukkit.command.defaults;
/*     */ 
/*     */ import com.google.common.base.Joiner;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.UnsafeValues;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ @Deprecated
/*     */ public class GiveCommand extends VanillaCommand
/*     */ {
/*     */   private static List<String> materials;
/*     */   
/*     */   static
/*     */   {
/*  25 */     ArrayList<String> materialList = new ArrayList();
/*  26 */     Material[] arrayOfMaterial; int i = (arrayOfMaterial = Material.values()).length; for (int j = 0; j < i; j++) { Material material = arrayOfMaterial[j];
/*  27 */       materialList.add(material.name());
/*     */     }
/*  29 */     Collections.sort(materialList);
/*  30 */     materials = ImmutableList.copyOf(materialList);
/*     */   }
/*     */   
/*     */   public GiveCommand() {
/*  34 */     super("give");
/*  35 */     this.description = "Gives the specified player a certain amount of items";
/*  36 */     this.usageMessage = "/give <player> <item> [amount [data]]";
/*  37 */     setPermission("bukkit.command.give");
/*     */   }
/*     */   
/*     */   public boolean execute(CommandSender sender, String currentAlias, String[] args)
/*     */   {
/*  42 */     if (!testPermission(sender)) return true;
/*  43 */     if (args.length < 2) {
/*  44 */       sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/*  45 */       return false;
/*     */     }
/*     */     
/*  48 */     Player player = Bukkit.getPlayerExact(args[0]);
/*     */     
/*  50 */     if (player != null) {
/*  51 */       Material material = Material.matchMaterial(args[1]);
/*     */       
/*  53 */       if (material == null) {
/*  54 */         material = Bukkit.getUnsafe().getMaterialFromInternalName(args[1]);
/*     */       }
/*     */       
/*  57 */       if (material != null) {
/*  58 */         int amount = 1;
/*  59 */         short data = 0;
/*     */         
/*  61 */         if (args.length >= 3) {
/*  62 */           amount = getInteger(sender, args[2], 1, 64);
/*     */           
/*  64 */           if (args.length >= 4) {
/*     */             try {
/*  66 */               data = Short.parseShort(args[3]);
/*     */             }
/*     */             catch (NumberFormatException localNumberFormatException) {}
/*     */           }
/*     */         }
/*  71 */         ItemStack stack = new ItemStack(material, amount, data);
/*     */         
/*  73 */         if (args.length >= 5) {
/*     */           try {
/*  75 */             stack = Bukkit.getUnsafe().modifyItemStack(stack, Joiner.on(' ').join(Arrays.asList(args).subList(4, args.length)));
/*     */           } catch (Throwable localThrowable) {
/*  77 */             player.sendMessage("Not a valid tag");
/*  78 */             return true;
/*     */           }
/*     */         }
/*     */         
/*  82 */         player.getInventory().addItem(new ItemStack[] { stack });
/*     */         
/*  84 */         org.bukkit.command.Command.broadcastCommandMessage(sender, "Gave " + player.getName() + " some " + material.getId() + " (" + material + ")");
/*     */       } else {
/*  86 */         sender.sendMessage("There's no item called " + args[1]);
/*     */       }
/*     */     } else {
/*  89 */       sender.sendMessage("Can't find player " + args[0]);
/*     */     }
/*     */     
/*  92 */     return true;
/*     */   }
/*     */   
/*     */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException
/*     */   {
/*  97 */     Validate.notNull(sender, "Sender cannot be null");
/*  98 */     Validate.notNull(args, "Arguments cannot be null");
/*  99 */     Validate.notNull(alias, "Alias cannot be null");
/*     */     
/* 101 */     if (args.length == 1) {
/* 102 */       return super.tabComplete(sender, alias, args);
/*     */     }
/* 104 */     if (args.length == 2) {
/* 105 */       String arg = args[1];
/* 106 */       List<String> materials = materials;
/* 107 */       List<String> completion = new ArrayList();
/*     */       
/* 109 */       int size = materials.size();
/* 110 */       int i = Collections.binarySearch(materials, arg, String.CASE_INSENSITIVE_ORDER);
/*     */       
/* 112 */       if (i < 0) {}
/*     */       
/* 114 */       for (i = -1 - i; 
/*     */           
/*     */ 
/* 117 */           i < size; i++) {
/* 118 */         String material = (String)materials.get(i);
/* 119 */         if (!org.bukkit.util.StringUtil.startsWithIgnoreCase(material, arg)) break;
/* 120 */         completion.add(material);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 126 */       return Bukkit.getUnsafe().tabCompleteInternalMaterialName(arg, completion);
/*     */     }
/* 128 */     return ImmutableList.of();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\GiveCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */