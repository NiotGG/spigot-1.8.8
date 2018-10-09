/*     */ package org.apache.logging.log4j.core.pattern;
/*     */ 
/*     */ import java.util.EnumMap;
/*     */ import java.util.Locale;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
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
/*     */ @Plugin(name="LevelPatternConverter", category="Converter")
/*     */ @ConverterKeys({"p", "level"})
/*     */ public final class LevelPatternConverter
/*     */   extends LogEventPatternConverter
/*     */ {
/*     */   private static final String OPTION_LENGTH = "length";
/*     */   private static final String OPTION_LOWER = "lowerCase";
/*  38 */   private static final LevelPatternConverter INSTANCE = new LevelPatternConverter(null);
/*     */   
/*     */ 
/*     */   private final EnumMap<Level, String> levelMap;
/*     */   
/*     */ 
/*     */   private LevelPatternConverter(EnumMap<Level, String> paramEnumMap)
/*     */   {
/*  46 */     super("Level", "level");
/*  47 */     this.levelMap = paramEnumMap;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static LevelPatternConverter newInstance(String[] paramArrayOfString)
/*     */   {
/*  59 */     if ((paramArrayOfString == null) || (paramArrayOfString.length == 0)) {
/*  60 */       return INSTANCE;
/*     */     }
/*  62 */     EnumMap localEnumMap = new EnumMap(Level.class);
/*  63 */     int i = Integer.MAX_VALUE;
/*  64 */     boolean bool = false;
/*  65 */     String[] arrayOfString = paramArrayOfString[0].split(",");
/*  66 */     Object localObject2; Object localObject3; for (localObject2 : arrayOfString) {
/*  67 */       localObject3 = ((String)localObject2).split("=");
/*  68 */       if ((localObject3 == null) || (localObject3.length != 2)) {
/*  69 */         LOGGER.error("Invalid option {}", new Object[] { localObject2 });
/*     */       }
/*     */       else {
/*  72 */         String str1 = localObject3[0].trim();
/*  73 */         String str2 = localObject3[1].trim();
/*  74 */         if ("length".equalsIgnoreCase(str1)) {
/*  75 */           i = Integer.parseInt(str2);
/*  76 */         } else if ("lowerCase".equalsIgnoreCase(str1)) {
/*  77 */           bool = Boolean.parseBoolean(str2);
/*     */         } else {
/*  79 */           Level localLevel = Level.toLevel(str1, null);
/*  80 */           if (localLevel == null) {
/*  81 */             LOGGER.error("Invalid Level {}", new Object[] { str1 });
/*     */           } else
/*  83 */             localEnumMap.put(localLevel, str2);
/*     */         }
/*     */       }
/*     */     }
/*  87 */     if ((localEnumMap.size() == 0) && (i == Integer.MAX_VALUE) && (!bool)) {
/*  88 */       return INSTANCE;
/*     */     }
/*  90 */     for (localObject2 : Level.values()) {
/*  91 */       if (!localEnumMap.containsKey(localObject2)) {
/*  92 */         localObject3 = left((Level)localObject2, i);
/*  93 */         localEnumMap.put((Enum)localObject2, bool ? ((String)localObject3).toLowerCase(Locale.US) : localObject3);
/*     */       }
/*     */     }
/*  96 */     return new LevelPatternConverter(localEnumMap);
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
/*     */   private static String left(Level paramLevel, int paramInt)
/*     */   {
/* 110 */     String str = paramLevel.toString();
/* 111 */     if (paramInt >= str.length()) {
/* 112 */       return str;
/*     */     }
/* 114 */     return str.substring(0, paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void format(LogEvent paramLogEvent, StringBuilder paramStringBuilder)
/*     */   {
/* 122 */     paramStringBuilder.append(this.levelMap == null ? paramLogEvent.getLevel().toString() : (String)this.levelMap.get(paramLogEvent.getLevel()));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getStyleClass(Object paramObject)
/*     */   {
/* 130 */     if ((paramObject instanceof LogEvent)) {
/* 131 */       Level localLevel = ((LogEvent)paramObject).getLevel();
/*     */       
/* 133 */       switch (localLevel) {
/*     */       case TRACE: 
/* 135 */         return "level trace";
/*     */       
/*     */       case DEBUG: 
/* 138 */         return "level debug";
/*     */       
/*     */       case INFO: 
/* 141 */         return "level info";
/*     */       
/*     */       case WARN: 
/* 144 */         return "level warn";
/*     */       
/*     */       case ERROR: 
/* 147 */         return "level error";
/*     */       
/*     */       case FATAL: 
/* 150 */         return "level fatal";
/*     */       }
/*     */       
/* 153 */       return "level " + ((LogEvent)paramObject).getLevel().toString();
/*     */     }
/*     */     
/*     */ 
/* 157 */     return "level";
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\pattern\LevelPatternConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */