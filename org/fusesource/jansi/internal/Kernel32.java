/*     */ package org.fusesource.jansi.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.fusesource.hawtjni.runtime.JniArg;
/*     */ import org.fusesource.hawtjni.runtime.JniClass;
/*     */ import org.fusesource.hawtjni.runtime.JniField;
/*     */ import org.fusesource.hawtjni.runtime.JniMethod;
/*     */ import org.fusesource.hawtjni.runtime.Library;
/*     */ import org.fusesource.hawtjni.runtime.PointerMath;
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
/*     */ @JniClass(conditional="defined(_WIN32) || defined(_WIN64)")
/*     */ public class Kernel32
/*     */ {
/*  35 */   private static final Library LIBRARY = new Library("jansi", Kernel32.class);
/*     */   
/*  37 */   static { LIBRARY.load();
/*  38 */     init();
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
/*     */   @JniField(flags={org.fusesource.hawtjni.runtime.FieldFlag.CONSTANT})
/*     */   public static short FOREGROUND_BLUE;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @JniField(flags={org.fusesource.hawtjni.runtime.FieldFlag.CONSTANT})
/*     */   public static short FOREGROUND_GREEN;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @JniField(flags={org.fusesource.hawtjni.runtime.FieldFlag.CONSTANT})
/*     */   public static short FOREGROUND_RED;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @JniField(flags={org.fusesource.hawtjni.runtime.FieldFlag.CONSTANT})
/*     */   public static short FOREGROUND_INTENSITY;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @JniField(flags={org.fusesource.hawtjni.runtime.FieldFlag.CONSTANT})
/*     */   public static short BACKGROUND_BLUE;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @JniField(flags={org.fusesource.hawtjni.runtime.FieldFlag.CONSTANT})
/*     */   public static short BACKGROUND_GREEN;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @JniField(flags={org.fusesource.hawtjni.runtime.FieldFlag.CONSTANT})
/*     */   public static short BACKGROUND_RED;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @JniField(flags={org.fusesource.hawtjni.runtime.FieldFlag.CONSTANT})
/*     */   public static short BACKGROUND_INTENSITY;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @JniField(flags={org.fusesource.hawtjni.runtime.FieldFlag.CONSTANT})
/*     */   public static short COMMON_LVB_LEADING_BYTE;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @JniField(flags={org.fusesource.hawtjni.runtime.FieldFlag.CONSTANT})
/*     */   public static short COMMON_LVB_TRAILING_BYTE;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @JniClass(flags={org.fusesource.hawtjni.runtime.ClassFlag.STRUCT, org.fusesource.hawtjni.runtime.ClassFlag.TYPEDEF}, conditional="defined(_WIN32) || defined(_WIN64)")
/*     */   public static class SMALL_RECT
/*     */   {
/*     */     @JniField(flags={org.fusesource.hawtjni.runtime.FieldFlag.CONSTANT}, accessor="sizeof(SMALL_RECT)")
/*     */     public static int SIZEOF;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     @JniField(accessor="Left")
/*     */     public short left;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     @JniField(accessor="Top")
/*     */     public short top;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     @JniField(accessor="Right")
/*     */     public short right;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     @JniField(accessor="Bottom")
/*     */     public short bottom;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     static
/*     */     {
/* 205 */       Kernel32.LIBRARY.load();
/* 206 */       init();
/*     */     }
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
/*     */     public short width()
/*     */     {
/* 224 */       return (short)(this.right - this.left);
/*     */     }
/*     */     
/* 227 */     public short height() { return (short)(this.bottom - this.top); }
/*     */     
/*     */     @JniMethod(flags={org.fusesource.hawtjni.runtime.MethodFlag.CONSTANT_INITIALIZER})
/*     */     private static final native void init();
/*     */   }
/*     */   
/*     */   @JniClass(flags={org.fusesource.hawtjni.runtime.ClassFlag.STRUCT, org.fusesource.hawtjni.runtime.ClassFlag.TYPEDEF}, conditional="defined(_WIN32) || defined(_WIN64)")
/*     */   public static class COORD
/*     */   {
/*     */     @JniField(flags={org.fusesource.hawtjni.runtime.FieldFlag.CONSTANT}, accessor="sizeof(COORD)")
/*     */     public static int SIZEOF;
/*     */     @JniField(accessor="X")
/*     */     public short x;
/*     */     @JniField(accessor="Y")
/*     */     public short y;
/*     */     
/*     */     static
/*     */     {
/* 245 */       Kernel32.LIBRARY.load();
/* 246 */       init();
/*     */     }
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
/*     */     public COORD copy()
/*     */     {
/* 260 */       COORD rc = new COORD();
/* 261 */       rc.x = this.x;
/* 262 */       rc.y = this.y;
/* 263 */       return rc;
/*     */     }
/*     */     
/*     */     @JniMethod(flags={org.fusesource.hawtjni.runtime.MethodFlag.CONSTANT_INITIALIZER})
/*     */     private static final native void init();
/*     */   }
/*     */   
/*     */   @JniClass(flags={org.fusesource.hawtjni.runtime.ClassFlag.STRUCT, org.fusesource.hawtjni.runtime.ClassFlag.TYPEDEF}, conditional="defined(_WIN32) || defined(_WIN64)")
/*     */   public static class CONSOLE_SCREEN_BUFFER_INFO { @JniField(flags={org.fusesource.hawtjni.runtime.FieldFlag.CONSTANT}, accessor="sizeof(CONSOLE_SCREEN_BUFFER_INFO)")
/*     */     public static int SIZEOF;
/*     */     
/* 274 */     static { Kernel32.LIBRARY.load();
/* 275 */       init();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     @JniField(accessor="dwSize")
/* 283 */     public Kernel32.COORD size = new Kernel32.COORD();
/*     */     @JniField(accessor="dwCursorPosition")
/* 285 */     public Kernel32.COORD cursorPosition = new Kernel32.COORD();
/*     */     @JniField(accessor="wAttributes")
/*     */     public short attributes;
/*     */     @JniField(accessor="srWindow")
/* 289 */     public Kernel32.SMALL_RECT window = new Kernel32.SMALL_RECT();
/*     */     @JniField(accessor="dwMaximumWindowSize")
/* 291 */     public Kernel32.COORD maximumWindowSize = new Kernel32.COORD();
/*     */     
/*     */     public int windowWidth()
/*     */     {
/* 295 */       return this.window.width() + 1;
/*     */     }
/*     */     
/*     */     public int windowHeight() {
/* 299 */       return this.window.height() + 1;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     @JniMethod(flags={org.fusesource.hawtjni.runtime.MethodFlag.CONSTANT_INITIALIZER})
/*     */     private static final native void init();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @JniClass(flags={org.fusesource.hawtjni.runtime.ClassFlag.STRUCT, org.fusesource.hawtjni.runtime.ClassFlag.TYPEDEF}, conditional="defined(_WIN32) || defined(_WIN64)")
/*     */   public static class KEY_EVENT_RECORD
/*     */   {
/*     */     @JniField(flags={org.fusesource.hawtjni.runtime.FieldFlag.CONSTANT}, accessor="sizeof(KEY_EVENT_RECORD)")
/*     */     public static int SIZEOF;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     @JniField(flags={org.fusesource.hawtjni.runtime.FieldFlag.CONSTANT}, accessor="CAPSLOCK_ON")
/*     */     public static int CAPSLOCK_ON;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     @JniField(flags={org.fusesource.hawtjni.runtime.FieldFlag.CONSTANT}, accessor="NUMLOCK_ON")
/*     */     public static int NUMLOCK_ON;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     @JniField(flags={org.fusesource.hawtjni.runtime.FieldFlag.CONSTANT}, accessor="SCROLLLOCK_ON")
/*     */     public static int SCROLLLOCK_ON;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     @JniField(flags={org.fusesource.hawtjni.runtime.FieldFlag.CONSTANT}, accessor="ENHANCED_KEY")
/*     */     public static int ENHANCED_KEY;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     @JniField(flags={org.fusesource.hawtjni.runtime.FieldFlag.CONSTANT}, accessor="LEFT_ALT_PRESSED")
/*     */     public static int LEFT_ALT_PRESSED;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     @JniField(flags={org.fusesource.hawtjni.runtime.FieldFlag.CONSTANT}, accessor="LEFT_CTRL_PRESSED")
/*     */     public static int LEFT_CTRL_PRESSED;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     @JniField(flags={org.fusesource.hawtjni.runtime.FieldFlag.CONSTANT}, accessor="RIGHT_ALT_PRESSED")
/*     */     public static int RIGHT_ALT_PRESSED;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     @JniField(flags={org.fusesource.hawtjni.runtime.FieldFlag.CONSTANT}, accessor="RIGHT_CTRL_PRESSED")
/*     */     public static int RIGHT_CTRL_PRESSED;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     @JniField(flags={org.fusesource.hawtjni.runtime.FieldFlag.CONSTANT}, accessor="SHIFT_PRESSED")
/*     */     public static int SHIFT_PRESSED;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     @JniField(accessor="bKeyDown")
/*     */     public boolean keyDown;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     @JniField(accessor="wRepeatCount")
/*     */     public short repeatCount;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     @JniField(accessor="wVirtualKeyCode")
/*     */     public short keyCode;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     @JniField(accessor="wVirtualScanCode")
/*     */     public short scanCode;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     @JniField(accessor="uChar.UnicodeChar")
/*     */     public char uchar;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     @JniField(accessor="dwControlKeyState")
/*     */     public int controlKeyState;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     static
/*     */     {
/* 461 */       Kernel32.LIBRARY.load();
/* 462 */       init();
/*     */     }
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
/*     */     public String toString()
/*     */     {
/* 502 */       return "KEY_EVENT_RECORD{keyDown=" + this.keyDown + ", repeatCount=" + this.repeatCount + ", keyCode=" + this.keyCode + ", scanCode=" + this.scanCode + ", uchar=" + this.uchar + ", controlKeyState=" + this.controlKeyState + '}';
/*     */     }
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
/*     */     @JniMethod(flags={org.fusesource.hawtjni.runtime.MethodFlag.CONSTANT_INITIALIZER})
/*     */     private static final native void init();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @JniClass(flags={org.fusesource.hawtjni.runtime.ClassFlag.STRUCT, org.fusesource.hawtjni.runtime.ClassFlag.TYPEDEF}, conditional="defined(_WIN32) || defined(_WIN64)")
/*     */   public static class INPUT_RECORD
/*     */   {
/*     */     @JniField(flags={org.fusesource.hawtjni.runtime.FieldFlag.CONSTANT}, accessor="sizeof(INPUT_RECORD)")
/*     */     public static int SIZEOF;
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
/*     */     @JniField(flags={org.fusesource.hawtjni.runtime.FieldFlag.CONSTANT}, accessor="KEY_EVENT")
/*     */     public static short KEY_EVENT;
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
/*     */     @JniField(accessor="EventType")
/*     */     public short eventType;
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
/*     */     @JniMethod(flags={org.fusesource.hawtjni.runtime.MethodFlag.CONSTANT_INITIALIZER})
/*     */     private static final native void init();
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
/*     */     public static final native void memmove(@JniArg(cast="void *", flags={org.fusesource.hawtjni.runtime.ArgFlag.NO_IN, org.fusesource.hawtjni.runtime.ArgFlag.CRITICAL}) INPUT_RECORD paramINPUT_RECORD, @JniArg(cast="const void *", flags={org.fusesource.hawtjni.runtime.ArgFlag.NO_OUT, org.fusesource.hawtjni.runtime.ArgFlag.CRITICAL}) long paramLong1, @JniArg(cast="size_t") long paramLong2);
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
/*     */     static
/*     */     {
/* 659 */       Kernel32.LIBRARY.load();
/* 660 */       init();
/*     */     }
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
/*     */     @JniField(accessor="Event.KeyEvent")
/* 681 */     public Kernel32.KEY_EVENT_RECORD keyEvent = new Kernel32.KEY_EVENT_RECORD();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @JniField(flags={org.fusesource.hawtjni.runtime.FieldFlag.CONSTANT})
/*     */   public static short COMMON_LVB_GRID_HORIZONTAL;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @JniField(flags={org.fusesource.hawtjni.runtime.FieldFlag.CONSTANT})
/*     */   public static short COMMON_LVB_GRID_LVERTICAL;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @JniField(flags={org.fusesource.hawtjni.runtime.FieldFlag.CONSTANT})
/*     */   public static short COMMON_LVB_GRID_RVERTICAL;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @JniField(flags={org.fusesource.hawtjni.runtime.FieldFlag.CONSTANT})
/*     */   public static short COMMON_LVB_REVERSE_VIDEO;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @JniField(flags={org.fusesource.hawtjni.runtime.FieldFlag.CONSTANT})
/*     */   public static short COMMON_LVB_UNDERSCORE;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @JniField(flags={org.fusesource.hawtjni.runtime.FieldFlag.CONSTANT})
/*     */   public static int FORMAT_MESSAGE_FROM_SYSTEM;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @JniField(flags={org.fusesource.hawtjni.runtime.FieldFlag.CONSTANT})
/*     */   public static int STD_INPUT_HANDLE;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @JniField(flags={org.fusesource.hawtjni.runtime.FieldFlag.CONSTANT})
/*     */   public static int STD_OUTPUT_HANDLE;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @JniField(flags={org.fusesource.hawtjni.runtime.FieldFlag.CONSTANT})
/*     */   public static int STD_ERROR_HANDLE;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @JniField(flags={org.fusesource.hawtjni.runtime.FieldFlag.CONSTANT})
/*     */   public static int INVALID_HANDLE_VALUE;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static INPUT_RECORD[] readConsoleInputHelper(long handle, int count, boolean peek)
/*     */     throws IOException
/*     */   {
/* 753 */     int[] length = new int[1];
/*     */     
/* 755 */     long inputRecordPtr = 0L;
/*     */     try {
/* 757 */       inputRecordPtr = malloc(INPUT_RECORD.SIZEOF * count);
/* 758 */       if (inputRecordPtr == 0L) {
/* 759 */         throw new IOException("cannot allocate memory with JNI");
/*     */       }
/* 761 */       int res = peek ? PeekConsoleInputW(handle, inputRecordPtr, count, length) : ReadConsoleInputW(handle, inputRecordPtr, count, length);
/*     */       
/*     */ 
/* 764 */       if (res == 0) {
/* 765 */         throw new IOException("ReadConsoleInputW failed");
/*     */       }
/* 767 */       if (length[0] <= 0) {
/* 768 */         return new INPUT_RECORD[0];
/*     */       }
/* 770 */       INPUT_RECORD[] records = new INPUT_RECORD[length[0]];
/* 771 */       for (int i = 0; i < records.length; i++) {
/* 772 */         records[i] = new INPUT_RECORD();
/* 773 */         INPUT_RECORD.memmove(records[i], PointerMath.add(inputRecordPtr, i * INPUT_RECORD.SIZEOF), INPUT_RECORD.SIZEOF);
/*     */       }
/* 775 */       return records;
/*     */     } finally {
/* 777 */       if (inputRecordPtr != 0L) {
/* 778 */         free(inputRecordPtr);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static INPUT_RECORD[] readConsoleKeyInput(long handle, int count, boolean peek)
/*     */     throws IOException
/*     */   {
/*     */     for (;;)
/*     */     {
/* 794 */       INPUT_RECORD[] evts = readConsoleInputHelper(handle, count, peek);
/* 795 */       int keyEvtCount = 0;
/* 796 */       for (INPUT_RECORD evt : evts) {
/* 797 */         if (evt.eventType == INPUT_RECORD.KEY_EVENT) keyEvtCount++;
/*     */       }
/* 799 */       if (keyEvtCount > 0) {
/* 800 */         INPUT_RECORD[] res = new INPUT_RECORD[keyEvtCount];
/* 801 */         int i = 0;
/* 802 */         for (INPUT_RECORD evt : evts) {
/* 803 */           if (evt.eventType == INPUT_RECORD.KEY_EVENT) {
/* 804 */             res[(i++)] = evt;
/*     */           }
/*     */         }
/* 807 */         return res;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @JniMethod(flags={org.fusesource.hawtjni.runtime.MethodFlag.CONSTANT_INITIALIZER})
/*     */   private static final native void init();
/*     */   
/*     */   @JniMethod(cast="void *")
/*     */   public static final native long malloc(@JniArg(cast="size_t") long paramLong);
/*     */   
/*     */   public static final native void free(@JniArg(cast="void *") long paramLong);
/*     */   
/*     */   public static final native int SetConsoleTextAttribute(@JniArg(cast="HANDLE") long paramLong, short paramShort);
/*     */   
/*     */   public static final native int CloseHandle(@JniArg(cast="HANDLE") long paramLong);
/*     */   
/*     */   public static final native int GetLastError();
/*     */   
/*     */   public static final native int FormatMessageW(int paramInt1, @JniArg(cast="void *") long paramLong, int paramInt2, int paramInt3, @JniArg(cast="void *", flags={org.fusesource.hawtjni.runtime.ArgFlag.NO_IN, org.fusesource.hawtjni.runtime.ArgFlag.CRITICAL}) byte[] paramArrayOfByte, int paramInt4, @JniArg(cast="void *", flags={org.fusesource.hawtjni.runtime.ArgFlag.NO_IN, org.fusesource.hawtjni.runtime.ArgFlag.CRITICAL, org.fusesource.hawtjni.runtime.ArgFlag.SENTINEL}) long[] paramArrayOfLong);
/*     */   
/*     */   public static final native int GetConsoleScreenBufferInfo(@JniArg(cast="HANDLE", flags={org.fusesource.hawtjni.runtime.ArgFlag.POINTER_ARG}) long paramLong, CONSOLE_SCREEN_BUFFER_INFO paramCONSOLE_SCREEN_BUFFER_INFO);
/*     */   
/*     */   @JniMethod(cast="HANDLE", flags={org.fusesource.hawtjni.runtime.MethodFlag.POINTER_RETURN})
/*     */   public static final native long GetStdHandle(int paramInt);
/*     */   
/*     */   public static final native int SetConsoleCursorPosition(@JniArg(cast="HANDLE", flags={org.fusesource.hawtjni.runtime.ArgFlag.POINTER_ARG}) long paramLong, @JniArg(flags={org.fusesource.hawtjni.runtime.ArgFlag.BY_VALUE}) COORD paramCOORD);
/*     */   
/*     */   public static final native int FillConsoleOutputCharacterW(@JniArg(cast="HANDLE", flags={org.fusesource.hawtjni.runtime.ArgFlag.POINTER_ARG}) long paramLong, char paramChar, int paramInt, @JniArg(flags={org.fusesource.hawtjni.runtime.ArgFlag.BY_VALUE}) COORD paramCOORD, int[] paramArrayOfInt);
/*     */   
/*     */   public static final native int WriteConsoleW(@JniArg(cast="HANDLE", flags={org.fusesource.hawtjni.runtime.ArgFlag.POINTER_ARG}) long paramLong1, char[] paramArrayOfChar, int paramInt, int[] paramArrayOfInt, @JniArg(cast="LPVOID", flags={org.fusesource.hawtjni.runtime.ArgFlag.POINTER_ARG}) long paramLong2);
/*     */   
/*     */   public static final native int GetConsoleMode(@JniArg(cast="HANDLE", flags={org.fusesource.hawtjni.runtime.ArgFlag.POINTER_ARG}) long paramLong, int[] paramArrayOfInt);
/*     */   
/*     */   public static final native int SetConsoleMode(@JniArg(cast="HANDLE", flags={org.fusesource.hawtjni.runtime.ArgFlag.POINTER_ARG}) long paramLong, int paramInt);
/*     */   
/*     */   public static final native int _getch();
/*     */   
/*     */   public static final native int SetConsoleTitle(@JniArg(flags={org.fusesource.hawtjni.runtime.ArgFlag.UNICODE}) String paramString);
/*     */   
/*     */   public static final native int GetConsoleOutputCP();
/*     */   
/*     */   public static final native int SetConsoleOutputCP(int paramInt);
/*     */   
/*     */   private static final native int ReadConsoleInputW(@JniArg(cast="HANDLE", flags={org.fusesource.hawtjni.runtime.ArgFlag.POINTER_ARG}) long paramLong1, @JniArg(cast="PINPUT_RECORD", flags={org.fusesource.hawtjni.runtime.ArgFlag.POINTER_ARG}) long paramLong2, int paramInt, int[] paramArrayOfInt);
/*     */   
/*     */   private static final native int PeekConsoleInputW(@JniArg(cast="HANDLE", flags={org.fusesource.hawtjni.runtime.ArgFlag.POINTER_ARG}) long paramLong1, @JniArg(cast="PINPUT_RECORD", flags={org.fusesource.hawtjni.runtime.ArgFlag.POINTER_ARG}) long paramLong2, int paramInt, int[] paramArrayOfInt);
/*     */   
/*     */   public static final native int GetNumberOfConsoleInputEvents(@JniArg(cast="HANDLE", flags={org.fusesource.hawtjni.runtime.ArgFlag.POINTER_ARG}) long paramLong, int[] paramArrayOfInt);
/*     */   
/*     */   public static final native int FlushConsoleInputBuffer(@JniArg(cast="HANDLE", flags={org.fusesource.hawtjni.runtime.ArgFlag.POINTER_ARG}) long paramLong);
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\fusesource\jansi\internal\Kernel32.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */