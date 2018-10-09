/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonSerializationContext;
/*     */ import com.google.gson.JsonSerializer;
/*     */ import java.lang.reflect.Type;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CustomWorldSettingsFinal
/*     */ {
/*     */   public final float a;
/*     */   public final float b;
/*     */   public final float c;
/*     */   public final float d;
/*     */   public final float e;
/*     */   public final float f;
/*     */   public final float g;
/*     */   public final float h;
/*     */   public final float i;
/*     */   public final float j;
/*     */   public final float k;
/*     */   public final float l;
/*     */   public final float m;
/*     */   public final float n;
/*     */   public final float o;
/*     */   public final float p;
/*     */   public final int q;
/*     */   public final boolean r;
/*     */   public final boolean s;
/*     */   public final int t;
/*     */   public final boolean u;
/*     */   public final boolean v;
/*     */   public final boolean w;
/*     */   public final boolean x;
/*     */   public final boolean y;
/*     */   public final boolean z;
/*     */   public final boolean A;
/*     */   public final int B;
/*     */   public final boolean C;
/*     */   public final int D;
/*     */   public final boolean E;
/*     */   public final int F;
/*     */   public final int G;
/*     */   public final int H;
/*     */   public final int I;
/*     */   public final int J;
/*     */   public final int K;
/*     */   public final int L;
/*     */   public final int M;
/*     */   public final int N;
/*     */   public final int O;
/*     */   public final int P;
/*     */   public final int Q;
/*     */   public final int R;
/*     */   public final int S;
/*     */   public final int T;
/*     */   public final int U;
/*     */   public final int V;
/*     */   public final int W;
/*     */   public final int X;
/*     */   public final int Y;
/*     */   public final int Z;
/*     */   public final int aa;
/*     */   public final int ab;
/*     */   public final int ac;
/*     */   public final int ad;
/*     */   public final int ae;
/*     */   public final int af;
/*     */   public final int ag;
/*     */   public final int ah;
/*     */   public final int ai;
/*     */   public final int aj;
/*     */   public final int ak;
/*     */   public final int al;
/*     */   public final int am;
/*     */   public final int an;
/*     */   public final int ao;
/*     */   public final int ap;
/*     */   public final int aq;
/*     */   public final int ar;
/*     */   public final int as;
/*     */   public final int at;
/*     */   public final int au;
/*     */   public final int av;
/*     */   public final int aw;
/*     */   public final int ax;
/*     */   public final int ay;
/*     */   public final int az;
/*     */   
/*     */   private CustomWorldSettingsFinal(CustomWorldSettings paramCustomWorldSettings)
/*     */   {
/* 132 */     this.a = paramCustomWorldSettings.b;
/* 133 */     this.b = paramCustomWorldSettings.c;
/* 134 */     this.c = paramCustomWorldSettings.d;
/* 135 */     this.d = paramCustomWorldSettings.e;
/* 136 */     this.e = paramCustomWorldSettings.f;
/* 137 */     this.f = paramCustomWorldSettings.g;
/* 138 */     this.g = paramCustomWorldSettings.h;
/* 139 */     this.h = paramCustomWorldSettings.i;
/* 140 */     this.i = paramCustomWorldSettings.j;
/* 141 */     this.j = paramCustomWorldSettings.k;
/* 142 */     this.k = paramCustomWorldSettings.l;
/* 143 */     this.l = paramCustomWorldSettings.m;
/* 144 */     this.m = paramCustomWorldSettings.n;
/* 145 */     this.n = paramCustomWorldSettings.o;
/* 146 */     this.o = paramCustomWorldSettings.p;
/* 147 */     this.p = paramCustomWorldSettings.q;
/* 148 */     this.q = paramCustomWorldSettings.r;
/*     */     
/* 150 */     this.r = paramCustomWorldSettings.s;
/* 151 */     this.s = paramCustomWorldSettings.t;
/* 152 */     this.t = paramCustomWorldSettings.u;
/* 153 */     this.u = paramCustomWorldSettings.v;
/* 154 */     this.v = paramCustomWorldSettings.w;
/* 155 */     this.w = paramCustomWorldSettings.x;
/* 156 */     this.x = paramCustomWorldSettings.y;
/* 157 */     this.y = paramCustomWorldSettings.z;
/* 158 */     this.z = paramCustomWorldSettings.A;
/* 159 */     this.A = paramCustomWorldSettings.B;
/* 160 */     this.B = paramCustomWorldSettings.C;
/* 161 */     this.C = paramCustomWorldSettings.D;
/* 162 */     this.D = paramCustomWorldSettings.E;
/* 163 */     this.E = paramCustomWorldSettings.F;
/*     */     
/* 165 */     this.F = paramCustomWorldSettings.G;
/* 166 */     this.G = paramCustomWorldSettings.H;
/* 167 */     this.H = paramCustomWorldSettings.I;
/*     */     
/* 169 */     this.I = paramCustomWorldSettings.J;
/* 170 */     this.J = paramCustomWorldSettings.K;
/* 171 */     this.K = paramCustomWorldSettings.L;
/* 172 */     this.L = paramCustomWorldSettings.M;
/* 173 */     this.M = paramCustomWorldSettings.N;
/* 174 */     this.N = paramCustomWorldSettings.O;
/* 175 */     this.O = paramCustomWorldSettings.P;
/* 176 */     this.P = paramCustomWorldSettings.Q;
/* 177 */     this.Q = paramCustomWorldSettings.R;
/* 178 */     this.R = paramCustomWorldSettings.S;
/* 179 */     this.S = paramCustomWorldSettings.T;
/* 180 */     this.T = paramCustomWorldSettings.U;
/* 181 */     this.U = paramCustomWorldSettings.V;
/* 182 */     this.V = paramCustomWorldSettings.W;
/* 183 */     this.W = paramCustomWorldSettings.X;
/* 184 */     this.X = paramCustomWorldSettings.Y;
/* 185 */     this.Y = paramCustomWorldSettings.Z;
/* 186 */     this.Z = paramCustomWorldSettings.aa;
/* 187 */     this.aa = paramCustomWorldSettings.ab;
/* 188 */     this.ab = paramCustomWorldSettings.ac;
/* 189 */     this.ac = paramCustomWorldSettings.ad;
/* 190 */     this.ad = paramCustomWorldSettings.ae;
/* 191 */     this.ae = paramCustomWorldSettings.af;
/* 192 */     this.af = paramCustomWorldSettings.ag;
/* 193 */     this.ag = paramCustomWorldSettings.ah;
/* 194 */     this.ah = paramCustomWorldSettings.ai;
/* 195 */     this.ai = paramCustomWorldSettings.aj;
/* 196 */     this.aj = paramCustomWorldSettings.ak;
/* 197 */     this.ak = paramCustomWorldSettings.al;
/* 198 */     this.al = paramCustomWorldSettings.am;
/* 199 */     this.am = paramCustomWorldSettings.an;
/* 200 */     this.an = paramCustomWorldSettings.ao;
/* 201 */     this.ao = paramCustomWorldSettings.ap;
/* 202 */     this.ap = paramCustomWorldSettings.aq;
/* 203 */     this.aq = paramCustomWorldSettings.ar;
/* 204 */     this.ar = paramCustomWorldSettings.as;
/* 205 */     this.as = paramCustomWorldSettings.at;
/* 206 */     this.at = paramCustomWorldSettings.au;
/* 207 */     this.au = paramCustomWorldSettings.av;
/* 208 */     this.av = paramCustomWorldSettings.aw;
/* 209 */     this.aw = paramCustomWorldSettings.ax;
/* 210 */     this.ax = paramCustomWorldSettings.ay;
/* 211 */     this.ay = paramCustomWorldSettings.az;
/* 212 */     this.az = paramCustomWorldSettings.aA;
/*     */   }
/*     */   
/*     */   public static class CustomWorldSettings
/*     */   {
/* 217 */     static final Gson a = new GsonBuilder().registerTypeAdapter(CustomWorldSettings.class, new CustomWorldSettingsFinal.CustomWorldSettingsSerializer()).create();
/*     */     
/*     */     public static CustomWorldSettings a(String paramString) {
/* 220 */       if (paramString.length() == 0) {
/* 221 */         return new CustomWorldSettings();
/*     */       }
/*     */       try {
/* 224 */         return (CustomWorldSettings)a.fromJson(paramString, CustomWorldSettings.class);
/*     */       }
/*     */       catch (Exception localException) {}
/* 227 */       return new CustomWorldSettings();
/*     */     }
/*     */     
/*     */     public String toString()
/*     */     {
/* 232 */       return a.toJson(this);
/*     */     }
/*     */     
/*     */ 
/* 236 */     public float b = 684.412F;
/* 237 */     public float c = 684.412F;
/* 238 */     public float d = 512.0F;
/* 239 */     public float e = 512.0F;
/* 240 */     public float f = 200.0F;
/* 241 */     public float g = 200.0F;
/* 242 */     public float h = 0.5F;
/* 243 */     public float i = 80.0F;
/* 244 */     public float j = 160.0F;
/* 245 */     public float k = 80.0F;
/* 246 */     public float l = 8.5F;
/* 247 */     public float m = 12.0F;
/* 248 */     public float n = 1.0F;
/* 249 */     public float o = 0.0F;
/* 250 */     public float p = 1.0F;
/* 251 */     public float q = 0.0F;
/* 252 */     public int r = 63;
/*     */     
/*     */ 
/* 255 */     public boolean s = true;
/* 256 */     public boolean t = true;
/* 257 */     public int u = 8;
/* 258 */     public boolean v = true;
/* 259 */     public boolean w = true;
/* 260 */     public boolean x = true;
/* 261 */     public boolean y = true;
/* 262 */     public boolean z = true;
/* 263 */     public boolean A = true;
/* 264 */     public boolean B = true;
/* 265 */     public int C = 4;
/* 266 */     public boolean D = true;
/* 267 */     public int E = 80;
/* 268 */     public boolean F = false;
/*     */     
/*     */ 
/* 271 */     public int G = -1;
/* 272 */     public int H = 4;
/* 273 */     public int I = 4;
/*     */     
/*     */ 
/* 276 */     public int J = 33;
/* 277 */     public int K = 10;
/* 278 */     public int L = 0;
/* 279 */     public int M = 256;
/* 280 */     public int N = 33;
/* 281 */     public int O = 8;
/* 282 */     public int P = 0;
/* 283 */     public int Q = 256;
/* 284 */     public int R = 33;
/* 285 */     public int S = 10;
/* 286 */     public int T = 0;
/* 287 */     public int U = 80;
/* 288 */     public int V = 33;
/* 289 */     public int W = 10;
/* 290 */     public int X = 0;
/* 291 */     public int Y = 80;
/* 292 */     public int Z = 33;
/* 293 */     public int aa = 10;
/* 294 */     public int ab = 0;
/* 295 */     public int ac = 80;
/* 296 */     public int ad = 17;
/* 297 */     public int ae = 20;
/* 298 */     public int af = 0;
/* 299 */     public int ag = 128;
/* 300 */     public int ah = 9;
/* 301 */     public int ai = 20;
/* 302 */     public int aj = 0;
/* 303 */     public int ak = 64;
/* 304 */     public int al = 9;
/* 305 */     public int am = 2;
/* 306 */     public int an = 0;
/* 307 */     public int ao = 32;
/* 308 */     public int ap = 8;
/* 309 */     public int aq = 8;
/* 310 */     public int ar = 0;
/* 311 */     public int as = 16;
/* 312 */     public int at = 8;
/* 313 */     public int au = 1;
/* 314 */     public int av = 0;
/* 315 */     public int aw = 16;
/* 316 */     public int ax = 7;
/* 317 */     public int ay = 1;
/* 318 */     public int az = 16;
/* 319 */     public int aA = 16;
/*     */     
/*     */     public CustomWorldSettings() {
/* 322 */       a();
/*     */     }
/*     */     
/*     */     public void a() {
/* 326 */       this.b = 684.412F;
/* 327 */       this.c = 684.412F;
/* 328 */       this.d = 512.0F;
/* 329 */       this.e = 512.0F;
/* 330 */       this.f = 200.0F;
/* 331 */       this.g = 200.0F;
/* 332 */       this.h = 0.5F;
/* 333 */       this.i = 80.0F;
/* 334 */       this.j = 160.0F;
/* 335 */       this.k = 80.0F;
/* 336 */       this.l = 8.5F;
/* 337 */       this.m = 12.0F;
/* 338 */       this.n = 1.0F;
/* 339 */       this.o = 0.0F;
/* 340 */       this.p = 1.0F;
/* 341 */       this.q = 0.0F;
/* 342 */       this.r = 63;
/*     */       
/* 344 */       this.s = true;
/* 345 */       this.t = true;
/* 346 */       this.u = 8;
/* 347 */       this.v = true;
/* 348 */       this.w = true;
/* 349 */       this.x = true;
/* 350 */       this.y = true;
/* 351 */       this.z = true;
/* 352 */       this.A = true;
/* 353 */       this.B = true;
/* 354 */       this.C = 4;
/* 355 */       this.D = true;
/* 356 */       this.E = 80;
/* 357 */       this.F = false;
/*     */       
/* 359 */       this.G = -1;
/* 360 */       this.H = 4;
/* 361 */       this.I = 4;
/*     */       
/* 363 */       this.J = 33;
/* 364 */       this.K = 10;
/* 365 */       this.L = 0;
/* 366 */       this.M = 256;
/* 367 */       this.N = 33;
/* 368 */       this.O = 8;
/* 369 */       this.P = 0;
/* 370 */       this.Q = 256;
/* 371 */       this.R = 33;
/* 372 */       this.S = 10;
/* 373 */       this.T = 0;
/* 374 */       this.U = 80;
/* 375 */       this.V = 33;
/* 376 */       this.W = 10;
/* 377 */       this.X = 0;
/* 378 */       this.Y = 80;
/* 379 */       this.Z = 33;
/* 380 */       this.aa = 10;
/* 381 */       this.ab = 0;
/* 382 */       this.ac = 80;
/* 383 */       this.ad = 17;
/* 384 */       this.ae = 20;
/* 385 */       this.af = 0;
/* 386 */       this.ag = 128;
/* 387 */       this.ah = 9;
/* 388 */       this.ai = 20;
/* 389 */       this.aj = 0;
/* 390 */       this.ak = 64;
/* 391 */       this.al = 9;
/* 392 */       this.am = 2;
/* 393 */       this.an = 0;
/* 394 */       this.ao = 32;
/* 395 */       this.ap = 8;
/* 396 */       this.aq = 8;
/* 397 */       this.ar = 0;
/* 398 */       this.as = 16;
/* 399 */       this.at = 8;
/* 400 */       this.au = 1;
/* 401 */       this.av = 0;
/* 402 */       this.aw = 16;
/* 403 */       this.ax = 7;
/* 404 */       this.ay = 1;
/* 405 */       this.az = 16;
/* 406 */       this.aA = 16;
/*     */     }
/*     */     
/*     */     public boolean equals(Object paramObject)
/*     */     {
/* 411 */       if (this == paramObject) {
/* 412 */         return true;
/*     */       }
/* 414 */       if ((paramObject == null) || (getClass() != paramObject.getClass())) {
/* 415 */         return false;
/*     */       }
/*     */       
/* 418 */       CustomWorldSettings localCustomWorldSettings = (CustomWorldSettings)paramObject;
/*     */       
/* 420 */       if (this.aa != localCustomWorldSettings.aa) {
/* 421 */         return false;
/*     */       }
/* 423 */       if (this.ac != localCustomWorldSettings.ac) {
/* 424 */         return false;
/*     */       }
/* 426 */       if (this.ab != localCustomWorldSettings.ab) {
/* 427 */         return false;
/*     */       }
/* 429 */       if (this.Z != localCustomWorldSettings.Z) {
/* 430 */         return false;
/*     */       }
/* 432 */       if (Float.compare(localCustomWorldSettings.l, this.l) != 0) {
/* 433 */         return false;
/*     */       }
/* 435 */       if (Float.compare(localCustomWorldSettings.o, this.o) != 0) {
/* 436 */         return false;
/*     */       }
/* 438 */       if (Float.compare(localCustomWorldSettings.n, this.n) != 0) {
/* 439 */         return false;
/*     */       }
/* 441 */       if (Float.compare(localCustomWorldSettings.q, this.q) != 0) {
/* 442 */         return false;
/*     */       }
/* 444 */       if (Float.compare(localCustomWorldSettings.p, this.p) != 0) {
/* 445 */         return false;
/*     */       }
/* 447 */       if (this.H != localCustomWorldSettings.H) {
/* 448 */         return false;
/*     */       }
/* 450 */       if (this.ae != localCustomWorldSettings.ae) {
/* 451 */         return false;
/*     */       }
/* 453 */       if (this.ag != localCustomWorldSettings.ag) {
/* 454 */         return false;
/*     */       }
/* 456 */       if (this.af != localCustomWorldSettings.af) {
/* 457 */         return false;
/*     */       }
/* 459 */       if (this.ad != localCustomWorldSettings.ad) {
/* 460 */         return false;
/*     */       }
/* 462 */       if (Float.compare(localCustomWorldSettings.b, this.b) != 0) {
/* 463 */         return false;
/*     */       }
/* 465 */       if (Float.compare(localCustomWorldSettings.h, this.h) != 0) {
/* 466 */         return false;
/*     */       }
/* 468 */       if (Float.compare(localCustomWorldSettings.f, this.f) != 0) {
/* 469 */         return false;
/*     */       }
/* 471 */       if (Float.compare(localCustomWorldSettings.g, this.g) != 0) {
/* 472 */         return false;
/*     */       }
/* 474 */       if (this.au != localCustomWorldSettings.au) {
/* 475 */         return false;
/*     */       }
/* 477 */       if (this.aw != localCustomWorldSettings.aw) {
/* 478 */         return false;
/*     */       }
/* 480 */       if (this.av != localCustomWorldSettings.av) {
/* 481 */         return false;
/*     */       }
/* 483 */       if (this.at != localCustomWorldSettings.at) {
/* 484 */         return false;
/*     */       }
/* 486 */       if (this.W != localCustomWorldSettings.W) {
/* 487 */         return false;
/*     */       }
/* 489 */       if (this.Y != localCustomWorldSettings.Y) {
/* 490 */         return false;
/*     */       }
/* 492 */       if (this.X != localCustomWorldSettings.X) {
/* 493 */         return false;
/*     */       }
/* 495 */       if (this.V != localCustomWorldSettings.V) {
/* 496 */         return false;
/*     */       }
/* 498 */       if (this.K != localCustomWorldSettings.K) {
/* 499 */         return false;
/*     */       }
/* 501 */       if (this.M != localCustomWorldSettings.M) {
/* 502 */         return false;
/*     */       }
/* 504 */       if (this.L != localCustomWorldSettings.L) {
/* 505 */         return false;
/*     */       }
/* 507 */       if (this.J != localCustomWorldSettings.J) {
/* 508 */         return false;
/*     */       }
/* 510 */       if (this.u != localCustomWorldSettings.u) {
/* 511 */         return false;
/*     */       }
/* 513 */       if (this.G != localCustomWorldSettings.G) {
/* 514 */         return false;
/*     */       }
/* 516 */       if (this.am != localCustomWorldSettings.am) {
/* 517 */         return false;
/*     */       }
/* 519 */       if (this.ao != localCustomWorldSettings.ao) {
/* 520 */         return false;
/*     */       }
/* 522 */       if (this.an != localCustomWorldSettings.an) {
/* 523 */         return false;
/*     */       }
/* 525 */       if (this.al != localCustomWorldSettings.al) {
/* 526 */         return false;
/*     */       }
/* 528 */       if (this.S != localCustomWorldSettings.S) {
/* 529 */         return false;
/*     */       }
/* 531 */       if (this.U != localCustomWorldSettings.U) {
/* 532 */         return false;
/*     */       }
/* 534 */       if (this.T != localCustomWorldSettings.T) {
/* 535 */         return false;
/*     */       }
/* 537 */       if (this.R != localCustomWorldSettings.R) {
/* 538 */         return false;
/*     */       }
/* 540 */       if (this.O != localCustomWorldSettings.O) {
/* 541 */         return false;
/*     */       }
/* 543 */       if (this.Q != localCustomWorldSettings.Q) {
/* 544 */         return false;
/*     */       }
/* 546 */       if (this.P != localCustomWorldSettings.P) {
/* 547 */         return false;
/*     */       }
/* 549 */       if (this.N != localCustomWorldSettings.N) {
/* 550 */         return false;
/*     */       }
/* 552 */       if (Float.compare(localCustomWorldSettings.c, this.c) != 0) {
/* 553 */         return false;
/*     */       }
/* 555 */       if (this.ai != localCustomWorldSettings.ai) {
/* 556 */         return false;
/*     */       }
/* 558 */       if (this.ak != localCustomWorldSettings.ak) {
/* 559 */         return false;
/*     */       }
/* 561 */       if (this.aj != localCustomWorldSettings.aj) {
/* 562 */         return false;
/*     */       }
/* 564 */       if (this.ah != localCustomWorldSettings.ah) {
/* 565 */         return false;
/*     */       }
/* 567 */       if (this.az != localCustomWorldSettings.az) {
/* 568 */         return false;
/*     */       }
/* 570 */       if (this.ay != localCustomWorldSettings.ay) {
/* 571 */         return false;
/*     */       }
/* 573 */       if (this.ax != localCustomWorldSettings.ax) {
/* 574 */         return false;
/*     */       }
/* 576 */       if (this.aA != localCustomWorldSettings.aA) {
/* 577 */         return false;
/*     */       }
/* 579 */       if (this.E != localCustomWorldSettings.E) {
/* 580 */         return false;
/*     */       }
/* 582 */       if (Float.compare(localCustomWorldSettings.e, this.e) != 0) {
/* 583 */         return false;
/*     */       }
/* 585 */       if (Float.compare(localCustomWorldSettings.i, this.i) != 0) {
/* 586 */         return false;
/*     */       }
/* 588 */       if (Float.compare(localCustomWorldSettings.j, this.j) != 0) {
/* 589 */         return false;
/*     */       }
/* 591 */       if (Float.compare(localCustomWorldSettings.k, this.k) != 0) {
/* 592 */         return false;
/*     */       }
/* 594 */       if (this.aq != localCustomWorldSettings.aq) {
/* 595 */         return false;
/*     */       }
/* 597 */       if (this.as != localCustomWorldSettings.as) {
/* 598 */         return false;
/*     */       }
/* 600 */       if (this.ar != localCustomWorldSettings.ar) {
/* 601 */         return false;
/*     */       }
/* 603 */       if (this.ap != localCustomWorldSettings.ap) {
/* 604 */         return false;
/*     */       }
/* 606 */       if (this.I != localCustomWorldSettings.I) {
/* 607 */         return false;
/*     */       }
/* 609 */       if (this.r != localCustomWorldSettings.r) {
/* 610 */         return false;
/*     */       }
/* 612 */       if (Float.compare(localCustomWorldSettings.m, this.m) != 0) {
/* 613 */         return false;
/*     */       }
/* 615 */       if (Float.compare(localCustomWorldSettings.d, this.d) != 0) {
/* 616 */         return false;
/*     */       }
/* 618 */       if (this.s != localCustomWorldSettings.s) {
/* 619 */         return false;
/*     */       }
/* 621 */       if (this.t != localCustomWorldSettings.t) {
/* 622 */         return false;
/*     */       }
/* 624 */       if (this.D != localCustomWorldSettings.D) {
/* 625 */         return false;
/*     */       }
/* 627 */       if (this.F != localCustomWorldSettings.F) {
/* 628 */         return false;
/*     */       }
/* 630 */       if (this.x != localCustomWorldSettings.x) {
/* 631 */         return false;
/*     */       }
/* 633 */       if (this.A != localCustomWorldSettings.A) {
/* 634 */         return false;
/*     */       }
/* 636 */       if (this.v != localCustomWorldSettings.v) {
/* 637 */         return false;
/*     */       }
/* 639 */       if (this.y != localCustomWorldSettings.y) {
/* 640 */         return false;
/*     */       }
/* 642 */       if (this.z != localCustomWorldSettings.z) {
/* 643 */         return false;
/*     */       }
/* 645 */       if (this.w != localCustomWorldSettings.w) {
/* 646 */         return false;
/*     */       }
/* 648 */       if (this.B != localCustomWorldSettings.B) {
/* 649 */         return false;
/*     */       }
/* 651 */       if (this.C != localCustomWorldSettings.C) {
/* 652 */         return false;
/*     */       }
/*     */       
/* 655 */       return true;
/*     */     }
/*     */     
/*     */     public int hashCode()
/*     */     {
/* 660 */       int i1 = this.b != 0.0F ? Float.floatToIntBits(this.b) : 0;
/* 661 */       i1 = 31 * i1 + (this.c != 0.0F ? Float.floatToIntBits(this.c) : 0);
/* 662 */       i1 = 31 * i1 + (this.d != 0.0F ? Float.floatToIntBits(this.d) : 0);
/* 663 */       i1 = 31 * i1 + (this.e != 0.0F ? Float.floatToIntBits(this.e) : 0);
/* 664 */       i1 = 31 * i1 + (this.f != 0.0F ? Float.floatToIntBits(this.f) : 0);
/* 665 */       i1 = 31 * i1 + (this.g != 0.0F ? Float.floatToIntBits(this.g) : 0);
/* 666 */       i1 = 31 * i1 + (this.h != 0.0F ? Float.floatToIntBits(this.h) : 0);
/* 667 */       i1 = 31 * i1 + (this.i != 0.0F ? Float.floatToIntBits(this.i) : 0);
/* 668 */       i1 = 31 * i1 + (this.j != 0.0F ? Float.floatToIntBits(this.j) : 0);
/* 669 */       i1 = 31 * i1 + (this.k != 0.0F ? Float.floatToIntBits(this.k) : 0);
/* 670 */       i1 = 31 * i1 + (this.l != 0.0F ? Float.floatToIntBits(this.l) : 0);
/* 671 */       i1 = 31 * i1 + (this.m != 0.0F ? Float.floatToIntBits(this.m) : 0);
/* 672 */       i1 = 31 * i1 + (this.n != 0.0F ? Float.floatToIntBits(this.n) : 0);
/* 673 */       i1 = 31 * i1 + (this.o != 0.0F ? Float.floatToIntBits(this.o) : 0);
/* 674 */       i1 = 31 * i1 + (this.p != 0.0F ? Float.floatToIntBits(this.p) : 0);
/* 675 */       i1 = 31 * i1 + (this.q != 0.0F ? Float.floatToIntBits(this.q) : 0);
/* 676 */       i1 = 31 * i1 + this.r;
/* 677 */       i1 = 31 * i1 + (this.s ? 1 : 0);
/* 678 */       i1 = 31 * i1 + (this.t ? 1 : 0);
/* 679 */       i1 = 31 * i1 + this.u;
/* 680 */       i1 = 31 * i1 + (this.v ? 1 : 0);
/* 681 */       i1 = 31 * i1 + (this.w ? 1 : 0);
/* 682 */       i1 = 31 * i1 + (this.x ? 1 : 0);
/* 683 */       i1 = 31 * i1 + (this.y ? 1 : 0);
/* 684 */       i1 = 31 * i1 + (this.z ? 1 : 0);
/* 685 */       i1 = 31 * i1 + (this.A ? 1 : 0);
/* 686 */       i1 = 31 * i1 + (this.B ? 1 : 0);
/* 687 */       i1 = 31 * i1 + this.C;
/* 688 */       i1 = 31 * i1 + (this.D ? 1 : 0);
/* 689 */       i1 = 31 * i1 + this.E;
/* 690 */       i1 = 31 * i1 + (this.F ? 1 : 0);
/* 691 */       i1 = 31 * i1 + this.G;
/* 692 */       i1 = 31 * i1 + this.H;
/* 693 */       i1 = 31 * i1 + this.I;
/* 694 */       i1 = 31 * i1 + this.J;
/* 695 */       i1 = 31 * i1 + this.K;
/* 696 */       i1 = 31 * i1 + this.L;
/* 697 */       i1 = 31 * i1 + this.M;
/* 698 */       i1 = 31 * i1 + this.N;
/* 699 */       i1 = 31 * i1 + this.O;
/* 700 */       i1 = 31 * i1 + this.P;
/* 701 */       i1 = 31 * i1 + this.Q;
/* 702 */       i1 = 31 * i1 + this.R;
/* 703 */       i1 = 31 * i1 + this.S;
/* 704 */       i1 = 31 * i1 + this.T;
/* 705 */       i1 = 31 * i1 + this.U;
/* 706 */       i1 = 31 * i1 + this.V;
/* 707 */       i1 = 31 * i1 + this.W;
/* 708 */       i1 = 31 * i1 + this.X;
/* 709 */       i1 = 31 * i1 + this.Y;
/* 710 */       i1 = 31 * i1 + this.Z;
/* 711 */       i1 = 31 * i1 + this.aa;
/* 712 */       i1 = 31 * i1 + this.ab;
/* 713 */       i1 = 31 * i1 + this.ac;
/* 714 */       i1 = 31 * i1 + this.ad;
/* 715 */       i1 = 31 * i1 + this.ae;
/* 716 */       i1 = 31 * i1 + this.af;
/* 717 */       i1 = 31 * i1 + this.ag;
/* 718 */       i1 = 31 * i1 + this.ah;
/* 719 */       i1 = 31 * i1 + this.ai;
/* 720 */       i1 = 31 * i1 + this.aj;
/* 721 */       i1 = 31 * i1 + this.ak;
/* 722 */       i1 = 31 * i1 + this.al;
/* 723 */       i1 = 31 * i1 + this.am;
/* 724 */       i1 = 31 * i1 + this.an;
/* 725 */       i1 = 31 * i1 + this.ao;
/* 726 */       i1 = 31 * i1 + this.ap;
/* 727 */       i1 = 31 * i1 + this.aq;
/* 728 */       i1 = 31 * i1 + this.ar;
/* 729 */       i1 = 31 * i1 + this.as;
/* 730 */       i1 = 31 * i1 + this.at;
/* 731 */       i1 = 31 * i1 + this.au;
/* 732 */       i1 = 31 * i1 + this.av;
/* 733 */       i1 = 31 * i1 + this.aw;
/* 734 */       i1 = 31 * i1 + this.ax;
/* 735 */       i1 = 31 * i1 + this.ay;
/* 736 */       i1 = 31 * i1 + this.az;
/* 737 */       i1 = 31 * i1 + this.aA;
/* 738 */       return i1;
/*     */     }
/*     */     
/*     */     public CustomWorldSettingsFinal b() {
/* 742 */       return new CustomWorldSettingsFinal(this, null);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class CustomWorldSettingsSerializer implements JsonDeserializer<CustomWorldSettingsFinal.CustomWorldSettings>, JsonSerializer<CustomWorldSettingsFinal.CustomWorldSettings>
/*     */   {
/*     */     public CustomWorldSettingsFinal.CustomWorldSettings a(JsonElement paramJsonElement, Type paramType, JsonDeserializationContext paramJsonDeserializationContext) throws JsonParseException {
/* 749 */       JsonObject localJsonObject = paramJsonElement.getAsJsonObject();
/*     */       
/* 751 */       CustomWorldSettingsFinal.CustomWorldSettings localCustomWorldSettings = new CustomWorldSettingsFinal.CustomWorldSettings();
/*     */       try
/*     */       {
/* 754 */         localCustomWorldSettings.b = ChatDeserializer.a(localJsonObject, "coordinateScale", localCustomWorldSettings.b);
/* 755 */         localCustomWorldSettings.c = ChatDeserializer.a(localJsonObject, "heightScale", localCustomWorldSettings.c);
/* 756 */         localCustomWorldSettings.e = ChatDeserializer.a(localJsonObject, "lowerLimitScale", localCustomWorldSettings.e);
/* 757 */         localCustomWorldSettings.d = ChatDeserializer.a(localJsonObject, "upperLimitScale", localCustomWorldSettings.d);
/* 758 */         localCustomWorldSettings.f = ChatDeserializer.a(localJsonObject, "depthNoiseScaleX", localCustomWorldSettings.f);
/* 759 */         localCustomWorldSettings.g = ChatDeserializer.a(localJsonObject, "depthNoiseScaleZ", localCustomWorldSettings.g);
/* 760 */         localCustomWorldSettings.h = ChatDeserializer.a(localJsonObject, "depthNoiseScaleExponent", localCustomWorldSettings.h);
/* 761 */         localCustomWorldSettings.i = ChatDeserializer.a(localJsonObject, "mainNoiseScaleX", localCustomWorldSettings.i);
/* 762 */         localCustomWorldSettings.j = ChatDeserializer.a(localJsonObject, "mainNoiseScaleY", localCustomWorldSettings.j);
/* 763 */         localCustomWorldSettings.k = ChatDeserializer.a(localJsonObject, "mainNoiseScaleZ", localCustomWorldSettings.k);
/* 764 */         localCustomWorldSettings.l = ChatDeserializer.a(localJsonObject, "baseSize", localCustomWorldSettings.l);
/* 765 */         localCustomWorldSettings.m = ChatDeserializer.a(localJsonObject, "stretchY", localCustomWorldSettings.m);
/* 766 */         localCustomWorldSettings.n = ChatDeserializer.a(localJsonObject, "biomeDepthWeight", localCustomWorldSettings.n);
/* 767 */         localCustomWorldSettings.o = ChatDeserializer.a(localJsonObject, "biomeDepthOffset", localCustomWorldSettings.o);
/* 768 */         localCustomWorldSettings.p = ChatDeserializer.a(localJsonObject, "biomeScaleWeight", localCustomWorldSettings.p);
/* 769 */         localCustomWorldSettings.q = ChatDeserializer.a(localJsonObject, "biomeScaleOffset", localCustomWorldSettings.q);
/* 770 */         localCustomWorldSettings.r = ChatDeserializer.a(localJsonObject, "seaLevel", localCustomWorldSettings.r);
/*     */         
/* 772 */         localCustomWorldSettings.s = ChatDeserializer.a(localJsonObject, "useCaves", localCustomWorldSettings.s);
/* 773 */         localCustomWorldSettings.t = ChatDeserializer.a(localJsonObject, "useDungeons", localCustomWorldSettings.t);
/* 774 */         localCustomWorldSettings.u = ChatDeserializer.a(localJsonObject, "dungeonChance", localCustomWorldSettings.u);
/* 775 */         localCustomWorldSettings.v = ChatDeserializer.a(localJsonObject, "useStrongholds", localCustomWorldSettings.v);
/* 776 */         localCustomWorldSettings.w = ChatDeserializer.a(localJsonObject, "useVillages", localCustomWorldSettings.w);
/* 777 */         localCustomWorldSettings.x = ChatDeserializer.a(localJsonObject, "useMineShafts", localCustomWorldSettings.x);
/* 778 */         localCustomWorldSettings.y = ChatDeserializer.a(localJsonObject, "useTemples", localCustomWorldSettings.y);
/* 779 */         localCustomWorldSettings.z = ChatDeserializer.a(localJsonObject, "useMonuments", localCustomWorldSettings.z);
/* 780 */         localCustomWorldSettings.A = ChatDeserializer.a(localJsonObject, "useRavines", localCustomWorldSettings.A);
/* 781 */         localCustomWorldSettings.B = ChatDeserializer.a(localJsonObject, "useWaterLakes", localCustomWorldSettings.B);
/* 782 */         localCustomWorldSettings.C = ChatDeserializer.a(localJsonObject, "waterLakeChance", localCustomWorldSettings.C);
/* 783 */         localCustomWorldSettings.D = ChatDeserializer.a(localJsonObject, "useLavaLakes", localCustomWorldSettings.D);
/* 784 */         localCustomWorldSettings.E = ChatDeserializer.a(localJsonObject, "lavaLakeChance", localCustomWorldSettings.E);
/* 785 */         localCustomWorldSettings.F = ChatDeserializer.a(localJsonObject, "useLavaOceans", localCustomWorldSettings.F);
/*     */         
/* 787 */         localCustomWorldSettings.G = ChatDeserializer.a(localJsonObject, "fixedBiome", localCustomWorldSettings.G);
/* 788 */         if ((localCustomWorldSettings.G >= 38) || (localCustomWorldSettings.G < -1)) {
/* 789 */           localCustomWorldSettings.G = -1;
/* 790 */         } else if (localCustomWorldSettings.G >= BiomeBase.HELL.id) {
/* 791 */           localCustomWorldSettings.G += 2;
/*     */         }
/* 793 */         localCustomWorldSettings.H = ChatDeserializer.a(localJsonObject, "biomeSize", localCustomWorldSettings.H);
/* 794 */         localCustomWorldSettings.I = ChatDeserializer.a(localJsonObject, "riverSize", localCustomWorldSettings.I);
/*     */         
/* 796 */         localCustomWorldSettings.J = ChatDeserializer.a(localJsonObject, "dirtSize", localCustomWorldSettings.J);
/* 797 */         localCustomWorldSettings.K = ChatDeserializer.a(localJsonObject, "dirtCount", localCustomWorldSettings.K);
/* 798 */         localCustomWorldSettings.L = ChatDeserializer.a(localJsonObject, "dirtMinHeight", localCustomWorldSettings.L);
/* 799 */         localCustomWorldSettings.M = ChatDeserializer.a(localJsonObject, "dirtMaxHeight", localCustomWorldSettings.M);
/* 800 */         localCustomWorldSettings.N = ChatDeserializer.a(localJsonObject, "gravelSize", localCustomWorldSettings.N);
/* 801 */         localCustomWorldSettings.O = ChatDeserializer.a(localJsonObject, "gravelCount", localCustomWorldSettings.O);
/* 802 */         localCustomWorldSettings.P = ChatDeserializer.a(localJsonObject, "gravelMinHeight", localCustomWorldSettings.P);
/* 803 */         localCustomWorldSettings.Q = ChatDeserializer.a(localJsonObject, "gravelMaxHeight", localCustomWorldSettings.Q);
/* 804 */         localCustomWorldSettings.R = ChatDeserializer.a(localJsonObject, "graniteSize", localCustomWorldSettings.R);
/* 805 */         localCustomWorldSettings.S = ChatDeserializer.a(localJsonObject, "graniteCount", localCustomWorldSettings.S);
/* 806 */         localCustomWorldSettings.T = ChatDeserializer.a(localJsonObject, "graniteMinHeight", localCustomWorldSettings.T);
/* 807 */         localCustomWorldSettings.U = ChatDeserializer.a(localJsonObject, "graniteMaxHeight", localCustomWorldSettings.U);
/* 808 */         localCustomWorldSettings.V = ChatDeserializer.a(localJsonObject, "dioriteSize", localCustomWorldSettings.V);
/* 809 */         localCustomWorldSettings.W = ChatDeserializer.a(localJsonObject, "dioriteCount", localCustomWorldSettings.W);
/* 810 */         localCustomWorldSettings.X = ChatDeserializer.a(localJsonObject, "dioriteMinHeight", localCustomWorldSettings.X);
/* 811 */         localCustomWorldSettings.Y = ChatDeserializer.a(localJsonObject, "dioriteMaxHeight", localCustomWorldSettings.Y);
/* 812 */         localCustomWorldSettings.Z = ChatDeserializer.a(localJsonObject, "andesiteSize", localCustomWorldSettings.Z);
/* 813 */         localCustomWorldSettings.aa = ChatDeserializer.a(localJsonObject, "andesiteCount", localCustomWorldSettings.aa);
/* 814 */         localCustomWorldSettings.ab = ChatDeserializer.a(localJsonObject, "andesiteMinHeight", localCustomWorldSettings.ab);
/* 815 */         localCustomWorldSettings.ac = ChatDeserializer.a(localJsonObject, "andesiteMaxHeight", localCustomWorldSettings.ac);
/* 816 */         localCustomWorldSettings.ad = ChatDeserializer.a(localJsonObject, "coalSize", localCustomWorldSettings.ad);
/* 817 */         localCustomWorldSettings.ae = ChatDeserializer.a(localJsonObject, "coalCount", localCustomWorldSettings.ae);
/* 818 */         localCustomWorldSettings.af = ChatDeserializer.a(localJsonObject, "coalMinHeight", localCustomWorldSettings.af);
/* 819 */         localCustomWorldSettings.ag = ChatDeserializer.a(localJsonObject, "coalMaxHeight", localCustomWorldSettings.ag);
/* 820 */         localCustomWorldSettings.ah = ChatDeserializer.a(localJsonObject, "ironSize", localCustomWorldSettings.ah);
/* 821 */         localCustomWorldSettings.ai = ChatDeserializer.a(localJsonObject, "ironCount", localCustomWorldSettings.ai);
/* 822 */         localCustomWorldSettings.aj = ChatDeserializer.a(localJsonObject, "ironMinHeight", localCustomWorldSettings.aj);
/* 823 */         localCustomWorldSettings.ak = ChatDeserializer.a(localJsonObject, "ironMaxHeight", localCustomWorldSettings.ak);
/* 824 */         localCustomWorldSettings.al = ChatDeserializer.a(localJsonObject, "goldSize", localCustomWorldSettings.al);
/* 825 */         localCustomWorldSettings.am = ChatDeserializer.a(localJsonObject, "goldCount", localCustomWorldSettings.am);
/* 826 */         localCustomWorldSettings.an = ChatDeserializer.a(localJsonObject, "goldMinHeight", localCustomWorldSettings.an);
/* 827 */         localCustomWorldSettings.ao = ChatDeserializer.a(localJsonObject, "goldMaxHeight", localCustomWorldSettings.ao);
/* 828 */         localCustomWorldSettings.ap = ChatDeserializer.a(localJsonObject, "redstoneSize", localCustomWorldSettings.ap);
/* 829 */         localCustomWorldSettings.aq = ChatDeserializer.a(localJsonObject, "redstoneCount", localCustomWorldSettings.aq);
/* 830 */         localCustomWorldSettings.ar = ChatDeserializer.a(localJsonObject, "redstoneMinHeight", localCustomWorldSettings.ar);
/* 831 */         localCustomWorldSettings.as = ChatDeserializer.a(localJsonObject, "redstoneMaxHeight", localCustomWorldSettings.as);
/* 832 */         localCustomWorldSettings.at = ChatDeserializer.a(localJsonObject, "diamondSize", localCustomWorldSettings.at);
/* 833 */         localCustomWorldSettings.au = ChatDeserializer.a(localJsonObject, "diamondCount", localCustomWorldSettings.au);
/* 834 */         localCustomWorldSettings.av = ChatDeserializer.a(localJsonObject, "diamondMinHeight", localCustomWorldSettings.av);
/* 835 */         localCustomWorldSettings.aw = ChatDeserializer.a(localJsonObject, "diamondMaxHeight", localCustomWorldSettings.aw);
/* 836 */         localCustomWorldSettings.ax = ChatDeserializer.a(localJsonObject, "lapisSize", localCustomWorldSettings.ax);
/* 837 */         localCustomWorldSettings.ay = ChatDeserializer.a(localJsonObject, "lapisCount", localCustomWorldSettings.ay);
/* 838 */         localCustomWorldSettings.az = ChatDeserializer.a(localJsonObject, "lapisCenterHeight", localCustomWorldSettings.az);
/* 839 */         localCustomWorldSettings.aA = ChatDeserializer.a(localJsonObject, "lapisSpread", localCustomWorldSettings.aA);
/*     */       }
/*     */       catch (Exception localException) {}
/*     */       
/*     */ 
/* 844 */       return localCustomWorldSettings;
/*     */     }
/*     */     
/*     */     public JsonElement a(CustomWorldSettingsFinal.CustomWorldSettings paramCustomWorldSettings, Type paramType, JsonSerializationContext paramJsonSerializationContext)
/*     */     {
/* 849 */       JsonObject localJsonObject = new JsonObject();
/*     */       
/* 851 */       localJsonObject.addProperty("coordinateScale", Float.valueOf(paramCustomWorldSettings.b));
/* 852 */       localJsonObject.addProperty("heightScale", Float.valueOf(paramCustomWorldSettings.c));
/* 853 */       localJsonObject.addProperty("lowerLimitScale", Float.valueOf(paramCustomWorldSettings.e));
/* 854 */       localJsonObject.addProperty("upperLimitScale", Float.valueOf(paramCustomWorldSettings.d));
/* 855 */       localJsonObject.addProperty("depthNoiseScaleX", Float.valueOf(paramCustomWorldSettings.f));
/* 856 */       localJsonObject.addProperty("depthNoiseScaleZ", Float.valueOf(paramCustomWorldSettings.g));
/* 857 */       localJsonObject.addProperty("depthNoiseScaleExponent", Float.valueOf(paramCustomWorldSettings.h));
/* 858 */       localJsonObject.addProperty("mainNoiseScaleX", Float.valueOf(paramCustomWorldSettings.i));
/* 859 */       localJsonObject.addProperty("mainNoiseScaleY", Float.valueOf(paramCustomWorldSettings.j));
/* 860 */       localJsonObject.addProperty("mainNoiseScaleZ", Float.valueOf(paramCustomWorldSettings.k));
/* 861 */       localJsonObject.addProperty("baseSize", Float.valueOf(paramCustomWorldSettings.l));
/* 862 */       localJsonObject.addProperty("stretchY", Float.valueOf(paramCustomWorldSettings.m));
/* 863 */       localJsonObject.addProperty("biomeDepthWeight", Float.valueOf(paramCustomWorldSettings.n));
/* 864 */       localJsonObject.addProperty("biomeDepthOffset", Float.valueOf(paramCustomWorldSettings.o));
/* 865 */       localJsonObject.addProperty("biomeScaleWeight", Float.valueOf(paramCustomWorldSettings.p));
/* 866 */       localJsonObject.addProperty("biomeScaleOffset", Float.valueOf(paramCustomWorldSettings.q));
/* 867 */       localJsonObject.addProperty("seaLevel", Integer.valueOf(paramCustomWorldSettings.r));
/*     */       
/* 869 */       localJsonObject.addProperty("useCaves", Boolean.valueOf(paramCustomWorldSettings.s));
/* 870 */       localJsonObject.addProperty("useDungeons", Boolean.valueOf(paramCustomWorldSettings.t));
/* 871 */       localJsonObject.addProperty("dungeonChance", Integer.valueOf(paramCustomWorldSettings.u));
/* 872 */       localJsonObject.addProperty("useStrongholds", Boolean.valueOf(paramCustomWorldSettings.v));
/* 873 */       localJsonObject.addProperty("useVillages", Boolean.valueOf(paramCustomWorldSettings.w));
/* 874 */       localJsonObject.addProperty("useMineShafts", Boolean.valueOf(paramCustomWorldSettings.x));
/* 875 */       localJsonObject.addProperty("useTemples", Boolean.valueOf(paramCustomWorldSettings.y));
/* 876 */       localJsonObject.addProperty("useMonuments", Boolean.valueOf(paramCustomWorldSettings.z));
/* 877 */       localJsonObject.addProperty("useRavines", Boolean.valueOf(paramCustomWorldSettings.A));
/* 878 */       localJsonObject.addProperty("useWaterLakes", Boolean.valueOf(paramCustomWorldSettings.B));
/* 879 */       localJsonObject.addProperty("waterLakeChance", Integer.valueOf(paramCustomWorldSettings.C));
/* 880 */       localJsonObject.addProperty("useLavaLakes", Boolean.valueOf(paramCustomWorldSettings.D));
/* 881 */       localJsonObject.addProperty("lavaLakeChance", Integer.valueOf(paramCustomWorldSettings.E));
/* 882 */       localJsonObject.addProperty("useLavaOceans", Boolean.valueOf(paramCustomWorldSettings.F));
/*     */       
/* 884 */       localJsonObject.addProperty("fixedBiome", Integer.valueOf(paramCustomWorldSettings.G));
/* 885 */       localJsonObject.addProperty("biomeSize", Integer.valueOf(paramCustomWorldSettings.H));
/* 886 */       localJsonObject.addProperty("riverSize", Integer.valueOf(paramCustomWorldSettings.I));
/*     */       
/* 888 */       localJsonObject.addProperty("dirtSize", Integer.valueOf(paramCustomWorldSettings.J));
/* 889 */       localJsonObject.addProperty("dirtCount", Integer.valueOf(paramCustomWorldSettings.K));
/* 890 */       localJsonObject.addProperty("dirtMinHeight", Integer.valueOf(paramCustomWorldSettings.L));
/* 891 */       localJsonObject.addProperty("dirtMaxHeight", Integer.valueOf(paramCustomWorldSettings.M));
/* 892 */       localJsonObject.addProperty("gravelSize", Integer.valueOf(paramCustomWorldSettings.N));
/* 893 */       localJsonObject.addProperty("gravelCount", Integer.valueOf(paramCustomWorldSettings.O));
/* 894 */       localJsonObject.addProperty("gravelMinHeight", Integer.valueOf(paramCustomWorldSettings.P));
/* 895 */       localJsonObject.addProperty("gravelMaxHeight", Integer.valueOf(paramCustomWorldSettings.Q));
/* 896 */       localJsonObject.addProperty("graniteSize", Integer.valueOf(paramCustomWorldSettings.R));
/* 897 */       localJsonObject.addProperty("graniteCount", Integer.valueOf(paramCustomWorldSettings.S));
/* 898 */       localJsonObject.addProperty("graniteMinHeight", Integer.valueOf(paramCustomWorldSettings.T));
/* 899 */       localJsonObject.addProperty("graniteMaxHeight", Integer.valueOf(paramCustomWorldSettings.U));
/* 900 */       localJsonObject.addProperty("dioriteSize", Integer.valueOf(paramCustomWorldSettings.V));
/* 901 */       localJsonObject.addProperty("dioriteCount", Integer.valueOf(paramCustomWorldSettings.W));
/* 902 */       localJsonObject.addProperty("dioriteMinHeight", Integer.valueOf(paramCustomWorldSettings.X));
/* 903 */       localJsonObject.addProperty("dioriteMaxHeight", Integer.valueOf(paramCustomWorldSettings.Y));
/* 904 */       localJsonObject.addProperty("andesiteSize", Integer.valueOf(paramCustomWorldSettings.Z));
/* 905 */       localJsonObject.addProperty("andesiteCount", Integer.valueOf(paramCustomWorldSettings.aa));
/* 906 */       localJsonObject.addProperty("andesiteMinHeight", Integer.valueOf(paramCustomWorldSettings.ab));
/* 907 */       localJsonObject.addProperty("andesiteMaxHeight", Integer.valueOf(paramCustomWorldSettings.ac));
/* 908 */       localJsonObject.addProperty("coalSize", Integer.valueOf(paramCustomWorldSettings.ad));
/* 909 */       localJsonObject.addProperty("coalCount", Integer.valueOf(paramCustomWorldSettings.ae));
/* 910 */       localJsonObject.addProperty("coalMinHeight", Integer.valueOf(paramCustomWorldSettings.af));
/* 911 */       localJsonObject.addProperty("coalMaxHeight", Integer.valueOf(paramCustomWorldSettings.ag));
/* 912 */       localJsonObject.addProperty("ironSize", Integer.valueOf(paramCustomWorldSettings.ah));
/* 913 */       localJsonObject.addProperty("ironCount", Integer.valueOf(paramCustomWorldSettings.ai));
/* 914 */       localJsonObject.addProperty("ironMinHeight", Integer.valueOf(paramCustomWorldSettings.aj));
/* 915 */       localJsonObject.addProperty("ironMaxHeight", Integer.valueOf(paramCustomWorldSettings.ak));
/* 916 */       localJsonObject.addProperty("goldSize", Integer.valueOf(paramCustomWorldSettings.al));
/* 917 */       localJsonObject.addProperty("goldCount", Integer.valueOf(paramCustomWorldSettings.am));
/* 918 */       localJsonObject.addProperty("goldMinHeight", Integer.valueOf(paramCustomWorldSettings.an));
/* 919 */       localJsonObject.addProperty("goldMaxHeight", Integer.valueOf(paramCustomWorldSettings.ao));
/* 920 */       localJsonObject.addProperty("redstoneSize", Integer.valueOf(paramCustomWorldSettings.ap));
/* 921 */       localJsonObject.addProperty("redstoneCount", Integer.valueOf(paramCustomWorldSettings.aq));
/* 922 */       localJsonObject.addProperty("redstoneMinHeight", Integer.valueOf(paramCustomWorldSettings.ar));
/* 923 */       localJsonObject.addProperty("redstoneMaxHeight", Integer.valueOf(paramCustomWorldSettings.as));
/* 924 */       localJsonObject.addProperty("diamondSize", Integer.valueOf(paramCustomWorldSettings.at));
/* 925 */       localJsonObject.addProperty("diamondCount", Integer.valueOf(paramCustomWorldSettings.au));
/* 926 */       localJsonObject.addProperty("diamondMinHeight", Integer.valueOf(paramCustomWorldSettings.av));
/* 927 */       localJsonObject.addProperty("diamondMaxHeight", Integer.valueOf(paramCustomWorldSettings.aw));
/* 928 */       localJsonObject.addProperty("lapisSize", Integer.valueOf(paramCustomWorldSettings.ax));
/* 929 */       localJsonObject.addProperty("lapisCount", Integer.valueOf(paramCustomWorldSettings.ay));
/* 930 */       localJsonObject.addProperty("lapisCenterHeight", Integer.valueOf(paramCustomWorldSettings.az));
/* 931 */       localJsonObject.addProperty("lapisSpread", Integer.valueOf(paramCustomWorldSettings.aA));
/*     */       
/* 933 */       return localJsonObject;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CustomWorldSettingsFinal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */