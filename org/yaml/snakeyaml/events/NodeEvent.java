/*    */ package org.yaml.snakeyaml.events;
/*    */ 
/*    */ import org.yaml.snakeyaml.error.Mark;
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
/*    */ public abstract class NodeEvent
/*    */   extends Event
/*    */ {
/*    */   private final String anchor;
/*    */   
/*    */   public NodeEvent(String anchor, Mark startMark, Mark endMark)
/*    */   {
/* 28 */     super(startMark, endMark);
/* 29 */     this.anchor = anchor;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getAnchor()
/*    */   {
/* 42 */     return this.anchor;
/*    */   }
/*    */   
/*    */   protected String getArguments()
/*    */   {
/* 47 */     return "anchor=" + this.anchor;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\yaml\snakeyaml\events\NodeEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */