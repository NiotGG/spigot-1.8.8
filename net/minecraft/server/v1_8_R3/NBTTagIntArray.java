/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.DataInput;
/*    */ import java.io.DataOutput;
/*    */ import java.util.Arrays;
/*    */ 
/*    */ public class NBTTagIntArray extends NBTBase
/*    */ {
/*    */   private int[] data;
/*    */   
/*    */   NBTTagIntArray() {}
/*    */   
/*    */   public NBTTagIntArray(int[] aint)
/*    */   {
/* 15 */     this.data = aint;
/*    */   }
/*    */   
/*    */   void write(DataOutput dataoutput) throws java.io.IOException {
/* 19 */     dataoutput.writeInt(this.data.length);
/*    */     
/* 21 */     for (int i = 0; i < this.data.length; i++) {
/* 22 */       dataoutput.writeInt(this.data[i]);
/*    */     }
/*    */   }
/*    */   
/*    */   void load(DataInput datainput, int i, NBTReadLimiter nbtreadlimiter) throws java.io.IOException
/*    */   {
/* 28 */     nbtreadlimiter.a(192L);
/* 29 */     int j = datainput.readInt();
/* 30 */     com.google.common.base.Preconditions.checkArgument(j < 16777216);
/*    */     
/* 32 */     nbtreadlimiter.a(32 * j);
/* 33 */     this.data = new int[j];
/*    */     
/* 35 */     for (int k = 0; k < j; k++) {
/* 36 */       this.data[k] = datainput.readInt();
/*    */     }
/*    */   }
/*    */   
/*    */   public byte getTypeId()
/*    */   {
/* 42 */     return 11;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 46 */     String s = "[";
/* 47 */     int[] aint = this.data;
/* 48 */     int i = aint.length;
/*    */     
/* 50 */     for (int j = 0; j < i; j++) {
/* 51 */       int k = aint[j];
/*    */       
/* 53 */       s = s + k + ",";
/*    */     }
/*    */     
/* 56 */     return s + "]";
/*    */   }
/*    */   
/*    */   public NBTBase clone() {
/* 60 */     int[] aint = new int[this.data.length];
/*    */     
/* 62 */     System.arraycopy(this.data, 0, aint, 0, this.data.length);
/* 63 */     return new NBTTagIntArray(aint);
/*    */   }
/*    */   
/*    */   public boolean equals(Object object) {
/* 67 */     return super.equals(object) ? Arrays.equals(this.data, ((NBTTagIntArray)object).data) : false;
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 71 */     return super.hashCode() ^ Arrays.hashCode(this.data);
/*    */   }
/*    */   
/*    */   public int[] c() {
/* 75 */     return this.data;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\NBTTagIntArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */