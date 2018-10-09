/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.DataInput;
/*    */ import java.io.DataOutput;
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NBTTagString
/*    */   extends NBTBase
/*    */ {
/*    */   private String data;
/*    */   
/*    */   public NBTTagString()
/*    */   {
/* 17 */     this.data = "";
/*    */   }
/*    */   
/*    */   public NBTTagString(String paramString) {
/* 21 */     this.data = paramString;
/* 22 */     if (paramString == null) {
/* 23 */       throw new IllegalArgumentException("Empty string not allowed");
/*    */     }
/*    */   }
/*    */   
/*    */   void write(DataOutput paramDataOutput) throws IOException
/*    */   {
/* 29 */     paramDataOutput.writeUTF(this.data);
/*    */   }
/*    */   
/*    */   void load(DataInput paramDataInput, int paramInt, NBTReadLimiter paramNBTReadLimiter) throws IOException
/*    */   {
/* 34 */     paramNBTReadLimiter.a(288L);
/*    */     
/*    */ 
/* 37 */     this.data = paramDataInput.readUTF();
/* 38 */     paramNBTReadLimiter.a(16 * this.data.length());
/*    */   }
/*    */   
/*    */   public byte getTypeId()
/*    */   {
/* 43 */     return 8;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 48 */     return "\"" + this.data.replace("\"", "\\\"") + "\"";
/*    */   }
/*    */   
/*    */   public NBTBase clone()
/*    */   {
/* 53 */     return new NBTTagString(this.data);
/*    */   }
/*    */   
/*    */   public boolean isEmpty()
/*    */   {
/* 58 */     return this.data.isEmpty();
/*    */   }
/*    */   
/*    */   public boolean equals(Object paramObject)
/*    */   {
/* 63 */     if (super.equals(paramObject)) {
/* 64 */       NBTTagString localNBTTagString = (NBTTagString)paramObject;
/* 65 */       return ((this.data == null) && (localNBTTagString.data == null)) || ((this.data != null) && (this.data.equals(localNBTTagString.data)));
/*    */     }
/* 67 */     return false;
/*    */   }
/*    */   
/*    */   public int hashCode()
/*    */   {
/* 72 */     return super.hashCode() ^ this.data.hashCode();
/*    */   }
/*    */   
/*    */   public String a_()
/*    */   {
/* 77 */     return this.data;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\NBTTagString.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */