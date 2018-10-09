/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity;
/*    */ import org.bukkit.entity.Horse;
/*    */ import org.bukkit.entity.HumanEntity;
/*    */ 
/*    */ public class InventoryHorseChest extends InventorySubcontainer
/*    */ {
/*    */   public InventoryHorseChest(String s, int i)
/*    */   {
/* 12 */     super(s, false, i);
/*    */   }
/*    */   
/*    */ 
/* 16 */   public List<HumanEntity> transaction = new java.util.ArrayList();
/*    */   private EntityHorse horse;
/* 18 */   private int maxStack = 64;
/*    */   
/*    */   public InventoryHorseChest(String s, int i, EntityHorse horse) {
/* 21 */     super(s, false, i, (org.bukkit.craftbukkit.v1_8_R3.entity.CraftHorse)horse.getBukkitEntity());
/* 22 */     this.horse = horse;
/*    */   }
/*    */   
/*    */   public ItemStack[] getContents()
/*    */   {
/* 27 */     return this.items;
/*    */   }
/*    */   
/*    */   public void onOpen(CraftHumanEntity who)
/*    */   {
/* 32 */     this.transaction.add(who);
/*    */   }
/*    */   
/*    */   public void onClose(CraftHumanEntity who)
/*    */   {
/* 37 */     this.transaction.remove(who);
/*    */   }
/*    */   
/*    */   public List<HumanEntity> getViewers()
/*    */   {
/* 42 */     return this.transaction;
/*    */   }
/*    */   
/*    */   public org.bukkit.inventory.InventoryHolder getOwner()
/*    */   {
/* 47 */     return (Horse)this.horse.getBukkitEntity();
/*    */   }
/*    */   
/*    */   public void setMaxStackSize(int size)
/*    */   {
/* 52 */     this.maxStack = size;
/*    */   }
/*    */   
/*    */   public int getMaxStackSize()
/*    */   {
/* 57 */     return this.maxStack;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\InventoryHorseChest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */