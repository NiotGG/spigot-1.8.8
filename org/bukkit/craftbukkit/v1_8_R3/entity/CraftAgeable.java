/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntityAgeable;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ 
/*    */ public class CraftAgeable extends CraftCreature implements org.bukkit.entity.Ageable
/*    */ {
/*    */   public CraftAgeable(CraftServer server, EntityAgeable entity)
/*    */   {
/* 10 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public int getAge() {
/* 14 */     return getHandle().getAge();
/*    */   }
/*    */   
/*    */   public void setAge(int age) {
/* 18 */     getHandle().setAgeRaw(age);
/*    */   }
/*    */   
/*    */   public void setAgeLock(boolean lock) {
/* 22 */     getHandle().ageLocked = lock;
/*    */   }
/*    */   
/*    */   public boolean getAgeLock() {
/* 26 */     return getHandle().ageLocked;
/*    */   }
/*    */   
/*    */   public void setBaby() {
/* 30 */     if (isAdult()) {
/* 31 */       setAge(41536);
/*    */     }
/*    */   }
/*    */   
/*    */   public void setAdult() {
/* 36 */     if (!isAdult()) {
/* 37 */       setAge(0);
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean isAdult() {
/* 42 */     return getAge() >= 0;
/*    */   }
/*    */   
/*    */   public boolean canBreed()
/*    */   {
/* 47 */     return getAge() == 0;
/*    */   }
/*    */   
/*    */   public void setBreed(boolean breed) {
/* 51 */     if (breed) {
/* 52 */       setAge(0);
/* 53 */     } else if (isAdult()) {
/* 54 */       setAge(6000);
/*    */     }
/*    */   }
/*    */   
/*    */   public EntityAgeable getHandle()
/*    */   {
/* 60 */     return (EntityAgeable)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 65 */     return "CraftAgeable";
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftAgeable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */