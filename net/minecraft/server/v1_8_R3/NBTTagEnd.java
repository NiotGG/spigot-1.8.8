/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.DataInput;
/*    */ import java.io.DataOutput;
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NBTTagEnd
/*    */   extends NBTBase
/*    */ {
/*    */   void load(DataInput paramDataInput, int paramInt, NBTReadLimiter paramNBTReadLimiter)
/*    */     throws IOException
/*    */   {
/* 18 */     paramNBTReadLimiter.a(64L);
/*    */   }
/*    */   
/*    */   void write(DataOutput paramDataOutput)
/*    */     throws IOException
/*    */   {}
/*    */   
/*    */   public byte getTypeId()
/*    */   {
/* 27 */     return 0;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 32 */     return "END";
/*    */   }
/*    */   
/*    */   public NBTBase clone()
/*    */   {
/* 37 */     return new NBTTagEnd();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\NBTTagEnd.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */