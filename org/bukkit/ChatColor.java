/*     */ package org.bukkit;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.commons.lang.Validate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum ChatColor
/*     */ {
/*  14 */   BLACK(
/*     */   
/*     */ 
/*  17 */     '0', 0), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  23 */   DARK_BLUE(
/*     */   
/*     */ 
/*  26 */     '1', 1), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  32 */   DARK_GREEN(
/*     */   
/*     */ 
/*  35 */     '2', 2), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  41 */   DARK_AQUA(
/*     */   
/*     */ 
/*  44 */     '3', 3), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  50 */   DARK_RED(
/*     */   
/*     */ 
/*  53 */     '4', 4), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  59 */   DARK_PURPLE(
/*     */   
/*     */ 
/*  62 */     '5', 5), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  68 */   GOLD(
/*     */   
/*     */ 
/*  71 */     '6', 6), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  77 */   GRAY(
/*     */   
/*     */ 
/*  80 */     '7', 7), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  86 */   DARK_GRAY(
/*     */   
/*     */ 
/*  89 */     '8', 8), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  95 */   BLUE(
/*     */   
/*     */ 
/*  98 */     '9', 9), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 104 */   GREEN(
/*     */   
/*     */ 
/* 107 */     'a', 10), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 113 */   AQUA(
/*     */   
/*     */ 
/* 116 */     'b', 11), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 122 */   RED(
/*     */   
/*     */ 
/* 125 */     'c', 12), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 131 */   LIGHT_PURPLE(
/*     */   
/*     */ 
/* 134 */     'd', 13), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 140 */   YELLOW(
/*     */   
/*     */ 
/* 143 */     'e', 14), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 149 */   WHITE(
/*     */   
/*     */ 
/* 152 */     'f', 15), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 158 */   MAGIC(
/*     */   
/*     */ 
/* 161 */     'k', 16, true), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 167 */   BOLD(
/*     */   
/*     */ 
/* 170 */     'l', 17, true), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 176 */   STRIKETHROUGH(
/*     */   
/*     */ 
/* 179 */     'm', 18, true), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 185 */   UNDERLINE(
/*     */   
/*     */ 
/* 188 */     'n', 19, true), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 194 */   ITALIC(
/*     */   
/*     */ 
/* 197 */     'o', 20, true), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 203 */   RESET(
/*     */   
/*     */ 
/* 206 */     'r', 21);
/*     */   
/*     */ 
/*     */   public static final char COLOR_CHAR = '§';
/*     */   
/*     */   private static final Pattern STRIP_COLOR_PATTERN;
/*     */   
/*     */   private final int intCode;
/*     */   
/*     */   private final char code;
/*     */   
/*     */   private final boolean isFormat;
/*     */   
/*     */   private final String toString;
/*     */   
/*     */   private static final Map<Integer, ChatColor> BY_ID;
/*     */   
/*     */   private static final Map<Character, ChatColor> BY_CHAR;
/*     */   
/*     */ 
/*     */   private ChatColor(char code, int intCode)
/*     */   {
/* 228 */     this(code, intCode, false);
/*     */   }
/*     */   
/*     */   private ChatColor(char code, int intCode, boolean isFormat) {
/* 232 */     this.code = code;
/* 233 */     this.intCode = intCode;
/* 234 */     this.isFormat = isFormat;
/* 235 */     this.toString = new String(new char[] { '§', code });
/*     */   }
/*     */   
/*     */   public net.md_5.bungee.api.ChatColor asBungee() {
/* 239 */     return net.md_5.bungee.api.ChatColor.RESET;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public char getChar()
/*     */   {
/* 248 */     return this.code;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 253 */     return this.toString;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isFormat()
/*     */   {
/* 262 */     return this.isFormat;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isColor()
/*     */   {
/* 271 */     return (!this.isFormat) && (this != RESET);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ChatColor getByChar(char code)
/*     */   {
/* 282 */     return (ChatColor)BY_CHAR.get(Character.valueOf(code));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ChatColor getByChar(String code)
/*     */   {
/* 293 */     Validate.notNull(code, "Code cannot be null");
/* 294 */     Validate.isTrue(code.length() > 0, "Code must have at least one char");
/*     */     
/* 296 */     return (ChatColor)BY_CHAR.get(Character.valueOf(code.charAt(0)));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String stripColor(String input)
/*     */   {
/* 306 */     if (input == null) {
/* 307 */       return null;
/*     */     }
/*     */     
/* 310 */     return STRIP_COLOR_PATTERN.matcher(input).replaceAll("");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String translateAlternateColorCodes(char altColorChar, String textToTranslate)
/*     */   {
/* 324 */     char[] b = textToTranslate.toCharArray();
/* 325 */     for (int i = 0; i < b.length - 1; i++) {
/* 326 */       if ((b[i] == altColorChar) && ("0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(b[(i + 1)]) > -1)) {
/* 327 */         b[i] = '§';
/* 328 */         b[(i + 1)] = Character.toLowerCase(b[(i + 1)]);
/*     */       }
/*     */     }
/* 331 */     return new String(b);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String getLastColors(String input)
/*     */   {
/* 341 */     String result = "";
/* 342 */     int length = input.length();
/*     */     
/*     */ 
/* 345 */     for (int index = length - 1; index > -1; index--) {
/* 346 */       char section = input.charAt(index);
/* 347 */       if ((section == '§') && (index < length - 1)) {
/* 348 */         char c = input.charAt(index + 1);
/* 349 */         ChatColor color = getByChar(c);
/*     */         
/* 351 */         if (color != null) {
/* 352 */           result = color.toString() + result;
/*     */           
/*     */ 
/* 355 */           if ((color.isColor()) || (color.equals(RESET))) {
/*     */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 362 */     return result;
/*     */   }
/*     */   
/*     */   static
/*     */   {
/* 218 */     STRIP_COLOR_PATTERN = Pattern.compile("(?i)" + String.valueOf('§') + "[0-9A-FK-OR]");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 224 */     BY_ID = Maps.newHashMap();
/* 225 */     BY_CHAR = Maps.newHashMap();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     ChatColor[] arrayOfChatColor;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 366 */     int i = (arrayOfChatColor = values()).length; for (int j = 0; j < i; j++) { ChatColor color = arrayOfChatColor[j];
/* 367 */       BY_ID.put(Integer.valueOf(color.intCode), color);
/* 368 */       BY_CHAR.put(Character.valueOf(color.code), color);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\ChatColor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */