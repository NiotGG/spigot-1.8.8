/*     */ package org.bukkit.map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MinecraftFont
/*     */   extends MapFont
/*     */ {
/*     */   private static final int spaceSize = 2;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final String fontChars = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_'abcdefghijklmnopqrstuvwxyz{|}~ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƑáíóúñÑªº¿®¬½¼¡«»";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  21 */   private static final int[][] fontData = {
/*  22 */     new int[8], 
/*  23 */     { 126, 129, 165, 129, 189, 153, 129, 126 }, 
/*  24 */     { 126, 255, 219, 255, 195, 231, 255, 126 }, 
/*  25 */     { 54, 127, 127, 127, 62, 28, 8 }, 
/*  26 */     { 8, 28, 62, 127, 62, 28, 8 }, 
/*  27 */     { 28, 62, 28, 127, 127, 62, 28, 62 }, 
/*  28 */     { 8, 8, 28, 62, 127, 62, 28, 62 }, 
/*  29 */     { 0, 0, 24, 60, 60, 24 }, 
/*  30 */     { 255, 255, 231, 195, 195, 231, 255, 255 }, 
/*  31 */     { 0, 60, 102, 66, 66, 102, 60 }, 
/*  32 */     { 255, 195, 153, 189, 189, 153, 195, 255 }, 
/*  33 */     { 240, 224, 240, 190, 51, 51, 51, 30 }, 
/*  34 */     { 60, 102, 102, 102, 60, 24, 126, 24 }, 
/*  35 */     { 252, 204, 252, 12, 12, 14, 15, 7 }, 
/*  36 */     { 254, 198, 254, 198, 198, 230, 103, 3 }, 
/*  37 */     { 153, 90, 60, 231, 231, 60, 90, 153 }, 
/*  38 */     { 1, 7, 31, 127, 31, 7, 1 }, 
/*  39 */     { 64, 112, 124, 127, 124, 112, 64 }, 
/*  40 */     { 24, 60, 126, 24, 24, 126, 60, 24 }, 
/*  41 */     { 102, 102, 102, 102, 102, 0, 102 }, 
/*  42 */     { 254, 219, 219, 222, 216, 216, 216 }, 
/*  43 */     { 124, 198, 28, 54, 54, 28, 51, 30 }, 
/*  44 */     { 0, 0, 0, 0, 126, 126, 126 }, 
/*  45 */     { 24, 60, 126, 24, 126, 60, 24, 255 }, 
/*  46 */     { 24, 60, 126, 24, 24, 24, 24 }, 
/*  47 */     { 24, 24, 24, 24, 126, 60, 24 }, 
/*  48 */     { 0, 24, 48, 127, 48, 24 }, 
/*  49 */     { 0, 12, 6, 127, 6, 12 }, 
/*  50 */     { 0, 0, 3, 3, 3, 127 }, 
/*  51 */     { 0, 36, 102, 255, 102, 36 }, 
/*  52 */     { 0, 24, 60, 126, 255, 255 }, 
/*  53 */     { 0, 255, 255, 126, 60, 24 }, 
/*  54 */     new int[8], 
/*  55 */     { 1, 1, 1, 1, 1, 0, 1 }, 
/*  56 */     { 10, 10, 5 }, 
/*  57 */     { 10, 10, 31, 10, 31, 10, 10 }, 
/*  58 */     { 4, 30, 1, 14, 16, 15, 4 }, 
/*  59 */     { 17, 9, 8, 4, 2, 18, 17 }, 
/*  60 */     { 4, 10, 4, 22, 13, 9, 22 }, 
/*  61 */     { 2, 2, 1 }, 
/*  62 */     { 12, 2, 1, 1, 1, 2, 12 }, 
/*  63 */     { 3, 4, 8, 8, 8, 4, 3 }, 
/*  64 */     { 0, 0, 9, 6, 9 }, 
/*  65 */     { 0, 4, 4, 31, 4, 4 }, 
/*  66 */     { 0, 0, 0, 0, 0, 1, 1, 1 }, 
/*  67 */     { 0, 0, 0, 31 }, 
/*  68 */     { 0, 0, 0, 0, 0, 1, 1 }, 
/*  69 */     { 16, 8, 8, 4, 2, 2, 1 }, 
/*  70 */     { 14, 17, 25, 21, 19, 17, 14 }, 
/*  71 */     { 4, 6, 4, 4, 4, 4, 31 }, 
/*  72 */     { 14, 17, 16, 12, 2, 17, 31 }, 
/*  73 */     { 14, 17, 16, 12, 16, 17, 14 }, 
/*  74 */     { 24, 20, 18, 17, 31, 16, 16 }, 
/*  75 */     { 31, 1, 15, 16, 16, 17, 14 }, 
/*  76 */     { 12, 2, 1, 15, 17, 17, 14 }, 
/*  77 */     { 31, 17, 16, 8, 4, 4, 4 }, 
/*  78 */     { 14, 17, 17, 14, 17, 17, 14 }, 
/*  79 */     { 14, 17, 17, 30, 16, 8, 6 }, 
/*  80 */     { 0, 1, 1, 0, 0, 1, 1 }, 
/*  81 */     { 0, 1, 1, 0, 0, 1, 1, 1 }, 
/*  82 */     { 8, 4, 2, 1, 2, 4, 8 }, 
/*  83 */     { 0, 0, 31, 0, 0, 31 }, 
/*  84 */     { 1, 2, 4, 8, 4, 2, 1 }, 
/*  85 */     { 14, 17, 16, 8, 4, 0, 4 }, 
/*  86 */     { 30, 33, 45, 45, 61, 1, 30 }, 
/*  87 */     { 14, 17, 31, 17, 17, 17, 17 }, 
/*  88 */     { 15, 17, 15, 17, 17, 17, 15 }, 
/*  89 */     { 14, 17, 1, 1, 1, 17, 14 }, 
/*  90 */     { 15, 17, 17, 17, 17, 17, 15 }, 
/*  91 */     { 31, 1, 7, 1, 1, 1, 31 }, 
/*  92 */     { 31, 1, 7, 1, 1, 1, 1 }, 
/*  93 */     { 30, 1, 25, 17, 17, 17, 14 }, 
/*  94 */     { 17, 17, 31, 17, 17, 17, 17 }, 
/*  95 */     { 7, 2, 2, 2, 2, 2, 7 }, 
/*  96 */     { 16, 16, 16, 16, 16, 17, 14 }, 
/*  97 */     { 17, 9, 7, 9, 17, 17, 17 }, 
/*  98 */     { 1, 1, 1, 1, 1, 1, 31 }, 
/*  99 */     { 17, 27, 21, 17, 17, 17, 17 }, 
/* 100 */     { 17, 19, 21, 25, 17, 17, 17 }, 
/* 101 */     { 14, 17, 17, 17, 17, 17, 14 }, 
/* 102 */     { 15, 17, 15, 1, 1, 1, 1 }, 
/* 103 */     { 14, 17, 17, 17, 17, 9, 22 }, 
/* 104 */     { 15, 17, 15, 17, 17, 17, 17 }, 
/* 105 */     { 30, 1, 14, 16, 16, 17, 14 }, 
/* 106 */     { 31, 4, 4, 4, 4, 4, 4 }, 
/* 107 */     { 17, 17, 17, 17, 17, 17, 14 }, 
/* 108 */     { 17, 17, 17, 17, 10, 10, 4 }, 
/* 109 */     { 17, 17, 17, 17, 21, 27, 17 }, 
/* 110 */     { 17, 10, 4, 10, 17, 17, 17 }, 
/* 111 */     { 17, 10, 4, 4, 4, 4, 4 }, 
/* 112 */     { 31, 16, 8, 4, 2, 1, 31 }, 
/* 113 */     { 7, 1, 1, 1, 1, 1, 7 }, 
/* 114 */     { 1, 2, 2, 4, 8, 8, 16 }, 
/* 115 */     { 7, 4, 4, 4, 4, 4, 7 }, 
/* 116 */     { 4, 10, 17 }, 
/* 117 */     { 0, 0, 0, 0, 0, 0, 0, 31 }, 
/* 118 */     { 1, 1, 2 }, 
/* 119 */     { 0, 0, 14, 16, 30, 17, 30 }, 
/* 120 */     { 1, 1, 13, 19, 17, 17, 15 }, 
/* 121 */     { 0, 0, 14, 17, 1, 17, 14 }, 
/* 122 */     { 16, 16, 22, 25, 17, 17, 30 }, 
/* 123 */     { 0, 0, 14, 17, 31, 1, 30 }, 
/* 124 */     { 12, 2, 15, 2, 2, 2, 2 }, 
/* 125 */     { 0, 0, 30, 17, 17, 30, 16, 15 }, 
/* 126 */     { 1, 1, 13, 19, 17, 17, 17 }, 
/* 127 */     { 1, 0, 1, 1, 1, 1, 1 }, 
/* 128 */     { 16, 0, 16, 16, 16, 17, 17, 14 }, 
/* 129 */     { 1, 1, 9, 5, 3, 5, 9 }, 
/* 130 */     { 1, 1, 1, 1, 1, 1, 2 }, 
/* 131 */     { 0, 0, 11, 21, 21, 17, 17 }, 
/* 132 */     { 0, 0, 15, 17, 17, 17, 17 }, 
/* 133 */     { 0, 0, 14, 17, 17, 17, 14 }, 
/* 134 */     { 0, 0, 13, 19, 17, 15, 1, 1 }, 
/* 135 */     { 0, 0, 22, 25, 17, 30, 16, 16 }, 
/* 136 */     { 0, 0, 13, 19, 1, 1, 1 }, 
/* 137 */     { 0, 0, 30, 1, 14, 16, 15 }, 
/* 138 */     { 2, 2, 7, 2, 2, 2, 4 }, 
/* 139 */     { 0, 0, 17, 17, 17, 17, 30 }, 
/* 140 */     { 0, 0, 17, 17, 17, 10, 4 }, 
/* 141 */     { 0, 0, 17, 17, 21, 21, 30 }, 
/* 142 */     { 0, 0, 17, 10, 4, 10, 17 }, 
/* 143 */     { 0, 0, 17, 17, 17, 30, 16, 15 }, 
/* 144 */     { 0, 0, 31, 8, 4, 2, 31 }, 
/* 145 */     { 12, 2, 2, 1, 2, 2, 12 }, 
/* 146 */     { 1, 1, 1, 0, 1, 1, 1 }, 
/* 147 */     { 3, 4, 4, 8, 4, 4, 3 }, 
/* 148 */     { 38, 25 }, 
/* 149 */     { 0, 0, 4, 10, 17, 17, 31 }, 
/* 150 */     { 14, 17, 1, 1, 17, 14, 16, 12 }, 
/* 151 */     { 10, 0, 17, 17, 17, 17, 30 }, 
/* 152 */     { 24, 0, 14, 17, 31, 1, 30 }, 
/* 153 */     { 14, 17, 14, 16, 30, 17, 30 }, 
/* 154 */     { 10, 0, 14, 16, 30, 17, 30 }, 
/* 155 */     { 3, 0, 14, 16, 30, 17, 30 }, 
/* 156 */     { 4, 0, 14, 16, 30, 17, 30 }, 
/* 157 */     { 0, 14, 17, 1, 17, 14, 16, 12 }, 
/* 158 */     { 14, 17, 14, 17, 31, 1, 30 }, 
/* 159 */     { 10, 0, 14, 17, 31, 1, 30 }, 
/* 160 */     { 3, 0, 14, 17, 31, 1, 30 }, 
/* 161 */     { 5, 0, 2, 2, 2, 2, 2 }, 
/* 162 */     { 14, 17, 4, 4, 4, 4, 4 }, 
/* 163 */     { 3, 0, 2, 2, 2, 2, 2 }, 
/* 164 */     { 17, 14, 17, 31, 17, 17, 17 }, 
/* 165 */     { 4, 0, 14, 17, 31, 17, 17 }, 
/* 166 */     { 24, 0, 31, 1, 7, 1, 31 }, 
/* 167 */     { 0, 0, 10, 20, 30, 5, 30 }, 
/* 168 */     { 30, 5, 15, 5, 5, 5, 29 }, 
/* 169 */     { 14, 17, 14, 17, 17, 17, 14 }, 
/* 170 */     { 10, 0, 14, 17, 17, 17, 14 }, 
/* 171 */     { 3, 0, 14, 17, 17, 17, 14 }, 
/* 172 */     { 14, 17, 0, 17, 17, 17, 30 }, 
/* 173 */     { 3, 0, 17, 17, 17, 17, 30 }, 
/* 174 */     { 10, 0, 17, 17, 17, 30, 16, 15 }, 
/* 175 */     { 17, 14, 17, 17, 17, 17, 14 }, 
/* 176 */     { 17, 0, 17, 17, 17, 17, 14 }, 
/* 177 */     { 0, 0, 14, 25, 21, 19, 14, 4 }, 
/* 178 */     { 12, 18, 2, 15, 2, 2, 31 }, 
/* 179 */     { 14, 17, 25, 21, 19, 17, 14 }, 
/* 180 */     { 0, 0, 5, 2, 5 }, 
/* 181 */     { 8, 20, 4, 14, 4, 4, 5, 2 }, 
/* 182 */     { 24, 0, 14, 16, 30, 17, 30 }, 
/* 183 */     { 3, 0, 1, 1, 1, 1, 1 }, 
/* 184 */     { 24, 0, 14, 17, 17, 17, 14 }, 
/* 185 */     { 24, 0, 17, 17, 17, 17, 30 }, 
/* 186 */     { 31, 0, 15, 17, 17, 17, 17 }, 
/* 187 */     { 31, 0, 17, 19, 21, 25, 17 }, 
/* 188 */     { 14, 16, 31, 30, 0, 31 }, 
/* 189 */     { 14, 17, 17, 14, 0, 31 }, 
/* 190 */     { 4, 0, 4, 2, 1, 17, 14 }, 
/* 191 */     { 0, 30, 45, 37, 43, 30 }, 
/* 192 */     { 0, 0, 0, 31, 16, 16 }, 
/* 193 */     { 17, 9, 8, 4, 18, 10, 25 }, 
/* 194 */     { 17, 9, 8, 4, 26, 26, 17 }, 
/* 195 */     { 0, 1, 0, 1, 1, 1, 1 }, 
/* 196 */     { 0, 20, 10, 5, 10, 20 }, 
/* 197 */     { 0, 5, 10, 20, 10, 5 }, 
/* 198 */     { 68, 17, 68, 17, 68, 17, 68, 17 }, 
/* 199 */     { 170, 85, 170, 85, 170, 85, 170, 85 }, 
/* 200 */     { 219, 238, 219, 119, 219, 238, 219, 119 }, 
/* 201 */     { 24, 24, 24, 24, 24, 24, 24, 24 }, 
/* 202 */     { 24, 24, 24, 24, 31, 24, 24, 24 }, 
/* 203 */     { 24, 24, 31, 24, 31, 24, 24, 24 }, 
/* 204 */     { 108, 108, 108, 108, 111, 108, 108, 108 }, 
/* 205 */     { 0, 0, 0, 0, 127, 108, 108, 108 }, 
/* 206 */     { 0, 0, 31, 24, 31, 24, 24, 24 }, 
/* 207 */     { 108, 108, 111, 96, 111, 108, 108, 108 }, 
/* 208 */     { 108, 108, 108, 108, 108, 108, 108, 108 }, 
/* 209 */     { 0, 0, 127, 96, 111, 108, 108, 108 }, 
/* 210 */     { 108, 108, 111, 96, 127 }, 
/* 211 */     { 108, 108, 108, 108, 127 }, 
/* 212 */     { 24, 24, 31, 24, 31 }, 
/* 213 */     { 0, 0, 0, 0, 31, 24, 24, 24 }, 
/* 214 */     { 24, 24, 24, 24, 248 }, 
/* 215 */     { 24, 24, 24, 24, 255 }, 
/* 216 */     { 0, 0, 0, 0, 255, 24, 24, 24 }, 
/* 217 */     { 24, 24, 24, 24, 248, 24, 24, 24 }, 
/* 218 */     { 0, 0, 0, 0, 255 }, 
/* 219 */     { 24, 24, 24, 24, 255, 24, 24, 24 }, 
/* 220 */     { 24, 24, 248, 24, 248, 24, 24, 24 }, 
/* 221 */     { 108, 108, 108, 108, 236, 108, 108, 108 }, 
/* 222 */     { 108, 108, 236, 12, 252 }, 
/* 223 */     { 0, 0, 252, 12, 236, 108, 108, 108 }, 
/* 224 */     { 108, 108, 239, 0, 255 }, 
/* 225 */     { 0, 0, 255, 0, 239, 108, 108, 108 }, 
/* 226 */     { 108, 108, 236, 12, 236, 108, 108, 108 }, 
/* 227 */     { 0, 0, 255, 0, 255 }, 
/* 228 */     { 108, 108, 239, 0, 239, 108, 108, 108 }, 
/* 229 */     { 24, 24, 255, 0, 255 }, 
/* 230 */     { 108, 108, 108, 108, 255 }, 
/* 231 */     { 0, 0, 255, 0, 255, 24, 24, 24 }, 
/* 232 */     { 0, 0, 0, 0, 255, 108, 108, 108 }, 
/* 233 */     { 108, 108, 108, 108, 252 }, 
/* 234 */     { 24, 24, 248, 24, 248 }, 
/* 235 */     { 0, 0, 248, 24, 248, 24, 24, 24 }, 
/* 236 */     { 0, 0, 0, 0, 252, 108, 108, 108 }, 
/* 237 */     { 108, 108, 108, 108, 255, 108, 108, 108 }, 
/* 238 */     { 24, 24, 255, 24, 255, 24, 24, 24 }, 
/* 239 */     { 24, 24, 24, 24, 31 }, 
/* 240 */     { 0, 0, 0, 0, 248, 24, 24, 24 }, 
/* 241 */     { 255, 255, 255, 255, 255, 255, 255, 255 }, 
/* 242 */     { 0, 0, 0, 0, 255, 255, 255, 255 }, 
/* 243 */     { 15, 15, 15, 15, 15, 15, 15, 15 }, 
/* 244 */     { 240, 240, 240, 240, 240, 240, 240, 240 }, 
/* 245 */     { 255, 255, 255, 255 }, 
/* 246 */     { 0, 0, 110, 59, 19, 59, 110 }, 
/* 247 */     { 0, 30, 51, 31, 51, 31, 3, 3 }, 
/* 248 */     { 0, 63, 51, 3, 3, 3, 3 }, 
/* 249 */     { 0, 127, 54, 54, 54, 54, 54 }, 
/* 250 */     { 63, 51, 6, 12, 6, 51, 63 }, 
/* 251 */     { 0, 0, 126, 27, 27, 27, 14 }, 
/* 252 */     { 0, 102, 102, 102, 102, 62, 6, 3 }, 
/* 253 */     { 0, 110, 59, 24, 24, 24, 24 }, 
/* 254 */     { 63, 12, 30, 51, 51, 30, 12, 63 }, 
/* 255 */     { 28, 54, 99, 127, 99, 54, 28 }, 
/* 256 */     { 28, 54, 99, 99, 54, 54, 119 }, 
/* 257 */     { 56, 12, 24, 62, 51, 51, 30 }, 
/* 258 */     { 0, 0, 126, 219, 219, 126 }, 
/* 259 */     { 96, 48, 126, 219, 219, 126, 6, 3 }, 
/* 260 */     { 28, 6, 3, 31, 3, 6, 28 }, 
/* 261 */     { 30, 51, 51, 51, 51, 51, 51 }, 
/* 262 */     { 0, 63, 0, 63, 0, 63 }, 
/* 263 */     { 12, 12, 63, 12, 12, 0, 63 }, 
/* 264 */     { 6, 12, 24, 12, 6, 0, 63 }, 
/* 265 */     { 24, 12, 6, 12, 24, 0, 63 }, 
/* 266 */     { 112, 216, 216, 24, 24, 24, 24, 24 }, 
/* 267 */     { 24, 24, 24, 24, 24, 27, 27, 14 }, 
/* 268 */     { 12, 12, 0, 63, 0, 12, 12 }, 
/* 269 */     { 0, 110, 59, 0, 110, 59 }, 
/* 270 */     { 28, 54, 54, 28 }, 
/* 271 */     { 0, 0, 0, 24, 24 }, 
/* 272 */     { 0, 0, 0, 0, 24 }, 
/* 273 */     { 240, 48, 48, 48, 55, 54, 60, 56 }, 
/* 274 */     { 30, 54, 54, 54, 54 }, 
/* 275 */     { 14, 24, 12, 6, 30 }, 
/* 276 */     { 0, 0, 60, 60, 60, 60 }, 
/* 277 */     new int[8] };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 283 */   public static final MinecraftFont Font = new MinecraftFont(false);
/*     */   
/*     */ 
/*     */ 
/*     */   public MinecraftFont()
/*     */   {
/* 289 */     this(true);
/*     */   }
/*     */   
/*     */   private MinecraftFont(boolean malleable) {
/* 293 */     for (int i = 1; i < fontData.length; i++) {
/* 294 */       char ch = (char)i;
/* 295 */       if ((i >= 32) && (i < 32 + " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_'abcdefghijklmnopqrstuvwxyz{|}~ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƑáíóúñÑªº¿®¬½¼¡«»".length())) {
/* 296 */         ch = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_'abcdefghijklmnopqrstuvwxyz{|}~ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƑáíóúñÑªº¿®¬½¼¡«»".charAt(i - 32);
/*     */       }
/*     */       
/* 299 */       if (ch == ' ') {
/* 300 */         setChar(ch, new MapFont.CharacterSprite(2, 8, new boolean[16]));
/*     */       }
/*     */       else
/*     */       {
/* 304 */         int[] rows = fontData[i];
/* 305 */         int width = 0;
/* 306 */         for (int r = 0; r < 8; r++) {
/* 307 */           for (int c = 0; c < 8; c++) {
/* 308 */             if (((rows[r] & 1 << c) != 0) && (c > width)) {
/* 309 */               width = c;
/*     */             }
/*     */           }
/*     */         }
/* 313 */         width++;
/*     */         
/* 315 */         boolean[] data = new boolean[width * 8];
/* 316 */         for (int r = 0; r < 8; r++) {
/* 317 */           for (int c = 0; c < width; c++) {
/* 318 */             data[(r * width + c)] = ((rows[r] & 1 << c) != 0 ? 1 : false);
/*     */           }
/*     */         }
/*     */         
/* 322 */         setChar(ch, new MapFont.CharacterSprite(width, 8, data));
/*     */       }
/*     */     }
/* 325 */     this.malleable = malleable;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\map\MinecraftFont.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */