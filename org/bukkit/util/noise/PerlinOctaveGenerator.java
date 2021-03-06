/*    */ package org.bukkit.util.noise;
/*    */ 
/*    */ import java.util.Random;
/*    */ import org.bukkit.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PerlinOctaveGenerator
/*    */   extends OctaveGenerator
/*    */ {
/*    */   public PerlinOctaveGenerator(World world, int octaves)
/*    */   {
/* 18 */     this(new Random(world.getSeed()), octaves);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public PerlinOctaveGenerator(long seed, int octaves)
/*    */   {
/* 28 */     this(new Random(seed), octaves);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public PerlinOctaveGenerator(Random rand, int octaves)
/*    */   {
/* 38 */     super(createOctaves(rand, octaves));
/*    */   }
/*    */   
/*    */   private static NoiseGenerator[] createOctaves(Random rand, int octaves) {
/* 42 */     NoiseGenerator[] result = new NoiseGenerator[octaves];
/*    */     
/* 44 */     for (int i = 0; i < octaves; i++) {
/* 45 */       result[i] = new PerlinNoiseGenerator(rand);
/*    */     }
/*    */     
/* 48 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\util\noise\PerlinOctaveGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */