/*      */ package org.apache.commons.codec.language;
/*      */ 
/*      */ import java.util.Locale;
/*      */ import org.apache.commons.codec.EncoderException;
/*      */ import org.apache.commons.codec.StringEncoder;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DoubleMetaphone
/*      */   implements StringEncoder
/*      */ {
/*      */   private static final String VOWELS = "AEIOUY";
/*   47 */   private static final String[] SILENT_START = { "GN", "KN", "PN", "WR", "PS" };
/*      */   
/*   49 */   private static final String[] L_R_N_M_B_H_F_V_W_SPACE = { "L", "R", "N", "M", "B", "H", "F", "V", "W", " " };
/*      */   
/*   51 */   private static final String[] ES_EP_EB_EL_EY_IB_IL_IN_IE_EI_ER = { "ES", "EP", "EB", "EL", "EY", "IB", "IL", "IN", "IE", "EI", "ER" };
/*      */   
/*   53 */   private static final String[] L_T_K_S_N_M_B_Z = { "L", "T", "K", "S", "N", "M", "B", "Z" };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   59 */   private int maxCodeLen = 4;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String doubleMetaphone(String paramString)
/*      */   {
/*   75 */     return doubleMetaphone(paramString, false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String doubleMetaphone(String paramString, boolean paramBoolean)
/*      */   {
/*   86 */     paramString = cleanInput(paramString);
/*   87 */     if (paramString == null) {
/*   88 */       return null;
/*      */     }
/*      */     
/*   91 */     boolean bool = isSlavoGermanic(paramString);
/*   92 */     int i = isSilentStart(paramString) ? 1 : 0;
/*      */     
/*   94 */     DoubleMetaphoneResult localDoubleMetaphoneResult = new DoubleMetaphoneResult(getMaxCodeLen());
/*      */     
/*   96 */     while ((!localDoubleMetaphoneResult.isComplete()) && (i <= paramString.length() - 1)) {
/*   97 */       switch (paramString.charAt(i)) {
/*      */       case 'A': 
/*      */       case 'E': 
/*      */       case 'I': 
/*      */       case 'O': 
/*      */       case 'U': 
/*      */       case 'Y': 
/*  104 */         i = handleAEIOUY(localDoubleMetaphoneResult, i);
/*  105 */         break;
/*      */       case 'B': 
/*  107 */         localDoubleMetaphoneResult.append('P');
/*  108 */         i = charAt(paramString, i + 1) == 'B' ? i + 2 : i + 1;
/*  109 */         break;
/*      */       
/*      */       case 'Ç': 
/*  112 */         localDoubleMetaphoneResult.append('S');
/*  113 */         i++;
/*  114 */         break;
/*      */       case 'C': 
/*  116 */         i = handleC(paramString, localDoubleMetaphoneResult, i);
/*  117 */         break;
/*      */       case 'D': 
/*  119 */         i = handleD(paramString, localDoubleMetaphoneResult, i);
/*  120 */         break;
/*      */       case 'F': 
/*  122 */         localDoubleMetaphoneResult.append('F');
/*  123 */         i = charAt(paramString, i + 1) == 'F' ? i + 2 : i + 1;
/*  124 */         break;
/*      */       case 'G': 
/*  126 */         i = handleG(paramString, localDoubleMetaphoneResult, i, bool);
/*  127 */         break;
/*      */       case 'H': 
/*  129 */         i = handleH(paramString, localDoubleMetaphoneResult, i);
/*  130 */         break;
/*      */       case 'J': 
/*  132 */         i = handleJ(paramString, localDoubleMetaphoneResult, i, bool);
/*  133 */         break;
/*      */       case 'K': 
/*  135 */         localDoubleMetaphoneResult.append('K');
/*  136 */         i = charAt(paramString, i + 1) == 'K' ? i + 2 : i + 1;
/*  137 */         break;
/*      */       case 'L': 
/*  139 */         i = handleL(paramString, localDoubleMetaphoneResult, i);
/*  140 */         break;
/*      */       case 'M': 
/*  142 */         localDoubleMetaphoneResult.append('M');
/*  143 */         i = conditionM0(paramString, i) ? i + 2 : i + 1;
/*  144 */         break;
/*      */       case 'N': 
/*  146 */         localDoubleMetaphoneResult.append('N');
/*  147 */         i = charAt(paramString, i + 1) == 'N' ? i + 2 : i + 1;
/*  148 */         break;
/*      */       
/*      */       case 'Ñ': 
/*  151 */         localDoubleMetaphoneResult.append('N');
/*  152 */         i++;
/*  153 */         break;
/*      */       case 'P': 
/*  155 */         i = handleP(paramString, localDoubleMetaphoneResult, i);
/*  156 */         break;
/*      */       case 'Q': 
/*  158 */         localDoubleMetaphoneResult.append('K');
/*  159 */         i = charAt(paramString, i + 1) == 'Q' ? i + 2 : i + 1;
/*  160 */         break;
/*      */       case 'R': 
/*  162 */         i = handleR(paramString, localDoubleMetaphoneResult, i, bool);
/*  163 */         break;
/*      */       case 'S': 
/*  165 */         i = handleS(paramString, localDoubleMetaphoneResult, i, bool);
/*  166 */         break;
/*      */       case 'T': 
/*  168 */         i = handleT(paramString, localDoubleMetaphoneResult, i);
/*  169 */         break;
/*      */       case 'V': 
/*  171 */         localDoubleMetaphoneResult.append('F');
/*  172 */         i = charAt(paramString, i + 1) == 'V' ? i + 2 : i + 1;
/*  173 */         break;
/*      */       case 'W': 
/*  175 */         i = handleW(paramString, localDoubleMetaphoneResult, i);
/*  176 */         break;
/*      */       case 'X': 
/*  178 */         i = handleX(paramString, localDoubleMetaphoneResult, i);
/*  179 */         break;
/*      */       case 'Z': 
/*  181 */         i = handleZ(paramString, localDoubleMetaphoneResult, i, bool);
/*  182 */         break;
/*      */       default: 
/*  184 */         i++;
/*      */       }
/*      */       
/*      */     }
/*      */     
/*  189 */     return paramBoolean ? localDoubleMetaphoneResult.getAlternate() : localDoubleMetaphoneResult.getPrimary();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Object encode(Object paramObject)
/*      */     throws EncoderException
/*      */   {
/*  202 */     if (!(paramObject instanceof String)) {
/*  203 */       throw new EncoderException("DoubleMetaphone encode parameter is not of type String");
/*      */     }
/*  205 */     return doubleMetaphone((String)paramObject);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String encode(String paramString)
/*      */   {
/*  216 */     return doubleMetaphone(paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isDoubleMetaphoneEqual(String paramString1, String paramString2)
/*      */   {
/*  230 */     return isDoubleMetaphoneEqual(paramString1, paramString2, false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isDoubleMetaphoneEqual(String paramString1, String paramString2, boolean paramBoolean)
/*      */   {
/*  244 */     return doubleMetaphone(paramString1, paramBoolean).equals(doubleMetaphone(paramString2, paramBoolean));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getMaxCodeLen()
/*      */   {
/*  252 */     return this.maxCodeLen;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setMaxCodeLen(int paramInt)
/*      */   {
/*  260 */     this.maxCodeLen = paramInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int handleAEIOUY(DoubleMetaphoneResult paramDoubleMetaphoneResult, int paramInt)
/*      */   {
/*  269 */     if (paramInt == 0) {
/*  270 */       paramDoubleMetaphoneResult.append('A');
/*      */     }
/*  272 */     return paramInt + 1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private int handleC(String paramString, DoubleMetaphoneResult paramDoubleMetaphoneResult, int paramInt)
/*      */   {
/*  279 */     if (conditionC0(paramString, paramInt)) {
/*  280 */       paramDoubleMetaphoneResult.append('K');
/*  281 */       paramInt += 2;
/*  282 */     } else { if (paramInt == 0) if (contains(paramString, paramInt, 6, new String[] { "CAESAR" })) {
/*  283 */           paramDoubleMetaphoneResult.append('S');
/*  284 */           paramInt += 2; return paramInt; }
/*  285 */       if (contains(paramString, paramInt, 2, new String[] { "CH" })) {
/*  286 */         paramInt = handleCH(paramString, paramDoubleMetaphoneResult, paramInt);
/*  287 */       } else { if (contains(paramString, paramInt, 2, new String[] { "CZ" })) if (!contains(paramString, paramInt - 2, 4, new String[] { "WICZ" }))
/*      */           {
/*      */ 
/*  290 */             paramDoubleMetaphoneResult.append('S', 'X');
/*  291 */             paramInt += 2; return paramInt; }
/*  292 */         if (contains(paramString, paramInt + 1, 3, new String[] { "CIA" }))
/*      */         {
/*  294 */           paramDoubleMetaphoneResult.append('X');
/*  295 */           paramInt += 3;
/*  296 */         } else { if ((contains(paramString, paramInt, 2, new String[] { "CC" })) && ((paramInt != 1) || (charAt(paramString, 0) != 'M')))
/*      */           {
/*      */ 
/*  299 */             return handleCC(paramString, paramDoubleMetaphoneResult, paramInt); }
/*  300 */           if (contains(paramString, paramInt, 2, new String[] { "CK", "CG", "CQ" })) {
/*  301 */             paramDoubleMetaphoneResult.append('K');
/*  302 */             paramInt += 2;
/*  303 */           } else if (contains(paramString, paramInt, 2, new String[] { "CI", "CE", "CY" }))
/*      */           {
/*  305 */             if (contains(paramString, paramInt, 3, new String[] { "CIO", "CIE", "CIA" })) {
/*  306 */               paramDoubleMetaphoneResult.append('S', 'X');
/*      */             } else {
/*  308 */               paramDoubleMetaphoneResult.append('S');
/*      */             }
/*  310 */             paramInt += 2;
/*      */           } else {
/*  312 */             paramDoubleMetaphoneResult.append('K');
/*  313 */             if (contains(paramString, paramInt + 1, 2, new String[] { " C", " Q", " G" }))
/*      */             {
/*  315 */               paramInt += 3;
/*  316 */             } else { if (contains(paramString, paramInt + 1, 1, new String[] { "C", "K", "Q" })) if (!contains(paramString, paramInt + 1, 2, new String[] { "CE", "CI" }))
/*      */                 {
/*  318 */                   paramInt += 2; return paramInt;
/*      */                 }
/*  320 */               paramInt++;
/*      */             }
/*      */           }
/*      */         } } }
/*  324 */     return paramInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private int handleCC(String paramString, DoubleMetaphoneResult paramDoubleMetaphoneResult, int paramInt)
/*      */   {
/*  331 */     if (contains(paramString, paramInt + 2, 1, new String[] { "I", "E", "H" })) if (!contains(paramString, paramInt + 2, 2, new String[] { "HU" }))
/*      */       {
/*      */ 
/*  334 */         if ((paramInt != 1) || (charAt(paramString, paramInt - 1) != 'A')) { if (!contains(paramString, paramInt - 1, 5, new String[] { "UCCEE", "UCCES" })) {}
/*      */         }
/*      */         else {
/*  337 */           paramDoubleMetaphoneResult.append("KS");
/*      */           break label108;
/*      */         }
/*  340 */         paramDoubleMetaphoneResult.append('X');
/*      */         label108:
/*  342 */         paramInt += 3;
/*      */         return paramInt; }
/*  344 */     paramDoubleMetaphoneResult.append('K');
/*  345 */     paramInt += 2;
/*      */     
/*      */ 
/*  348 */     return paramInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private int handleCH(String paramString, DoubleMetaphoneResult paramDoubleMetaphoneResult, int paramInt)
/*      */   {
/*  355 */     if (paramInt > 0) if (contains(paramString, paramInt, 4, new String[] { "CHAE" })) {
/*  356 */         paramDoubleMetaphoneResult.append('K', 'X');
/*  357 */         return paramInt + 2; }
/*  358 */     if (conditionCH0(paramString, paramInt))
/*      */     {
/*  360 */       paramDoubleMetaphoneResult.append('K');
/*  361 */       return paramInt + 2; }
/*  362 */     if (conditionCH1(paramString, paramInt))
/*      */     {
/*  364 */       paramDoubleMetaphoneResult.append('K');
/*  365 */       return paramInt + 2;
/*      */     }
/*  367 */     if (paramInt > 0) {
/*  368 */       if (contains(paramString, 0, 2, new String[] { "MC" })) {
/*  369 */         paramDoubleMetaphoneResult.append('K');
/*      */       } else {
/*  371 */         paramDoubleMetaphoneResult.append('X', 'K');
/*      */       }
/*      */     } else {
/*  374 */       paramDoubleMetaphoneResult.append('X');
/*      */     }
/*  376 */     return paramInt + 2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private int handleD(String paramString, DoubleMetaphoneResult paramDoubleMetaphoneResult, int paramInt)
/*      */   {
/*  384 */     if (contains(paramString, paramInt, 2, new String[] { "DG" }))
/*      */     {
/*  386 */       if (contains(paramString, paramInt + 2, 1, new String[] { "I", "E", "Y" })) {
/*  387 */         paramDoubleMetaphoneResult.append('J');
/*  388 */         paramInt += 3;
/*      */       }
/*      */       else {
/*  391 */         paramDoubleMetaphoneResult.append("TK");
/*  392 */         paramInt += 2;
/*      */       }
/*  394 */     } else if (contains(paramString, paramInt, 2, new String[] { "DT", "DD" })) {
/*  395 */       paramDoubleMetaphoneResult.append('T');
/*  396 */       paramInt += 2;
/*      */     } else {
/*  398 */       paramDoubleMetaphoneResult.append('T');
/*  399 */       paramInt++;
/*      */     }
/*  401 */     return paramInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private int handleG(String paramString, DoubleMetaphoneResult paramDoubleMetaphoneResult, int paramInt, boolean paramBoolean)
/*      */   {
/*  409 */     if (charAt(paramString, paramInt + 1) == 'H') {
/*  410 */       paramInt = handleGH(paramString, paramDoubleMetaphoneResult, paramInt);
/*  411 */     } else if (charAt(paramString, paramInt + 1) == 'N') {
/*  412 */       if ((paramInt == 1) && (isVowel(charAt(paramString, 0))) && (!paramBoolean)) {
/*  413 */         paramDoubleMetaphoneResult.append("KN", "N");
/*  414 */       } else if ((!contains(paramString, paramInt + 2, 2, new String[] { "EY" })) && (charAt(paramString, paramInt + 1) != 'Y') && (!paramBoolean))
/*      */       {
/*  416 */         paramDoubleMetaphoneResult.append("N", "KN");
/*      */       } else {
/*  418 */         paramDoubleMetaphoneResult.append("KN");
/*      */       }
/*  420 */       paramInt += 2;
/*  421 */     } else if ((contains(paramString, paramInt + 1, 2, new String[] { "LI" })) && (!paramBoolean)) {
/*  422 */       paramDoubleMetaphoneResult.append("KL", "L");
/*  423 */       paramInt += 2;
/*  424 */     } else if ((paramInt == 0) && ((charAt(paramString, paramInt + 1) == 'Y') || (contains(paramString, paramInt + 1, 2, ES_EP_EB_EL_EY_IB_IL_IN_IE_EI_ER))))
/*      */     {
/*      */ 
/*      */ 
/*  428 */       paramDoubleMetaphoneResult.append('K', 'J');
/*  429 */       paramInt += 2;
/*  430 */     } else { if ((contains(paramString, paramInt + 1, 2, new String[] { "ER" })) || (charAt(paramString, paramInt + 1) == 'Y')) if (!contains(paramString, 0, 6, new String[] { "DANGER", "RANGER", "MANGER" })) if (!contains(paramString, paramInt - 1, 1, new String[] { "E", "I" })) if (!contains(paramString, paramInt - 1, 3, new String[] { "RGY", "OGY" }))
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  436 */               paramDoubleMetaphoneResult.append('K', 'J');
/*  437 */               paramInt += 2; return paramInt; }
/*  438 */       if (!contains(paramString, paramInt + 1, 1, new String[] { "E", "I", "Y" })) { if (!contains(paramString, paramInt - 1, 4, new String[] { "AGGI", "OGGI" })) {}
/*      */       }
/*      */       else {
/*  441 */         if (!contains(paramString, 0, 4, new String[] { "VAN ", "VON " })) if (!contains(paramString, 0, 3, new String[] { "SCH" })) { if (!contains(paramString, paramInt + 1, 2, new String[] { "ET" })) {
/*      */               break label483;
/*      */             }
/*      */           }
/*  445 */         paramDoubleMetaphoneResult.append('K');
/*  446 */         break label521; label483: if (contains(paramString, paramInt + 1, 3, new String[] { "IER" })) {
/*  447 */           paramDoubleMetaphoneResult.append('J');
/*      */         } else
/*  449 */           paramDoubleMetaphoneResult.append('J', 'K');
/*      */         label521:
/*  451 */         paramInt += 2;
/*      */         return paramInt; } if (charAt(paramString, paramInt + 1) == 'G') {
/*  453 */         paramInt += 2;
/*  454 */         paramDoubleMetaphoneResult.append('K');
/*      */       } else {
/*  456 */         paramInt++;
/*  457 */         paramDoubleMetaphoneResult.append('K');
/*      */       } }
/*  459 */     return paramInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private int handleGH(String paramString, DoubleMetaphoneResult paramDoubleMetaphoneResult, int paramInt)
/*      */   {
/*  466 */     if ((paramInt > 0) && (!isVowel(charAt(paramString, paramInt - 1)))) {
/*  467 */       paramDoubleMetaphoneResult.append('K');
/*  468 */       paramInt += 2;
/*  469 */     } else if (paramInt == 0) {
/*  470 */       if (charAt(paramString, paramInt + 2) == 'I') {
/*  471 */         paramDoubleMetaphoneResult.append('J');
/*      */       } else {
/*  473 */         paramDoubleMetaphoneResult.append('K');
/*      */       }
/*  475 */       paramInt += 2;
/*  476 */     } else { if (paramInt > 1) { if (contains(paramString, paramInt - 2, 1, new String[] { "B", "H", "D" })) {} } else if (paramInt > 2) { if (contains(paramString, paramInt - 3, 1, new String[] { "B", "H", "D" })) {} } else { if (paramInt <= 3) break label180; if (!contains(paramString, paramInt - 4, 1, new String[] { "B", "H" })) {
/*      */           break label180;
/*      */         }
/*      */       }
/*  480 */       paramInt += 2; return paramInt;
/*      */       label180:
/*  482 */       if ((paramInt > 2) && (charAt(paramString, paramInt - 1) == 'U')) if (contains(paramString, paramInt - 3, 1, new String[] { "C", "G", "L", "R", "T" }))
/*      */         {
/*      */ 
/*  485 */           paramDoubleMetaphoneResult.append('F');
/*  486 */           break label274; } if ((paramInt > 0) && (charAt(paramString, paramInt - 1) != 'I'))
/*  487 */         paramDoubleMetaphoneResult.append('K');
/*      */       label274:
/*  489 */       paramInt += 2;
/*      */     }
/*  491 */     return paramInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private int handleH(String paramString, DoubleMetaphoneResult paramDoubleMetaphoneResult, int paramInt)
/*      */   {
/*  499 */     if (((paramInt == 0) || (isVowel(charAt(paramString, paramInt - 1)))) && (isVowel(charAt(paramString, paramInt + 1))))
/*      */     {
/*  501 */       paramDoubleMetaphoneResult.append('H');
/*  502 */       paramInt += 2;
/*      */     }
/*      */     else {
/*  505 */       paramInt++;
/*      */     }
/*  507 */     return paramInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private int handleJ(String paramString, DoubleMetaphoneResult paramDoubleMetaphoneResult, int paramInt, boolean paramBoolean)
/*      */   {
/*  515 */     if (!contains(paramString, paramInt, 4, new String[] { "JOSE" })) { if (!contains(paramString, 0, 4, new String[] { "SAN " })) {}
/*      */     } else {
/*  517 */       if (((paramInt != 0) || (charAt(paramString, paramInt + 4) != ' ')) && (paramString.length() != 4)) { if (!contains(paramString, 0, 4, new String[] { "SAN " })) {}
/*      */       } else {
/*  519 */         paramDoubleMetaphoneResult.append('H');
/*      */         break label99; }
/*  521 */       paramDoubleMetaphoneResult.append('J', 'H');
/*      */       label99:
/*  523 */       paramInt++;
/*      */       return paramInt; }
/*  525 */     if (paramInt == 0) if (!contains(paramString, paramInt, 4, new String[] { "JOSE" })) {
/*  526 */         paramDoubleMetaphoneResult.append('J', 'A');
/*  527 */         break label269; } if ((isVowel(charAt(paramString, paramInt - 1))) && (!paramBoolean) && ((charAt(paramString, paramInt + 1) == 'A') || (charAt(paramString, paramInt + 1) == 'O')))
/*      */     {
/*  529 */       paramDoubleMetaphoneResult.append('J', 'H');
/*  530 */     } else if (paramInt == paramString.length() - 1) {
/*  531 */       paramDoubleMetaphoneResult.append('J', ' ');
/*  532 */     } else if (!contains(paramString, paramInt + 1, 1, L_T_K_S_N_M_B_Z)) if (!contains(paramString, paramInt - 1, 1, new String[] { "S", "K", "L" }))
/*      */       {
/*  534 */         paramDoubleMetaphoneResult.append('J');
/*      */       }
/*      */     label269:
/*  537 */     if (charAt(paramString, paramInt + 1) == 'J') {
/*  538 */       paramInt += 2;
/*      */     } else {
/*  540 */       paramInt++;
/*      */     }
/*      */     
/*  543 */     return paramInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private int handleL(String paramString, DoubleMetaphoneResult paramDoubleMetaphoneResult, int paramInt)
/*      */   {
/*  550 */     if (charAt(paramString, paramInt + 1) == 'L') {
/*  551 */       if (conditionL0(paramString, paramInt)) {
/*  552 */         paramDoubleMetaphoneResult.appendPrimary('L');
/*      */       } else {
/*  554 */         paramDoubleMetaphoneResult.append('L');
/*      */       }
/*  556 */       paramInt += 2;
/*      */     } else {
/*  558 */       paramInt++;
/*  559 */       paramDoubleMetaphoneResult.append('L');
/*      */     }
/*  561 */     return paramInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private int handleP(String paramString, DoubleMetaphoneResult paramDoubleMetaphoneResult, int paramInt)
/*      */   {
/*  568 */     if (charAt(paramString, paramInt + 1) == 'H') {
/*  569 */       paramDoubleMetaphoneResult.append('F');
/*  570 */       paramInt += 2;
/*      */     } else {
/*  572 */       paramDoubleMetaphoneResult.append('P');
/*  573 */       paramInt = contains(paramString, paramInt + 1, 1, tmp46_40) ? paramInt + 2 : paramInt + 1;
/*      */     }
/*  575 */     return paramInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private int handleR(String paramString, DoubleMetaphoneResult paramDoubleMetaphoneResult, int paramInt, boolean paramBoolean)
/*      */   {
/*  583 */     if ((paramInt == paramString.length() - 1) && (!paramBoolean)) if (contains(paramString, paramInt - 2, 2, new String[] { "IE" })) if (!contains(paramString, paramInt - 4, 2, new String[] { "ME", "MA" }))
/*      */         {
/*      */ 
/*  586 */           paramDoubleMetaphoneResult.appendAlternate('R');
/*      */           break label78; }
/*  588 */     paramDoubleMetaphoneResult.append('R');
/*      */     label78:
/*  590 */     return charAt(paramString, paramInt + 1) == 'R' ? paramInt + 2 : paramInt + 1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private int handleS(String paramString, DoubleMetaphoneResult paramDoubleMetaphoneResult, int paramInt, boolean paramBoolean)
/*      */   {
/*  598 */     if (contains(paramString, paramInt - 1, 3, new String[] { "ISL", "YSL" }))
/*      */     {
/*  600 */       paramInt++;
/*  601 */     } else { if (paramInt == 0) if (contains(paramString, paramInt, 5, new String[] { "SUGAR" }))
/*      */         {
/*  603 */           paramDoubleMetaphoneResult.append('X', 'S');
/*  604 */           paramInt++; return paramInt; }
/*  605 */       if (contains(paramString, paramInt, 2, new String[] { "SH" })) {
/*  606 */         if (contains(paramString, paramInt + 1, 4, new String[] { "HEIM", "HOEK", "HOLM", "HOLZ" }))
/*      */         {
/*  608 */           paramDoubleMetaphoneResult.append('S');
/*      */         } else {
/*  610 */           paramDoubleMetaphoneResult.append('X');
/*      */         }
/*  612 */         paramInt += 2;
/*  613 */       } else { if (!contains(paramString, paramInt, 3, new String[] { "SIO", "SIA" })) { if (!contains(paramString, paramInt, 4, new String[] { "SIAN" })) {}
/*      */         } else {
/*  615 */           if (paramBoolean) {
/*  616 */             paramDoubleMetaphoneResult.append('S');
/*      */           } else {
/*  618 */             paramDoubleMetaphoneResult.append('S', 'X');
/*      */           }
/*  620 */           paramInt += 3; return paramInt; }
/*  621 */         if (paramInt == 0) { if (contains(paramString, paramInt + 1, 1, new String[] { "M", "N", "L", "W" })) {} } else { if (!contains(paramString, paramInt + 1, 1, new String[] { "Z" })) {
/*      */             break label326;
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*  627 */         paramDoubleMetaphoneResult.append('S', 'X');
/*  628 */         paramInt = contains(paramString, paramInt + 1, 1, tmp301_298) ? paramInt + 2 : paramInt + 1; return paramInt;
/*  629 */         label326: if (contains(paramString, paramInt, 2, new String[] { "SC" })) {
/*  630 */           paramInt = handleSC(paramString, paramDoubleMetaphoneResult, paramInt);
/*      */         } else {
/*  632 */           if (paramInt == paramString.length() - 1) if (contains(paramString, paramInt - 2, 2, new String[] { "AI", "OI" }))
/*      */             {
/*  634 */               paramDoubleMetaphoneResult.appendAlternate('S');
/*      */               break label408; }
/*  636 */           paramDoubleMetaphoneResult.append('S');
/*      */           label408:
/*  638 */           paramInt = contains(paramString, paramInt + 1, 1, tmp423_417) ? paramInt + 2 : paramInt + 1;
/*      */         } } }
/*  640 */     return paramInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private int handleSC(String paramString, DoubleMetaphoneResult paramDoubleMetaphoneResult, int paramInt)
/*      */   {
/*  647 */     if (charAt(paramString, paramInt + 2) == 'H')
/*      */     {
/*  649 */       if (contains(paramString, paramInt + 3, 2, new String[] { "OO", "ER", "EN", "UY", "ED", "EM" }))
/*      */       {
/*  651 */         if (contains(paramString, paramInt + 3, 2, new String[] { "ER", "EN" }))
/*      */         {
/*  653 */           paramDoubleMetaphoneResult.append("X", "SK");
/*      */         } else {
/*  655 */           paramDoubleMetaphoneResult.append("SK");
/*      */         }
/*      */       }
/*  658 */       else if ((paramInt == 0) && (!isVowel(charAt(paramString, 3))) && (charAt(paramString, 3) != 'W')) {
/*  659 */         paramDoubleMetaphoneResult.append('X', 'S');
/*      */       } else {
/*  661 */         paramDoubleMetaphoneResult.append('X');
/*      */       }
/*      */     }
/*  664 */     else if (contains(paramString, paramInt + 2, 1, new String[] { "I", "E", "Y" })) {
/*  665 */       paramDoubleMetaphoneResult.append('S');
/*      */     } else {
/*  667 */       paramDoubleMetaphoneResult.append("SK");
/*      */     }
/*  669 */     return paramInt + 3;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private int handleT(String paramString, DoubleMetaphoneResult paramDoubleMetaphoneResult, int paramInt)
/*      */   {
/*  676 */     if (contains(paramString, paramInt, 4, new String[] { "TION" })) {
/*  677 */       paramDoubleMetaphoneResult.append('X');
/*  678 */       paramInt += 3;
/*  679 */     } else if (contains(paramString, paramInt, 3, new String[] { "TIA", "TCH" })) {
/*  680 */       paramDoubleMetaphoneResult.append('X');
/*  681 */       paramInt += 3;
/*  682 */     } else { if (!contains(paramString, paramInt, 2, new String[] { "TH" })) { if (!contains(paramString, paramInt, 3, new String[] { "TTH" })) {}
/*  683 */       } else { if (!contains(paramString, paramInt + 2, 2, new String[] { "OM", "AM" })) if (!contains(paramString, 0, 4, new String[] { "VAN ", "VON " })) { if (!contains(paramString, 0, 3, new String[] { "SCH" })) {
/*      */               break label186;
/*      */             }
/*      */           }
/*  687 */         paramDoubleMetaphoneResult.append('T');
/*      */         break label194;
/*  689 */         label186: paramDoubleMetaphoneResult.append('0', 'T');
/*      */         label194:
/*  691 */         paramInt += 2;
/*      */         return paramInt; }
/*  693 */       paramDoubleMetaphoneResult.append('T');
/*  694 */       paramInt = contains(paramString, paramInt + 1, 1, tmp221_215) ? paramInt + 2 : paramInt + 1;
/*      */     }
/*  696 */     return paramInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private int handleW(String paramString, DoubleMetaphoneResult paramDoubleMetaphoneResult, int paramInt)
/*      */   {
/*  703 */     if (contains(paramString, paramInt, 2, new String[] { "WR" }))
/*      */     {
/*  705 */       paramDoubleMetaphoneResult.append('R');
/*  706 */       paramInt += 2;
/*      */     } else {
/*  708 */       if (paramInt == 0) if (!isVowel(charAt(paramString, paramInt + 1))) { if (!contains(paramString, paramInt, 2, new String[] { "WH" })) {}
/*      */         } else {
/*  710 */           if (isVowel(charAt(paramString, paramInt + 1)))
/*      */           {
/*  712 */             paramDoubleMetaphoneResult.append('A', 'F');
/*      */           }
/*      */           else {
/*  715 */             paramDoubleMetaphoneResult.append('A');
/*      */           }
/*  717 */           paramInt++; return paramInt; }
/*  718 */       if ((paramInt != paramString.length() - 1) || (!isVowel(charAt(paramString, paramInt - 1)))) if (!contains(paramString, paramInt - 1, 5, new String[] { "EWSKI", "EWSKY", "OWSKI", "OWSKY" })) { if (!contains(paramString, 0, 3, new String[] { "SCH" })) {
/*      */             break label202;
/*      */           }
/*      */         }
/*  722 */       paramDoubleMetaphoneResult.appendAlternate('F');
/*  723 */       paramInt++; return paramInt;
/*  724 */       label202: if (contains(paramString, paramInt, 4, new String[] { "WICZ", "WITZ" }))
/*      */       {
/*  726 */         paramDoubleMetaphoneResult.append("TS", "FX");
/*  727 */         paramInt += 4;
/*      */       } else {
/*  729 */         paramInt++;
/*      */       }
/*      */     }
/*  732 */     return paramInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private int handleX(String paramString, DoubleMetaphoneResult paramDoubleMetaphoneResult, int paramInt)
/*      */   {
/*  739 */     if (paramInt == 0) {
/*  740 */       paramDoubleMetaphoneResult.append('S');
/*  741 */       paramInt++;
/*      */     } else {
/*  743 */       if (paramInt == paramString.length() - 1) { if (!contains(paramString, paramInt - 3, 3, new String[] { "IAU", "EAU" })) { if (contains(paramString, paramInt - 2, 2, new String[] { "AU", "OU" })) {}
/*      */         }
/*      */       }
/*      */       else {
/*  747 */         paramDoubleMetaphoneResult.append("KS");
/*      */       }
/*  749 */       paramInt = contains(paramString, paramInt + 1, 1, tmp100_95) ? paramInt + 2 : paramInt + 1;
/*      */     }
/*  751 */     return paramInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private int handleZ(String paramString, DoubleMetaphoneResult paramDoubleMetaphoneResult, int paramInt, boolean paramBoolean)
/*      */   {
/*  759 */     if (charAt(paramString, paramInt + 1) == 'H')
/*      */     {
/*  761 */       paramDoubleMetaphoneResult.append('J');
/*  762 */       paramInt += 2;
/*      */     } else {
/*  764 */       if ((contains(paramString, paramInt + 1, 2, new String[] { "ZO", "ZI", "ZA" })) || ((paramBoolean) && (paramInt > 0) && (charAt(paramString, paramInt - 1) != 'T')))
/*      */       {
/*  766 */         paramDoubleMetaphoneResult.append("S", "TS");
/*      */       } else {
/*  768 */         paramDoubleMetaphoneResult.append('S');
/*      */       }
/*  770 */       paramInt = charAt(paramString, paramInt + 1) == 'Z' ? paramInt + 2 : paramInt + 1;
/*      */     }
/*  772 */     return paramInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean conditionC0(String paramString, int paramInt)
/*      */   {
/*  781 */     if (contains(paramString, paramInt, 4, new String[] { "CHIA" }))
/*  782 */       return true;
/*  783 */     if (paramInt <= 1)
/*  784 */       return false;
/*  785 */     if (isVowel(charAt(paramString, paramInt - 2)))
/*  786 */       return false;
/*  787 */     if (!contains(paramString, paramInt - 1, 3, new String[] { "ACH" })) {
/*  788 */       return false;
/*      */     }
/*  790 */     int i = charAt(paramString, paramInt + 2);
/*  791 */     if ((i == 73) || (i == 69)) {} return contains(paramString, paramInt - 2, 6, new String[] { "BACHER", "MACHER" });
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean conditionCH0(String paramString, int paramInt)
/*      */   {
/*  800 */     if (paramInt != 0)
/*  801 */       return false;
/*  802 */     if (!contains(paramString, paramInt + 1, 5, new String[] { "HARAC", "HARIS" })) if (!contains(paramString, paramInt + 1, 3, new String[] { "HOR", "HYM", "HIA", "HEM" }))
/*      */       {
/*  804 */         return false; }
/*  805 */     if (contains(paramString, 0, 5, new String[] { "CHORE" })) {
/*  806 */       return false;
/*      */     }
/*  808 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean conditionCH1(String paramString, int paramInt)
/*      */   {
/*  816 */     if (!contains(paramString, 0, 4, new String[] { "VAN ", "VON " })) if (!contains(paramString, 0, 3, new String[] { "SCH" })) if (!contains(paramString, paramInt - 2, 6, new String[] { "ORCHES", "ARCHIT", "ORCHID" })) if (contains(paramString, paramInt + 2, 1, new String[] { "T", "S" })) {} return ((contains(paramString, paramInt - 1, 1, new String[] { "A", "O", "U", "E" })) || (paramInt == 0)) && ((contains(paramString, paramInt + 2, 1, L_R_N_M_B_H_F_V_W_SPACE)) || (paramInt + 1 == paramString.length() - 1));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean conditionL0(String paramString, int paramInt)
/*      */   {
/*  827 */     if (paramInt == paramString.length() - 3) if (contains(paramString, paramInt - 1, 4, new String[] { "ILLO", "ILLA", "ALLE" }))
/*      */       {
/*  829 */         return true; }
/*  830 */     if (!contains(paramString, paramString.length() - 2, 2, new String[] { "AS", "OS" })) { if (!contains(paramString, paramString.length() - 1, 1, new String[] { "A", "O" })) {} } else if (contains(paramString, paramInt - 1, 4, new String[] { "ALLE" }))
/*      */     {
/*      */ 
/*  833 */       return true;
/*      */     }
/*  835 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean conditionM0(String paramString, int paramInt)
/*      */   {
/*  843 */     if (charAt(paramString, paramInt + 1) == 'M') {
/*  844 */       return true;
/*      */     }
/*  846 */     if (contains(paramString, paramInt - 1, 3, new String[] { "UMB" })) if (paramInt + 1 == paramString.length() - 1) {} return contains(paramString, paramInt + 2, 2, new String[] { "ER" });
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean isSlavoGermanic(String paramString)
/*      */   {
/*  857 */     return (paramString.indexOf('W') > -1) || (paramString.indexOf('K') > -1) || (paramString.indexOf("CZ") > -1) || (paramString.indexOf("WITZ") > -1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean isVowel(char paramChar)
/*      */   {
/*  865 */     return "AEIOUY".indexOf(paramChar) != -1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean isSilentStart(String paramString)
/*      */   {
/*  874 */     boolean bool = false;
/*  875 */     for (String str : SILENT_START) {
/*  876 */       if (paramString.startsWith(str)) {
/*  877 */         bool = true;
/*  878 */         break;
/*      */       }
/*      */     }
/*  881 */     return bool;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String cleanInput(String paramString)
/*      */   {
/*  888 */     if (paramString == null) {
/*  889 */       return null;
/*      */     }
/*  891 */     paramString = paramString.trim();
/*  892 */     if (paramString.length() == 0) {
/*  893 */       return null;
/*      */     }
/*  895 */     return paramString.toUpperCase(Locale.ENGLISH);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected char charAt(String paramString, int paramInt)
/*      */   {
/*  904 */     if ((paramInt < 0) || (paramInt >= paramString.length())) {
/*  905 */       return '\000';
/*      */     }
/*  907 */     return paramString.charAt(paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected static boolean contains(String paramString, int paramInt1, int paramInt2, String... paramVarArgs)
/*      */   {
/*  916 */     boolean bool = false;
/*  917 */     if ((paramInt1 >= 0) && (paramInt1 + paramInt2 <= paramString.length())) {
/*  918 */       String str1 = paramString.substring(paramInt1, paramInt1 + paramInt2);
/*      */       
/*  920 */       for (String str2 : paramVarArgs) {
/*  921 */         if (str1.equals(str2)) {
/*  922 */           bool = true;
/*  923 */           break;
/*      */         }
/*      */       }
/*      */     }
/*  927 */     return bool;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public class DoubleMetaphoneResult
/*      */   {
/*  937 */     private final StringBuilder primary = new StringBuilder(DoubleMetaphone.this.getMaxCodeLen());
/*  938 */     private final StringBuilder alternate = new StringBuilder(DoubleMetaphone.this.getMaxCodeLen());
/*      */     private final int maxLength;
/*      */     
/*      */     public DoubleMetaphoneResult(int paramInt) {
/*  942 */       this.maxLength = paramInt;
/*      */     }
/*      */     
/*      */     public void append(char paramChar) {
/*  946 */       appendPrimary(paramChar);
/*  947 */       appendAlternate(paramChar);
/*      */     }
/*      */     
/*      */     public void append(char paramChar1, char paramChar2) {
/*  951 */       appendPrimary(paramChar1);
/*  952 */       appendAlternate(paramChar2);
/*      */     }
/*      */     
/*      */     public void appendPrimary(char paramChar) {
/*  956 */       if (this.primary.length() < this.maxLength) {
/*  957 */         this.primary.append(paramChar);
/*      */       }
/*      */     }
/*      */     
/*      */     public void appendAlternate(char paramChar) {
/*  962 */       if (this.alternate.length() < this.maxLength) {
/*  963 */         this.alternate.append(paramChar);
/*      */       }
/*      */     }
/*      */     
/*      */     public void append(String paramString) {
/*  968 */       appendPrimary(paramString);
/*  969 */       appendAlternate(paramString);
/*      */     }
/*      */     
/*      */     public void append(String paramString1, String paramString2) {
/*  973 */       appendPrimary(paramString1);
/*  974 */       appendAlternate(paramString2);
/*      */     }
/*      */     
/*      */     public void appendPrimary(String paramString) {
/*  978 */       int i = this.maxLength - this.primary.length();
/*  979 */       if (paramString.length() <= i) {
/*  980 */         this.primary.append(paramString);
/*      */       } else {
/*  982 */         this.primary.append(paramString.substring(0, i));
/*      */       }
/*      */     }
/*      */     
/*      */     public void appendAlternate(String paramString) {
/*  987 */       int i = this.maxLength - this.alternate.length();
/*  988 */       if (paramString.length() <= i) {
/*  989 */         this.alternate.append(paramString);
/*      */       } else {
/*  991 */         this.alternate.append(paramString.substring(0, i));
/*      */       }
/*      */     }
/*      */     
/*      */     public String getPrimary() {
/*  996 */       return this.primary.toString();
/*      */     }
/*      */     
/*      */     public String getAlternate() {
/* 1000 */       return this.alternate.toString();
/*      */     }
/*      */     
/*      */     public boolean isComplete() {
/* 1004 */       return (this.primary.length() >= this.maxLength) && (this.alternate.length() >= this.maxLength);
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\codec\language\DoubleMetaphone.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */