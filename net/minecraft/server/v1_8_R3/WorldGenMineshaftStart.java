/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.LinkedList;
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ public class WorldGenMineshaftStart
/*    */   extends StructureStart
/*    */ {
/*    */   public WorldGenMineshaftStart() {}
/*    */   
/*    */   public WorldGenMineshaftStart(World paramWorld, Random paramRandom, int paramInt1, int paramInt2)
/*    */   {
/* 14 */     super(paramInt1, paramInt2);
/*    */     
/* 16 */     WorldGenMineshaftPieces.WorldGenMineshaftRoom localWorldGenMineshaftRoom = new WorldGenMineshaftPieces.WorldGenMineshaftRoom(0, paramRandom, (paramInt1 << 4) + 2, (paramInt2 << 4) + 2);
/* 17 */     this.a.add(localWorldGenMineshaftRoom);
/* 18 */     localWorldGenMineshaftRoom.a(localWorldGenMineshaftRoom, this.a, paramRandom);
/*    */     
/* 20 */     c();
/* 21 */     a(paramWorld, paramRandom, 10);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenMineshaftStart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */