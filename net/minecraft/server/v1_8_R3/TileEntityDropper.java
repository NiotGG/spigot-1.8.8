/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class TileEntityDropper
/*    */   extends TileEntityDispenser
/*    */ {
/*    */   public String getName()
/*    */   {
/*  8 */     return hasCustomName() ? this.a : "container.dropper";
/*    */   }
/*    */   
/*    */   public String getContainerName()
/*    */   {
/* 13 */     return "minecraft:dropper";
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\TileEntityDropper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */