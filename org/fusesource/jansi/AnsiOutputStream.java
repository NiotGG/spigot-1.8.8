/*     */ package org.fusesource.jansi;
/*     */ 
/*     */ import java.io.FilterOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.ArrayList;
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
/*     */ public class AnsiOutputStream
/*     */   extends FilterOutputStream
/*     */ {
/*  43 */   public static final byte[] REST_CODE = ;
/*     */   private static final int MAX_ESCAPE_SEQUENCE_LENGTH = 100;
/*     */   
/*  46 */   public AnsiOutputStream(OutputStream os) { super(os); }
/*     */   
/*     */ 
/*     */ 
/*  50 */   private byte[] buffer = new byte[100];
/*  51 */   private int pos = 0;
/*     */   private int startOfValue;
/*  53 */   private final ArrayList<Object> options = new ArrayList();
/*     */   
/*     */   private static final int LOOKING_FOR_FIRST_ESC_CHAR = 0;
/*     */   
/*     */   private static final int LOOKING_FOR_SECOND_ESC_CHAR = 1;
/*     */   private static final int LOOKING_FOR_NEXT_ARG = 2;
/*     */   private static final int LOOKING_FOR_STR_ARG_END = 3;
/*     */   private static final int LOOKING_FOR_INT_ARG_END = 4;
/*     */   private static final int LOOKING_FOR_OSC_COMMAND = 5;
/*     */   private static final int LOOKING_FOR_OSC_COMMAND_END = 6;
/*     */   private static final int LOOKING_FOR_OSC_PARAM = 7;
/*     */   private static final int LOOKING_FOR_ST = 8;
/*  65 */   int state = 0;
/*     */   private static final int FIRST_ESC_CHAR = 27;
/*     */   private static final int SECOND_ESC_CHAR = 91;
/*     */   private static final int SECOND_OSC_CHAR = 93;
/*     */   private static final int BEL = 7;
/*     */   private static final int SECOND_ST_CHAR = 92;
/*     */   protected static final int ERASE_SCREEN_TO_END = 0;
/*     */   protected static final int ERASE_SCREEN_TO_BEGINING = 1;
/*     */   protected static final int ERASE_SCREEN = 2;
/*     */   
/*     */   public void write(int data) throws IOException {
/*  76 */     switch (this.state) {
/*     */     case 0: 
/*  78 */       if (data == 27) {
/*  79 */         this.buffer[(this.pos++)] = ((byte)data);
/*  80 */         this.state = 1;
/*     */       } else {
/*  82 */         this.out.write(data);
/*     */       }
/*  84 */       break;
/*     */     
/*     */     case 1: 
/*  87 */       this.buffer[(this.pos++)] = ((byte)data);
/*  88 */       if (data == 91) {
/*  89 */         this.state = 2;
/*  90 */       } else if (data == 93) {
/*  91 */         this.state = 5;
/*     */       } else {
/*  93 */         reset(false);
/*     */       }
/*  95 */       break;
/*     */     
/*     */     case 2: 
/*  98 */       this.buffer[(this.pos++)] = ((byte)data);
/*  99 */       if (34 == data) {
/* 100 */         this.startOfValue = (this.pos - 1);
/* 101 */         this.state = 3;
/* 102 */       } else if ((48 <= data) && (data <= 57)) {
/* 103 */         this.startOfValue = (this.pos - 1);
/* 104 */         this.state = 4;
/* 105 */       } else if (59 == data) {
/* 106 */         this.options.add(null);
/* 107 */       } else if (63 == data) {
/* 108 */         this.options.add(new Character('?'));
/* 109 */       } else if (61 == data) {
/* 110 */         this.options.add(new Character('='));
/*     */       } else {
/* 112 */         reset(processEscapeCommand(this.options, data));
/*     */       }
/* 114 */       break;
/*     */     
/*     */     case 4: 
/* 117 */       this.buffer[(this.pos++)] = ((byte)data);
/* 118 */       if ((48 > data) || (data > 57)) {
/* 119 */         String strValue = new String(this.buffer, this.startOfValue, this.pos - 1 - this.startOfValue, "UTF-8");
/* 120 */         Integer value = new Integer(strValue);
/* 121 */         this.options.add(value);
/* 122 */         if (data == 59) {
/* 123 */           this.state = 2;
/*     */         } else
/* 125 */           reset(processEscapeCommand(this.options, data));
/*     */       }
/* 127 */       break;
/*     */     
/*     */ 
/*     */     case 3: 
/* 131 */       this.buffer[(this.pos++)] = ((byte)data);
/* 132 */       if (34 != data) {
/* 133 */         String value = new String(this.buffer, this.startOfValue, this.pos - 1 - this.startOfValue, "UTF-8");
/* 134 */         this.options.add(value);
/* 135 */         if (data == 59) {
/* 136 */           this.state = 2;
/*     */         } else
/* 138 */           reset(processEscapeCommand(this.options, data));
/*     */       }
/* 140 */       break;
/*     */     
/*     */ 
/*     */     case 5: 
/* 144 */       this.buffer[(this.pos++)] = ((byte)data);
/* 145 */       if ((48 <= data) && (data <= 57)) {
/* 146 */         this.startOfValue = (this.pos - 1);
/* 147 */         this.state = 6;
/*     */       } else {
/* 149 */         reset(false);
/*     */       }
/* 151 */       break;
/*     */     
/*     */     case 6: 
/* 154 */       this.buffer[(this.pos++)] = ((byte)data);
/* 155 */       if (59 == data) {
/* 156 */         String strValue = new String(this.buffer, this.startOfValue, this.pos - 1 - this.startOfValue, "UTF-8");
/* 157 */         Integer value = new Integer(strValue);
/* 158 */         this.options.add(value);
/* 159 */         this.startOfValue = this.pos;
/* 160 */         this.state = 7;
/* 161 */       } else if ((48 > data) || (data > 57))
/*     */       {
/*     */ 
/*     */ 
/* 165 */         reset(false);
/*     */       }
/* 167 */       break;
/*     */     
/*     */     case 7: 
/* 170 */       this.buffer[(this.pos++)] = ((byte)data);
/* 171 */       if (7 == data) {
/* 172 */         String value = new String(this.buffer, this.startOfValue, this.pos - 1 - this.startOfValue, "UTF-8");
/* 173 */         this.options.add(value);
/* 174 */         reset(processOperatingSystemCommand(this.options));
/* 175 */       } else if (27 == data) {
/* 176 */         this.state = 8;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */       break;
/*     */     case 8: 
/* 183 */       this.buffer[(this.pos++)] = ((byte)data);
/* 184 */       if (92 == data) {
/* 185 */         String value = new String(this.buffer, this.startOfValue, this.pos - 2 - this.startOfValue, "UTF-8");
/* 186 */         this.options.add(value);
/* 187 */         reset(processOperatingSystemCommand(this.options));
/*     */       } else {
/* 189 */         this.state = 7;
/*     */       }
/*     */       
/*     */       break;
/*     */     }
/*     */     
/* 195 */     if (this.pos >= this.buffer.length)
/* 196 */       reset(false); }
/*     */   
/*     */   protected static final int ERASE_LINE_TO_END = 0;
/*     */   protected static final int ERASE_LINE_TO_BEGINING = 1;
/*     */   protected static final int ERASE_LINE = 2;
/*     */   protected static final int ATTRIBUTE_INTENSITY_BOLD = 1;
/*     */   protected static final int ATTRIBUTE_INTENSITY_FAINT = 2;
/*     */   protected static final int ATTRIBUTE_ITALIC = 3;
/*     */   protected static final int ATTRIBUTE_UNDERLINE = 4;
/*     */   protected static final int ATTRIBUTE_BLINK_SLOW = 5;
/* 206 */   private void reset(boolean skipBuffer) throws IOException { if (!skipBuffer) {
/* 207 */       this.out.write(this.buffer, 0, this.pos);
/*     */     }
/* 209 */     this.pos = 0;
/* 210 */     this.startOfValue = 0;
/* 211 */     this.options.clear();
/* 212 */     this.state = 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean processEscapeCommand(ArrayList<Object> options, int command)
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 223 */       switch (command) {
/*     */       case 65: 
/* 225 */         processCursorUp(optionInt(options, 0, 1));
/* 226 */         return true;
/*     */       case 66: 
/* 228 */         processCursorDown(optionInt(options, 0, 1));
/* 229 */         return true;
/*     */       case 67: 
/* 231 */         processCursorRight(optionInt(options, 0, 1));
/* 232 */         return true;
/*     */       case 68: 
/* 234 */         processCursorLeft(optionInt(options, 0, 1));
/* 235 */         return true;
/*     */       case 69: 
/* 237 */         processCursorDownLine(optionInt(options, 0, 1));
/* 238 */         return true;
/*     */       case 70: 
/* 240 */         processCursorUpLine(optionInt(options, 0, 1));
/* 241 */         return true;
/*     */       case 71: 
/* 243 */         processCursorToColumn(optionInt(options, 0));
/* 244 */         return true;
/*     */       case 72: 
/*     */       case 102: 
/* 247 */         processCursorTo(optionInt(options, 0, 1), optionInt(options, 1, 1));
/* 248 */         return true;
/*     */       case 74: 
/* 250 */         processEraseScreen(optionInt(options, 0, 0));
/* 251 */         return true;
/*     */       case 75: 
/* 253 */         processEraseLine(optionInt(options, 0, 0));
/* 254 */         return true;
/*     */       case 83: 
/* 256 */         processScrollUp(optionInt(options, 0, 1));
/* 257 */         return true;
/*     */       case 84: 
/* 259 */         processScrollDown(optionInt(options, 0, 1));
/* 260 */         return true;
/*     */       
/*     */       case 109: 
/* 263 */         for (Object next : options) {
/* 264 */           if ((next != null) && (next.getClass() != Integer.class)) {
/* 265 */             throw new IllegalArgumentException();
/*     */           }
/*     */         }
/*     */         
/* 269 */         int count = 0;
/* 270 */         for (Object next : options) {
/* 271 */           if (next != null) {
/* 272 */             count++;
/* 273 */             int value = ((Integer)next).intValue();
/* 274 */             if ((30 <= value) && (value <= 37)) {
/* 275 */               processSetForegroundColor(value - 30);
/* 276 */             } else if ((40 <= value) && (value <= 47)) {
/* 277 */               processSetBackgroundColor(value - 40);
/*     */             } else {
/* 279 */               switch (value) {
/*     */               case 39: 
/* 281 */                 processDefaultTextColor();
/* 282 */                 break;
/*     */               case 49: 
/* 284 */                 processDefaultBackgroundColor();
/* 285 */                 break;
/*     */               case 0: 
/* 287 */                 processAttributeRest();
/* 288 */                 break;
/*     */               default: 
/* 290 */                 processSetAttribute(value);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/* 295 */         if (count == 0) {
/* 296 */           processAttributeRest();
/*     */         }
/* 298 */         return true;
/*     */       case 115: 
/* 300 */         processSaveCursorPosition();
/* 301 */         return true;
/*     */       case 117: 
/* 303 */         processRestoreCursorPosition();
/* 304 */         return true;
/*     */       }
/*     */       
/* 307 */       if ((97 <= command) && (122 <= command)) {
/* 308 */         processUnknownExtension(options, command);
/* 309 */         return true;
/*     */       }
/* 311 */       if ((65 <= command) && (90 <= command)) {
/* 312 */         processUnknownExtension(options, command);
/* 313 */         return true;
/*     */       }
/* 315 */       return false;
/*     */     }
/*     */     catch (IllegalArgumentException ignore) {}
/*     */     
/* 319 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean processOperatingSystemCommand(ArrayList<Object> options)
/*     */     throws IOException
/*     */   {
/* 328 */     int command = optionInt(options, 0);
/* 329 */     String label = (String)options.get(1);
/*     */     
/*     */     try
/*     */     {
/* 333 */       switch (command) {
/*     */       case 0: 
/* 335 */         processChangeIconNameAndWindowTitle(label);
/* 336 */         return true;
/*     */       case 1: 
/* 338 */         processChangeIconName(label);
/* 339 */         return true;
/*     */       case 2: 
/* 341 */         processChangeWindowTitle(label);
/* 342 */         return true;
/*     */       }
/*     */       
/*     */       
/* 346 */       processUnknownOperatingSystemCommand(command, label);
/* 347 */       return true;
/*     */     }
/*     */     catch (IllegalArgumentException ignore) {}
/*     */     
/* 351 */     return false;
/*     */   }
/*     */   
/*     */   protected static final int ATTRIBUTE_BLINK_FAST = 6;
/*     */   protected static final int ATTRIBUTE_NEGATIVE_ON = 7;
/*     */   protected static final int ATTRIBUTE_CONCEAL_ON = 8;
/*     */   protected static final int ATTRIBUTE_UNDERLINE_DOUBLE = 21;
/*     */   protected static final int ATTRIBUTE_INTENSITY_NORMAL = 22;
/*     */   protected static final int ATTRIBUTE_UNDERLINE_OFF = 24;
/*     */   protected static final int ATTRIBUTE_BLINK_OFF = 25;
/*     */   protected static final int ATTRIBUTE_NEGATIVE_Off = 27;
/*     */   protected static final int ATTRIBUTE_CONCEAL_OFF = 28;
/*     */   protected static final int BLACK = 0;
/*     */   protected static final int RED = 1;
/*     */   protected static final int GREEN = 2;
/*     */   protected static final int YELLOW = 3;
/*     */   protected static final int BLUE = 4;
/*     */   protected static final int MAGENTA = 5;
/*     */   protected static final int CYAN = 6;
/*     */   protected static final int WHITE = 7;
/*     */   protected void processRestoreCursorPosition()
/*     */     throws IOException
/*     */   {}
/*     */   
/*     */   protected void processSaveCursorPosition()
/*     */     throws IOException
/*     */   {}
/*     */   
/*     */   protected void processScrollDown(int optionInt)
/*     */     throws IOException
/*     */   {}
/*     */   
/*     */   protected void processScrollUp(int optionInt)
/*     */     throws IOException
/*     */   {}
/*     */   
/*     */   protected void processEraseScreen(int eraseOption)
/*     */     throws IOException
/*     */   {}
/*     */   
/*     */   protected void processEraseLine(int eraseOption)
/*     */     throws IOException
/*     */   {}
/*     */   
/*     */   protected void processSetAttribute(int attribute)
/*     */     throws IOException
/*     */   {}
/*     */   
/*     */   protected void processSetForegroundColor(int color)
/*     */     throws IOException
/*     */   {}
/*     */   
/*     */   protected void processSetBackgroundColor(int color)
/*     */     throws IOException
/*     */   {}
/*     */   
/*     */   protected void processDefaultTextColor()
/*     */     throws IOException
/*     */   {}
/*     */   
/*     */   protected void processDefaultBackgroundColor()
/*     */     throws IOException
/*     */   {}
/*     */   
/*     */   protected void processAttributeRest()
/*     */     throws IOException
/*     */   {}
/*     */   
/*     */   protected void processCursorTo(int row, int col) throws IOException
/*     */   {}
/*     */   
/*     */   protected void processCursorToColumn(int x) throws IOException
/*     */   {}
/*     */   
/*     */   protected void processCursorUpLine(int count) throws IOException
/*     */   {}
/*     */   
/*     */   protected void processCursorDownLine(int count) throws IOException
/*     */   {
/* 430 */     for (int i = 0; i < count; i++) {
/* 431 */       this.out.write(10);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void processCursorLeft(int count) throws IOException
/*     */   {}
/*     */   
/*     */   protected void processCursorRight(int count) throws IOException
/*     */   {
/* 440 */     for (int i = 0; i < count; i++) {
/* 441 */       this.out.write(32);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void processCursorDown(int count) throws IOException
/*     */   {}
/*     */   
/*     */   protected void processCursorUp(int count) throws IOException
/*     */   {}
/*     */   
/*     */   protected void processUnknownExtension(ArrayList<Object> options, int command) {}
/*     */   
/*     */   protected void processChangeIconNameAndWindowTitle(String label)
/*     */   {
/* 455 */     processChangeIconName(label);
/* 456 */     processChangeWindowTitle(label);
/*     */   }
/*     */   
/*     */ 
/*     */   protected void processChangeIconName(String label) {}
/*     */   
/*     */ 
/*     */   protected void processChangeWindowTitle(String label) {}
/*     */   
/*     */   protected void processUnknownOperatingSystemCommand(int command, String param) {}
/*     */   
/*     */   private int optionInt(ArrayList<Object> options, int index)
/*     */   {
/* 469 */     if (options.size() <= index)
/* 470 */       throw new IllegalArgumentException();
/* 471 */     Object value = options.get(index);
/* 472 */     if (value == null)
/* 473 */       throw new IllegalArgumentException();
/* 474 */     if (!value.getClass().equals(Integer.class))
/* 475 */       throw new IllegalArgumentException();
/* 476 */     return ((Integer)value).intValue();
/*     */   }
/*     */   
/*     */   private int optionInt(ArrayList<Object> options, int index, int defaultValue) {
/* 480 */     if (options.size() > index) {
/* 481 */       Object value = options.get(index);
/* 482 */       if (value == null) {
/* 483 */         return defaultValue;
/*     */       }
/* 485 */       return ((Integer)value).intValue();
/*     */     }
/* 487 */     return defaultValue;
/*     */   }
/*     */   
/*     */   public void close() throws IOException
/*     */   {
/* 492 */     write(REST_CODE);
/* 493 */     flush();
/* 494 */     super.close();
/*     */   }
/*     */   
/*     */   private static byte[] resetCode() {
/*     */     try {
/* 499 */       return new Ansi().reset().toString().getBytes("UTF-8");
/*     */     } catch (UnsupportedEncodingException e) {
/* 501 */       throw new RuntimeException(e);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\fusesource\jansi\AnsiOutputStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */