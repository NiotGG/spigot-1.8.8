/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ public class PlayerAbilities
/*    */ {
/*    */   public boolean isInvulnerable;
/*    */   
/*    */   public boolean isFlying;
/*    */   public boolean canFly;
/*    */   public boolean canInstantlyBuild;
/* 11 */   public boolean mayBuild = true;
/* 12 */   public float flySpeed = 0.05F;
/* 13 */   public float walkSpeed = 0.1F;
/*    */   
/*    */   public void a(NBTTagCompound paramNBTTagCompound) {
/* 16 */     NBTTagCompound localNBTTagCompound = new NBTTagCompound();
/*    */     
/* 18 */     localNBTTagCompound.setBoolean("invulnerable", this.isInvulnerable);
/* 19 */     localNBTTagCompound.setBoolean("flying", this.isFlying);
/* 20 */     localNBTTagCompound.setBoolean("mayfly", this.canFly);
/* 21 */     localNBTTagCompound.setBoolean("instabuild", this.canInstantlyBuild);
/* 22 */     localNBTTagCompound.setBoolean("mayBuild", this.mayBuild);
/* 23 */     localNBTTagCompound.setFloat("flySpeed", this.flySpeed);
/* 24 */     localNBTTagCompound.setFloat("walkSpeed", this.walkSpeed);
/* 25 */     paramNBTTagCompound.set("abilities", localNBTTagCompound);
/*    */   }
/*    */   
/*    */   public void b(NBTTagCompound paramNBTTagCompound) {
/* 29 */     if (paramNBTTagCompound.hasKeyOfType("abilities", 10)) {
/* 30 */       NBTTagCompound localNBTTagCompound = paramNBTTagCompound.getCompound("abilities");
/*    */       
/* 32 */       this.isInvulnerable = localNBTTagCompound.getBoolean("invulnerable");
/* 33 */       this.isFlying = localNBTTagCompound.getBoolean("flying");
/* 34 */       this.canFly = localNBTTagCompound.getBoolean("mayfly");
/* 35 */       this.canInstantlyBuild = localNBTTagCompound.getBoolean("instabuild");
/*    */       
/* 37 */       if (localNBTTagCompound.hasKeyOfType("flySpeed", 99)) {
/* 38 */         this.flySpeed = localNBTTagCompound.getFloat("flySpeed");
/* 39 */         this.walkSpeed = localNBTTagCompound.getFloat("walkSpeed");
/*    */       }
/* 41 */       if (localNBTTagCompound.hasKeyOfType("mayBuild", 1)) {
/* 42 */         this.mayBuild = localNBTTagCompound.getBoolean("mayBuild");
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public float a() {
/* 48 */     return this.flySpeed;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public float b()
/*    */   {
/* 56 */     return this.walkSpeed;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PlayerAbilities.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */