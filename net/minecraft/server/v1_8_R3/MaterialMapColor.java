/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class MaterialMapColor {
/*  4 */   public static final MaterialMapColor[] a = new MaterialMapColor[64];
/*    */   
/*  6 */   public static final MaterialMapColor b = new MaterialMapColor(0, 0);
/*  7 */   public static final MaterialMapColor c = new MaterialMapColor(1, 8368696);
/*  8 */   public static final MaterialMapColor d = new MaterialMapColor(2, 16247203);
/*  9 */   public static final MaterialMapColor e = new MaterialMapColor(3, 13092807);
/* 10 */   public static final MaterialMapColor f = new MaterialMapColor(4, 16711680);
/* 11 */   public static final MaterialMapColor g = new MaterialMapColor(5, 10526975);
/* 12 */   public static final MaterialMapColor h = new MaterialMapColor(6, 10987431);
/* 13 */   public static final MaterialMapColor i = new MaterialMapColor(7, 31744);
/* 14 */   public static final MaterialMapColor j = new MaterialMapColor(8, 16777215);
/* 15 */   public static final MaterialMapColor k = new MaterialMapColor(9, 10791096);
/* 16 */   public static final MaterialMapColor l = new MaterialMapColor(10, 9923917);
/* 17 */   public static final MaterialMapColor m = new MaterialMapColor(11, 7368816);
/* 18 */   public static final MaterialMapColor n = new MaterialMapColor(12, 4210943);
/* 19 */   public static final MaterialMapColor o = new MaterialMapColor(13, 9402184);
/* 20 */   public static final MaterialMapColor p = new MaterialMapColor(14, 16776437);
/* 21 */   public static final MaterialMapColor q = new MaterialMapColor(15, 14188339);
/* 22 */   public static final MaterialMapColor r = new MaterialMapColor(16, 11685080);
/* 23 */   public static final MaterialMapColor s = new MaterialMapColor(17, 6724056);
/* 24 */   public static final MaterialMapColor t = new MaterialMapColor(18, 15066419);
/* 25 */   public static final MaterialMapColor u = new MaterialMapColor(19, 8375321);
/* 26 */   public static final MaterialMapColor v = new MaterialMapColor(20, 15892389);
/* 27 */   public static final MaterialMapColor w = new MaterialMapColor(21, 5000268);
/* 28 */   public static final MaterialMapColor x = new MaterialMapColor(22, 10066329);
/* 29 */   public static final MaterialMapColor y = new MaterialMapColor(23, 5013401);
/* 30 */   public static final MaterialMapColor z = new MaterialMapColor(24, 8339378);
/* 31 */   public static final MaterialMapColor A = new MaterialMapColor(25, 3361970);
/* 32 */   public static final MaterialMapColor B = new MaterialMapColor(26, 6704179);
/* 33 */   public static final MaterialMapColor C = new MaterialMapColor(27, 6717235);
/* 34 */   public static final MaterialMapColor D = new MaterialMapColor(28, 10040115);
/* 35 */   public static final MaterialMapColor E = new MaterialMapColor(29, 1644825);
/* 36 */   public static final MaterialMapColor F = new MaterialMapColor(30, 16445005);
/* 37 */   public static final MaterialMapColor G = new MaterialMapColor(31, 6085589);
/* 38 */   public static final MaterialMapColor H = new MaterialMapColor(32, 4882687);
/* 39 */   public static final MaterialMapColor I = new MaterialMapColor(33, 55610);
/* 40 */   public static final MaterialMapColor J = new MaterialMapColor(34, 8476209);
/* 41 */   public static final MaterialMapColor K = new MaterialMapColor(35, 7340544);
/*    */   public final int L;
/*    */   public final int M;
/*    */   
/*    */   private MaterialMapColor(int paramInt1, int paramInt2)
/*    */   {
/* 47 */     if ((paramInt1 < 0) || (paramInt1 > 63)) {
/* 48 */       throw new IndexOutOfBoundsException("Map colour ID must be between 0 and 63 (inclusive)");
/*    */     }
/* 50 */     this.M = paramInt1;
/* 51 */     this.L = paramInt2;
/* 52 */     a[paramInt1] = this;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\MaterialMapColor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */