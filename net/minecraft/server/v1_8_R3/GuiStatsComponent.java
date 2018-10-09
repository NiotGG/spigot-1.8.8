/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Graphics;
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.text.DecimalFormat;
/*    */ import javax.swing.Timer;
/*    */ 
/*    */ public class GuiStatsComponent extends javax.swing.JComponent
/*    */ {
/* 12 */   private static final DecimalFormat a = new DecimalFormat("########0.000");
/*    */   
/*    */ 
/* 15 */   private int[] b = new int['Ā'];
/*    */   private int c;
/* 17 */   private String[] d = new String[11];
/*    */   private final MinecraftServer e;
/*    */   
/*    */   public GuiStatsComponent(MinecraftServer paramMinecraftServer) {
/* 21 */     this.e = paramMinecraftServer;
/* 22 */     setPreferredSize(new Dimension(456, 246));
/* 23 */     setMinimumSize(new Dimension(456, 246));
/* 24 */     setMaximumSize(new Dimension(456, 246));
/* 25 */     new Timer(500, new java.awt.event.ActionListener()
/*    */     {
/*    */       public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
/* 28 */         GuiStatsComponent.a(GuiStatsComponent.this);
/*    */       }
/* 30 */     }).start();
/* 31 */     setBackground(Color.BLACK);
/*    */   }
/*    */   
/*    */   private void a() {
/* 35 */     long l = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
/* 36 */     System.gc();
/* 37 */     this.d[0] = ("Memory use: " + l / 1024L / 1024L + " mb (" + Runtime.getRuntime().freeMemory() * 100L / Runtime.getRuntime().maxMemory() + "% free)");
/* 38 */     this.d[1] = ("Avg tick: " + a.format(a(this.e.h) * 1.0E-6D) + " ms");
/* 39 */     repaint();
/*    */   }
/*    */   
/*    */   private double a(long[] paramArrayOfLong) {
/* 43 */     long l = 0L;
/* 44 */     for (int i = 0; i < paramArrayOfLong.length; i++) {
/* 45 */       l += paramArrayOfLong[i];
/*    */     }
/* 47 */     return l / paramArrayOfLong.length;
/*    */   }
/*    */   
/*    */   public void paint(Graphics paramGraphics)
/*    */   {
/* 52 */     paramGraphics.setColor(new Color(16777215));
/* 53 */     paramGraphics.fillRect(0, 0, 456, 246);
/*    */     
/* 55 */     for (int i = 0; i < 256; i++) {
/* 56 */       int j = this.b[(i + this.c & 0xFF)];
/* 57 */       paramGraphics.setColor(new Color(j + 28 << 16));
/* 58 */       paramGraphics.fillRect(i, 100 - j, 1, j);
/*    */     }
/* 60 */     paramGraphics.setColor(Color.BLACK);
/* 61 */     for (i = 0; i < this.d.length; i++) {
/* 62 */       String str = this.d[i];
/* 63 */       if (str != null) {
/* 64 */         paramGraphics.drawString(str, 32, 116 + i * 16);
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\GuiStatsComponent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */