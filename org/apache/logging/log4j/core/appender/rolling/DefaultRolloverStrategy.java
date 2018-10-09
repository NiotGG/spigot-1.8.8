/*     */ package org.apache.logging.log4j.core.appender.rolling;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.appender.rolling.helper.Action;
/*     */ import org.apache.logging.log4j.core.appender.rolling.helper.FileRenameAction;
/*     */ import org.apache.logging.log4j.core.appender.rolling.helper.GZCompressAction;
/*     */ import org.apache.logging.log4j.core.appender.rolling.helper.ZipCompressAction;
/*     */ import org.apache.logging.log4j.core.config.Configuration;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.core.helpers.Integers;
/*     */ import org.apache.logging.log4j.core.lookup.StrSubstitutor;
/*     */ import org.apache.logging.log4j.status.StatusLogger;
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
/*     */ @Plugin(name="DefaultRolloverStrategy", category="Core", printObject=true)
/*     */ public class DefaultRolloverStrategy
/*     */   implements RolloverStrategy
/*     */ {
/*  82 */   protected static final Logger LOGGER = ;
/*     */   
/*     */ 
/*     */   private static final int MIN_WINDOW_SIZE = 1;
/*     */   
/*     */ 
/*     */   private static final int DEFAULT_WINDOW_SIZE = 7;
/*     */   
/*     */ 
/*     */   private final int maxIndex;
/*     */   
/*     */ 
/*     */   private final int minIndex;
/*     */   
/*     */ 
/*     */   private final boolean useMax;
/*     */   
/*     */ 
/*     */   private final StrSubstitutor subst;
/*     */   
/*     */ 
/*     */   private final int compressionLevel;
/*     */   
/*     */ 
/*     */ 
/*     */   protected DefaultRolloverStrategy(int paramInt1, int paramInt2, boolean paramBoolean, int paramInt3, StrSubstitutor paramStrSubstitutor)
/*     */   {
/* 109 */     this.minIndex = paramInt1;
/* 110 */     this.maxIndex = paramInt2;
/* 111 */     this.useMax = paramBoolean;
/* 112 */     this.compressionLevel = paramInt3;
/* 113 */     this.subst = paramStrSubstitutor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public RolloverDescription rollover(RollingFileManager paramRollingFileManager)
/*     */     throws SecurityException
/*     */   {
/* 124 */     if (this.maxIndex >= 0)
/*     */     {
/*     */       int i;
/* 127 */       if ((i = purge(this.minIndex, this.maxIndex, paramRollingFileManager)) < 0) {
/* 128 */         return null;
/*     */       }
/*     */       
/* 131 */       StringBuilder localStringBuilder = new StringBuilder();
/* 132 */       paramRollingFileManager.getPatternProcessor().formatFileName(this.subst, localStringBuilder, Integer.valueOf(i));
/* 133 */       String str1 = paramRollingFileManager.getFileName();
/*     */       
/* 135 */       String str2 = localStringBuilder.toString();
/* 136 */       String str3 = str2;
/* 137 */       Object localObject = null;
/*     */       
/* 139 */       if (str2.endsWith(".gz")) {
/* 140 */         str2 = str2.substring(0, str2.length() - 3);
/* 141 */         localObject = new GZCompressAction(new File(str2), new File(str3), true);
/* 142 */       } else if (str2.endsWith(".zip")) {
/* 143 */         str2 = str2.substring(0, str2.length() - 4);
/* 144 */         localObject = new ZipCompressAction(new File(str2), new File(str3), true, this.compressionLevel);
/*     */       }
/*     */       
/*     */ 
/* 148 */       FileRenameAction localFileRenameAction = new FileRenameAction(new File(str1), new File(str2), false);
/*     */       
/*     */ 
/* 151 */       return new RolloverDescriptionImpl(str1, false, localFileRenameAction, (Action)localObject);
/*     */     }
/*     */     
/* 154 */     return null;
/*     */   }
/*     */   
/*     */   private int purge(int paramInt1, int paramInt2, RollingFileManager paramRollingFileManager) {
/* 158 */     return this.useMax ? purgeAscending(paramInt1, paramInt2, paramRollingFileManager) : purgeDescending(paramInt1, paramInt2, paramRollingFileManager);
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
/*     */   private int purgeDescending(int paramInt1, int paramInt2, RollingFileManager paramRollingFileManager)
/*     */   {
/* 172 */     int i = 0;
/*     */     
/* 174 */     ArrayList localArrayList = new ArrayList();
/* 175 */     StringBuilder localStringBuilder = new StringBuilder();
/* 176 */     paramRollingFileManager.getPatternProcessor().formatFileName(localStringBuilder, Integer.valueOf(paramInt1));
/*     */     
/* 178 */     Object localObject1 = this.subst.replace(localStringBuilder);
/*     */     
/* 180 */     if (((String)localObject1).endsWith(".gz")) {
/* 181 */       i = 3;
/* 182 */     } else if (((String)localObject1).endsWith(".zip")) {
/* 183 */       i = 4;
/*     */     }
/*     */     Object localObject2;
/* 186 */     for (int j = paramInt1; j <= paramInt2; j++) {
/* 187 */       localObject2 = new File((String)localObject1);
/* 188 */       int k = 0;
/*     */       
/* 190 */       if (i > 0) {
/* 191 */         localObject3 = new File(((String)localObject1).substring(0, ((String)localObject1).length() - i));
/*     */         
/*     */ 
/* 194 */         if (((File)localObject2).exists()) {
/* 195 */           if (((File)localObject3).exists()) {
/* 196 */             ((File)localObject3).delete();
/*     */           }
/*     */         } else {
/* 199 */           localObject2 = localObject3;
/* 200 */           k = 1;
/*     */         }
/*     */       }
/*     */       
/* 204 */       if (!((File)localObject2).exists()) {
/*     */         break;
/*     */       }
/*     */       
/*     */ 
/* 209 */       if (j == paramInt2) {
/* 210 */         if (((File)localObject2).delete()) break;
/* 211 */         return -1;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 220 */       localStringBuilder.setLength(0);
/* 221 */       paramRollingFileManager.getPatternProcessor().formatFileName(localStringBuilder, Integer.valueOf(j + 1));
/*     */       
/* 223 */       Object localObject3 = this.subst.replace(localStringBuilder);
/* 224 */       Object localObject4 = localObject3;
/*     */       
/* 226 */       if (k != 0) {
/* 227 */         localObject4 = ((String)localObject3).substring(0, ((String)localObject3).length() - i);
/*     */       }
/*     */       
/* 230 */       localArrayList.add(new FileRenameAction((File)localObject2, new File((String)localObject4), true));
/* 231 */       localObject1 = localObject3;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 240 */     for (j = localArrayList.size() - 1; j >= 0; j--) {
/* 241 */       localObject2 = (Action)localArrayList.get(j);
/*     */       try
/*     */       {
/* 244 */         if (!((Action)localObject2).execute()) {
/* 245 */           return -1;
/*     */         }
/*     */       } catch (Exception localException) {
/* 248 */         LOGGER.warn("Exception during purge in RollingFileAppender", localException);
/* 249 */         return -1;
/*     */       }
/*     */     }
/*     */     
/* 253 */     return paramInt1;
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
/*     */   private int purgeAscending(int paramInt1, int paramInt2, RollingFileManager paramRollingFileManager)
/*     */   {
/* 266 */     int i = 0;
/*     */     
/* 268 */     ArrayList localArrayList = new ArrayList();
/* 269 */     StringBuilder localStringBuilder = new StringBuilder();
/* 270 */     paramRollingFileManager.getPatternProcessor().formatFileName(localStringBuilder, Integer.valueOf(paramInt2));
/*     */     
/* 272 */     Object localObject1 = this.subst.replace(localStringBuilder);
/*     */     
/* 274 */     if (((String)localObject1).endsWith(".gz")) {
/* 275 */       i = 3;
/* 276 */     } else if (((String)localObject1).endsWith(".zip")) {
/* 277 */       i = 4;
/*     */     }
/*     */     
/* 280 */     int j = 0;
/*     */     Object localObject2;
/* 282 */     for (int k = paramInt2; k >= paramInt1; k--) {
/* 283 */       localObject2 = new File((String)localObject1);
/* 284 */       if ((k == paramInt2) && (((File)localObject2).exists())) {
/* 285 */         j = paramInt2;
/* 286 */       } else if ((j == 0) && (((File)localObject2).exists())) {
/* 287 */         j = k + 1;
/* 288 */         break;
/*     */       }
/*     */       
/* 291 */       int m = 0;
/*     */       Object localObject3;
/* 293 */       if (i > 0) {
/* 294 */         localObject3 = new File(((String)localObject1).substring(0, ((String)localObject1).length() - i));
/*     */         
/*     */ 
/* 297 */         if (((File)localObject2).exists()) {
/* 298 */           if (((File)localObject3).exists()) {
/* 299 */             ((File)localObject3).delete();
/*     */           }
/*     */         } else {
/* 302 */           localObject2 = localObject3;
/* 303 */           m = 1;
/*     */         }
/*     */       }
/*     */       
/* 307 */       if (((File)localObject2).exists())
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/* 312 */         if (k == paramInt1) {
/* 313 */           if (((File)localObject2).delete()) break;
/* 314 */           return -1;
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 323 */         localStringBuilder.setLength(0);
/* 324 */         paramRollingFileManager.getPatternProcessor().formatFileName(localStringBuilder, Integer.valueOf(k - 1));
/*     */         
/* 326 */         localObject3 = this.subst.replace(localStringBuilder);
/* 327 */         Object localObject4 = localObject3;
/*     */         
/* 329 */         if (m != 0) {
/* 330 */           localObject4 = ((String)localObject3).substring(0, ((String)localObject3).length() - i);
/*     */         }
/*     */         
/* 333 */         localArrayList.add(new FileRenameAction((File)localObject2, new File((String)localObject4), true));
/* 334 */         localObject1 = localObject3;
/*     */       } else {
/* 336 */         localStringBuilder.setLength(0);
/* 337 */         paramRollingFileManager.getPatternProcessor().formatFileName(localStringBuilder, Integer.valueOf(k - 1));
/*     */         
/* 339 */         localObject1 = this.subst.replace(localStringBuilder);
/*     */       }
/*     */     }
/* 342 */     if (j == 0) {
/* 343 */       j = paramInt1;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 349 */     for (k = localArrayList.size() - 1; k >= 0; k--) {
/* 350 */       localObject2 = (Action)localArrayList.get(k);
/*     */       try
/*     */       {
/* 353 */         if (!((Action)localObject2).execute()) {
/* 354 */           return -1;
/*     */         }
/*     */       } catch (Exception localException) {
/* 357 */         LOGGER.warn("Exception during purge in RollingFileAppender", localException);
/* 358 */         return -1;
/*     */       }
/*     */     }
/* 361 */     return j;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 366 */     return "DefaultRolloverStrategy(min=" + this.minIndex + ", max=" + this.maxIndex + ")";
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @PluginFactory
/*     */   public static DefaultRolloverStrategy createStrategy(@PluginAttribute("max") String paramString1, @PluginAttribute("min") String paramString2, @PluginAttribute("fileIndex") String paramString3, @PluginAttribute("compressionLevel") String paramString4, @PluginConfiguration Configuration paramConfiguration)
/*     */   {
/* 386 */     boolean bool = paramString3 == null ? true : paramString3.equalsIgnoreCase("max");
/*     */     int i;
/* 388 */     if (paramString2 != null) {
/* 389 */       i = Integer.parseInt(paramString2);
/* 390 */       if (i < 1) {
/* 391 */         LOGGER.error("Minimum window size too small. Limited to 1");
/* 392 */         i = 1;
/*     */       }
/*     */     } else {
/* 395 */       i = 1;
/*     */     }
/*     */     int j;
/* 398 */     if (paramString1 != null) {
/* 399 */       j = Integer.parseInt(paramString1);
/* 400 */       if (j < i) {
/* 401 */         j = i < 7 ? 7 : i;
/* 402 */         LOGGER.error("Maximum window size must be greater than the minimum windows size. Set to " + j);
/*     */       }
/*     */     } else {
/* 405 */       j = 7;
/*     */     }
/* 407 */     int k = Integers.parseInt(paramString4, -1);
/* 408 */     return new DefaultRolloverStrategy(i, j, bool, k, paramConfiguration.getStrSubstitutor());
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\rolling\DefaultRolloverStrategy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */