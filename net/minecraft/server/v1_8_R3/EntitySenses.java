/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntitySenses
/*    */ {
/*    */   EntityInsentient a;
/* 11 */   List<Entity> b = Lists.newArrayList();
/* 12 */   List<Entity> c = Lists.newArrayList();
/*    */   
/*    */   public EntitySenses(EntityInsentient paramEntityInsentient) {
/* 15 */     this.a = paramEntityInsentient;
/*    */   }
/*    */   
/*    */   public void a() {
/* 19 */     this.b.clear();
/* 20 */     this.c.clear();
/*    */   }
/*    */   
/*    */   public boolean a(Entity paramEntity) {
/* 24 */     if (this.b.contains(paramEntity)) {
/* 25 */       return true;
/*    */     }
/* 27 */     if (this.c.contains(paramEntity)) {
/* 28 */       return false;
/*    */     }
/*    */     
/* 31 */     this.a.world.methodProfiler.a("canSee");
/* 32 */     boolean bool = this.a.hasLineOfSight(paramEntity);
/* 33 */     this.a.world.methodProfiler.b();
/* 34 */     if (bool) {
/* 35 */       this.b.add(paramEntity);
/*    */     } else {
/* 37 */       this.c.add(paramEntity);
/*    */     }
/* 39 */     return bool;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntitySenses.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */