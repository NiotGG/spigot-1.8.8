/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public abstract class PersistentBase
/*    */ {
/*    */   public final String id;
/*    */   private boolean b;
/*    */   
/*    */   public PersistentBase(String paramString)
/*    */   {
/* 10 */     this.id = paramString;
/*    */   }
/*    */   
/*    */   public abstract void a(NBTTagCompound paramNBTTagCompound);
/*    */   
/*    */   public abstract void b(NBTTagCompound paramNBTTagCompound);
/*    */   
/*    */   public void c() {
/* 18 */     a(true);
/*    */   }
/*    */   
/*    */   public void a(boolean paramBoolean) {
/* 22 */     this.b = paramBoolean;
/*    */   }
/*    */   
/*    */   public boolean d() {
/* 26 */     return this.b;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PersistentBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */