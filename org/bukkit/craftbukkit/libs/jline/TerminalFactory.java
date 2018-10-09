/*     */ package org.bukkit.craftbukkit.libs.jline;
/*     */ 
/*     */ import java.text.MessageFormat;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.bukkit.craftbukkit.libs.jline.internal.Configuration;
/*     */ import org.bukkit.craftbukkit.libs.jline.internal.Log;
/*     */ import org.bukkit.craftbukkit.libs.jline.internal.Preconditions;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TerminalFactory
/*     */ {
/*     */   public static final String JLINE_TERMINAL = "org.bukkit.craftbukkit.libs.jline.terminal";
/*     */   public static final String AUTO = "auto";
/*     */   public static final String UNIX = "unix";
/*     */   public static final String WIN = "win";
/*     */   public static final String WINDOWS = "windows";
/*     */   public static final String NONE = "none";
/*     */   public static final String OFF = "off";
/*     */   public static final String FALSE = "false";
/*  45 */   private static Terminal term = null;
/*     */   
/*     */   public static synchronized Terminal create() {
/*  48 */     if (Log.TRACE)
/*     */     {
/*  50 */       Log.trace(new Object[] { new Throwable("CREATE MARKER") });
/*     */     }
/*     */     
/*  53 */     String type = Configuration.getString("org.bukkit.craftbukkit.libs.jline.terminal", "auto");
/*  54 */     if ("dumb".equals(System.getenv("TERM"))) {
/*  55 */       type = "none";
/*  56 */       Log.debug(new Object[] { "$TERM=dumb; setting type=", type });
/*     */     }
/*     */     
/*  59 */     Log.debug(new Object[] { "Creating terminal; type=", type });
/*     */     Terminal t;
/*     */     try
/*     */     {
/*  63 */       String tmp = type.toLowerCase();
/*     */       Terminal t;
/*  65 */       if (tmp.equals("unix")) {
/*  66 */         t = getFlavor(Flavor.UNIX);
/*     */       } else { Terminal t;
/*  68 */         if ((tmp.equals("win") | tmp.equals("windows"))) {
/*  69 */           t = getFlavor(Flavor.WINDOWS);
/*     */         } else { Terminal t;
/*  71 */           if ((tmp.equals("none")) || (tmp.equals("off")) || (tmp.equals("false"))) {
/*  72 */             t = new UnsupportedTerminal();
/*     */           } else {
/*     */             Terminal t;
/*  75 */             if (tmp.equals("auto")) {
/*  76 */               String os = Configuration.getOsName();
/*  77 */               Flavor flavor = Flavor.UNIX;
/*  78 */               if (os.contains("windows")) {
/*  79 */                 flavor = Flavor.WINDOWS;
/*     */               }
/*  81 */               t = getFlavor(flavor);
/*     */             }
/*     */             else {
/*     */               try {
/*  85 */                 t = (Terminal)Thread.currentThread().getContextClassLoader().loadClass(type).newInstance();
/*     */               }
/*     */               catch (Exception e) {
/*  88 */                 throw new IllegalArgumentException(MessageFormat.format("Invalid terminal type: {0}", new Object[] { type }), e);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*  94 */     } catch (Exception e) { Log.error(new Object[] { "Failed to construct terminal; falling back to unsupported", e });
/*  95 */       t = new UnsupportedTerminal();
/*     */     }
/*     */     
/*  98 */     Log.debug(new Object[] { "Created Terminal: ", t });
/*     */     try
/*     */     {
/* 101 */       t.init();
/*     */     }
/*     */     catch (Throwable e) {
/* 104 */       Log.error(new Object[] { "Terminal initialization failed; falling back to unsupported", e });
/* 105 */       return new UnsupportedTerminal();
/*     */     }
/*     */     
/* 108 */     return t;
/*     */   }
/*     */   
/*     */   public static synchronized void reset() {
/* 112 */     term = null;
/*     */   }
/*     */   
/*     */   public static synchronized void resetIf(Terminal t) {
/* 116 */     if (t == term) {
/* 117 */       reset();
/*     */     }
/*     */   }
/*     */   
/*     */   public static enum Type
/*     */   {
/* 123 */     AUTO, 
/* 124 */     WINDOWS, 
/* 125 */     UNIX, 
/* 126 */     NONE;
/*     */     
/*     */     private Type() {} }
/*     */   
/* 130 */   public static synchronized void configure(String type) { Preconditions.checkNotNull(type);
/* 131 */     System.setProperty("org.bukkit.craftbukkit.libs.jline.terminal", type);
/*     */   }
/*     */   
/*     */   public static synchronized void configure(Type type) {
/* 135 */     Preconditions.checkNotNull(type);
/* 136 */     configure(type.name().toLowerCase());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static enum Flavor
/*     */   {
/* 145 */     WINDOWS, 
/* 146 */     UNIX;
/*     */     
/*     */     private Flavor() {} }
/* 149 */   private static final Map<Flavor, Class<? extends Terminal>> FLAVORS = new HashMap();
/*     */   
/*     */   static {
/* 152 */     registerFlavor(Flavor.WINDOWS, AnsiWindowsTerminal.class);
/* 153 */     registerFlavor(Flavor.UNIX, UnixTerminal.class);
/*     */   }
/*     */   
/*     */   public static synchronized Terminal get() {
/* 157 */     if (term == null) {
/* 158 */       term = create();
/*     */     }
/* 160 */     return term;
/*     */   }
/*     */   
/*     */   public static Terminal getFlavor(Flavor flavor) throws Exception {
/* 164 */     Class<? extends Terminal> type = (Class)FLAVORS.get(flavor);
/* 165 */     if (type != null) {
/* 166 */       return (Terminal)type.newInstance();
/*     */     }
/*     */     
/* 169 */     throw new InternalError();
/*     */   }
/*     */   
/*     */   public static void registerFlavor(Flavor flavor, Class<? extends Terminal> type) {
/* 173 */     FLAVORS.put(flavor, type);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\TerminalFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */