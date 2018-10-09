/*    */ package org.bukkit.craftbukkit.v1_8_R3.block;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.IChatBaseComponent;
/*    */ import net.minecraft.server.v1_8_R3.TileEntitySign;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.util.CraftChatMessage;
/*    */ 
/*    */ public class CraftSign extends CraftBlockState implements org.bukkit.block.Sign
/*    */ {
/*    */   private final TileEntitySign sign;
/*    */   private final String[] lines;
/*    */   
/*    */   public CraftSign(Block block)
/*    */   {
/* 17 */     super(block);
/*    */     
/* 19 */     CraftWorld world = (CraftWorld)block.getWorld();
/* 20 */     this.sign = ((TileEntitySign)world.getTileEntityAt(getX(), getY(), getZ()));
/*    */     
/* 22 */     if (this.sign == null) {
/* 23 */       this.lines = new String[] { "", "", "", "" };
/* 24 */       return;
/*    */     }
/*    */     
/* 27 */     this.lines = new String[this.sign.lines.length];
/* 28 */     System.arraycopy(revertComponents(this.sign.lines), 0, this.lines, 0, this.lines.length);
/*    */   }
/*    */   
/*    */   public CraftSign(Material material, TileEntitySign te) {
/* 32 */     super(material);
/* 33 */     this.sign = te;
/* 34 */     this.lines = new String[this.sign.lines.length];
/* 35 */     System.arraycopy(revertComponents(this.sign.lines), 0, this.lines, 0, this.lines.length);
/*    */   }
/*    */   
/*    */   public String[] getLines() {
/* 39 */     return this.lines;
/*    */   }
/*    */   
/*    */   public String getLine(int index) throws IndexOutOfBoundsException {
/* 43 */     return this.lines[index];
/*    */   }
/*    */   
/*    */   public void setLine(int index, String line) throws IndexOutOfBoundsException {
/* 47 */     this.lines[index] = line;
/*    */   }
/*    */   
/*    */   public boolean update(boolean force, boolean applyPhysics)
/*    */   {
/* 52 */     boolean result = super.update(force, applyPhysics);
/*    */     
/* 54 */     if (result) {
/* 55 */       IChatBaseComponent[] newLines = sanitizeLines(this.lines);
/* 56 */       System.arraycopy(newLines, 0, this.sign.lines, 0, 4);
/* 57 */       this.sign.update();
/*    */     }
/*    */     
/* 60 */     return result;
/*    */   }
/*    */   
/*    */   public static IChatBaseComponent[] sanitizeLines(String[] lines) {
/* 64 */     IChatBaseComponent[] components = new IChatBaseComponent[4];
/*    */     
/* 66 */     for (int i = 0; i < 4; i++) {
/* 67 */       if ((i < lines.length) && (lines[i] != null)) {
/* 68 */         components[i] = CraftChatMessage.fromString(lines[i])[0];
/*    */       } else {
/* 70 */         components[i] = new net.minecraft.server.v1_8_R3.ChatComponentText("");
/*    */       }
/*    */     }
/*    */     
/* 74 */     return components;
/*    */   }
/*    */   
/*    */   public static String[] revertComponents(IChatBaseComponent[] components) {
/* 78 */     String[] lines = new String[components.length];
/* 79 */     for (int i = 0; i < lines.length; i++) {
/* 80 */       lines[i] = revertComponent(components[i]);
/*    */     }
/* 82 */     return lines;
/*    */   }
/*    */   
/*    */   private static String revertComponent(IChatBaseComponent component) {
/* 86 */     return CraftChatMessage.fromComponent(component);
/*    */   }
/*    */   
/*    */   public TileEntitySign getTileEntity()
/*    */   {
/* 91 */     return this.sign;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\block\CraftSign.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */