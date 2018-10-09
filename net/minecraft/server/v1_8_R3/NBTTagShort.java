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
/*    */ public class NBTTagShort
/*    */   extends NBTBase.NBTNumber
/*    */ {
/*    */   private short data;
/*    */   
/*    */   public NBTTagShort() {}
/*    */   
/*    */   public NBTTagShort(short paramShort)
/*    */   {
/* 22 */     this.data = paramShort;
/*    */   }
/*    */   
/*    */   void write(DataOutput paramDataOutput) throws IOException
/*    */   {
/* 27 */     paramDataOutput.writeShort(this.data);
/*    */   }
/*    */   
/*    */   void load(DataInput paramDataInput, int paramInt, NBTReadLimiter paramNBTReadLimiter) throws IOException
/*    */   {
/* 32 */     paramNBTReadLimiter.a(80L);
/* 33 */     this.data = paramDataInput.readShort();
/*    */   }
/*    */   
/*    */   public byte getTypeId()
/*    */   {
/* 38 */     return 2;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 43 */     return "" + this.data + "s";
/*    */   }
/*    */   
/*    */   public NBTBase clone()
/*    */   {
/* 48 */     return new NBTTagShort(this.data);
/*    */   }
/*    */   
/*    */   public boolean equals(Object paramObject)
/*    */   {
/* 53 */     if (super.equals(paramObject)) {
/* 54 */       NBTTagShort localNBTTagShort = (NBTTagShort)paramObject;
/* 55 */       return this.data == localNBTTagShort.data;
/*    */     }
/* 57 */     return false;
/*    */   }
/*    */   
/*    */   public int hashCode()
/*    */   {
/* 62 */     return super.hashCode() ^ this.data;
/*    */   }
/*    */   
/*    */   public long c()
/*    */   {
/* 67 */     return this.data;
/*    */   }
/*    */   
/*    */   public int d()
/*    */   {
/* 72 */     return this.data;
/*    */   }
/*    */   
/*    */   public short e()
/*    */   {
/* 77 */     return this.data;
/*    */   }
/*    */   
/*    */   public byte f()
/*    */   {
/* 82 */     return (byte)(this.data & 0xFF);
/*    */   }
/*    */   
/*    */   public double g()
/*    */   {
/* 87 */     return this.data;
/*    */   }
/*    */   
/*    */   public float h()
/*    */   {
/* 92 */     return this.data;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\NBTTagShort.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */