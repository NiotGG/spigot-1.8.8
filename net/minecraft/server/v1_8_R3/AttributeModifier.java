/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import io.netty.util.internal.ThreadLocalRandom;
/*    */ import java.util.UUID;
/*    */ import org.apache.commons.lang3.Validate;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AttributeModifier
/*    */ {
/*    */   private final double a;
/*    */   private final int b;
/*    */   private final String c;
/*    */   private final UUID d;
/* 19 */   private boolean e = true;
/*    */   
/*    */   public AttributeModifier(String paramString, double paramDouble, int paramInt) {
/* 22 */     this(MathHelper.a(ThreadLocalRandom.current()), paramString, paramDouble, paramInt);
/*    */   }
/*    */   
/*    */   public AttributeModifier(UUID paramUUID, String paramString, double paramDouble, int paramInt) {
/* 26 */     this.d = paramUUID;
/* 27 */     this.c = paramString;
/* 28 */     this.a = paramDouble;
/* 29 */     this.b = paramInt;
/*    */     
/* 31 */     Validate.notEmpty(paramString, "Modifier name cannot be empty", new Object[0]);
/* 32 */     Validate.inclusiveBetween(0L, 2L, paramInt, "Invalid operation");
/*    */   }
/*    */   
/*    */   public UUID a() {
/* 36 */     return this.d;
/*    */   }
/*    */   
/*    */   public String b() {
/* 40 */     return this.c;
/*    */   }
/*    */   
/*    */   public int c() {
/* 44 */     return this.b;
/*    */   }
/*    */   
/*    */   public double d() {
/* 48 */     return this.a;
/*    */   }
/*    */   
/*    */   public boolean e() {
/* 52 */     return this.e;
/*    */   }
/*    */   
/*    */   public AttributeModifier a(boolean paramBoolean) {
/* 56 */     this.e = paramBoolean;
/* 57 */     return this;
/*    */   }
/*    */   
/*    */   public boolean equals(Object paramObject)
/*    */   {
/* 62 */     if (this == paramObject) {
/* 63 */       return true;
/*    */     }
/* 65 */     if ((paramObject == null) || (getClass() != paramObject.getClass())) {
/* 66 */       return false;
/*    */     }
/*    */     
/* 69 */     AttributeModifier localAttributeModifier = (AttributeModifier)paramObject;
/*    */     
/* 71 */     if (this.d != null ? !this.d.equals(localAttributeModifier.d) : localAttributeModifier.d != null) {
/* 72 */       return false;
/*    */     }
/*    */     
/* 75 */     return true;
/*    */   }
/*    */   
/*    */   public int hashCode()
/*    */   {
/* 80 */     return this.d != null ? this.d.hashCode() : 0;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 85 */     return "AttributeModifier{amount=" + this.a + ", operation=" + this.b + ", name='" + this.c + '\'' + ", id=" + this.d + ", serialize=" + this.e + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\AttributeModifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */