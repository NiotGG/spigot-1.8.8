/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldLoader
/*     */   implements Convertable
/*     */ {
/*  16 */   private static final Logger b = ;
/*     */   protected final File a;
/*     */   
/*     */   public WorldLoader(File paramFile)
/*     */   {
/*  21 */     if (!paramFile.exists()) {
/*  22 */       paramFile.mkdirs();
/*     */     }
/*  24 */     this.a = paramFile;
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
/*     */   public void d() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public WorldData c(String paramString)
/*     */   {
/*  54 */     File localFile1 = new File(this.a, paramString);
/*  55 */     if (!localFile1.exists()) {
/*  56 */       return null;
/*     */     }
/*     */     
/*  59 */     File localFile2 = new File(localFile1, "level.dat");
/*  60 */     NBTTagCompound localNBTTagCompound3; if (localFile2.exists()) {
/*     */       try {
/*  62 */         NBTTagCompound localNBTTagCompound1 = NBTCompressedStreamTools.a(new FileInputStream(localFile2));
/*  63 */         localNBTTagCompound3 = localNBTTagCompound1.getCompound("Data");
/*  64 */         return new WorldData(localNBTTagCompound3);
/*     */       } catch (Exception localException1) {
/*  66 */         b.error("Exception reading " + localFile2, localException1);
/*     */       }
/*     */     }
/*     */     
/*  70 */     localFile2 = new File(localFile1, "level.dat_old");
/*  71 */     if (localFile2.exists()) {
/*     */       try {
/*  73 */         NBTTagCompound localNBTTagCompound2 = NBTCompressedStreamTools.a(new FileInputStream(localFile2));
/*  74 */         localNBTTagCompound3 = localNBTTagCompound2.getCompound("Data");
/*  75 */         return new WorldData(localNBTTagCompound3);
/*     */       } catch (Exception localException2) {
/*  77 */         b.error("Exception reading " + localFile2, localException2);
/*     */       }
/*     */     }
/*  80 */     return null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean e(String paramString)
/*     */   {
/* 124 */     File localFile = new File(this.a, paramString);
/* 125 */     if (!localFile.exists()) {
/* 126 */       return true;
/*     */     }
/*     */     
/* 129 */     b.info("Deleting level " + paramString);
/*     */     
/* 131 */     for (int i = 1; i <= 5; i++) {
/* 132 */       b.info("Attempt " + i + "...");
/*     */       
/* 134 */       if (a(localFile.listFiles())) {
/*     */         break;
/*     */       }
/* 137 */       b.warn("Unsuccessful in deleting contents.");
/*     */       
/*     */ 
/* 140 */       if (i < 5) {
/*     */         try {
/* 142 */           Thread.sleep(500L);
/*     */         }
/*     */         catch (InterruptedException localInterruptedException) {}
/*     */       }
/*     */     }
/*     */     
/* 148 */     return localFile.delete();
/*     */   }
/*     */   
/*     */   protected static boolean a(File[] paramArrayOfFile) {
/* 152 */     for (int i = 0; i < paramArrayOfFile.length; i++) {
/* 153 */       File localFile = paramArrayOfFile[i];
/* 154 */       b.debug("Deleting " + localFile);
/*     */       
/* 156 */       if ((localFile.isDirectory()) && 
/* 157 */         (!a(localFile.listFiles()))) {
/* 158 */         b.warn("Couldn't delete directory " + localFile);
/* 159 */         return false;
/*     */       }
/*     */       
/*     */ 
/* 163 */       if (!localFile.delete()) {
/* 164 */         b.warn("Couldn't delete file " + localFile);
/* 165 */         return false;
/*     */       }
/*     */     }
/*     */     
/* 169 */     return true;
/*     */   }
/*     */   
/*     */   public IDataManager a(String paramString, boolean paramBoolean)
/*     */   {
/* 174 */     return new WorldNBTStorage(this.a, paramString, paramBoolean);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isConvertable(String paramString)
/*     */   {
/* 184 */     return false;
/*     */   }
/*     */   
/*     */   public boolean convert(String paramString, IProgressUpdate paramIProgressUpdate)
/*     */   {
/* 189 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */