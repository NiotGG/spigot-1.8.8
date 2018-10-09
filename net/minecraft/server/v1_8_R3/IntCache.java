/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.spigotmc.SpigotConfig;
/*    */ 
/*    */ public class IntCache
/*    */ {
/*  8 */   private static int a = 256;
/*  9 */   private static List<int[]> b = com.google.common.collect.Lists.newArrayList();
/* 10 */   private static List<int[]> c = com.google.common.collect.Lists.newArrayList();
/* 11 */   private static List<int[]> d = com.google.common.collect.Lists.newArrayList();
/* 12 */   private static List<int[]> e = com.google.common.collect.Lists.newArrayList();
/*    */   
/*    */ 
/*    */   public static synchronized int[] a(int i)
/*    */   {
/* 17 */     if (i <= 256) {
/* 18 */       if (b.isEmpty()) {
/* 19 */         int[] aint = new int['Ā'];
/* 20 */         if (c.size() < SpigotConfig.intCacheLimit) c.add(aint);
/* 21 */         return aint;
/*    */       }
/* 23 */       int[] aint = (int[])b.remove(b.size() - 1);
/* 24 */       if (c.size() < SpigotConfig.intCacheLimit) c.add(aint);
/* 25 */       return aint;
/*    */     }
/* 27 */     if (i > a) {
/* 28 */       a = i;
/* 29 */       d.clear();
/* 30 */       e.clear();
/* 31 */       int[] aint = new int[a];
/* 32 */       if (e.size() < SpigotConfig.intCacheLimit) e.add(aint);
/* 33 */       return aint; }
/* 34 */     if (d.isEmpty()) {
/* 35 */       int[] aint = new int[a];
/* 36 */       if (e.size() < SpigotConfig.intCacheLimit) e.add(aint);
/* 37 */       return aint;
/*    */     }
/* 39 */     int[] aint = (int[])d.remove(d.size() - 1);
/* 40 */     if (e.size() < SpigotConfig.intCacheLimit) e.add(aint);
/* 41 */     return aint;
/*    */   }
/*    */   
/*    */   public static synchronized void a()
/*    */   {
/* 46 */     if (!d.isEmpty()) {
/* 47 */       d.remove(d.size() - 1);
/*    */     }
/*    */     
/* 50 */     if (!b.isEmpty()) {
/* 51 */       b.remove(b.size() - 1);
/*    */     }
/*    */     
/* 54 */     d.addAll(e);
/* 55 */     b.addAll(c);
/* 56 */     e.clear();
/* 57 */     c.clear();
/*    */   }
/*    */   
/*    */   public static synchronized String b() {
/* 61 */     return "cache: " + d.size() + ", tcache: " + b.size() + ", allocated: " + e.size() + ", tallocated: " + c.size();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\IntCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */