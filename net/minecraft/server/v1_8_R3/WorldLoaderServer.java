/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FilenameFilter;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldLoaderServer
/*     */   extends WorldLoader
/*     */ {
/*  25 */   private static final Logger b = ;
/*     */   
/*     */   public WorldLoaderServer(File paramFile) {
/*  28 */     super(paramFile);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected int c()
/*     */   {
/*  68 */     return 19133;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void d() {}
/*     */   
/*     */ 
/*     */   public IDataManager a(String paramString, boolean paramBoolean)
/*     */   {
/*  78 */     return new ServerNBTManager(this.a, paramString, paramBoolean);
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
/*     */   public boolean isConvertable(String paramString)
/*     */   {
/*  94 */     WorldData localWorldData = c(paramString);
/*  95 */     if ((localWorldData == null) || (localWorldData.l() == c())) {
/*  96 */       return false;
/*     */     }
/*  98 */     return true;
/*     */   }
/*     */   
/*     */   public boolean convert(String paramString, IProgressUpdate paramIProgressUpdate)
/*     */   {
/* 103 */     paramIProgressUpdate.a(0);
/*     */     
/* 105 */     ArrayList localArrayList1 = Lists.newArrayList();
/* 106 */     ArrayList localArrayList2 = Lists.newArrayList();
/* 107 */     ArrayList localArrayList3 = Lists.newArrayList();
/* 108 */     File localFile1 = new File(this.a, paramString);
/* 109 */     File localFile2 = new File(localFile1, "DIM-1");
/* 110 */     File localFile3 = new File(localFile1, "DIM1");
/*     */     
/* 112 */     b.info("Scanning folders...");
/*     */     
/*     */ 
/* 115 */     a(localFile1, localArrayList1);
/*     */     
/*     */ 
/* 118 */     if (localFile2.exists()) {
/* 119 */       a(localFile2, localArrayList2);
/*     */     }
/* 121 */     if (localFile3.exists()) {
/* 122 */       a(localFile3, localArrayList3);
/*     */     }
/*     */     
/* 125 */     int i = localArrayList1.size() + localArrayList2.size() + localArrayList3.size();
/* 126 */     b.info("Total conversion count is " + i);
/*     */     
/* 128 */     WorldData localWorldData = c(paramString);
/*     */     
/* 130 */     Object localObject = null;
/* 131 */     if (localWorldData.getType() == WorldType.FLAT) {
/* 132 */       localObject = new WorldChunkManagerHell(BiomeBase.PLAINS, 0.5F);
/*     */     } else {
/* 134 */       localObject = new WorldChunkManager(localWorldData.getSeed(), localWorldData.getType(), localWorldData.getGeneratorOptions());
/*     */     }
/*     */     
/*     */ 
/* 138 */     a(new File(localFile1, "region"), localArrayList1, (WorldChunkManager)localObject, 0, i, paramIProgressUpdate);
/*     */     
/* 140 */     a(new File(localFile2, "region"), localArrayList2, new WorldChunkManagerHell(BiomeBase.HELL, 0.0F), localArrayList1.size(), i, paramIProgressUpdate);
/*     */     
/* 142 */     a(new File(localFile3, "region"), localArrayList3, new WorldChunkManagerHell(BiomeBase.SKY, 0.0F), localArrayList1.size() + localArrayList2.size(), i, paramIProgressUpdate);
/*     */     
/* 144 */     localWorldData.e(19133);
/* 145 */     if (localWorldData.getType() == WorldType.NORMAL_1_1) {
/* 146 */       localWorldData.a(WorldType.NORMAL);
/*     */     }
/*     */     
/* 149 */     g(paramString);
/*     */     
/* 151 */     IDataManager localIDataManager = a(paramString, false);
/* 152 */     localIDataManager.saveWorldData(localWorldData);
/*     */     
/* 154 */     return true;
/*     */   }
/*     */   
/*     */   private void g(String paramString) {
/* 158 */     File localFile1 = new File(this.a, paramString);
/* 159 */     if (!localFile1.exists()) {
/* 160 */       b.warn("Unable to create level.dat_mcr backup");
/* 161 */       return;
/*     */     }
/*     */     
/* 164 */     File localFile2 = new File(localFile1, "level.dat");
/* 165 */     if (!localFile2.exists()) {
/* 166 */       b.warn("Unable to create level.dat_mcr backup");
/* 167 */       return;
/*     */     }
/*     */     
/* 170 */     File localFile3 = new File(localFile1, "level.dat_mcr");
/* 171 */     if (!localFile2.renameTo(localFile3)) {
/* 172 */       b.warn("Unable to create level.dat_mcr backup");
/*     */     }
/*     */   }
/*     */   
/*     */   private void a(File paramFile, Iterable<File> paramIterable, WorldChunkManager paramWorldChunkManager, int paramInt1, int paramInt2, IProgressUpdate paramIProgressUpdate) {
/* 177 */     for (File localFile : paramIterable) {
/* 178 */       a(paramFile, localFile, paramWorldChunkManager, paramInt1, paramInt2, paramIProgressUpdate);
/*     */       
/* 180 */       paramInt1++;
/* 181 */       int i = (int)Math.round(100.0D * paramInt1 / paramInt2);
/* 182 */       paramIProgressUpdate.a(i);
/*     */     }
/*     */   }
/*     */   
/*     */   private void a(File paramFile1, File paramFile2, WorldChunkManager paramWorldChunkManager, int paramInt1, int paramInt2, IProgressUpdate paramIProgressUpdate) {
/*     */     try {
/* 188 */       String str = paramFile2.getName();
/*     */       
/* 190 */       RegionFile localRegionFile1 = new RegionFile(paramFile2);
/* 191 */       RegionFile localRegionFile2 = new RegionFile(new File(paramFile1, str.substring(0, str.length() - ".mcr".length()) + ".mca"));
/*     */       
/* 193 */       for (int i = 0; i < 32; i++) {
/* 194 */         for (int j = 0; j < 32; j++)
/* 195 */           if ((localRegionFile1.c(i, j)) && (!localRegionFile2.c(i, j))) {
/* 196 */             DataInputStream localDataInputStream = localRegionFile1.a(i, j);
/* 197 */             if (localDataInputStream == null) {
/* 198 */               b.warn("Failed to fetch input stream");
/*     */             }
/*     */             else {
/* 201 */               NBTTagCompound localNBTTagCompound1 = NBTCompressedStreamTools.a(localDataInputStream);
/* 202 */               localDataInputStream.close();
/*     */               
/* 204 */               NBTTagCompound localNBTTagCompound2 = localNBTTagCompound1.getCompound("Level");
/* 205 */               OldChunkLoader.OldChunk localOldChunk = OldChunkLoader.a(localNBTTagCompound2);
/*     */               
/* 207 */               NBTTagCompound localNBTTagCompound3 = new NBTTagCompound();
/* 208 */               NBTTagCompound localNBTTagCompound4 = new NBTTagCompound();
/* 209 */               localNBTTagCompound3.set("Level", localNBTTagCompound4);
/* 210 */               OldChunkLoader.a(localOldChunk, localNBTTagCompound4, paramWorldChunkManager);
/*     */               
/* 212 */               DataOutputStream localDataOutputStream = localRegionFile2.b(i, j);
/* 213 */               NBTCompressedStreamTools.a(localNBTTagCompound3, localDataOutputStream);
/* 214 */               localDataOutputStream.close();
/*     */             }
/*     */           }
/* 217 */         j = (int)Math.round(100.0D * (paramInt1 * 1024) / (paramInt2 * 1024));
/* 218 */         int k = (int)Math.round(100.0D * ((i + 1) * 32 + paramInt1 * 1024) / (paramInt2 * 1024));
/* 219 */         if (k > j) {
/* 220 */           paramIProgressUpdate.a(k);
/*     */         }
/*     */       }
/*     */       
/* 224 */       localRegionFile1.c();
/* 225 */       localRegionFile2.c();
/*     */     } catch (IOException localIOException) {
/* 227 */       localIOException.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   class ChunkFilenameFilter implements FilenameFilter
/*     */   {
/*     */     ChunkFilenameFilter() {}
/*     */     
/*     */     public boolean accept(File paramFile, String paramString) {
/* 236 */       return paramString.endsWith(".mcr");
/*     */     }
/*     */   }
/*     */   
/*     */   private void a(File paramFile, Collection<File> paramCollection)
/*     */   {
/* 232 */     File localFile = new File(paramFile, "region");
/* 233 */     File[] arrayOfFile = localFile.listFiles(new ChunkFilenameFilter());
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 240 */     if (arrayOfFile != null) {
/* 241 */       Collections.addAll(paramCollection, arrayOfFile);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldLoaderServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */