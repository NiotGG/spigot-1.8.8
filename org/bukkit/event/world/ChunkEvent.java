/*    */ package org.bukkit.event.world;
/*    */ 
/*    */ import org.bukkit.Chunk;
/*    */ 
/*    */ public abstract class ChunkEvent
/*    */   extends WorldEvent
/*    */ {
/*    */   protected Chunk chunk;
/*    */   
/*    */   protected ChunkEvent(Chunk chunk)
/*    */   {
/* 12 */     super(chunk.getWorld());
/* 13 */     this.chunk = chunk;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Chunk getChunk()
/*    */   {
/* 22 */     return this.chunk;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\world\ChunkEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */