/*     */ package org.bukkit.craftbukkit.libs.org.ibex.nestedvm;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.HashMap;
/*     */ import org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util.ELF;
/*     */ import org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util.ELF.ELFHeader;
/*     */ import org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util.ELF.PHeader;
/*     */ import org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util.ELF.Symbol;
/*     */ import org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util.ELF.Symtab;
/*     */ import org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util.Seekable;
/*     */ 
/*     */ public class Interpreter extends UnixRuntime implements Cloneable
/*     */ {
/*  16 */   private int[] registers = new int[32];
/*     */   
/*     */   private int hi;
/*     */   private int lo;
/*  20 */   private int[] fpregs = new int[32];
/*     */   
/*     */ 
/*     */   private int fcsr;
/*     */   
/*     */ 
/*     */   private int pc;
/*     */   
/*     */   public String image;
/*     */   
/*     */   private ELF.Symtab symtab;
/*     */   private int gp;
/*     */   private ELF.Symbol userInfo;
/*     */   private int entryPoint;
/*     */   private int heapStart;
/*     */   private HashMap sourceLineCache;
/*     */   
/*  37 */   private final void setFC(boolean paramBoolean) { this.fcsr = (this.fcsr & 0xFF7FFFFF | (paramBoolean ? 8388608 : 0)); }
/*  38 */   private final int roundingMode() { return this.fcsr & 0x3; }
/*     */   
/*  40 */   private final double getDouble(int paramInt) { return Double.longBitsToDouble((this.fpregs[(paramInt + 1)] & 0xFFFFFFFF) << 32 | this.fpregs[paramInt] & 0xFFFFFFFF); }
/*     */   
/*     */   private final void setDouble(int paramInt, double paramDouble) {
/*  43 */     long l = Double.doubleToLongBits(paramDouble);
/*  44 */     this.fpregs[(paramInt + 1)] = ((int)(l >>> 32));this.fpregs[paramInt] = ((int)l); }
/*     */   
/*  46 */   private final float getFloat(int paramInt) { return Float.intBitsToFloat(this.fpregs[paramInt]); }
/*  47 */   private final void setFloat(int paramInt, float paramFloat) { this.fpregs[paramInt] = Float.floatToRawIntBits(paramFloat); }
/*     */   
/*     */   protected void _execute() throws Runtime.ExecutionException {
/*     */     try {
/*  51 */       runSome();
/*     */     } catch (Runtime.ExecutionException localExecutionException) {
/*  53 */       localExecutionException.setLocation(toHex(this.pc) + ": " + sourceLine(this.pc));
/*  54 */       throw localExecutionException;
/*     */     }
/*     */   }
/*     */   
/*     */   protected Object clone() throws CloneNotSupportedException {
/*  59 */     Interpreter localInterpreter = (Interpreter)super.clone();
/*  60 */     localInterpreter.registers = ((int[])this.registers.clone());
/*  61 */     localInterpreter.fpregs = ((int[])this.fpregs.clone());
/*  62 */     return localInterpreter;
/*     */   }
/*     */   
/*     */   private final int runSome()
/*     */     throws Runtime.FaultException, Runtime.ExecutionException
/*     */   {
/*  68 */     int i = 1 << this.pageShift >> 2;
/*  69 */     int[] arrayOfInt1 = this.registers;
/*  70 */     int[] arrayOfInt2 = this.fpregs;
/*  71 */     int j = this.pc;
/*  72 */     int k = j + 4;
/*     */     try {
/*     */       for (;;) {
/*     */         int m;
/*     */         try {
/*  77 */           m = this.readPages[(j >>> this.pageShift)][(j >>> 2 & i - 1)];
/*     */         } catch (RuntimeException localRuntimeException1) {
/*  79 */           if (j == -559038737) throw new Error("fell off cpu: r2: " + arrayOfInt1[2]);
/*  80 */           m = memRead(j);
/*     */         }
/*     */         
/*  83 */         int n = m >>> 26 & 0xFF;
/*  84 */         int i1 = m >>> 21 & 0x1F;
/*  85 */         int i2 = m >>> 16 & 0x1F;
/*  86 */         int i3 = m >>> 16 & 0x1F;
/*  87 */         int i4 = m >>> 11 & 0x1F;
/*  88 */         int i5 = m >>> 11 & 0x1F;
/*  89 */         int i6 = m >>> 6 & 0x1F;
/*  90 */         int i7 = m >>> 6 & 0x1F;
/*  91 */         int i8 = m & 0x3F;
/*     */         
/*  93 */         int i9 = m & 0x3FFFFFF;
/*  94 */         int i10 = m & 0xFFFF;
/*  95 */         int i11 = m << 16 >> 16;
/*  96 */         int i12 = i11;
/*     */         
/*     */ 
/*     */ 
/* 100 */         arrayOfInt1[0] = 0;
/*     */         int i13;
/* 102 */         int i16; switch (n) {
/*     */         case 0:  long l;
/* 104 */           switch (i8) {
/*     */           case 0: 
/* 106 */             if (m != 0)
/* 107 */               arrayOfInt1[i2] <<= i6;
/* 108 */             break;
/*     */           case 2: 
/* 110 */             arrayOfInt1[i2] >>>= i6;
/* 111 */             break;
/*     */           case 3: 
/* 113 */             arrayOfInt1[i2] >>= i6;
/* 114 */             break;
/*     */           case 4: 
/* 116 */             arrayOfInt1[i2] <<= (arrayOfInt1[i1] & 0x1F);
/* 117 */             break;
/*     */           case 6: 
/* 119 */             arrayOfInt1[i2] >>>= (arrayOfInt1[i1] & 0x1F);
/* 120 */             break;
/*     */           case 7: 
/* 122 */             arrayOfInt1[i2] >>= (arrayOfInt1[i1] & 0x1F);
/* 123 */             break;
/*     */           case 8: 
/* 125 */             i13 = arrayOfInt1[i1];j += 4;k = i13;
/* 126 */             break;
/*     */           case 9: 
/* 128 */             i13 = arrayOfInt1[i1];j += 4;arrayOfInt1[i4] = (j + 4);k = i13;
/* 129 */             break;
/*     */           case 12: 
/* 131 */             this.pc = j;
/* 132 */             arrayOfInt1[2] = syscall(arrayOfInt1[2], arrayOfInt1[4], arrayOfInt1[5], arrayOfInt1[6], arrayOfInt1[7], arrayOfInt1[8], arrayOfInt1[9]);
/* 133 */             if (this.state != 0) this.pc = k;
/*     */             break;
/*     */           case 13: 
/* 136 */             throw new Runtime.ExecutionException("Break");
/*     */           case 16: 
/* 138 */             arrayOfInt1[i4] = this.hi;
/* 139 */             break;
/*     */           case 17: 
/* 141 */             this.hi = arrayOfInt1[i1];
/* 142 */             break;
/*     */           case 18: 
/* 144 */             arrayOfInt1[i4] = this.lo;
/* 145 */             break;
/*     */           case 19: 
/* 147 */             this.lo = arrayOfInt1[i1];
/* 148 */             break;
/*     */           case 24: 
/* 150 */             l = arrayOfInt1[i1] * arrayOfInt1[i2];
/* 151 */             this.hi = ((int)(l >>> 32));
/* 152 */             this.lo = ((int)l);
/* 153 */             break;
/*     */           
/*     */           case 25: 
/* 156 */             l = (arrayOfInt1[i1] & 0xFFFFFFFF) * (arrayOfInt1[i2] & 0xFFFFFFFF);
/* 157 */             this.hi = ((int)(l >>> 32));
/* 158 */             this.lo = ((int)l);
/* 159 */             break;
/*     */           
/*     */           case 26: 
/* 162 */             this.hi = (arrayOfInt1[i1] % arrayOfInt1[i2]);
/* 163 */             this.lo = (arrayOfInt1[i1] / arrayOfInt1[i2]);
/* 164 */             break;
/*     */           case 27: 
/* 166 */             if (i2 != 0) {
/* 167 */               this.hi = ((int)((arrayOfInt1[i1] & 0xFFFFFFFF) % (arrayOfInt1[i2] & 0xFFFFFFFF)));
/* 168 */               this.lo = ((int)((arrayOfInt1[i1] & 0xFFFFFFFF) / (arrayOfInt1[i2] & 0xFFFFFFFF)));
/*     */             }
/*     */             break;
/*     */           case 32: 
/* 172 */             throw new Runtime.ExecutionException("ADD (add with oveflow trap) not suported");
/*     */           
/*     */ 
/*     */ 
/*     */           case 33: 
/* 177 */             arrayOfInt1[i1] += arrayOfInt1[i2];
/* 178 */             break;
/*     */           case 34: 
/* 180 */             throw new Runtime.ExecutionException("SUB (sub with oveflow trap) not suported");
/*     */           
/*     */ 
/*     */ 
/*     */           case 35: 
/* 185 */             arrayOfInt1[i1] -= arrayOfInt1[i2];
/* 186 */             break;
/*     */           case 36: 
/* 188 */             arrayOfInt1[i1] &= arrayOfInt1[i2];
/* 189 */             break;
/*     */           case 37: 
/* 191 */             arrayOfInt1[i1] |= arrayOfInt1[i2];
/* 192 */             break;
/*     */           case 38: 
/* 194 */             arrayOfInt1[i1] ^= arrayOfInt1[i2];
/* 195 */             break;
/*     */           case 39: 
/* 197 */             arrayOfInt1[i4] = ((arrayOfInt1[i1] | arrayOfInt1[i2]) ^ 0xFFFFFFFF);
/* 198 */             break;
/*     */           case 42: 
/* 200 */             arrayOfInt1[i4] = (arrayOfInt1[i1] < arrayOfInt1[i2] ? 1 : 0);
/* 201 */             break;
/*     */           case 43: 
/* 203 */             arrayOfInt1[i4] = ((arrayOfInt1[i1] & 0xFFFFFFFF) < (arrayOfInt1[i2] & 0xFFFFFFFF) ? 1 : 0);
/* 204 */             break;
/*     */           case 1: case 5: case 10: case 11: case 14: case 15: case 20: case 21: case 22: case 23: case 28: case 29: case 30: case 31: case 40: case 41: default: 
/* 206 */             throw new Runtime.ExecutionException("Illegal instruction 0/" + i8);
/*     */           }
/*     */           
/*     */           break;
/*     */         case 1: 
/* 211 */           switch (i2) {
/*     */           case 0: 
/* 213 */             if (arrayOfInt1[i1] < 0) {
/* 214 */               j += 4;i13 = j + i12 * 4;k = i13; }
/* 215 */             break;
/*     */           
/*     */ 
/*     */           case 1: 
/* 219 */             if (arrayOfInt1[i1] >= 0) {
/* 220 */               j += 4;i13 = j + i12 * 4;k = i13; }
/* 221 */             break;
/*     */           
/*     */ 
/*     */           case 16: 
/* 225 */             if (arrayOfInt1[i1] < 0) {
/* 226 */               j += 4;arrayOfInt1[31] = (j + 4);i13 = j + i12 * 4;k = i13; }
/* 227 */             break;
/*     */           
/*     */ 
/*     */           case 17: 
/* 231 */             if (arrayOfInt1[i1] >= 0) {
/* 232 */               j += 4;arrayOfInt1[31] = (j + 4);i13 = j + i12 * 4;k = i13; }
/* 233 */             break;
/*     */           
/*     */ 
/*     */           default: 
/* 237 */             throw new Runtime.ExecutionException("Illegal Instruction");
/*     */           }
/*     */           
/*     */           break;
/*     */         case 2: 
/* 242 */           i13 = j & 0xF0000000 | i9 << 2;
/* 243 */           j += 4;k = i13;
/* 244 */           break;
/*     */         
/*     */         case 3: 
/* 247 */           i13 = j & 0xF0000000 | i9 << 2;
/* 248 */           j += 4;arrayOfInt1[31] = (j + 4);k = i13;
/* 249 */           break;
/*     */         
/*     */         case 4: 
/* 252 */           if (arrayOfInt1[i1] == arrayOfInt1[i2]) {
/* 253 */             j += 4;i13 = j + i12 * 4;k = i13; }
/* 254 */           break;
/*     */         
/*     */ 
/*     */         case 5: 
/* 258 */           if (arrayOfInt1[i1] != arrayOfInt1[i2]) {
/* 259 */             j += 4;i13 = j + i12 * 4;k = i13; }
/* 260 */           break;
/*     */         
/*     */ 
/*     */         case 6: 
/* 264 */           if (arrayOfInt1[i1] <= 0) {
/* 265 */             j += 4;i13 = j + i12 * 4;k = i13; }
/* 266 */           break;
/*     */         
/*     */ 
/*     */         case 7: 
/* 270 */           if (arrayOfInt1[i1] > 0) {
/* 271 */             j += 4;i13 = j + i12 * 4;k = i13; }
/* 272 */           break;
/*     */         
/*     */ 
/*     */         case 8: 
/* 276 */           arrayOfInt1[i1] += i11;
/* 277 */           break;
/*     */         case 9: 
/* 279 */           arrayOfInt1[i1] += i11;
/* 280 */           break;
/*     */         case 10: 
/* 282 */           arrayOfInt1[i2] = (arrayOfInt1[i1] < i11 ? 1 : 0);
/* 283 */           break;
/*     */         case 11: 
/* 285 */           arrayOfInt1[i2] = ((arrayOfInt1[i1] & 0xFFFFFFFF) < (i11 & 0xFFFFFFFF) ? 1 : 0);
/* 286 */           break;
/*     */         case 12: 
/* 288 */           arrayOfInt1[i1] &= i10;
/* 289 */           break;
/*     */         case 13: 
/* 291 */           arrayOfInt1[i1] |= i10;
/* 292 */           break;
/*     */         case 14: 
/* 294 */           arrayOfInt1[i1] ^= i10;
/* 295 */           break;
/*     */         case 15: 
/* 297 */           arrayOfInt1[i2] = (i10 << 16);
/* 298 */           break;
/*     */         case 16: 
/* 300 */           throw new Runtime.ExecutionException("TLB/Exception support not implemented");
/*     */         case 17: 
/* 302 */           int i14 = 0;
/* 303 */           String str = i14 != 0 ? sourceLine(j) : "";
/* 304 */           int i15 = (i14 != 0) && ((str.indexOf("dtoa.c:51") >= 0) || (str.indexOf("dtoa.c:52") >= 0) || (str.indexOf("test.c") >= 0)) ? 1 : 0;
/* 305 */           if ((i1 > 8) && (i15 != 0))
/* 306 */             System.out.println("               FP Op: " + n + "/" + i1 + "/" + i8 + " " + str);
/* 307 */           if ((roundingMode() != 0) && (i1 != 6) && (((i1 != 16) && (i1 != 17)) || (i8 != 36)))
/* 308 */             throw new Runtime.ExecutionException("Non-cvt.w.z operation attempted with roundingMode != round to nearest");
/* 309 */           switch (i1) {
/*     */           case 0: 
/* 311 */             arrayOfInt1[i2] = arrayOfInt2[i4];
/* 312 */             break;
/*     */           case 2: 
/* 314 */             if (i5 != 31) throw new Runtime.ExecutionException("FCR " + i5 + " unavailable");
/* 315 */             arrayOfInt1[i2] = this.fcsr;
/* 316 */             break;
/*     */           case 4: 
/* 318 */             arrayOfInt2[i4] = arrayOfInt1[i2];
/* 319 */             break;
/*     */           case 6: 
/* 321 */             if (i5 != 31) throw new Runtime.ExecutionException("FCR " + i5 + " unavailable");
/* 322 */             this.fcsr = arrayOfInt1[i2];
/* 323 */             break;
/*     */           case 8: 
/* 325 */             if (((this.fcsr & 0x800000) != 0 ? 1 : 0) == ((m >>> 16 & 0x1) != 0 ? 1 : 0)) {
/* 326 */               j += 4;i13 = j + i12 * 4;k = i13; }
/* 327 */             break;
/*     */           
/*     */ 
/*     */           case 16: 
/* 331 */             switch (i8) {
/*     */             case 0: 
/* 333 */               setFloat(i7, getFloat(i5) + getFloat(i3));
/* 334 */               break;
/*     */             case 1: 
/* 336 */               setFloat(i7, getFloat(i5) - getFloat(i3));
/* 337 */               break;
/*     */             case 2: 
/* 339 */               setFloat(i7, getFloat(i5) * getFloat(i3));
/* 340 */               break;
/*     */             case 3: 
/* 342 */               setFloat(i7, getFloat(i5) / getFloat(i3));
/* 343 */               break;
/*     */             case 5: 
/* 345 */               setFloat(i7, Math.abs(getFloat(i5)));
/* 346 */               break;
/*     */             case 6: 
/* 348 */               arrayOfInt2[i7] = arrayOfInt2[i5];
/* 349 */               break;
/*     */             case 7: 
/* 351 */               setFloat(i7, -getFloat(i5));
/* 352 */               break;
/*     */             case 33: 
/* 354 */               setDouble(i7, getFloat(i5));
/* 355 */               break;
/*     */             case 36: 
/* 357 */               switch (roundingMode()) {
/* 358 */               case 0:  arrayOfInt2[i7] = ((int)Math.floor(getFloat(i5) + 0.5F)); break;
/* 359 */               case 1:  arrayOfInt2[i7] = ((int)getFloat(i5)); break;
/* 360 */               case 2:  arrayOfInt2[i7] = ((int)Math.ceil(getFloat(i5))); break;
/* 361 */               case 3:  arrayOfInt2[i7] = ((int)Math.floor(getFloat(i5)));
/*     */               }
/* 363 */               break;
/*     */             case 50: 
/* 365 */               setFC(getFloat(i5) == getFloat(i3));
/* 366 */               break;
/*     */             case 60: 
/* 368 */               setFC(getFloat(i5) < getFloat(i3));
/* 369 */               break;
/*     */             case 62: 
/* 371 */               setFC(getFloat(i5) <= getFloat(i3));
/* 372 */               break;
/* 373 */             default:  throw new Runtime.ExecutionException("Invalid Instruction 17/" + i1 + "/" + i8 + " at " + sourceLine(j));
/*     */             }
/*     */             
/*     */             break;
/*     */           case 17: 
/* 378 */             switch (i8) {
/*     */             case 0: 
/* 380 */               setDouble(i7, getDouble(i5) + getDouble(i3));
/* 381 */               break;
/*     */             case 1: 
/* 383 */               if (i15 != 0) System.out.println("f" + i7 + " = f" + i5 + " (" + getDouble(i5) + ") - f" + i3 + " (" + getDouble(i3) + ")");
/* 384 */               setDouble(i7, getDouble(i5) - getDouble(i3));
/* 385 */               break;
/*     */             case 2: 
/* 387 */               if (i15 != 0) System.out.println("f" + i7 + " = f" + i5 + " (" + getDouble(i5) + ") * f" + i3 + " (" + getDouble(i3) + ")");
/* 388 */               setDouble(i7, getDouble(i5) * getDouble(i3));
/* 389 */               if (i15 != 0) System.out.println("f" + i7 + " = " + getDouble(i7));
/*     */               break;
/*     */             case 3: 
/* 392 */               setDouble(i7, getDouble(i5) / getDouble(i3));
/* 393 */               break;
/*     */             case 5: 
/* 395 */               setDouble(i7, Math.abs(getDouble(i5)));
/* 396 */               break;
/*     */             case 6: 
/* 398 */               arrayOfInt2[i7] = arrayOfInt2[i5];
/* 399 */               arrayOfInt2[(i7 + 1)] = arrayOfInt2[(i5 + 1)];
/* 400 */               break;
/*     */             case 7: 
/* 402 */               setDouble(i7, -getDouble(i5));
/* 403 */               break;
/*     */             case 32: 
/* 405 */               setFloat(i7, (float)getDouble(i5));
/* 406 */               break;
/*     */             case 36: 
/* 408 */               if (i15 != 0) System.out.println("CVT.W.D rm: " + roundingMode() + " f" + i5 + ":" + getDouble(i5));
/* 409 */               switch (roundingMode()) {
/* 410 */               case 0:  arrayOfInt2[i7] = ((int)Math.floor(getDouble(i5) + 0.5D)); break;
/* 411 */               case 1:  arrayOfInt2[i7] = ((int)getDouble(i5)); break;
/* 412 */               case 2:  arrayOfInt2[i7] = ((int)Math.ceil(getDouble(i5))); break;
/* 413 */               case 3:  arrayOfInt2[i7] = ((int)Math.floor(getDouble(i5)));
/*     */               }
/* 415 */               if (i15 != 0) System.out.println("CVT.W.D: f" + i7 + ":" + arrayOfInt2[i7]);
/*     */               break;
/*     */             case 50: 
/* 418 */               setFC(getDouble(i5) == getDouble(i3));
/* 419 */               break;
/*     */             case 60: 
/* 421 */               setFC(getDouble(i5) < getDouble(i3));
/* 422 */               break;
/*     */             case 62: 
/* 424 */               setFC(getDouble(i5) <= getDouble(i3));
/* 425 */               break;
/* 426 */             default:  throw new Runtime.ExecutionException("Invalid Instruction 17/" + i1 + "/" + i8 + " at " + sourceLine(j));
/*     */             }
/*     */             
/*     */             break;
/*     */           case 20: 
/* 431 */             switch (i8) {
/*     */             case 32: 
/* 433 */               setFloat(i7, arrayOfInt2[i5]);
/* 434 */               break;
/*     */             case 33: 
/* 436 */               setDouble(i7, arrayOfInt2[i5]);
/* 437 */               break;
/* 438 */             default:  throw new Runtime.ExecutionException("Invalid Instruction 17/" + i1 + "/" + i8 + " at " + sourceLine(j));
/*     */             }
/*     */             break;
/*     */           case 1: case 3: case 5: case 7: case 9: case 10: case 11: 
/*     */           case 12: case 13: case 14: case 15: case 18: case 19: default: 
/* 443 */             throw new Runtime.ExecutionException("Invalid Instruction 17/" + i1);
/*     */           }
/*     */           break;
/*     */         case 18: 
/*     */         case 19: 
/* 448 */           throw new Runtime.ExecutionException("No coprocessor installed");
/*     */         case 32: 
/* 450 */           i16 = arrayOfInt1[i1] + i11;
/*     */           try {
/* 452 */             i13 = this.readPages[(i16 >>> this.pageShift)][(i16 >>> 2 & i - 1)];
/*     */           } catch (RuntimeException localRuntimeException2) {
/* 454 */             i13 = memRead(i16 & 0xFFFFFFFC);
/*     */           }
/* 456 */           switch (i16 & 0x3) {
/* 457 */           case 0:  i13 = i13 >>> 24 & 0xFF; break;
/* 458 */           case 1:  i13 = i13 >>> 16 & 0xFF; break;
/* 459 */           case 2:  i13 = i13 >>> 8 & 0xFF; break;
/* 460 */           case 3:  i13 = i13 >>> 0 & 0xFF;
/*     */           }
/* 462 */           if ((i13 & 0x80) != 0) i13 |= 0xFF00;
/* 463 */           arrayOfInt1[i2] = i13;
/* 464 */           break;
/*     */         
/*     */         case 33: 
/* 467 */           i16 = arrayOfInt1[i1] + i11;
/*     */           try {
/* 469 */             i13 = this.readPages[(i16 >>> this.pageShift)][(i16 >>> 2 & i - 1)];
/*     */           } catch (RuntimeException localRuntimeException3) {
/* 471 */             i13 = memRead(i16 & 0xFFFFFFFC);
/*     */           }
/* 473 */           switch (i16 & 0x3) {
/* 474 */           case 0:  i13 = i13 >>> 16 & 0xFFFF; break;
/* 475 */           case 2:  i13 = i13 >>> 0 & 0xFFFF; break;
/* 476 */           default:  throw new Runtime.ReadFaultException(i16);
/*     */           }
/* 478 */           if ((i13 & 0x8000) != 0) i13 |= 0xFFFF0000;
/* 479 */           arrayOfInt1[i2] = i13;
/* 480 */           break;
/*     */         
/*     */         case 34: 
/* 483 */           i16 = arrayOfInt1[i1] + i11;
/*     */           try {
/* 485 */             i13 = this.readPages[(i16 >>> this.pageShift)][(i16 >>> 2 & i - 1)];
/*     */           } catch (RuntimeException localRuntimeException4) {
/* 487 */             i13 = memRead(i16 & 0xFFFFFFFC);
/*     */           }
/* 489 */           switch (i16 & 0x3) {
/* 490 */           case 0:  arrayOfInt1[i2] = (arrayOfInt1[i2] & 0x0 | i13 << 0); break;
/* 491 */           case 1:  arrayOfInt1[i2] = (arrayOfInt1[i2] & 0xFF | i13 << 8); break;
/* 492 */           case 2:  arrayOfInt1[i2] = (arrayOfInt1[i2] & 0xFFFF | i13 << 16); break;
/* 493 */           case 3:  arrayOfInt1[i2] = (arrayOfInt1[i2] & 0xFFFFFF | i13 << 24);
/*     */           }
/* 495 */           break;
/*     */         
/*     */         case 35: 
/* 498 */           i16 = arrayOfInt1[i1] + i11;
/*     */           try {
/* 500 */             arrayOfInt1[i2] = this.readPages[(i16 >>> this.pageShift)][(i16 >>> 2 & i - 1)];
/*     */           } catch (RuntimeException localRuntimeException5) {
/* 502 */             arrayOfInt1[i2] = memRead(i16);
/*     */           }
/*     */         
/*     */         case 36: 
/* 506 */           i16 = arrayOfInt1[i1] + i11;
/*     */           try {
/* 508 */             i13 = this.readPages[(i16 >>> this.pageShift)][(i16 >>> 2 & i - 1)];
/*     */           } catch (RuntimeException localRuntimeException6) {
/* 510 */             i13 = memRead(i16);
/*     */           }
/* 512 */           switch (i16 & 0x3) {
/* 513 */           case 0:  arrayOfInt1[i2] = (i13 >>> 24 & 0xFF); break;
/* 514 */           case 1:  arrayOfInt1[i2] = (i13 >>> 16 & 0xFF); break;
/* 515 */           case 2:  arrayOfInt1[i2] = (i13 >>> 8 & 0xFF); break;
/* 516 */           case 3:  arrayOfInt1[i2] = (i13 >>> 0 & 0xFF);
/*     */           }
/* 518 */           break;
/*     */         
/*     */         case 37: 
/* 521 */           i16 = arrayOfInt1[i1] + i11;
/*     */           try {
/* 523 */             i13 = this.readPages[(i16 >>> this.pageShift)][(i16 >>> 2 & i - 1)];
/*     */           } catch (RuntimeException localRuntimeException7) {
/* 525 */             i13 = memRead(i16 & 0xFFFFFFFC);
/*     */           }
/* 527 */           switch (i16 & 0x3) {
/* 528 */           case 0:  arrayOfInt1[i2] = (i13 >>> 16 & 0xFFFF); break;
/* 529 */           case 2:  arrayOfInt1[i2] = (i13 >>> 0 & 0xFFFF); break;
/* 530 */           default:  throw new Runtime.ReadFaultException(i16);
/*     */           }
/*     */           
/*     */         
/*     */         case 38: 
/* 535 */           i16 = arrayOfInt1[i1] + i11;
/*     */           try {
/* 537 */             i13 = this.readPages[(i16 >>> this.pageShift)][(i16 >>> 2 & i - 1)];
/*     */           } catch (RuntimeException localRuntimeException8) {
/* 539 */             i13 = memRead(i16 & 0xFFFFFFFC);
/*     */           }
/* 541 */           switch (i16 & 0x3) {
/* 542 */           case 0:  arrayOfInt1[i2] = (arrayOfInt1[i2] & 0xFF00 | i13 >>> 24); break;
/* 543 */           case 1:  arrayOfInt1[i2] = (arrayOfInt1[i2] & 0xFFFF0000 | i13 >>> 16); break;
/* 544 */           case 2:  arrayOfInt1[i2] = (arrayOfInt1[i2] & 0xFF000000 | i13 >>> 8); break;
/* 545 */           case 3:  arrayOfInt1[i2] = (arrayOfInt1[i2] & 0x0 | i13 >>> 0);
/*     */           }
/* 547 */           break;
/*     */         
/*     */         case 40: 
/* 550 */           i16 = arrayOfInt1[i1] + i11;
/*     */           try {
/* 552 */             i13 = this.readPages[(i16 >>> this.pageShift)][(i16 >>> 2 & i - 1)];
/*     */           } catch (RuntimeException localRuntimeException9) {
/* 554 */             i13 = memRead(i16 & 0xFFFFFFFC);
/*     */           }
/* 556 */           switch (i16 & 0x3) {
/* 557 */           case 0:  i13 = i13 & 0xFFFFFF | (arrayOfInt1[i2] & 0xFF) << 24; break;
/* 558 */           case 1:  i13 = i13 & 0xFF00FFFF | (arrayOfInt1[i2] & 0xFF) << 16; break;
/* 559 */           case 2:  i13 = i13 & 0xFFFF00FF | (arrayOfInt1[i2] & 0xFF) << 8; break;
/* 560 */           case 3:  i13 = i13 & 0xFF00 | (arrayOfInt1[i2] & 0xFF) << 0;
/*     */           }
/*     */           try {
/* 563 */             this.writePages[(i16 >>> this.pageShift)][(i16 >>> 2 & i - 1)] = i13;
/*     */           } catch (RuntimeException localRuntimeException10) {
/* 565 */             memWrite(i16 & 0xFFFFFFFC, i13);
/*     */           }
/*     */         
/*     */ 
/*     */         case 41: 
/* 570 */           i16 = arrayOfInt1[i1] + i11;
/*     */           try {
/* 572 */             i13 = this.readPages[(i16 >>> this.pageShift)][(i16 >>> 2 & i - 1)];
/*     */           } catch (RuntimeException localRuntimeException11) {
/* 574 */             i13 = memRead(i16 & 0xFFFFFFFC);
/*     */           }
/* 576 */           switch (i16 & 0x3) {
/* 577 */           case 0:  i13 = i13 & 0xFFFF | (arrayOfInt1[i2] & 0xFFFF) << 16; break;
/* 578 */           case 2:  i13 = i13 & 0xFFFF0000 | (arrayOfInt1[i2] & 0xFFFF) << 0; break;
/* 579 */           default:  throw new Runtime.WriteFaultException(i16);
/*     */           }
/*     */           try {
/* 582 */             this.writePages[(i16 >>> this.pageShift)][(i16 >>> 2 & i - 1)] = i13;
/*     */           } catch (RuntimeException localRuntimeException12) {
/* 584 */             memWrite(i16 & 0xFFFFFFFC, i13);
/*     */           }
/*     */         
/*     */ 
/*     */         case 42: 
/* 589 */           i16 = arrayOfInt1[i1] + i11;
/* 590 */           i13 = memRead(i16 & 0xFFFFFFFC);
/* 591 */           switch (i16 & 0x3) {
/* 592 */           case 0:  i13 = i13 & 0x0 | arrayOfInt1[i2] >>> 0; break;
/* 593 */           case 1:  i13 = i13 & 0xFF000000 | arrayOfInt1[i2] >>> 8; break;
/* 594 */           case 2:  i13 = i13 & 0xFFFF0000 | arrayOfInt1[i2] >>> 16; break;
/* 595 */           case 3:  i13 = i13 & 0xFF00 | arrayOfInt1[i2] >>> 24;
/*     */           }
/*     */           try {
/* 598 */             this.writePages[(i16 >>> this.pageShift)][(i16 >>> 2 & i - 1)] = i13;
/*     */           } catch (RuntimeException localRuntimeException13) {
/* 600 */             memWrite(i16 & 0xFFFFFFFC, i13);
/*     */           }
/*     */         
/*     */ 
/*     */         case 43: 
/* 605 */           i16 = arrayOfInt1[i1] + i11;
/*     */           try {
/* 607 */             this.writePages[(i16 >>> this.pageShift)][(i16 >>> 2 & i - 1)] = arrayOfInt1[i2];
/*     */           } catch (RuntimeException localRuntimeException14) {
/* 609 */             memWrite(i16 & 0xFFFFFFFC, arrayOfInt1[i2]);
/*     */           }
/*     */         
/*     */         case 46: 
/* 613 */           i16 = arrayOfInt1[i1] + i11;
/* 614 */           i13 = memRead(i16 & 0xFFFFFFFC);
/* 615 */           switch (i16 & 0x3) {
/* 616 */           case 0:  i13 = i13 & 0xFFFFFF | arrayOfInt1[i2] << 24; break;
/* 617 */           case 1:  i13 = i13 & 0xFFFF | arrayOfInt1[i2] << 16; break;
/* 618 */           case 2:  i13 = i13 & 0xFF | arrayOfInt1[i2] << 8; break;
/* 619 */           case 3:  i13 = i13 & 0x0 | arrayOfInt1[i2] << 0;
/*     */           }
/* 621 */           memWrite(i16 & 0xFFFFFFFC, i13);
/* 622 */           break;
/*     */         
/*     */ 
/*     */         case 48: 
/* 626 */           arrayOfInt1[i2] = memRead(arrayOfInt1[i1] + i11);
/* 627 */           break;
/*     */         case 49: 
/* 629 */           arrayOfInt2[i2] = memRead(arrayOfInt1[i1] + i11);
/* 630 */           break;
/*     */         
/*     */         case 56: 
/* 633 */           memWrite(arrayOfInt1[i1] + i11, arrayOfInt1[i2]);
/* 634 */           arrayOfInt1[i2] = 1;
/* 635 */           break;
/*     */         case 57: 
/* 637 */           memWrite(arrayOfInt1[i1] + i11, arrayOfInt2[i2]);
/* 638 */           break;
/*     */         case 20: case 21: case 22: case 23: case 24: case 25: case 26: case 27: case 28: case 29: case 30: case 31: case 39: case 44: case 45: case 47: case 50: case 51: case 52: case 53: case 54: case 55: default: 
/* 640 */           throw new Runtime.ExecutionException("Invalid Instruction: " + n);
/*     */           
/* 642 */           j = k;
/* 643 */           k = j + 4; }
/*     */       }
/*     */     } catch (Runtime.ExecutionException localExecutionException) {
/* 646 */       this.pc = j;
/* 647 */       throw localExecutionException;
/*     */     }
/* 649 */     return 0;
/*     */   }
/*     */   
/*     */   public int lookupSymbol(String paramString) {
/* 653 */     ELF.Symbol localSymbol = this.symtab.getGlobalSymbol(paramString);
/* 654 */     return localSymbol == null ? -1 : localSymbol.addr;
/*     */   }
/*     */   
/*     */   protected int gp() {
/* 658 */     return this.gp;
/*     */   }
/*     */   
/* 661 */   protected int userInfoBae() { return this.userInfo == null ? 0 : this.userInfo.addr; }
/* 662 */   protected int userInfoSize() { return this.userInfo == null ? 0 : this.userInfo.size; }
/*     */   
/*     */   protected int entryPoint() {
/* 665 */     return this.entryPoint;
/*     */   }
/*     */   
/* 668 */   protected int heapStart() { return this.heapStart; }
/*     */   
/*     */   private void loadImage(Seekable paramSeekable) throws IOException
/*     */   {
/* 672 */     ELF localELF = new ELF(paramSeekable);
/* 673 */     this.symtab = localELF.getSymtab();
/*     */     
/* 675 */     if (localELF.header.type != 2) throw new IOException("Binary is not an executable");
/* 676 */     if (localELF.header.machine != 8) throw new IOException("Binary is not for the MIPS I Architecture");
/* 677 */     if (localELF.ident.data != 2) { throw new IOException("Binary is not big endian");
/*     */     }
/* 679 */     this.entryPoint = localELF.header.entry;
/*     */     
/* 681 */     ELF.Symtab localSymtab = localELF.getSymtab();
/* 682 */     if (localSymtab == null) throw new IOException("No symtab in binary (did you strip it?)");
/* 683 */     this.userInfo = localSymtab.getGlobalSymbol("user_info");
/* 684 */     ELF.Symbol localSymbol = localSymtab.getGlobalSymbol("_gp");
/*     */     
/* 686 */     if (localSymbol == null) throw new IOException("NO _gp symbol!");
/* 687 */     this.gp = localSymbol.addr;
/*     */     
/* 689 */     this.entryPoint = localELF.header.entry;
/*     */     
/* 691 */     ELF.PHeader[] arrayOfPHeader = localELF.pheaders;
/* 692 */     int i = 0;
/* 693 */     int j = 1 << this.pageShift;
/* 694 */     int k = 1 << this.pageShift >> 2;
/* 695 */     for (int m = 0; m < arrayOfPHeader.length; m++) {
/* 696 */       ELF.PHeader localPHeader = arrayOfPHeader[m];
/* 697 */       if (localPHeader.type == 1) {
/* 698 */         int n = localPHeader.memsz;
/* 699 */         int i1 = localPHeader.filesz;
/* 700 */         if (n != 0) {
/* 701 */           if (n < 0) throw new IOException("pheader size too large");
/* 702 */           int i2 = localPHeader.vaddr;
/* 703 */           if (i2 == 0) throw new IOException("pheader vaddr == 0x0");
/* 704 */           i = max(i2 + n, i);
/*     */           
/* 706 */           for (int i3 = 0; i3 < n + j - 1; i3 += j) {
/* 707 */             int i4 = i3 + i2 >>> this.pageShift;
/* 708 */             if (this.readPages[i4] == null)
/* 709 */               this.readPages[i4] = new int[k];
/* 710 */             if (localPHeader.writable()) this.writePages[i4] = this.readPages[i4];
/*     */           }
/* 712 */           if (i1 != 0) {
/* 713 */             i1 &= 0xFFFFFFFC;
/* 714 */             DataInputStream localDataInputStream = new DataInputStream(localPHeader.getInputStream());
/*     */             do {
/* 716 */               this.readPages[(i2 >>> this.pageShift)][(i2 >>> 2 & k - 1)] = localDataInputStream.readInt();
/* 717 */               i2 += 4;
/* 718 */               i1 -= 4;
/* 719 */             } while (i1 > 0);
/* 720 */             localDataInputStream.close();
/*     */           }
/*     */         } } }
/* 723 */     this.heapStart = (i + j - 1 & (j - 1 ^ 0xFFFFFFFF));
/*     */   }
/*     */   
/*     */   protected void setCPUState(Runtime.CPUState paramCPUState) {
/* 727 */     for (int i = 1; i < 32; i++) this.registers[i] = paramCPUState.r[i];
/* 728 */     for (i = 0; i < 32; i++) this.fpregs[i] = paramCPUState.f[i];
/* 729 */     this.hi = paramCPUState.hi;this.lo = paramCPUState.lo;this.fcsr = paramCPUState.fcsr;
/* 730 */     this.pc = paramCPUState.pc;
/*     */   }
/*     */   
/*     */   protected void getCPUState(Runtime.CPUState paramCPUState) {
/* 734 */     for (int i = 1; i < 32; i++) paramCPUState.r[i] = this.registers[i];
/* 735 */     for (i = 0; i < 32; i++) paramCPUState.f[i] = this.fpregs[i];
/* 736 */     paramCPUState.hi = this.hi;paramCPUState.lo = this.lo;paramCPUState.fcsr = this.fcsr;
/* 737 */     paramCPUState.pc = this.pc;
/*     */   }
/*     */   
/*     */   public Interpreter(Seekable paramSeekable) throws IOException {
/* 741 */     super(4096, 65536);
/* 742 */     loadImage(paramSeekable);
/*     */   }
/*     */   
/* 745 */   public Interpreter(String paramString) throws IOException { this(new org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util.Seekable.File(paramString, false));
/* 746 */     this.image = paramString; }
/*     */   
/* 748 */   public Interpreter(java.io.InputStream paramInputStream) throws IOException { this(new org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util.Seekable.InputStream(paramInputStream)); }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String sourceLine(int paramInt)
/*     */   {
/* 755 */     String str = (String)(this.sourceLineCache == null ? null : this.sourceLineCache.get(new Integer(paramInt)));
/* 756 */     if (str != null) return str;
/* 757 */     if (this.image == null) return null;
/*     */     try {
/* 759 */       Process localProcess = java.lang.Runtime.getRuntime().exec(new String[] { "mips-unknown-elf-addr2line", "-e", this.image, toHex(paramInt) });
/* 760 */       str = new java.io.BufferedReader(new java.io.InputStreamReader(localProcess.getInputStream())).readLine();
/* 761 */       if (str == null) return null;
/* 762 */       while (str.startsWith("../")) str = str.substring(3);
/* 763 */       if (this.sourceLineCache == null) this.sourceLineCache = new HashMap();
/* 764 */       this.sourceLineCache.put(new Integer(paramInt), str);
/* 765 */       return str;
/*     */     } catch (IOException localIOException) {}
/* 767 */     return null;
/*     */   }
/*     */   
/*     */   public class DebugShutdownHook implements Runnable {
/*     */     public DebugShutdownHook() {}
/*     */     
/* 773 */     public void run() { int i = Interpreter.this.pc;
/* 774 */       if (Interpreter.this.getState() == 0)
/* 775 */         System.err.print("\nCPU Executing " + Runtime.toHex(i) + ": " + Interpreter.this.sourceLine(i) + "\n");
/*     */     }
/*     */   }
/*     */   
/*     */   public static void main(String[] paramArrayOfString) throws Exception {
/* 780 */     String str = paramArrayOfString[0];
/* 781 */     Interpreter localInterpreter = new Interpreter(str); Interpreter 
/* 782 */       tmp25_24 = localInterpreter;tmp25_24.getClass();java.lang.Runtime.getRuntime().addShutdownHook(new Thread(new DebugShutdownHook(tmp25_24)));
/* 783 */     int i = localInterpreter.run(paramArrayOfString);
/* 784 */     System.err.println("Exit status: " + i);
/* 785 */     System.exit(i);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\org\ibex\nestedvm\Interpreter.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */