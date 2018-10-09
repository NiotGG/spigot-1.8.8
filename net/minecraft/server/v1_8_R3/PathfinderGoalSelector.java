/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.util.UnsafeList;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.util.UnsafeList.Itr;
/*     */ 
/*     */ 
/*     */ public class PathfinderGoalSelector
/*     */ {
/*  13 */   private static final Logger a = ;
/*  14 */   private List<PathfinderGoalSelectorItem> b = new UnsafeList();
/*  15 */   private List<PathfinderGoalSelectorItem> c = new UnsafeList();
/*     */   private final MethodProfiler d;
/*     */   private int e;
/*  18 */   private int f = 3;
/*     */   
/*     */   public PathfinderGoalSelector(MethodProfiler methodprofiler) {
/*  21 */     this.d = methodprofiler;
/*     */   }
/*     */   
/*     */   public void a(int i, PathfinderGoal pathfindergoal) {
/*  25 */     this.b.add(new PathfinderGoalSelectorItem(i, pathfindergoal));
/*     */   }
/*     */   
/*     */   public void a(PathfinderGoal pathfindergoal) {
/*  29 */     Iterator iterator = this.b.iterator();
/*     */     
/*  31 */     while (iterator.hasNext()) {
/*  32 */       PathfinderGoalSelectorItem pathfindergoalselector_pathfindergoalselectoritem = (PathfinderGoalSelectorItem)iterator.next();
/*  33 */       PathfinderGoal pathfindergoal1 = pathfindergoalselector_pathfindergoalselectoritem.a;
/*     */       
/*  35 */       if (pathfindergoal1 == pathfindergoal) {
/*  36 */         if (this.c.contains(pathfindergoalselector_pathfindergoalselectoritem)) {
/*  37 */           pathfindergoal1.d();
/*  38 */           this.c.remove(pathfindergoalselector_pathfindergoalselectoritem);
/*     */         }
/*     */         
/*  41 */         iterator.remove();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void a()
/*     */   {
/*  48 */     this.d.a("goalSetup");
/*     */     
/*     */ 
/*     */ 
/*  52 */     if (this.e++ % this.f == 0) {
/*  53 */       Iterator iterator = this.b.iterator();
/*     */       
/*  55 */       while (iterator.hasNext()) {
/*  56 */         PathfinderGoalSelectorItem pathfindergoalselector_pathfindergoalselectoritem = (PathfinderGoalSelectorItem)iterator.next();
/*  57 */         boolean flag = this.c.contains(pathfindergoalselector_pathfindergoalselectoritem);
/*     */         
/*  59 */         if (flag) {
/*  60 */           if ((!b(pathfindergoalselector_pathfindergoalselectoritem)) || (!a(pathfindergoalselector_pathfindergoalselectoritem)))
/*     */           {
/*     */ 
/*     */ 
/*  64 */             pathfindergoalselector_pathfindergoalselectoritem.a.d();
/*  65 */             this.c.remove(pathfindergoalselector_pathfindergoalselectoritem);
/*     */           }
/*     */         }
/*  68 */         else if ((b(pathfindergoalselector_pathfindergoalselectoritem)) && (pathfindergoalselector_pathfindergoalselectoritem.a.a())) {
/*  69 */           pathfindergoalselector_pathfindergoalselectoritem.a.c();
/*  70 */           this.c.add(pathfindergoalselector_pathfindergoalselectoritem);
/*     */         }
/*     */       }
/*     */     } else {
/*  74 */       iterator = this.c.iterator();
/*     */       
/*  76 */       while (iterator.hasNext()) {
/*  77 */         PathfinderGoalSelectorItem pathfindergoalselector_pathfindergoalselectoritem = (PathfinderGoalSelectorItem)iterator.next();
/*  78 */         if (!a(pathfindergoalselector_pathfindergoalselectoritem)) {
/*  79 */           pathfindergoalselector_pathfindergoalselectoritem.a.d();
/*  80 */           iterator.remove();
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*  85 */     this.d.b();
/*  86 */     this.d.a("goalTick");
/*  87 */     Iterator iterator = this.c.iterator();
/*     */     
/*  89 */     while (iterator.hasNext()) {
/*  90 */       PathfinderGoalSelectorItem pathfindergoalselector_pathfindergoalselectoritem = (PathfinderGoalSelectorItem)iterator.next();
/*  91 */       pathfindergoalselector_pathfindergoalselectoritem.a.e();
/*     */     }
/*     */     
/*  94 */     this.d.b();
/*     */   }
/*     */   
/*     */   private boolean a(PathfinderGoalSelectorItem pathfindergoalselector_pathfindergoalselectoritem) {
/*  98 */     boolean flag = pathfindergoalselector_pathfindergoalselectoritem.a.b();
/*     */     
/* 100 */     return flag;
/*     */   }
/*     */   
/*     */   private boolean b(PathfinderGoalSelectorItem pathfindergoalselector_pathfindergoalselectoritem) {
/* 104 */     Iterator iterator = this.b.iterator();
/*     */     
/* 106 */     while (iterator.hasNext()) {
/* 107 */       PathfinderGoalSelectorItem pathfindergoalselector_pathfindergoalselectoritem1 = (PathfinderGoalSelectorItem)iterator.next();
/*     */       
/* 109 */       if (pathfindergoalselector_pathfindergoalselectoritem1 != pathfindergoalselector_pathfindergoalselectoritem) {
/* 110 */         if (pathfindergoalselector_pathfindergoalselectoritem.b >= pathfindergoalselector_pathfindergoalselectoritem1.b) {
/* 111 */           if ((!a(pathfindergoalselector_pathfindergoalselectoritem, pathfindergoalselector_pathfindergoalselectoritem1)) && (this.c.contains(pathfindergoalselector_pathfindergoalselectoritem1))) {
/* 112 */             ((UnsafeList.Itr)iterator).valid = false;
/* 113 */             return false;
/*     */           }
/* 115 */         } else if ((!pathfindergoalselector_pathfindergoalselectoritem1.a.i()) && (this.c.contains(pathfindergoalselector_pathfindergoalselectoritem1))) {
/* 116 */           ((UnsafeList.Itr)iterator).valid = false;
/* 117 */           return false;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 122 */     return true;
/*     */   }
/*     */   
/*     */   private boolean a(PathfinderGoalSelectorItem pathfindergoalselector_pathfindergoalselectoritem, PathfinderGoalSelectorItem pathfindergoalselector_pathfindergoalselectoritem1) {
/* 126 */     return (pathfindergoalselector_pathfindergoalselectoritem.a.j() & pathfindergoalselector_pathfindergoalselectoritem1.a.j()) == 0;
/*     */   }
/*     */   
/*     */   class PathfinderGoalSelectorItem
/*     */   {
/*     */     public PathfinderGoal a;
/*     */     public int b;
/*     */     
/*     */     public PathfinderGoalSelectorItem(int i, PathfinderGoal pathfindergoal) {
/* 135 */       this.b = i;
/* 136 */       this.a = pathfindergoal;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderGoalSelector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */