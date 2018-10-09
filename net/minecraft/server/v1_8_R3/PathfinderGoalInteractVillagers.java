/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PathfinderGoalInteractVillagers
/*    */   extends PathfinderGoalInteract
/*    */ {
/*    */   private int e;
/*    */   
/*    */ 
/*    */   private EntityVillager f;
/*    */   
/*    */ 
/*    */   public PathfinderGoalInteractVillagers(EntityVillager paramEntityVillager)
/*    */   {
/* 16 */     super(paramEntityVillager, EntityVillager.class, 3.0F, 0.02F);
/* 17 */     this.f = paramEntityVillager;
/*    */   }
/*    */   
/*    */   public void c()
/*    */   {
/* 22 */     super.c();
/*    */     
/* 24 */     if ((this.f.cs()) && ((this.b instanceof EntityVillager)) && (((EntityVillager)this.b).ct())) {
/* 25 */       this.e = 10;
/*    */     } else {
/* 27 */       this.e = 0;
/*    */     }
/*    */   }
/*    */   
/*    */   public void e()
/*    */   {
/* 33 */     super.e();
/*    */     
/* 35 */     if (this.e > 0) {
/* 36 */       this.e -= 1;
/*    */       
/* 38 */       if (this.e == 0)
/*    */       {
/* 40 */         InventorySubcontainer localInventorySubcontainer = this.f.cq();
/* 41 */         for (int i = 0; i < localInventorySubcontainer.getSize(); i++) {
/* 42 */           ItemStack localItemStack1 = localInventorySubcontainer.getItem(i);
/* 43 */           ItemStack localItemStack2 = null;
/*    */           
/* 45 */           if (localItemStack1 != null) {
/* 46 */             Item localItem = localItemStack1.getItem();
/*    */             int j;
/* 48 */             if (((localItem == Items.BREAD) || (localItem == Items.POTATO) || (localItem == Items.CARROT)) && (localItemStack1.count > 3)) {
/* 49 */               j = localItemStack1.count / 2;
/* 50 */               localItemStack1.count -= j;
/* 51 */               localItemStack2 = new ItemStack(localItem, j, localItemStack1.getData());
/* 52 */             } else if ((localItem == Items.WHEAT) && (localItemStack1.count > 5)) {
/* 53 */               j = localItemStack1.count / 2 / 3 * 3;
/* 54 */               int k = j / 3;
/* 55 */               localItemStack1.count -= j;
/* 56 */               localItemStack2 = new ItemStack(Items.BREAD, k, 0);
/*    */             }
/* 58 */             if (localItemStack1.count <= 0) {
/* 59 */               localInventorySubcontainer.setItem(i, null);
/*    */             }
/*    */           }
/* 62 */           if (localItemStack2 != null) {
/* 63 */             double d = this.f.locY - 0.30000001192092896D + this.f.getHeadHeight();
/* 64 */             EntityItem localEntityItem = new EntityItem(this.f.world, this.f.locX, d, this.f.locZ, localItemStack2);
/* 65 */             float f1 = 0.3F;
/* 66 */             float f2 = this.f.aK;
/* 67 */             float f3 = this.f.pitch;
/* 68 */             localEntityItem.motX = (-MathHelper.sin(f2 / 180.0F * 3.1415927F) * MathHelper.cos(f3 / 180.0F * 3.1415927F) * f1);
/* 69 */             localEntityItem.motZ = (MathHelper.cos(f2 / 180.0F * 3.1415927F) * MathHelper.cos(f3 / 180.0F * 3.1415927F) * f1);
/* 70 */             localEntityItem.motY = (-MathHelper.sin(f3 / 180.0F * 3.1415927F) * f1 + 0.1F);
/* 71 */             localEntityItem.p();
/*    */             
/* 73 */             this.f.world.addEntity(localEntityItem);
/*    */             
/* 75 */             break;
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderGoalInteractVillagers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */