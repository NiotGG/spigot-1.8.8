/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Font;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollBar;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.border.EtchedBorder;
/*     */ import javax.swing.border.TitledBorder;
/*     */ import javax.swing.text.Document;
/*     */ 
/*     */ public class ServerGUI extends JComponent
/*     */ {
/*  18 */   private static final Font a = new Font("Monospaced", 0, 12);
/*  19 */   private static final org.apache.logging.log4j.Logger b = org.apache.logging.log4j.LogManager.getLogger();
/*     */   private DedicatedServer c;
/*     */   
/*     */   public static void a(DedicatedServer paramDedicatedServer)
/*     */   {
/*     */     try
/*     */     {
/*  26 */       javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
/*     */     }
/*     */     catch (Exception localException) {}
/*     */     
/*  30 */     ServerGUI localServerGUI = new ServerGUI(paramDedicatedServer);
/*  31 */     JFrame localJFrame = new JFrame("Minecraft server");
/*  32 */     localJFrame.add(localServerGUI);
/*  33 */     localJFrame.pack();
/*  34 */     localJFrame.setLocationRelativeTo(null);
/*  35 */     localJFrame.setVisible(true);
/*  36 */     localJFrame.addWindowListener(new java.awt.event.WindowAdapter()
/*     */     {
/*     */       public void windowClosing(java.awt.event.WindowEvent paramAnonymousWindowEvent) {
/*  39 */         this.a.safeShutdown();
/*  40 */         while (!this.a.isStopped()) {
/*     */           try {
/*  42 */             Thread.sleep(100L);
/*     */           } catch (InterruptedException localInterruptedException) {
/*  44 */             localInterruptedException.printStackTrace();
/*     */           }
/*     */         }
/*  47 */         System.exit(0);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public ServerGUI(DedicatedServer paramDedicatedServer) {
/*  53 */     this.c = paramDedicatedServer;
/*  54 */     setPreferredSize(new java.awt.Dimension(854, 480));
/*     */     
/*  56 */     setLayout(new BorderLayout());
/*     */     try {
/*  58 */       add(c(), "Center");
/*  59 */       add(a(), "West");
/*     */     } catch (Exception localException) {
/*  61 */       b.error("Couldn't build server GUI", localException);
/*     */     }
/*     */   }
/*     */   
/*     */   private JComponent a() throws Exception {
/*  66 */     JPanel localJPanel = new JPanel(new BorderLayout());
/*  67 */     localJPanel.add(new GuiStatsComponent(this.c), "North");
/*  68 */     localJPanel.add(b(), "Center");
/*  69 */     localJPanel.setBorder(new TitledBorder(new EtchedBorder(), "Stats"));
/*  70 */     return localJPanel;
/*     */   }
/*     */   
/*     */   private JComponent b() throws Exception {
/*  74 */     PlayerListBox localPlayerListBox = new PlayerListBox(this.c);
/*  75 */     JScrollPane localJScrollPane = new JScrollPane(localPlayerListBox, 22, 30);
/*  76 */     localJScrollPane.setBorder(new TitledBorder(new EtchedBorder(), "Players"));
/*     */     
/*  78 */     return localJScrollPane;
/*     */   }
/*     */   
/*     */   private JComponent c() throws Exception {
/*  82 */     JPanel localJPanel = new JPanel(new BorderLayout());
/*  83 */     final JTextArea localJTextArea = new JTextArea();
/*  84 */     final JScrollPane localJScrollPane = new JScrollPane(localJTextArea, 22, 30);
/*  85 */     localJTextArea.setEditable(false);
/*  86 */     localJTextArea.setFont(a);
/*     */     
/*  88 */     final JTextField localJTextField = new JTextField();
/*  89 */     localJTextField.addActionListener(new java.awt.event.ActionListener()
/*     */     {
/*     */       public void actionPerformed(java.awt.event.ActionEvent paramAnonymousActionEvent) {
/*  92 */         String str = localJTextField.getText().trim();
/*  93 */         if (str.length() > 0) {
/*  94 */           ServerGUI.a(ServerGUI.this).issueCommand(str, MinecraftServer.getServer());
/*     */         }
/*  96 */         localJTextField.setText("");
/*     */       }
/*     */       
/*  99 */     });
/* 100 */     localJTextArea.addFocusListener(new java.awt.event.FocusAdapter()
/*     */     {
/*     */ 
/*     */       public void focusGained(java.awt.event.FocusEvent paramAnonymousFocusEvent) {}
/*     */ 
/* 105 */     });
/* 106 */     localJPanel.add(localJScrollPane, "Center");
/* 107 */     localJPanel.add(localJTextField, "South");
/* 108 */     localJPanel.setBorder(new TitledBorder(new EtchedBorder(), "Log and chat"));
/*     */     
/* 110 */     Thread localThread = new Thread(new Runnable()
/*     */     {
/*     */       public void run() {
/*     */         String str;
/* 114 */         while ((str = com.mojang.util.QueueLogAppender.getNextLogEvent("ServerGuiConsole")) != null) {
/* 115 */           ServerGUI.this.a(localJTextArea, localJScrollPane, str);
/*     */         }
/*     */       }
/* 118 */     });
/* 119 */     localThread.setDaemon(true);
/* 120 */     localThread.start();
/*     */     
/* 122 */     return localJPanel;
/*     */   }
/*     */   
/*     */   public void a(final JTextArea paramJTextArea, final JScrollPane paramJScrollPane, final String paramString) {
/* 126 */     if (!javax.swing.SwingUtilities.isEventDispatchThread()) {
/* 127 */       javax.swing.SwingUtilities.invokeLater(new Runnable()
/*     */       {
/*     */         public void run() {
/* 130 */           ServerGUI.this.a(paramJTextArea, paramJScrollPane, paramString);
/*     */         }
/* 132 */       });
/* 133 */       return;
/*     */     }
/*     */     
/* 136 */     Document localDocument = paramJTextArea.getDocument();
/* 137 */     JScrollBar localJScrollBar = paramJScrollPane.getVerticalScrollBar();
/* 138 */     int i = 0;
/*     */     
/* 140 */     if (paramJScrollPane.getViewport().getView() == paramJTextArea) {
/* 141 */       i = localJScrollBar.getValue() + localJScrollBar.getSize().getHeight() + a.getSize() * 4 > localJScrollBar.getMaximum() ? 1 : 0;
/*     */     }
/*     */     try
/*     */     {
/* 145 */       localDocument.insertString(localDocument.getLength(), paramString, null);
/*     */     }
/*     */     catch (javax.swing.text.BadLocationException localBadLocationException) {}
/* 148 */     if (i != 0) {
/* 149 */       localJScrollBar.setValue(Integer.MAX_VALUE);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ServerGUI.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */