/*    */ package org.bukkit.metadata;
/*    */ 
/*    */ import org.bukkit.plugin.Plugin;
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
/*    */ public class FixedMetadataValue
/*    */   extends LazyMetadataValue
/*    */ {
/*    */   private final Object internalValue;
/*    */   
/*    */   public FixedMetadataValue(Plugin owningPlugin, Object value)
/*    */   {
/* 30 */     super(owningPlugin);
/* 31 */     this.internalValue = value;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void invalidate() {}
/*    */   
/*    */ 
/*    */   public Object value()
/*    */   {
/* 41 */     return this.internalValue;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\metadata\FixedMetadataValue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */