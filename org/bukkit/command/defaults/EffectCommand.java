/*     */ package org.bukkit.command.defaults;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList.Builder;
/*     */ import java.util.List;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ import org.bukkit.potion.PotionEffectType;
/*     */ 
/*     */ @Deprecated
/*     */ public class EffectCommand extends VanillaCommand
/*     */ {
/*     */   private static final List<String> effects;
/*     */   
/*     */   public EffectCommand()
/*     */   {
/*  18 */     super("effect");
/*  19 */     this.description = "Adds/Removes effects on players";
/*  20 */     this.usageMessage = "/effect <player> <effect|clear> [seconds] [amplifier]";
/*  21 */     setPermission("bukkit.command.effect");
/*     */   }
/*     */   
/*     */   static {
/*  25 */     ImmutableList.Builder<String> builder = com.google.common.collect.ImmutableList.builder();
/*     */     PotionEffectType[] arrayOfPotionEffectType;
/*  27 */     int i = (arrayOfPotionEffectType = PotionEffectType.values()).length; for (int j = 0; j < i; j++) { PotionEffectType type = arrayOfPotionEffectType[j];
/*  28 */       if (type != null) {
/*  29 */         builder.add(type.getName());
/*     */       }
/*     */     }
/*     */     
/*  33 */     effects = builder.build();
/*     */   }
/*     */   
/*     */   public boolean execute(CommandSender sender, String commandLabel, String[] args)
/*     */   {
/*  38 */     if (!testPermission(sender)) {
/*  39 */       return true;
/*     */     }
/*     */     
/*  42 */     if (args.length < 2) {
/*  43 */       sender.sendMessage(getUsage());
/*  44 */       return true;
/*     */     }
/*     */     
/*  47 */     Player player = sender.getServer().getPlayer(args[0]);
/*     */     
/*  49 */     if (player == null) {
/*  50 */       sender.sendMessage(ChatColor.RED + String.format("Player, %s, not found", new Object[] { args[0] }));
/*  51 */       return true;
/*     */     }
/*     */     
/*  54 */     if ("clear".equalsIgnoreCase(args[1])) {
/*  55 */       for (PotionEffect effect : player.getActivePotionEffects()) {
/*  56 */         player.removePotionEffect(effect.getType());
/*     */       }
/*  58 */       sender.sendMessage(String.format("Took all effects from %s", new Object[] { args[0] }));
/*  59 */       return true;
/*     */     }
/*     */     
/*  62 */     PotionEffectType effect = PotionEffectType.getByName(args[1]);
/*     */     
/*  64 */     if (effect == null) {
/*  65 */       effect = PotionEffectType.getById(getInteger(sender, args[1], 0));
/*     */     }
/*     */     
/*  68 */     if (effect == null) {
/*  69 */       sender.sendMessage(ChatColor.RED + String.format("Effect, %s, not found", new Object[] { args[1] }));
/*  70 */       return true;
/*     */     }
/*     */     
/*  73 */     int duration = 600;
/*  74 */     int duration_temp = 30;
/*  75 */     int amplification = 0;
/*     */     
/*  77 */     if (args.length >= 3) {
/*  78 */       duration_temp = getInteger(sender, args[2], 0, 1000000);
/*  79 */       if (effect.isInstant()) {
/*  80 */         duration = duration_temp;
/*     */       } else {
/*  82 */         duration = duration_temp * 20;
/*     */       }
/*  84 */     } else if (effect.isInstant()) {
/*  85 */       duration = 1;
/*     */     }
/*     */     
/*  88 */     if (args.length >= 4) {
/*  89 */       amplification = getInteger(sender, args[3], 0, 255);
/*     */     }
/*     */     
/*  92 */     if (duration_temp == 0) {
/*  93 */       if (!player.hasPotionEffect(effect)) {
/*  94 */         sender.sendMessage(String.format("Couldn't take %s from %s as they do not have the effect", new Object[] { effect.getName(), args[0] }));
/*  95 */         return true;
/*     */       }
/*     */       
/*  98 */       player.removePotionEffect(effect);
/*  99 */       broadcastCommandMessage(sender, String.format("Took %s from %s", new Object[] { effect.getName(), args[0] }));
/*     */     } else {
/* 101 */       PotionEffect applyEffect = new PotionEffect(effect, duration, amplification);
/*     */       
/* 103 */       player.addPotionEffect(applyEffect, true);
/* 104 */       broadcastCommandMessage(sender, String.format("Given %s (ID %d) * %d to %s for %d seconds", new Object[] { effect.getName(), Integer.valueOf(effect.getId()), Integer.valueOf(amplification), args[0], Integer.valueOf(duration_temp) }));
/*     */     }
/*     */     
/* 107 */     return true;
/*     */   }
/*     */   
/*     */   public List<String> tabComplete(CommandSender sender, String commandLabel, String[] args)
/*     */   {
/* 112 */     if (args.length == 1)
/* 113 */       return super.tabComplete(sender, commandLabel, args);
/* 114 */     if (args.length == 2) {
/* 115 */       return (List)org.bukkit.util.StringUtil.copyPartialMatches(args[1], effects, new java.util.ArrayList(effects.size()));
/*     */     }
/*     */     
/* 118 */     return com.google.common.collect.ImmutableList.of();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\EffectCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */