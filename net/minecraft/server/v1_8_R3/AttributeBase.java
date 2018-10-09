/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public abstract class AttributeBase implements IAttribute
/*    */ {
/*    */   private final IAttribute a;
/*    */   private final String b;
/*    */   private final double c;
/*    */   private boolean d;
/*    */   
/*    */   protected AttributeBase(IAttribute paramIAttribute, String paramString, double paramDouble)
/*    */   {
/* 12 */     this.a = paramIAttribute;
/* 13 */     this.b = paramString;
/* 14 */     this.c = paramDouble;
/*    */     
/* 16 */     if (paramString == null) {
/* 17 */       throw new IllegalArgumentException("Name cannot be null!");
/*    */     }
/*    */   }
/*    */   
/*    */   public String getName()
/*    */   {
/* 23 */     return this.b;
/*    */   }
/*    */   
/*    */   public double b()
/*    */   {
/* 28 */     return this.c;
/*    */   }
/*    */   
/*    */   public boolean c()
/*    */   {
/* 33 */     return this.d;
/*    */   }
/*    */   
/*    */   public AttributeBase a(boolean paramBoolean) {
/* 37 */     this.d = paramBoolean;
/* 38 */     return this;
/*    */   }
/*    */   
/*    */ 
/*    */   public IAttribute d()
/*    */   {
/* 44 */     return this.a;
/*    */   }
/*    */   
/*    */   public int hashCode()
/*    */   {
/* 49 */     return this.b.hashCode();
/*    */   }
/*    */   
/*    */   public boolean equals(Object paramObject)
/*    */   {
/* 54 */     return ((paramObject instanceof IAttribute)) && (this.b.equals(((IAttribute)paramObject).getName()));
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\AttributeBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */