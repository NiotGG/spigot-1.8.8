/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GenLayerBiome
/*    */   extends GenLayer
/*    */ {
/*  8 */   private BiomeBase[] c = { BiomeBase.DESERT, BiomeBase.DESERT, BiomeBase.DESERT, BiomeBase.SAVANNA, BiomeBase.SAVANNA, BiomeBase.PLAINS };
/*    */   
/*    */ 
/*    */ 
/* 12 */   private BiomeBase[] d = { BiomeBase.FOREST, BiomeBase.ROOFED_FOREST, BiomeBase.EXTREME_HILLS, BiomeBase.PLAINS, BiomeBase.BIRCH_FOREST, BiomeBase.SWAMPLAND };
/*    */   
/*    */ 
/*    */ 
/* 16 */   private BiomeBase[] e = { BiomeBase.FOREST, BiomeBase.EXTREME_HILLS, BiomeBase.TAIGA, BiomeBase.PLAINS };
/*    */   
/*    */ 
/*    */ 
/* 20 */   private BiomeBase[] f = { BiomeBase.ICE_PLAINS, BiomeBase.ICE_PLAINS, BiomeBase.ICE_PLAINS, BiomeBase.COLD_TAIGA };
/*    */   
/*    */   private final CustomWorldSettingsFinal g;
/*    */   
/*    */ 
/*    */   public GenLayerBiome(long paramLong, GenLayer paramGenLayer, WorldType paramWorldType, String paramString)
/*    */   {
/* 27 */     super(paramLong);
/* 28 */     this.a = paramGenLayer;
/*    */     
/* 30 */     if (paramWorldType == WorldType.NORMAL_1_1) {
/* 31 */       this.c = new BiomeBase[] { BiomeBase.DESERT, BiomeBase.FOREST, BiomeBase.EXTREME_HILLS, BiomeBase.SWAMPLAND, BiomeBase.PLAINS, BiomeBase.TAIGA };
/*    */       
/*    */ 
/* 34 */       this.g = null;
/* 35 */     } else if (paramWorldType == WorldType.CUSTOMIZED) {
/* 36 */       this.g = CustomWorldSettingsFinal.CustomWorldSettings.a(paramString).b();
/*    */     } else {
/* 38 */       this.g = null;
/*    */     }
/*    */   }
/*    */   
/*    */   public int[] a(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*    */   {
/* 44 */     int[] arrayOfInt1 = this.a.a(paramInt1, paramInt2, paramInt3, paramInt4);
/*    */     
/* 46 */     int[] arrayOfInt2 = IntCache.a(paramInt3 * paramInt4);
/* 47 */     for (int i = 0; i < paramInt4; i++) {
/* 48 */       for (int j = 0; j < paramInt3; j++) {
/* 49 */         a(j + paramInt1, i + paramInt2);
/* 50 */         int k = arrayOfInt1[(j + i * paramInt3)];
/* 51 */         int m = (k & 0xF00) >> 8;
/* 52 */         k &= 0xF0FF;
/* 53 */         if ((this.g != null) && (this.g.F >= 0)) {
/* 54 */           arrayOfInt2[(j + i * paramInt3)] = this.g.F;
/* 55 */         } else if (b(k)) {
/* 56 */           arrayOfInt2[(j + i * paramInt3)] = k;
/* 57 */         } else if (k == BiomeBase.MUSHROOM_ISLAND.id) {
/* 58 */           arrayOfInt2[(j + i * paramInt3)] = k;
/* 59 */         } else if (k == 1) {
/* 60 */           if (m > 0) {
/* 61 */             if (a(3) == 0) {
/* 62 */               arrayOfInt2[(j + i * paramInt3)] = BiomeBase.MESA_PLATEAU.id;
/*    */             } else {
/* 64 */               arrayOfInt2[(j + i * paramInt3)] = BiomeBase.MESA_PLATEAU_F.id;
/*    */             }
/*    */           } else {
/* 67 */             arrayOfInt2[(j + i * paramInt3)] = this.c[a(this.c.length)].id;
/*    */           }
/* 69 */         } else if (k == 2) {
/* 70 */           if (m > 0) {
/* 71 */             arrayOfInt2[(j + i * paramInt3)] = BiomeBase.JUNGLE.id;
/*    */           } else {
/* 73 */             arrayOfInt2[(j + i * paramInt3)] = this.d[a(this.d.length)].id;
/*    */           }
/* 75 */         } else if (k == 3) {
/* 76 */           if (m > 0) {
/* 77 */             arrayOfInt2[(j + i * paramInt3)] = BiomeBase.MEGA_TAIGA.id;
/*    */           } else {
/* 79 */             arrayOfInt2[(j + i * paramInt3)] = this.e[a(this.e.length)].id;
/*    */           }
/* 81 */         } else if (k == 4) {
/* 82 */           arrayOfInt2[(j + i * paramInt3)] = this.f[a(this.f.length)].id;
/*    */         } else {
/* 84 */           arrayOfInt2[(j + i * paramInt3)] = BiomeBase.MUSHROOM_ISLAND.id;
/*    */         }
/*    */       }
/*    */     }
/*    */     
/* 89 */     return arrayOfInt2;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\GenLayerBiome.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */