/*    */ package org.bukkit.event.block;
/*    */ 
/*    */ import org.bukkit.Instrument;
/*    */ import org.bukkit.Note;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NotePlayEvent
/*    */   extends BlockEvent
/*    */   implements Cancellable
/*    */ {
/* 15 */   private static HandlerList handlers = new HandlerList();
/*    */   private Instrument instrument;
/*    */   private Note note;
/* 18 */   private boolean cancelled = false;
/*    */   
/*    */   public NotePlayEvent(Block block, Instrument instrument, Note note) {
/* 21 */     super(block);
/* 22 */     this.instrument = instrument;
/* 23 */     this.note = note;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 27 */     return this.cancelled;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 31 */     this.cancelled = cancel;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Instrument getInstrument()
/*    */   {
/* 40 */     return this.instrument;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Note getNote()
/*    */   {
/* 49 */     return this.note;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setInstrument(Instrument instrument)
/*    */   {
/* 58 */     if (instrument != null) {
/* 59 */       this.instrument = instrument;
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setNote(Note note)
/*    */   {
/* 70 */     if (note != null) {
/* 71 */       this.note = note;
/*    */     }
/*    */   }
/*    */   
/*    */   public HandlerList getHandlers()
/*    */   {
/* 77 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 81 */     return handlers;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\block\NotePlayEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */