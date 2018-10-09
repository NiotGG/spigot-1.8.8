/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class MapIcon {
/*    */   private byte type;
/*    */   private byte x;
/*    */   private byte y;
/*    */   private byte rotation;
/*    */   
/*    */   public MapIcon(byte paramByte1, byte paramByte2, byte paramByte3, byte paramByte4) {
/* 10 */     this.type = paramByte1;
/* 11 */     this.x = paramByte2;
/* 12 */     this.y = paramByte3;
/* 13 */     this.rotation = paramByte4;
/*    */   }
/*    */   
/*    */   public MapIcon(MapIcon paramMapIcon) {
/* 17 */     this.type = paramMapIcon.type;
/* 18 */     this.x = paramMapIcon.x;
/* 19 */     this.y = paramMapIcon.y;
/* 20 */     this.rotation = paramMapIcon.rotation;
/*    */   }
/*    */   
/*    */   public byte getType() {
/* 24 */     return this.type;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public byte getX()
/*    */   {
/* 32 */     return this.x;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public byte getY()
/*    */   {
/* 40 */     return this.y;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public byte getRotation()
/*    */   {
/* 48 */     return this.rotation;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean equals(Object paramObject)
/*    */   {
/* 57 */     if (this == paramObject) {
/* 58 */       return true;
/*    */     }
/* 60 */     if (!(paramObject instanceof MapIcon)) {
/* 61 */       return false;
/*    */     }
/*    */     
/* 64 */     MapIcon localMapIcon = (MapIcon)paramObject;
/*    */     
/* 66 */     if (this.type != localMapIcon.type) {
/* 67 */       return false;
/*    */     }
/* 69 */     if (this.rotation != localMapIcon.rotation) {
/* 70 */       return false;
/*    */     }
/* 72 */     if (this.x != localMapIcon.x) {
/* 73 */       return false;
/*    */     }
/* 75 */     if (this.y != localMapIcon.y) {
/* 76 */       return false;
/*    */     }
/*    */     
/* 79 */     return true;
/*    */   }
/*    */   
/*    */   public int hashCode()
/*    */   {
/* 84 */     int i = this.type;
/* 85 */     i = 31 * i + this.x;
/* 86 */     i = 31 * i + this.y;
/* 87 */     i = 31 * i + this.rotation;
/* 88 */     return i;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\MapIcon.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */