/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ 
/*    */ public class PathfinderGoalHurtByTarget extends PathfinderGoalTarget
/*    */ {
/*    */   private boolean a;
/*    */   private int b;
/*    */   private final Class[] c;
/*    */   
/*    */   public PathfinderGoalHurtByTarget(EntityCreature entitycreature, boolean flag, Class... aclass)
/*    */   {
/* 13 */     super(entitycreature, false);
/* 14 */     this.a = flag;
/* 15 */     this.c = aclass;
/* 16 */     a(1);
/*    */   }
/*    */   
/*    */   public boolean a() {
/* 20 */     int i = this.e.be();
/*    */     
/* 22 */     return (i != this.b) && (a(this.e.getLastDamager(), false));
/*    */   }
/*    */   
/*    */   public void c() {
/* 26 */     this.e.setGoalTarget(this.e.getLastDamager(), org.bukkit.event.entity.EntityTargetEvent.TargetReason.TARGET_ATTACKED_ENTITY, true);
/* 27 */     this.b = this.e.be();
/* 28 */     if (this.a) {
/* 29 */       double d0 = f();
/* 30 */       java.util.List list = this.e.world.a(this.e.getClass(), new AxisAlignedBB(this.e.locX, this.e.locY, this.e.locZ, this.e.locX + 1.0D, this.e.locY + 1.0D, this.e.locZ + 1.0D).grow(d0, 10.0D, d0));
/* 31 */       Iterator iterator = list.iterator();
/*    */       
/* 33 */       while (iterator.hasNext()) {
/* 34 */         EntityCreature entitycreature = (EntityCreature)iterator.next();
/*    */         
/* 36 */         if ((this.e != entitycreature) && (entitycreature.getGoalTarget() == null) && (!entitycreature.c(this.e.getLastDamager()))) {
/* 37 */           boolean flag = false;
/* 38 */           Class[] aclass = this.c;
/* 39 */           int i = aclass.length;
/*    */           
/* 41 */           for (int j = 0; j < i; j++) {
/* 42 */             Class oclass = aclass[j];
/*    */             
/* 44 */             if (entitycreature.getClass() == oclass) {
/* 45 */               flag = true;
/* 46 */               break;
/*    */             }
/*    */           }
/*    */           
/* 50 */           if (!flag) {
/* 51 */             a(entitycreature, this.e.getLastDamager());
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/*    */     
/* 57 */     super.c();
/*    */   }
/*    */   
/*    */   protected void a(EntityCreature entitycreature, EntityLiving entityliving) {
/* 61 */     entitycreature.setGoalTarget(entityliving, org.bukkit.event.entity.EntityTargetEvent.TargetReason.TARGET_ATTACKED_NEARBY_ENTITY, true);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderGoalHurtByTarget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */