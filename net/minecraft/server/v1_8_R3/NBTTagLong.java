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
/*    */ public class NBTTagLong
/*    */   extends NBTBase.NBTNumber
/*    */ {
/*    */   private long data;
/*    */   
/*    */   NBTTagLong() {}
/*    */   
/*    */   public NBTTagLong(long paramLong)
/*    */   {
/* 22 */     this.data = paramLong;
/*    */   }
/*    */   
/*    */   void write(DataOutput paramDataOutput) throws IOException
/*    */   {
/* 27 */     paramDataOutput.writeLong(this.data);
/*    */   }
/*    */   
/*    */   void load(DataInput paramDataInput, int paramInt, NBTReadLimiter paramNBTReadLimiter) throws IOException
/*    */   {
/* 32 */     paramNBTReadLimiter.a(128L);
/* 33 */     this.data = paramDataInput.readLong();
/*    */   }
/*    */   
/*    */   public byte getTypeId()
/*    */   {
/* 38 */     return 4;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 43 */     return "" + this.data + "L";
/*    */   }
/*    */   
/*    */   public NBTBase clone()
/*    */   {
/* 48 */     return new NBTTagLong(this.data);
/*    */   }
/*    */   
/*    */   public boolean equals(Object paramObject)
/*    */   {
/* 53 */     if (super.equals(paramObject)) {
/* 54 */       NBTTagLong localNBTTagLong = (NBTTagLong)paramObject;
/* 55 */       return this.data == localNBTTagLong.data;
/*    */     }
/* 57 */     return false;
/*    */   }
/*    */   
/*    */   public int hashCode()
/*    */   {
/* 62 */     return super.hashCode() ^ (int)(this.data ^ this.data >>> 32);
/*    */   }
/*    */   
/*    */   public long c()
/*    */   {
/* 67 */     return this.data;
/*    */   }
/*    */   
/*    */   public int d()
/*    */   {
/* 72 */     return (int)(this.data & 0xFFFFFFFFFFFFFFFF);
/*    */   }
/*    */   
/*    */   public short e()
/*    */   {
/* 77 */     return (short)(int)(this.data & 0xFFFF);
/*    */   }
/*    */   
/*    */   public byte f()
/*    */   {
/* 82 */     return (byte)(int)(this.data & 0xFF);
/*    */   }
/*    */   
/*    */   public double g()
/*    */   {
/* 87 */     return this.data;
/*    */   }
/*    */   
/*    */   public float h()
/*    */   {
/* 92 */     return (float)this.data;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\NBTTagLong.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */