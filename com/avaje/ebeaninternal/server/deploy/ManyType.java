/*    */ package com.avaje.ebeaninternal.server.deploy;
/*    */ 
/*    */ import com.avaje.ebeaninternal.api.SpiQuery.Type;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ManyType
/*    */ {
/* 29 */   public static final ManyType JAVA_LIST = new ManyType(Underlying.LIST);
/* 30 */   public static final ManyType JAVA_SET = new ManyType(Underlying.SET);
/* 31 */   public static final ManyType JAVA_MAP = new ManyType(Underlying.MAP);
/*    */   private final SpiQuery.Type queryType;
/*    */   
/* 34 */   public static enum Underlying { LIST, 
/* 35 */     SET, 
/* 36 */     MAP;
/*    */     
/*    */ 
/*    */     private Underlying() {}
/*    */   }
/*    */   
/*    */   private final Underlying underlying;
/*    */   private final CollectionTypeConverter typeConverter;
/*    */   private ManyType(Underlying underlying)
/*    */   {
/* 46 */     this(underlying, null);
/*    */   }
/*    */   
/*    */   public ManyType(Underlying underlying, CollectionTypeConverter typeConverter) {
/* 50 */     this.underlying = underlying;
/* 51 */     this.typeConverter = typeConverter;
/* 52 */     switch (underlying) {
/*    */     case LIST: 
/* 54 */       this.queryType = SpiQuery.Type.LIST;
/* 55 */       break;
/*    */     case SET: 
/* 57 */       this.queryType = SpiQuery.Type.SET;
/* 58 */       break;
/*    */     
/*    */     default: 
/* 61 */       this.queryType = SpiQuery.Type.MAP;
/*    */     }
/*    */     
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public SpiQuery.Type getQueryType()
/*    */   {
/* 70 */     return this.queryType;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public Underlying getUnderlying()
/*    */   {
/* 77 */     return this.underlying;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public CollectionTypeConverter getTypeConverter()
/*    */   {
/* 84 */     return this.typeConverter;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\ManyType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */