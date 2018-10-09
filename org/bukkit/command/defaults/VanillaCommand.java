/*     */ package org.bukkit.command.defaults;
/*     */ 
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandSender;
/*     */ 
/*     */ @Deprecated
/*     */ public abstract class VanillaCommand extends Command
/*     */ {
/*     */   static final int MAX_COORD = 30000000;
/*     */   static final int MIN_COORD_MINUS_ONE = -30000001;
/*     */   static final int MIN_COORD = -30000000;
/*     */   
/*     */   protected VanillaCommand(String name)
/*     */   {
/*  15 */     super(name);
/*     */   }
/*     */   
/*     */   protected VanillaCommand(String name, String description, String usageMessage, java.util.List<String> aliases) {
/*  19 */     super(name, description, usageMessage, aliases);
/*     */   }
/*     */   
/*     */   public boolean matches(String input) {
/*  23 */     return input.equalsIgnoreCase(getName());
/*     */   }
/*     */   
/*     */   protected int getInteger(CommandSender sender, String value, int min) {
/*  27 */     return getInteger(sender, value, min, Integer.MAX_VALUE);
/*     */   }
/*     */   
/*     */   int getInteger(CommandSender sender, String value, int min, int max) {
/*  31 */     return getInteger(sender, value, min, max, false);
/*     */   }
/*     */   
/*     */   int getInteger(CommandSender sender, String value, int min, int max, boolean Throws) {
/*  35 */     int i = min;
/*     */     try
/*     */     {
/*  38 */       i = Integer.valueOf(value).intValue();
/*     */     } catch (NumberFormatException localNumberFormatException) {
/*  40 */       if (Throws) {
/*  41 */         throw new NumberFormatException(String.format("%s is not a valid number", new Object[] { value }));
/*     */       }
/*     */     }
/*     */     
/*  45 */     if (i < min) {
/*  46 */       i = min;
/*  47 */     } else if (i > max) {
/*  48 */       i = max;
/*     */     }
/*     */     
/*  51 */     return i;
/*     */   }
/*     */   
/*     */   Integer getInteger(String value) {
/*     */     try {
/*  56 */       return Integer.valueOf(value);
/*     */     } catch (NumberFormatException localNumberFormatException) {}
/*  58 */     return null;
/*     */   }
/*     */   
/*     */   public static double getRelativeDouble(double original, CommandSender sender, String input)
/*     */   {
/*  63 */     if (input.startsWith("~")) {
/*  64 */       double value = getDouble(sender, input.substring(1));
/*  65 */       if (value == -3.0000001E7D) {
/*  66 */         return -3.0000001E7D;
/*     */       }
/*  68 */       return original + value;
/*     */     }
/*  70 */     return getDouble(sender, input);
/*     */   }
/*     */   
/*     */   public static double getDouble(CommandSender sender, String input)
/*     */   {
/*     */     try {
/*  76 */       return Double.parseDouble(input);
/*     */     } catch (NumberFormatException localNumberFormatException) {}
/*  78 */     return -3.0000001E7D;
/*     */   }
/*     */   
/*     */   public static double getDouble(CommandSender sender, String input, double min, double max)
/*     */   {
/*  83 */     double result = getDouble(sender, input);
/*     */     
/*     */ 
/*  86 */     if (result < min) {
/*  87 */       result = min;
/*  88 */     } else if (result > max) {
/*  89 */       result = max;
/*     */     }
/*     */     
/*  92 */     return result;
/*     */   }
/*     */   
/*     */   String createString(String[] args, int start) {
/*  96 */     return createString(args, start, " ");
/*     */   }
/*     */   
/*     */   String createString(String[] args, int start, String glue) {
/* 100 */     StringBuilder string = new StringBuilder();
/*     */     
/* 102 */     for (int x = start; x < args.length; x++) {
/* 103 */       string.append(args[x]);
/* 104 */       if (x != args.length - 1) {
/* 105 */         string.append(glue);
/*     */       }
/*     */     }
/*     */     
/* 109 */     return string.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\VanillaCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */