/*     */ package org.apache.logging.log4j.core.pattern;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.config.Configuration;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.layout.PatternLayout;
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
/*     */ public abstract class AbstractStyleNameConverter
/*     */   extends LogEventPatternConverter
/*     */ {
/*     */   private final List<PatternFormatter> formatters;
/*     */   private final String style;
/*     */   
/*     */   protected AbstractStyleNameConverter(String paramString1, List<PatternFormatter> paramList, String paramString2)
/*     */   {
/*  46 */     super(paramString1, "style");
/*  47 */     this.formatters = paramList;
/*  48 */     this.style = paramString2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Plugin(name="black", category="Converter")
/*     */   @ConverterKeys({"black"})
/*     */   public static final class Black
/*     */     extends AbstractStyleNameConverter
/*     */   {
/*     */     protected static final String NAME = "black";
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Black(List<PatternFormatter> paramList, String paramString)
/*     */     {
/*  68 */       super(paramList, paramString);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public static Black newInstance(Configuration paramConfiguration, String[] paramArrayOfString)
/*     */     {
/*  80 */       return (Black)newInstance(Black.class, "black", paramConfiguration, paramArrayOfString);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Plugin(name="blue", category="Converter")
/*     */   @ConverterKeys({"blue"})
/*     */   public static final class Blue
/*     */     extends AbstractStyleNameConverter
/*     */   {
/*     */     protected static final String NAME = "blue";
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Blue(List<PatternFormatter> paramList, String paramString)
/*     */     {
/* 101 */       super(paramList, paramString);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public static Blue newInstance(Configuration paramConfiguration, String[] paramArrayOfString)
/*     */     {
/* 113 */       return (Blue)newInstance(Blue.class, "blue", paramConfiguration, paramArrayOfString);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Plugin(name="cyan", category="Converter")
/*     */   @ConverterKeys({"cyan"})
/*     */   public static final class Cyan
/*     */     extends AbstractStyleNameConverter
/*     */   {
/*     */     protected static final String NAME = "cyan";
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Cyan(List<PatternFormatter> paramList, String paramString)
/*     */     {
/* 134 */       super(paramList, paramString);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public static Cyan newInstance(Configuration paramConfiguration, String[] paramArrayOfString)
/*     */     {
/* 146 */       return (Cyan)newInstance(Cyan.class, "cyan", paramConfiguration, paramArrayOfString);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Plugin(name="green", category="Converter")
/*     */   @ConverterKeys({"green"})
/*     */   public static final class Green
/*     */     extends AbstractStyleNameConverter
/*     */   {
/*     */     protected static final String NAME = "green";
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Green(List<PatternFormatter> paramList, String paramString)
/*     */     {
/* 167 */       super(paramList, paramString);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public static Green newInstance(Configuration paramConfiguration, String[] paramArrayOfString)
/*     */     {
/* 179 */       return (Green)newInstance(Green.class, "green", paramConfiguration, paramArrayOfString);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Plugin(name="magenta", category="Converter")
/*     */   @ConverterKeys({"magenta"})
/*     */   public static final class Magenta
/*     */     extends AbstractStyleNameConverter
/*     */   {
/*     */     protected static final String NAME = "magenta";
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Magenta(List<PatternFormatter> paramList, String paramString)
/*     */     {
/* 200 */       super(paramList, paramString);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public static Magenta newInstance(Configuration paramConfiguration, String[] paramArrayOfString)
/*     */     {
/* 212 */       return (Magenta)newInstance(Magenta.class, "magenta", paramConfiguration, paramArrayOfString);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Plugin(name="red", category="Converter")
/*     */   @ConverterKeys({"red"})
/*     */   public static final class Red
/*     */     extends AbstractStyleNameConverter
/*     */   {
/*     */     protected static final String NAME = "red";
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Red(List<PatternFormatter> paramList, String paramString)
/*     */     {
/* 233 */       super(paramList, paramString);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public static Red newInstance(Configuration paramConfiguration, String[] paramArrayOfString)
/*     */     {
/* 245 */       return (Red)newInstance(Red.class, "red", paramConfiguration, paramArrayOfString);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Plugin(name="white", category="Converter")
/*     */   @ConverterKeys({"white"})
/*     */   public static final class White
/*     */     extends AbstractStyleNameConverter
/*     */   {
/*     */     protected static final String NAME = "white";
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public White(List<PatternFormatter> paramList, String paramString)
/*     */     {
/* 266 */       super(paramList, paramString);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public static White newInstance(Configuration paramConfiguration, String[] paramArrayOfString)
/*     */     {
/* 278 */       return (White)newInstance(White.class, "white", paramConfiguration, paramArrayOfString);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Plugin(name="yellow", category="Converter")
/*     */   @ConverterKeys({"yellow"})
/*     */   public static final class Yellow
/*     */     extends AbstractStyleNameConverter
/*     */   {
/*     */     protected static final String NAME = "yellow";
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Yellow(List<PatternFormatter> paramList, String paramString)
/*     */     {
/* 299 */       super(paramList, paramString);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public static Yellow newInstance(Configuration paramConfiguration, String[] paramArrayOfString)
/*     */     {
/* 311 */       return (Yellow)newInstance(Yellow.class, "yellow", paramConfiguration, paramArrayOfString);
/*     */     }
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
/*     */   protected static <T extends AbstractStyleNameConverter> T newInstance(Class<T> paramClass, String paramString, Configuration paramConfiguration, String[] paramArrayOfString)
/*     */   {
/* 326 */     List localList = toPatternFormatterList(paramConfiguration, paramArrayOfString);
/* 327 */     if (localList == null) {
/* 328 */       return null;
/*     */     }
/*     */     try {
/* 331 */       Constructor localConstructor = paramClass.getConstructor(new Class[] { List.class, String.class });
/* 332 */       return (AbstractStyleNameConverter)localConstructor.newInstance(new Object[] { localList, AnsiEscape.createSequence(new String[] { paramString }) });
/*     */     } catch (SecurityException localSecurityException) {
/* 334 */       LOGGER.error(localSecurityException.toString(), localSecurityException);
/*     */     } catch (NoSuchMethodException localNoSuchMethodException) {
/* 336 */       LOGGER.error(localNoSuchMethodException.toString(), localNoSuchMethodException);
/*     */     } catch (IllegalArgumentException localIllegalArgumentException) {
/* 338 */       LOGGER.error(localIllegalArgumentException.toString(), localIllegalArgumentException);
/*     */     } catch (InstantiationException localInstantiationException) {
/* 340 */       LOGGER.error(localInstantiationException.toString(), localInstantiationException);
/*     */     } catch (IllegalAccessException localIllegalAccessException) {
/* 342 */       LOGGER.error(localIllegalAccessException.toString(), localIllegalAccessException);
/*     */     } catch (InvocationTargetException localInvocationTargetException) {
/* 344 */       LOGGER.error(localInvocationTargetException.toString(), localInvocationTargetException);
/*     */     }
/* 346 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static List<PatternFormatter> toPatternFormatterList(Configuration paramConfiguration, String[] paramArrayOfString)
/*     */   {
/* 357 */     if ((paramArrayOfString.length == 0) || (paramArrayOfString[0] == null)) {
/* 358 */       LOGGER.error("No pattern supplied on style for config=" + paramConfiguration);
/* 359 */       return null;
/*     */     }
/* 361 */     PatternParser localPatternParser = PatternLayout.createPatternParser(paramConfiguration);
/* 362 */     if (localPatternParser == null) {
/* 363 */       LOGGER.error("No PatternParser created for config=" + paramConfiguration + ", options=" + Arrays.toString(paramArrayOfString));
/* 364 */       return null;
/*     */     }
/* 366 */     return localPatternParser.parse(paramArrayOfString[0]);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void format(LogEvent paramLogEvent, StringBuilder paramStringBuilder)
/*     */   {
/* 374 */     StringBuilder localStringBuilder = new StringBuilder();
/* 375 */     for (PatternFormatter localPatternFormatter : this.formatters) {
/* 376 */       localPatternFormatter.format(paramLogEvent, localStringBuilder);
/*     */     }
/* 378 */     if (localStringBuilder.length() > 0) {
/* 379 */       paramStringBuilder.append(this.style).append(localStringBuilder.toString()).append(AnsiEscape.getDefaultStyle());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\pattern\AbstractStyleNameConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */