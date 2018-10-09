/*     */ package org.fusesource.jansi;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.concurrent.Callable;
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
/*     */ public class Ansi
/*     */ {
/*     */   private static final char FIRST_ESC_CHAR = '\033';
/*     */   private static final char SECOND_ESC_CHAR = '[';
/*     */   
/*     */   public static enum Color
/*     */   {
/*  34 */     BLACK(0, "BLACK"), 
/*  35 */     RED(1, "RED"), 
/*  36 */     GREEN(2, "GREEN"), 
/*  37 */     YELLOW(3, "YELLOW"), 
/*  38 */     BLUE(4, "BLUE"), 
/*  39 */     MAGENTA(5, "MAGENTA"), 
/*  40 */     CYAN(6, "CYAN"), 
/*  41 */     WHITE(7, "WHITE"), 
/*  42 */     DEFAULT(9, "DEFAULT");
/*     */     
/*     */     private final int value;
/*     */     private final String name;
/*     */     
/*     */     private Color(int index, String name) {
/*  48 */       this.value = index;
/*  49 */       this.name = name;
/*     */     }
/*     */     
/*     */     public String toString()
/*     */     {
/*  54 */       return this.name;
/*     */     }
/*     */     
/*     */     public int value() {
/*  58 */       return this.value;
/*     */     }
/*     */     
/*     */     public int fg() {
/*  62 */       return this.value + 30;
/*     */     }
/*     */     
/*     */     public int bg() {
/*  66 */       return this.value + 40;
/*     */     }
/*     */     
/*     */     public int fgBright() {
/*  70 */       return this.value + 90;
/*     */     }
/*     */     
/*     */     public int bgBright() {
/*  74 */       return this.value + 100;
/*     */     }
/*     */   }
/*     */   
/*     */   public static enum Attribute {
/*  79 */     RESET(0, "RESET"), 
/*  80 */     INTENSITY_BOLD(1, "INTENSITY_BOLD"), 
/*  81 */     INTENSITY_FAINT(2, "INTENSITY_FAINT"), 
/*  82 */     ITALIC(3, "ITALIC_ON"), 
/*  83 */     UNDERLINE(4, "UNDERLINE_ON"), 
/*  84 */     BLINK_SLOW(5, "BLINK_SLOW"), 
/*  85 */     BLINK_FAST(6, "BLINK_FAST"), 
/*  86 */     NEGATIVE_ON(7, "NEGATIVE_ON"), 
/*  87 */     CONCEAL_ON(8, "CONCEAL_ON"), 
/*  88 */     STRIKETHROUGH_ON(9, "STRIKETHROUGH_ON"), 
/*  89 */     UNDERLINE_DOUBLE(21, "UNDERLINE_DOUBLE"), 
/*  90 */     INTENSITY_BOLD_OFF(22, "INTENSITY_BOLD_OFF"), 
/*  91 */     ITALIC_OFF(23, "ITALIC_OFF"), 
/*  92 */     UNDERLINE_OFF(24, "UNDERLINE_OFF"), 
/*  93 */     BLINK_OFF(25, "BLINK_OFF"), 
/*  94 */     NEGATIVE_OFF(27, "NEGATIVE_OFF"), 
/*  95 */     CONCEAL_OFF(28, "CONCEAL_OFF"), 
/*  96 */     STRIKETHROUGH_OFF(29, "STRIKETHROUGH_OFF");
/*     */     
/*     */     private final int value;
/*     */     private final String name;
/*     */     
/*     */     private Attribute(int index, String name) {
/* 102 */       this.value = index;
/* 103 */       this.name = name;
/*     */     }
/*     */     
/*     */     public String toString()
/*     */     {
/* 108 */       return this.name;
/*     */     }
/*     */     
/*     */     public int value() {
/* 112 */       return this.value;
/*     */     }
/*     */   }
/*     */   
/*     */   public static enum Erase
/*     */   {
/* 118 */     FORWARD(0, "FORWARD"), 
/* 119 */     BACKWARD(1, "BACKWARD"), 
/* 120 */     ALL(2, "ALL");
/*     */     
/*     */     private final int value;
/*     */     private final String name;
/*     */     
/*     */     private Erase(int index, String name) {
/* 126 */       this.value = index;
/* 127 */       this.name = name;
/*     */     }
/*     */     
/*     */     public String toString()
/*     */     {
/* 132 */       return this.name;
/*     */     }
/*     */     
/*     */     public int value() {
/* 136 */       return this.value;
/*     */     }
/*     */   }
/*     */   
/* 140 */   public static final String DISABLE = Ansi.class.getName() + ".disable";
/*     */   
/* 142 */   private static Callable<Boolean> detector = new Callable() {
/*     */     public Boolean call() throws Exception {
/* 144 */       return Boolean.valueOf(!Boolean.getBoolean(Ansi.DISABLE));
/*     */     }
/*     */   };
/*     */   
/*     */   public static void setDetector(Callable<Boolean> detector) {
/* 149 */     if (detector == null) throw new IllegalArgumentException();
/* 150 */     detector = detector;
/*     */   }
/*     */   
/*     */   public static boolean isDetected() {
/*     */     try {
/* 155 */       return ((Boolean)detector.call()).booleanValue();
/*     */     }
/*     */     catch (Exception e) {}
/* 158 */     return true;
/*     */   }
/*     */   
/*     */ 
/* 162 */   private static final InheritableThreadLocal<Boolean> holder = new InheritableThreadLocal()
/*     */   {
/*     */     protected Boolean initialValue()
/*     */     {
/* 166 */       return Boolean.valueOf(Ansi.isDetected());
/*     */     }
/*     */   };
/*     */   private final StringBuilder builder;
/*     */   
/* 171 */   public static void setEnabled(boolean flag) { holder.set(Boolean.valueOf(flag)); }
/*     */   
/*     */   public static boolean isEnabled()
/*     */   {
/* 175 */     return ((Boolean)holder.get()).booleanValue();
/*     */   }
/*     */   
/*     */   public static Ansi ansi() {
/* 179 */     if (isEnabled()) {
/* 180 */       return new Ansi();
/*     */     }
/*     */     
/* 183 */     return new NoAnsi(null);
/*     */   }
/*     */   
/*     */ 
/*     */   private static class NoAnsi
/*     */     extends Ansi
/*     */   {
/*     */     public Ansi fg(Ansi.Color color)
/*     */     {
/* 192 */       return this;
/*     */     }
/*     */     
/*     */     public Ansi bg(Ansi.Color color)
/*     */     {
/* 197 */       return this;
/*     */     }
/*     */     
/*     */     public Ansi fgBright(Ansi.Color color)
/*     */     {
/* 202 */       return this;
/*     */     }
/*     */     
/*     */     public Ansi bgBright(Ansi.Color color)
/*     */     {
/* 207 */       return this;
/*     */     }
/*     */     
/*     */     public Ansi a(Ansi.Attribute attribute)
/*     */     {
/* 212 */       return this;
/*     */     }
/*     */     
/*     */     public Ansi cursor(int x, int y)
/*     */     {
/* 217 */       return this;
/*     */     }
/*     */     
/*     */     public Ansi cursorUp(int y)
/*     */     {
/* 222 */       return this;
/*     */     }
/*     */     
/*     */     public Ansi cursorRight(int x)
/*     */     {
/* 227 */       return this;
/*     */     }
/*     */     
/*     */     public Ansi cursorDown(int y)
/*     */     {
/* 232 */       return this;
/*     */     }
/*     */     
/*     */     public Ansi cursorLeft(int x)
/*     */     {
/* 237 */       return this;
/*     */     }
/*     */     
/*     */     public Ansi eraseScreen()
/*     */     {
/* 242 */       return this;
/*     */     }
/*     */     
/*     */     public Ansi eraseScreen(Ansi.Erase kind)
/*     */     {
/* 247 */       return this;
/*     */     }
/*     */     
/*     */     public Ansi eraseLine()
/*     */     {
/* 252 */       return this;
/*     */     }
/*     */     
/*     */     public Ansi eraseLine(Ansi.Erase kind)
/*     */     {
/* 257 */       return this;
/*     */     }
/*     */     
/*     */     public Ansi scrollUp(int rows)
/*     */     {
/* 262 */       return this;
/*     */     }
/*     */     
/*     */     public Ansi scrollDown(int rows)
/*     */     {
/* 267 */       return this;
/*     */     }
/*     */     
/*     */     public Ansi saveCursorPosition()
/*     */     {
/* 272 */       return this;
/*     */     }
/*     */     
/*     */     public Ansi restorCursorPosition()
/*     */     {
/* 277 */       return this;
/*     */     }
/*     */     
/*     */     public Ansi reset()
/*     */     {
/* 282 */       return this;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/* 287 */   private final ArrayList<Integer> attributeOptions = new ArrayList(5);
/*     */   
/*     */   public Ansi() {
/* 290 */     this(new StringBuilder());
/*     */   }
/*     */   
/*     */   public Ansi(Ansi parent) {
/* 294 */     this(new StringBuilder(parent.builder));
/* 295 */     this.attributeOptions.addAll(parent.attributeOptions);
/*     */   }
/*     */   
/*     */   public Ansi(int size) {
/* 299 */     this(new StringBuilder(size));
/*     */   }
/*     */   
/*     */   public Ansi(StringBuilder builder) {
/* 303 */     this.builder = builder;
/*     */   }
/*     */   
/*     */   public static Ansi ansi(StringBuilder builder) {
/* 307 */     return new Ansi(builder);
/*     */   }
/*     */   
/* 310 */   public static Ansi ansi(int size) { return new Ansi(size); }
/*     */   
/*     */   public Ansi fg(Color color)
/*     */   {
/* 314 */     this.attributeOptions.add(Integer.valueOf(color.fg()));
/* 315 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi bg(Color color) {
/* 319 */     this.attributeOptions.add(Integer.valueOf(color.bg()));
/* 320 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi fgBright(Color color) {
/* 324 */     this.attributeOptions.add(Integer.valueOf(color.fgBright()));
/* 325 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi bgBright(Color color) {
/* 329 */     this.attributeOptions.add(Integer.valueOf(color.bgBright()));
/* 330 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi a(Attribute attribute) {
/* 334 */     this.attributeOptions.add(Integer.valueOf(attribute.value()));
/* 335 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi cursor(int x, int y) {
/* 339 */     return appendEscapeSequence('H', new Object[] { Integer.valueOf(x), Integer.valueOf(y) });
/*     */   }
/*     */   
/*     */   public Ansi cursorUp(int y) {
/* 343 */     return appendEscapeSequence('A', y);
/*     */   }
/*     */   
/*     */   public Ansi cursorDown(int y) {
/* 347 */     return appendEscapeSequence('B', y);
/*     */   }
/*     */   
/*     */   public Ansi cursorRight(int x) {
/* 351 */     return appendEscapeSequence('C', x);
/*     */   }
/*     */   
/*     */   public Ansi cursorLeft(int x) {
/* 355 */     return appendEscapeSequence('D', x);
/*     */   }
/*     */   
/*     */   public Ansi eraseScreen() {
/* 359 */     return appendEscapeSequence('J', Erase.ALL.value());
/*     */   }
/*     */   
/*     */   public Ansi eraseScreen(Erase kind) {
/* 363 */     return appendEscapeSequence('J', kind.value());
/*     */   }
/*     */   
/*     */   public Ansi eraseLine() {
/* 367 */     return appendEscapeSequence('K');
/*     */   }
/*     */   
/*     */   public Ansi eraseLine(Erase kind) {
/* 371 */     return appendEscapeSequence('K', kind.value());
/*     */   }
/*     */   
/*     */   public Ansi scrollUp(int rows) {
/* 375 */     return appendEscapeSequence('S', rows);
/*     */   }
/*     */   
/*     */   public Ansi scrollDown(int rows) {
/* 379 */     return appendEscapeSequence('T', rows);
/*     */   }
/*     */   
/*     */   public Ansi saveCursorPosition() {
/* 383 */     return appendEscapeSequence('s');
/*     */   }
/*     */   
/*     */   public Ansi restorCursorPosition() {
/* 387 */     return appendEscapeSequence('u');
/*     */   }
/*     */   
/*     */   public Ansi reset() {
/* 391 */     return a(Attribute.RESET);
/*     */   }
/*     */   
/*     */   public Ansi bold() {
/* 395 */     return a(Attribute.INTENSITY_BOLD);
/*     */   }
/*     */   
/*     */   public Ansi boldOff() {
/* 399 */     return a(Attribute.INTENSITY_BOLD_OFF);
/*     */   }
/*     */   
/*     */   public Ansi a(String value) {
/* 403 */     flushAtttributes();
/* 404 */     this.builder.append(value);
/* 405 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi a(boolean value) {
/* 409 */     flushAtttributes();
/* 410 */     this.builder.append(value);
/* 411 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi a(char value) {
/* 415 */     flushAtttributes();
/* 416 */     this.builder.append(value);
/* 417 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi a(char[] value, int offset, int len) {
/* 421 */     flushAtttributes();
/* 422 */     this.builder.append(value, offset, len);
/* 423 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi a(char[] value) {
/* 427 */     flushAtttributes();
/* 428 */     this.builder.append(value);
/* 429 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi a(CharSequence value, int start, int end) {
/* 433 */     flushAtttributes();
/* 434 */     this.builder.append(value, start, end);
/* 435 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi a(CharSequence value) {
/* 439 */     flushAtttributes();
/* 440 */     this.builder.append(value);
/* 441 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi a(double value) {
/* 445 */     flushAtttributes();
/* 446 */     this.builder.append(value);
/* 447 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi a(float value) {
/* 451 */     flushAtttributes();
/* 452 */     this.builder.append(value);
/* 453 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi a(int value) {
/* 457 */     flushAtttributes();
/* 458 */     this.builder.append(value);
/* 459 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi a(long value) {
/* 463 */     flushAtttributes();
/* 464 */     this.builder.append(value);
/* 465 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi a(Object value) {
/* 469 */     flushAtttributes();
/* 470 */     this.builder.append(value);
/* 471 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi a(StringBuffer value) {
/* 475 */     flushAtttributes();
/* 476 */     this.builder.append(value);
/* 477 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi newline() {
/* 481 */     flushAtttributes();
/* 482 */     this.builder.append(System.getProperty("line.separator"));
/* 483 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi format(String pattern, Object... args) {
/* 487 */     flushAtttributes();
/* 488 */     this.builder.append(String.format(pattern, args));
/* 489 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Ansi render(String text)
/*     */   {
/* 500 */     a(AnsiRenderer.render(text));
/* 501 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Ansi render(String text, Object... args)
/*     */   {
/* 513 */     a(String.format(AnsiRenderer.render(text), args));
/* 514 */     return this;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 519 */     flushAtttributes();
/* 520 */     return this.builder.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private Ansi appendEscapeSequence(char command)
/*     */   {
/* 528 */     flushAtttributes();
/* 529 */     this.builder.append('\033');
/* 530 */     this.builder.append('[');
/* 531 */     this.builder.append(command);
/* 532 */     return this;
/*     */   }
/*     */   
/*     */   private Ansi appendEscapeSequence(char command, int option) {
/* 536 */     flushAtttributes();
/* 537 */     this.builder.append('\033');
/* 538 */     this.builder.append('[');
/* 539 */     this.builder.append(option);
/* 540 */     this.builder.append(command);
/* 541 */     return this;
/*     */   }
/*     */   
/*     */   private Ansi appendEscapeSequence(char command, Object... options) {
/* 545 */     flushAtttributes();
/* 546 */     return _appendEscapeSequence(command, options);
/*     */   }
/*     */   
/*     */   private void flushAtttributes() {
/* 550 */     if (this.attributeOptions.isEmpty())
/* 551 */       return;
/* 552 */     if ((this.attributeOptions.size() == 1) && (((Integer)this.attributeOptions.get(0)).intValue() == 0)) {
/* 553 */       this.builder.append('\033');
/* 554 */       this.builder.append('[');
/* 555 */       this.builder.append('m');
/*     */     } else {
/* 557 */       _appendEscapeSequence('m', this.attributeOptions.toArray());
/*     */     }
/* 559 */     this.attributeOptions.clear();
/*     */   }
/*     */   
/*     */   private Ansi _appendEscapeSequence(char command, Object... options) {
/* 563 */     this.builder.append('\033');
/* 564 */     this.builder.append('[');
/* 565 */     int size = options.length;
/* 566 */     for (int i = 0; i < size; i++) {
/* 567 */       if (i != 0) {
/* 568 */         this.builder.append(';');
/*     */       }
/* 570 */       if (options[i] != null) {
/* 571 */         this.builder.append(options[i]);
/*     */       }
/*     */     }
/* 574 */     this.builder.append(command);
/* 575 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\fusesource\jansi\Ansi.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */