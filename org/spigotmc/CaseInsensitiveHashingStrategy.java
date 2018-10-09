/*    */ package org.spigotmc;
/*    */ 
/*    */ import gnu.trove.strategy.HashingStrategy;
/*    */ 
/*    */ class CaseInsensitiveHashingStrategy implements HashingStrategy
/*    */ {
/*  7 */   static final CaseInsensitiveHashingStrategy INSTANCE = new CaseInsensitiveHashingStrategy();
/*    */   
/*    */   public int computeHashCode(Object object)
/*    */   {
/* 11 */     return ((String)object).toLowerCase().hashCode();
/*    */   }
/*    */   
/*    */   public boolean equals(Object o1, Object o2)
/*    */   {
/* 16 */     return (o1.equals(o2)) || (((o1 instanceof String)) && ((o2 instanceof String)) && (((String)o1).toLowerCase().equals(((String)o2).toLowerCase())));
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\spigotmc\CaseInsensitiveHashingStrategy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */