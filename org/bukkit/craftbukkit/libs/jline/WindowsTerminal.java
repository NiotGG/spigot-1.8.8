/*     */ package org.bukkit.craftbukkit.libs.jline;
/*     */ 
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.charset.Charset;
/*     */ import org.bukkit.craftbukkit.libs.jline.internal.Configuration;
/*     */ import org.bukkit.craftbukkit.libs.jline.internal.Log;
/*     */ import org.fusesource.jansi.internal.Kernel32;
/*     */ import org.fusesource.jansi.internal.Kernel32.INPUT_RECORD;
/*     */ import org.fusesource.jansi.internal.Kernel32.KEY_EVENT_RECORD;
/*     */ import org.fusesource.jansi.internal.WindowsSupport;
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
/*     */ public class WindowsTerminal
/*     */   extends TerminalSupport
/*     */ {
/*  58 */   public static final String DIRECT_CONSOLE = WindowsTerminal.class.getName() + ".directConsole";
/*     */   
/*  60 */   public static final String ANSI = WindowsTerminal.class.getName() + ".ansi";
/*     */   private boolean directConsole;
/*     */   private int originalMode;
/*     */   
/*     */   public WindowsTerminal()
/*     */     throws Exception
/*     */   {
/*  67 */     super(true);
/*     */   }
/*     */   
/*     */   public void init() throws Exception
/*     */   {
/*  72 */     super.init();
/*     */     
/*  74 */     setAnsiSupported(Configuration.getBoolean(ANSI, true));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  80 */     setDirectConsole(Configuration.getBoolean(DIRECT_CONSOLE, true));
/*     */     
/*  82 */     this.originalMode = getConsoleMode();
/*  83 */     setConsoleMode(this.originalMode & (ConsoleMode.ENABLE_ECHO_INPUT.code ^ 0xFFFFFFFF));
/*  84 */     setEchoEnabled(false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void restore()
/*     */     throws Exception
/*     */   {
/*  95 */     setConsoleMode(this.originalMode);
/*  96 */     super.restore();
/*     */   }
/*     */   
/*     */   public int getWidth()
/*     */   {
/* 101 */     int w = getWindowsTerminalWidth();
/* 102 */     return w < 1 ? 80 : w;
/*     */   }
/*     */   
/*     */   public int getHeight()
/*     */   {
/* 107 */     int h = getWindowsTerminalHeight();
/* 108 */     return h < 1 ? 24 : h;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setEchoEnabled(boolean enabled)
/*     */   {
/* 114 */     if (enabled) {
/* 115 */       setConsoleMode(getConsoleMode() | ConsoleMode.ENABLE_ECHO_INPUT.code | ConsoleMode.ENABLE_LINE_INPUT.code | ConsoleMode.ENABLE_PROCESSED_INPUT.code | ConsoleMode.ENABLE_WINDOW_INPUT.code);
/*     */ 
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/*     */ 
/* 122 */       setConsoleMode(getConsoleMode() & ((ConsoleMode.ENABLE_LINE_INPUT.code | ConsoleMode.ENABLE_ECHO_INPUT.code | ConsoleMode.ENABLE_PROCESSED_INPUT.code | ConsoleMode.ENABLE_WINDOW_INPUT.code) ^ 0xFFFFFFFF));
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 128 */     super.setEchoEnabled(enabled);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setDirectConsole(boolean flag)
/*     */   {
/* 135 */     this.directConsole = flag;
/* 136 */     Log.debug(new Object[] { "Direct console: ", Boolean.valueOf(flag) });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Boolean getDirectConsole()
/*     */   {
/* 143 */     return Boolean.valueOf(this.directConsole);
/*     */   }
/*     */   
/*     */   public InputStream wrapInIfNeeded(InputStream in)
/*     */     throws IOException
/*     */   {
/* 149 */     if ((this.directConsole) && (isSystemIn(in))) {
/* 150 */       new InputStream() {
/* 151 */         private byte[] buf = null;
/* 152 */         int bufIdx = 0;
/*     */         
/*     */         public int read() throws IOException
/*     */         {
/* 156 */           while ((this.buf == null) || (this.bufIdx == this.buf.length)) {
/* 157 */             this.buf = WindowsTerminal.this.readConsoleInput();
/* 158 */             this.bufIdx = 0;
/*     */           }
/* 160 */           int c = this.buf[this.bufIdx] & 0xFF;
/* 161 */           this.bufIdx += 1;
/* 162 */           return c;
/*     */         }
/*     */       };
/*     */     }
/* 166 */     return super.wrapInIfNeeded(in);
/*     */   }
/*     */   
/*     */   protected boolean isSystemIn(InputStream in) throws IOException
/*     */   {
/* 171 */     if (in == null) {
/* 172 */       return false;
/*     */     }
/* 174 */     if (in == System.in) {
/* 175 */       return true;
/*     */     }
/* 177 */     if (((in instanceof FileInputStream)) && (((FileInputStream)in).getFD() == FileDescriptor.in)) {
/* 178 */       return true;
/*     */     }
/*     */     
/* 181 */     return false;
/*     */   }
/*     */   
/*     */   public String getOutputEncoding()
/*     */   {
/* 186 */     int codepage = getConsoleOutputCodepage();
/*     */     
/* 188 */     String charsetMS = "ms" + codepage;
/* 189 */     if (Charset.isSupported(charsetMS)) {
/* 190 */       return charsetMS;
/*     */     }
/* 192 */     String charsetCP = "cp" + codepage;
/* 193 */     if (Charset.isSupported(charsetCP)) {
/* 194 */       return charsetCP;
/*     */     }
/* 196 */     Log.debug(new Object[] { "can't figure out the Java Charset of this code page (" + codepage + ")..." });
/* 197 */     return super.getOutputEncoding();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private int getConsoleMode()
/*     */   {
/* 204 */     return WindowsSupport.getConsoleMode();
/*     */   }
/*     */   
/*     */   private void setConsoleMode(int mode) {
/* 208 */     WindowsSupport.setConsoleMode(mode);
/*     */   }
/*     */   
/*     */   private byte[] readConsoleInput()
/*     */   {
/* 213 */     Kernel32.INPUT_RECORD[] events = null;
/*     */     try {
/* 215 */       events = WindowsSupport.readConsoleInput(1);
/*     */     } catch (IOException e) {
/* 217 */       Log.debug(new Object[] { "read Windows console input error: ", e });
/*     */     }
/* 219 */     if (events == null) {
/* 220 */       return new byte[0];
/*     */     }
/* 222 */     StringBuilder sb = new StringBuilder();
/* 223 */     for (int i = 0; i < events.length; i++) {
/* 224 */       Kernel32.KEY_EVENT_RECORD keyEvent = events[i].keyEvent;
/*     */       
/* 226 */       if (keyEvent.keyDown) {
/* 227 */         if (keyEvent.uchar > 0)
/*     */         {
/*     */ 
/* 230 */           int altState = Kernel32.KEY_EVENT_RECORD.LEFT_ALT_PRESSED | Kernel32.KEY_EVENT_RECORD.RIGHT_ALT_PRESSED;
/* 231 */           if (((keyEvent.uchar >= '@') && (keyEvent.uchar <= '_')) || ((keyEvent.uchar >= 'a') && (keyEvent.uchar <= 'z') && ((keyEvent.controlKeyState & altState) != 0)))
/*     */           {
/* 233 */             sb.append('\033');
/*     */           }
/*     */           
/* 236 */           sb.append(keyEvent.uchar);
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 241 */           String escapeSequence = null;
/* 242 */           switch (keyEvent.keyCode) {
/*     */           case 33: 
/* 244 */             escapeSequence = "\033[5~";
/* 245 */             break;
/*     */           case 34: 
/* 247 */             escapeSequence = "\033[6~";
/* 248 */             break;
/*     */           case 35: 
/* 250 */             escapeSequence = "\033[4~";
/* 251 */             break;
/*     */           case 36: 
/* 253 */             escapeSequence = "\033[1~";
/* 254 */             break;
/*     */           case 37: 
/* 256 */             escapeSequence = "\033[D";
/* 257 */             break;
/*     */           case 38: 
/* 259 */             escapeSequence = "\033[A";
/* 260 */             break;
/*     */           case 39: 
/* 262 */             escapeSequence = "\033[C";
/* 263 */             break;
/*     */           case 40: 
/* 265 */             escapeSequence = "\033[B";
/* 266 */             break;
/*     */           case 45: 
/* 268 */             escapeSequence = "\033[2~";
/* 269 */             break;
/*     */           case 46: 
/* 271 */             escapeSequence = "\033[3~";
/* 272 */             break;
/*     */           }
/*     */           
/*     */           
/* 276 */           if (escapeSequence != null) {
/* 277 */             for (int k = 0; k < keyEvent.repeatCount; k++) {
/* 278 */               sb.append(escapeSequence);
/*     */             }
/*     */             
/*     */           }
/*     */         }
/*     */       }
/* 284 */       else if ((keyEvent.keyCode == 18) && (keyEvent.uchar > 0)) {
/* 285 */         sb.append(keyEvent.uchar);
/*     */       }
/*     */     }
/*     */     
/* 289 */     return sb.toString().getBytes();
/*     */   }
/*     */   
/*     */   private int getConsoleOutputCodepage() {
/* 293 */     return Kernel32.GetConsoleOutputCP();
/*     */   }
/*     */   
/*     */   private int getWindowsTerminalWidth() {
/* 297 */     return WindowsSupport.getWindowsTerminalWidth();
/*     */   }
/*     */   
/*     */   private int getWindowsTerminalHeight() {
/* 301 */     return WindowsSupport.getWindowsTerminalHeight();
/*     */   }
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
/*     */   public static enum ConsoleMode
/*     */   {
/* 316 */     ENABLE_LINE_INPUT(2), 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 323 */     ENABLE_ECHO_INPUT(4), 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 333 */     ENABLE_PROCESSED_INPUT(1), 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 342 */     ENABLE_WINDOW_INPUT(8), 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 350 */     ENABLE_MOUSE_INPUT(16), 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 359 */     ENABLE_PROCESSED_OUTPUT(1), 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 366 */     ENABLE_WRAP_AT_EOL_OUTPUT(2);
/*     */     
/*     */     public final int code;
/*     */     
/*     */     private ConsoleMode(int code) {
/* 371 */       this.code = code;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\WindowsTerminal.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */