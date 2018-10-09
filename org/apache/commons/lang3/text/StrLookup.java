/*     */ package org.apache.commons.lang3.text;
/*     */ 
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
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
/*     */ public abstract class StrLookup<V>
/*     */ {
/*  48 */   private static final StrLookup<String> NONE_LOOKUP = new MapStrLookup(null);
/*  49 */   static { Object localObject = null;
/*     */     try {
/*  51 */       Properties localProperties1 = System.getProperties();
/*     */       
/*  53 */       Properties localProperties2 = localProperties1;
/*  54 */       localObject = new MapStrLookup(localProperties2);
/*     */     } catch (SecurityException localSecurityException) {
/*  56 */       localObject = NONE_LOOKUP;
/*     */     }
/*  58 */     SYSTEM_PROPERTIES_LOOKUP = (StrLookup)localObject;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static StrLookup<?> noneLookup()
/*     */   {
/*  68 */     return NONE_LOOKUP;
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
/*     */   public static StrLookup<String> systemPropertiesLookup()
/*     */   {
/*  83 */     return SYSTEM_PROPERTIES_LOOKUP;
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
/*     */   public static <V> StrLookup<V> mapLookup(Map<String, V> paramMap)
/*     */   {
/*  97 */     return new MapStrLookup(paramMap);
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
/*     */   private static final StrLookup<String> SYSTEM_PROPERTIES_LOOKUP;
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
/*     */   public abstract String lookup(String paramString);
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
/*     */   static class MapStrLookup<V>
/*     */     extends StrLookup<V>
/*     */   {
/*     */     private final Map<String, V> map;
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
/*     */     MapStrLookup(Map<String, V> paramMap)
/*     */     {
/* 148 */       this.map = paramMap;
/*     */     }
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
/*     */     public String lookup(String paramString)
/*     */     {
/* 162 */       if (this.map == null) {
/* 163 */         return null;
/*     */       }
/* 165 */       Object localObject = this.map.get(paramString);
/* 166 */       if (localObject == null) {
/* 167 */         return null;
/*     */       }
/* 169 */       return localObject.toString();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\text\StrLookup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */