/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChestLock
/*    */ {
/* 10 */   public static final ChestLock a = new ChestLock("");
/*    */   
/*    */   private final String b;
/*    */   
/*    */   public ChestLock(String paramString)
/*    */   {
/* 16 */     this.b = paramString;
/*    */   }
/*    */   
/*    */   public boolean a() {
/* 20 */     return (this.b == null) || (this.b.isEmpty());
/*    */   }
/*    */   
/*    */   public String b() {
/* 24 */     return this.b;
/*    */   }
/*    */   
/*    */   public void a(NBTTagCompound paramNBTTagCompound) {
/* 28 */     paramNBTTagCompound.setString("Lock", this.b);
/*    */   }
/*    */   
/*    */   public static ChestLock b(NBTTagCompound paramNBTTagCompound) {
/* 32 */     if (paramNBTTagCompound.hasKeyOfType("Lock", 8)) {
/* 33 */       String str = paramNBTTagCompound.getString("Lock");
/* 34 */       return new ChestLock(str);
/*    */     }
/* 36 */     return a;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ChestLock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */