/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.DataInput;
/*    */ import java.io.DataOutput;
/*    */ import java.util.Arrays;
/*    */ 
/*    */ public class NBTTagByteArray extends NBTBase
/*    */ {
/*    */   private byte[] data;
/*    */   
/*    */   NBTTagByteArray() {}
/*    */   
/*    */   public NBTTagByteArray(byte[] abyte)
/*    */   {
/* 15 */     this.data = abyte;
/*    */   }
/*    */   
/*    */   void write(DataOutput dataoutput) throws java.io.IOException {
/* 19 */     dataoutput.writeInt(this.data.length);
/* 20 */     dataoutput.write(this.data);
/*    */   }
/*    */   
/*    */   void load(DataInput datainput, int i, NBTReadLimiter nbtreadlimiter) throws java.io.IOException {
/* 24 */     nbtreadlimiter.a(192L);
/* 25 */     int j = datainput.readInt();
/* 26 */     com.google.common.base.Preconditions.checkArgument(j < 16777216);
/*    */     
/* 28 */     nbtreadlimiter.a(8 * j);
/* 29 */     this.data = new byte[j];
/* 30 */     datainput.readFully(this.data);
/*    */   }
/*    */   
/*    */   public byte getTypeId() {
/* 34 */     return 7;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 38 */     return "[" + this.data.length + " bytes]";
/*    */   }
/*    */   
/*    */   public NBTBase clone() {
/* 42 */     byte[] abyte = new byte[this.data.length];
/*    */     
/* 44 */     System.arraycopy(this.data, 0, abyte, 0, this.data.length);
/* 45 */     return new NBTTagByteArray(abyte);
/*    */   }
/*    */   
/*    */   public boolean equals(Object object) {
/* 49 */     return super.equals(object) ? Arrays.equals(this.data, ((NBTTagByteArray)object).data) : false;
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 53 */     return super.hashCode() ^ Arrays.hashCode(this.data);
/*    */   }
/*    */   
/*    */   public byte[] c() {
/* 57 */     return this.data;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\NBTTagByteArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */