/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ public class PersistentStructure
/*    */   extends PersistentBase
/*    */ {
/*    */   private NBTTagCompound b;
/*    */   
/*    */   public PersistentStructure(String paramString)
/*    */   {
/* 11 */     super(paramString);
/* 12 */     this.b = new NBTTagCompound();
/*    */   }
/*    */   
/*    */   public void a(NBTTagCompound paramNBTTagCompound)
/*    */   {
/* 17 */     this.b = paramNBTTagCompound.getCompound("Features");
/*    */   }
/*    */   
/*    */   public void b(NBTTagCompound paramNBTTagCompound)
/*    */   {
/* 22 */     paramNBTTagCompound.set("Features", this.b);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void a(NBTTagCompound paramNBTTagCompound, int paramInt1, int paramInt2)
/*    */   {
/* 30 */     this.b.set(b(paramInt1, paramInt2), paramNBTTagCompound);
/*    */   }
/*    */   
/*    */   public static String b(int paramInt1, int paramInt2) {
/* 34 */     return "[" + paramInt1 + "," + paramInt2 + "]";
/*    */   }
/*    */   
/*    */   public NBTTagCompound a() {
/* 38 */     return this.b;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PersistentStructure.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */