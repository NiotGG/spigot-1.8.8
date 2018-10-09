/*    */ package org.bukkit.craftbukkit.v1_8_R3.block;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.BlockPosition;
/*    */ import net.minecraft.server.v1_8_R3.TileEntityNote;
/*    */ import net.minecraft.server.v1_8_R3.WorldServer;
/*    */ import org.bukkit.Instrument;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.Note;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*    */ 
/*    */ public class CraftNoteBlock extends CraftBlockState implements org.bukkit.block.NoteBlock
/*    */ {
/*    */   private final CraftWorld world;
/*    */   private final TileEntityNote note;
/*    */   
/*    */   public CraftNoteBlock(Block block)
/*    */   {
/* 19 */     super(block);
/*    */     
/* 21 */     this.world = ((CraftWorld)block.getWorld());
/* 22 */     this.note = ((TileEntityNote)this.world.getTileEntityAt(getX(), getY(), getZ()));
/*    */   }
/*    */   
/*    */   public CraftNoteBlock(Material material, TileEntityNote te) {
/* 26 */     super(material);
/* 27 */     this.world = null;
/* 28 */     this.note = te;
/*    */   }
/*    */   
/*    */   public Note getNote() {
/* 32 */     return new Note(this.note.note);
/*    */   }
/*    */   
/*    */   public byte getRawNote() {
/* 36 */     return this.note.note;
/*    */   }
/*    */   
/*    */   public void setNote(Note n) {
/* 40 */     this.note.note = n.getId();
/*    */   }
/*    */   
/*    */   public void setRawNote(byte n) {
/* 44 */     this.note.note = n;
/*    */   }
/*    */   
/*    */   public boolean play() {
/* 48 */     Block block = getBlock();
/*    */     
/* 50 */     if (block.getType() == Material.NOTE_BLOCK) {
/* 51 */       this.note.play(this.world.getHandle(), new BlockPosition(getX(), getY(), getZ()));
/* 52 */       return true;
/*    */     }
/* 54 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean play(byte instrument, byte note)
/*    */   {
/* 60 */     Block block = getBlock();
/*    */     
/* 62 */     if (block.getType() == Material.NOTE_BLOCK) {
/* 63 */       this.world.getHandle().playBlockAction(new BlockPosition(getX(), getY(), getZ()), org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers.getBlock(block), instrument, note);
/* 64 */       return true;
/*    */     }
/* 66 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean play(Instrument instrument, Note note)
/*    */   {
/* 72 */     Block block = getBlock();
/*    */     
/* 74 */     if (block.getType() == Material.NOTE_BLOCK) {
/* 75 */       this.world.getHandle().playBlockAction(new BlockPosition(getX(), getY(), getZ()), org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers.getBlock(block), instrument.getType(), note.getId());
/* 76 */       return true;
/*    */     }
/* 78 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */   public TileEntityNote getTileEntity()
/*    */   {
/* 84 */     return this.note;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\block\CraftNoteBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */