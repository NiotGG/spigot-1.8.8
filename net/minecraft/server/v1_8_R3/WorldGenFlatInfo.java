/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldGenFlatInfo
/*     */ {
/*  26 */   private final List<WorldGenFlatLayerInfo> layers = Lists.newArrayList();
/*  27 */   private final Map<String, Map<String, String>> structures = Maps.newHashMap();
/*     */   private int c;
/*     */   
/*     */   public int a() {
/*  31 */     return this.c;
/*     */   }
/*     */   
/*     */   public void a(int paramInt) {
/*  35 */     this.c = paramInt;
/*     */   }
/*     */   
/*     */   public Map<String, Map<String, String>> b() {
/*  39 */     return this.structures;
/*     */   }
/*     */   
/*     */   public List<WorldGenFlatLayerInfo> c() {
/*  43 */     return this.layers;
/*     */   }
/*     */   
/*     */   public void d() {
/*  47 */     int i = 0;
/*     */     
/*  49 */     for (WorldGenFlatLayerInfo localWorldGenFlatLayerInfo : this.layers) {
/*  50 */       localWorldGenFlatLayerInfo.b(i);
/*  51 */       i += localWorldGenFlatLayerInfo.b();
/*     */     }
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/*  57 */     StringBuilder localStringBuilder = new StringBuilder();
/*     */     
/*  59 */     localStringBuilder.append(3);
/*  60 */     localStringBuilder.append(";");
/*     */     
/*  62 */     for (int i = 0; i < this.layers.size(); i++) {
/*  63 */       if (i > 0) {
/*  64 */         localStringBuilder.append(",");
/*     */       }
/*  66 */       localStringBuilder.append(((WorldGenFlatLayerInfo)this.layers.get(i)).toString());
/*     */     }
/*     */     
/*  69 */     localStringBuilder.append(";");
/*  70 */     localStringBuilder.append(this.c);
/*     */     
/*  72 */     if (!this.structures.isEmpty()) {
/*  73 */       localStringBuilder.append(";");
/*  74 */       i = 0;
/*     */       
/*  76 */       for (Map.Entry localEntry1 : this.structures.entrySet()) {
/*  77 */         if (i++ > 0) {
/*  78 */           localStringBuilder.append(",");
/*     */         }
/*  80 */         localStringBuilder.append(((String)localEntry1.getKey()).toLowerCase());
/*     */         
/*  82 */         Map localMap = (Map)localEntry1.getValue();
/*  83 */         if (!localMap.isEmpty()) {
/*  84 */           localStringBuilder.append("(");
/*  85 */           int j = 0;
/*     */           
/*  87 */           for (Map.Entry localEntry2 : localMap.entrySet()) {
/*  88 */             if (j++ > 0) {
/*  89 */               localStringBuilder.append(" ");
/*     */             }
/*  91 */             localStringBuilder.append((String)localEntry2.getKey());
/*  92 */             localStringBuilder.append("=");
/*  93 */             localStringBuilder.append((String)localEntry2.getValue());
/*     */           }
/*     */           
/*  96 */           localStringBuilder.append(")");
/*     */         }
/*     */       }
/*     */     } else {
/* 100 */       localStringBuilder.append(";");
/*     */     }
/*     */     
/* 103 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */   private static WorldGenFlatLayerInfo a(int paramInt1, String paramString, int paramInt2) {
/* 107 */     String[] arrayOfString = paramInt1 >= 3 ? paramString.split("\\*", 2) : paramString.split("x", 2);
/* 108 */     int i = 1;
/* 109 */     int j = 0;
/*     */     
/* 111 */     if (arrayOfString.length == 2) {
/*     */       try {
/* 113 */         i = Integer.parseInt(arrayOfString[0]);
/* 114 */         if (paramInt2 + i >= 256) {
/* 115 */           i = 256 - paramInt2;
/*     */         }
/* 117 */         if (i < 0) {
/* 118 */           i = 0;
/*     */         }
/*     */       } catch (Throwable localThrowable1) {
/* 121 */         return null;
/*     */       }
/*     */     }
/*     */     
/* 125 */     Block localBlock = null;
/*     */     try {
/* 127 */       String str = arrayOfString[(arrayOfString.length - 1)];
/* 128 */       if (paramInt1 < 3) {
/* 129 */         arrayOfString = str.split(":", 2);
/* 130 */         if (arrayOfString.length > 1) {
/* 131 */           j = Integer.parseInt(arrayOfString[1]);
/*     */         }
/* 133 */         localBlock = Block.getById(Integer.parseInt(arrayOfString[0]));
/*     */       } else {
/* 135 */         arrayOfString = str.split(":", 3);
/* 136 */         localBlock = arrayOfString.length > 1 ? Block.getByName(arrayOfString[0] + ":" + arrayOfString[1]) : null;
/* 137 */         if (localBlock != null) {
/* 138 */           j = arrayOfString.length > 2 ? Integer.parseInt(arrayOfString[2]) : 0;
/*     */         } else {
/* 140 */           localBlock = Block.getByName(arrayOfString[0]);
/* 141 */           if (localBlock != null) {
/* 142 */             j = arrayOfString.length > 1 ? Integer.parseInt(arrayOfString[1]) : 0;
/*     */           }
/*     */         }
/* 145 */         if (localBlock == null) {
/* 146 */           return null;
/*     */         }
/*     */       }
/*     */       
/* 150 */       if (localBlock == Blocks.AIR) {
/* 151 */         j = 0;
/*     */       }
/*     */       
/* 154 */       if ((j < 0) || (j > 15)) {
/* 155 */         j = 0;
/*     */       }
/*     */     } catch (Throwable localThrowable2) {
/* 158 */       return null;
/*     */     }
/*     */     
/* 161 */     WorldGenFlatLayerInfo localWorldGenFlatLayerInfo = new WorldGenFlatLayerInfo(paramInt1, i, localBlock, j);
/* 162 */     localWorldGenFlatLayerInfo.b(paramInt2);
/* 163 */     return localWorldGenFlatLayerInfo;
/*     */   }
/*     */   
/*     */   private static List<WorldGenFlatLayerInfo> a(int paramInt, String paramString) {
/* 167 */     if ((paramString == null) || (paramString.length() < 1)) {
/* 168 */       return null;
/*     */     }
/*     */     
/* 171 */     ArrayList localArrayList = Lists.newArrayList();
/* 172 */     String[] arrayOfString1 = paramString.split(",");
/* 173 */     int i = 0;
/*     */     
/* 175 */     for (String str : arrayOfString1) {
/* 176 */       WorldGenFlatLayerInfo localWorldGenFlatLayerInfo = a(paramInt, str, i);
/* 177 */       if (localWorldGenFlatLayerInfo == null) {
/* 178 */         return null;
/*     */       }
/* 180 */       localArrayList.add(localWorldGenFlatLayerInfo);
/* 181 */       i += localWorldGenFlatLayerInfo.b();
/*     */     }
/*     */     
/* 184 */     return localArrayList;
/*     */   }
/*     */   
/*     */   public static WorldGenFlatInfo a(String paramString) {
/* 188 */     if (paramString == null) {
/* 189 */       return e();
/*     */     }
/* 191 */     String[] arrayOfString1 = paramString.split(";", -1);
/* 192 */     int i = arrayOfString1.length == 1 ? 0 : MathHelper.a(arrayOfString1[0], 0);
/* 193 */     if ((i < 0) || (i > 3)) {
/* 194 */       return e();
/*     */     }
/*     */     
/* 197 */     WorldGenFlatInfo localWorldGenFlatInfo = new WorldGenFlatInfo();
/* 198 */     int j = arrayOfString1.length == 1 ? 0 : 1;
/* 199 */     List localList = a(i, arrayOfString1[(j++)]);
/*     */     
/* 201 */     if ((localList == null) || (localList.isEmpty())) {
/* 202 */       return e();
/*     */     }
/*     */     
/* 205 */     localWorldGenFlatInfo.c().addAll(localList);
/* 206 */     localWorldGenFlatInfo.d();
/*     */     
/* 208 */     int k = BiomeBase.PLAINS.id;
/* 209 */     if ((i > 0) && (arrayOfString1.length > j)) {
/* 210 */       k = MathHelper.a(arrayOfString1[(j++)], k);
/*     */     }
/* 212 */     localWorldGenFlatInfo.a(k);
/*     */     
/* 214 */     if ((i > 0) && (arrayOfString1.length > j)) {
/* 215 */       String[] arrayOfString2 = arrayOfString1[(j++)].toLowerCase().split(",");
/*     */       
/* 217 */       for (String str : arrayOfString2) {
/* 218 */         String[] arrayOfString4 = str.split("\\(", 2);
/* 219 */         HashMap localHashMap = Maps.newHashMap();
/*     */         
/* 221 */         if (arrayOfString4[0].length() > 0) {
/* 222 */           localWorldGenFlatInfo.b().put(arrayOfString4[0], localHashMap);
/*     */           
/* 224 */           if ((arrayOfString4.length > 1) && (arrayOfString4[1].endsWith(")")) && (arrayOfString4[1].length() > 1)) {
/* 225 */             String[] arrayOfString5 = arrayOfString4[1].substring(0, arrayOfString4[1].length() - 1).split(" ");
/*     */             
/* 227 */             for (int i1 = 0; i1 < arrayOfString5.length; i1++) {
/* 228 */               String[] arrayOfString6 = arrayOfString5[i1].split("=", 2);
/* 229 */               if (arrayOfString6.length == 2) {
/* 230 */                 localHashMap.put(arrayOfString6[0], arrayOfString6[1]);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     } else {
/* 237 */       localWorldGenFlatInfo.b().put("village", Maps.newHashMap());
/*     */     }
/*     */     
/* 240 */     return localWorldGenFlatInfo;
/*     */   }
/*     */   
/*     */   public static WorldGenFlatInfo e() {
/* 244 */     WorldGenFlatInfo localWorldGenFlatInfo = new WorldGenFlatInfo();
/*     */     
/* 246 */     localWorldGenFlatInfo.a(BiomeBase.PLAINS.id);
/* 247 */     localWorldGenFlatInfo.c().add(new WorldGenFlatLayerInfo(1, Blocks.BEDROCK));
/* 248 */     localWorldGenFlatInfo.c().add(new WorldGenFlatLayerInfo(2, Blocks.DIRT));
/* 249 */     localWorldGenFlatInfo.c().add(new WorldGenFlatLayerInfo(1, Blocks.GRASS));
/* 250 */     localWorldGenFlatInfo.d();
/* 251 */     localWorldGenFlatInfo.b().put("village", Maps.newHashMap());
/*     */     
/* 253 */     return localWorldGenFlatInfo;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenFlatInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */