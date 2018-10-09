/*     */ package org.fusesource.jansi;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AnsiRenderer
/*     */ {
/*     */   public static final String BEGIN_TOKEN = "@|";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final int BEGIN_TOKEN_LEN = 2;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final String END_TOKEN = "|@";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final int END_TOKEN_LEN = 2;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final String CODE_TEXT_SEPARATOR = " ";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final String CODE_LIST_SEPARATOR = ",";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String render(String input)
/*     */     throws IllegalArgumentException
/*     */   {
/*  61 */     StringBuffer buff = new StringBuffer();
/*     */     
/*  63 */     int i = 0;
/*     */     
/*     */     for (;;)
/*     */     {
/*  67 */       int j = input.indexOf("@|", i);
/*  68 */       if (j == -1) {
/*  69 */         if (i == 0) {
/*  70 */           return input;
/*     */         }
/*     */         
/*  73 */         buff.append(input.substring(i, input.length()));
/*  74 */         return buff.toString();
/*     */       }
/*     */       
/*     */ 
/*  78 */       buff.append(input.substring(i, j));
/*  79 */       int k = input.indexOf("|@", j);
/*     */       
/*  81 */       if (k == -1) {
/*  82 */         return input;
/*     */       }
/*     */       
/*  85 */       j += 2;
/*  86 */       String spec = input.substring(j, k);
/*     */       
/*  88 */       String[] items = spec.split(" ", 2);
/*  89 */       if (items.length == 1) {
/*  90 */         return input;
/*     */       }
/*  92 */       String replacement = render(items[1], items[0].split(","));
/*     */       
/*  94 */       buff.append(replacement);
/*     */       
/*  96 */       i = k + 2;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private static String render(String text, String... codes)
/*     */   {
/* 103 */     Ansi ansi = Ansi.ansi();
/* 104 */     for (String name : codes) {
/* 105 */       Code code = Code.valueOf(name.toUpperCase());
/*     */       
/* 107 */       if (code.isColor()) {
/* 108 */         if (code.isBackground()) {
/* 109 */           ansi = ansi.bg(code.getColor());
/*     */         }
/*     */         else {
/* 112 */           ansi = ansi.fg(code.getColor());
/*     */         }
/*     */       }
/* 115 */       else if (code.isAttribute()) {
/* 116 */         ansi = ansi.a(code.getAttribute());
/*     */       }
/*     */     }
/*     */     
/* 120 */     return ansi.a(text).reset().toString();
/*     */   }
/*     */   
/*     */   public static boolean test(String text) {
/* 124 */     return (text != null) && (text.contains("@|"));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static enum Code
/*     */   {
/* 134 */     BLACK(Ansi.Color.BLACK), 
/* 135 */     RED(Ansi.Color.RED), 
/* 136 */     GREEN(Ansi.Color.GREEN), 
/* 137 */     YELLOW(Ansi.Color.YELLOW), 
/* 138 */     BLUE(Ansi.Color.BLUE), 
/* 139 */     MAGENTA(Ansi.Color.MAGENTA), 
/* 140 */     CYAN(Ansi.Color.CYAN), 
/* 141 */     WHITE(Ansi.Color.WHITE), 
/*     */     
/*     */ 
/* 144 */     FG_BLACK(Ansi.Color.BLACK, false), 
/* 145 */     FG_RED(Ansi.Color.RED, false), 
/* 146 */     FG_GREEN(Ansi.Color.GREEN, false), 
/* 147 */     FG_YELLOW(Ansi.Color.YELLOW, false), 
/* 148 */     FG_BLUE(Ansi.Color.BLUE, false), 
/* 149 */     FG_MAGENTA(Ansi.Color.MAGENTA, false), 
/* 150 */     FG_CYAN(Ansi.Color.CYAN, false), 
/* 151 */     FG_WHITE(Ansi.Color.WHITE, false), 
/*     */     
/*     */ 
/* 154 */     BG_BLACK(Ansi.Color.BLACK, true), 
/* 155 */     BG_RED(Ansi.Color.RED, true), 
/* 156 */     BG_GREEN(Ansi.Color.GREEN, true), 
/* 157 */     BG_YELLOW(Ansi.Color.YELLOW, true), 
/* 158 */     BG_BLUE(Ansi.Color.BLUE, true), 
/* 159 */     BG_MAGENTA(Ansi.Color.MAGENTA, true), 
/* 160 */     BG_CYAN(Ansi.Color.CYAN, true), 
/* 161 */     BG_WHITE(Ansi.Color.WHITE, true), 
/*     */     
/*     */ 
/* 164 */     RESET(Ansi.Attribute.RESET), 
/* 165 */     INTENSITY_BOLD(Ansi.Attribute.INTENSITY_BOLD), 
/* 166 */     INTENSITY_FAINT(Ansi.Attribute.INTENSITY_FAINT), 
/* 167 */     ITALIC(Ansi.Attribute.ITALIC), 
/* 168 */     UNDERLINE(Ansi.Attribute.UNDERLINE), 
/* 169 */     BLINK_SLOW(Ansi.Attribute.BLINK_SLOW), 
/* 170 */     BLINK_FAST(Ansi.Attribute.BLINK_FAST), 
/* 171 */     BLINK_OFF(Ansi.Attribute.BLINK_OFF), 
/* 172 */     NEGATIVE_ON(Ansi.Attribute.NEGATIVE_ON), 
/* 173 */     NEGATIVE_OFF(Ansi.Attribute.NEGATIVE_OFF), 
/* 174 */     CONCEAL_ON(Ansi.Attribute.CONCEAL_ON), 
/* 175 */     CONCEAL_OFF(Ansi.Attribute.CONCEAL_OFF), 
/* 176 */     UNDERLINE_DOUBLE(Ansi.Attribute.UNDERLINE_DOUBLE), 
/* 177 */     UNDERLINE_OFF(Ansi.Attribute.UNDERLINE_OFF), 
/*     */     
/*     */ 
/* 180 */     BOLD(Ansi.Attribute.INTENSITY_BOLD), 
/* 181 */     FAINT(Ansi.Attribute.INTENSITY_FAINT);
/*     */     
/*     */ 
/*     */     private final Enum n;
/*     */     
/*     */     private final boolean background;
/*     */     
/*     */     private Code(Enum n, boolean background)
/*     */     {
/* 190 */       this.n = n;
/* 191 */       this.background = background;
/*     */     }
/*     */     
/*     */     private Code(Enum n)
/*     */     {
/* 196 */       this(n, false);
/*     */     }
/*     */     
/*     */     public boolean isColor() {
/* 200 */       return this.n instanceof Ansi.Color;
/*     */     }
/*     */     
/*     */     public Ansi.Color getColor() {
/* 204 */       return (Ansi.Color)this.n;
/*     */     }
/*     */     
/*     */     public boolean isAttribute() {
/* 208 */       return this.n instanceof Ansi.Attribute;
/*     */     }
/*     */     
/*     */     public Ansi.Attribute getAttribute() {
/* 212 */       return (Ansi.Attribute)this.n;
/*     */     }
/*     */     
/*     */     public boolean isBackground() {
/* 216 */       return this.background;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\fusesource\jansi\AnsiRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */