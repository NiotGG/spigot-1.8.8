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
/*    */ public class NBTTagDouble
/*    */   extends NBTBase.NBTNumber
/*    */ {
/*    */   private double data;
/*    */   
/*    */   NBTTagDouble() {}
/*    */   
/*    */   public NBTTagDouble(double paramDouble)
/*    */   {
/* 23 */     this.data = paramDouble;
/*    */   }
/*    */   
/*    */   void write(DataOutput paramDataOutput) throws IOException
/*    */   {
/* 28 */     paramDataOutput.writeDouble(this.data);
/*    */   }
/*    */   
/*    */   void load(DataInput paramDataInput, int paramInt, NBTReadLimiter paramNBTReadLimiter) throws IOException
/*    */   {
/* 33 */     paramNBTReadLimiter.a(128L);
/* 34 */     this.data = paramDataInput.readDouble();
/*    */   }
/*    */   
/*    */   public byte getTypeId()
/*    */   {
/* 39 */     return 6;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 44 */     return "" + this.data + "d";
/*    */   }
/*    */   
/*    */   public NBTBase clone()
/*    */   {
/* 49 */     return new NBTTagDouble(this.data);
/*    */   }
/*    */   
/*    */   public boolean equals(Object paramObject)
/*    */   {
/* 54 */     if (super.equals(paramObject)) {
/* 55 */       NBTTagDouble localNBTTagDouble = (NBTTagDouble)paramObject;
/* 56 */       return this.data == localNBTTagDouble.data;
/*    */     }
/* 58 */     return false;
/*    */   }
/*    */   
/*    */   public int hashCode()
/*    */   {
/* 63 */     long l = Double.doubleToLongBits(this.data);
/* 64 */     return super.hashCode() ^ (int)(l ^ l >>> 32);
/*    */   }
/*    */   
/*    */   public long c()
/*    */   {
/* 69 */     return Math.floor(this.data);
/*    */   }
/*    */   
/*    */   public int d()
/*    */   {
/* 74 */     return MathHelper.floor(this.data);
/*    */   }
/*    */   
/*    */   public short e()
/*    */   {
/* 79 */     return (short)(MathHelper.floor(this.data) & 0xFFFF);
/*    */   }
/*    */   
/*    */   public byte f()
/*    */   {
/* 84 */     return (byte)(MathHelper.floor(this.data) & 0xFF);
/*    */   }
/*    */   
/*    */   public double g()
/*    */   {
/* 89 */     return this.data;
/*    */   }
/*    */   
/*    */   public float h()
/*    */   {
/* 94 */     return (float)this.data;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\NBTTagDouble.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */