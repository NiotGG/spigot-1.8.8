/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class NextTickListEntry implements Comparable<NextTickListEntry>
/*    */ {
/*    */   private static long d;
/*    */   private final Block e;
/*    */   public final BlockPosition a;
/*    */   public long b;
/*    */   public int c;
/*    */   private long f;
/*    */   
/*    */   public NextTickListEntry(BlockPosition blockposition, Block block) {
/* 13 */     this.f = (d++);
/* 14 */     this.a = blockposition;
/* 15 */     this.e = block;
/*    */   }
/*    */   
/*    */   public boolean equals(Object object) {
/* 19 */     if (!(object instanceof NextTickListEntry)) {
/* 20 */       return false;
/*    */     }
/* 22 */     NextTickListEntry nextticklistentry = (NextTickListEntry)object;
/*    */     
/* 24 */     return (this.a.equals(nextticklistentry.a)) && (Block.a(this.e, nextticklistentry.e));
/*    */   }
/*    */   
/*    */   public int hashCode()
/*    */   {
/* 29 */     return this.a.hashCode();
/*    */   }
/*    */   
/*    */   public NextTickListEntry a(long i) {
/* 33 */     this.b = i;
/* 34 */     return this;
/*    */   }
/*    */   
/*    */   public void a(int i) {
/* 38 */     this.c = i;
/*    */   }
/*    */   
/*    */   public int a(NextTickListEntry nextticklistentry) {
/* 42 */     return this.f > nextticklistentry.f ? 1 : this.f < nextticklistentry.f ? -1 : this.c != nextticklistentry.c ? this.c - nextticklistentry.c : this.b > nextticklistentry.b ? 1 : this.b < nextticklistentry.b ? -1 : 0;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 46 */     return Block.getId(this.e) + ": " + this.a + ", " + this.b + ", " + this.c + ", " + this.f;
/*    */   }
/*    */   
/*    */   public Block a() {
/* 50 */     return this.e;
/*    */   }
/*    */   
/*    */   public int compareTo(NextTickListEntry object) {
/* 54 */     return a(object);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\NextTickListEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */