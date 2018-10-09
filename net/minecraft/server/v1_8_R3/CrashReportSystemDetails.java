/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Callable;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CrashReportSystemDetails
/*     */ {
/*     */   private final CrashReport a;
/*     */   private final String b;
/*  14 */   private final List<CrashReportDetail> c = Lists.newArrayList();
/*  15 */   private StackTraceElement[] d = new StackTraceElement[0];
/*     */   
/*     */   public CrashReportSystemDetails(CrashReport paramCrashReport, String paramString) {
/*  18 */     this.a = paramCrashReport;
/*  19 */     this.b = paramString;
/*     */   }
/*     */   
/*     */   public static String a(double paramDouble1, double paramDouble2, double paramDouble3) {
/*  23 */     return String.format("%.2f,%.2f,%.2f - %s", new Object[] { Double.valueOf(paramDouble1), Double.valueOf(paramDouble2), Double.valueOf(paramDouble3), a(new BlockPosition(paramDouble1, paramDouble2, paramDouble3)) });
/*     */   }
/*     */   
/*     */   public static String a(BlockPosition paramBlockPosition) {
/*  27 */     int i = paramBlockPosition.getX();
/*  28 */     int j = paramBlockPosition.getY();
/*  29 */     int k = paramBlockPosition.getZ();
/*  30 */     StringBuilder localStringBuilder = new StringBuilder();
/*     */     try
/*     */     {
/*  33 */       localStringBuilder.append(String.format("World: (%d,%d,%d)", new Object[] { Integer.valueOf(i), Integer.valueOf(j), Integer.valueOf(k) }));
/*     */     } catch (Throwable localThrowable1) {
/*  35 */       localStringBuilder.append("(Error finding world loc)");
/*     */     }
/*     */     
/*  38 */     localStringBuilder.append(", ");
/*     */     int i1;
/*     */     int i2;
/*  41 */     int i3; int i4; int i5; int i6; int i7; int i8; try { int m = i >> 4;
/*  42 */       i1 = k >> 4;
/*  43 */       i2 = i & 0xF;
/*  44 */       i3 = j >> 4;
/*  45 */       i4 = k & 0xF;
/*  46 */       i5 = m << 4;
/*  47 */       i6 = i1 << 4;
/*  48 */       i7 = (m + 1 << 4) - 1;
/*  49 */       i8 = (i1 + 1 << 4) - 1;
/*  50 */       localStringBuilder.append(String.format("Chunk: (at %d,%d,%d in %d,%d; contains blocks %d,0,%d to %d,255,%d)", new Object[] { Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(m), Integer.valueOf(i1), Integer.valueOf(i5), Integer.valueOf(i6), Integer.valueOf(i7), Integer.valueOf(i8) }));
/*     */     } catch (Throwable localThrowable2) {
/*  52 */       localStringBuilder.append("(Error finding chunk loc)");
/*     */     }
/*     */     
/*  55 */     localStringBuilder.append(", ");
/*     */     try
/*     */     {
/*  58 */       int n = i >> 9;
/*  59 */       i1 = k >> 9;
/*  60 */       i2 = n << 5;
/*  61 */       i3 = i1 << 5;
/*  62 */       i4 = (n + 1 << 5) - 1;
/*  63 */       i5 = (i1 + 1 << 5) - 1;
/*  64 */       i6 = n << 9;
/*  65 */       i7 = i1 << 9;
/*  66 */       i8 = (n + 1 << 9) - 1;
/*  67 */       int i9 = (i1 + 1 << 9) - 1;
/*  68 */       localStringBuilder.append(String.format("Region: (%d,%d; contains chunks %d,%d to %d,%d, blocks %d,0,%d to %d,255,%d)", new Object[] { Integer.valueOf(n), Integer.valueOf(i1), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5), Integer.valueOf(i6), Integer.valueOf(i7), Integer.valueOf(i8), Integer.valueOf(i9) }));
/*     */     } catch (Throwable localThrowable3) {
/*  70 */       localStringBuilder.append("(Error finding world loc)");
/*     */     }
/*     */     
/*  73 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */   public void a(String paramString, Callable<String> paramCallable) {
/*     */     try {
/*  78 */       a(paramString, paramCallable.call());
/*     */     } catch (Throwable localThrowable) {
/*  80 */       a(paramString, localThrowable);
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(String paramString, Object paramObject) {
/*  85 */     this.c.add(new CrashReportDetail(paramString, paramObject));
/*     */   }
/*     */   
/*     */   public void a(String paramString, Throwable paramThrowable) {
/*  89 */     a(paramString, paramThrowable);
/*     */   }
/*     */   
/*     */   public int a(int paramInt) {
/*  93 */     StackTraceElement[] arrayOfStackTraceElement = Thread.currentThread().getStackTrace();
/*     */     
/*     */ 
/*  96 */     if (arrayOfStackTraceElement.length <= 0) {
/*  97 */       return 0;
/*     */     }
/*     */     
/* 100 */     this.d = new StackTraceElement[arrayOfStackTraceElement.length - 3 - paramInt];
/* 101 */     System.arraycopy(arrayOfStackTraceElement, 3 + paramInt, this.d, 0, this.d.length);
/* 102 */     return this.d.length;
/*     */   }
/*     */   
/*     */   public boolean a(StackTraceElement paramStackTraceElement1, StackTraceElement paramStackTraceElement2) {
/* 106 */     if ((this.d.length == 0) || (paramStackTraceElement1 == null)) {
/* 107 */       return false;
/*     */     }
/*     */     
/* 110 */     StackTraceElement localStackTraceElement = this.d[0];
/*     */     
/*     */ 
/* 113 */     if ((localStackTraceElement.isNativeMethod() != paramStackTraceElement1.isNativeMethod()) || (!localStackTraceElement.getClassName().equals(paramStackTraceElement1.getClassName())) || (!localStackTraceElement.getFileName().equals(paramStackTraceElement1.getFileName())) || (!localStackTraceElement.getMethodName().equals(paramStackTraceElement1.getMethodName())))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 118 */       return false;
/*     */     }
/*     */     
/* 121 */     if ((paramStackTraceElement2 != null ? 1 : 0) != (this.d.length > 1 ? 1 : 0)) {
/* 122 */       return false;
/*     */     }
/* 124 */     if ((paramStackTraceElement2 != null) && (!this.d[1].equals(paramStackTraceElement2))) {
/* 125 */       return false;
/*     */     }
/*     */     
/* 128 */     this.d[0] = paramStackTraceElement1;
/*     */     
/* 130 */     return true;
/*     */   }
/*     */   
/*     */   public void b(int paramInt) {
/* 134 */     StackTraceElement[] arrayOfStackTraceElement = new StackTraceElement[this.d.length - paramInt];
/* 135 */     System.arraycopy(this.d, 0, arrayOfStackTraceElement, 0, arrayOfStackTraceElement.length);
/* 136 */     this.d = arrayOfStackTraceElement;
/*     */   }
/*     */   
/*     */   public void a(StringBuilder paramStringBuilder) {
/* 140 */     paramStringBuilder.append("-- ").append(this.b).append(" --\n");
/* 141 */     paramStringBuilder.append("Details:");
/*     */     
/* 143 */     for (Object localObject1 = this.c.iterator(); ((Iterator)localObject1).hasNext();) { CrashReportDetail localCrashReportDetail = (CrashReportDetail)((Iterator)localObject1).next();
/* 144 */       paramStringBuilder.append("\n\t");
/* 145 */       paramStringBuilder.append(localCrashReportDetail.a());
/* 146 */       paramStringBuilder.append(": ");
/* 147 */       paramStringBuilder.append(localCrashReportDetail.b());
/*     */     }
/*     */     
/* 150 */     if ((this.d != null) && (this.d.length > 0)) {
/* 151 */       paramStringBuilder.append("\nStacktrace:");
/*     */       
/* 153 */       for (Object localObject2 : this.d) {
/* 154 */         paramStringBuilder.append("\n\tat ");
/* 155 */         paramStringBuilder.append(((StackTraceElement)localObject2).toString());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public StackTraceElement[] a() {
/* 161 */     return this.d;
/*     */   }
/*     */   
/*     */   public static void a(CrashReportSystemDetails paramCrashReportSystemDetails, BlockPosition paramBlockPosition, final Block paramBlock, int paramInt) {
/* 165 */     int i = Block.getId(paramBlock);
/* 166 */     paramCrashReportSystemDetails.a("Block type", new Callable()
/*     */     {
/*     */       public String a() throws Exception {
/*     */         try {
/* 170 */           return String.format("ID #%d (%s // %s)", new Object[] { Integer.valueOf(this.a), paramBlock.a(), paramBlock.getClass().getCanonicalName() });
/*     */         } catch (Throwable localThrowable) {}
/* 172 */         return "ID #" + this.a;
/*     */       }
/*     */       
/*     */ 
/* 176 */     });
/* 177 */     paramCrashReportSystemDetails.a("Block data value", new Callable()
/*     */     {
/*     */       public String a() throws Exception {
/* 180 */         if (this.a < 0) {
/* 181 */           return "Unknown? (Got " + this.a + ")";
/*     */         }
/* 183 */         String str = String.format("%4s", new Object[] { Integer.toBinaryString(this.a) }).replace(" ", "0");
/*     */         
/*     */ 
/* 186 */         return String.format("%1$d / 0x%1$X / 0b%2$s", new Object[] { Integer.valueOf(this.a), str });
/*     */       }
/*     */       
/* 189 */     });
/* 190 */     paramCrashReportSystemDetails.a("Block location", new Callable()
/*     */     {
/*     */       public String a() throws Exception {
/* 193 */         return CrashReportSystemDetails.a(this.a);
/*     */       }
/*     */     });
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
/*     */   public static void a(CrashReportSystemDetails paramCrashReportSystemDetails, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*     */   {
/* 222 */     paramCrashReportSystemDetails.a("Block", new Callable()
/*     */     {
/*     */       public String a() throws Exception {
/* 225 */         return this.a.toString();
/*     */       }
/*     */       
/* 228 */     });
/* 229 */     paramCrashReportSystemDetails.a("Block location", new Callable()
/*     */     {
/*     */       public String a() throws Exception {
/* 232 */         return CrashReportSystemDetails.a(this.a);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   static class CrashReportDetail {
/*     */     private final String a;
/*     */     private final String b;
/*     */     
/*     */     public CrashReportDetail(String paramString, Object paramObject) {
/* 242 */       this.a = paramString;
/*     */       
/* 244 */       if (paramObject == null) {
/* 245 */         this.b = "~~NULL~~";
/* 246 */       } else if ((paramObject instanceof Throwable)) {
/* 247 */         Throwable localThrowable = (Throwable)paramObject;
/* 248 */         this.b = ("~~ERROR~~ " + localThrowable.getClass().getSimpleName() + ": " + localThrowable.getMessage());
/*     */       } else {
/* 250 */         this.b = paramObject.toString();
/*     */       }
/*     */     }
/*     */     
/*     */     public String a() {
/* 255 */       return this.a;
/*     */     }
/*     */     
/*     */     public String b() {
/* 259 */       return this.b;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CrashReportSystemDetails.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */