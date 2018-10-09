/*      */ package org.bukkit.craftbukkit.libs.jline.console;
/*      */ 
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.datatransfer.Clipboard;
/*      */ import java.awt.datatransfer.DataFlavor;
/*      */ import java.awt.datatransfer.Transferable;
/*      */ import java.awt.datatransfer.UnsupportedFlavorException;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.File;
/*      */ import java.io.FileDescriptor;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.io.OutputStreamWriter;
/*      */ import java.io.Reader;
/*      */ import java.io.Writer;
/*      */ import java.net.URI;
/*      */ import java.net.URL;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.ListIterator;
/*      */ import java.util.Map;
/*      */ import java.util.ResourceBundle;
/*      */ import org.bukkit.craftbukkit.libs.jline.Terminal;
/*      */ import org.bukkit.craftbukkit.libs.jline.TerminalFactory;
/*      */ import org.bukkit.craftbukkit.libs.jline.console.completer.CandidateListCompletionHandler;
/*      */ import org.bukkit.craftbukkit.libs.jline.console.completer.Completer;
/*      */ import org.bukkit.craftbukkit.libs.jline.console.completer.CompletionHandler;
/*      */ import org.bukkit.craftbukkit.libs.jline.console.history.History;
/*      */ import org.bukkit.craftbukkit.libs.jline.console.history.History.Entry;
/*      */ import org.bukkit.craftbukkit.libs.jline.console.history.MemoryHistory;
/*      */ import org.bukkit.craftbukkit.libs.jline.internal.Configuration;
/*      */ import org.bukkit.craftbukkit.libs.jline.internal.InputStreamReader;
/*      */ import org.bukkit.craftbukkit.libs.jline.internal.Log;
/*      */ import org.bukkit.craftbukkit.libs.jline.internal.NonBlockingInputStream;
/*      */ import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
/*      */ import org.bukkit.craftbukkit.libs.jline.internal.Preconditions;
/*      */ import org.bukkit.craftbukkit.libs.jline.internal.Urls;
/*      */ import org.fusesource.jansi.AnsiOutputStream;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ConsoleReader
/*      */ {
/*      */   public static final String JLINE_NOBELL = "org.bukkit.craftbukkit.libs.jline.nobell";
/*      */   public static final String JLINE_ESC_TIMEOUT = "org.bukkit.craftbukkit.libs.jline.esc.timeout";
/*      */   public static final String JLINE_INPUTRC = "org.bukkit.craftbukkit.libs.jline.inputrc";
/*      */   public static final String INPUT_RC = ".inputrc";
/*      */   public static final String DEFAULT_INPUT_RC = "/etc/inputrc";
/*      */   public static final char BACKSPACE = '\b';
/*      */   public static final char RESET_LINE = '\r';
/*      */   public static final char KEYBOARD_BELL = '\007';
/*      */   public static final char NULL_MASK = '\000';
/*      */   public static final int TAB_WIDTH = 4;
/*   93 */   private static final ResourceBundle resources = ResourceBundle.getBundle(CandidateListCompletionHandler.class.getName());
/*      */   
/*      */   private final Terminal terminal;
/*      */   
/*      */   private final Writer out;
/*      */   
/*   99 */   private final CursorBuffer buf = new CursorBuffer();
/*      */   
/*      */   private String prompt;
/*      */   
/*      */   private int promptLen;
/*  104 */   private boolean expandEvents = true;
/*      */   
/*  106 */   private boolean bellEnabled = !Configuration.getBoolean("org.bukkit.craftbukkit.libs.jline.nobell", true);
/*      */   
/*  108 */   private boolean handleUserInterrupt = false;
/*      */   
/*      */   private Character mask;
/*      */   
/*      */   private Character echoCharacter;
/*      */   
/*  114 */   private StringBuffer searchTerm = null;
/*      */   
/*  116 */   private String previousSearchTerm = "";
/*      */   
/*  118 */   private int searchIndex = -1;
/*      */   
/*  120 */   private int parenBlinkTimeout = 500;
/*      */   
/*      */ 
/*      */ 
/*      */   private NonBlockingInputStream in;
/*      */   
/*      */ 
/*      */ 
/*      */   private long escapeTimeout;
/*      */   
/*      */ 
/*      */ 
/*      */   private Reader reader;
/*      */   
/*      */ 
/*      */ 
/*      */   private boolean isUnitTestInput;
/*      */   
/*      */ 
/*      */ 
/*  140 */   private char charSearchChar = '\000';
/*  141 */   private char charSearchLastInvokeChar = '\000';
/*  142 */   private char charSearchFirstInvokeChar = '\000';
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  147 */   private String yankBuffer = "";
/*      */   
/*  149 */   private KillRing killRing = new KillRing();
/*      */   
/*      */   private String encoding;
/*      */   
/*      */   private boolean recording;
/*      */   
/*  155 */   private String macro = "";
/*      */   
/*      */   private String appName;
/*      */   
/*      */   private URL inputrcUrl;
/*      */   
/*      */   private ConsoleKeys consoleKeys;
/*      */   
/*  163 */   private String commentBegin = null;
/*      */   
/*  165 */   private boolean skipLF = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  174 */   private boolean copyPasteDetection = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  179 */   private State state = State.NORMAL;
/*      */   
/*      */ 
/*      */   public static final String JLINE_COMPLETION_THRESHOLD = "org.bukkit.craftbukkit.libs.jline.completion.threshold";
/*      */   
/*      */ 
/*      */ 
/*      */   private static enum State
/*      */   {
/*  188 */     NORMAL, 
/*      */     
/*      */ 
/*      */ 
/*  192 */     SEARCH, 
/*  193 */     FORWARD_SEARCH, 
/*      */     
/*      */ 
/*      */ 
/*  197 */     VI_YANK_TO, 
/*      */     
/*      */ 
/*      */ 
/*  201 */     VI_DELETE_TO, 
/*      */     
/*      */ 
/*      */ 
/*  205 */     VI_CHANGE_TO;
/*      */     
/*      */     private State() {} }
/*      */   
/*  209 */   public ConsoleReader() throws IOException { this(null, new FileInputStream(FileDescriptor.in), System.out, null); }
/*      */   
/*      */   public ConsoleReader(InputStream in, OutputStream out) throws IOException
/*      */   {
/*  213 */     this(null, in, out, null);
/*      */   }
/*      */   
/*      */   public ConsoleReader(InputStream in, OutputStream out, Terminal term) throws IOException {
/*  217 */     this(null, in, out, term);
/*      */   }
/*      */   
/*      */   public ConsoleReader(@Nullable String appName, InputStream in, OutputStream out, @Nullable Terminal term) throws IOException {
/*  221 */     this(appName, in, out, term, null);
/*      */   }
/*      */   
/*      */   public ConsoleReader(@Nullable String appName, InputStream in, OutputStream out, @Nullable Terminal term, @Nullable String encoding)
/*      */     throws IOException
/*      */   {
/*  227 */     this.appName = (appName != null ? appName : "JLine");
/*  228 */     this.encoding = (encoding != null ? encoding : Configuration.getEncoding());
/*  229 */     this.terminal = (term != null ? term : TerminalFactory.get());
/*  230 */     String outEncoding = this.terminal.getOutputEncoding() != null ? this.terminal.getOutputEncoding() : this.encoding;
/*  231 */     this.out = new OutputStreamWriter(this.terminal.wrapOutIfNeeded(out), outEncoding);
/*  232 */     setInput(in);
/*      */     
/*  234 */     this.inputrcUrl = getInputRc();
/*      */     
/*  236 */     this.consoleKeys = new ConsoleKeys(this.appName, this.inputrcUrl);
/*      */   }
/*      */   
/*      */   private URL getInputRc() throws IOException {
/*  240 */     String path = Configuration.getString("org.bukkit.craftbukkit.libs.jline.inputrc");
/*  241 */     if (path == null) {
/*  242 */       File f = new File(Configuration.getUserHome(), ".inputrc");
/*  243 */       if (!f.exists()) {
/*  244 */         f = new File("/etc/inputrc");
/*      */       }
/*  246 */       return f.toURI().toURL();
/*      */     }
/*  248 */     return Urls.create(path);
/*      */   }
/*      */   
/*      */   public KeyMap getKeys()
/*      */   {
/*  253 */     return this.consoleKeys.getKeys();
/*      */   }
/*      */   
/*      */   void setInput(InputStream in) throws IOException {
/*  257 */     this.escapeTimeout = Configuration.getLong("org.bukkit.craftbukkit.libs.jline.esc.timeout", 100L);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  270 */     this.isUnitTestInput = (in instanceof ByteArrayInputStream);
/*  271 */     boolean nonBlockingEnabled = (this.escapeTimeout > 0L) && (this.terminal.isSupported()) && (in != null);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  280 */     if (this.in != null) {
/*  281 */       this.in.shutdown();
/*      */     }
/*      */     
/*  284 */     InputStream wrapped = this.terminal.wrapInIfNeeded(in);
/*      */     
/*  286 */     this.in = new NonBlockingInputStream(wrapped, nonBlockingEnabled);
/*  287 */     this.reader = new InputStreamReader(this.in, this.encoding);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void shutdown()
/*      */   {
/*  296 */     if (this.in != null) {
/*  297 */       this.in.shutdown();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   protected void finalize()
/*      */     throws Throwable
/*      */   {
/*      */     try
/*      */     {
/*  307 */       shutdown();
/*      */     }
/*      */     finally {
/*  310 */       super.finalize();
/*      */     }
/*      */   }
/*      */   
/*      */   public InputStream getInput() {
/*  315 */     return this.in;
/*      */   }
/*      */   
/*      */   public Writer getOutput() {
/*  319 */     return this.out;
/*      */   }
/*      */   
/*      */   public Terminal getTerminal() {
/*  323 */     return this.terminal;
/*      */   }
/*      */   
/*      */   public CursorBuffer getCursorBuffer() {
/*  327 */     return this.buf;
/*      */   }
/*      */   
/*      */   public void setExpandEvents(boolean expand) {
/*  331 */     this.expandEvents = expand;
/*      */   }
/*      */   
/*      */   public boolean getExpandEvents() {
/*  335 */     return this.expandEvents;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setCopyPasteDetection(boolean onoff)
/*      */   {
/*  345 */     this.copyPasteDetection = onoff;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public boolean isCopyPasteDetectionEnabled()
/*      */   {
/*  352 */     return this.copyPasteDetection;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setBellEnabled(boolean enabled)
/*      */   {
/*  362 */     this.bellEnabled = enabled;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean getBellEnabled()
/*      */   {
/*  372 */     return this.bellEnabled;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setHandleUserInterrupt(boolean enabled)
/*      */   {
/*  385 */     this.handleUserInterrupt = enabled;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean getHandleUserInterrupt()
/*      */   {
/*  396 */     return this.handleUserInterrupt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setCommentBegin(String commentBegin)
/*      */   {
/*  406 */     this.commentBegin = commentBegin;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getCommentBegin()
/*      */   {
/*  415 */     String str = this.commentBegin;
/*      */     
/*  417 */     if (str == null) {
/*  418 */       str = this.consoleKeys.getVariable("comment-begin");
/*  419 */       if (str == null) {
/*  420 */         str = "#";
/*      */       }
/*      */     }
/*  423 */     return str;
/*      */   }
/*      */   
/*      */   public void setPrompt(String prompt) {
/*  427 */     this.prompt = prompt;
/*  428 */     this.promptLen = (prompt == null ? 0 : stripAnsi(lastLine(prompt)).length());
/*      */   }
/*      */   
/*      */   public String getPrompt() {
/*  432 */     return this.prompt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setEchoCharacter(Character c)
/*      */   {
/*  459 */     this.echoCharacter = c;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public Character getEchoCharacter()
/*      */   {
/*  466 */     return this.echoCharacter;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final boolean resetLine()
/*      */     throws IOException
/*      */   {
/*  475 */     if (this.buf.cursor == 0) {
/*  476 */       return false;
/*      */     }
/*      */     
/*  479 */     StringBuilder killed = new StringBuilder();
/*      */     
/*  481 */     while (this.buf.cursor > 0) {
/*  482 */       char c = this.buf.current();
/*  483 */       if (c == 0) {
/*      */         break;
/*      */       }
/*      */       
/*  487 */       killed.append(c);
/*  488 */       backspace();
/*      */     }
/*      */     
/*  491 */     String copy = killed.reverse().toString();
/*  492 */     this.killRing.addBackwards(copy);
/*      */     
/*  494 */     return true;
/*      */   }
/*      */   
/*      */   int getCursorPosition()
/*      */   {
/*  499 */     return this.promptLen + this.buf.cursor;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String lastLine(String str)
/*      */   {
/*  508 */     if (str == null) return "";
/*  509 */     int last = str.lastIndexOf("\n");
/*      */     
/*  511 */     if (last >= 0) {
/*  512 */       return str.substring(last + 1, str.length());
/*      */     }
/*      */     
/*  515 */     return str;
/*      */   }
/*      */   
/*      */   private String stripAnsi(String str) {
/*  519 */     if (str == null) return "";
/*      */     try {
/*  521 */       ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*  522 */       AnsiOutputStream aos = new AnsiOutputStream(baos);
/*  523 */       aos.write(str.getBytes());
/*  524 */       aos.flush();
/*  525 */       return baos.toString();
/*      */     } catch (IOException e) {}
/*  527 */     return str;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public final boolean setCursorPosition(int position)
/*      */     throws IOException
/*      */   {
/*  535 */     if (position == this.buf.cursor) {
/*  536 */       return true;
/*      */     }
/*      */     
/*  539 */     return moveCursor(position - this.buf.cursor) != 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void setBuffer(String buffer)
/*      */     throws IOException
/*      */   {
/*  550 */     if (buffer.equals(this.buf.buffer.toString())) {
/*  551 */       return;
/*      */     }
/*      */     
/*      */ 
/*  555 */     int sameIndex = 0;
/*      */     
/*  557 */     int i = 0;int l1 = buffer.length();int l2 = this.buf.buffer.length();
/*  558 */     for (; (i < l1) && (i < l2); i++) {
/*  559 */       if (buffer.charAt(i) != this.buf.buffer.charAt(i)) break;
/*  560 */       sameIndex++;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  567 */     int diff = this.buf.cursor - sameIndex;
/*  568 */     if (diff < 0) {
/*  569 */       moveToEnd();
/*  570 */       diff = this.buf.buffer.length() - sameIndex;
/*      */     }
/*      */     
/*  573 */     backspace(diff);
/*  574 */     killLine();
/*  575 */     this.buf.buffer.setLength(sameIndex);
/*  576 */     putString(buffer.substring(sameIndex));
/*      */   }
/*      */   
/*      */   private void setBuffer(CharSequence buffer) throws IOException {
/*  580 */     setBuffer(String.valueOf(buffer));
/*      */   }
/*      */   
/*      */   private void setBufferKeepPos(String buffer) throws IOException {
/*  584 */     int pos = this.buf.cursor;
/*  585 */     setBuffer(buffer);
/*  586 */     setCursorPosition(pos);
/*      */   }
/*      */   
/*      */   private void setBufferKeepPos(CharSequence buffer) throws IOException {
/*  590 */     setBufferKeepPos(String.valueOf(buffer));
/*      */   }
/*      */   
/*      */ 
/*      */   public final void drawLine()
/*      */     throws IOException
/*      */   {
/*  597 */     String prompt = getPrompt();
/*  598 */     if (prompt != null) {
/*  599 */       print(prompt);
/*      */     }
/*      */     
/*  602 */     print(this.buf.buffer.toString());
/*      */     
/*  604 */     if (this.buf.length() != this.buf.cursor) {
/*  605 */       back(this.buf.length() - this.buf.cursor - 1);
/*      */     }
/*      */     
/*  608 */     drawBuffer();
/*      */   }
/*      */   
/*      */ 
/*      */   public final void redrawLine()
/*      */     throws IOException
/*      */   {
/*  615 */     print(13);
/*      */     
/*  617 */     drawLine();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   final String finishBuffer()
/*      */     throws IOException
/*      */   {
/*  626 */     String str = this.buf.buffer.toString();
/*  627 */     String historyLine = str;
/*      */     
/*  629 */     if (this.expandEvents) {
/*      */       try {
/*  631 */         str = expandEvents(str);
/*      */         
/*  633 */         historyLine = str.replace("!", "\\!");
/*      */         
/*  635 */         historyLine = historyLine.replaceAll("^\\^", "\\\\^");
/*      */       } catch (IllegalArgumentException e) {
/*  637 */         Log.error(new Object[] { "Could not expand event", e });
/*  638 */         beep();
/*  639 */         this.buf.clear();
/*  640 */         str = "";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  647 */     if (str.length() > 0) {
/*  648 */       if ((this.mask == null) && (isHistoryEnabled())) {
/*  649 */         this.history.add(historyLine);
/*      */       }
/*      */       else {
/*  652 */         this.mask = null;
/*      */       }
/*      */     }
/*      */     
/*  656 */     this.history.moveToEnd();
/*      */     
/*  658 */     this.buf.buffer.setLength(0);
/*  659 */     this.buf.cursor = 0;
/*      */     
/*  661 */     return str;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected String expandEvents(String str)
/*      */     throws IOException
/*      */   {
/*  669 */     StringBuilder sb = new StringBuilder();
/*  670 */     for (int i = 0; i < str.length(); i++) {
/*  671 */       char c = str.charAt(i);
/*  672 */       switch (c)
/*      */       {
/*      */ 
/*      */ 
/*      */       case '\\': 
/*  677 */         if (i + 1 < str.length()) {
/*  678 */           char nextChar = str.charAt(i + 1);
/*  679 */           if ((nextChar == '!') || ((nextChar == '^') && (i == 0))) {
/*  680 */             c = nextChar;
/*  681 */             i++;
/*      */           }
/*      */         }
/*  684 */         sb.append(c);
/*  685 */         break;
/*      */       case '!': 
/*  687 */         if (i + 1 < str.length()) {
/*  688 */           c = str.charAt(++i);
/*  689 */           boolean neg = false;
/*  690 */           String rep = null;
/*      */           int i1;
/*  692 */           int idx; switch (c) {
/*      */           case '!': 
/*  694 */             if (this.history.size() == 0) {
/*  695 */               throw new IllegalArgumentException("!!: event not found");
/*      */             }
/*  697 */             rep = this.history.get(this.history.index() - 1).toString();
/*  698 */             break;
/*      */           case '#': 
/*  700 */             sb.append(sb.toString());
/*  701 */             break;
/*      */           case '?': 
/*  703 */             i1 = str.indexOf('?', i + 1);
/*  704 */             if (i1 < 0) {
/*  705 */               i1 = str.length();
/*      */             }
/*  707 */             String sc = str.substring(i + 1, i1);
/*  708 */             i = i1;
/*  709 */             idx = searchBackwards(sc);
/*  710 */             if (idx < 0) {
/*  711 */               throw new IllegalArgumentException("!?" + sc + ": event not found");
/*      */             }
/*  713 */             rep = this.history.get(idx).toString();
/*      */             
/*  715 */             break;
/*      */           case '$': 
/*  717 */             if (this.history.size() == 0) {
/*  718 */               throw new IllegalArgumentException("!$: event not found");
/*      */             }
/*  720 */             String previous = this.history.get(this.history.index() - 1).toString().trim();
/*  721 */             int lastSpace = previous.lastIndexOf(' ');
/*  722 */             if (lastSpace != -1) {
/*  723 */               rep = previous.substring(lastSpace + 1);
/*      */             } else {
/*  725 */               rep = previous;
/*      */             }
/*  727 */             break;
/*      */           case '\t': 
/*      */           case ' ': 
/*  730 */             sb.append('!');
/*  731 */             sb.append(c);
/*  732 */             break;
/*      */           case '-': 
/*  734 */             neg = true;
/*  735 */             i++;
/*      */           
/*      */           case '0': 
/*      */           case '1': 
/*      */           case '2': 
/*      */           case '3': 
/*      */           case '4': 
/*      */           case '5': 
/*      */           case '6': 
/*      */           case '7': 
/*      */           case '8': 
/*      */           case '9': 
/*  747 */             i1 = i;
/*  748 */             for (; i < str.length(); i++) {
/*  749 */               c = str.charAt(i);
/*  750 */               if ((c < '0') || (c > '9')) {
/*      */                 break;
/*      */               }
/*      */             }
/*  754 */             idx = 0;
/*      */             try {
/*  756 */               idx = Integer.parseInt(str.substring(i1, i));
/*      */             } catch (NumberFormatException e) {
/*  758 */               throw new IllegalArgumentException((neg ? "!-" : "!") + str.substring(i1, i) + ": event not found");
/*      */             }
/*  760 */             if (neg) {
/*  761 */               if ((idx > 0) && (idx <= this.history.size())) {
/*  762 */                 rep = this.history.get(this.history.index() - idx).toString();
/*      */               } else {
/*  764 */                 throw new IllegalArgumentException((neg ? "!-" : "!") + str.substring(i1, i) + ": event not found");
/*      */               }
/*      */             }
/*  767 */             else if ((idx > this.history.index() - this.history.size()) && (idx <= this.history.index())) {
/*  768 */               rep = this.history.get(idx - 1).toString();
/*      */             } else {
/*  770 */               throw new IllegalArgumentException((neg ? "!-" : "!") + str.substring(i1, i) + ": event not found");
/*      */             }
/*      */             break;
/*      */           case '\n': case '\013': case '\f': case '\r': case '\016': case '\017': case '\020': case '\021': case '\022': case '\023': case '\024': case '\025': case '\026': case '\027': case '\030': case '\031': case '\032': case '\033': case '\034': case '\035': 
/*      */           case '\036': case '\037': case '"': case '%': case '&': case '\'': case '(': case ')': case '*': case '+': case ',': case '.': case '/': case ':': case ';': case '<': case '=': case '>': default: 
/*  775 */             String ss = str.substring(i);
/*  776 */             i = str.length();
/*  777 */             idx = searchBackwards(ss, this.history.index(), true);
/*  778 */             if (idx < 0) {
/*  779 */               throw new IllegalArgumentException("!" + ss + ": event not found");
/*      */             }
/*  781 */             rep = this.history.get(idx).toString();
/*      */           }
/*      */           
/*      */           
/*  785 */           if (rep != null) {
/*  786 */             sb.append(rep);
/*      */           }
/*      */         } else {
/*  789 */           sb.append(c);
/*      */         }
/*  791 */         break;
/*      */       case '^': 
/*  793 */         if (i == 0) {
/*  794 */           int i1 = str.indexOf('^', i + 1);
/*  795 */           int i2 = str.indexOf('^', i1 + 1);
/*  796 */           if (i2 < 0) {
/*  797 */             i2 = str.length();
/*      */           }
/*  799 */           if ((i1 > 0) && (i2 > 0)) {
/*  800 */             String s1 = str.substring(i + 1, i1);
/*  801 */             String s2 = str.substring(i1 + 1, i2);
/*  802 */             String s = this.history.get(this.history.index() - 1).toString().replace(s1, s2);
/*  803 */             sb.append(s);
/*  804 */             i = i2 + 1;
/*  805 */             continue;
/*      */           }
/*      */         }
/*  808 */         sb.append(c);
/*  809 */         break;
/*      */       default: 
/*  811 */         sb.append(c);
/*      */       }
/*      */       
/*      */     }
/*  815 */     String result = sb.toString();
/*  816 */     if (!str.equals(result)) {
/*  817 */       print(result);
/*  818 */       println();
/*  819 */       flush();
/*      */     }
/*  821 */     return result;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public final void putString(CharSequence str)
/*      */     throws IOException
/*      */   {
/*  829 */     this.buf.write(str);
/*  830 */     if (this.mask == null)
/*      */     {
/*  832 */       print(str);
/*  833 */     } else if (this.mask.charValue() != 0)
/*      */     {
/*      */ 
/*  836 */       print(this.mask.charValue(), str.length());
/*      */     }
/*  838 */     drawBuffer();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void drawBuffer(int clear)
/*      */     throws IOException
/*      */   {
/*  849 */     if ((this.buf.cursor != this.buf.length()) || (clear != 0))
/*      */     {
/*  851 */       char[] chars = this.buf.buffer.substring(this.buf.cursor).toCharArray();
/*  852 */       if (this.mask != null) {
/*  853 */         Arrays.fill(chars, this.mask.charValue());
/*      */       }
/*  855 */       if (this.terminal.hasWeirdWrap())
/*      */       {
/*  857 */         int width = this.terminal.getWidth();
/*  858 */         int pos = getCursorPosition();
/*  859 */         for (int i = 0; i < chars.length; i++) {
/*  860 */           print(chars[i]);
/*  861 */           if ((pos + i + 1) % width == 0) {
/*  862 */             print(32);
/*  863 */             print(13);
/*      */           }
/*      */         }
/*      */       } else {
/*  867 */         print(chars);
/*      */       }
/*  869 */       clearAhead(clear, chars.length);
/*  870 */       if (this.terminal.isAnsiSupported()) {
/*  871 */         if (chars.length > 0) {
/*  872 */           back(chars.length);
/*      */         }
/*      */       } else {
/*  875 */         back(chars.length);
/*      */       }
/*      */     }
/*  878 */     if (this.terminal.hasWeirdWrap()) {
/*  879 */       int width = this.terminal.getWidth();
/*      */       
/*      */ 
/*      */ 
/*  883 */       if ((getCursorPosition() > 0) && (getCursorPosition() % width == 0) && (this.buf.cursor == this.buf.length()) && (clear == 0))
/*      */       {
/*      */ 
/*      */ 
/*  887 */         print(32);
/*  888 */         print(13);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void drawBuffer()
/*      */     throws IOException
/*      */   {
/*  898 */     drawBuffer(0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void clearAhead(int num, int delta)
/*      */     throws IOException
/*      */   {
/*  910 */     if (num == 0) {
/*  911 */       return;
/*      */     }
/*      */     
/*  914 */     if (this.terminal.isAnsiSupported()) {
/*  915 */       int width = this.terminal.getWidth();
/*  916 */       int screenCursorCol = getCursorPosition() + delta;
/*      */       
/*  918 */       printAnsiSequence("K");
/*      */       
/*  920 */       int curCol = screenCursorCol % width;
/*  921 */       int endCol = (screenCursorCol + num - 1) % width;
/*  922 */       int lines = num / width;
/*  923 */       if (endCol < curCol) lines++;
/*  924 */       for (int i = 0; i < lines; i++) {
/*  925 */         printAnsiSequence("B");
/*  926 */         printAnsiSequence("2K");
/*      */       }
/*  928 */       for (int i = 0; i < lines; i++) {
/*  929 */         printAnsiSequence("A");
/*      */       }
/*  931 */       return;
/*      */     }
/*      */     
/*      */ 
/*  935 */     print(' ', num);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  942 */     back(num);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void back(int num)
/*      */     throws IOException
/*      */   {
/*  951 */     if (num == 0) return;
/*  952 */     if (this.terminal.isAnsiSupported()) {
/*  953 */       int width = getTerminal().getWidth();
/*  954 */       int cursor = getCursorPosition();
/*  955 */       int realCursor = cursor + num;
/*  956 */       int realCol = realCursor % width;
/*  957 */       int newCol = cursor % width;
/*  958 */       int moveup = num / width;
/*  959 */       int delta = realCol - newCol;
/*  960 */       if (delta < 0) moveup++;
/*  961 */       if (moveup > 0) {
/*  962 */         printAnsiSequence(moveup + "A");
/*      */       }
/*  964 */       printAnsiSequence(1 + newCol + "G");
/*  965 */       return;
/*      */     }
/*  967 */     print('\b', num);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void flush()
/*      */     throws IOException
/*      */   {
/*  976 */     this.out.flush();
/*      */   }
/*      */   
/*      */   private int backspaceAll() throws IOException {
/*  980 */     return backspace(Integer.MAX_VALUE);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private int backspace(int num)
/*      */     throws IOException
/*      */   {
/*  989 */     if (this.buf.cursor == 0) {
/*  990 */       return 0;
/*      */     }
/*      */     
/*  993 */     int count = 0;
/*      */     
/*  995 */     int termwidth = getTerminal().getWidth();
/*  996 */     int lines = getCursorPosition() / termwidth;
/*  997 */     count = moveCursor(-1 * num) * -1;
/*  998 */     this.buf.buffer.delete(this.buf.cursor, this.buf.cursor + count);
/*  999 */     if ((getCursorPosition() / termwidth != lines) && 
/* 1000 */       (this.terminal.isAnsiSupported()))
/*      */     {
/* 1002 */       printAnsiSequence("K");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1022 */     drawBuffer(count);
/*      */     
/* 1024 */     return count;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean backspace()
/*      */     throws IOException
/*      */   {
/* 1033 */     return backspace(1) == 1;
/*      */   }
/*      */   
/*      */   protected boolean moveToEnd() throws IOException {
/* 1037 */     if (this.buf.cursor == this.buf.length()) {
/* 1038 */       return true;
/*      */     }
/* 1040 */     return moveCursor(this.buf.length() - this.buf.cursor) > 0;
/*      */   }
/*      */   
/*      */ 
/*      */   private boolean deleteCurrentCharacter()
/*      */     throws IOException
/*      */   {
/* 1047 */     if ((this.buf.length() == 0) || (this.buf.cursor == this.buf.length())) {
/* 1048 */       return false;
/*      */     }
/*      */     
/* 1051 */     this.buf.buffer.deleteCharAt(this.buf.cursor);
/* 1052 */     drawBuffer(1);
/* 1053 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private Operation viDeleteChangeYankToRemap(Operation op)
/*      */   {
/* 1066 */     switch (op) {
/*      */     case VI_EOF_MAYBE: 
/*      */     case ABORT: 
/*      */     case BACKWARD_CHAR: 
/*      */     case FORWARD_CHAR: 
/*      */     case END_OF_LINE: 
/*      */     case VI_MATCH: 
/*      */     case VI_BEGNNING_OF_LINE_OR_ARG_DIGIT: 
/*      */     case VI_ARG_DIGIT: 
/*      */     case VI_PREV_WORD: 
/*      */     case VI_END_WORD: 
/*      */     case VI_CHAR_SEARCH: 
/*      */     case VI_NEXT_WORD: 
/*      */     case VI_FIRST_PRINT: 
/*      */     case VI_GOTO_MARK: 
/*      */     case VI_COLUMN: 
/*      */     case VI_DELETE_TO: 
/*      */     case VI_YANK_TO: 
/*      */     case VI_CHANGE_TO: 
/* 1085 */       return op;
/*      */     }
/*      */     
/* 1088 */     return Operation.VI_MOVEMENT_MODE;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean viRubout(int count)
/*      */     throws IOException
/*      */   {
/* 1099 */     boolean ok = true;
/* 1100 */     for (int i = 0; (ok) && (i < count); i++) {
/* 1101 */       ok = backspace();
/*      */     }
/* 1103 */     return ok;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean viDelete(int count)
/*      */     throws IOException
/*      */   {
/* 1114 */     boolean ok = true;
/* 1115 */     for (int i = 0; (ok) && (i < count); i++) {
/* 1116 */       ok = deleteCurrentCharacter();
/*      */     }
/* 1118 */     return ok;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean viChangeCase(int count)
/*      */     throws IOException
/*      */   {
/* 1131 */     boolean ok = true;
/* 1132 */     for (int i = 0; (ok) && (i < count); i++)
/*      */     {
/* 1134 */       ok = this.buf.cursor < this.buf.buffer.length();
/* 1135 */       if (ok) {
/* 1136 */         char ch = this.buf.buffer.charAt(this.buf.cursor);
/* 1137 */         if (Character.isUpperCase(ch)) {
/* 1138 */           ch = Character.toLowerCase(ch);
/*      */         }
/* 1140 */         else if (Character.isLowerCase(ch)) {
/* 1141 */           ch = Character.toUpperCase(ch);
/*      */         }
/* 1143 */         this.buf.buffer.setCharAt(this.buf.cursor, ch);
/* 1144 */         drawBuffer(1);
/* 1145 */         moveCursor(1);
/*      */       }
/*      */     }
/* 1148 */     return ok;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean viChangeChar(int count, int c)
/*      */     throws IOException
/*      */   {
/* 1161 */     if ((c < 0) || (c == 27) || (c == 3)) {
/* 1162 */       return true;
/*      */     }
/*      */     
/* 1165 */     boolean ok = true;
/* 1166 */     for (int i = 0; (ok) && (i < count); i++) {
/* 1167 */       ok = this.buf.cursor < this.buf.buffer.length();
/* 1168 */       if (ok) {
/* 1169 */         this.buf.buffer.setCharAt(this.buf.cursor, (char)c);
/* 1170 */         drawBuffer(1);
/* 1171 */         if (i < count - 1) {
/* 1172 */           moveCursor(1);
/*      */         }
/*      */       }
/*      */     }
/* 1176 */     return ok;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean viPreviousWord(int count)
/*      */     throws IOException
/*      */   {
/* 1190 */     boolean ok = true;
/* 1191 */     if (this.buf.cursor == 0) {
/* 1192 */       return false;
/*      */     }
/*      */     
/* 1195 */     int pos = this.buf.cursor - 1;
/* 1196 */     for (int i = 0; (pos > 0) && (i < count); i++)
/*      */     {
/* 1198 */       while ((pos > 0) && (isWhitespace(this.buf.buffer.charAt(pos)))) {
/* 1199 */         pos--;
/*      */       }
/*      */       
/* 1202 */       while ((pos > 0) && (!isDelimiter(this.buf.buffer.charAt(pos - 1)))) {
/* 1203 */         pos--;
/*      */       }
/*      */       
/* 1206 */       if ((pos > 0) && (i < count - 1)) {
/* 1207 */         pos--;
/*      */       }
/*      */     }
/* 1210 */     setCursorPosition(pos);
/* 1211 */     return ok;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean viDeleteTo(int startPos, int endPos, boolean isChange)
/*      */     throws IOException
/*      */   {
/* 1226 */     if (startPos == endPos) {
/* 1227 */       return true;
/*      */     }
/*      */     
/* 1230 */     if (endPos < startPos) {
/* 1231 */       int tmp = endPos;
/* 1232 */       endPos = startPos;
/* 1233 */       startPos = tmp;
/*      */     }
/*      */     
/* 1236 */     setCursorPosition(startPos);
/* 1237 */     this.buf.cursor = startPos;
/* 1238 */     this.buf.buffer.delete(startPos, endPos);
/* 1239 */     drawBuffer(endPos - startPos);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1246 */     if ((!isChange) && (startPos > 0) && (startPos == this.buf.length())) {
/* 1247 */       moveCursor(-1);
/*      */     }
/* 1249 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean viYankTo(int startPos, int endPos)
/*      */     throws IOException
/*      */   {
/* 1263 */     int cursorPos = startPos;
/*      */     
/* 1265 */     if (endPos < startPos) {
/* 1266 */       int tmp = endPos;
/* 1267 */       endPos = startPos;
/* 1268 */       startPos = tmp;
/*      */     }
/*      */     
/* 1271 */     if (startPos == endPos) {
/* 1272 */       this.yankBuffer = "";
/* 1273 */       return true;
/*      */     }
/*      */     
/* 1276 */     this.yankBuffer = this.buf.buffer.substring(startPos, endPos);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1282 */     setCursorPosition(cursorPos);
/* 1283 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean viPut(int count)
/*      */     throws IOException
/*      */   {
/* 1295 */     if (this.yankBuffer.length() == 0) {
/* 1296 */       return true;
/*      */     }
/* 1298 */     if (this.buf.cursor < this.buf.buffer.length()) {
/* 1299 */       moveCursor(1);
/*      */     }
/* 1301 */     for (int i = 0; i < count; i++) {
/* 1302 */       putString(this.yankBuffer);
/*      */     }
/* 1304 */     moveCursor(-1);
/* 1305 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean viCharSearch(int count, int invokeChar, int ch)
/*      */     throws IOException
/*      */   {
/* 1317 */     if ((ch < 0) || (invokeChar < 0)) {
/* 1318 */       return false;
/*      */     }
/*      */     
/* 1321 */     char searchChar = (char)ch;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1334 */     if ((invokeChar == 59) || (invokeChar == 44))
/*      */     {
/* 1336 */       if (this.charSearchChar == 0) {
/* 1337 */         return false;
/*      */       }
/*      */       
/*      */ 
/* 1341 */       if ((this.charSearchLastInvokeChar == ';') || (this.charSearchLastInvokeChar == ',')) {
/* 1342 */         if (this.charSearchLastInvokeChar != invokeChar) {
/* 1343 */           this.charSearchFirstInvokeChar = switchCase(this.charSearchFirstInvokeChar);
/*      */         }
/*      */         
/*      */       }
/* 1347 */       else if (invokeChar == 44) {
/* 1348 */         this.charSearchFirstInvokeChar = switchCase(this.charSearchFirstInvokeChar);
/*      */       }
/*      */       
/*      */ 
/* 1352 */       searchChar = this.charSearchChar;
/*      */     }
/*      */     else {
/* 1355 */       this.charSearchChar = searchChar;
/* 1356 */       this.charSearchFirstInvokeChar = ((char)invokeChar);
/*      */     }
/*      */     
/* 1359 */     this.charSearchLastInvokeChar = ((char)invokeChar);
/*      */     
/* 1361 */     boolean isForward = Character.isLowerCase(this.charSearchFirstInvokeChar);
/* 1362 */     boolean stopBefore = Character.toLowerCase(this.charSearchFirstInvokeChar) == 't';
/*      */     
/* 1364 */     boolean ok = false;
/*      */     
/* 1366 */     if (isForward) {
/* 1367 */       while (count-- > 0) {
/* 1368 */         int pos = this.buf.cursor + 1;
/* 1369 */         while (pos < this.buf.buffer.length()) {
/* 1370 */           if (this.buf.buffer.charAt(pos) == searchChar) {
/* 1371 */             setCursorPosition(pos);
/* 1372 */             ok = true;
/* 1373 */             break;
/*      */           }
/* 1375 */           pos++;
/*      */         }
/*      */       }
/*      */       
/* 1379 */       if (ok) {
/* 1380 */         if (stopBefore) {
/* 1381 */           moveCursor(-1);
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1389 */         if (isInViMoveOperationState()) {
/* 1390 */           moveCursor(1);
/*      */         }
/*      */       }
/*      */     }
/*      */     else {
/* 1395 */       while (count-- > 0) {
/* 1396 */         int pos = this.buf.cursor - 1;
/* 1397 */         while (pos >= 0) {
/* 1398 */           if (this.buf.buffer.charAt(pos) == searchChar) {
/* 1399 */             setCursorPosition(pos);
/* 1400 */             ok = true;
/* 1401 */             break;
/*      */           }
/* 1403 */           pos--;
/*      */         }
/*      */       }
/*      */       
/* 1407 */       if ((ok) && (stopBefore)) {
/* 1408 */         moveCursor(1);
/*      */       }
/*      */     }
/* 1411 */     return ok;
/*      */   }
/*      */   
/*      */   private char switchCase(char ch) {
/* 1415 */     if (Character.isUpperCase(ch)) {
/* 1416 */       return Character.toLowerCase(ch);
/*      */     }
/* 1418 */     return Character.toUpperCase(ch);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private final boolean isInViMoveOperationState()
/*      */   {
/* 1426 */     return (this.state == State.VI_CHANGE_TO) || (this.state == State.VI_DELETE_TO) || (this.state == State.VI_YANK_TO);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean viNextWord(int count)
/*      */     throws IOException
/*      */   {
/* 1441 */     int pos = this.buf.cursor;
/* 1442 */     int end = this.buf.buffer.length();
/*      */     
/* 1444 */     for (int i = 0; (pos < end) && (i < count); i++)
/*      */     {
/* 1446 */       while ((pos < end) && (!isDelimiter(this.buf.buffer.charAt(pos)))) {
/* 1447 */         pos++;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1456 */       if ((i < count - 1) || (this.state != State.VI_CHANGE_TO)) {
/* 1457 */         while ((pos < end) && (isDelimiter(this.buf.buffer.charAt(pos)))) {
/* 1458 */           pos++;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1463 */     setCursorPosition(pos);
/* 1464 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean viEndWord(int count)
/*      */     throws IOException
/*      */   {
/* 1479 */     int pos = this.buf.cursor;
/* 1480 */     int end = this.buf.buffer.length();
/*      */     
/* 1482 */     for (int i = 0; (pos < end) && (i < count); i++) {
/* 1483 */       if ((pos < end - 1) && (!isDelimiter(this.buf.buffer.charAt(pos))) && (isDelimiter(this.buf.buffer.charAt(pos + 1))))
/*      */       {
/*      */ 
/* 1486 */         pos++;
/*      */       }
/*      */       
/*      */ 
/* 1490 */       while ((pos < end) && (isDelimiter(this.buf.buffer.charAt(pos)))) {
/* 1491 */         pos++;
/*      */       }
/*      */       
/* 1494 */       while ((pos < end - 1) && (!isDelimiter(this.buf.buffer.charAt(pos + 1)))) {
/* 1495 */         pos++;
/*      */       }
/*      */     }
/* 1498 */     setCursorPosition(pos);
/* 1499 */     return true;
/*      */   }
/*      */   
/*      */   private boolean previousWord() throws IOException {
/* 1503 */     while ((isDelimiter(this.buf.current())) && (moveCursor(-1) != 0)) {}
/*      */     
/*      */ 
/*      */ 
/* 1507 */     while ((!isDelimiter(this.buf.current())) && (moveCursor(-1) != 0)) {}
/*      */     
/*      */ 
/*      */ 
/* 1511 */     return true;
/*      */   }
/*      */   
/*      */   private boolean nextWord() throws IOException {
/* 1515 */     while ((isDelimiter(this.buf.nextChar())) && (moveCursor(1) != 0)) {}
/*      */     
/*      */ 
/*      */ 
/* 1519 */     while ((!isDelimiter(this.buf.nextChar())) && (moveCursor(1) != 0)) {}
/*      */     
/*      */ 
/*      */ 
/* 1523 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean unixWordRubout(int count)
/*      */     throws IOException
/*      */   {
/* 1537 */     boolean success = true;
/* 1538 */     StringBuilder killed = new StringBuilder();
/* 1540 */     for (; 
/* 1540 */         count > 0; count--) {
/* 1541 */       if (this.buf.cursor == 0) {
/* 1542 */         success = false;
/* 1543 */         break;
/*      */       }
/*      */       
/* 1546 */       while (isWhitespace(this.buf.current())) {
/* 1547 */         char c = this.buf.current();
/* 1548 */         if (c == 0) {
/*      */           break;
/*      */         }
/*      */         
/* 1552 */         killed.append(c);
/* 1553 */         backspace();
/*      */       }
/*      */       
/* 1556 */       while (!isWhitespace(this.buf.current())) {
/* 1557 */         char c = this.buf.current();
/* 1558 */         if (c == 0) {
/*      */           break;
/*      */         }
/*      */         
/* 1562 */         killed.append(c);
/* 1563 */         backspace();
/*      */       }
/*      */     }
/*      */     
/* 1567 */     String copy = killed.reverse().toString();
/* 1568 */     this.killRing.addBackwards(copy);
/*      */     
/* 1570 */     return success;
/*      */   }
/*      */   
/*      */   private String insertComment(boolean isViMode) throws IOException {
/* 1574 */     String comment = getCommentBegin();
/* 1575 */     setCursorPosition(0);
/* 1576 */     putString(comment);
/* 1577 */     if (isViMode) {
/* 1578 */       this.consoleKeys.setKeyMap("vi-insert");
/*      */     }
/* 1580 */     return accept();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean insert(int count, CharSequence str)
/*      */     throws IOException
/*      */   {
/* 1594 */     for (int i = 0; i < count; i++) {
/* 1595 */       this.buf.write(str);
/* 1596 */       if (this.mask == null)
/*      */       {
/* 1598 */         print(str);
/* 1599 */       } else if (this.mask.charValue() != 0)
/*      */       {
/*      */ 
/* 1602 */         print(this.mask.charValue(), str.length());
/*      */       }
/*      */     }
/* 1605 */     drawBuffer();
/* 1606 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private int viSearch(char searchChar)
/*      */     throws IOException
/*      */   {
/* 1614 */     boolean isForward = searchChar == '/';
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1620 */     CursorBuffer origBuffer = this.buf.copy();
/*      */     
/*      */ 
/* 1623 */     setCursorPosition(0);
/* 1624 */     killLine();
/*      */     
/*      */ 
/* 1627 */     putString(Character.toString(searchChar));
/* 1628 */     flush();
/*      */     
/* 1630 */     boolean isAborted = false;
/* 1631 */     boolean isComplete = false;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1637 */     int ch = -1;
/* 1638 */     while ((!isAborted) && (!isComplete) && ((ch = readCharacter()) != -1)) {
/* 1639 */       switch (ch)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */       case 27: 
/* 1645 */         isAborted = true;
/* 1646 */         break;
/*      */       case 8: 
/*      */       case 127: 
/* 1649 */         backspace();
/*      */         
/*      */ 
/*      */ 
/* 1653 */         if (this.buf.cursor == 0) {
/* 1654 */           isAborted = true;
/*      */         }
/*      */         break;
/*      */       case 10: 
/*      */       case 13: 
/* 1659 */         isComplete = true;
/* 1660 */         break;
/*      */       default: 
/* 1662 */         putString(Character.toString((char)ch));
/*      */       }
/*      */       
/* 1665 */       flush();
/*      */     }
/*      */     
/*      */ 
/* 1669 */     if ((ch == -1) || (isAborted)) {
/* 1670 */       setCursorPosition(0);
/* 1671 */       killLine();
/* 1672 */       putString(origBuffer.buffer);
/* 1673 */       setCursorPosition(origBuffer.cursor);
/* 1674 */       return -1;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1681 */     String searchTerm = this.buf.buffer.substring(1);
/* 1682 */     int idx = -1;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1690 */     int end = this.history.index();
/* 1691 */     int start = end <= this.history.size() ? 0 : end - this.history.size();
/*      */     
/* 1693 */     if (isForward) {
/* 1694 */       for (int i = start; i < end; i++) {
/* 1695 */         if (this.history.get(i).toString().contains(searchTerm)) {
/* 1696 */           idx = i;
/* 1697 */           break;
/*      */         }
/*      */         
/*      */       }
/*      */     } else {
/* 1702 */       for (int i = end - 1; i >= start; i--) {
/* 1703 */         if (this.history.get(i).toString().contains(searchTerm)) {
/* 1704 */           idx = i;
/* 1705 */           break;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1714 */     if (idx == -1) {
/* 1715 */       setCursorPosition(0);
/* 1716 */       killLine();
/* 1717 */       putString(origBuffer.buffer);
/* 1718 */       setCursorPosition(0);
/* 1719 */       return -1;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1725 */     setCursorPosition(0);
/* 1726 */     killLine();
/* 1727 */     putString(this.history.get(idx));
/* 1728 */     setCursorPosition(0);
/* 1729 */     flush();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1736 */     isComplete = false;
/* 1737 */     while ((!isComplete) && ((ch = readCharacter()) != -1)) {
/* 1738 */       boolean forward = isForward;
/* 1739 */       switch (ch) {
/*      */       case 80: case 112: 
/* 1741 */         forward = !isForward;
/*      */       case 78: 
/*      */       case 110: 
/* 1744 */         boolean isMatch = false;
/* 1745 */         if (forward) {
/* 1746 */           for (int i = idx + 1; (!isMatch) && (i < end); i++) {
/* 1747 */             if (this.history.get(i).toString().contains(searchTerm)) {
/* 1748 */               idx = i;
/* 1749 */               isMatch = true;
/*      */             }
/*      */             
/*      */           }
/*      */         } else {
/* 1754 */           for (int i = idx - 1; (!isMatch) && (i >= start); i--) {
/* 1755 */             if (this.history.get(i).toString().contains(searchTerm)) {
/* 1756 */               idx = i;
/* 1757 */               isMatch = true;
/*      */             }
/*      */           }
/*      */         }
/* 1761 */         if (isMatch) {
/* 1762 */           setCursorPosition(0);
/* 1763 */           killLine();
/* 1764 */           putString(this.history.get(idx));
/* 1765 */           setCursorPosition(0);
/*      */         }
/*      */         break;
/*      */       default: 
/* 1769 */         isComplete = true;
/*      */       }
/* 1771 */       flush();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1777 */     return ch;
/*      */   }
/*      */   
/*      */   public void setParenBlinkTimeout(int timeout) {
/* 1781 */     this.parenBlinkTimeout = timeout;
/*      */   }
/*      */   
/*      */   private void insertClose(String s) throws IOException {
/* 1785 */     putString(s);
/* 1786 */     int closePosition = this.buf.cursor;
/*      */     
/* 1788 */     moveCursor(-1);
/* 1789 */     viMatch();
/*      */     
/*      */ 
/* 1792 */     if (this.in.isNonBlockingEnabled()) {
/* 1793 */       this.in.peek(this.parenBlinkTimeout);
/*      */     }
/*      */     
/* 1796 */     setCursorPosition(closePosition);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean viMatch()
/*      */     throws IOException
/*      */   {
/* 1808 */     int pos = this.buf.cursor;
/*      */     
/* 1810 */     if (pos == this.buf.length()) {
/* 1811 */       return false;
/*      */     }
/*      */     
/* 1814 */     int type = getBracketType(this.buf.buffer.charAt(pos));
/* 1815 */     int move = type < 0 ? -1 : 1;
/* 1816 */     int count = 1;
/*      */     
/* 1818 */     if (type == 0) {
/* 1819 */       return false;
/*      */     }
/* 1821 */     while (count > 0) {
/* 1822 */       pos += move;
/*      */       
/*      */ 
/* 1825 */       if ((pos < 0) || (pos >= this.buf.buffer.length())) {
/* 1826 */         return false;
/*      */       }
/*      */       
/* 1829 */       int curType = getBracketType(this.buf.buffer.charAt(pos));
/* 1830 */       if (curType == type) {
/* 1831 */         count++;
/*      */       }
/* 1833 */       else if (curType == -type) {
/* 1834 */         count--;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1842 */     if ((move > 0) && (isInViMoveOperationState())) {
/* 1843 */       pos++;
/*      */     }
/* 1845 */     setCursorPosition(pos);
/* 1846 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int getBracketType(char ch)
/*      */   {
/* 1857 */     switch (ch) {
/* 1858 */     case '[':  return 1;
/* 1859 */     case ']':  return -1;
/* 1860 */     case '{':  return 2;
/* 1861 */     case '}':  return -2;
/* 1862 */     case '(':  return 3;
/* 1863 */     case ')':  return -3;
/*      */     }
/* 1865 */     return 0;
/*      */   }
/*      */   
/*      */   private boolean deletePreviousWord() throws IOException
/*      */   {
/* 1870 */     StringBuilder killed = new StringBuilder();
/*      */     
/*      */     char c;
/* 1873 */     while ((isDelimiter(c = this.buf.current())) && 
/* 1874 */       (c != 0))
/*      */     {
/*      */ 
/*      */ 
/* 1878 */       killed.append(c);
/* 1879 */       backspace();
/*      */     }
/*      */     
/* 1882 */     while ((!isDelimiter(c = this.buf.current())) && 
/* 1883 */       (c != 0))
/*      */     {
/*      */ 
/*      */ 
/* 1887 */       killed.append(c);
/* 1888 */       backspace();
/*      */     }
/*      */     
/* 1891 */     String copy = killed.reverse().toString();
/* 1892 */     this.killRing.addBackwards(copy);
/* 1893 */     return true;
/*      */   }
/*      */   
/*      */   private boolean deleteNextWord() throws IOException {
/* 1897 */     StringBuilder killed = new StringBuilder();
/*      */     
/*      */     char c;
/* 1900 */     while ((isDelimiter(c = this.buf.nextChar())) && 
/* 1901 */       (c != 0))
/*      */     {
/*      */ 
/* 1904 */       killed.append(c);
/* 1905 */       delete();
/*      */     }
/*      */     
/* 1908 */     while ((!isDelimiter(c = this.buf.nextChar())) && 
/* 1909 */       (c != 0))
/*      */     {
/*      */ 
/* 1912 */       killed.append(c);
/* 1913 */       delete();
/*      */     }
/*      */     
/* 1916 */     String copy = killed.toString();
/* 1917 */     this.killRing.add(copy);
/*      */     
/* 1919 */     return true;
/*      */   }
/*      */   
/*      */   private boolean capitalizeWord() throws IOException {
/* 1923 */     boolean first = true;
/* 1924 */     int i = 1;
/*      */     char c;
/* 1926 */     while ((this.buf.cursor + i - 1 < this.buf.length()) && (!isDelimiter(c = this.buf.buffer.charAt(this.buf.cursor + i - 1)))) {
/* 1927 */       this.buf.buffer.setCharAt(this.buf.cursor + i - 1, first ? Character.toUpperCase(c) : Character.toLowerCase(c));
/* 1928 */       first = false;
/* 1929 */       i++;
/*      */     }
/* 1931 */     drawBuffer();
/* 1932 */     moveCursor(i - 1);
/* 1933 */     return true;
/*      */   }
/*      */   
/*      */   private boolean upCaseWord() throws IOException {
/* 1937 */     int i = 1;
/*      */     char c;
/* 1939 */     while ((this.buf.cursor + i - 1 < this.buf.length()) && (!isDelimiter(c = this.buf.buffer.charAt(this.buf.cursor + i - 1)))) {
/* 1940 */       this.buf.buffer.setCharAt(this.buf.cursor + i - 1, Character.toUpperCase(c));
/* 1941 */       i++;
/*      */     }
/* 1943 */     drawBuffer();
/* 1944 */     moveCursor(i - 1);
/* 1945 */     return true;
/*      */   }
/*      */   
/*      */   private boolean downCaseWord() throws IOException {
/* 1949 */     int i = 1;
/*      */     char c;
/* 1951 */     while ((this.buf.cursor + i - 1 < this.buf.length()) && (!isDelimiter(c = this.buf.buffer.charAt(this.buf.cursor + i - 1)))) {
/* 1952 */       this.buf.buffer.setCharAt(this.buf.cursor + i - 1, Character.toLowerCase(c));
/* 1953 */       i++;
/*      */     }
/* 1955 */     drawBuffer();
/* 1956 */     moveCursor(i - 1);
/* 1957 */     return true;
/*      */   }
/*      */   
/*      */   private boolean transposeChars(int count)
/*      */     throws IOException
/*      */   {
/* 1971 */     for (; 
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1971 */         count > 0; count--) {
/* 1972 */       if ((this.buf.cursor == 0) || (this.buf.cursor == this.buf.buffer.length())) {
/* 1973 */         return false;
/*      */       }
/*      */       
/* 1976 */       int first = this.buf.cursor - 1;
/* 1977 */       int second = this.buf.cursor;
/*      */       
/* 1979 */       char tmp = this.buf.buffer.charAt(first);
/* 1980 */       this.buf.buffer.setCharAt(first, this.buf.buffer.charAt(second));
/* 1981 */       this.buf.buffer.setCharAt(second, tmp);
/*      */       
/*      */ 
/* 1984 */       moveInternal(-1);
/* 1985 */       drawBuffer();
/* 1986 */       moveInternal(2);
/*      */     }
/*      */     
/* 1989 */     return true;
/*      */   }
/*      */   
/*      */   public boolean isKeyMap(String name)
/*      */   {
/* 1994 */     KeyMap map = this.consoleKeys.getKeys();
/* 1995 */     KeyMap mapByName = (KeyMap)this.consoleKeys.getKeyMaps().get(name);
/*      */     
/* 1997 */     if (mapByName == null) {
/* 1998 */       return false;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 2004 */     return map == mapByName;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String accept()
/*      */     throws IOException
/*      */   {
/* 2016 */     moveToEnd();
/* 2017 */     println();
/* 2018 */     flush();
/* 2019 */     return finishBuffer();
/*      */   }
/*      */   
/*      */   private void abort() throws IOException {
/* 2023 */     beep();
/* 2024 */     this.buf.clear();
/* 2025 */     println();
/* 2026 */     redrawLine();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int moveCursor(int num)
/*      */     throws IOException
/*      */   {
/* 2036 */     int where = num;
/*      */     
/* 2038 */     if ((this.buf.cursor == 0) && (where <= 0)) {
/* 2039 */       return 0;
/*      */     }
/*      */     
/* 2042 */     if ((this.buf.cursor == this.buf.buffer.length()) && (where >= 0)) {
/* 2043 */       return 0;
/*      */     }
/*      */     
/* 2046 */     if (this.buf.cursor + where < 0) {
/* 2047 */       where = -this.buf.cursor;
/*      */     }
/* 2049 */     else if (this.buf.cursor + where > this.buf.buffer.length()) {
/* 2050 */       where = this.buf.buffer.length() - this.buf.cursor;
/*      */     }
/*      */     
/* 2053 */     moveInternal(where);
/*      */     
/* 2055 */     return where;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void moveInternal(int where)
/*      */     throws IOException
/*      */   {
/* 2066 */     this.buf.cursor += where;
/*      */     
/* 2068 */     if (this.terminal.isAnsiSupported()) {
/* 2069 */       if (where < 0) {
/* 2070 */         back(Math.abs(where));
/*      */       } else {
/* 2072 */         int width = getTerminal().getWidth();
/* 2073 */         int cursor = getCursorPosition();
/* 2074 */         int oldLine = (cursor - where) / width;
/* 2075 */         int newLine = cursor / width;
/* 2076 */         if (newLine > oldLine) {
/* 2077 */           printAnsiSequence(newLine - oldLine + "B");
/*      */         }
/* 2079 */         printAnsiSequence(1 + cursor % width + "G");
/*      */       }
/*      */       
/* 2082 */       return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 2087 */     if (where < 0) {
/* 2088 */       int len = 0;
/* 2089 */       for (int i = this.buf.cursor; i < this.buf.cursor - where; i++) {
/* 2090 */         if (this.buf.buffer.charAt(i) == '\t') {
/* 2091 */           len += 4;
/*      */         }
/*      */         else {
/* 2094 */           len++;
/*      */         }
/*      */       }
/*      */       
/* 2098 */       char[] chars = new char[len];
/* 2099 */       Arrays.fill(chars, '\b');
/* 2100 */       this.out.write(chars);
/*      */       
/* 2102 */       return;
/*      */     }
/* 2104 */     if (this.buf.cursor == 0)
/*      */       return;
/*      */     char c;
/* 2107 */     if (this.mask != null) {
/* 2108 */       c = this.mask.charValue();
/*      */     }
/*      */     else {
/* 2111 */       print(this.buf.buffer.substring(this.buf.cursor - where, this.buf.cursor).toCharArray()); return;
/*      */     }
/*      */     
/*      */     char c;
/*      */     
/* 2116 */     if (this.mask.charValue() == 0) {
/* 2117 */       return;
/*      */     }
/*      */     
/* 2120 */     print(c, Math.abs(where));
/*      */   }
/*      */   
/*      */ 
/*      */   public final boolean replace(int num, String replacement)
/*      */   {
/* 2126 */     this.buf.buffer.replace(this.buf.cursor - num, this.buf.cursor, replacement);
/*      */     try {
/* 2128 */       moveCursor(-num);
/* 2129 */       drawBuffer(Math.max(0, num - replacement.length()));
/* 2130 */       moveCursor(replacement.length());
/*      */     }
/*      */     catch (IOException e) {
/* 2133 */       e.printStackTrace();
/* 2134 */       return false;
/*      */     }
/* 2136 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public final int readCharacter()
/*      */     throws IOException
/*      */   {
/* 2145 */     int c = this.reader.read();
/* 2146 */     if (c >= 0) {
/* 2147 */       Log.trace(new Object[] { "Keystroke: ", Integer.valueOf(c) });
/*      */       
/* 2149 */       if (this.terminal.isSupported()) {
/* 2150 */         clearEcho(c);
/*      */       }
/*      */     }
/* 2153 */     return c;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private int clearEcho(int c)
/*      */     throws IOException
/*      */   {
/* 2161 */     if (!this.terminal.isEchoEnabled()) {
/* 2162 */       return 0;
/*      */     }
/*      */     
/*      */ 
/* 2166 */     int num = countEchoCharacters(c);
/* 2167 */     back(num);
/* 2168 */     drawBuffer(num);
/*      */     
/* 2170 */     return num;
/*      */   }
/*      */   
/*      */ 
/*      */   private int countEchoCharacters(int c)
/*      */   {
/* 2176 */     if (c == 9) {
/* 2177 */       int tabStop = 8;
/* 2178 */       int position = getCursorPosition();
/*      */       
/* 2180 */       return tabStop - position % tabStop;
/*      */     }
/*      */     
/* 2183 */     return getPrintableCharacters(c).length();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private StringBuilder getPrintableCharacters(int ch)
/*      */   {
/* 2193 */     StringBuilder sbuff = new StringBuilder();
/*      */     
/* 2195 */     if (ch >= 32) {
/* 2196 */       if (ch < 127) {
/* 2197 */         sbuff.append(ch);
/*      */       }
/* 2199 */       else if (ch == 127) {
/* 2200 */         sbuff.append('^');
/* 2201 */         sbuff.append('?');
/*      */       }
/*      */       else {
/* 2204 */         sbuff.append('M');
/* 2205 */         sbuff.append('-');
/*      */         
/* 2207 */         if (ch >= 160) {
/* 2208 */           if (ch < 255) {
/* 2209 */             sbuff.append((char)(ch - 128));
/*      */           }
/*      */           else {
/* 2212 */             sbuff.append('^');
/* 2213 */             sbuff.append('?');
/*      */           }
/*      */         }
/*      */         else {
/* 2217 */           sbuff.append('^');
/* 2218 */           sbuff.append((char)(ch - 128 + 64));
/*      */         }
/*      */       }
/*      */     }
/*      */     else {
/* 2223 */       sbuff.append('^');
/* 2224 */       sbuff.append((char)(ch + 64));
/*      */     }
/*      */     
/* 2227 */     return sbuff;
/*      */   }
/*      */   
/*      */ 
/*      */   public final int readCharacter(char... allowed)
/*      */     throws IOException
/*      */   {
/* 2234 */     Arrays.sort(allowed);
/*      */     char c;
/* 2236 */     while (Arrays.binarySearch(allowed, c = (char)readCharacter()) < 0) {}
/*      */     
/*      */ 
/*      */ 
/* 2240 */     return c;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String readLine()
/*      */     throws IOException
/*      */   {
/* 2257 */     return readLine((String)null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String readLine(Character mask)
/*      */     throws IOException
/*      */   {
/* 2265 */     return readLine(null, mask);
/*      */   }
/*      */   
/*      */   public String readLine(String prompt) throws IOException {
/* 2269 */     return readLine(prompt, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean setKeyMap(String name)
/*      */   {
/* 2280 */     return this.consoleKeys.setKeyMap(name);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getKeyMap()
/*      */   {
/* 2290 */     return this.consoleKeys.getKeys().getName();
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   public String readLine(String prompt, Character mask)
/*      */     throws IOException
/*      */   {
/*      */     // Byte code:
/*      */     //   0: iconst_0
/*      */     //   1: istore_3
/*      */     //   2: aload_0
/*      */     //   3: aload_2
/*      */     //   4: putfield 602	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:mask	Ljava/lang/Character;
/*      */     //   7: aload_1
/*      */     //   8: ifnull +11 -> 19
/*      */     //   11: aload_0
/*      */     //   12: aload_1
/*      */     //   13: invokevirtual 1022	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:setPrompt	(Ljava/lang/String;)V
/*      */     //   16: goto +8 -> 24
/*      */     //   19: aload_0
/*      */     //   20: invokevirtual 550	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:getPrompt	()Ljava/lang/String;
/*      */     //   23: astore_1
/*      */     //   24: aload_0
/*      */     //   25: getfield 246	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:terminal	Lorg/bukkit/craftbukkit/libs/jline/Terminal;
/*      */     //   28: invokeinterface 337 1 0
/*      */     //   33: ifne +9 -> 42
/*      */     //   36: aload_0
/*      */     //   37: aload_1
/*      */     //   38: aload_2
/*      */     //   39: invokespecial 1026	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:beforeReadLine	(Ljava/lang/String;Ljava/lang/Character;)V
/*      */     //   42: aload_1
/*      */     //   43: ifnull +25 -> 68
/*      */     //   46: aload_1
/*      */     //   47: invokevirtual 410	java/lang/String:length	()I
/*      */     //   50: ifle +18 -> 68
/*      */     //   53: aload_0
/*      */     //   54: getfield 262	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:out	Ljava/io/Writer;
/*      */     //   57: aload_1
/*      */     //   58: invokevirtual 1028	java/io/Writer:write	(Ljava/lang/String;)V
/*      */     //   61: aload_0
/*      */     //   62: getfield 262	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:out	Ljava/io/Writer;
/*      */     //   65: invokevirtual 756	java/io/Writer:flush	()V
/*      */     //   68: aload_0
/*      */     //   69: getfield 246	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:terminal	Lorg/bukkit/craftbukkit/libs/jline/Terminal;
/*      */     //   72: invokeinterface 337 1 0
/*      */     //   77: ifne +55 -> 132
/*      */     //   80: aload_0
/*      */     //   81: invokespecial 1031	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:readLineSimple	()Ljava/lang/String;
/*      */     //   84: astore 4
/*      */     //   86: aload_0
/*      */     //   87: getfield 246	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:terminal	Lorg/bukkit/craftbukkit/libs/jline/Terminal;
/*      */     //   90: invokeinterface 337 1 0
/*      */     //   95: ifne +7 -> 102
/*      */     //   98: aload_0
/*      */     //   99: invokespecial 1034	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:afterReadLine	()V
/*      */     //   102: aload_0
/*      */     //   103: getfield 164	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:handleUserInterrupt	Z
/*      */     //   106: ifeq +23 -> 129
/*      */     //   109: aload_0
/*      */     //   110: getfield 246	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:terminal	Lorg/bukkit/craftbukkit/libs/jline/Terminal;
/*      */     //   113: instanceof 1036
/*      */     //   116: ifeq +13 -> 129
/*      */     //   119: aload_0
/*      */     //   120: getfield 246	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:terminal	Lorg/bukkit/craftbukkit/libs/jline/Terminal;
/*      */     //   123: checkcast 1036	org/bukkit/craftbukkit/libs/jline/UnixTerminal
/*      */     //   126: invokevirtual 1039	org/bukkit/craftbukkit/libs/jline/UnixTerminal:enableInterruptCharacter	()V
/*      */     //   129: aload 4
/*      */     //   131: areturn
/*      */     //   132: aload_0
/*      */     //   133: getfield 164	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:handleUserInterrupt	Z
/*      */     //   136: ifeq +23 -> 159
/*      */     //   139: aload_0
/*      */     //   140: getfield 246	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:terminal	Lorg/bukkit/craftbukkit/libs/jline/Terminal;
/*      */     //   143: instanceof 1036
/*      */     //   146: ifeq +13 -> 159
/*      */     //   149: aload_0
/*      */     //   150: getfield 246	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:terminal	Lorg/bukkit/craftbukkit/libs/jline/Terminal;
/*      */     //   153: checkcast 1036	org/bukkit/craftbukkit/libs/jline/UnixTerminal
/*      */     //   156: invokevirtual 1042	org/bukkit/craftbukkit/libs/jline/UnixTerminal:disableInterruptCharacter	()V
/*      */     //   159: aload_0
/*      */     //   160: getfield 398	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:prompt	Ljava/lang/String;
/*      */     //   163: astore 4
/*      */     //   165: aload_0
/*      */     //   166: getstatic 198	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State:NORMAL	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   169: putfield 200	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:state	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   172: iconst_1
/*      */     //   173: istore 5
/*      */     //   175: new 426	java/lang/StringBuilder
/*      */     //   178: dup
/*      */     //   179: invokespecial 427	java/lang/StringBuilder:<init>	()V
/*      */     //   182: astore 6
/*      */     //   184: new 1044	java/util/Stack
/*      */     //   187: dup
/*      */     //   188: invokespecial 1045	java/util/Stack:<init>	()V
/*      */     //   191: astore 7
/*      */     //   193: aload 7
/*      */     //   195: invokevirtual 1048	java/util/Stack:isEmpty	()Z
/*      */     //   198: ifeq +10 -> 208
/*      */     //   201: aload_0
/*      */     //   202: invokevirtual 885	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:readCharacter	()I
/*      */     //   205: goto +14 -> 219
/*      */     //   208: aload 7
/*      */     //   210: invokevirtual 1052	java/util/Stack:pop	()Ljava/lang/Object;
/*      */     //   213: checkcast 684	java/lang/Character
/*      */     //   216: invokevirtual 687	java/lang/Character:charValue	()C
/*      */     //   219: istore 8
/*      */     //   221: iload 8
/*      */     //   223: iconst_m1
/*      */     //   224: if_icmpne +52 -> 276
/*      */     //   227: aconst_null
/*      */     //   228: astore 9
/*      */     //   230: aload_0
/*      */     //   231: getfield 246	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:terminal	Lorg/bukkit/craftbukkit/libs/jline/Terminal;
/*      */     //   234: invokeinterface 337 1 0
/*      */     //   239: ifne +7 -> 246
/*      */     //   242: aload_0
/*      */     //   243: invokespecial 1034	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:afterReadLine	()V
/*      */     //   246: aload_0
/*      */     //   247: getfield 164	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:handleUserInterrupt	Z
/*      */     //   250: ifeq +23 -> 273
/*      */     //   253: aload_0
/*      */     //   254: getfield 246	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:terminal	Lorg/bukkit/craftbukkit/libs/jline/Terminal;
/*      */     //   257: instanceof 1036
/*      */     //   260: ifeq +13 -> 273
/*      */     //   263: aload_0
/*      */     //   264: getfield 246	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:terminal	Lorg/bukkit/craftbukkit/libs/jline/Terminal;
/*      */     //   267: checkcast 1036	org/bukkit/craftbukkit/libs/jline/UnixTerminal
/*      */     //   270: invokevirtual 1039	org/bukkit/craftbukkit/libs/jline/UnixTerminal:enableInterruptCharacter	()V
/*      */     //   273: aload 9
/*      */     //   275: areturn
/*      */     //   276: aload 6
/*      */     //   278: iload 8
/*      */     //   280: invokevirtual 1055	java/lang/StringBuilder:appendCodePoint	(I)Ljava/lang/StringBuilder;
/*      */     //   283: pop
/*      */     //   284: aload_0
/*      */     //   285: getfield 1057	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:recording	Z
/*      */     //   288: ifeq +44 -> 332
/*      */     //   291: new 426	java/lang/StringBuilder
/*      */     //   294: dup
/*      */     //   295: invokespecial 427	java/lang/StringBuilder:<init>	()V
/*      */     //   298: aload_0
/*      */     //   299: dup_x1
/*      */     //   300: getfield 189	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:macro	Ljava/lang/String;
/*      */     //   303: invokevirtual 632	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   306: new 406	java/lang/String
/*      */     //   309: dup
/*      */     //   310: iconst_1
/*      */     //   311: newarray <illegal type>
/*      */     //   313: dup
/*      */     //   314: iconst_0
/*      */     //   315: iload 8
/*      */     //   317: iastore
/*      */     //   318: iconst_0
/*      */     //   319: iconst_1
/*      */     //   320: invokespecial 1060	java/lang/String:<init>	([III)V
/*      */     //   323: invokevirtual 632	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   326: invokevirtual 445	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*      */     //   329: putfield 189	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:macro	Ljava/lang/String;
/*      */     //   332: aload_0
/*      */     //   333: invokevirtual 1061	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:getKeys	()Lorg/bukkit/craftbukkit/libs/jline/console/KeyMap;
/*      */     //   336: aload 6
/*      */     //   338: invokevirtual 1065	org/bukkit/craftbukkit/libs/jline/console/KeyMap:getBound	(Ljava/lang/CharSequence;)Ljava/lang/Object;
/*      */     //   341: astore 9
/*      */     //   343: aload_0
/*      */     //   344: getfield 1057	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:recording	Z
/*      */     //   347: ifne +89 -> 436
/*      */     //   350: aload 9
/*      */     //   352: instanceof 942
/*      */     //   355: ifne +81 -> 436
/*      */     //   358: aload 9
/*      */     //   360: getstatic 1068	org/bukkit/craftbukkit/libs/jline/console/Operation:YANK_POP	Lorg/bukkit/craftbukkit/libs/jline/console/Operation;
/*      */     //   363: if_acmpeq +18 -> 381
/*      */     //   366: aload 9
/*      */     //   368: getstatic 1071	org/bukkit/craftbukkit/libs/jline/console/Operation:YANK	Lorg/bukkit/craftbukkit/libs/jline/console/Operation;
/*      */     //   371: if_acmpeq +10 -> 381
/*      */     //   374: aload_0
/*      */     //   375: getfield 187	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:killRing	Lorg/bukkit/craftbukkit/libs/jline/console/KillRing;
/*      */     //   378: invokevirtual 1074	org/bukkit/craftbukkit/libs/jline/console/KillRing:resetLastYank	()V
/*      */     //   381: aload 9
/*      */     //   383: getstatic 1077	org/bukkit/craftbukkit/libs/jline/console/Operation:KILL_LINE	Lorg/bukkit/craftbukkit/libs/jline/console/Operation;
/*      */     //   386: if_acmpeq +50 -> 436
/*      */     //   389: aload 9
/*      */     //   391: getstatic 1080	org/bukkit/craftbukkit/libs/jline/console/Operation:KILL_WHOLE_LINE	Lorg/bukkit/craftbukkit/libs/jline/console/Operation;
/*      */     //   394: if_acmpeq +42 -> 436
/*      */     //   397: aload 9
/*      */     //   399: getstatic 1083	org/bukkit/craftbukkit/libs/jline/console/Operation:BACKWARD_KILL_WORD	Lorg/bukkit/craftbukkit/libs/jline/console/Operation;
/*      */     //   402: if_acmpeq +34 -> 436
/*      */     //   405: aload 9
/*      */     //   407: getstatic 1086	org/bukkit/craftbukkit/libs/jline/console/Operation:KILL_WORD	Lorg/bukkit/craftbukkit/libs/jline/console/Operation;
/*      */     //   410: if_acmpeq +26 -> 436
/*      */     //   413: aload 9
/*      */     //   415: getstatic 1089	org/bukkit/craftbukkit/libs/jline/console/Operation:UNIX_LINE_DISCARD	Lorg/bukkit/craftbukkit/libs/jline/console/Operation;
/*      */     //   418: if_acmpeq +18 -> 436
/*      */     //   421: aload 9
/*      */     //   423: getstatic 1092	org/bukkit/craftbukkit/libs/jline/console/Operation:UNIX_WORD_RUBOUT	Lorg/bukkit/craftbukkit/libs/jline/console/Operation;
/*      */     //   426: if_acmpeq +10 -> 436
/*      */     //   429: aload_0
/*      */     //   430: getfield 187	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:killRing	Lorg/bukkit/craftbukkit/libs/jline/console/KillRing;
/*      */     //   433: invokevirtual 1095	org/bukkit/craftbukkit/libs/jline/console/KillRing:resetLastKill	()V
/*      */     //   436: aload 9
/*      */     //   438: getstatic 1098	org/bukkit/craftbukkit/libs/jline/console/Operation:DO_LOWERCASE_VERSION	Lorg/bukkit/craftbukkit/libs/jline/console/Operation;
/*      */     //   441: if_acmpne +38 -> 479
/*      */     //   444: aload 6
/*      */     //   446: aload 6
/*      */     //   448: invokevirtual 505	java/lang/StringBuilder:length	()I
/*      */     //   451: iconst_1
/*      */     //   452: isub
/*      */     //   453: invokevirtual 522	java/lang/StringBuilder:setLength	(I)V
/*      */     //   456: aload 6
/*      */     //   458: iload 8
/*      */     //   460: i2c
/*      */     //   461: invokestatic 798	java/lang/Character:toLowerCase	(C)C
/*      */     //   464: invokevirtual 435	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
/*      */     //   467: pop
/*      */     //   468: aload_0
/*      */     //   469: invokevirtual 1061	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:getKeys	()Lorg/bukkit/craftbukkit/libs/jline/console/KeyMap;
/*      */     //   472: aload 6
/*      */     //   474: invokevirtual 1065	org/bukkit/craftbukkit/libs/jline/console/KeyMap:getBound	(Ljava/lang/CharSequence;)Ljava/lang/Object;
/*      */     //   477: astore 9
/*      */     //   479: aload 9
/*      */     //   481: instanceof 942
/*      */     //   484: ifeq +76 -> 560
/*      */     //   487: iload 8
/*      */     //   489: bipush 27
/*      */     //   491: if_icmpne -298 -> 193
/*      */     //   494: aload 7
/*      */     //   496: invokevirtual 1048	java/util/Stack:isEmpty	()Z
/*      */     //   499: ifeq -306 -> 193
/*      */     //   502: aload_0
/*      */     //   503: getfield 339	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:in	Lorg/bukkit/craftbukkit/libs/jline/internal/NonBlockingInputStream;
/*      */     //   506: invokevirtual 904	org/bukkit/craftbukkit/libs/jline/internal/NonBlockingInputStream:isNonBlockingEnabled	()Z
/*      */     //   509: ifeq -316 -> 193
/*      */     //   512: aload_0
/*      */     //   513: getfield 339	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:in	Lorg/bukkit/craftbukkit/libs/jline/internal/NonBlockingInputStream;
/*      */     //   516: aload_0
/*      */     //   517: getfield 330	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:escapeTimeout	J
/*      */     //   520: invokevirtual 908	org/bukkit/craftbukkit/libs/jline/internal/NonBlockingInputStream:peek	(J)I
/*      */     //   523: bipush -2
/*      */     //   525: if_icmpne -332 -> 193
/*      */     //   528: aload 9
/*      */     //   530: checkcast 942	org/bukkit/craftbukkit/libs/jline/console/KeyMap
/*      */     //   533: invokevirtual 1101	org/bukkit/craftbukkit/libs/jline/console/KeyMap:getAnotherKey	()Ljava/lang/Object;
/*      */     //   536: astore 9
/*      */     //   538: aload 9
/*      */     //   540: ifnull -347 -> 193
/*      */     //   543: aload 9
/*      */     //   545: instanceof 942
/*      */     //   548: ifeq +6 -> 554
/*      */     //   551: goto -358 -> 193
/*      */     //   554: aload 6
/*      */     //   556: iconst_0
/*      */     //   557: invokevirtual 522	java/lang/StringBuilder:setLength	(I)V
/*      */     //   560: aload 9
/*      */     //   562: ifnonnull +89 -> 651
/*      */     //   565: aload 6
/*      */     //   567: invokevirtual 505	java/lang/StringBuilder:length	()I
/*      */     //   570: ifle +81 -> 651
/*      */     //   573: aload 6
/*      */     //   575: aload 6
/*      */     //   577: invokevirtual 505	java/lang/StringBuilder:length	()I
/*      */     //   580: iconst_1
/*      */     //   581: isub
/*      */     //   582: invokevirtual 510	java/lang/StringBuilder:charAt	(I)C
/*      */     //   585: istore 8
/*      */     //   587: aload 6
/*      */     //   589: aload 6
/*      */     //   591: invokevirtual 505	java/lang/StringBuilder:length	()I
/*      */     //   594: iconst_1
/*      */     //   595: isub
/*      */     //   596: invokevirtual 522	java/lang/StringBuilder:setLength	(I)V
/*      */     //   599: aload_0
/*      */     //   600: invokevirtual 1061	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:getKeys	()Lorg/bukkit/craftbukkit/libs/jline/console/KeyMap;
/*      */     //   603: aload 6
/*      */     //   605: invokevirtual 1065	org/bukkit/craftbukkit/libs/jline/console/KeyMap:getBound	(Ljava/lang/CharSequence;)Ljava/lang/Object;
/*      */     //   608: astore 10
/*      */     //   610: aload 10
/*      */     //   612: instanceof 942
/*      */     //   615: ifeq +33 -> 648
/*      */     //   618: aload 10
/*      */     //   620: checkcast 942	org/bukkit/craftbukkit/libs/jline/console/KeyMap
/*      */     //   623: invokevirtual 1101	org/bukkit/craftbukkit/libs/jline/console/KeyMap:getAnotherKey	()Ljava/lang/Object;
/*      */     //   626: astore 9
/*      */     //   628: aload 9
/*      */     //   630: ifnonnull +6 -> 636
/*      */     //   633: goto -73 -> 560
/*      */     //   636: aload 7
/*      */     //   638: iload 8
/*      */     //   640: i2c
/*      */     //   641: invokestatic 1104	java/lang/Character:valueOf	(C)Ljava/lang/Character;
/*      */     //   644: invokevirtual 1107	java/util/Stack:push	(Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   647: pop
/*      */     //   648: goto -88 -> 560
/*      */     //   651: aload 9
/*      */     //   653: ifnonnull +6 -> 659
/*      */     //   656: goto -463 -> 193
/*      */     //   659: iconst_2
/*      */     //   660: anewarray 4	java/lang/Object
/*      */     //   663: dup
/*      */     //   664: iconst_0
/*      */     //   665: ldc_w 1109
/*      */     //   668: aastore
/*      */     //   669: dup
/*      */     //   670: iconst_1
/*      */     //   671: aload 9
/*      */     //   673: aastore
/*      */     //   674: invokestatic 986	org/bukkit/craftbukkit/libs/jline/internal/Log:trace	([Ljava/lang/Object;)V
/*      */     //   677: aload 9
/*      */     //   679: instanceof 406
/*      */     //   682: ifeq +62 -> 744
/*      */     //   685: aload 9
/*      */     //   687: checkcast 406	java/lang/String
/*      */     //   690: astore 10
/*      */     //   692: iconst_0
/*      */     //   693: istore 11
/*      */     //   695: iload 11
/*      */     //   697: aload 10
/*      */     //   699: invokevirtual 410	java/lang/String:length	()I
/*      */     //   702: if_icmpge +33 -> 735
/*      */     //   705: aload 7
/*      */     //   707: aload 10
/*      */     //   709: aload 10
/*      */     //   711: invokevirtual 410	java/lang/String:length	()I
/*      */     //   714: iconst_1
/*      */     //   715: isub
/*      */     //   716: iload 11
/*      */     //   718: isub
/*      */     //   719: invokevirtual 509	java/lang/String:charAt	(I)C
/*      */     //   722: invokestatic 1104	java/lang/Character:valueOf	(C)Ljava/lang/Character;
/*      */     //   725: invokevirtual 1107	java/util/Stack:push	(Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   728: pop
/*      */     //   729: iinc 11 1
/*      */     //   732: goto -37 -> 695
/*      */     //   735: aload 6
/*      */     //   737: iconst_0
/*      */     //   738: invokevirtual 522	java/lang/StringBuilder:setLength	(I)V
/*      */     //   741: goto -548 -> 193
/*      */     //   744: aload 9
/*      */     //   746: instanceof 1111
/*      */     //   749: ifeq +23 -> 772
/*      */     //   752: aload 9
/*      */     //   754: checkcast 1111	java/awt/event/ActionListener
/*      */     //   757: aconst_null
/*      */     //   758: invokeinterface 1115 2 0
/*      */     //   763: aload 6
/*      */     //   765: iconst_0
/*      */     //   766: invokevirtual 522	java/lang/StringBuilder:setLength	(I)V
/*      */     //   769: goto -576 -> 193
/*      */     //   772: aload_0
/*      */     //   773: getfield 200	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:state	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   776: getstatic 1118	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State:SEARCH	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   779: if_acmpeq +13 -> 792
/*      */     //   782: aload_0
/*      */     //   783: getfield 200	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:state	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   786: getstatic 1121	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State:FORWARD_SEARCH	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   789: if_acmpne +596 -> 1385
/*      */     //   792: iconst_m1
/*      */     //   793: istore 10
/*      */     //   795: getstatic 774	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader$2:$SwitchMap$jline$console$Operation	[I
/*      */     //   798: aload 9
/*      */     //   800: checkcast 776	org/bukkit/craftbukkit/libs/jline/console/Operation
/*      */     //   803: invokevirtual 779	org/bukkit/craftbukkit/libs/jline/console/Operation:ordinal	()I
/*      */     //   806: iaload
/*      */     //   807: lookupswitch	default:+346->1153, 2:+49->856, 19:+82->889, 20:+140->947, 21:+217->1024, 22:+290->1097
/*      */     //   856: aload_0
/*      */     //   857: getstatic 198	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State:NORMAL	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   860: putfield 200	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:state	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   863: aload_0
/*      */     //   864: getfield 152	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:buf	Lorg/bukkit/craftbukkit/libs/jline/console/CursorBuffer;
/*      */     //   867: invokevirtual 600	org/bukkit/craftbukkit/libs/jline/console/CursorBuffer:clear	()Z
/*      */     //   870: pop
/*      */     //   871: aload_0
/*      */     //   872: getfield 152	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:buf	Lorg/bukkit/craftbukkit/libs/jline/console/CursorBuffer;
/*      */     //   875: getfield 500	org/bukkit/craftbukkit/libs/jline/console/CursorBuffer:buffer	Ljava/lang/StringBuilder;
/*      */     //   878: aload_0
/*      */     //   879: getfield 166	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchTerm	Ljava/lang/StringBuffer;
/*      */     //   882: invokevirtual 1124	java/lang/StringBuilder:append	(Ljava/lang/StringBuffer;)Ljava/lang/StringBuilder;
/*      */     //   885: pop
/*      */     //   886: goto +322 -> 1208
/*      */     //   889: aload_0
/*      */     //   890: getstatic 1118	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State:SEARCH	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   893: putfield 200	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:state	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   896: aload_0
/*      */     //   897: getfield 166	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchTerm	Ljava/lang/StringBuffer;
/*      */     //   900: invokevirtual 1127	java/lang/StringBuffer:length	()I
/*      */     //   903: ifne +15 -> 918
/*      */     //   906: aload_0
/*      */     //   907: getfield 166	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchTerm	Ljava/lang/StringBuffer;
/*      */     //   910: aload_0
/*      */     //   911: getfield 170	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:previousSearchTerm	Ljava/lang/String;
/*      */     //   914: invokevirtual 1130	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
/*      */     //   917: pop
/*      */     //   918: aload_0
/*      */     //   919: getfield 172	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchIndex	I
/*      */     //   922: ifle +286 -> 1208
/*      */     //   925: aload_0
/*      */     //   926: aload_0
/*      */     //   927: aload_0
/*      */     //   928: getfield 166	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchTerm	Ljava/lang/StringBuffer;
/*      */     //   931: invokevirtual 1131	java/lang/StringBuffer:toString	()Ljava/lang/String;
/*      */     //   934: aload_0
/*      */     //   935: getfield 172	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchIndex	I
/*      */     //   938: invokevirtual 1133	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchBackwards	(Ljava/lang/String;I)I
/*      */     //   941: putfield 172	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchIndex	I
/*      */     //   944: goto +264 -> 1208
/*      */     //   947: aload_0
/*      */     //   948: getstatic 1121	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State:FORWARD_SEARCH	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   951: putfield 200	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:state	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   954: aload_0
/*      */     //   955: getfield 166	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchTerm	Ljava/lang/StringBuffer;
/*      */     //   958: invokevirtual 1127	java/lang/StringBuffer:length	()I
/*      */     //   961: ifne +15 -> 976
/*      */     //   964: aload_0
/*      */     //   965: getfield 166	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchTerm	Ljava/lang/StringBuffer;
/*      */     //   968: aload_0
/*      */     //   969: getfield 170	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:previousSearchTerm	Ljava/lang/String;
/*      */     //   972: invokevirtual 1130	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
/*      */     //   975: pop
/*      */     //   976: aload_0
/*      */     //   977: getfield 172	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchIndex	I
/*      */     //   980: iconst_m1
/*      */     //   981: if_icmple +227 -> 1208
/*      */     //   984: aload_0
/*      */     //   985: getfield 172	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchIndex	I
/*      */     //   988: aload_0
/*      */     //   989: getfield 221	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:history	Lorg/bukkit/craftbukkit/libs/jline/console/history/History;
/*      */     //   992: invokeinterface 617 1 0
/*      */     //   997: iconst_1
/*      */     //   998: isub
/*      */     //   999: if_icmpge +209 -> 1208
/*      */     //   1002: aload_0
/*      */     //   1003: aload_0
/*      */     //   1004: aload_0
/*      */     //   1005: getfield 166	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchTerm	Ljava/lang/StringBuffer;
/*      */     //   1008: invokevirtual 1131	java/lang/StringBuffer:toString	()Ljava/lang/String;
/*      */     //   1011: aload_0
/*      */     //   1012: getfield 172	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchIndex	I
/*      */     //   1015: invokevirtual 1136	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchForwards	(Ljava/lang/String;I)I
/*      */     //   1018: putfield 172	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchIndex	I
/*      */     //   1021: goto +187 -> 1208
/*      */     //   1024: aload_0
/*      */     //   1025: getfield 166	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchTerm	Ljava/lang/StringBuffer;
/*      */     //   1028: invokevirtual 1127	java/lang/StringBuffer:length	()I
/*      */     //   1031: ifle +177 -> 1208
/*      */     //   1034: aload_0
/*      */     //   1035: getfield 166	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchTerm	Ljava/lang/StringBuffer;
/*      */     //   1038: aload_0
/*      */     //   1039: getfield 166	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchTerm	Ljava/lang/StringBuffer;
/*      */     //   1042: invokevirtual 1127	java/lang/StringBuffer:length	()I
/*      */     //   1045: iconst_1
/*      */     //   1046: isub
/*      */     //   1047: invokevirtual 1139	java/lang/StringBuffer:deleteCharAt	(I)Ljava/lang/StringBuffer;
/*      */     //   1050: pop
/*      */     //   1051: aload_0
/*      */     //   1052: getfield 200	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:state	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   1055: getstatic 1118	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State:SEARCH	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   1058: if_acmpne +21 -> 1079
/*      */     //   1061: aload_0
/*      */     //   1062: aload_0
/*      */     //   1063: aload_0
/*      */     //   1064: getfield 166	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchTerm	Ljava/lang/StringBuffer;
/*      */     //   1067: invokevirtual 1131	java/lang/StringBuffer:toString	()Ljava/lang/String;
/*      */     //   1070: invokevirtual 639	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchBackwards	(Ljava/lang/String;)I
/*      */     //   1073: putfield 172	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchIndex	I
/*      */     //   1076: goto +132 -> 1208
/*      */     //   1079: aload_0
/*      */     //   1080: aload_0
/*      */     //   1081: aload_0
/*      */     //   1082: getfield 166	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchTerm	Ljava/lang/StringBuffer;
/*      */     //   1085: invokevirtual 1131	java/lang/StringBuffer:toString	()Ljava/lang/String;
/*      */     //   1088: invokevirtual 1141	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchForwards	(Ljava/lang/String;)I
/*      */     //   1091: putfield 172	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchIndex	I
/*      */     //   1094: goto +114 -> 1208
/*      */     //   1097: aload_0
/*      */     //   1098: getfield 166	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchTerm	Ljava/lang/StringBuffer;
/*      */     //   1101: iload 8
/*      */     //   1103: invokevirtual 1143	java/lang/StringBuffer:appendCodePoint	(I)Ljava/lang/StringBuffer;
/*      */     //   1106: pop
/*      */     //   1107: aload_0
/*      */     //   1108: getfield 200	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:state	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   1111: getstatic 1118	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State:SEARCH	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   1114: if_acmpne +21 -> 1135
/*      */     //   1117: aload_0
/*      */     //   1118: aload_0
/*      */     //   1119: aload_0
/*      */     //   1120: getfield 166	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchTerm	Ljava/lang/StringBuffer;
/*      */     //   1123: invokevirtual 1131	java/lang/StringBuffer:toString	()Ljava/lang/String;
/*      */     //   1126: invokevirtual 639	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchBackwards	(Ljava/lang/String;)I
/*      */     //   1129: putfield 172	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchIndex	I
/*      */     //   1132: goto +76 -> 1208
/*      */     //   1135: aload_0
/*      */     //   1136: aload_0
/*      */     //   1137: aload_0
/*      */     //   1138: getfield 166	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchTerm	Ljava/lang/StringBuffer;
/*      */     //   1141: invokevirtual 1131	java/lang/StringBuffer:toString	()Ljava/lang/String;
/*      */     //   1144: invokevirtual 1141	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchForwards	(Ljava/lang/String;)I
/*      */     //   1147: putfield 172	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchIndex	I
/*      */     //   1150: goto +58 -> 1208
/*      */     //   1153: aload_0
/*      */     //   1154: getfield 172	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchIndex	I
/*      */     //   1157: iconst_m1
/*      */     //   1158: if_icmpeq +43 -> 1201
/*      */     //   1161: aload_0
/*      */     //   1162: getfield 221	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:history	Lorg/bukkit/craftbukkit/libs/jline/console/history/History;
/*      */     //   1165: aload_0
/*      */     //   1166: getfield 172	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchIndex	I
/*      */     //   1169: invokeinterface 1146 2 0
/*      */     //   1174: pop
/*      */     //   1175: aload_0
/*      */     //   1176: getfield 221	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:history	Lorg/bukkit/craftbukkit/libs/jline/console/history/History;
/*      */     //   1179: invokeinterface 1149 1 0
/*      */     //   1184: invokeinterface 629 1 0
/*      */     //   1189: aload_0
/*      */     //   1190: getfield 166	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchTerm	Ljava/lang/StringBuffer;
/*      */     //   1193: invokevirtual 1131	java/lang/StringBuffer:toString	()Ljava/lang/String;
/*      */     //   1196: invokevirtual 1151	java/lang/String:indexOf	(Ljava/lang/String;)I
/*      */     //   1199: istore 10
/*      */     //   1201: aload_0
/*      */     //   1202: getstatic 198	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State:NORMAL	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   1205: putfield 200	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:state	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   1208: aload_0
/*      */     //   1209: getfield 200	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:state	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   1212: getstatic 1118	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State:SEARCH	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   1215: if_acmpeq +13 -> 1228
/*      */     //   1218: aload_0
/*      */     //   1219: getfield 200	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:state	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   1222: getstatic 1121	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State:FORWARD_SEARCH	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   1225: if_acmpne +152 -> 1377
/*      */     //   1228: aload_0
/*      */     //   1229: getfield 166	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchTerm	Ljava/lang/StringBuffer;
/*      */     //   1232: invokevirtual 1127	java/lang/StringBuffer:length	()I
/*      */     //   1235: ifne +40 -> 1275
/*      */     //   1238: aload_0
/*      */     //   1239: getfield 200	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:state	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   1242: getstatic 1118	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State:SEARCH	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   1245: if_acmpne +14 -> 1259
/*      */     //   1248: aload_0
/*      */     //   1249: ldc -88
/*      */     //   1251: ldc -88
/*      */     //   1253: invokevirtual 1155	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:printSearchStatus	(Ljava/lang/String;Ljava/lang/String;)V
/*      */     //   1256: goto +11 -> 1267
/*      */     //   1259: aload_0
/*      */     //   1260: ldc -88
/*      */     //   1262: ldc -88
/*      */     //   1264: invokevirtual 1158	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:printForwardSearchStatus	(Ljava/lang/String;Ljava/lang/String;)V
/*      */     //   1267: aload_0
/*      */     //   1268: iconst_m1
/*      */     //   1269: putfield 172	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchIndex	I
/*      */     //   1272: goto +113 -> 1385
/*      */     //   1275: aload_0
/*      */     //   1276: getfield 172	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchIndex	I
/*      */     //   1279: iconst_m1
/*      */     //   1280: if_icmpne +23 -> 1303
/*      */     //   1283: aload_0
/*      */     //   1284: invokevirtual 597	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:beep	()V
/*      */     //   1287: aload_0
/*      */     //   1288: aload_0
/*      */     //   1289: getfield 166	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchTerm	Ljava/lang/StringBuffer;
/*      */     //   1292: invokevirtual 1131	java/lang/StringBuffer:toString	()Ljava/lang/String;
/*      */     //   1295: ldc -88
/*      */     //   1297: invokevirtual 1155	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:printSearchStatus	(Ljava/lang/String;Ljava/lang/String;)V
/*      */     //   1300: goto +85 -> 1385
/*      */     //   1303: aload_0
/*      */     //   1304: getfield 200	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:state	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   1307: getstatic 1118	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State:SEARCH	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   1310: if_acmpne +35 -> 1345
/*      */     //   1313: aload_0
/*      */     //   1314: aload_0
/*      */     //   1315: getfield 166	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchTerm	Ljava/lang/StringBuffer;
/*      */     //   1318: invokevirtual 1131	java/lang/StringBuffer:toString	()Ljava/lang/String;
/*      */     //   1321: aload_0
/*      */     //   1322: getfield 221	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:history	Lorg/bukkit/craftbukkit/libs/jline/console/history/History;
/*      */     //   1325: aload_0
/*      */     //   1326: getfield 172	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchIndex	I
/*      */     //   1329: invokeinterface 626 2 0
/*      */     //   1334: invokeinterface 629 1 0
/*      */     //   1339: invokevirtual 1155	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:printSearchStatus	(Ljava/lang/String;Ljava/lang/String;)V
/*      */     //   1342: goto +43 -> 1385
/*      */     //   1345: aload_0
/*      */     //   1346: aload_0
/*      */     //   1347: getfield 166	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchTerm	Ljava/lang/StringBuffer;
/*      */     //   1350: invokevirtual 1131	java/lang/StringBuffer:toString	()Ljava/lang/String;
/*      */     //   1353: aload_0
/*      */     //   1354: getfield 221	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:history	Lorg/bukkit/craftbukkit/libs/jline/console/history/History;
/*      */     //   1357: aload_0
/*      */     //   1358: getfield 172	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchIndex	I
/*      */     //   1361: invokeinterface 626 2 0
/*      */     //   1366: invokeinterface 629 1 0
/*      */     //   1371: invokevirtual 1158	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:printForwardSearchStatus	(Ljava/lang/String;Ljava/lang/String;)V
/*      */     //   1374: goto +11 -> 1385
/*      */     //   1377: aload_0
/*      */     //   1378: aload 4
/*      */     //   1380: iload 10
/*      */     //   1382: invokevirtual 1162	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:restoreLine	(Ljava/lang/String;I)V
/*      */     //   1385: aload_0
/*      */     //   1386: getfield 200	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:state	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   1389: getstatic 1118	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State:SEARCH	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   1392: if_acmpeq +2953 -> 4345
/*      */     //   1395: aload_0
/*      */     //   1396: getfield 200	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:state	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   1399: getstatic 1121	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State:FORWARD_SEARCH	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   1402: if_acmpeq +2943 -> 4345
/*      */     //   1405: iconst_0
/*      */     //   1406: istore 10
/*      */     //   1408: iload_3
/*      */     //   1409: ifne +7 -> 1416
/*      */     //   1412: iconst_1
/*      */     //   1413: goto +4 -> 1417
/*      */     //   1416: iload_3
/*      */     //   1417: istore 11
/*      */     //   1419: iconst_1
/*      */     //   1420: istore 5
/*      */     //   1422: aload 9
/*      */     //   1424: instanceof 776
/*      */     //   1427: ifeq +2918 -> 4345
/*      */     //   1430: aload 9
/*      */     //   1432: checkcast 776	org/bukkit/craftbukkit/libs/jline/console/Operation
/*      */     //   1435: astore 12
/*      */     //   1437: aload_0
/*      */     //   1438: getfield 152	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:buf	Lorg/bukkit/craftbukkit/libs/jline/console/CursorBuffer;
/*      */     //   1441: getfield 424	org/bukkit/craftbukkit/libs/jline/console/CursorBuffer:cursor	I
/*      */     //   1444: istore 13
/*      */     //   1446: aload_0
/*      */     //   1447: getfield 200	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:state	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   1450: astore 14
/*      */     //   1452: aload_0
/*      */     //   1453: getfield 200	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:state	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   1456: getstatic 843	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State:VI_CHANGE_TO	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   1459: if_acmpeq +23 -> 1482
/*      */     //   1462: aload_0
/*      */     //   1463: getfield 200	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:state	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   1466: getstatic 849	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State:VI_YANK_TO	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   1469: if_acmpeq +13 -> 1482
/*      */     //   1472: aload_0
/*      */     //   1473: getfield 200	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:state	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   1476: getstatic 846	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State:VI_DELETE_TO	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   1479: if_acmpne +11 -> 1490
/*      */     //   1482: aload_0
/*      */     //   1483: aload 12
/*      */     //   1485: invokespecial 1164	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:viDeleteChangeYankToRemap	(Lorg/bukkit/craftbukkit/libs/jline/console/Operation;)Lorg/bukkit/craftbukkit/libs/jline/console/Operation;
/*      */     //   1488: astore 12
/*      */     //   1490: getstatic 774	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader$2:$SwitchMap$jline$console$Operation	[I
/*      */     //   1493: aload 12
/*      */     //   1495: invokevirtual 779	org/bukkit/craftbukkit/libs/jline/console/Operation:ordinal	()I
/*      */     //   1498: iaload
/*      */     //   1499: tableswitch	default:+2690->4189, 1:+1826->3325, 2:+578->2077, 3:+892->2391, 4:+912->2411, 5:+1587->3086, 6:+2087->3586, 7:+2150->3649, 8:+2129->3628, 9:+2211->3710, 10:+2233->3732, 11:+2502->4001, 12:+2222->3721, 13:+2185->3684, 14:+2690->4189, 15:+2690->4189, 16:+2287->3786, 17:+2342->3841, 18:+2389->3888, 19:+1278->2777, 20:+1419->2918, 21:+803->2302, 22:+517->2016, 23:+333->1832, 24:+410->1909, 25:+417->1916, 26:+427->1926, 27:+436->1935, 28:+445->1944, 29:+454->1953, 30:+479->1978, 31:+492->1991, 32:+526->2025, 33:+592->2091, 34:+646->2145, 35:+709->2208, 36:+718->2217, 37:+727->2226, 38:+737->2236, 39:+765->2264, 40:+775->2274, 41:+812->2311, 42:+883->2382, 43:+931->2430, 44:+940->2439, 45:+951->2450, 46:+960->2459, 47:+969->2468, 48:+1001->2500, 49:+1033->2532, 50:+1127->2626, 51:+1560->3059, 52:+1569->3068, 53:+1578->3077, 54:+1596->3095, 55:+1606->3105, 56:+1624->3123, 57:+1632->3131, 58:+1665->3164, 59:+1723->3222, 60:+1737->3236, 61:+1772->3271, 62:+1786->3285, 63:+1806->3305, 64:+1940->3439, 65:+1951->3450, 66:+2004->3503, 67:+2014->3513, 68:+2024->3523, 69:+2034->3533, 70:+2096->3595, 71:+2244->3743, 72:+2265->3764, 73:+2276->3775, 74:+2455->3954, 75:+2491->3990, 76:+2563->4062, 77:+2574->4073, 78:+2611->4110, 79:+2638->4137, 80:+2676->4175
/*      */     //   1832: iconst_0
/*      */     //   1833: istore 15
/*      */     //   1835: aload_0
/*      */     //   1836: getfield 195	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:copyPasteDetection	Z
/*      */     //   1839: ifeq +47 -> 1886
/*      */     //   1842: iload 8
/*      */     //   1844: bipush 9
/*      */     //   1846: if_icmpne +40 -> 1886
/*      */     //   1849: aload 7
/*      */     //   1851: invokevirtual 1048	java/util/Stack:isEmpty	()Z
/*      */     //   1854: ifeq +29 -> 1883
/*      */     //   1857: aload_0
/*      */     //   1858: getfield 339	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:in	Lorg/bukkit/craftbukkit/libs/jline/internal/NonBlockingInputStream;
/*      */     //   1861: invokevirtual 904	org/bukkit/craftbukkit/libs/jline/internal/NonBlockingInputStream:isNonBlockingEnabled	()Z
/*      */     //   1864: ifeq +22 -> 1886
/*      */     //   1867: aload_0
/*      */     //   1868: getfield 339	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:in	Lorg/bukkit/craftbukkit/libs/jline/internal/NonBlockingInputStream;
/*      */     //   1871: aload_0
/*      */     //   1872: getfield 330	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:escapeTimeout	J
/*      */     //   1875: invokevirtual 908	org/bukkit/craftbukkit/libs/jline/internal/NonBlockingInputStream:peek	(J)I
/*      */     //   1878: bipush -2
/*      */     //   1880: if_icmpeq +6 -> 1886
/*      */     //   1883: iconst_1
/*      */     //   1884: istore 15
/*      */     //   1886: iload 15
/*      */     //   1888: ifne +12 -> 1900
/*      */     //   1891: aload_0
/*      */     //   1892: invokevirtual 1167	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:complete	()Z
/*      */     //   1895: istore 5
/*      */     //   1897: goto +2292 -> 4189
/*      */     //   1900: aload_0
/*      */     //   1901: aload 6
/*      */     //   1903: invokevirtual 529	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:putString	(Ljava/lang/CharSequence;)V
/*      */     //   1906: goto +2283 -> 4189
/*      */     //   1909: aload_0
/*      */     //   1910: invokevirtual 1170	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:printCompletionCandidates	()V
/*      */     //   1913: goto +2276 -> 4189
/*      */     //   1916: aload_0
/*      */     //   1917: iconst_0
/*      */     //   1918: invokevirtual 544	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:setCursorPosition	(I)Z
/*      */     //   1921: istore 5
/*      */     //   1923: goto +2266 -> 4189
/*      */     //   1926: aload_0
/*      */     //   1927: invokevirtual 1173	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:yank	()Z
/*      */     //   1930: istore 5
/*      */     //   1932: goto +2257 -> 4189
/*      */     //   1935: aload_0
/*      */     //   1936: invokevirtual 1176	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:yankPop	()Z
/*      */     //   1939: istore 5
/*      */     //   1941: goto +2248 -> 4189
/*      */     //   1944: aload_0
/*      */     //   1945: invokevirtual 518	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:killLine	()Z
/*      */     //   1948: istore 5
/*      */     //   1950: goto +2239 -> 4189
/*      */     //   1953: aload_0
/*      */     //   1954: iconst_0
/*      */     //   1955: invokevirtual 544	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:setCursorPosition	(I)Z
/*      */     //   1958: ifeq +14 -> 1972
/*      */     //   1961: aload_0
/*      */     //   1962: invokevirtual 518	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:killLine	()Z
/*      */     //   1965: ifeq +7 -> 1972
/*      */     //   1968: iconst_1
/*      */     //   1969: goto +4 -> 1973
/*      */     //   1972: iconst_0
/*      */     //   1973: istore 5
/*      */     //   1975: goto +2214 -> 4189
/*      */     //   1978: aload_0
/*      */     //   1979: invokevirtual 1179	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:clearScreen	()Z
/*      */     //   1982: istore 5
/*      */     //   1984: aload_0
/*      */     //   1985: invokevirtual 951	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:redrawLine	()V
/*      */     //   1988: goto +2201 -> 4189
/*      */     //   1991: aload_0
/*      */     //   1992: getfield 152	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:buf	Lorg/bukkit/craftbukkit/libs/jline/console/CursorBuffer;
/*      */     //   1995: aload_0
/*      */     //   1996: getfield 152	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:buf	Lorg/bukkit/craftbukkit/libs/jline/console/CursorBuffer;
/*      */     //   1999: invokevirtual 1182	org/bukkit/craftbukkit/libs/jline/console/CursorBuffer:isOverTyping	()Z
/*      */     //   2002: ifne +7 -> 2009
/*      */     //   2005: iconst_1
/*      */     //   2006: goto +4 -> 2010
/*      */     //   2009: iconst_0
/*      */     //   2010: invokevirtual 1185	org/bukkit/craftbukkit/libs/jline/console/CursorBuffer:setOverTyping	(Z)V
/*      */     //   2013: goto +2176 -> 4189
/*      */     //   2016: aload_0
/*      */     //   2017: aload 6
/*      */     //   2019: invokevirtual 529	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:putString	(Ljava/lang/CharSequence;)V
/*      */     //   2022: goto +2167 -> 4189
/*      */     //   2025: aload_0
/*      */     //   2026: invokevirtual 871	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:accept	()Ljava/lang/String;
/*      */     //   2029: astore 16
/*      */     //   2031: aload_0
/*      */     //   2032: getfield 246	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:terminal	Lorg/bukkit/craftbukkit/libs/jline/Terminal;
/*      */     //   2035: invokeinterface 337 1 0
/*      */     //   2040: ifne +7 -> 2047
/*      */     //   2043: aload_0
/*      */     //   2044: invokespecial 1034	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:afterReadLine	()V
/*      */     //   2047: aload_0
/*      */     //   2048: getfield 164	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:handleUserInterrupt	Z
/*      */     //   2051: ifeq +23 -> 2074
/*      */     //   2054: aload_0
/*      */     //   2055: getfield 246	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:terminal	Lorg/bukkit/craftbukkit/libs/jline/Terminal;
/*      */     //   2058: instanceof 1036
/*      */     //   2061: ifeq +13 -> 2074
/*      */     //   2064: aload_0
/*      */     //   2065: getfield 246	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:terminal	Lorg/bukkit/craftbukkit/libs/jline/Terminal;
/*      */     //   2068: checkcast 1036	org/bukkit/craftbukkit/libs/jline/UnixTerminal
/*      */     //   2071: invokevirtual 1039	org/bukkit/craftbukkit/libs/jline/UnixTerminal:enableInterruptCharacter	()V
/*      */     //   2074: aload 16
/*      */     //   2076: areturn
/*      */     //   2077: aload_0
/*      */     //   2078: getfield 166	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchTerm	Ljava/lang/StringBuffer;
/*      */     //   2081: ifnonnull +2108 -> 4189
/*      */     //   2084: aload_0
/*      */     //   2085: invokespecial 1187	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:abort	()V
/*      */     //   2088: goto +2101 -> 4189
/*      */     //   2091: aload_0
/*      */     //   2092: getfield 164	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:handleUserInterrupt	Z
/*      */     //   2095: ifeq +2094 -> 4189
/*      */     //   2098: aload_0
/*      */     //   2099: invokevirtual 663	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:println	()V
/*      */     //   2102: aload_0
/*      */     //   2103: invokevirtual 664	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:flush	()V
/*      */     //   2106: aload_0
/*      */     //   2107: getfield 152	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:buf	Lorg/bukkit/craftbukkit/libs/jline/console/CursorBuffer;
/*      */     //   2110: getfield 500	org/bukkit/craftbukkit/libs/jline/console/CursorBuffer:buffer	Ljava/lang/StringBuilder;
/*      */     //   2113: invokevirtual 445	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*      */     //   2116: astore 16
/*      */     //   2118: aload_0
/*      */     //   2119: getfield 152	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:buf	Lorg/bukkit/craftbukkit/libs/jline/console/CursorBuffer;
/*      */     //   2122: invokevirtual 600	org/bukkit/craftbukkit/libs/jline/console/CursorBuffer:clear	()Z
/*      */     //   2125: pop
/*      */     //   2126: aload_0
/*      */     //   2127: getfield 221	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:history	Lorg/bukkit/craftbukkit/libs/jline/console/history/History;
/*      */     //   2130: invokeinterface 610 1 0
/*      */     //   2135: new 1189	org/bukkit/craftbukkit/libs/jline/console/UserInterruptException
/*      */     //   2138: dup
/*      */     //   2139: aload 16
/*      */     //   2141: invokespecial 1190	org/bukkit/craftbukkit/libs/jline/console/UserInterruptException:<init>	(Ljava/lang/String;)V
/*      */     //   2144: athrow
/*      */     //   2145: aload_0
/*      */     //   2146: getfield 279	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:consoleKeys	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleKeys;
/*      */     //   2149: ldc_w 864
/*      */     //   2152: invokevirtual 868	org/bukkit/craftbukkit/libs/jline/console/ConsoleKeys:setKeyMap	(Ljava/lang/String;)Z
/*      */     //   2155: pop
/*      */     //   2156: aload_0
/*      */     //   2157: invokevirtual 871	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:accept	()Ljava/lang/String;
/*      */     //   2160: astore 16
/*      */     //   2162: aload_0
/*      */     //   2163: getfield 246	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:terminal	Lorg/bukkit/craftbukkit/libs/jline/Terminal;
/*      */     //   2166: invokeinterface 337 1 0
/*      */     //   2171: ifne +7 -> 2178
/*      */     //   2174: aload_0
/*      */     //   2175: invokespecial 1034	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:afterReadLine	()V
/*      */     //   2178: aload_0
/*      */     //   2179: getfield 164	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:handleUserInterrupt	Z
/*      */     //   2182: ifeq +23 -> 2205
/*      */     //   2185: aload_0
/*      */     //   2186: getfield 246	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:terminal	Lorg/bukkit/craftbukkit/libs/jline/Terminal;
/*      */     //   2189: instanceof 1036
/*      */     //   2192: ifeq +13 -> 2205
/*      */     //   2195: aload_0
/*      */     //   2196: getfield 246	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:terminal	Lorg/bukkit/craftbukkit/libs/jline/Terminal;
/*      */     //   2199: checkcast 1036	org/bukkit/craftbukkit/libs/jline/UnixTerminal
/*      */     //   2202: invokevirtual 1039	org/bukkit/craftbukkit/libs/jline/UnixTerminal:enableInterruptCharacter	()V
/*      */     //   2205: aload 16
/*      */     //   2207: areturn
/*      */     //   2208: aload_0
/*      */     //   2209: invokespecial 1192	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:previousWord	()Z
/*      */     //   2212: istore 5
/*      */     //   2214: goto +1975 -> 4189
/*      */     //   2217: aload_0
/*      */     //   2218: invokespecial 1194	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:nextWord	()Z
/*      */     //   2221: istore 5
/*      */     //   2223: goto +1966 -> 4189
/*      */     //   2226: aload_0
/*      */     //   2227: iconst_0
/*      */     //   2228: invokespecial 1198	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:moveHistory	(Z)Z
/*      */     //   2231: istore 5
/*      */     //   2233: goto +1956 -> 4189
/*      */     //   2236: aload_0
/*      */     //   2237: iconst_0
/*      */     //   2238: iload 11
/*      */     //   2240: invokespecial 1201	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:moveHistory	(ZI)Z
/*      */     //   2243: ifeq +15 -> 2258
/*      */     //   2246: aload_0
/*      */     //   2247: iconst_0
/*      */     //   2248: invokevirtual 544	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:setCursorPosition	(I)Z
/*      */     //   2251: ifeq +7 -> 2258
/*      */     //   2254: iconst_1
/*      */     //   2255: goto +4 -> 2259
/*      */     //   2258: iconst_0
/*      */     //   2259: istore 5
/*      */     //   2261: goto +1928 -> 4189
/*      */     //   2264: aload_0
/*      */     //   2265: iconst_1
/*      */     //   2266: invokespecial 1198	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:moveHistory	(Z)Z
/*      */     //   2269: istore 5
/*      */     //   2271: goto +1918 -> 4189
/*      */     //   2274: aload_0
/*      */     //   2275: iconst_1
/*      */     //   2276: iload 11
/*      */     //   2278: invokespecial 1201	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:moveHistory	(ZI)Z
/*      */     //   2281: ifeq +15 -> 2296
/*      */     //   2284: aload_0
/*      */     //   2285: iconst_0
/*      */     //   2286: invokevirtual 544	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:setCursorPosition	(I)Z
/*      */     //   2289: ifeq +7 -> 2296
/*      */     //   2292: iconst_1
/*      */     //   2293: goto +4 -> 2297
/*      */     //   2296: iconst_0
/*      */     //   2297: istore 5
/*      */     //   2299: goto +1890 -> 4189
/*      */     //   2302: aload_0
/*      */     //   2303: invokevirtual 438	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:backspace	()Z
/*      */     //   2306: istore 5
/*      */     //   2308: goto +1881 -> 4189
/*      */     //   2311: aload_0
/*      */     //   2312: getfield 152	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:buf	Lorg/bukkit/craftbukkit/libs/jline/console/CursorBuffer;
/*      */     //   2315: getfield 500	org/bukkit/craftbukkit/libs/jline/console/CursorBuffer:buffer	Ljava/lang/StringBuilder;
/*      */     //   2318: invokevirtual 505	java/lang/StringBuilder:length	()I
/*      */     //   2321: ifne +52 -> 2373
/*      */     //   2324: aconst_null
/*      */     //   2325: astore 16
/*      */     //   2327: aload_0
/*      */     //   2328: getfield 246	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:terminal	Lorg/bukkit/craftbukkit/libs/jline/Terminal;
/*      */     //   2331: invokeinterface 337 1 0
/*      */     //   2336: ifne +7 -> 2343
/*      */     //   2339: aload_0
/*      */     //   2340: invokespecial 1034	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:afterReadLine	()V
/*      */     //   2343: aload_0
/*      */     //   2344: getfield 164	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:handleUserInterrupt	Z
/*      */     //   2347: ifeq +23 -> 2370
/*      */     //   2350: aload_0
/*      */     //   2351: getfield 246	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:terminal	Lorg/bukkit/craftbukkit/libs/jline/Terminal;
/*      */     //   2354: instanceof 1036
/*      */     //   2357: ifeq +13 -> 2370
/*      */     //   2360: aload_0
/*      */     //   2361: getfield 246	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:terminal	Lorg/bukkit/craftbukkit/libs/jline/Terminal;
/*      */     //   2364: checkcast 1036	org/bukkit/craftbukkit/libs/jline/UnixTerminal
/*      */     //   2367: invokevirtual 1039	org/bukkit/craftbukkit/libs/jline/UnixTerminal:enableInterruptCharacter	()V
/*      */     //   2370: aload 16
/*      */     //   2372: areturn
/*      */     //   2373: aload_0
/*      */     //   2374: invokespecial 789	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:deleteCurrentCharacter	()Z
/*      */     //   2377: istore 5
/*      */     //   2379: goto +1810 -> 4189
/*      */     //   2382: aload_0
/*      */     //   2383: invokespecial 789	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:deleteCurrentCharacter	()Z
/*      */     //   2386: istore 5
/*      */     //   2388: goto +1801 -> 4189
/*      */     //   2391: aload_0
/*      */     //   2392: iload 11
/*      */     //   2394: ineg
/*      */     //   2395: invokevirtual 495	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:moveCursor	(I)I
/*      */     //   2398: ifeq +7 -> 2405
/*      */     //   2401: iconst_1
/*      */     //   2402: goto +4 -> 2406
/*      */     //   2405: iconst_0
/*      */     //   2406: istore 5
/*      */     //   2408: goto +1781 -> 4189
/*      */     //   2411: aload_0
/*      */     //   2412: iload 11
/*      */     //   2414: invokevirtual 495	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:moveCursor	(I)I
/*      */     //   2417: ifeq +7 -> 2424
/*      */     //   2420: iconst_1
/*      */     //   2421: goto +4 -> 2425
/*      */     //   2424: iconst_0
/*      */     //   2425: istore 5
/*      */     //   2427: goto +1762 -> 4189
/*      */     //   2430: aload_0
/*      */     //   2431: invokevirtual 1203	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:resetLine	()Z
/*      */     //   2434: istore 5
/*      */     //   2436: goto +1753 -> 4189
/*      */     //   2439: aload_0
/*      */     //   2440: iload 11
/*      */     //   2442: invokespecial 1205	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:unixWordRubout	(I)Z
/*      */     //   2445: istore 5
/*      */     //   2447: goto +1742 -> 4189
/*      */     //   2450: aload_0
/*      */     //   2451: invokespecial 1207	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:deletePreviousWord	()Z
/*      */     //   2454: istore 5
/*      */     //   2456: goto +1733 -> 4189
/*      */     //   2459: aload_0
/*      */     //   2460: invokespecial 1209	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:deleteNextWord	()Z
/*      */     //   2463: istore 5
/*      */     //   2465: goto +1724 -> 4189
/*      */     //   2468: aload_0
/*      */     //   2469: getfield 221	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:history	Lorg/bukkit/craftbukkit/libs/jline/console/history/History;
/*      */     //   2472: invokeinterface 1212 1 0
/*      */     //   2477: istore 5
/*      */     //   2479: iload 5
/*      */     //   2481: ifeq +1708 -> 4189
/*      */     //   2484: aload_0
/*      */     //   2485: aload_0
/*      */     //   2486: getfield 221	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:history	Lorg/bukkit/craftbukkit/libs/jline/console/history/History;
/*      */     //   2489: invokeinterface 1149 1 0
/*      */     //   2494: invokespecial 1214	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:setBuffer	(Ljava/lang/CharSequence;)V
/*      */     //   2497: goto +1692 -> 4189
/*      */     //   2500: aload_0
/*      */     //   2501: getfield 221	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:history	Lorg/bukkit/craftbukkit/libs/jline/console/history/History;
/*      */     //   2504: invokeinterface 1217 1 0
/*      */     //   2509: istore 5
/*      */     //   2511: iload 5
/*      */     //   2513: ifeq +1676 -> 4189
/*      */     //   2516: aload_0
/*      */     //   2517: aload_0
/*      */     //   2518: getfield 221	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:history	Lorg/bukkit/craftbukkit/libs/jline/console/history/History;
/*      */     //   2521: invokeinterface 1149 1 0
/*      */     //   2526: invokespecial 1214	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:setBuffer	(Ljava/lang/CharSequence;)V
/*      */     //   2529: goto +1660 -> 4189
/*      */     //   2532: aload_0
/*      */     //   2533: new 1126	java/lang/StringBuffer
/*      */     //   2536: dup
/*      */     //   2537: aload_0
/*      */     //   2538: getfield 152	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:buf	Lorg/bukkit/craftbukkit/libs/jline/console/CursorBuffer;
/*      */     //   2541: invokevirtual 1220	org/bukkit/craftbukkit/libs/jline/console/CursorBuffer:upToCursor	()Ljava/lang/String;
/*      */     //   2544: invokespecial 1221	java/lang/StringBuffer:<init>	(Ljava/lang/String;)V
/*      */     //   2547: putfield 166	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchTerm	Ljava/lang/StringBuffer;
/*      */     //   2550: aload_0
/*      */     //   2551: aload_0
/*      */     //   2552: aload_0
/*      */     //   2553: getfield 166	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchTerm	Ljava/lang/StringBuffer;
/*      */     //   2556: invokevirtual 1131	java/lang/StringBuffer:toString	()Ljava/lang/String;
/*      */     //   2559: aload_0
/*      */     //   2560: getfield 221	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:history	Lorg/bukkit/craftbukkit/libs/jline/console/history/History;
/*      */     //   2563: invokeinterface 623 1 0
/*      */     //   2568: iconst_1
/*      */     //   2569: invokevirtual 660	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchBackwards	(Ljava/lang/String;IZ)I
/*      */     //   2572: putfield 172	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchIndex	I
/*      */     //   2575: aload_0
/*      */     //   2576: getfield 172	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchIndex	I
/*      */     //   2579: iconst_m1
/*      */     //   2580: if_icmpne +10 -> 2590
/*      */     //   2583: aload_0
/*      */     //   2584: invokevirtual 597	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:beep	()V
/*      */     //   2587: goto +1602 -> 4189
/*      */     //   2590: aload_0
/*      */     //   2591: getfield 221	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:history	Lorg/bukkit/craftbukkit/libs/jline/console/history/History;
/*      */     //   2594: aload_0
/*      */     //   2595: getfield 172	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchIndex	I
/*      */     //   2598: invokeinterface 1146 2 0
/*      */     //   2603: istore 5
/*      */     //   2605: iload 5
/*      */     //   2607: ifeq +1582 -> 4189
/*      */     //   2610: aload_0
/*      */     //   2611: aload_0
/*      */     //   2612: getfield 221	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:history	Lorg/bukkit/craftbukkit/libs/jline/console/history/History;
/*      */     //   2615: invokeinterface 1149 1 0
/*      */     //   2620: invokespecial 1223	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:setBufferKeepPos	(Ljava/lang/CharSequence;)V
/*      */     //   2623: goto +1566 -> 4189
/*      */     //   2626: aload_0
/*      */     //   2627: new 1126	java/lang/StringBuffer
/*      */     //   2630: dup
/*      */     //   2631: aload_0
/*      */     //   2632: getfield 152	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:buf	Lorg/bukkit/craftbukkit/libs/jline/console/CursorBuffer;
/*      */     //   2635: invokevirtual 1220	org/bukkit/craftbukkit/libs/jline/console/CursorBuffer:upToCursor	()Ljava/lang/String;
/*      */     //   2638: invokespecial 1221	java/lang/StringBuffer:<init>	(Ljava/lang/String;)V
/*      */     //   2641: putfield 166	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchTerm	Ljava/lang/StringBuffer;
/*      */     //   2644: aload_0
/*      */     //   2645: getfield 221	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:history	Lorg/bukkit/craftbukkit/libs/jline/console/history/History;
/*      */     //   2648: invokeinterface 623 1 0
/*      */     //   2653: iconst_1
/*      */     //   2654: iadd
/*      */     //   2655: istore 16
/*      */     //   2657: iload 16
/*      */     //   2659: aload_0
/*      */     //   2660: getfield 221	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:history	Lorg/bukkit/craftbukkit/libs/jline/console/history/History;
/*      */     //   2663: invokeinterface 617 1 0
/*      */     //   2668: if_icmpne +26 -> 2694
/*      */     //   2671: aload_0
/*      */     //   2672: getfield 221	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:history	Lorg/bukkit/craftbukkit/libs/jline/console/history/History;
/*      */     //   2675: invokeinterface 610 1 0
/*      */     //   2680: aload_0
/*      */     //   2681: aload_0
/*      */     //   2682: getfield 166	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchTerm	Ljava/lang/StringBuffer;
/*      */     //   2685: invokevirtual 1131	java/lang/StringBuffer:toString	()Ljava/lang/String;
/*      */     //   2688: invokespecial 547	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:setBufferKeepPos	(Ljava/lang/String;)V
/*      */     //   2691: goto +1498 -> 4189
/*      */     //   2694: iload 16
/*      */     //   2696: aload_0
/*      */     //   2697: getfield 221	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:history	Lorg/bukkit/craftbukkit/libs/jline/console/history/History;
/*      */     //   2700: invokeinterface 617 1 0
/*      */     //   2705: if_icmpge +1484 -> 4189
/*      */     //   2708: aload_0
/*      */     //   2709: aload_0
/*      */     //   2710: aload_0
/*      */     //   2711: getfield 166	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchTerm	Ljava/lang/StringBuffer;
/*      */     //   2714: invokevirtual 1131	java/lang/StringBuffer:toString	()Ljava/lang/String;
/*      */     //   2717: iload 16
/*      */     //   2719: iconst_1
/*      */     //   2720: invokevirtual 1225	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchForwards	(Ljava/lang/String;IZ)I
/*      */     //   2723: putfield 172	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchIndex	I
/*      */     //   2726: aload_0
/*      */     //   2727: getfield 172	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchIndex	I
/*      */     //   2730: iconst_m1
/*      */     //   2731: if_icmpne +10 -> 2741
/*      */     //   2734: aload_0
/*      */     //   2735: invokevirtual 597	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:beep	()V
/*      */     //   2738: goto +1451 -> 4189
/*      */     //   2741: aload_0
/*      */     //   2742: getfield 221	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:history	Lorg/bukkit/craftbukkit/libs/jline/console/history/History;
/*      */     //   2745: aload_0
/*      */     //   2746: getfield 172	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchIndex	I
/*      */     //   2749: invokeinterface 1146 2 0
/*      */     //   2754: istore 5
/*      */     //   2756: iload 5
/*      */     //   2758: ifeq +1431 -> 4189
/*      */     //   2761: aload_0
/*      */     //   2762: aload_0
/*      */     //   2763: getfield 221	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:history	Lorg/bukkit/craftbukkit/libs/jline/console/history/History;
/*      */     //   2766: invokeinterface 1149 1 0
/*      */     //   2771: invokespecial 1223	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:setBufferKeepPos	(Ljava/lang/CharSequence;)V
/*      */     //   2774: goto +1415 -> 4189
/*      */     //   2777: aload_0
/*      */     //   2778: getfield 166	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchTerm	Ljava/lang/StringBuffer;
/*      */     //   2781: ifnull +14 -> 2795
/*      */     //   2784: aload_0
/*      */     //   2785: aload_0
/*      */     //   2786: getfield 166	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchTerm	Ljava/lang/StringBuffer;
/*      */     //   2789: invokevirtual 1131	java/lang/StringBuffer:toString	()Ljava/lang/String;
/*      */     //   2792: putfield 170	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:previousSearchTerm	Ljava/lang/String;
/*      */     //   2795: aload_0
/*      */     //   2796: new 1126	java/lang/StringBuffer
/*      */     //   2799: dup
/*      */     //   2800: aload_0
/*      */     //   2801: getfield 152	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:buf	Lorg/bukkit/craftbukkit/libs/jline/console/CursorBuffer;
/*      */     //   2804: getfield 500	org/bukkit/craftbukkit/libs/jline/console/CursorBuffer:buffer	Ljava/lang/StringBuilder;
/*      */     //   2807: invokespecial 1227	java/lang/StringBuffer:<init>	(Ljava/lang/CharSequence;)V
/*      */     //   2810: putfield 166	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchTerm	Ljava/lang/StringBuffer;
/*      */     //   2813: aload_0
/*      */     //   2814: getstatic 1118	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State:SEARCH	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   2817: putfield 200	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:state	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   2820: aload_0
/*      */     //   2821: getfield 166	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchTerm	Ljava/lang/StringBuffer;
/*      */     //   2824: invokevirtual 1127	java/lang/StringBuffer:length	()I
/*      */     //   2827: ifle +75 -> 2902
/*      */     //   2830: aload_0
/*      */     //   2831: aload_0
/*      */     //   2832: aload_0
/*      */     //   2833: getfield 166	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchTerm	Ljava/lang/StringBuffer;
/*      */     //   2836: invokevirtual 1131	java/lang/StringBuffer:toString	()Ljava/lang/String;
/*      */     //   2839: invokevirtual 639	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchBackwards	(Ljava/lang/String;)I
/*      */     //   2842: putfield 172	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchIndex	I
/*      */     //   2845: aload_0
/*      */     //   2846: getfield 172	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchIndex	I
/*      */     //   2849: iconst_m1
/*      */     //   2850: if_icmpne +7 -> 2857
/*      */     //   2853: aload_0
/*      */     //   2854: invokevirtual 597	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:beep	()V
/*      */     //   2857: aload_0
/*      */     //   2858: aload_0
/*      */     //   2859: getfield 166	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchTerm	Ljava/lang/StringBuffer;
/*      */     //   2862: invokevirtual 1131	java/lang/StringBuffer:toString	()Ljava/lang/String;
/*      */     //   2865: aload_0
/*      */     //   2866: getfield 172	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchIndex	I
/*      */     //   2869: iconst_m1
/*      */     //   2870: if_icmple +24 -> 2894
/*      */     //   2873: aload_0
/*      */     //   2874: getfield 221	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:history	Lorg/bukkit/craftbukkit/libs/jline/console/history/History;
/*      */     //   2877: aload_0
/*      */     //   2878: getfield 172	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchIndex	I
/*      */     //   2881: invokeinterface 626 2 0
/*      */     //   2886: invokeinterface 629 1 0
/*      */     //   2891: goto +5 -> 2896
/*      */     //   2894: ldc -88
/*      */     //   2896: invokevirtual 1155	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:printSearchStatus	(Ljava/lang/String;Ljava/lang/String;)V
/*      */     //   2899: goto +1290 -> 4189
/*      */     //   2902: aload_0
/*      */     //   2903: iconst_m1
/*      */     //   2904: putfield 172	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchIndex	I
/*      */     //   2907: aload_0
/*      */     //   2908: ldc -88
/*      */     //   2910: ldc -88
/*      */     //   2912: invokevirtual 1155	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:printSearchStatus	(Ljava/lang/String;Ljava/lang/String;)V
/*      */     //   2915: goto +1274 -> 4189
/*      */     //   2918: aload_0
/*      */     //   2919: getfield 166	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchTerm	Ljava/lang/StringBuffer;
/*      */     //   2922: ifnull +14 -> 2936
/*      */     //   2925: aload_0
/*      */     //   2926: aload_0
/*      */     //   2927: getfield 166	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchTerm	Ljava/lang/StringBuffer;
/*      */     //   2930: invokevirtual 1131	java/lang/StringBuffer:toString	()Ljava/lang/String;
/*      */     //   2933: putfield 170	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:previousSearchTerm	Ljava/lang/String;
/*      */     //   2936: aload_0
/*      */     //   2937: new 1126	java/lang/StringBuffer
/*      */     //   2940: dup
/*      */     //   2941: aload_0
/*      */     //   2942: getfield 152	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:buf	Lorg/bukkit/craftbukkit/libs/jline/console/CursorBuffer;
/*      */     //   2945: getfield 500	org/bukkit/craftbukkit/libs/jline/console/CursorBuffer:buffer	Ljava/lang/StringBuilder;
/*      */     //   2948: invokespecial 1227	java/lang/StringBuffer:<init>	(Ljava/lang/CharSequence;)V
/*      */     //   2951: putfield 166	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchTerm	Ljava/lang/StringBuffer;
/*      */     //   2954: aload_0
/*      */     //   2955: getstatic 1121	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State:FORWARD_SEARCH	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   2958: putfield 200	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:state	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   2961: aload_0
/*      */     //   2962: getfield 166	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchTerm	Ljava/lang/StringBuffer;
/*      */     //   2965: invokevirtual 1127	java/lang/StringBuffer:length	()I
/*      */     //   2968: ifle +75 -> 3043
/*      */     //   2971: aload_0
/*      */     //   2972: aload_0
/*      */     //   2973: aload_0
/*      */     //   2974: getfield 166	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchTerm	Ljava/lang/StringBuffer;
/*      */     //   2977: invokevirtual 1131	java/lang/StringBuffer:toString	()Ljava/lang/String;
/*      */     //   2980: invokevirtual 1141	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchForwards	(Ljava/lang/String;)I
/*      */     //   2983: putfield 172	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchIndex	I
/*      */     //   2986: aload_0
/*      */     //   2987: getfield 172	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchIndex	I
/*      */     //   2990: iconst_m1
/*      */     //   2991: if_icmpne +7 -> 2998
/*      */     //   2994: aload_0
/*      */     //   2995: invokevirtual 597	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:beep	()V
/*      */     //   2998: aload_0
/*      */     //   2999: aload_0
/*      */     //   3000: getfield 166	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchTerm	Ljava/lang/StringBuffer;
/*      */     //   3003: invokevirtual 1131	java/lang/StringBuffer:toString	()Ljava/lang/String;
/*      */     //   3006: aload_0
/*      */     //   3007: getfield 172	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchIndex	I
/*      */     //   3010: iconst_m1
/*      */     //   3011: if_icmple +24 -> 3035
/*      */     //   3014: aload_0
/*      */     //   3015: getfield 221	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:history	Lorg/bukkit/craftbukkit/libs/jline/console/history/History;
/*      */     //   3018: aload_0
/*      */     //   3019: getfield 172	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchIndex	I
/*      */     //   3022: invokeinterface 626 2 0
/*      */     //   3027: invokeinterface 629 1 0
/*      */     //   3032: goto +5 -> 3037
/*      */     //   3035: ldc -88
/*      */     //   3037: invokevirtual 1158	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:printForwardSearchStatus	(Ljava/lang/String;Ljava/lang/String;)V
/*      */     //   3040: goto +1149 -> 4189
/*      */     //   3043: aload_0
/*      */     //   3044: iconst_m1
/*      */     //   3045: putfield 172	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchIndex	I
/*      */     //   3048: aload_0
/*      */     //   3049: ldc -88
/*      */     //   3051: ldc -88
/*      */     //   3053: invokevirtual 1158	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:printForwardSearchStatus	(Ljava/lang/String;Ljava/lang/String;)V
/*      */     //   3056: goto +1133 -> 4189
/*      */     //   3059: aload_0
/*      */     //   3060: invokespecial 1229	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:capitalizeWord	()Z
/*      */     //   3063: istore 5
/*      */     //   3065: goto +1124 -> 4189
/*      */     //   3068: aload_0
/*      */     //   3069: invokespecial 1231	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:upCaseWord	()Z
/*      */     //   3072: istore 5
/*      */     //   3074: goto +1115 -> 4189
/*      */     //   3077: aload_0
/*      */     //   3078: invokespecial 1233	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:downCaseWord	()Z
/*      */     //   3081: istore 5
/*      */     //   3083: goto +1106 -> 4189
/*      */     //   3086: aload_0
/*      */     //   3087: invokevirtual 513	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:moveToEnd	()Z
/*      */     //   3090: istore 5
/*      */     //   3092: goto +1097 -> 4189
/*      */     //   3095: aload_0
/*      */     //   3096: ldc_w 1235
/*      */     //   3099: invokevirtual 529	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:putString	(Ljava/lang/CharSequence;)V
/*      */     //   3102: goto +1087 -> 4189
/*      */     //   3105: aload_0
/*      */     //   3106: getfield 279	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:consoleKeys	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleKeys;
/*      */     //   3109: aload_0
/*      */     //   3110: getfield 232	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:appName	Ljava/lang/String;
/*      */     //   3113: aload_0
/*      */     //   3114: getfield 272	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:inputrcUrl	Ljava/net/URL;
/*      */     //   3117: invokevirtual 1238	org/bukkit/craftbukkit/libs/jline/console/ConsoleKeys:loadKeys	(Ljava/lang/String;Ljava/net/URL;)V
/*      */     //   3120: goto +1069 -> 4189
/*      */     //   3123: aload_0
/*      */     //   3124: iconst_1
/*      */     //   3125: putfield 1057	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:recording	Z
/*      */     //   3128: goto +1061 -> 4189
/*      */     //   3131: aload_0
/*      */     //   3132: iconst_0
/*      */     //   3133: putfield 1057	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:recording	Z
/*      */     //   3136: aload_0
/*      */     //   3137: aload_0
/*      */     //   3138: getfield 189	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:macro	Ljava/lang/String;
/*      */     //   3141: iconst_0
/*      */     //   3142: aload_0
/*      */     //   3143: getfield 189	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:macro	Ljava/lang/String;
/*      */     //   3146: invokevirtual 410	java/lang/String:length	()I
/*      */     //   3149: aload 6
/*      */     //   3151: invokevirtual 505	java/lang/StringBuilder:length	()I
/*      */     //   3154: isub
/*      */     //   3155: invokevirtual 462	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   3158: putfield 189	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:macro	Ljava/lang/String;
/*      */     //   3161: goto +1028 -> 4189
/*      */     //   3164: iconst_0
/*      */     //   3165: istore 17
/*      */     //   3167: iload 17
/*      */     //   3169: aload_0
/*      */     //   3170: getfield 189	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:macro	Ljava/lang/String;
/*      */     //   3173: invokevirtual 410	java/lang/String:length	()I
/*      */     //   3176: if_icmpge +37 -> 3213
/*      */     //   3179: aload 7
/*      */     //   3181: aload_0
/*      */     //   3182: getfield 189	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:macro	Ljava/lang/String;
/*      */     //   3185: aload_0
/*      */     //   3186: getfield 189	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:macro	Ljava/lang/String;
/*      */     //   3189: invokevirtual 410	java/lang/String:length	()I
/*      */     //   3192: iconst_1
/*      */     //   3193: isub
/*      */     //   3194: iload 17
/*      */     //   3196: isub
/*      */     //   3197: invokevirtual 509	java/lang/String:charAt	(I)C
/*      */     //   3200: invokestatic 1104	java/lang/Character:valueOf	(C)Ljava/lang/Character;
/*      */     //   3203: invokevirtual 1107	java/util/Stack:push	(Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   3206: pop
/*      */     //   3207: iinc 17 1
/*      */     //   3210: goto -43 -> 3167
/*      */     //   3213: aload 6
/*      */     //   3215: iconst_0
/*      */     //   3216: invokevirtual 522	java/lang/StringBuilder:setLength	(I)V
/*      */     //   3219: goto +970 -> 4189
/*      */     //   3222: aload_0
/*      */     //   3223: getfield 279	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:consoleKeys	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleKeys;
/*      */     //   3226: ldc_w 864
/*      */     //   3229: invokevirtual 868	org/bukkit/craftbukkit/libs/jline/console/ConsoleKeys:setKeyMap	(Ljava/lang/String;)Z
/*      */     //   3232: pop
/*      */     //   3233: goto +956 -> 4189
/*      */     //   3236: aload_0
/*      */     //   3237: getfield 200	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:state	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   3240: aload_0
/*      */     //   3241: getfield 200	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:state	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   3244: pop
/*      */     //   3245: getstatic 198	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State:NORMAL	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   3248: if_acmpne +9 -> 3257
/*      */     //   3251: aload_0
/*      */     //   3252: iconst_m1
/*      */     //   3253: invokevirtual 495	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:moveCursor	(I)I
/*      */     //   3256: pop
/*      */     //   3257: aload_0
/*      */     //   3258: getfield 279	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:consoleKeys	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleKeys;
/*      */     //   3261: ldc_w 1240
/*      */     //   3264: invokevirtual 868	org/bukkit/craftbukkit/libs/jline/console/ConsoleKeys:setKeyMap	(Ljava/lang/String;)Z
/*      */     //   3267: pop
/*      */     //   3268: goto +921 -> 4189
/*      */     //   3271: aload_0
/*      */     //   3272: getfield 279	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:consoleKeys	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleKeys;
/*      */     //   3275: ldc_w 864
/*      */     //   3278: invokevirtual 868	org/bukkit/craftbukkit/libs/jline/console/ConsoleKeys:setKeyMap	(Ljava/lang/String;)Z
/*      */     //   3281: pop
/*      */     //   3282: goto +907 -> 4189
/*      */     //   3285: aload_0
/*      */     //   3286: iconst_1
/*      */     //   3287: invokevirtual 495	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:moveCursor	(I)I
/*      */     //   3290: pop
/*      */     //   3291: aload_0
/*      */     //   3292: getfield 279	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:consoleKeys	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleKeys;
/*      */     //   3295: ldc_w 864
/*      */     //   3298: invokevirtual 868	org/bukkit/craftbukkit/libs/jline/console/ConsoleKeys:setKeyMap	(Ljava/lang/String;)Z
/*      */     //   3301: pop
/*      */     //   3302: goto +887 -> 4189
/*      */     //   3305: aload_0
/*      */     //   3306: invokevirtual 513	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:moveToEnd	()Z
/*      */     //   3309: istore 5
/*      */     //   3311: aload_0
/*      */     //   3312: getfield 279	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:consoleKeys	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleKeys;
/*      */     //   3315: ldc_w 864
/*      */     //   3318: invokevirtual 868	org/bukkit/craftbukkit/libs/jline/console/ConsoleKeys:setKeyMap	(Ljava/lang/String;)Z
/*      */     //   3321: pop
/*      */     //   3322: goto +867 -> 4189
/*      */     //   3325: aload_0
/*      */     //   3326: getfield 152	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:buf	Lorg/bukkit/craftbukkit/libs/jline/console/CursorBuffer;
/*      */     //   3329: getfield 500	org/bukkit/craftbukkit/libs/jline/console/CursorBuffer:buffer	Ljava/lang/StringBuilder;
/*      */     //   3332: invokevirtual 505	java/lang/StringBuilder:length	()I
/*      */     //   3335: ifne +52 -> 3387
/*      */     //   3338: aconst_null
/*      */     //   3339: astore 17
/*      */     //   3341: aload_0
/*      */     //   3342: getfield 246	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:terminal	Lorg/bukkit/craftbukkit/libs/jline/Terminal;
/*      */     //   3345: invokeinterface 337 1 0
/*      */     //   3350: ifne +7 -> 3357
/*      */     //   3353: aload_0
/*      */     //   3354: invokespecial 1034	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:afterReadLine	()V
/*      */     //   3357: aload_0
/*      */     //   3358: getfield 164	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:handleUserInterrupt	Z
/*      */     //   3361: ifeq +23 -> 3384
/*      */     //   3364: aload_0
/*      */     //   3365: getfield 246	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:terminal	Lorg/bukkit/craftbukkit/libs/jline/Terminal;
/*      */     //   3368: instanceof 1036
/*      */     //   3371: ifeq +13 -> 3384
/*      */     //   3374: aload_0
/*      */     //   3375: getfield 246	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:terminal	Lorg/bukkit/craftbukkit/libs/jline/Terminal;
/*      */     //   3378: checkcast 1036	org/bukkit/craftbukkit/libs/jline/UnixTerminal
/*      */     //   3381: invokevirtual 1039	org/bukkit/craftbukkit/libs/jline/UnixTerminal:enableInterruptCharacter	()V
/*      */     //   3384: aload 17
/*      */     //   3386: areturn
/*      */     //   3387: aload_0
/*      */     //   3388: invokevirtual 871	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:accept	()Ljava/lang/String;
/*      */     //   3391: astore 17
/*      */     //   3393: aload_0
/*      */     //   3394: getfield 246	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:terminal	Lorg/bukkit/craftbukkit/libs/jline/Terminal;
/*      */     //   3397: invokeinterface 337 1 0
/*      */     //   3402: ifne +7 -> 3409
/*      */     //   3405: aload_0
/*      */     //   3406: invokespecial 1034	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:afterReadLine	()V
/*      */     //   3409: aload_0
/*      */     //   3410: getfield 164	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:handleUserInterrupt	Z
/*      */     //   3413: ifeq +23 -> 3436
/*      */     //   3416: aload_0
/*      */     //   3417: getfield 246	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:terminal	Lorg/bukkit/craftbukkit/libs/jline/Terminal;
/*      */     //   3420: instanceof 1036
/*      */     //   3423: ifeq +13 -> 3436
/*      */     //   3426: aload_0
/*      */     //   3427: getfield 246	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:terminal	Lorg/bukkit/craftbukkit/libs/jline/Terminal;
/*      */     //   3430: checkcast 1036	org/bukkit/craftbukkit/libs/jline/UnixTerminal
/*      */     //   3433: invokevirtual 1039	org/bukkit/craftbukkit/libs/jline/UnixTerminal:enableInterruptCharacter	()V
/*      */     //   3436: aload 17
/*      */     //   3438: areturn
/*      */     //   3439: aload_0
/*      */     //   3440: iload 11
/*      */     //   3442: invokespecial 1242	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:transposeChars	(I)Z
/*      */     //   3445: istore 5
/*      */     //   3447: goto +742 -> 4189
/*      */     //   3450: aload_0
/*      */     //   3451: iconst_0
/*      */     //   3452: invokespecial 1244	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:insertComment	(Z)Ljava/lang/String;
/*      */     //   3455: astore 17
/*      */     //   3457: aload_0
/*      */     //   3458: getfield 246	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:terminal	Lorg/bukkit/craftbukkit/libs/jline/Terminal;
/*      */     //   3461: invokeinterface 337 1 0
/*      */     //   3466: ifne +7 -> 3473
/*      */     //   3469: aload_0
/*      */     //   3470: invokespecial 1034	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:afterReadLine	()V
/*      */     //   3473: aload_0
/*      */     //   3474: getfield 164	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:handleUserInterrupt	Z
/*      */     //   3477: ifeq +23 -> 3500
/*      */     //   3480: aload_0
/*      */     //   3481: getfield 246	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:terminal	Lorg/bukkit/craftbukkit/libs/jline/Terminal;
/*      */     //   3484: instanceof 1036
/*      */     //   3487: ifeq +13 -> 3500
/*      */     //   3490: aload_0
/*      */     //   3491: getfield 246	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:terminal	Lorg/bukkit/craftbukkit/libs/jline/Terminal;
/*      */     //   3494: checkcast 1036	org/bukkit/craftbukkit/libs/jline/UnixTerminal
/*      */     //   3497: invokevirtual 1039	org/bukkit/craftbukkit/libs/jline/UnixTerminal:enableInterruptCharacter	()V
/*      */     //   3500: aload 17
/*      */     //   3502: areturn
/*      */     //   3503: aload_0
/*      */     //   3504: ldc_w 1246
/*      */     //   3507: invokespecial 1248	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:insertClose	(Ljava/lang/String;)V
/*      */     //   3510: goto +679 -> 4189
/*      */     //   3513: aload_0
/*      */     //   3514: ldc_w 1250
/*      */     //   3517: invokespecial 1248	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:insertClose	(Ljava/lang/String;)V
/*      */     //   3520: goto +669 -> 4189
/*      */     //   3523: aload_0
/*      */     //   3524: ldc_w 1252
/*      */     //   3527: invokespecial 1248	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:insertClose	(Ljava/lang/String;)V
/*      */     //   3530: goto +659 -> 4189
/*      */     //   3533: aload_0
/*      */     //   3534: iconst_1
/*      */     //   3535: invokespecial 1244	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:insertComment	(Z)Ljava/lang/String;
/*      */     //   3538: astore 17
/*      */     //   3540: aload_0
/*      */     //   3541: getfield 246	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:terminal	Lorg/bukkit/craftbukkit/libs/jline/Terminal;
/*      */     //   3544: invokeinterface 337 1 0
/*      */     //   3549: ifne +7 -> 3556
/*      */     //   3552: aload_0
/*      */     //   3553: invokespecial 1034	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:afterReadLine	()V
/*      */     //   3556: aload_0
/*      */     //   3557: getfield 164	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:handleUserInterrupt	Z
/*      */     //   3560: ifeq +23 -> 3583
/*      */     //   3563: aload_0
/*      */     //   3564: getfield 246	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:terminal	Lorg/bukkit/craftbukkit/libs/jline/Terminal;
/*      */     //   3567: instanceof 1036
/*      */     //   3570: ifeq +13 -> 3583
/*      */     //   3573: aload_0
/*      */     //   3574: getfield 246	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:terminal	Lorg/bukkit/craftbukkit/libs/jline/Terminal;
/*      */     //   3577: checkcast 1036	org/bukkit/craftbukkit/libs/jline/UnixTerminal
/*      */     //   3580: invokevirtual 1039	org/bukkit/craftbukkit/libs/jline/UnixTerminal:enableInterruptCharacter	()V
/*      */     //   3583: aload 17
/*      */     //   3585: areturn
/*      */     //   3586: aload_0
/*      */     //   3587: invokespecial 901	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:viMatch	()Z
/*      */     //   3590: istore 5
/*      */     //   3592: goto +597 -> 4189
/*      */     //   3595: aload_0
/*      */     //   3596: aload 6
/*      */     //   3598: iconst_0
/*      */     //   3599: invokevirtual 510	java/lang/StringBuilder:charAt	(I)C
/*      */     //   3602: invokespecial 1254	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:viSearch	(C)I
/*      */     //   3605: istore 17
/*      */     //   3607: iload 17
/*      */     //   3609: iconst_m1
/*      */     //   3610: if_icmpeq +579 -> 4189
/*      */     //   3613: aload 7
/*      */     //   3615: iload 17
/*      */     //   3617: i2c
/*      */     //   3618: invokestatic 1104	java/lang/Character:valueOf	(C)Ljava/lang/Character;
/*      */     //   3621: invokevirtual 1107	java/util/Stack:push	(Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   3624: pop
/*      */     //   3625: goto +564 -> 4189
/*      */     //   3628: iload_3
/*      */     //   3629: bipush 10
/*      */     //   3631: imul
/*      */     //   3632: aload 6
/*      */     //   3634: iconst_0
/*      */     //   3635: invokevirtual 510	java/lang/StringBuilder:charAt	(I)C
/*      */     //   3638: iadd
/*      */     //   3639: bipush 48
/*      */     //   3641: isub
/*      */     //   3642: istore_3
/*      */     //   3643: iconst_1
/*      */     //   3644: istore 10
/*      */     //   3646: goto +543 -> 4189
/*      */     //   3649: iload_3
/*      */     //   3650: ifle +24 -> 3674
/*      */     //   3653: iload_3
/*      */     //   3654: bipush 10
/*      */     //   3656: imul
/*      */     //   3657: aload 6
/*      */     //   3659: iconst_0
/*      */     //   3660: invokevirtual 510	java/lang/StringBuilder:charAt	(I)C
/*      */     //   3663: iadd
/*      */     //   3664: bipush 48
/*      */     //   3666: isub
/*      */     //   3667: istore_3
/*      */     //   3668: iconst_1
/*      */     //   3669: istore 10
/*      */     //   3671: goto +518 -> 4189
/*      */     //   3674: aload_0
/*      */     //   3675: iconst_0
/*      */     //   3676: invokevirtual 544	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:setCursorPosition	(I)Z
/*      */     //   3679: istore 5
/*      */     //   3681: goto +508 -> 4189
/*      */     //   3684: aload_0
/*      */     //   3685: iconst_0
/*      */     //   3686: invokevirtual 544	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:setCursorPosition	(I)Z
/*      */     //   3689: ifeq +15 -> 3704
/*      */     //   3692: aload_0
/*      */     //   3693: iconst_1
/*      */     //   3694: invokespecial 1256	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:viNextWord	(I)Z
/*      */     //   3697: ifeq +7 -> 3704
/*      */     //   3700: iconst_1
/*      */     //   3701: goto +4 -> 3705
/*      */     //   3704: iconst_0
/*      */     //   3705: istore 5
/*      */     //   3707: goto +482 -> 4189
/*      */     //   3710: aload_0
/*      */     //   3711: iload 11
/*      */     //   3713: invokespecial 1258	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:viPreviousWord	(I)Z
/*      */     //   3716: istore 5
/*      */     //   3718: goto +471 -> 4189
/*      */     //   3721: aload_0
/*      */     //   3722: iload 11
/*      */     //   3724: invokespecial 1256	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:viNextWord	(I)Z
/*      */     //   3727: istore 5
/*      */     //   3729: goto +460 -> 4189
/*      */     //   3732: aload_0
/*      */     //   3733: iload 11
/*      */     //   3735: invokespecial 1260	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:viEndWord	(I)Z
/*      */     //   3738: istore 5
/*      */     //   3740: goto +449 -> 4189
/*      */     //   3743: aload_0
/*      */     //   3744: iconst_0
/*      */     //   3745: invokevirtual 544	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:setCursorPosition	(I)Z
/*      */     //   3748: istore 5
/*      */     //   3750: aload_0
/*      */     //   3751: getfield 279	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:consoleKeys	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleKeys;
/*      */     //   3754: ldc_w 864
/*      */     //   3757: invokevirtual 868	org/bukkit/craftbukkit/libs/jline/console/ConsoleKeys:setKeyMap	(Ljava/lang/String;)Z
/*      */     //   3760: pop
/*      */     //   3761: goto +428 -> 4189
/*      */     //   3764: aload_0
/*      */     //   3765: iload 11
/*      */     //   3767: invokespecial 1262	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:viRubout	(I)Z
/*      */     //   3770: istore 5
/*      */     //   3772: goto +417 -> 4189
/*      */     //   3775: aload_0
/*      */     //   3776: iload 11
/*      */     //   3778: invokespecial 1264	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:viDelete	(I)Z
/*      */     //   3781: istore 5
/*      */     //   3783: goto +406 -> 4189
/*      */     //   3786: aload_0
/*      */     //   3787: getfield 200	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:state	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   3790: getstatic 846	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State:VI_DELETE_TO	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   3793: if_acmpne +38 -> 3831
/*      */     //   3796: aload_0
/*      */     //   3797: iconst_0
/*      */     //   3798: invokevirtual 544	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:setCursorPosition	(I)Z
/*      */     //   3801: ifeq +14 -> 3815
/*      */     //   3804: aload_0
/*      */     //   3805: invokevirtual 518	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:killLine	()Z
/*      */     //   3808: ifeq +7 -> 3815
/*      */     //   3811: iconst_1
/*      */     //   3812: goto +4 -> 3816
/*      */     //   3815: iconst_0
/*      */     //   3816: istore 5
/*      */     //   3818: aload_0
/*      */     //   3819: getstatic 198	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State:NORMAL	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   3822: dup
/*      */     //   3823: astore 14
/*      */     //   3825: putfield 200	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:state	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   3828: goto +361 -> 4189
/*      */     //   3831: aload_0
/*      */     //   3832: getstatic 846	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State:VI_DELETE_TO	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   3835: putfield 200	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:state	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   3838: goto +351 -> 4189
/*      */     //   3841: aload_0
/*      */     //   3842: getfield 200	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:state	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   3845: getstatic 849	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State:VI_YANK_TO	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   3848: if_acmpne +30 -> 3878
/*      */     //   3851: aload_0
/*      */     //   3852: aload_0
/*      */     //   3853: getfield 152	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:buf	Lorg/bukkit/craftbukkit/libs/jline/console/CursorBuffer;
/*      */     //   3856: getfield 500	org/bukkit/craftbukkit/libs/jline/console/CursorBuffer:buffer	Ljava/lang/StringBuilder;
/*      */     //   3859: invokevirtual 445	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*      */     //   3862: putfield 182	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:yankBuffer	Ljava/lang/String;
/*      */     //   3865: aload_0
/*      */     //   3866: getstatic 198	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State:NORMAL	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   3869: dup
/*      */     //   3870: astore 14
/*      */     //   3872: putfield 200	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:state	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   3875: goto +314 -> 4189
/*      */     //   3878: aload_0
/*      */     //   3879: getstatic 849	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State:VI_YANK_TO	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   3882: putfield 200	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:state	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   3885: goto +304 -> 4189
/*      */     //   3888: aload_0
/*      */     //   3889: getfield 200	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:state	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   3892: getstatic 843	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State:VI_CHANGE_TO	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   3895: if_acmpne +49 -> 3944
/*      */     //   3898: aload_0
/*      */     //   3899: iconst_0
/*      */     //   3900: invokevirtual 544	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:setCursorPosition	(I)Z
/*      */     //   3903: ifeq +14 -> 3917
/*      */     //   3906: aload_0
/*      */     //   3907: invokevirtual 518	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:killLine	()Z
/*      */     //   3910: ifeq +7 -> 3917
/*      */     //   3913: iconst_1
/*      */     //   3914: goto +4 -> 3918
/*      */     //   3917: iconst_0
/*      */     //   3918: istore 5
/*      */     //   3920: aload_0
/*      */     //   3921: getstatic 198	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State:NORMAL	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   3924: dup
/*      */     //   3925: astore 14
/*      */     //   3927: putfield 200	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:state	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   3930: aload_0
/*      */     //   3931: getfield 279	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:consoleKeys	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleKeys;
/*      */     //   3934: ldc_w 864
/*      */     //   3937: invokevirtual 868	org/bukkit/craftbukkit/libs/jline/console/ConsoleKeys:setKeyMap	(Ljava/lang/String;)Z
/*      */     //   3940: pop
/*      */     //   3941: goto +248 -> 4189
/*      */     //   3944: aload_0
/*      */     //   3945: getstatic 843	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State:VI_CHANGE_TO	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   3948: putfield 200	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:state	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   3951: goto +238 -> 4189
/*      */     //   3954: aload_0
/*      */     //   3955: iconst_0
/*      */     //   3956: invokevirtual 544	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:setCursorPosition	(I)Z
/*      */     //   3959: ifeq +14 -> 3973
/*      */     //   3962: aload_0
/*      */     //   3963: invokevirtual 518	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:killLine	()Z
/*      */     //   3966: ifeq +7 -> 3973
/*      */     //   3969: iconst_1
/*      */     //   3970: goto +4 -> 3974
/*      */     //   3973: iconst_0
/*      */     //   3974: istore 5
/*      */     //   3976: aload_0
/*      */     //   3977: getfield 279	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:consoleKeys	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleKeys;
/*      */     //   3980: ldc_w 864
/*      */     //   3983: invokevirtual 868	org/bukkit/craftbukkit/libs/jline/console/ConsoleKeys:setKeyMap	(Ljava/lang/String;)Z
/*      */     //   3986: pop
/*      */     //   3987: goto +202 -> 4189
/*      */     //   3990: aload_0
/*      */     //   3991: iload 11
/*      */     //   3993: invokespecial 1266	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:viPut	(I)Z
/*      */     //   3996: istore 5
/*      */     //   3998: goto +191 -> 4189
/*      */     //   4001: iload 8
/*      */     //   4003: bipush 59
/*      */     //   4005: if_icmpeq +39 -> 4044
/*      */     //   4008: iload 8
/*      */     //   4010: bipush 44
/*      */     //   4012: if_icmpeq +32 -> 4044
/*      */     //   4015: aload 7
/*      */     //   4017: invokevirtual 1048	java/util/Stack:isEmpty	()Z
/*      */     //   4020: ifeq +10 -> 4030
/*      */     //   4023: aload_0
/*      */     //   4024: invokevirtual 885	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:readCharacter	()I
/*      */     //   4027: goto +18 -> 4045
/*      */     //   4030: aload 7
/*      */     //   4032: invokevirtual 1052	java/util/Stack:pop	()Ljava/lang/Object;
/*      */     //   4035: checkcast 684	java/lang/Character
/*      */     //   4038: invokevirtual 687	java/lang/Character:charValue	()C
/*      */     //   4041: goto +4 -> 4045
/*      */     //   4044: iconst_0
/*      */     //   4045: istore 18
/*      */     //   4047: aload_0
/*      */     //   4048: iload 11
/*      */     //   4050: iload 8
/*      */     //   4052: iload 18
/*      */     //   4054: invokespecial 1268	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:viCharSearch	(III)Z
/*      */     //   4057: istore 5
/*      */     //   4059: goto +130 -> 4189
/*      */     //   4062: aload_0
/*      */     //   4063: iload 11
/*      */     //   4065: invokespecial 1270	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:viChangeCase	(I)Z
/*      */     //   4068: istore 5
/*      */     //   4070: goto +119 -> 4189
/*      */     //   4073: aload_0
/*      */     //   4074: iload 11
/*      */     //   4076: aload 7
/*      */     //   4078: invokevirtual 1048	java/util/Stack:isEmpty	()Z
/*      */     //   4081: ifeq +10 -> 4091
/*      */     //   4084: aload_0
/*      */     //   4085: invokevirtual 885	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:readCharacter	()I
/*      */     //   4088: goto +14 -> 4102
/*      */     //   4091: aload 7
/*      */     //   4093: invokevirtual 1052	java/util/Stack:pop	()Ljava/lang/Object;
/*      */     //   4096: checkcast 684	java/lang/Character
/*      */     //   4099: invokevirtual 687	java/lang/Character:charValue	()C
/*      */     //   4102: invokespecial 1272	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:viChangeChar	(II)Z
/*      */     //   4105: istore 5
/*      */     //   4107: goto +82 -> 4189
/*      */     //   4110: aload_0
/*      */     //   4111: aload_0
/*      */     //   4112: getfield 152	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:buf	Lorg/bukkit/craftbukkit/libs/jline/console/CursorBuffer;
/*      */     //   4115: getfield 424	org/bukkit/craftbukkit/libs/jline/console/CursorBuffer:cursor	I
/*      */     //   4118: aload_0
/*      */     //   4119: getfield 152	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:buf	Lorg/bukkit/craftbukkit/libs/jline/console/CursorBuffer;
/*      */     //   4122: getfield 500	org/bukkit/craftbukkit/libs/jline/console/CursorBuffer:buffer	Ljava/lang/StringBuilder;
/*      */     //   4125: invokevirtual 505	java/lang/StringBuilder:length	()I
/*      */     //   4128: iconst_0
/*      */     //   4129: invokespecial 1274	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:viDeleteTo	(IIZ)Z
/*      */     //   4132: istore 5
/*      */     //   4134: goto +55 -> 4189
/*      */     //   4137: aload_0
/*      */     //   4138: aload_0
/*      */     //   4139: getfield 152	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:buf	Lorg/bukkit/craftbukkit/libs/jline/console/CursorBuffer;
/*      */     //   4142: getfield 424	org/bukkit/craftbukkit/libs/jline/console/CursorBuffer:cursor	I
/*      */     //   4145: aload_0
/*      */     //   4146: getfield 152	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:buf	Lorg/bukkit/craftbukkit/libs/jline/console/CursorBuffer;
/*      */     //   4149: getfield 500	org/bukkit/craftbukkit/libs/jline/console/CursorBuffer:buffer	Ljava/lang/StringBuilder;
/*      */     //   4152: invokevirtual 505	java/lang/StringBuilder:length	()I
/*      */     //   4155: iconst_1
/*      */     //   4156: invokespecial 1274	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:viDeleteTo	(IIZ)Z
/*      */     //   4159: istore 5
/*      */     //   4161: aload_0
/*      */     //   4162: getfield 279	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:consoleKeys	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleKeys;
/*      */     //   4165: ldc_w 864
/*      */     //   4168: invokevirtual 868	org/bukkit/craftbukkit/libs/jline/console/ConsoleKeys:setKeyMap	(Ljava/lang/String;)Z
/*      */     //   4171: pop
/*      */     //   4172: goto +17 -> 4189
/*      */     //   4175: aload_0
/*      */     //   4176: getfield 279	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:consoleKeys	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleKeys;
/*      */     //   4179: ldc_w 1276
/*      */     //   4182: invokevirtual 868	org/bukkit/craftbukkit/libs/jline/console/ConsoleKeys:setKeyMap	(Ljava/lang/String;)Z
/*      */     //   4185: pop
/*      */     //   4186: goto +3 -> 4189
/*      */     //   4189: aload 14
/*      */     //   4191: getstatic 198	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State:NORMAL	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   4194: if_acmpeq +98 -> 4292
/*      */     //   4197: aload 14
/*      */     //   4199: getstatic 846	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State:VI_DELETE_TO	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   4202: if_acmpne +22 -> 4224
/*      */     //   4205: aload_0
/*      */     //   4206: iload 13
/*      */     //   4208: aload_0
/*      */     //   4209: getfield 152	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:buf	Lorg/bukkit/craftbukkit/libs/jline/console/CursorBuffer;
/*      */     //   4212: getfield 424	org/bukkit/craftbukkit/libs/jline/console/CursorBuffer:cursor	I
/*      */     //   4215: iconst_0
/*      */     //   4216: invokespecial 1274	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:viDeleteTo	(IIZ)Z
/*      */     //   4219: istore 5
/*      */     //   4221: goto +64 -> 4285
/*      */     //   4224: aload 14
/*      */     //   4226: getstatic 843	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State:VI_CHANGE_TO	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   4229: if_acmpne +33 -> 4262
/*      */     //   4232: aload_0
/*      */     //   4233: iload 13
/*      */     //   4235: aload_0
/*      */     //   4236: getfield 152	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:buf	Lorg/bukkit/craftbukkit/libs/jline/console/CursorBuffer;
/*      */     //   4239: getfield 424	org/bukkit/craftbukkit/libs/jline/console/CursorBuffer:cursor	I
/*      */     //   4242: iconst_1
/*      */     //   4243: invokespecial 1274	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:viDeleteTo	(IIZ)Z
/*      */     //   4246: istore 5
/*      */     //   4248: aload_0
/*      */     //   4249: getfield 279	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:consoleKeys	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleKeys;
/*      */     //   4252: ldc_w 864
/*      */     //   4255: invokevirtual 868	org/bukkit/craftbukkit/libs/jline/console/ConsoleKeys:setKeyMap	(Ljava/lang/String;)Z
/*      */     //   4258: pop
/*      */     //   4259: goto +26 -> 4285
/*      */     //   4262: aload 14
/*      */     //   4264: getstatic 849	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State:VI_YANK_TO	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   4267: if_acmpne +18 -> 4285
/*      */     //   4270: aload_0
/*      */     //   4271: iload 13
/*      */     //   4273: aload_0
/*      */     //   4274: getfield 152	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:buf	Lorg/bukkit/craftbukkit/libs/jline/console/CursorBuffer;
/*      */     //   4277: getfield 424	org/bukkit/craftbukkit/libs/jline/console/CursorBuffer:cursor	I
/*      */     //   4280: invokespecial 1278	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:viYankTo	(II)Z
/*      */     //   4283: istore 5
/*      */     //   4285: aload_0
/*      */     //   4286: getstatic 198	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State:NORMAL	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   4289: putfield 200	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:state	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   4292: aload_0
/*      */     //   4293: getfield 200	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:state	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   4296: getstatic 198	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State:NORMAL	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   4299: if_acmpne +10 -> 4309
/*      */     //   4302: iload 10
/*      */     //   4304: ifne +5 -> 4309
/*      */     //   4307: iconst_0
/*      */     //   4308: istore_3
/*      */     //   4309: aload_0
/*      */     //   4310: getfield 200	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:state	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   4313: getstatic 1118	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State:SEARCH	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   4316: if_acmpeq +29 -> 4345
/*      */     //   4319: aload_0
/*      */     //   4320: getfield 200	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:state	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   4323: getstatic 1121	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State:FORWARD_SEARCH	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader$State;
/*      */     //   4326: if_acmpeq +19 -> 4345
/*      */     //   4329: aload_0
/*      */     //   4330: ldc -88
/*      */     //   4332: putfield 170	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:previousSearchTerm	Ljava/lang/String;
/*      */     //   4335: aload_0
/*      */     //   4336: aconst_null
/*      */     //   4337: putfield 166	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchTerm	Ljava/lang/StringBuffer;
/*      */     //   4340: aload_0
/*      */     //   4341: iconst_m1
/*      */     //   4342: putfield 172	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:searchIndex	I
/*      */     //   4345: iload 5
/*      */     //   4347: ifne +7 -> 4354
/*      */     //   4350: aload_0
/*      */     //   4351: invokevirtual 597	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:beep	()V
/*      */     //   4354: aload 6
/*      */     //   4356: iconst_0
/*      */     //   4357: invokevirtual 522	java/lang/StringBuilder:setLength	(I)V
/*      */     //   4360: aload_0
/*      */     //   4361: invokevirtual 664	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:flush	()V
/*      */     //   4364: goto -4171 -> 193
/*      */     //   4367: astore 19
/*      */     //   4369: aload_0
/*      */     //   4370: getfield 246	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:terminal	Lorg/bukkit/craftbukkit/libs/jline/Terminal;
/*      */     //   4373: invokeinterface 337 1 0
/*      */     //   4378: ifne +7 -> 4385
/*      */     //   4381: aload_0
/*      */     //   4382: invokespecial 1034	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:afterReadLine	()V
/*      */     //   4385: aload_0
/*      */     //   4386: getfield 164	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:handleUserInterrupt	Z
/*      */     //   4389: ifeq +23 -> 4412
/*      */     //   4392: aload_0
/*      */     //   4393: getfield 246	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:terminal	Lorg/bukkit/craftbukkit/libs/jline/Terminal;
/*      */     //   4396: instanceof 1036
/*      */     //   4399: ifeq +13 -> 4412
/*      */     //   4402: aload_0
/*      */     //   4403: getfield 246	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:terminal	Lorg/bukkit/craftbukkit/libs/jline/Terminal;
/*      */     //   4406: checkcast 1036	org/bukkit/craftbukkit/libs/jline/UnixTerminal
/*      */     //   4409: invokevirtual 1039	org/bukkit/craftbukkit/libs/jline/UnixTerminal:enableInterruptCharacter	()V
/*      */     //   4412: aload 19
/*      */     //   4414: athrow
/*      */     // Line number table:
/*      */     //   Java source line #2310	-> byte code offset #0
/*      */     //   Java source line #2313	-> byte code offset #2
/*      */     //   Java source line #2314	-> byte code offset #7
/*      */     //   Java source line #2315	-> byte code offset #11
/*      */     //   Java source line #2318	-> byte code offset #19
/*      */     //   Java source line #2322	-> byte code offset #24
/*      */     //   Java source line #2323	-> byte code offset #36
/*      */     //   Java source line #2326	-> byte code offset #42
/*      */     //   Java source line #2327	-> byte code offset #53
/*      */     //   Java source line #2328	-> byte code offset #61
/*      */     //   Java source line #2332	-> byte code offset #68
/*      */     //   Java source line #2333	-> byte code offset #80
/*      */     //   Java source line #3153	-> byte code offset #86
/*      */     //   Java source line #3154	-> byte code offset #98
/*      */     //   Java source line #3156	-> byte code offset #102
/*      */     //   Java source line #3157	-> byte code offset #119
/*      */     //   Java source line #2336	-> byte code offset #132
/*      */     //   Java source line #2337	-> byte code offset #149
/*      */     //   Java source line #2340	-> byte code offset #159
/*      */     //   Java source line #2342	-> byte code offset #165
/*      */     //   Java source line #2344	-> byte code offset #172
/*      */     //   Java source line #2346	-> byte code offset #175
/*      */     //   Java source line #2347	-> byte code offset #184
/*      */     //   Java source line #2349	-> byte code offset #193
/*      */     //   Java source line #2350	-> byte code offset #221
/*      */     //   Java source line #2351	-> byte code offset #227
/*      */     //   Java source line #3153	-> byte code offset #230
/*      */     //   Java source line #3154	-> byte code offset #242
/*      */     //   Java source line #3156	-> byte code offset #246
/*      */     //   Java source line #3157	-> byte code offset #263
/*      */     //   Java source line #2353	-> byte code offset #276
/*      */     //   Java source line #2355	-> byte code offset #284
/*      */     //   Java source line #2356	-> byte code offset #291
/*      */     //   Java source line #2359	-> byte code offset #332
/*      */     //   Java source line #2365	-> byte code offset #343
/*      */     //   Java source line #2366	-> byte code offset #358
/*      */     //   Java source line #2367	-> byte code offset #374
/*      */     //   Java source line #2369	-> byte code offset #381
/*      */     //   Java source line #2372	-> byte code offset #429
/*      */     //   Java source line #2376	-> byte code offset #436
/*      */     //   Java source line #2377	-> byte code offset #444
/*      */     //   Java source line #2378	-> byte code offset #456
/*      */     //   Java source line #2379	-> byte code offset #468
/*      */     //   Java source line #2389	-> byte code offset #479
/*      */     //   Java source line #2404	-> byte code offset #487
/*      */     //   Java source line #2408	-> byte code offset #528
/*      */     //   Java source line #2409	-> byte code offset #538
/*      */     //   Java source line #2410	-> byte code offset #551
/*      */     //   Java source line #2412	-> byte code offset #554
/*      */     //   Java source line #2435	-> byte code offset #560
/*      */     //   Java source line #2436	-> byte code offset #573
/*      */     //   Java source line #2437	-> byte code offset #587
/*      */     //   Java source line #2438	-> byte code offset #599
/*      */     //   Java source line #2439	-> byte code offset #610
/*      */     //   Java source line #2440	-> byte code offset #618
/*      */     //   Java source line #2441	-> byte code offset #628
/*      */     //   Java source line #2442	-> byte code offset #633
/*      */     //   Java source line #2444	-> byte code offset #636
/*      */     //   Java source line #2447	-> byte code offset #648
/*      */     //   Java source line #2449	-> byte code offset #651
/*      */     //   Java source line #2450	-> byte code offset #656
/*      */     //   Java source line #2452	-> byte code offset #659
/*      */     //   Java source line #2456	-> byte code offset #677
/*      */     //   Java source line #2457	-> byte code offset #685
/*      */     //   Java source line #2458	-> byte code offset #692
/*      */     //   Java source line #2459	-> byte code offset #705
/*      */     //   Java source line #2458	-> byte code offset #729
/*      */     //   Java source line #2461	-> byte code offset #735
/*      */     //   Java source line #2462	-> byte code offset #741
/*      */     //   Java source line #2466	-> byte code offset #744
/*      */     //   Java source line #2467	-> byte code offset #752
/*      */     //   Java source line #2468	-> byte code offset #763
/*      */     //   Java source line #2469	-> byte code offset #769
/*      */     //   Java source line #2477	-> byte code offset #772
/*      */     //   Java source line #2478	-> byte code offset #792
/*      */     //   Java source line #2479	-> byte code offset #795
/*      */     //   Java source line #2481	-> byte code offset #856
/*      */     //   Java source line #2482	-> byte code offset #863
/*      */     //   Java source line #2483	-> byte code offset #871
/*      */     //   Java source line #2484	-> byte code offset #886
/*      */     //   Java source line #2487	-> byte code offset #889
/*      */     //   Java source line #2488	-> byte code offset #896
/*      */     //   Java source line #2489	-> byte code offset #906
/*      */     //   Java source line #2492	-> byte code offset #918
/*      */     //   Java source line #2493	-> byte code offset #925
/*      */     //   Java source line #2498	-> byte code offset #947
/*      */     //   Java source line #2499	-> byte code offset #954
/*      */     //   Java source line #2500	-> byte code offset #964
/*      */     //   Java source line #2503	-> byte code offset #976
/*      */     //   Java source line #2504	-> byte code offset #1002
/*      */     //   Java source line #2509	-> byte code offset #1024
/*      */     //   Java source line #2510	-> byte code offset #1034
/*      */     //   Java source line #2511	-> byte code offset #1051
/*      */     //   Java source line #2512	-> byte code offset #1061
/*      */     //   Java source line #2514	-> byte code offset #1079
/*      */     //   Java source line #2520	-> byte code offset #1097
/*      */     //   Java source line #2521	-> byte code offset #1107
/*      */     //   Java source line #2522	-> byte code offset #1117
/*      */     //   Java source line #2524	-> byte code offset #1135
/*      */     //   Java source line #2526	-> byte code offset #1150
/*      */     //   Java source line #2530	-> byte code offset #1153
/*      */     //   Java source line #2531	-> byte code offset #1161
/*      */     //   Java source line #2533	-> byte code offset #1175
/*      */     //   Java source line #2535	-> byte code offset #1201
/*      */     //   Java source line #2540	-> byte code offset #1208
/*      */     //   Java source line #2541	-> byte code offset #1228
/*      */     //   Java source line #2542	-> byte code offset #1238
/*      */     //   Java source line #2543	-> byte code offset #1248
/*      */     //   Java source line #2545	-> byte code offset #1259
/*      */     //   Java source line #2547	-> byte code offset #1267
/*      */     //   Java source line #2549	-> byte code offset #1275
/*      */     //   Java source line #2550	-> byte code offset #1283
/*      */     //   Java source line #2551	-> byte code offset #1287
/*      */     //   Java source line #2552	-> byte code offset #1303
/*      */     //   Java source line #2553	-> byte code offset #1313
/*      */     //   Java source line #2555	-> byte code offset #1345
/*      */     //   Java source line #2561	-> byte code offset #1377
/*      */     //   Java source line #2564	-> byte code offset #1385
/*      */     //   Java source line #2569	-> byte code offset #1405
/*      */     //   Java source line #2576	-> byte code offset #1408
/*      */     //   Java source line #2582	-> byte code offset #1419
/*      */     //   Java source line #2584	-> byte code offset #1422
/*      */     //   Java source line #2585	-> byte code offset #1430
/*      */     //   Java source line #2591	-> byte code offset #1437
/*      */     //   Java source line #2592	-> byte code offset #1446
/*      */     //   Java source line #2598	-> byte code offset #1452
/*      */     //   Java source line #2602	-> byte code offset #1482
/*      */     //   Java source line #2605	-> byte code offset #1490
/*      */     //   Java source line #2612	-> byte code offset #1832
/*      */     //   Java source line #2613	-> byte code offset #1835
/*      */     //   Java source line #2617	-> byte code offset #1883
/*      */     //   Java source line #2620	-> byte code offset #1886
/*      */     //   Java source line #2621	-> byte code offset #1891
/*      */     //   Java source line #2624	-> byte code offset #1900
/*      */     //   Java source line #2626	-> byte code offset #1906
/*      */     //   Java source line #2629	-> byte code offset #1909
/*      */     //   Java source line #2630	-> byte code offset #1913
/*      */     //   Java source line #2633	-> byte code offset #1916
/*      */     //   Java source line #2634	-> byte code offset #1923
/*      */     //   Java source line #2637	-> byte code offset #1926
/*      */     //   Java source line #2638	-> byte code offset #1932
/*      */     //   Java source line #2641	-> byte code offset #1935
/*      */     //   Java source line #2642	-> byte code offset #1941
/*      */     //   Java source line #2645	-> byte code offset #1944
/*      */     //   Java source line #2646	-> byte code offset #1950
/*      */     //   Java source line #2649	-> byte code offset #1953
/*      */     //   Java source line #2650	-> byte code offset #1975
/*      */     //   Java source line #2653	-> byte code offset #1978
/*      */     //   Java source line #2654	-> byte code offset #1984
/*      */     //   Java source line #2655	-> byte code offset #1988
/*      */     //   Java source line #2658	-> byte code offset #1991
/*      */     //   Java source line #2659	-> byte code offset #2013
/*      */     //   Java source line #2662	-> byte code offset #2016
/*      */     //   Java source line #2663	-> byte code offset #2022
/*      */     //   Java source line #2666	-> byte code offset #2025
/*      */     //   Java source line #3153	-> byte code offset #2031
/*      */     //   Java source line #3154	-> byte code offset #2043
/*      */     //   Java source line #3156	-> byte code offset #2047
/*      */     //   Java source line #3157	-> byte code offset #2064
/*      */     //   Java source line #2669	-> byte code offset #2077
/*      */     //   Java source line #2670	-> byte code offset #2084
/*      */     //   Java source line #2675	-> byte code offset #2091
/*      */     //   Java source line #2676	-> byte code offset #2098
/*      */     //   Java source line #2677	-> byte code offset #2102
/*      */     //   Java source line #2678	-> byte code offset #2106
/*      */     //   Java source line #2679	-> byte code offset #2118
/*      */     //   Java source line #2680	-> byte code offset #2126
/*      */     //   Java source line #2681	-> byte code offset #2135
/*      */     //   Java source line #2692	-> byte code offset #2145
/*      */     //   Java source line #2693	-> byte code offset #2156
/*      */     //   Java source line #3153	-> byte code offset #2162
/*      */     //   Java source line #3154	-> byte code offset #2174
/*      */     //   Java source line #3156	-> byte code offset #2178
/*      */     //   Java source line #3157	-> byte code offset #2195
/*      */     //   Java source line #2696	-> byte code offset #2208
/*      */     //   Java source line #2697	-> byte code offset #2214
/*      */     //   Java source line #2700	-> byte code offset #2217
/*      */     //   Java source line #2701	-> byte code offset #2223
/*      */     //   Java source line #2704	-> byte code offset #2226
/*      */     //   Java source line #2705	-> byte code offset #2233
/*      */     //   Java source line #2714	-> byte code offset #2236
/*      */     //   Java source line #2716	-> byte code offset #2261
/*      */     //   Java source line #2719	-> byte code offset #2264
/*      */     //   Java source line #2720	-> byte code offset #2271
/*      */     //   Java source line #2729	-> byte code offset #2274
/*      */     //   Java source line #2731	-> byte code offset #2299
/*      */     //   Java source line #2734	-> byte code offset #2302
/*      */     //   Java source line #2735	-> byte code offset #2308
/*      */     //   Java source line #2738	-> byte code offset #2311
/*      */     //   Java source line #2739	-> byte code offset #2324
/*      */     //   Java source line #3153	-> byte code offset #2327
/*      */     //   Java source line #3154	-> byte code offset #2339
/*      */     //   Java source line #3156	-> byte code offset #2343
/*      */     //   Java source line #3157	-> byte code offset #2360
/*      */     //   Java source line #2741	-> byte code offset #2373
/*      */     //   Java source line #2742	-> byte code offset #2379
/*      */     //   Java source line #2745	-> byte code offset #2382
/*      */     //   Java source line #2746	-> byte code offset #2388
/*      */     //   Java source line #2749	-> byte code offset #2391
/*      */     //   Java source line #2750	-> byte code offset #2408
/*      */     //   Java source line #2753	-> byte code offset #2411
/*      */     //   Java source line #2754	-> byte code offset #2427
/*      */     //   Java source line #2757	-> byte code offset #2430
/*      */     //   Java source line #2758	-> byte code offset #2436
/*      */     //   Java source line #2761	-> byte code offset #2439
/*      */     //   Java source line #2762	-> byte code offset #2447
/*      */     //   Java source line #2765	-> byte code offset #2450
/*      */     //   Java source line #2766	-> byte code offset #2456
/*      */     //   Java source line #2769	-> byte code offset #2459
/*      */     //   Java source line #2770	-> byte code offset #2465
/*      */     //   Java source line #2773	-> byte code offset #2468
/*      */     //   Java source line #2774	-> byte code offset #2479
/*      */     //   Java source line #2775	-> byte code offset #2484
/*      */     //   Java source line #2780	-> byte code offset #2500
/*      */     //   Java source line #2781	-> byte code offset #2511
/*      */     //   Java source line #2782	-> byte code offset #2516
/*      */     //   Java source line #2787	-> byte code offset #2532
/*      */     //   Java source line #2788	-> byte code offset #2550
/*      */     //   Java source line #2790	-> byte code offset #2575
/*      */     //   Java source line #2791	-> byte code offset #2583
/*      */     //   Java source line #2794	-> byte code offset #2590
/*      */     //   Java source line #2795	-> byte code offset #2605
/*      */     //   Java source line #2796	-> byte code offset #2610
/*      */     //   Java source line #2802	-> byte code offset #2626
/*      */     //   Java source line #2803	-> byte code offset #2644
/*      */     //   Java source line #2805	-> byte code offset #2657
/*      */     //   Java source line #2806	-> byte code offset #2671
/*      */     //   Java source line #2807	-> byte code offset #2680
/*      */     //   Java source line #2808	-> byte code offset #2694
/*      */     //   Java source line #2809	-> byte code offset #2708
/*      */     //   Java source line #2810	-> byte code offset #2726
/*      */     //   Java source line #2811	-> byte code offset #2734
/*      */     //   Java source line #2814	-> byte code offset #2741
/*      */     //   Java source line #2815	-> byte code offset #2756
/*      */     //   Java source line #2816	-> byte code offset #2761
/*      */     //   Java source line #2823	-> byte code offset #2777
/*      */     //   Java source line #2824	-> byte code offset #2784
/*      */     //   Java source line #2826	-> byte code offset #2795
/*      */     //   Java source line #2827	-> byte code offset #2813
/*      */     //   Java source line #2828	-> byte code offset #2820
/*      */     //   Java source line #2829	-> byte code offset #2830
/*      */     //   Java source line #2830	-> byte code offset #2845
/*      */     //   Java source line #2831	-> byte code offset #2853
/*      */     //   Java source line #2833	-> byte code offset #2857
/*      */     //   Java source line #2836	-> byte code offset #2902
/*      */     //   Java source line #2837	-> byte code offset #2907
/*      */     //   Java source line #2839	-> byte code offset #2915
/*      */     //   Java source line #2842	-> byte code offset #2918
/*      */     //   Java source line #2843	-> byte code offset #2925
/*      */     //   Java source line #2845	-> byte code offset #2936
/*      */     //   Java source line #2846	-> byte code offset #2954
/*      */     //   Java source line #2847	-> byte code offset #2961
/*      */     //   Java source line #2848	-> byte code offset #2971
/*      */     //   Java source line #2849	-> byte code offset #2986
/*      */     //   Java source line #2850	-> byte code offset #2994
/*      */     //   Java source line #2852	-> byte code offset #2998
/*      */     //   Java source line #2855	-> byte code offset #3043
/*      */     //   Java source line #2856	-> byte code offset #3048
/*      */     //   Java source line #2858	-> byte code offset #3056
/*      */     //   Java source line #2861	-> byte code offset #3059
/*      */     //   Java source line #2862	-> byte code offset #3065
/*      */     //   Java source line #2865	-> byte code offset #3068
/*      */     //   Java source line #2866	-> byte code offset #3074
/*      */     //   Java source line #2869	-> byte code offset #3077
/*      */     //   Java source line #2870	-> byte code offset #3083
/*      */     //   Java source line #2873	-> byte code offset #3086
/*      */     //   Java source line #2874	-> byte code offset #3092
/*      */     //   Java source line #2877	-> byte code offset #3095
/*      */     //   Java source line #2878	-> byte code offset #3102
/*      */     //   Java source line #2881	-> byte code offset #3105
/*      */     //   Java source line #2882	-> byte code offset #3120
/*      */     //   Java source line #2885	-> byte code offset #3123
/*      */     //   Java source line #2886	-> byte code offset #3128
/*      */     //   Java source line #2889	-> byte code offset #3131
/*      */     //   Java source line #2890	-> byte code offset #3136
/*      */     //   Java source line #2891	-> byte code offset #3161
/*      */     //   Java source line #2894	-> byte code offset #3164
/*      */     //   Java source line #2895	-> byte code offset #3179
/*      */     //   Java source line #2894	-> byte code offset #3207
/*      */     //   Java source line #2897	-> byte code offset #3213
/*      */     //   Java source line #2898	-> byte code offset #3219
/*      */     //   Java source line #2901	-> byte code offset #3222
/*      */     //   Java source line #2902	-> byte code offset #3233
/*      */     //   Java source line #2912	-> byte code offset #3236
/*      */     //   Java source line #2913	-> byte code offset #3251
/*      */     //   Java source line #2915	-> byte code offset #3257
/*      */     //   Java source line #2916	-> byte code offset #3268
/*      */     //   Java source line #2919	-> byte code offset #3271
/*      */     //   Java source line #2920	-> byte code offset #3282
/*      */     //   Java source line #2923	-> byte code offset #3285
/*      */     //   Java source line #2924	-> byte code offset #3291
/*      */     //   Java source line #2925	-> byte code offset #3302
/*      */     //   Java source line #2928	-> byte code offset #3305
/*      */     //   Java source line #2929	-> byte code offset #3311
/*      */     //   Java source line #2930	-> byte code offset #3322
/*      */     //   Java source line #2938	-> byte code offset #3325
/*      */     //   Java source line #2939	-> byte code offset #3338
/*      */     //   Java source line #3153	-> byte code offset #3341
/*      */     //   Java source line #3154	-> byte code offset #3353
/*      */     //   Java source line #3156	-> byte code offset #3357
/*      */     //   Java source line #3157	-> byte code offset #3374
/*      */     //   Java source line #2941	-> byte code offset #3387
/*      */     //   Java source line #3153	-> byte code offset #3393
/*      */     //   Java source line #3154	-> byte code offset #3405
/*      */     //   Java source line #3156	-> byte code offset #3409
/*      */     //   Java source line #3157	-> byte code offset #3426
/*      */     //   Java source line #2944	-> byte code offset #3439
/*      */     //   Java source line #2945	-> byte code offset #3447
/*      */     //   Java source line #2948	-> byte code offset #3450
/*      */     //   Java source line #3153	-> byte code offset #3457
/*      */     //   Java source line #3154	-> byte code offset #3469
/*      */     //   Java source line #3156	-> byte code offset #3473
/*      */     //   Java source line #3157	-> byte code offset #3490
/*      */     //   Java source line #2951	-> byte code offset #3503
/*      */     //   Java source line #2952	-> byte code offset #3510
/*      */     //   Java source line #2955	-> byte code offset #3513
/*      */     //   Java source line #2956	-> byte code offset #3520
/*      */     //   Java source line #2959	-> byte code offset #3523
/*      */     //   Java source line #2960	-> byte code offset #3530
/*      */     //   Java source line #2963	-> byte code offset #3533
/*      */     //   Java source line #3153	-> byte code offset #3540
/*      */     //   Java source line #3154	-> byte code offset #3552
/*      */     //   Java source line #3156	-> byte code offset #3556
/*      */     //   Java source line #3157	-> byte code offset #3573
/*      */     //   Java source line #2966	-> byte code offset #3586
/*      */     //   Java source line #2967	-> byte code offset #3592
/*      */     //   Java source line #2970	-> byte code offset #3595
/*      */     //   Java source line #2971	-> byte code offset #3607
/*      */     //   Java source line #2972	-> byte code offset #3613
/*      */     //   Java source line #2977	-> byte code offset #3628
/*      */     //   Java source line #2978	-> byte code offset #3643
/*      */     //   Java source line #2979	-> byte code offset #3646
/*      */     //   Java source line #2982	-> byte code offset #3649
/*      */     //   Java source line #2983	-> byte code offset #3653
/*      */     //   Java source line #2984	-> byte code offset #3668
/*      */     //   Java source line #2987	-> byte code offset #3674
/*      */     //   Java source line #2989	-> byte code offset #3681
/*      */     //   Java source line #2992	-> byte code offset #3684
/*      */     //   Java source line #2993	-> byte code offset #3707
/*      */     //   Java source line #2996	-> byte code offset #3710
/*      */     //   Java source line #2997	-> byte code offset #3718
/*      */     //   Java source line #3000	-> byte code offset #3721
/*      */     //   Java source line #3001	-> byte code offset #3729
/*      */     //   Java source line #3004	-> byte code offset #3732
/*      */     //   Java source line #3005	-> byte code offset #3740
/*      */     //   Java source line #3008	-> byte code offset #3743
/*      */     //   Java source line #3009	-> byte code offset #3750
/*      */     //   Java source line #3010	-> byte code offset #3761
/*      */     //   Java source line #3013	-> byte code offset #3764
/*      */     //   Java source line #3014	-> byte code offset #3772
/*      */     //   Java source line #3017	-> byte code offset #3775
/*      */     //   Java source line #3018	-> byte code offset #3783
/*      */     //   Java source line #3027	-> byte code offset #3786
/*      */     //   Java source line #3028	-> byte code offset #3796
/*      */     //   Java source line #3029	-> byte code offset #3818
/*      */     //   Java source line #3032	-> byte code offset #3831
/*      */     //   Java source line #3034	-> byte code offset #3838
/*      */     //   Java source line #3038	-> byte code offset #3841
/*      */     //   Java source line #3039	-> byte code offset #3851
/*      */     //   Java source line #3040	-> byte code offset #3865
/*      */     //   Java source line #3043	-> byte code offset #3878
/*      */     //   Java source line #3045	-> byte code offset #3885
/*      */     //   Java source line #3048	-> byte code offset #3888
/*      */     //   Java source line #3049	-> byte code offset #3898
/*      */     //   Java source line #3050	-> byte code offset #3920
/*      */     //   Java source line #3051	-> byte code offset #3930
/*      */     //   Java source line #3054	-> byte code offset #3944
/*      */     //   Java source line #3056	-> byte code offset #3951
/*      */     //   Java source line #3059	-> byte code offset #3954
/*      */     //   Java source line #3060	-> byte code offset #3976
/*      */     //   Java source line #3061	-> byte code offset #3987
/*      */     //   Java source line #3064	-> byte code offset #3990
/*      */     //   Java source line #3065	-> byte code offset #3998
/*      */     //   Java source line #3069	-> byte code offset #4001
/*      */     //   Java source line #3075	-> byte code offset #4047
/*      */     //   Java source line #3077	-> byte code offset #4059
/*      */     //   Java source line #3080	-> byte code offset #4062
/*      */     //   Java source line #3081	-> byte code offset #4070
/*      */     //   Java source line #3084	-> byte code offset #4073
/*      */     //   Java source line #3088	-> byte code offset #4107
/*      */     //   Java source line #3091	-> byte code offset #4110
/*      */     //   Java source line #3092	-> byte code offset #4134
/*      */     //   Java source line #3095	-> byte code offset #4137
/*      */     //   Java source line #3096	-> byte code offset #4161
/*      */     //   Java source line #3097	-> byte code offset #4172
/*      */     //   Java source line #3100	-> byte code offset #4175
/*      */     //   Java source line #3101	-> byte code offset #4186
/*      */     //   Java source line #3111	-> byte code offset #4189
/*      */     //   Java source line #3112	-> byte code offset #4197
/*      */     //   Java source line #3113	-> byte code offset #4205
/*      */     //   Java source line #3115	-> byte code offset #4224
/*      */     //   Java source line #3116	-> byte code offset #4232
/*      */     //   Java source line #3117	-> byte code offset #4248
/*      */     //   Java source line #3119	-> byte code offset #4262
/*      */     //   Java source line #3120	-> byte code offset #4270
/*      */     //   Java source line #3122	-> byte code offset #4285
/*      */     //   Java source line #3130	-> byte code offset #4292
/*      */     //   Java source line #3135	-> byte code offset #4307
/*      */     //   Java source line #3138	-> byte code offset #4309
/*      */     //   Java source line #3139	-> byte code offset #4329
/*      */     //   Java source line #3140	-> byte code offset #4335
/*      */     //   Java source line #3141	-> byte code offset #4340
/*      */     //   Java source line #3145	-> byte code offset #4345
/*      */     //   Java source line #3146	-> byte code offset #4350
/*      */     //   Java source line #3148	-> byte code offset #4354
/*      */     //   Java source line #3149	-> byte code offset #4360
/*      */     //   Java source line #3150	-> byte code offset #4364
/*      */     //   Java source line #3153	-> byte code offset #4367
/*      */     //   Java source line #3154	-> byte code offset #4381
/*      */     //   Java source line #3156	-> byte code offset #4385
/*      */     //   Java source line #3157	-> byte code offset #4402
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	4415	0	this	ConsoleReader
/*      */     //   0	4415	1	prompt	String
/*      */     //   0	4415	2	mask	Character
/*      */     //   1	4308	3	repeatCount	int
/*      */     //   84	46	4	str1	String
/*      */     //   163	1216	4	originalPrompt	String
/*      */     //   173	4173	5	success	boolean
/*      */     //   182	4173	6	sb	StringBuilder
/*      */     //   191	3901	7	pushBackChar	java.util.Stack<Character>
/*      */     //   219	3832	8	c	int
/*      */     //   228	46	9	str2	String
/*      */     //   341	1090	9	o	Object
/*      */     //   608	11	10	o2	Object
/*      */     //   690	20	10	macro	String
/*      */     //   793	588	10	cursorDest	int
/*      */     //   1406	2897	10	isArgDigit	boolean
/*      */     //   693	37	11	i	int
/*      */     //   1417	2658	11	count	int
/*      */     //   1435	59	12	op	Operation
/*      */     //   1444	2828	13	cursorStart	int
/*      */     //   1450	2813	14	origState	State
/*      */     //   1833	54	15	isTabLiteral	boolean
/*      */     //   2029	46	16	str3	String
/*      */     //   2116	255	16	partialLine	String
/*      */     //   2655	63	16	index	int
/*      */     //   3165	419	17	i	int
/*      */     //   3605	11	17	lastChar	int
/*      */     //   4045	8	18	searchChar	int
/*      */     //   4367	46	19	localObject1	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   24	86	4367	finally
/*      */     //   132	230	4367	finally
/*      */     //   276	2031	4367	finally
/*      */     //   2077	2162	4367	finally
/*      */     //   2208	2327	4367	finally
/*      */     //   2373	3341	4367	finally
/*      */     //   3387	3393	4367	finally
/*      */     //   3439	3457	4367	finally
/*      */     //   3503	3540	4367	finally
/*      */     //   3586	4369	4367	finally
/*      */   }
/*      */   
/*      */   private String readLineSimple()
/*      */     throws IOException
/*      */   {
/* 3166 */     StringBuilder buff = new StringBuilder();
/*      */     
/* 3168 */     if (this.skipLF) {
/* 3169 */       this.skipLF = false;
/*      */       
/* 3171 */       int i = readCharacter();
/*      */       
/* 3173 */       if ((i == -1) || (i == 13))
/* 3174 */         return buff.toString();
/* 3175 */       if (i != 10)
/*      */       {
/*      */ 
/* 3178 */         buff.append((char)i);
/*      */       }
/*      */     }
/*      */     for (;;)
/*      */     {
/* 3183 */       int i = readCharacter();
/*      */       
/* 3185 */       if ((i == -1) && (buff.length() == 0)) {
/* 3186 */         return null;
/*      */       }
/*      */       
/* 3189 */       if ((i == -1) || (i == 10))
/* 3190 */         return buff.toString();
/* 3191 */       if (i == 13) {
/* 3192 */         this.skipLF = true;
/* 3193 */         return buff.toString();
/*      */       }
/* 3195 */       buff.append((char)i);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3204 */   private final List<Completer> completers = new LinkedList();
/*      */   
/* 3206 */   private CompletionHandler completionHandler = new CandidateListCompletionHandler();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean addCompleter(Completer completer)
/*      */   {
/* 3215 */     return this.completers.add(completer);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean removeCompleter(Completer completer)
/*      */   {
/* 3225 */     return this.completers.remove(completer);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public Collection<Completer> getCompleters()
/*      */   {
/* 3232 */     return Collections.unmodifiableList(this.completers);
/*      */   }
/*      */   
/*      */   public void setCompletionHandler(CompletionHandler handler) {
/* 3236 */     this.completionHandler = ((CompletionHandler)Preconditions.checkNotNull(handler));
/*      */   }
/*      */   
/*      */   public CompletionHandler getCompletionHandler() {
/* 3240 */     return this.completionHandler;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected boolean complete()
/*      */     throws IOException
/*      */   {
/* 3250 */     if (this.completers.size() == 0) {
/* 3251 */       return false;
/*      */     }
/*      */     
/* 3254 */     List<CharSequence> candidates = new LinkedList();
/* 3255 */     String bufstr = this.buf.buffer.toString();
/* 3256 */     int cursor = this.buf.cursor;
/*      */     
/* 3258 */     int position = -1;
/*      */     
/* 3260 */     for (Completer comp : this.completers) {
/* 3261 */       if ((position = comp.complete(bufstr, cursor, candidates)) != -1) {
/*      */         break;
/*      */       }
/*      */     }
/*      */     
/* 3266 */     return (candidates.size() != 0) && (getCompletionHandler().complete(this, candidates, position));
/*      */   }
/*      */   
/*      */   protected void printCompletionCandidates() throws IOException
/*      */   {
/* 3271 */     if (this.completers.size() == 0) {
/* 3272 */       return;
/*      */     }
/*      */     
/* 3275 */     List<CharSequence> candidates = new LinkedList();
/* 3276 */     String bufstr = this.buf.buffer.toString();
/* 3277 */     int cursor = this.buf.cursor;
/*      */     
/* 3279 */     for (Completer comp : this.completers) {
/* 3280 */       if (comp.complete(bufstr, cursor, candidates) != -1) {
/*      */         break;
/*      */       }
/*      */     }
/* 3284 */     CandidateListCompletionHandler.printCandidates(this, candidates);
/* 3285 */     drawLine();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3292 */   private int autoprintThreshold = Configuration.getInteger("org.bukkit.craftbukkit.libs.jline.completion.threshold", 100);
/*      */   
/*      */   private boolean paginationEnabled;
/*      */   
/*      */   public void setAutoprintThreshold(int threshold)
/*      */   {
/* 3298 */     this.autoprintThreshold = threshold;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public int getAutoprintThreshold()
/*      */   {
/* 3305 */     return this.autoprintThreshold;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setPaginationEnabled(boolean enabled)
/*      */   {
/* 3314 */     this.paginationEnabled = enabled;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public boolean isPaginationEnabled()
/*      */   {
/* 3321 */     return this.paginationEnabled;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3328 */   private History history = new MemoryHistory();
/*      */   
/*      */   public void setHistory(History history) {
/* 3331 */     this.history = history;
/*      */   }
/*      */   
/*      */   public History getHistory() {
/* 3335 */     return this.history;
/*      */   }
/*      */   
/* 3338 */   private boolean historyEnabled = true;
/*      */   
/*      */ 
/*      */ 
/*      */   public void setHistoryEnabled(boolean enabled)
/*      */   {
/* 3344 */     this.historyEnabled = enabled;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public boolean isHistoryEnabled()
/*      */   {
/* 3351 */     return this.historyEnabled;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean moveHistory(boolean next, int count)
/*      */     throws IOException
/*      */   {
/* 3364 */     boolean ok = true;
/* 3365 */     for (int i = 0; (i < count) && ((ok = moveHistory(next))); i++) {}
/*      */     
/*      */ 
/* 3368 */     return ok;
/*      */   }
/*      */   
/*      */ 
/*      */   private boolean moveHistory(boolean next)
/*      */     throws IOException
/*      */   {
/* 3375 */     if ((next) && (!this.history.next())) {
/* 3376 */       return false;
/*      */     }
/* 3378 */     if ((!next) && (!this.history.previous())) {
/* 3379 */       return false;
/*      */     }
/*      */     
/* 3382 */     setBuffer(this.history.current());
/*      */     
/* 3384 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3391 */   public static final String CR = Configuration.getLineSeparator();
/*      */   
/*      */ 
/*      */   private void print(int c)
/*      */     throws IOException
/*      */   {
/* 3397 */     if (c == 9) {
/* 3398 */       char[] chars = new char[4];
/* 3399 */       Arrays.fill(chars, ' ');
/* 3400 */       this.out.write(chars);
/* 3401 */       return;
/*      */     }
/*      */     
/* 3404 */     this.out.write(c);
/*      */   }
/*      */   
/*      */ 
/*      */   private void print(char... buff)
/*      */     throws IOException
/*      */   {
/* 3411 */     int len = 0;
/* 3412 */     for (char c : buff) {
/* 3413 */       if (c == '\t') {
/* 3414 */         len += 4;
/*      */       }
/*      */       else {
/* 3417 */         len++;
/*      */       }
/*      */     }
/*      */     char[] chars;
/*      */     char[] chars;
/* 3422 */     if (len == buff.length) {
/* 3423 */       chars = buff;
/*      */     }
/*      */     else {
/* 3426 */       chars = new char[len];
/* 3427 */       int pos = 0;
/* 3428 */       for (char c : buff) {
/* 3429 */         if (c == '\t') {
/* 3430 */           Arrays.fill(chars, pos, pos + 4, ' ');
/* 3431 */           pos += 4;
/*      */         }
/*      */         else {
/* 3434 */           chars[pos] = c;
/* 3435 */           pos++;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 3440 */     this.out.write(chars);
/*      */   }
/*      */   
/*      */   private void print(char c, int num) throws IOException {
/* 3444 */     if (num == 1) {
/* 3445 */       print(c);
/*      */     }
/*      */     else {
/* 3448 */       char[] chars = new char[num];
/* 3449 */       Arrays.fill(chars, c);
/* 3450 */       print(chars);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public final void print(CharSequence s)
/*      */     throws IOException
/*      */   {
/* 3458 */     print(((CharSequence)Preconditions.checkNotNull(s)).toString().toCharArray());
/*      */   }
/*      */   
/*      */   public final void println(CharSequence s) throws IOException {
/* 3462 */     print(((CharSequence)Preconditions.checkNotNull(s)).toString().toCharArray());
/* 3463 */     println();
/*      */   }
/*      */   
/*      */ 
/*      */   public final void println()
/*      */     throws IOException
/*      */   {
/* 3470 */     print(CR);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final boolean delete()
/*      */     throws IOException
/*      */   {
/* 3484 */     if (this.buf.cursor == this.buf.buffer.length()) {
/* 3485 */       return false;
/*      */     }
/*      */     
/* 3488 */     this.buf.buffer.delete(this.buf.cursor, this.buf.cursor + 1);
/* 3489 */     drawBuffer(1);
/*      */     
/* 3491 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean killLine()
/*      */     throws IOException
/*      */   {
/* 3500 */     int cp = this.buf.cursor;
/* 3501 */     int len = this.buf.buffer.length();
/*      */     
/* 3503 */     if (cp >= len) {
/* 3504 */       return false;
/*      */     }
/*      */     
/* 3507 */     int num = len - cp;
/* 3508 */     clearAhead(num, 0);
/*      */     
/* 3510 */     char[] killed = new char[num];
/* 3511 */     this.buf.buffer.getChars(cp, cp + num, killed, 0);
/* 3512 */     this.buf.buffer.delete(cp, cp + num);
/*      */     
/* 3514 */     String copy = new String(killed);
/* 3515 */     this.killRing.add(copy);
/*      */     
/* 3517 */     return true;
/*      */   }
/*      */   
/*      */   public boolean yank() throws IOException {
/* 3521 */     String yanked = this.killRing.yank();
/*      */     
/* 3523 */     if (yanked == null) {
/* 3524 */       return false;
/*      */     }
/* 3526 */     putString(yanked);
/* 3527 */     return true;
/*      */   }
/*      */   
/*      */   public boolean yankPop() throws IOException {
/* 3531 */     if (!this.killRing.lastYank()) {
/* 3532 */       return false;
/*      */     }
/* 3534 */     String current = this.killRing.yank();
/* 3535 */     if (current == null)
/*      */     {
/* 3537 */       return false;
/*      */     }
/* 3539 */     backspace(current.length());
/* 3540 */     String yanked = this.killRing.yankPop();
/* 3541 */     if (yanked == null)
/*      */     {
/* 3543 */       return false;
/*      */     }
/*      */     
/* 3546 */     putString(yanked);
/* 3547 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */   public boolean clearScreen()
/*      */     throws IOException
/*      */   {
/* 3554 */     if (!this.terminal.isAnsiSupported()) {
/* 3555 */       return false;
/*      */     }
/*      */     
/*      */ 
/* 3559 */     printAnsiSequence("2J");
/*      */     
/*      */ 
/* 3562 */     printAnsiSequence("1;1H");
/*      */     
/* 3564 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */   public void beep()
/*      */     throws IOException
/*      */   {
/* 3571 */     if (this.bellEnabled) {
/* 3572 */       print(7);
/*      */       
/* 3574 */       flush();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public boolean paste()
/*      */     throws IOException
/*      */   {
/*      */     Clipboard clipboard;
/*      */     
/*      */     try
/*      */     {
/* 3586 */       clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
/*      */     }
/*      */     catch (Exception e) {
/* 3589 */       return false;
/*      */     }
/*      */     
/* 3592 */     if (clipboard == null) {
/* 3593 */       return false;
/*      */     }
/*      */     
/* 3596 */     Transferable transferable = clipboard.getContents(null);
/*      */     
/* 3598 */     if (transferable == null) {
/* 3599 */       return false;
/*      */     }
/*      */     try
/*      */     {
/* 3603 */       Object content = transferable.getTransferData(DataFlavor.plainTextFlavor);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3610 */       if (content == null) {
/*      */         try {
/* 3612 */           content = new DataFlavor().getReaderForText(transferable);
/*      */         }
/*      */         catch (Exception e) {}
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 3619 */       if (content == null) {
/* 3620 */         return false;
/*      */       }
/*      */       
/*      */       String value;
/*      */       
/* 3625 */       if ((content instanceof Reader))
/*      */       {
/*      */ 
/* 3628 */         String value = "";
/*      */         
/*      */ 
/* 3631 */         BufferedReader read = new BufferedReader((Reader)content);
/* 3632 */         String line; while ((line = read.readLine()) != null) {
/* 3633 */           if (value.length() > 0) {
/* 3634 */             value = value + "\n";
/*      */           }
/*      */           
/* 3637 */           value = value + line;
/*      */         }
/*      */       }
/*      */       else {
/* 3641 */         value = content.toString();
/*      */       }
/*      */       
/* 3644 */       if (value == null) {
/* 3645 */         return true;
/*      */       }
/*      */       
/* 3648 */       putString(value);
/*      */       
/* 3650 */       return true;
/*      */     }
/*      */     catch (UnsupportedFlavorException e) {
/* 3653 */       Log.error(new Object[] { "Paste failed: ", e });
/*      */     }
/* 3655 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3663 */   private final Map<Character, ActionListener> triggeredActions = new HashMap();
/*      */   
/*      */ 
/*      */   private Thread maskThread;
/*      */   
/*      */ 
/*      */ 
/*      */   public void addTriggeredAction(char c, ActionListener listener)
/*      */   {
/* 3672 */     this.triggeredActions.put(Character.valueOf(c), listener);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void printColumns(Collection<? extends CharSequence> items)
/*      */     throws IOException
/*      */   {
/* 3683 */     if ((items == null) || (items.isEmpty())) {
/* 3684 */       return;
/*      */     }
/*      */     
/* 3687 */     int width = getTerminal().getWidth();
/* 3688 */     int height = getTerminal().getHeight();
/*      */     
/* 3690 */     int maxWidth = 0;
/* 3691 */     for (CharSequence item : items) {
/* 3692 */       maxWidth = Math.max(maxWidth, item.length());
/*      */     }
/* 3694 */     maxWidth += 3;
/* 3695 */     Log.debug(new Object[] { "Max width: ", Integer.valueOf(maxWidth) });
/*      */     int showLines;
/*      */     int showLines;
/* 3698 */     if (isPaginationEnabled()) {
/* 3699 */       showLines = height - 1;
/*      */     }
/*      */     else {
/* 3702 */       showLines = Integer.MAX_VALUE;
/*      */     }
/*      */     
/* 3705 */     StringBuilder buff = new StringBuilder();
/* 3706 */     for (CharSequence item : items) {
/* 3707 */       if (buff.length() + maxWidth > width) {
/* 3708 */         println(buff);
/* 3709 */         buff.setLength(0);
/*      */         
/* 3711 */         showLines--; if (showLines == 0)
/*      */         {
/* 3713 */           print(resources.getString("DISPLAY_MORE"));
/* 3714 */           flush();
/* 3715 */           int c = readCharacter();
/* 3716 */           if ((c == 13) || (c == 10))
/*      */           {
/* 3718 */             showLines = 1;
/*      */           }
/* 3720 */           else if (c != 113)
/*      */           {
/* 3722 */             showLines = height - 1;
/*      */           }
/*      */           
/* 3725 */           back(resources.getString("DISPLAY_MORE").length());
/* 3726 */           if (c == 113) {
/*      */             break;
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 3734 */       buff.append(item.toString());
/* 3735 */       for (int i = 0; i < maxWidth - item.length(); i++) {
/* 3736 */         buff.append(' ');
/*      */       }
/*      */     }
/*      */     
/* 3740 */     if (buff.length() > 0) {
/* 3741 */       println(buff);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void beforeReadLine(String prompt, Character mask)
/*      */   {
/* 3752 */     if ((mask != null) && (this.maskThread == null)) {
/* 3753 */       final String fullPrompt = "\r" + prompt + "                 " + "                 " + "                 " + "\r" + prompt;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3759 */       this.maskThread = new Thread()
/*      */       {
/*      */         public void run() {
/* 3762 */           while (!interrupted()) {
/*      */             try {
/* 3764 */               Writer out = ConsoleReader.this.getOutput();
/* 3765 */               out.write(fullPrompt);
/* 3766 */               out.flush();
/* 3767 */               sleep(3L);
/*      */ 
/*      */ 
/*      */             }
/*      */             catch (IOException e) {}catch (InterruptedException e) {}
/*      */ 
/*      */           }
/*      */           
/*      */         }
/*      */         
/*      */ 
/* 3778 */       };
/* 3779 */       this.maskThread.setPriority(10);
/* 3780 */       this.maskThread.setDaemon(true);
/* 3781 */       this.maskThread.start();
/*      */     }
/*      */   }
/*      */   
/*      */   private void afterReadLine() {
/* 3786 */     if ((this.maskThread != null) && (this.maskThread.isAlive())) {
/* 3787 */       this.maskThread.interrupt();
/*      */     }
/*      */     
/* 3790 */     this.maskThread = null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void resetPromptLine(String prompt, String buffer, int cursorDest)
/*      */     throws IOException
/*      */   {
/* 3806 */     moveToEnd();
/*      */     
/*      */ 
/* 3809 */     this.buf.buffer.append(this.prompt);
/* 3810 */     int promptLength = 0;
/* 3811 */     if (this.prompt != null) {
/* 3812 */       promptLength = this.prompt.length();
/*      */     }
/*      */     
/* 3815 */     this.buf.cursor += promptLength;
/* 3816 */     setPrompt("");
/* 3817 */     backspaceAll();
/*      */     
/* 3819 */     setPrompt(prompt);
/* 3820 */     redrawLine();
/* 3821 */     setBuffer(buffer);
/*      */     
/*      */ 
/* 3824 */     if (cursorDest < 0) cursorDest = buffer.length();
/* 3825 */     setCursorPosition(cursorDest);
/*      */     
/* 3827 */     flush();
/*      */   }
/*      */   
/*      */   public void printSearchStatus(String searchTerm, String match) throws IOException {
/* 3831 */     printSearchStatus(searchTerm, match, "(reverse-i-search)`");
/*      */   }
/*      */   
/*      */   public void printForwardSearchStatus(String searchTerm, String match) throws IOException {
/* 3835 */     printSearchStatus(searchTerm, match, "(i-search)`");
/*      */   }
/*      */   
/*      */   private void printSearchStatus(String searchTerm, String match, String searchLabel) throws IOException {
/* 3839 */     String prompt = searchLabel + searchTerm + "': ";
/* 3840 */     int cursorDest = match.indexOf(searchTerm);
/* 3841 */     resetPromptLine(prompt, match, cursorDest);
/*      */   }
/*      */   
/*      */   public void restoreLine(String originalPrompt, int cursorDest) throws IOException
/*      */   {
/* 3846 */     String prompt = lastLine(originalPrompt);
/* 3847 */     String buffer = this.buf.buffer.toString();
/* 3848 */     resetPromptLine(prompt, buffer, cursorDest);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int searchBackwards(String searchTerm, int startIndex)
/*      */   {
/* 3862 */     return searchBackwards(searchTerm, startIndex, false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int searchBackwards(String searchTerm)
/*      */   {
/* 3872 */     return searchBackwards(searchTerm, this.history.index());
/*      */   }
/*      */   
/*      */   public int searchBackwards(String searchTerm, int startIndex, boolean startsWith)
/*      */   {
/* 3877 */     ListIterator<History.Entry> it = this.history.entries(startIndex);
/* 3878 */     while (it.hasPrevious()) {
/* 3879 */       History.Entry e = (History.Entry)it.previous();
/* 3880 */       if (startsWith) {
/* 3881 */         if (e.value().toString().startsWith(searchTerm)) {
/* 3882 */           return e.index();
/*      */         }
/*      */       }
/* 3885 */       else if (e.value().toString().contains(searchTerm)) {
/* 3886 */         return e.index();
/*      */       }
/*      */     }
/*      */     
/* 3890 */     return -1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int searchForwards(String searchTerm, int startIndex)
/*      */   {
/* 3901 */     return searchForwards(searchTerm, startIndex, false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int searchForwards(String searchTerm)
/*      */   {
/* 3910 */     return searchForwards(searchTerm, this.history.index());
/*      */   }
/*      */   
/*      */   public int searchForwards(String searchTerm, int startIndex, boolean startsWith) {
/* 3914 */     if (startIndex >= this.history.size()) {
/* 3915 */       startIndex = this.history.size() - 1;
/*      */     }
/*      */     
/* 3918 */     ListIterator<History.Entry> it = this.history.entries(startIndex);
/*      */     
/* 3920 */     if ((this.searchIndex != -1) && (it.hasNext())) {
/* 3921 */       it.next();
/*      */     }
/*      */     
/* 3924 */     while (it.hasNext()) {
/* 3925 */       History.Entry e = (History.Entry)it.next();
/* 3926 */       if (startsWith) {
/* 3927 */         if (e.value().toString().startsWith(searchTerm)) {
/* 3928 */           return e.index();
/*      */         }
/*      */       }
/* 3931 */       else if (e.value().toString().contains(searchTerm)) {
/* 3932 */         return e.index();
/*      */       }
/*      */     }
/*      */     
/* 3936 */     return -1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean isDelimiter(char c)
/*      */   {
/* 3951 */     return !Character.isLetterOrDigit(c);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean isWhitespace(char c)
/*      */   {
/* 3964 */     return Character.isWhitespace(c);
/*      */   }
/*      */   
/*      */   private void printAnsiSequence(String sequence) throws IOException {
/* 3968 */     print(27);
/* 3969 */     print(91);
/* 3970 */     print(sequence);
/* 3971 */     flush();
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\console\ConsoleReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */