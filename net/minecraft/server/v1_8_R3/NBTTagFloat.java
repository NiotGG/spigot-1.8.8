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
/*    */ 
/*    */ 
/*    */ public class NBTTagFloat
/*    */   extends NBTBase.NBTNumber
/*    */ {
/*    */   private float data;
/*    */   
/*    */   NBTTagFloat() {}
/*    */   
/*    */   public NBTTagFloat(float paramFloat)
/*    */   {
/* 23 */     this.data = paramFloat;
/*    */   }
/*    */   
/*    */   void write(DataOutput paramDataOutput) throws IOException
/*    */   {
/* 28 */     paramDataOutput.writeFloat(this.data);
/*    */   }
/*    */   
/*    */   void load(DataInput paramDataInput, int paramInt, NBTReadLimiter paramNBTReadLimiter) throws IOException
/*    */   {
/* 33 */     paramNBTReadLimiter.a(96L);
/* 34 */     this.data = paramDataInput.readFloat();
/*    */   }
/*    */   
/*    */   public byte getTypeId()
/*    */   {
/* 39 */     return 5;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 44 */     return "" + this.data + "f";
/*    */   }
/*    */   
/*    */   public NBTBase clone()
/*    */   {
/* 49 */     return new NBTTagFloat(this.data);
/*    */   }
/*    */   
/*    */   public boolean equals(Object paramObject)
/*    */   {
/* 54 */     if (super.equals(paramObject)) {
/* 55 */       NBTTagFloat localNBTTagFloat = (NBTTagFloat)paramObject;
/* 56 */       return this.data == localNBTTagFloat.data;
/*    */     }
/* 58 */     return false;
/*    */   }
/*    */   
/*    */   public int hashCode()
/*    */   {
/* 63 */     return super.hashCode() ^ Float.floatToIntBits(this.data);
/*    */   }
/*    */   
/*    */   public long c()
/*    */   {
/* 68 */     return this.data;
/*    */   }
/*    */   
/*    */   public int d()
/*    */   {
/* 73 */     return MathHelper.d(this.data);
/*    */   }
/*    */   
/*    */   public short e()
/*    */   {
/* 78 */     return (short)(MathHelper.d(this.data) & 0xFFFF);
/*    */   }
/*    */   
/*    */   public byte f()
/*    */   {
/* 83 */     return (byte)(MathHelper.d(this.data) & 0xFF);
/*    */   }
/*    */   
/*    */   public double g()
/*    */   {
/* 88 */     return this.data;
/*    */   }
/*    */   
/*    */   public float h()
/*    */   {
/* 93 */     return this.data;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\NBTTagFloat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */