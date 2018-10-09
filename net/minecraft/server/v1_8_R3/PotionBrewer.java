/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PotionBrewer
/*     */ {
/*  55 */   private static final Map<Integer, String> effectDurations = ;
/*  56 */   private static final Map<Integer, String> effectAmplifiers = Maps.newHashMap();
/*     */   
/*     */   static
/*     */   {
/*  60 */     a = null;
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
/*  82 */     c = "+0-1-2-3&4-4+13";
/*  83 */     effectDurations.put(Integer.valueOf(MobEffectList.REGENERATION.getId()), "0 & !1 & !2 & !3 & 0+6");
/*     */     
/*  85 */     b = "-0+1-2-3&4-4+13";
/*  86 */     effectDurations.put(Integer.valueOf(MobEffectList.FASTER_MOVEMENT.getId()), "!0 & 1 & !2 & !3 & 1+6");
/*     */     
/*  88 */     h = "+0+1-2-3&4-4+13";
/*  89 */     effectDurations.put(Integer.valueOf(MobEffectList.FIRE_RESISTANCE.getId()), "0 & 1 & !2 & !3 & 0+6");
/*     */     
/*  91 */     f = "+0-1+2-3&4-4+13";
/*  92 */     effectDurations.put(Integer.valueOf(MobEffectList.HEAL.getId()), "0 & !1 & 2 & !3");
/*     */     
/*  94 */     d = "-0-1+2-3&4-4+13";
/*  95 */     effectDurations.put(Integer.valueOf(MobEffectList.POISON.getId()), "!0 & !1 & 2 & !3 & 2+6");
/*     */     
/*  97 */     e = "-0+3-4+13";
/*  98 */     effectDurations.put(Integer.valueOf(MobEffectList.WEAKNESS.getId()), "!0 & !1 & !2 & 3 & 3+6");
/*  99 */     effectDurations.put(Integer.valueOf(MobEffectList.HARM.getId()), "!0 & !1 & 2 & 3");
/* 100 */     effectDurations.put(Integer.valueOf(MobEffectList.SLOWER_MOVEMENT.getId()), "!0 & 1 & !2 & 3 & 3+6");
/*     */     
/* 102 */     g = "+0-1-2+3&4-4+13";
/* 103 */     effectDurations.put(Integer.valueOf(MobEffectList.INCREASE_DAMAGE.getId()), "0 & !1 & !2 & 3 & 3+6");
/*     */     
/* 105 */     l = "-0+1+2-3+13&4-4";
/* 106 */     effectDurations.put(Integer.valueOf(MobEffectList.NIGHT_VISION.getId()), "!0 & 1 & 2 & !3 & 2+6");
/*     */     
/*     */ 
/* 109 */     effectDurations.put(Integer.valueOf(MobEffectList.INVISIBILITY.getId()), "!0 & 1 & 2 & 3 & 2+6");
/*     */     
/* 111 */     m = "+0-1+2+3+13&4-4";
/* 112 */     effectDurations.put(Integer.valueOf(MobEffectList.WATER_BREATHING.getId()), "0 & !1 & 2 & 3 & 2+6");
/*     */     
/* 114 */     n = "+0+1-2+3&4-4+13";
/* 115 */     effectDurations.put(Integer.valueOf(MobEffectList.JUMP.getId()), "0 & 1 & !2 & 3 & 3+6");
/*     */     
/*     */ 
/* 118 */     j = "+5-6-7";
/* 119 */     effectAmplifiers.put(Integer.valueOf(MobEffectList.FASTER_MOVEMENT.getId()), "5");
/* 120 */     effectAmplifiers.put(Integer.valueOf(MobEffectList.FASTER_DIG.getId()), "5");
/* 121 */     effectAmplifiers.put(Integer.valueOf(MobEffectList.INCREASE_DAMAGE.getId()), "5");
/* 122 */     effectAmplifiers.put(Integer.valueOf(MobEffectList.REGENERATION.getId()), "5");
/* 123 */     effectAmplifiers.put(Integer.valueOf(MobEffectList.HARM.getId()), "5");
/* 124 */     effectAmplifiers.put(Integer.valueOf(MobEffectList.HEAL.getId()), "5");
/* 125 */     effectAmplifiers.put(Integer.valueOf(MobEffectList.RESISTANCE.getId()), "5");
/* 126 */     effectAmplifiers.put(Integer.valueOf(MobEffectList.POISON.getId()), "5");
/* 127 */     effectAmplifiers.put(Integer.valueOf(MobEffectList.JUMP.getId()), "5");
/*     */   }
/*     */   
/* 130 */   public static final String i = "-5+6-7";
/*     */   
/*     */ 
/*     */ 
/* 134 */   public static final String k = "+14&13-13";
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean a(int paramInt1, int paramInt2)
/*     */   {
/* 186 */     return (paramInt1 & 1 << paramInt2) != 0;
/*     */   }
/*     */   
/*     */   private static int c(int paramInt1, int paramInt2) {
/* 190 */     return a(paramInt1, paramInt2) ? 1 : 0;
/*     */   }
/*     */   
/*     */   private static int d(int paramInt1, int paramInt2) {
/* 194 */     return a(paramInt1, paramInt2) ? 0 : 1;
/*     */   }
/*     */   
/*     */   public static int a(int paramInt) {
/* 198 */     return a(paramInt, 5, 4, 3, 2, 1);
/*     */   }
/*     */   
/*     */   public static int a(Collection<MobEffect> paramCollection) {
/* 202 */     int i1 = 3694022;
/*     */     
/* 204 */     if ((paramCollection == null) || (paramCollection.isEmpty())) {
/* 205 */       return i1;
/*     */     }
/*     */     
/* 208 */     float f1 = 0.0F;
/* 209 */     float f2 = 0.0F;
/* 210 */     float f3 = 0.0F;
/* 211 */     float f4 = 0.0F;
/*     */     
/* 213 */     for (MobEffect localMobEffect : paramCollection) {
/* 214 */       if (localMobEffect.isShowParticles())
/*     */       {
/*     */ 
/* 217 */         int i2 = MobEffectList.byId[localMobEffect.getEffectId()].k();
/*     */         
/* 219 */         for (int i3 = 0; i3 <= localMobEffect.getAmplifier(); i3++) {
/* 220 */           f1 += (i2 >> 16 & 0xFF) / 255.0F;
/* 221 */           f2 += (i2 >> 8 & 0xFF) / 255.0F;
/* 222 */           f3 += (i2 >> 0 & 0xFF) / 255.0F;
/* 223 */           f4 += 1.0F;
/*     */         }
/*     */       }
/*     */     }
/* 227 */     if (f4 == 0.0F) {
/* 228 */       return 0;
/*     */     }
/*     */     
/* 231 */     f1 = f1 / f4 * 255.0F;
/* 232 */     f2 = f2 / f4 * 255.0F;
/* 233 */     f3 = f3 / f4 * 255.0F;
/*     */     
/* 235 */     return (int)f1 << 16 | (int)f2 << 8 | (int)f3;
/*     */   }
/*     */   
/*     */   public static boolean b(Collection<MobEffect> paramCollection) {
/* 239 */     for (MobEffect localMobEffect : paramCollection) {
/* 240 */       if (!localMobEffect.isAmbient()) {
/* 241 */         return false;
/*     */       }
/*     */     }
/*     */     
/* 245 */     return true;
/*     */   }
/*     */   
/* 248 */   private static final Map<Integer, Integer> q = Maps.newHashMap();
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
/* 269 */   private static final String[] appearances = { "potion.prefix.mundane", "potion.prefix.uninteresting", "potion.prefix.bland", "potion.prefix.clear", "potion.prefix.milky", "potion.prefix.diffuse", "potion.prefix.artless", "potion.prefix.thin", "potion.prefix.awkward", "potion.prefix.flat", "potion.prefix.bulky", "potion.prefix.bungling", "potion.prefix.buttered", "potion.prefix.smooth", "potion.prefix.suave", "potion.prefix.debonair", "potion.prefix.thick", "potion.prefix.elegant", "potion.prefix.fancy", "potion.prefix.charming", "potion.prefix.dashing", "potion.prefix.refined", "potion.prefix.cordial", "potion.prefix.sparkling", "potion.prefix.potent", "potion.prefix.foul", "potion.prefix.odorless", "potion.prefix.rank", "potion.prefix.harsh", "potion.prefix.acrid", "potion.prefix.gross", "potion.prefix.stinky" };
/*     */   
/*     */ 
/*     */   public static final String a;
/*     */   
/*     */ 
/*     */   public static final String b;
/*     */   
/*     */ 
/*     */   public static final String c;
/*     */   
/*     */ 
/*     */   public static final String d;
/*     */   
/*     */ 
/*     */   public static final String e;
/*     */   
/*     */ 
/*     */   public static final String f;
/*     */   
/*     */ 
/*     */   public static final String g;
/*     */   
/*     */ 
/*     */   public static final String h;
/*     */   
/*     */   public static final String j;
/*     */   
/*     */   public static final String l;
/*     */   
/*     */   public static final String m;
/*     */   
/*     */   public static final String n;
/*     */   
/*     */ 
/*     */   public static String c(int paramInt)
/*     */   {
/* 306 */     int i1 = a(paramInt);
/* 307 */     return appearances[i1];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static int a(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/* 316 */     int i1 = 0;
/* 317 */     if (paramBoolean1) {
/* 318 */       i1 = d(paramInt4, paramInt2);
/* 319 */     } else if (paramInt1 != -1) {
/* 320 */       if ((paramInt1 == 0) && (h(paramInt4) == paramInt2)) {
/* 321 */         i1 = 1;
/* 322 */       } else if ((paramInt1 == 1) && (h(paramInt4) > paramInt2)) {
/* 323 */         i1 = 1;
/* 324 */       } else if ((paramInt1 == 2) && (h(paramInt4) < paramInt2)) {
/* 325 */         i1 = 1;
/*     */       }
/*     */     } else {
/* 328 */       i1 = c(paramInt4, paramInt2);
/*     */     }
/* 330 */     if (paramBoolean2) {
/* 331 */       i1 *= paramInt3;
/*     */     }
/* 333 */     if (paramBoolean3) {
/* 334 */       i1 *= -1;
/*     */     }
/* 336 */     return i1;
/*     */   }
/*     */   
/*     */   private static int h(int paramInt) {
/* 340 */     for (int i1 = 0; 
/* 341 */         paramInt > 0; i1++) {
/* 342 */       paramInt &= paramInt - 1;
/*     */     }
/* 344 */     return i1;
/*     */   }
/*     */   
/*     */   private static int a(String paramString, int paramInt1, int paramInt2, int paramInt3) {
/* 348 */     if ((paramInt1 >= paramString.length()) || (paramInt2 < 0) || (paramInt1 >= paramInt2)) {
/* 349 */       return 0;
/*     */     }
/*     */     
/*     */ 
/* 353 */     int i1 = paramString.indexOf('|', paramInt1);
/* 354 */     if ((i1 >= 0) && (i1 < paramInt2)) {
/* 355 */       i2 = a(paramString, paramInt1, i1 - 1, paramInt3);
/* 356 */       if (i2 > 0) {
/* 357 */         return i2;
/*     */       }
/*     */       
/* 360 */       i3 = a(paramString, i1 + 1, paramInt2, paramInt3);
/* 361 */       if (i3 > 0) {
/* 362 */         return i3;
/*     */       }
/* 364 */       return 0;
/*     */     }
/*     */     
/* 367 */     int i2 = paramString.indexOf('&', paramInt1);
/* 368 */     if ((i2 >= 0) && (i2 < paramInt2)) {
/* 369 */       i3 = a(paramString, paramInt1, i2 - 1, paramInt3);
/* 370 */       if (i3 <= 0) {
/* 371 */         return 0;
/*     */       }
/*     */       
/* 374 */       i4 = a(paramString, i2 + 1, paramInt2, paramInt3);
/* 375 */       if (i4 <= 0) {
/* 376 */         return 0;
/*     */       }
/*     */       
/* 379 */       if (i3 > i4) {
/* 380 */         return i3;
/*     */       }
/* 382 */       return i4;
/*     */     }
/*     */     
/* 385 */     int i3 = 0;
/* 386 */     int i4 = 0;
/* 387 */     int i5 = 0;
/* 388 */     boolean bool2 = false;
/* 389 */     boolean bool3 = false;
/* 390 */     int i6 = -1;
/* 391 */     int i7 = 0;
/* 392 */     int i8 = 0;
/* 393 */     int i9 = 0;
/* 394 */     boolean bool1; for (int i10 = paramInt1; i10 < paramInt2; i10++) {
/* 395 */       int i11 = paramString.charAt(i10);
/* 396 */       if ((i11 >= 48) && (i11 <= 57)) {
/* 397 */         if (i3 != 0) {
/* 398 */           i8 = i11 - 48;
/* 399 */           i4 = 1;
/*     */         } else {
/* 401 */           i7 *= 10;
/* 402 */           i7 += i11 - 48;
/* 403 */           i5 = 1;
/*     */         }
/* 405 */       } else if (i11 == 42) {
/* 406 */         i3 = 1;
/* 407 */       } else if (i11 == 33) {
/* 408 */         if (i5 != 0) {
/* 409 */           i9 += a(bool2, i4, bool3, i6, i7, i8, paramInt3);
/* 410 */           i5 = bool1 = i3 = bool3 = bool2 = 0;
/* 411 */           i7 = i8 = 0;
/* 412 */           i6 = -1;
/*     */         }
/*     */         
/* 415 */         bool2 = true;
/* 416 */       } else if (i11 == 45) {
/* 417 */         if (i5 != 0) {
/* 418 */           i9 += a(bool2, bool1, bool3, i6, i7, i8, paramInt3);
/* 419 */           i5 = bool1 = i3 = bool3 = bool2 = 0;
/* 420 */           i7 = i8 = 0;
/* 421 */           i6 = -1;
/*     */         }
/*     */         
/* 424 */         bool3 = true;
/* 425 */       } else if ((i11 == 61) || (i11 == 60) || (i11 == 62)) {
/* 426 */         if (i5 != 0) {
/* 427 */           i9 += a(bool2, bool1, bool3, i6, i7, i8, paramInt3);
/* 428 */           i5 = bool1 = i3 = bool3 = bool2 = 0;
/* 429 */           i7 = i8 = 0;
/* 430 */           i6 = -1;
/*     */         }
/*     */         
/* 433 */         if (i11 == 61) {
/* 434 */           i6 = 0;
/* 435 */         } else if (i11 == 60) {
/* 436 */           i6 = 2;
/* 437 */         } else if (i11 == 62) {
/* 438 */           i6 = 1;
/*     */         }
/* 440 */       } else if ((i11 == 43) && 
/* 441 */         (i5 != 0)) {
/* 442 */         i9 += a(bool2, bool1, bool3, i6, i7, i8, paramInt3);
/* 443 */         i5 = bool1 = i3 = bool3 = bool2 = 0;
/* 444 */         i7 = i8 = 0;
/* 445 */         i6 = -1;
/*     */       }
/*     */     }
/*     */     
/* 449 */     if (i5 != 0) {
/* 450 */       i9 += a(bool2, bool1, bool3, i6, i7, i8, paramInt3);
/*     */     }
/*     */     
/* 453 */     return i9;
/*     */   }
/*     */   
/*     */   public static List<MobEffect> getEffects(int paramInt, boolean paramBoolean) {
/* 457 */     ArrayList localArrayList = null;
/*     */     
/* 459 */     for (MobEffectList localMobEffectList : MobEffectList.byId)
/* 460 */       if ((localMobEffectList != null) && ((!localMobEffectList.j()) || (paramBoolean)))
/*     */       {
/*     */ 
/* 463 */         String str1 = (String)effectDurations.get(Integer.valueOf(localMobEffectList.getId()));
/* 464 */         if (str1 != null)
/*     */         {
/*     */ 
/*     */ 
/* 468 */           int i3 = a(str1, 0, str1.length(), paramInt);
/* 469 */           if (i3 > 0) {
/* 470 */             int i4 = 0;
/* 471 */             String str2 = (String)effectAmplifiers.get(Integer.valueOf(localMobEffectList.getId()));
/* 472 */             if (str2 != null) {
/* 473 */               i4 = a(str2, 0, str2.length(), paramInt);
/* 474 */               if (i4 < 0) {
/* 475 */                 i4 = 0;
/*     */               }
/*     */             }
/*     */             
/* 479 */             if (localMobEffectList.isInstant()) {
/* 480 */               i3 = 1;
/*     */             }
/*     */             else {
/* 483 */               i3 = 1200 * (i3 * 3 + (i3 - 1) * 2);
/* 484 */               i3 >>= i4;
/* 485 */               i3 = (int)Math.round(i3 * localMobEffectList.getDurationModifier());
/*     */               
/* 487 */               if ((paramInt & 0x4000) != 0) {
/* 488 */                 i3 = (int)Math.round(i3 * 0.75D + 0.5D);
/*     */               }
/*     */             }
/*     */             
/* 492 */             if (localArrayList == null) {
/* 493 */               localArrayList = Lists.newArrayList();
/*     */             }
/* 495 */             MobEffect localMobEffect = new MobEffect(localMobEffectList.getId(), i3, i4);
/* 496 */             if ((paramInt & 0x4000) != 0) {
/* 497 */               localMobEffect.setSplash(true);
/*     */             }
/* 499 */             localArrayList.add(localMobEffect);
/*     */           }
/*     */         }
/*     */       }
/* 503 */     return localArrayList;
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
/*     */   private static int a(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
/*     */   {
/* 586 */     if (paramBoolean3) {
/* 587 */       if (!a(paramInt1, paramInt2)) {
/* 588 */         return 0;
/*     */       }
/* 590 */     } else if (paramBoolean1) {
/* 591 */       paramInt1 &= (1 << paramInt2 ^ 0xFFFFFFFF);
/* 592 */     } else if (paramBoolean2) {
/* 593 */       if ((paramInt1 & 1 << paramInt2) == 0) {
/* 594 */         paramInt1 |= 1 << paramInt2;
/*     */       } else {
/* 596 */         paramInt1 &= (1 << paramInt2 ^ 0xFFFFFFFF);
/*     */       }
/*     */     } else {
/* 599 */       paramInt1 |= 1 << paramInt2;
/*     */     }
/* 601 */     return paramInt1;
/*     */   }
/*     */   
/*     */   public static int a(int paramInt, String paramString) {
/* 605 */     int i1 = 0;
/* 606 */     int i2 = paramString.length();
/*     */     
/* 608 */     int i3 = 0;
/* 609 */     boolean bool1 = false;
/* 610 */     boolean bool2 = false;
/* 611 */     boolean bool3 = false;
/* 612 */     int i4 = 0;
/* 613 */     for (int i5 = i1; i5 < i2; i5++) {
/* 614 */       int i6 = paramString.charAt(i5);
/* 615 */       if ((i6 >= 48) && (i6 <= 57)) {
/* 616 */         i4 *= 10;
/* 617 */         i4 += i6 - 48;
/* 618 */         i3 = 1;
/* 619 */       } else if (i6 == 33) {
/* 620 */         if (i3 != 0) {
/* 621 */           paramInt = a(paramInt, i4, bool2, bool1, bool3);
/* 622 */           i3 = bool2 = bool1 = bool3 = 0;
/* 623 */           i4 = 0;
/*     */         }
/*     */         
/* 626 */         bool1 = true;
/* 627 */       } else if (i6 == 45) {
/* 628 */         if (i3 != 0) {
/* 629 */           paramInt = a(paramInt, i4, bool2, bool1, bool3);
/* 630 */           i3 = bool2 = bool1 = bool3 = 0;
/* 631 */           i4 = 0;
/*     */         }
/*     */         
/* 634 */         bool2 = true;
/* 635 */       } else if (i6 == 43) {
/* 636 */         if (i3 != 0) {
/* 637 */           paramInt = a(paramInt, i4, bool2, bool1, bool3);
/* 638 */           i3 = bool2 = bool1 = bool3 = 0;
/* 639 */           i4 = 0;
/*     */         }
/* 641 */       } else if (i6 == 38) {
/* 642 */         if (i3 != 0) {
/* 643 */           paramInt = a(paramInt, i4, bool2, bool1, bool3);
/* 644 */           i3 = bool2 = bool1 = bool3 = 0;
/* 645 */           i4 = 0;
/*     */         }
/* 647 */         bool3 = true;
/*     */       }
/*     */     }
/* 650 */     if (i3 != 0) {
/* 651 */       paramInt = a(paramInt, i4, bool2, bool1, bool3);
/*     */     }
/*     */     
/* 654 */     return paramInt & 0x7FFF;
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
/*     */   public static int a(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */   {
/* 669 */     return (a(paramInt1, paramInt2) ? 16 : 0) | (a(paramInt1, paramInt3) ? 8 : 0) | (a(paramInt1, paramInt4) ? 4 : 0) | (a(paramInt1, paramInt5) ? 2 : 0) | (a(paramInt1, paramInt6) ? 1 : 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PotionBrewer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */