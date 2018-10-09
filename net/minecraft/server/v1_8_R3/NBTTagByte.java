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
/*    */ public class NBTTagByte
/*    */   extends NBTBase.NBTNumber
/*    */ {
/*    */   private byte data;
/*    */   
/*    */   NBTTagByte() {}
/*    */   
/*    */   public NBTTagByte(byte paramByte)
/*    */   {
/* 22 */     this.data = paramByte;
/*    */   }
/*    */   
/*    */   void write(DataOutput paramDataOutput) throws IOException
/*    */   {
/* 27 */     paramDataOutput.writeByte(this.data);
/*    */   }
/*    */   
/*    */   void load(DataInput paramDataInput, int paramInt, NBTReadLimiter paramNBTReadLimiter) throws IOException
/*    */   {
/* 32 */     paramNBTReadLimiter.a(72L);
/* 33 */     this.data = paramDataInput.readByte();
/*    */   }
/*    */   
/*    */   public byte getTypeId()
/*    */   {
/* 38 */     return 1;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 43 */     return "" + this.data + "b";
/*    */   }
/*    */   
/*    */   public NBTBase clone()
/*    */   {
/* 48 */     return new NBTTagByte(this.data);
/*    */   }
/*    */   
/*    */   public boolean equals(Object paramObject)
/*    */   {
/* 53 */     if (super.equals(paramObject)) {
/* 54 */       NBTTagByte localNBTTagByte = (NBTTagByte)paramObject;
/* 55 */       return this.data == localNBTTagByte.data;
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
/* 77 */     return (short)this.data;
/*    */   }
/*    */   
/*    */   public byte f()
/*    */   {
/* 82 */     return this.data;
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


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\NBTTagByte.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */