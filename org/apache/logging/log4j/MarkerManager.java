/*     */ package org.apache.logging.log4j;
/*     */ 
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
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
/*     */ public final class MarkerManager
/*     */ {
/*  29 */   private static ConcurrentMap<String, Marker> markerMap = new ConcurrentHashMap();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Marker getMarker(String paramString)
/*     */   {
/*  40 */     markerMap.putIfAbsent(paramString, new Log4jMarker(paramString));
/*  41 */     return (Marker)markerMap.get(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Marker getMarker(String paramString1, String paramString2)
/*     */   {
/*  52 */     Marker localMarker = (Marker)markerMap.get(paramString2);
/*  53 */     if (localMarker == null) {
/*  54 */       throw new IllegalArgumentException("Parent Marker " + paramString2 + " has not been defined");
/*     */     }
/*  56 */     return getMarker(paramString1, localMarker);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Marker getMarker(String paramString, Marker paramMarker)
/*     */   {
/*  66 */     markerMap.putIfAbsent(paramString, new Log4jMarker(paramString, paramMarker));
/*  67 */     return (Marker)markerMap.get(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */   private static class Log4jMarker
/*     */     implements Marker
/*     */   {
/*     */     private static final long serialVersionUID = 100L;
/*     */     
/*     */     private final String name;
/*     */     private final Marker parent;
/*     */     
/*     */     public Log4jMarker(String paramString)
/*     */     {
/*  81 */       this.name = paramString;
/*  82 */       this.parent = null;
/*     */     }
/*     */     
/*     */     public Log4jMarker(String paramString, Marker paramMarker) {
/*  86 */       this.name = paramString;
/*  87 */       this.parent = paramMarker;
/*     */     }
/*     */     
/*     */     public String getName()
/*     */     {
/*  92 */       return this.name;
/*     */     }
/*     */     
/*     */     public Marker getParent()
/*     */     {
/*  97 */       return this.parent;
/*     */     }
/*     */     
/*     */     public boolean isInstanceOf(Marker paramMarker)
/*     */     {
/* 102 */       if (paramMarker == null) {
/* 103 */         throw new IllegalArgumentException("A marker parameter is required");
/*     */       }
/* 105 */       Object localObject = this;
/*     */       do {
/* 107 */         if (localObject == paramMarker) {
/* 108 */           return true;
/*     */         }
/* 110 */         localObject = ((Marker)localObject).getParent();
/* 111 */       } while (localObject != null);
/* 112 */       return false;
/*     */     }
/*     */     
/*     */     public boolean isInstanceOf(String paramString)
/*     */     {
/* 117 */       if (paramString == null) {
/* 118 */         throw new IllegalArgumentException("A marker name is required");
/*     */       }
/* 120 */       Object localObject = this;
/*     */       do {
/* 122 */         if (paramString.equals(((Marker)localObject).getName())) {
/* 123 */           return true;
/*     */         }
/* 125 */         localObject = ((Marker)localObject).getParent();
/* 126 */       } while (localObject != null);
/* 127 */       return false;
/*     */     }
/*     */     
/*     */     public boolean equals(Object paramObject)
/*     */     {
/* 132 */       if (this == paramObject) {
/* 133 */         return true;
/*     */       }
/* 135 */       if ((paramObject == null) || (!(paramObject instanceof Marker))) {
/* 136 */         return false;
/*     */       }
/*     */       
/* 139 */       Marker localMarker = (Marker)paramObject;
/*     */       
/* 141 */       if (this.name != null ? !this.name.equals(localMarker.getName()) : localMarker.getName() != null) {
/* 142 */         return false;
/*     */       }
/*     */       
/* 145 */       return true;
/*     */     }
/*     */     
/*     */     public int hashCode()
/*     */     {
/* 150 */       return this.name != null ? this.name.hashCode() : 0;
/*     */     }
/*     */     
/*     */     public String toString()
/*     */     {
/* 155 */       StringBuilder localStringBuilder = new StringBuilder(this.name);
/* 156 */       if (this.parent != null) {
/* 157 */         Marker localMarker = this.parent;
/* 158 */         localStringBuilder.append("[ ");
/* 159 */         int i = 1;
/* 160 */         while (localMarker != null) {
/* 161 */           if (i == 0) {
/* 162 */             localStringBuilder.append(", ");
/*     */           }
/* 164 */           localStringBuilder.append(localMarker.getName());
/* 165 */           i = 0;
/* 166 */           localMarker = localMarker.getParent();
/*     */         }
/* 168 */         localStringBuilder.append(" ]");
/*     */       }
/* 170 */       return localStringBuilder.toString();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\MarkerManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */