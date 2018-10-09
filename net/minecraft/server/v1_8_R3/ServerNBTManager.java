/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.File;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ServerNBTManager
/*    */   extends WorldNBTStorage
/*    */ {
/*    */   public ServerNBTManager(File paramFile, String paramString, boolean paramBoolean)
/*    */   {
/* 19 */     super(paramFile, paramString, paramBoolean);
/*    */   }
/*    */   
/*    */   public IChunkLoader createChunkLoader(WorldProvider paramWorldProvider)
/*    */   {
/* 24 */     File localFile1 = getDirectory();
/*    */     File localFile2;
/* 26 */     if ((paramWorldProvider instanceof WorldProviderHell)) {
/* 27 */       localFile2 = new File(localFile1, "DIM-1");
/* 28 */       localFile2.mkdirs();
/* 29 */       return new ChunkRegionLoader(localFile2);
/*    */     }
/* 31 */     if ((paramWorldProvider instanceof WorldProviderTheEnd)) {
/* 32 */       localFile2 = new File(localFile1, "DIM1");
/* 33 */       localFile2.mkdirs();
/* 34 */       return new ChunkRegionLoader(localFile2);
/*    */     }
/*    */     
/* 37 */     return new ChunkRegionLoader(localFile1);
/*    */   }
/*    */   
/*    */   public void saveWorldData(WorldData paramWorldData, NBTTagCompound paramNBTTagCompound)
/*    */   {
/* 42 */     paramWorldData.e(19133);
/* 43 */     super.saveWorldData(paramWorldData, paramNBTTagCompound);
/*    */   }
/*    */   
/*    */   public void a()
/*    */   {
/*    */     try {
/* 49 */       FileIOThread.a().b();
/*    */     } catch (InterruptedException localInterruptedException) {
/* 51 */       localInterruptedException.printStackTrace();
/*    */     }
/*    */     
/* 54 */     RegionFileCache.a();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ServerNBTManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */