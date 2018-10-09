/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MethodProfiler
/*    */ {
/*    */   public boolean a;
/*    */   
/*    */   public void a() {}
/*    */   
/*    */   public void a(String s) {}
/*    */   
/*    */   public void b() {}
/*    */   
/*    */   public List<ProfilerInfo> b(String s)
/*    */   {
/* 29 */     return null;
/*    */   }
/*    */   
/*    */   public void c(String s) {}
/*    */   
/*    */   public String c()
/*    */   {
/* 36 */     return "";
/*    */   }
/*    */   
/*    */   public static final class ProfilerInfo implements Comparable<ProfilerInfo>
/*    */   {
/*    */     public double a;
/*    */     public double b;
/*    */     public String c;
/*    */     
/*    */     public ProfilerInfo(String s, double d0, double d1) {
/* 46 */       this.c = s;
/* 47 */       this.a = d0;
/* 48 */       this.b = d1;
/*    */     }
/*    */     
/*    */     public int a(ProfilerInfo methodprofiler_profilerinfo) {
/* 52 */       return methodprofiler_profilerinfo.a > this.a ? 1 : methodprofiler_profilerinfo.a < this.a ? -1 : methodprofiler_profilerinfo.c.compareTo(this.c);
/*    */     }
/*    */     
/*    */     public int compareTo(ProfilerInfo object) {
/* 56 */       return a(object);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\MethodProfiler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */