/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Vector;
/*    */ import javax.swing.JList;
/*    */ 
/*    */ public class PlayerListBox
/*    */   extends JList implements IUpdatePlayerListBox
/*    */ {
/*    */   private MinecraftServer a;
/*    */   private int b;
/*    */   
/*    */   public PlayerListBox(MinecraftServer paramMinecraftServer)
/*    */   {
/* 15 */     this.a = paramMinecraftServer;
/* 16 */     paramMinecraftServer.a(this);
/*    */   }
/*    */   
/*    */   public void c()
/*    */   {
/* 21 */     if (this.b++ % 20 == 0) {
/* 22 */       Vector localVector = new Vector();
/* 23 */       for (int i = 0; i < this.a.getPlayerList().v().size(); i++) {
/* 24 */         localVector.add(((EntityPlayer)this.a.getPlayerList().v().get(i)).getName());
/*    */       }
/* 26 */       setListData(localVector);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PlayerListBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */