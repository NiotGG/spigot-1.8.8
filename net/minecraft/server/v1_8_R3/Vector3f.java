/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ public class Vector3f
/*    */ {
/*    */   protected final float x;
/*    */   
/*    */   protected final float y;
/*    */   protected final float z;
/*    */   
/*    */   public Vector3f(float paramFloat1, float paramFloat2, float paramFloat3)
/*    */   {
/* 13 */     this.x = paramFloat1;
/* 14 */     this.y = paramFloat2;
/* 15 */     this.z = paramFloat3;
/*    */   }
/*    */   
/*    */   public Vector3f(NBTTagList paramNBTTagList) {
/* 19 */     this.x = paramNBTTagList.e(0);
/* 20 */     this.y = paramNBTTagList.e(1);
/* 21 */     this.z = paramNBTTagList.e(2);
/*    */   }
/*    */   
/*    */   public NBTTagList a() {
/* 25 */     NBTTagList localNBTTagList = new NBTTagList();
/* 26 */     localNBTTagList.add(new NBTTagFloat(this.x));
/* 27 */     localNBTTagList.add(new NBTTagFloat(this.y));
/* 28 */     localNBTTagList.add(new NBTTagFloat(this.z));
/* 29 */     return localNBTTagList;
/*    */   }
/*    */   
/*    */   public boolean equals(Object paramObject)
/*    */   {
/* 34 */     if (!(paramObject instanceof Vector3f)) {
/* 35 */       return false;
/*    */     }
/* 37 */     Vector3f localVector3f = (Vector3f)paramObject;
/* 38 */     return (this.x == localVector3f.x) && (this.y == localVector3f.y) && (this.z == localVector3f.z);
/*    */   }
/*    */   
/*    */   public float getX() {
/* 42 */     return this.x;
/*    */   }
/*    */   
/*    */   public float getY() {
/* 46 */     return this.y;
/*    */   }
/*    */   
/*    */   public float getZ() {
/* 50 */     return this.z;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\Vector3f.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */