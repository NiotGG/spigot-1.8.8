/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.io.DataInputStream;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class RegionFileCache
/*    */ {
/* 13 */   public static final Map<File, RegionFile> a = ;
/*    */   
/*    */   public static synchronized RegionFile a(File file, int i, int j) {
/* 16 */     File file1 = new File(file, "region");
/* 17 */     File file2 = new File(file1, "r." + (i >> 5) + "." + (j >> 5) + ".mca");
/* 18 */     RegionFile regionfile = (RegionFile)a.get(file2);
/*    */     
/* 20 */     if (regionfile != null) {
/* 21 */       return regionfile;
/*    */     }
/* 23 */     if (!file1.exists()) {
/* 24 */       file1.mkdirs();
/*    */     }
/*    */     
/* 27 */     if (a.size() >= 256) {
/* 28 */       a();
/*    */     }
/*    */     
/* 31 */     RegionFile regionfile1 = new RegionFile(file2);
/*    */     
/* 33 */     a.put(file2, regionfile1);
/* 34 */     return regionfile1;
/*    */   }
/*    */   
/*    */   public static synchronized void a()
/*    */   {
/* 39 */     Iterator iterator = a.values().iterator();
/*    */     
/* 41 */     while (iterator.hasNext()) {
/* 42 */       RegionFile regionfile = (RegionFile)iterator.next();
/*    */       try
/*    */       {
/* 45 */         if (regionfile != null) {
/* 46 */           regionfile.c();
/*    */         }
/*    */       } catch (IOException ioexception) {
/* 49 */         ioexception.printStackTrace();
/*    */       }
/*    */     }
/*    */     
/* 53 */     a.clear();
/*    */   }
/*    */   
/*    */   public static DataInputStream c(File file, int i, int j) {
/* 57 */     RegionFile regionfile = a(file, i, j);
/*    */     
/* 59 */     return regionfile.a(i & 0x1F, j & 0x1F);
/*    */   }
/*    */   
/*    */   public static java.io.DataOutputStream d(File file, int i, int j) {
/* 63 */     RegionFile regionfile = a(file, i, j);
/*    */     
/* 65 */     return regionfile.b(i & 0x1F, j & 0x1F);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\RegionFileCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */