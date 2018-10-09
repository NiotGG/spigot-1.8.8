/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.DataInput;
/*    */ import java.io.DataOutput;
/*    */ import java.io.IOException;
/*    */ 
/*    */ public abstract class NBTBase
/*    */ {
/*  9 */   public static final String[] a = { "END", "BYTE", "SHORT", "INT", "LONG", "FLOAT", "DOUBLE", "BYTE[]", "STRING", "LIST", "COMPOUND", "INT[]" };
/*    */   
/*    */   abstract void write(DataOutput paramDataOutput)
/*    */     throws IOException;
/*    */   
/*    */   abstract void load(DataInput paramDataInput, int paramInt, NBTReadLimiter paramNBTReadLimiter) throws IOException;
/*    */   
/*    */   public abstract String toString();
/*    */   
/*    */   public abstract byte getTypeId();
/*    */   
/*    */   protected static NBTBase createTag(byte b0)
/*    */   {
/* 22 */     switch (b0) {
/*    */     case 0: 
/* 24 */       return new NBTTagEnd();
/*    */     
/*    */     case 1: 
/* 27 */       return new NBTTagByte();
/*    */     
/*    */     case 2: 
/* 30 */       return new NBTTagShort();
/*    */     
/*    */     case 3: 
/* 33 */       return new NBTTagInt();
/*    */     
/*    */     case 4: 
/* 36 */       return new NBTTagLong();
/*    */     
/*    */     case 5: 
/* 39 */       return new NBTTagFloat();
/*    */     
/*    */     case 6: 
/* 42 */       return new NBTTagDouble();
/*    */     
/*    */     case 7: 
/* 45 */       return new NBTTagByteArray();
/*    */     
/*    */     case 8: 
/* 48 */       return new NBTTagString();
/*    */     
/*    */     case 9: 
/* 51 */       return new NBTTagList();
/*    */     
/*    */     case 10: 
/* 54 */       return new NBTTagCompound();
/*    */     
/*    */     case 11: 
/* 57 */       return new NBTTagIntArray();
/*    */     }
/*    */     
/* 60 */     return null;
/*    */   }
/*    */   
/*    */   public abstract NBTBase clone();
/*    */   
/*    */   public boolean isEmpty()
/*    */   {
/* 67 */     return false;
/*    */   }
/*    */   
/*    */   public boolean equals(Object object) {
/* 71 */     if (!(object instanceof NBTBase)) {
/* 72 */       return false;
/*    */     }
/* 74 */     NBTBase nbtbase = (NBTBase)object;
/*    */     
/* 76 */     return getTypeId() == nbtbase.getTypeId();
/*    */   }
/*    */   
/*    */   public int hashCode()
/*    */   {
/* 81 */     return getTypeId();
/*    */   }
/*    */   
/*    */   protected String a_() {
/* 85 */     return toString();
/*    */   }
/*    */   
/*    */   public static abstract class NBTNumber
/*    */     extends NBTBase
/*    */   {
/*    */     public abstract long c();
/*    */     
/*    */     public abstract int d();
/*    */     
/*    */     public abstract short e();
/*    */     
/*    */     public abstract byte f();
/*    */     
/*    */     public abstract double g();
/*    */     
/*    */     public abstract float h();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\NBTBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */