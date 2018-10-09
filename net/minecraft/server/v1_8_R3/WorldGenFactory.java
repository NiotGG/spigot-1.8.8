/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenFactory
/*    */ {
/* 18 */   private static final Logger a = ;
/* 19 */   private static Map<String, Class<? extends StructureStart>> b = Maps.newHashMap();
/* 20 */   private static Map<Class<? extends StructureStart>, String> c = Maps.newHashMap();
/*    */   
/* 22 */   private static Map<String, Class<? extends StructurePiece>> d = Maps.newHashMap();
/* 23 */   private static Map<Class<? extends StructurePiece>, String> e = Maps.newHashMap();
/*    */   
/*    */   private static void b(Class<? extends StructureStart> paramClass, String paramString) {
/* 26 */     b.put(paramString, paramClass);
/* 27 */     c.put(paramClass, paramString);
/*    */   }
/*    */   
/*    */   static void a(Class<? extends StructurePiece> paramClass, String paramString) {
/* 31 */     d.put(paramString, paramClass);
/* 32 */     e.put(paramClass, paramString);
/*    */   }
/*    */   
/*    */   static {
/* 36 */     b(WorldGenMineshaftStart.class, "Mineshaft");
/* 37 */     b(WorldGenVillage.WorldGenVillageStart.class, "Village");
/* 38 */     b(WorldGenNether.WorldGenNetherStart.class, "Fortress");
/* 39 */     b(WorldGenStronghold.WorldGenStronghold2Start.class, "Stronghold");
/* 40 */     b(WorldGenLargeFeature.WorldGenLargeFeatureStart.class, "Temple");
/* 41 */     b(WorldGenMonument.WorldGenMonumentStart.class, "Monument");
/*    */     
/* 43 */     WorldGenMineshaftPieces.a();
/* 44 */     WorldGenVillagePieces.a();
/* 45 */     WorldGenNetherPieces.a();
/* 46 */     WorldGenStrongholdPieces.a();
/* 47 */     WorldGenRegistration.a();
/* 48 */     WorldGenMonumentPieces.a();
/*    */   }
/*    */   
/*    */   public static String a(StructureStart paramStructureStart) {
/* 52 */     return (String)c.get(paramStructureStart.getClass());
/*    */   }
/*    */   
/*    */   public static String a(StructurePiece paramStructurePiece) {
/* 56 */     return (String)e.get(paramStructurePiece.getClass());
/*    */   }
/*    */   
/*    */   public static StructureStart a(NBTTagCompound paramNBTTagCompound, World paramWorld)
/*    */   {
/* 61 */     StructureStart localStructureStart = null;
/*    */     try
/*    */     {
/* 64 */       Class localClass = (Class)b.get(paramNBTTagCompound.getString("id"));
/* 65 */       if (localClass != null) {
/* 66 */         localStructureStart = (StructureStart)localClass.newInstance();
/*    */       }
/*    */     } catch (Exception localException) {
/* 69 */       a.warn("Failed Start with id " + paramNBTTagCompound.getString("id"));
/* 70 */       localException.printStackTrace();
/*    */     }
/* 72 */     if (localStructureStart != null) {
/* 73 */       localStructureStart.a(paramWorld, paramNBTTagCompound);
/*    */     } else {
/* 75 */       a.warn("Skipping Structure with id " + paramNBTTagCompound.getString("id"));
/*    */     }
/* 77 */     return localStructureStart;
/*    */   }
/*    */   
/*    */   public static StructurePiece b(NBTTagCompound paramNBTTagCompound, World paramWorld) {
/* 81 */     StructurePiece localStructurePiece = null;
/*    */     try
/*    */     {
/* 84 */       Class localClass = (Class)d.get(paramNBTTagCompound.getString("id"));
/* 85 */       if (localClass != null) {
/* 86 */         localStructurePiece = (StructurePiece)localClass.newInstance();
/*    */       }
/*    */     } catch (Exception localException) {
/* 89 */       a.warn("Failed Piece with id " + paramNBTTagCompound.getString("id"));
/* 90 */       localException.printStackTrace();
/*    */     }
/* 92 */     if (localStructurePiece != null) {
/* 93 */       localStructurePiece.a(paramWorld, paramNBTTagCompound);
/*    */     } else {
/* 95 */       a.warn("Skipping Piece with id " + paramNBTTagCompound.getString("id"));
/*    */     }
/* 97 */     return localStructurePiece;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */