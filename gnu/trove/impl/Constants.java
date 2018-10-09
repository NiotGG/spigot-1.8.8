/*     */ package gnu.trove.impl;
/*     */ 
/*     */ import java.io.PrintStream;
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
/*     */ public class Constants
/*     */ {
/*  26 */   private static final boolean VERBOSE = System.getProperty("gnu.trove.verbose", null) != null;
/*     */   
/*     */   public static final int DEFAULT_CAPACITY = 10;
/*     */   public static final float DEFAULT_LOAD_FACTOR = 0.5F;
/*     */   public static final byte DEFAULT_BYTE_NO_ENTRY_VALUE;
/*     */   public static final short DEFAULT_SHORT_NO_ENTRY_VALUE;
/*     */   public static final char DEFAULT_CHAR_NO_ENTRY_VALUE;
/*     */   public static final int DEFAULT_INT_NO_ENTRY_VALUE;
/*     */   public static final long DEFAULT_LONG_NO_ENTRY_VALUE;
/*     */   public static final float DEFAULT_FLOAT_NO_ENTRY_VALUE;
/*     */   public static final double DEFAULT_DOUBLE_NO_ENTRY_VALUE;
/*     */   
/*     */   static
/*     */   {
/*  40 */     String property = System.getProperty("gnu.trove.no_entry.byte", "0");
/*  41 */     byte value; byte value; if ("MAX_VALUE".equalsIgnoreCase(property)) { value = Byte.MAX_VALUE; } else { byte value;
/*  42 */       if ("MIN_VALUE".equalsIgnoreCase(property)) value = Byte.MIN_VALUE; else
/*  43 */         value = Byte.valueOf(property).byteValue();
/*     */     }
/*  45 */     if (value > Byte.MAX_VALUE) { value = Byte.MAX_VALUE;
/*  46 */     } else if (value < Byte.MIN_VALUE) value = Byte.MIN_VALUE;
/*  47 */     DEFAULT_BYTE_NO_ENTRY_VALUE = value;
/*  48 */     if (VERBOSE) {
/*  49 */       System.out.println("DEFAULT_BYTE_NO_ENTRY_VALUE: " + DEFAULT_BYTE_NO_ENTRY_VALUE);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  59 */     String property = System.getProperty("gnu.trove.no_entry.short", "0");
/*  60 */     short value; short value; if ("MAX_VALUE".equalsIgnoreCase(property)) { value = Short.MAX_VALUE; } else { short value;
/*  61 */       if ("MIN_VALUE".equalsIgnoreCase(property)) value = Short.MIN_VALUE; else
/*  62 */         value = Short.valueOf(property).shortValue();
/*     */     }
/*  64 */     if (value > Short.MAX_VALUE) { value = Short.MAX_VALUE;
/*  65 */     } else if (value < Short.MIN_VALUE) value = Short.MIN_VALUE;
/*  66 */     DEFAULT_SHORT_NO_ENTRY_VALUE = value;
/*  67 */     if (VERBOSE) {
/*  68 */       System.out.println("DEFAULT_SHORT_NO_ENTRY_VALUE: " + DEFAULT_SHORT_NO_ENTRY_VALUE);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  78 */     String property = System.getProperty("gnu.trove.no_entry.char", "\000");
/*  79 */     char value; char value; if ("MAX_VALUE".equalsIgnoreCase(property)) { value = 65535; } else { char value;
/*  80 */       if ("MIN_VALUE".equalsIgnoreCase(property)) value = '\000'; else
/*  81 */         value = property.toCharArray()[0];
/*     */     }
/*  83 */     if (value > 65535) { value = 65535;
/*  84 */     } else if (value < 0) value = '\000';
/*  85 */     DEFAULT_CHAR_NO_ENTRY_VALUE = value;
/*  86 */     if (VERBOSE) {
/*  87 */       System.out.println("DEFAULT_CHAR_NO_ENTRY_VALUE: " + Integer.valueOf(value));
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  97 */     String property = System.getProperty("gnu.trove.no_entry.int", "0");
/*  98 */     int value; int value; if ("MAX_VALUE".equalsIgnoreCase(property)) { value = Integer.MAX_VALUE; } else { int value;
/*  99 */       if ("MIN_VALUE".equalsIgnoreCase(property)) value = Integer.MIN_VALUE; else
/* 100 */         value = Integer.valueOf(property).intValue(); }
/* 101 */     DEFAULT_INT_NO_ENTRY_VALUE = value;
/* 102 */     if (VERBOSE) {
/* 103 */       System.out.println("DEFAULT_INT_NO_ENTRY_VALUE: " + DEFAULT_INT_NO_ENTRY_VALUE);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 113 */     String property = System.getProperty("gnu.trove.no_entry.long", "0");
/* 114 */     long value; long value; if ("MAX_VALUE".equalsIgnoreCase(property)) { value = Long.MAX_VALUE; } else { long value;
/* 115 */       if ("MIN_VALUE".equalsIgnoreCase(property)) value = Long.MIN_VALUE; else
/* 116 */         value = Long.valueOf(property).longValue(); }
/* 117 */     DEFAULT_LONG_NO_ENTRY_VALUE = value;
/* 118 */     if (VERBOSE) {
/* 119 */       System.out.println("DEFAULT_LONG_NO_ENTRY_VALUE: " + DEFAULT_LONG_NO_ENTRY_VALUE);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 129 */     String property = System.getProperty("gnu.trove.no_entry.float", "0");
/* 130 */     float value; float value; if ("MAX_VALUE".equalsIgnoreCase(property)) { value = Float.MAX_VALUE; } else { float value;
/* 131 */       if ("MIN_VALUE".equalsIgnoreCase(property)) { value = Float.MIN_VALUE;
/*     */       } else { float value;
/* 133 */         if ("MIN_NORMAL".equalsIgnoreCase(property)) { value = 1.17549435E-38F; } else { float value;
/* 134 */           if ("NEGATIVE_INFINITY".equalsIgnoreCase(property)) { value = Float.NEGATIVE_INFINITY; } else { float value;
/* 135 */             if ("POSITIVE_INFINITY".equalsIgnoreCase(property)) { value = Float.POSITIVE_INFINITY;
/*     */             } else
/* 137 */               value = Float.valueOf(property).floatValue(); } } } }
/* 138 */     DEFAULT_FLOAT_NO_ENTRY_VALUE = value;
/* 139 */     if (VERBOSE) {
/* 140 */       System.out.println("DEFAULT_FLOAT_NO_ENTRY_VALUE: " + DEFAULT_FLOAT_NO_ENTRY_VALUE);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 150 */     String property = System.getProperty("gnu.trove.no_entry.double", "0");
/* 151 */     double value; double value; if ("MAX_VALUE".equalsIgnoreCase(property)) { value = Double.MAX_VALUE; } else { double value;
/* 152 */       if ("MIN_VALUE".equalsIgnoreCase(property)) { value = Double.MIN_VALUE;
/*     */       } else { double value;
/* 154 */         if ("MIN_NORMAL".equalsIgnoreCase(property)) { value = 2.2250738585072014E-308D; } else { double value;
/* 155 */           if ("NEGATIVE_INFINITY".equalsIgnoreCase(property)) { value = Double.NEGATIVE_INFINITY; } else { double value;
/* 156 */             if ("POSITIVE_INFINITY".equalsIgnoreCase(property)) { value = Double.POSITIVE_INFINITY;
/*     */             } else
/* 158 */               value = Double.valueOf(property).doubleValue(); } } } }
/* 159 */     DEFAULT_DOUBLE_NO_ENTRY_VALUE = value;
/* 160 */     if (VERBOSE) {
/* 161 */       System.out.println("DEFAULT_DOUBLE_NO_ENTRY_VALUE: " + DEFAULT_DOUBLE_NO_ENTRY_VALUE);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\impl\Constants.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */