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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class StreamStartEvent
/*    */   extends Event
/*    */ {
/*    */   public StreamStartEvent(Mark startMark, Mark endMark)
/*    */   {
/* 34 */     super(startMark, endMark);
/*    */   }
/*    */   
/*    */   public boolean is(Event.ID id)
/*    */   {
/* 39 */     return Event.ID.StreamStart == id;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\yaml\snakeyaml\events\StreamStartEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */