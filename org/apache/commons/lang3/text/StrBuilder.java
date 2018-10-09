/*      */ package org.apache.commons.lang3.text;
/*      */ 
/*      */ import java.io.Reader;
/*      */ import java.io.Serializable;
/*      */ import java.io.Writer;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import org.apache.commons.lang3.ArrayUtils;
/*      */ import org.apache.commons.lang3.ObjectUtils;
/*      */ import org.apache.commons.lang3.SystemUtils;
/*      */ import org.apache.commons.lang3.builder.Builder;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class StrBuilder
/*      */   implements CharSequence, Appendable, Serializable, Builder<String>
/*      */ {
/*      */   static final int CAPACITY = 32;
/*      */   private static final long serialVersionUID = 7628716375283629643L;
/*      */   protected char[] buffer;
/*      */   protected int size;
/*      */   private String newLine;
/*      */   private String nullText;
/*      */   
/*      */   public StrBuilder()
/*      */   {
/*  103 */     this(32);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrBuilder(int paramInt)
/*      */   {
/*  113 */     if (paramInt <= 0) {
/*  114 */       paramInt = 32;
/*      */     }
/*  116 */     this.buffer = new char[paramInt];
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrBuilder(String paramString)
/*      */   {
/*  127 */     if (paramString == null) {
/*  128 */       this.buffer = new char[32];
/*      */     } else {
/*  130 */       this.buffer = new char[paramString.length() + 32];
/*  131 */       append(paramString);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getNewLineText()
/*      */   {
/*  142 */     return this.newLine;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrBuilder setNewLineText(String paramString)
/*      */   {
/*  152 */     this.newLine = paramString;
/*  153 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getNullText()
/*      */   {
/*  163 */     return this.nullText;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrBuilder setNullText(String paramString)
/*      */   {
/*  173 */     if ((paramString != null) && (paramString.isEmpty())) {
/*  174 */       paramString = null;
/*      */     }
/*  176 */     this.nullText = paramString;
/*  177 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int length()
/*      */   {
/*  188 */     return this.size;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrBuilder setLength(int paramInt)
/*      */   {
/*  200 */     if (paramInt < 0) {
/*  201 */       throw new StringIndexOutOfBoundsException(paramInt);
/*      */     }
/*  203 */     if (paramInt < this.size) {
/*  204 */       this.size = paramInt;
/*  205 */     } else if (paramInt > this.size) {
/*  206 */       ensureCapacity(paramInt);
/*  207 */       int i = this.size;
/*  208 */       int j = paramInt;
/*  209 */       this.size = paramInt;
/*  210 */       for (int k = i; k < j; k++) {
/*  211 */         this.buffer[k] = '\000';
/*      */       }
/*      */     }
/*  214 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int capacity()
/*      */   {
/*  224 */     return this.buffer.length;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrBuilder ensureCapacity(int paramInt)
/*      */   {
/*  234 */     if (paramInt > this.buffer.length) {
/*  235 */       char[] arrayOfChar = this.buffer;
/*  236 */       this.buffer = new char[paramInt * 2];
/*  237 */       System.arraycopy(arrayOfChar, 0, this.buffer, 0, this.size);
/*      */     }
/*  239 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrBuilder minimizeCapacity()
/*      */   {
/*  248 */     if (this.buffer.length > length()) {
/*  249 */       char[] arrayOfChar = this.buffer;
/*  250 */       this.buffer = new char[length()];
/*  251 */       System.arraycopy(arrayOfChar, 0, this.buffer, 0, this.size);
/*      */     }
/*  253 */     return this;
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
/*      */   public int size()
/*      */   {
/*  266 */     return this.size;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isEmpty()
/*      */   {
/*  278 */     return this.size == 0;
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
/*      */ 
/*      */   public StrBuilder clear()
/*      */   {
/*  293 */     this.size = 0;
/*  294 */     return this;
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
/*      */ 
/*      */   public char charAt(int paramInt)
/*      */   {
/*  309 */     if ((paramInt < 0) || (paramInt >= length())) {
/*  310 */       throw new StringIndexOutOfBoundsException(paramInt);
/*      */     }
/*  312 */     return this.buffer[paramInt];
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
/*      */   public StrBuilder setCharAt(int paramInt, char paramChar)
/*      */   {
/*  326 */     if ((paramInt < 0) || (paramInt >= length())) {
/*  327 */       throw new StringIndexOutOfBoundsException(paramInt);
/*      */     }
/*  329 */     this.buffer[paramInt] = paramChar;
/*  330 */     return this;
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
/*      */   public StrBuilder deleteCharAt(int paramInt)
/*      */   {
/*  343 */     if ((paramInt < 0) || (paramInt >= this.size)) {
/*  344 */       throw new StringIndexOutOfBoundsException(paramInt);
/*      */     }
/*  346 */     deleteImpl(paramInt, paramInt + 1, 1);
/*  347 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public char[] toCharArray()
/*      */   {
/*  357 */     if (this.size == 0) {
/*  358 */       return ArrayUtils.EMPTY_CHAR_ARRAY;
/*      */     }
/*  360 */     char[] arrayOfChar = new char[this.size];
/*  361 */     System.arraycopy(this.buffer, 0, arrayOfChar, 0, this.size);
/*  362 */     return arrayOfChar;
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
/*      */   public char[] toCharArray(int paramInt1, int paramInt2)
/*      */   {
/*  376 */     paramInt2 = validateRange(paramInt1, paramInt2);
/*  377 */     int i = paramInt2 - paramInt1;
/*  378 */     if (i == 0) {
/*  379 */       return ArrayUtils.EMPTY_CHAR_ARRAY;
/*      */     }
/*  381 */     char[] arrayOfChar = new char[i];
/*  382 */     System.arraycopy(this.buffer, paramInt1, arrayOfChar, 0, i);
/*  383 */     return arrayOfChar;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public char[] getChars(char[] paramArrayOfChar)
/*      */   {
/*  393 */     int i = length();
/*  394 */     if ((paramArrayOfChar == null) || (paramArrayOfChar.length < i)) {
/*  395 */       paramArrayOfChar = new char[i];
/*      */     }
/*  397 */     System.arraycopy(this.buffer, 0, paramArrayOfChar, 0, i);
/*  398 */     return paramArrayOfChar;
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
/*      */   public void getChars(int paramInt1, int paramInt2, char[] paramArrayOfChar, int paramInt3)
/*      */   {
/*  412 */     if (paramInt1 < 0) {
/*  413 */       throw new StringIndexOutOfBoundsException(paramInt1);
/*      */     }
/*  415 */     if ((paramInt2 < 0) || (paramInt2 > length())) {
/*  416 */       throw new StringIndexOutOfBoundsException(paramInt2);
/*      */     }
/*  418 */     if (paramInt1 > paramInt2) {
/*  419 */       throw new StringIndexOutOfBoundsException("end < start");
/*      */     }
/*  421 */     System.arraycopy(this.buffer, paramInt1, paramArrayOfChar, paramInt3, paramInt2 - paramInt1);
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
/*      */   public StrBuilder appendNewLine()
/*      */   {
/*  435 */     if (this.newLine == null) {
/*  436 */       append(SystemUtils.LINE_SEPARATOR);
/*  437 */       return this;
/*      */     }
/*  439 */     return append(this.newLine);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrBuilder appendNull()
/*      */   {
/*  448 */     if (this.nullText == null) {
/*  449 */       return this;
/*      */     }
/*  451 */     return append(this.nullText);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrBuilder append(Object paramObject)
/*      */   {
/*  462 */     if (paramObject == null) {
/*  463 */       return appendNull();
/*      */     }
/*  465 */     return append(paramObject.toString());
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
/*      */   public StrBuilder append(CharSequence paramCharSequence)
/*      */   {
/*  478 */     if (paramCharSequence == null) {
/*  479 */       return appendNull();
/*      */     }
/*  481 */     return append(paramCharSequence.toString());
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
/*      */ 
/*      */   public StrBuilder append(CharSequence paramCharSequence, int paramInt1, int paramInt2)
/*      */   {
/*  496 */     if (paramCharSequence == null) {
/*  497 */       return appendNull();
/*      */     }
/*  499 */     return append(paramCharSequence.toString(), paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrBuilder append(String paramString)
/*      */   {
/*  510 */     if (paramString == null) {
/*  511 */       return appendNull();
/*      */     }
/*  513 */     int i = paramString.length();
/*  514 */     if (i > 0) {
/*  515 */       int j = length();
/*  516 */       ensureCapacity(j + i);
/*  517 */       paramString.getChars(0, i, this.buffer, j);
/*  518 */       this.size += i;
/*      */     }
/*  520 */     return this;
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
/*      */   public StrBuilder append(String paramString, int paramInt1, int paramInt2)
/*      */   {
/*  534 */     if (paramString == null) {
/*  535 */       return appendNull();
/*      */     }
/*  537 */     if ((paramInt1 < 0) || (paramInt1 > paramString.length())) {
/*  538 */       throw new StringIndexOutOfBoundsException("startIndex must be valid");
/*      */     }
/*  540 */     if ((paramInt2 < 0) || (paramInt1 + paramInt2 > paramString.length())) {
/*  541 */       throw new StringIndexOutOfBoundsException("length must be valid");
/*      */     }
/*  543 */     if (paramInt2 > 0) {
/*  544 */       int i = length();
/*  545 */       ensureCapacity(i + paramInt2);
/*  546 */       paramString.getChars(paramInt1, paramInt1 + paramInt2, this.buffer, i);
/*  547 */       this.size += paramInt2;
/*      */     }
/*  549 */     return this;
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
/*      */   public StrBuilder append(String paramString, Object... paramVarArgs)
/*      */   {
/*  562 */     return append(String.format(paramString, paramVarArgs));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrBuilder append(StringBuffer paramStringBuffer)
/*      */   {
/*  573 */     if (paramStringBuffer == null) {
/*  574 */       return appendNull();
/*      */     }
/*  576 */     int i = paramStringBuffer.length();
/*  577 */     if (i > 0) {
/*  578 */       int j = length();
/*  579 */       ensureCapacity(j + i);
/*  580 */       paramStringBuffer.getChars(0, i, this.buffer, j);
/*  581 */       this.size += i;
/*      */     }
/*  583 */     return this;
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
/*      */   public StrBuilder append(StringBuffer paramStringBuffer, int paramInt1, int paramInt2)
/*      */   {
/*  596 */     if (paramStringBuffer == null) {
/*  597 */       return appendNull();
/*      */     }
/*  599 */     if ((paramInt1 < 0) || (paramInt1 > paramStringBuffer.length())) {
/*  600 */       throw new StringIndexOutOfBoundsException("startIndex must be valid");
/*      */     }
/*  602 */     if ((paramInt2 < 0) || (paramInt1 + paramInt2 > paramStringBuffer.length())) {
/*  603 */       throw new StringIndexOutOfBoundsException("length must be valid");
/*      */     }
/*  605 */     if (paramInt2 > 0) {
/*  606 */       int i = length();
/*  607 */       ensureCapacity(i + paramInt2);
/*  608 */       paramStringBuffer.getChars(paramInt1, paramInt1 + paramInt2, this.buffer, i);
/*  609 */       this.size += paramInt2;
/*      */     }
/*  611 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrBuilder append(StringBuilder paramStringBuilder)
/*      */   {
/*  623 */     if (paramStringBuilder == null) {
/*  624 */       return appendNull();
/*      */     }
/*  626 */     int i = paramStringBuilder.length();
/*  627 */     if (i > 0) {
/*  628 */       int j = length();
/*  629 */       ensureCapacity(j + i);
/*  630 */       paramStringBuilder.getChars(0, i, this.buffer, j);
/*  631 */       this.size += i;
/*      */     }
/*  633 */     return this;
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
/*      */   public StrBuilder append(StringBuilder paramStringBuilder, int paramInt1, int paramInt2)
/*      */   {
/*  647 */     if (paramStringBuilder == null) {
/*  648 */       return appendNull();
/*      */     }
/*  650 */     if ((paramInt1 < 0) || (paramInt1 > paramStringBuilder.length())) {
/*  651 */       throw new StringIndexOutOfBoundsException("startIndex must be valid");
/*      */     }
/*  653 */     if ((paramInt2 < 0) || (paramInt1 + paramInt2 > paramStringBuilder.length())) {
/*  654 */       throw new StringIndexOutOfBoundsException("length must be valid");
/*      */     }
/*  656 */     if (paramInt2 > 0) {
/*  657 */       int i = length();
/*  658 */       ensureCapacity(i + paramInt2);
/*  659 */       paramStringBuilder.getChars(paramInt1, paramInt1 + paramInt2, this.buffer, i);
/*  660 */       this.size += paramInt2;
/*      */     }
/*  662 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrBuilder append(StrBuilder paramStrBuilder)
/*      */   {
/*  673 */     if (paramStrBuilder == null) {
/*  674 */       return appendNull();
/*      */     }
/*  676 */     int i = paramStrBuilder.length();
/*  677 */     if (i > 0) {
/*  678 */       int j = length();
/*  679 */       ensureCapacity(j + i);
/*  680 */       System.arraycopy(paramStrBuilder.buffer, 0, this.buffer, j, i);
/*  681 */       this.size += i;
/*      */     }
/*  683 */     return this;
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
/*      */   public StrBuilder append(StrBuilder paramStrBuilder, int paramInt1, int paramInt2)
/*      */   {
/*  696 */     if (paramStrBuilder == null) {
/*  697 */       return appendNull();
/*      */     }
/*  699 */     if ((paramInt1 < 0) || (paramInt1 > paramStrBuilder.length())) {
/*  700 */       throw new StringIndexOutOfBoundsException("startIndex must be valid");
/*      */     }
/*  702 */     if ((paramInt2 < 0) || (paramInt1 + paramInt2 > paramStrBuilder.length())) {
/*  703 */       throw new StringIndexOutOfBoundsException("length must be valid");
/*      */     }
/*  705 */     if (paramInt2 > 0) {
/*  706 */       int i = length();
/*  707 */       ensureCapacity(i + paramInt2);
/*  708 */       paramStrBuilder.getChars(paramInt1, paramInt1 + paramInt2, this.buffer, i);
/*  709 */       this.size += paramInt2;
/*      */     }
/*  711 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrBuilder append(char[] paramArrayOfChar)
/*      */   {
/*  722 */     if (paramArrayOfChar == null) {
/*  723 */       return appendNull();
/*      */     }
/*  725 */     int i = paramArrayOfChar.length;
/*  726 */     if (i > 0) {
/*  727 */       int j = length();
/*  728 */       ensureCapacity(j + i);
/*  729 */       System.arraycopy(paramArrayOfChar, 0, this.buffer, j, i);
/*  730 */       this.size += i;
/*      */     }
/*  732 */     return this;
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
/*      */   public StrBuilder append(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*      */   {
/*  745 */     if (paramArrayOfChar == null) {
/*  746 */       return appendNull();
/*      */     }
/*  748 */     if ((paramInt1 < 0) || (paramInt1 > paramArrayOfChar.length)) {
/*  749 */       throw new StringIndexOutOfBoundsException("Invalid startIndex: " + paramInt2);
/*      */     }
/*  751 */     if ((paramInt2 < 0) || (paramInt1 + paramInt2 > paramArrayOfChar.length)) {
/*  752 */       throw new StringIndexOutOfBoundsException("Invalid length: " + paramInt2);
/*      */     }
/*  754 */     if (paramInt2 > 0) {
/*  755 */       int i = length();
/*  756 */       ensureCapacity(i + paramInt2);
/*  757 */       System.arraycopy(paramArrayOfChar, paramInt1, this.buffer, i, paramInt2);
/*  758 */       this.size += paramInt2;
/*      */     }
/*  760 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrBuilder append(boolean paramBoolean)
/*      */   {
/*  770 */     if (paramBoolean) {
/*  771 */       ensureCapacity(this.size + 4);
/*  772 */       this.buffer[(this.size++)] = 't';
/*  773 */       this.buffer[(this.size++)] = 'r';
/*  774 */       this.buffer[(this.size++)] = 'u';
/*  775 */       this.buffer[(this.size++)] = 'e';
/*      */     } else {
/*  777 */       ensureCapacity(this.size + 5);
/*  778 */       this.buffer[(this.size++)] = 'f';
/*  779 */       this.buffer[(this.size++)] = 'a';
/*  780 */       this.buffer[(this.size++)] = 'l';
/*  781 */       this.buffer[(this.size++)] = 's';
/*  782 */       this.buffer[(this.size++)] = 'e';
/*      */     }
/*  784 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrBuilder append(char paramChar)
/*      */   {
/*  796 */     int i = length();
/*  797 */     ensureCapacity(i + 1);
/*  798 */     this.buffer[(this.size++)] = paramChar;
/*  799 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrBuilder append(int paramInt)
/*      */   {
/*  809 */     return append(String.valueOf(paramInt));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrBuilder append(long paramLong)
/*      */   {
/*  819 */     return append(String.valueOf(paramLong));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrBuilder append(float paramFloat)
/*      */   {
/*  829 */     return append(String.valueOf(paramFloat));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrBuilder append(double paramDouble)
/*      */   {
/*  839 */     return append(String.valueOf(paramDouble));
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
/*      */   public StrBuilder appendln(Object paramObject)
/*      */   {
/*  852 */     return append(paramObject).appendNewLine();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrBuilder appendln(String paramString)
/*      */   {
/*  864 */     return append(paramString).appendNewLine();
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
/*      */   public StrBuilder appendln(String paramString, int paramInt1, int paramInt2)
/*      */   {
/*  878 */     return append(paramString, paramInt1, paramInt2).appendNewLine();
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
/*      */   public StrBuilder appendln(String paramString, Object... paramVarArgs)
/*      */   {
/*  891 */     return append(paramString, paramVarArgs).appendNewLine();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrBuilder appendln(StringBuffer paramStringBuffer)
/*      */   {
/*  903 */     return append(paramStringBuffer).appendNewLine();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrBuilder appendln(StringBuilder paramStringBuilder)
/*      */   {
/*  915 */     return append(paramStringBuilder).appendNewLine();
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
/*      */   public StrBuilder appendln(StringBuilder paramStringBuilder, int paramInt1, int paramInt2)
/*      */   {
/*  929 */     return append(paramStringBuilder, paramInt1, paramInt2).appendNewLine();
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
/*      */   public StrBuilder appendln(StringBuffer paramStringBuffer, int paramInt1, int paramInt2)
/*      */   {
/*  943 */     return append(paramStringBuffer, paramInt1, paramInt2).appendNewLine();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrBuilder appendln(StrBuilder paramStrBuilder)
/*      */   {
/*  955 */     return append(paramStrBuilder).appendNewLine();
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
/*      */   public StrBuilder appendln(StrBuilder paramStrBuilder, int paramInt1, int paramInt2)
/*      */   {
/*  969 */     return append(paramStrBuilder, paramInt1, paramInt2).appendNewLine();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrBuilder appendln(char[] paramArrayOfChar)
/*      */   {
/*  981 */     return append(paramArrayOfChar).appendNewLine();
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
/*      */   public StrBuilder appendln(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*      */   {
/*  995 */     return append(paramArrayOfChar, paramInt1, paramInt2).appendNewLine();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrBuilder appendln(boolean paramBoolean)
/*      */   {
/* 1006 */     return append(paramBoolean).appendNewLine();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrBuilder appendln(char paramChar)
/*      */   {
/* 1017 */     return append(paramChar).appendNewLine();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrBuilder appendln(int paramInt)
/*      */   {
/* 1028 */     return append(paramInt).appendNewLine();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrBuilder appendln(long paramLong)
/*      */   {
/* 1039 */     return append(paramLong).appendNewLine();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrBuilder appendln(float paramFloat)
/*      */   {
/* 1050 */     return append(paramFloat).appendNewLine();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrBuilder appendln(double paramDouble)
/*      */   {
/* 1061 */     return append(paramDouble).appendNewLine();
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
/*      */ 
/*      */   public <T> StrBuilder appendAll(T... paramVarArgs)
/*      */   {
/* 1076 */     if ((paramVarArgs != null) && (paramVarArgs.length > 0)) {
/* 1077 */       for (T ? : paramVarArgs) {
/* 1078 */         append(?);
/*      */       }
/*      */     }
/* 1081 */     return this;
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
/*      */   public StrBuilder appendAll(Iterable<?> paramIterable)
/*      */   {
/* 1094 */     if (paramIterable != null) {
/* 1095 */       for (Object localObject : paramIterable) {
/* 1096 */         append(localObject);
/*      */       }
/*      */     }
/* 1099 */     return this;
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
/*      */   public StrBuilder appendAll(Iterator<?> paramIterator)
/*      */   {
/* 1112 */     if (paramIterator != null) {
/* 1113 */       while (paramIterator.hasNext()) {
/* 1114 */         append(paramIterator.next());
/*      */       }
/*      */     }
/* 1117 */     return this;
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
/*      */ 
/*      */   public StrBuilder appendWithSeparators(Object[] paramArrayOfObject, String paramString)
/*      */   {
/* 1132 */     if ((paramArrayOfObject != null) && (paramArrayOfObject.length > 0))
/*      */     {
/* 1134 */       String str = ObjectUtils.toString(paramString);
/* 1135 */       append(paramArrayOfObject[0]);
/* 1136 */       for (int i = 1; i < paramArrayOfObject.length; i++) {
/* 1137 */         append(str);
/* 1138 */         append(paramArrayOfObject[i]);
/*      */       }
/*      */     }
/* 1141 */     return this;
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
/*      */   public StrBuilder appendWithSeparators(Iterable<?> paramIterable, String paramString)
/*      */   {
/* 1155 */     if (paramIterable != null)
/*      */     {
/* 1157 */       String str = ObjectUtils.toString(paramString);
/* 1158 */       Iterator localIterator = paramIterable.iterator();
/* 1159 */       while (localIterator.hasNext()) {
/* 1160 */         append(localIterator.next());
/* 1161 */         if (localIterator.hasNext()) {
/* 1162 */           append(str);
/*      */         }
/*      */       }
/*      */     }
/* 1166 */     return this;
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
/*      */   public StrBuilder appendWithSeparators(Iterator<?> paramIterator, String paramString)
/*      */   {
/* 1180 */     if (paramIterator != null)
/*      */     {
/* 1182 */       String str = ObjectUtils.toString(paramString);
/* 1183 */       while (paramIterator.hasNext()) {
/* 1184 */         append(paramIterator.next());
/* 1185 */         if (paramIterator.hasNext()) {
/* 1186 */           append(str);
/*      */         }
/*      */       }
/*      */     }
/* 1190 */     return this;
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
/*      */   public StrBuilder appendSeparator(String paramString)
/*      */   {
/* 1215 */     return appendSeparator(paramString, null);
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
/*      */   public StrBuilder appendSeparator(String paramString1, String paramString2)
/*      */   {
/* 1246 */     String str = isEmpty() ? paramString2 : paramString1;
/* 1247 */     if (str != null) {
/* 1248 */       append(str);
/*      */     }
/* 1250 */     return this;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrBuilder appendSeparator(char paramChar)
/*      */   {
/* 1273 */     if (size() > 0) {
/* 1274 */       append(paramChar);
/*      */     }
/* 1276 */     return this;
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
/*      */ 
/*      */   public StrBuilder appendSeparator(char paramChar1, char paramChar2)
/*      */   {
/* 1291 */     if (size() > 0) {
/* 1292 */       append(paramChar1);
/*      */     } else {
/* 1294 */       append(paramChar2);
/*      */     }
/* 1296 */     return this;
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
/*      */   public StrBuilder appendSeparator(String paramString, int paramInt)
/*      */   {
/* 1321 */     if ((paramString != null) && (paramInt > 0)) {
/* 1322 */       append(paramString);
/*      */     }
/* 1324 */     return this;
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
/*      */   public StrBuilder appendSeparator(char paramChar, int paramInt)
/*      */   {
/* 1349 */     if (paramInt > 0) {
/* 1350 */       append(paramChar);
/*      */     }
/* 1352 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrBuilder appendPadding(int paramInt, char paramChar)
/*      */   {
/* 1364 */     if (paramInt >= 0) {
/* 1365 */       ensureCapacity(this.size + paramInt);
/* 1366 */       for (int i = 0; i < paramInt; i++) {
/* 1367 */         this.buffer[(this.size++)] = paramChar;
/*      */       }
/*      */     }
/* 1370 */     return this;
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
/*      */ 
/*      */ 
/*      */   public StrBuilder appendFixedWidthPadLeft(Object paramObject, int paramInt, char paramChar)
/*      */   {
/* 1386 */     if (paramInt > 0) {
/* 1387 */       ensureCapacity(this.size + paramInt);
/* 1388 */       String str = paramObject == null ? getNullText() : paramObject.toString();
/* 1389 */       if (str == null) {
/* 1390 */         str = "";
/*      */       }
/* 1392 */       int i = str.length();
/* 1393 */       if (i >= paramInt) {
/* 1394 */         str.getChars(i - paramInt, i, this.buffer, this.size);
/*      */       } else {
/* 1396 */         int j = paramInt - i;
/* 1397 */         for (int k = 0; k < j; k++) {
/* 1398 */           this.buffer[(this.size + k)] = paramChar;
/*      */         }
/* 1400 */         str.getChars(0, i, this.buffer, this.size + j);
/*      */       }
/* 1402 */       this.size += paramInt;
/*      */     }
/* 1404 */     return this;
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
/*      */   public StrBuilder appendFixedWidthPadLeft(int paramInt1, int paramInt2, char paramChar)
/*      */   {
/* 1418 */     return appendFixedWidthPadLeft(String.valueOf(paramInt1), paramInt2, paramChar);
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
/*      */ 
/*      */   public StrBuilder appendFixedWidthPadRight(Object paramObject, int paramInt, char paramChar)
/*      */   {
/* 1433 */     if (paramInt > 0) {
/* 1434 */       ensureCapacity(this.size + paramInt);
/* 1435 */       String str = paramObject == null ? getNullText() : paramObject.toString();
/* 1436 */       if (str == null) {
/* 1437 */         str = "";
/*      */       }
/* 1439 */       int i = str.length();
/* 1440 */       if (i >= paramInt) {
/* 1441 */         str.getChars(0, paramInt, this.buffer, this.size);
/*      */       } else {
/* 1443 */         int j = paramInt - i;
/* 1444 */         str.getChars(0, i, this.buffer, this.size);
/* 1445 */         for (int k = 0; k < j; k++) {
/* 1446 */           this.buffer[(this.size + i + k)] = paramChar;
/*      */         }
/*      */       }
/* 1449 */       this.size += paramInt;
/*      */     }
/* 1451 */     return this;
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
/*      */   public StrBuilder appendFixedWidthPadRight(int paramInt1, int paramInt2, char paramChar)
/*      */   {
/* 1465 */     return appendFixedWidthPadRight(String.valueOf(paramInt1), paramInt2, paramChar);
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
/*      */   public StrBuilder insert(int paramInt, Object paramObject)
/*      */   {
/* 1479 */     if (paramObject == null) {
/* 1480 */       return insert(paramInt, this.nullText);
/*      */     }
/* 1482 */     return insert(paramInt, paramObject.toString());
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
/*      */   public StrBuilder insert(int paramInt, String paramString)
/*      */   {
/* 1495 */     validateIndex(paramInt);
/* 1496 */     if (paramString == null) {
/* 1497 */       paramString = this.nullText;
/*      */     }
/* 1499 */     if (paramString != null) {
/* 1500 */       int i = paramString.length();
/* 1501 */       if (i > 0) {
/* 1502 */         int j = this.size + i;
/* 1503 */         ensureCapacity(j);
/* 1504 */         System.arraycopy(this.buffer, paramInt, this.buffer, paramInt + i, this.size - paramInt);
/* 1505 */         this.size = j;
/* 1506 */         paramString.getChars(0, i, this.buffer, paramInt);
/*      */       }
/*      */     }
/* 1509 */     return this;
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
/*      */   public StrBuilder insert(int paramInt, char[] paramArrayOfChar)
/*      */   {
/* 1522 */     validateIndex(paramInt);
/* 1523 */     if (paramArrayOfChar == null) {
/* 1524 */       return insert(paramInt, this.nullText);
/*      */     }
/* 1526 */     int i = paramArrayOfChar.length;
/* 1527 */     if (i > 0) {
/* 1528 */       ensureCapacity(this.size + i);
/* 1529 */       System.arraycopy(this.buffer, paramInt, this.buffer, paramInt + i, this.size - paramInt);
/* 1530 */       System.arraycopy(paramArrayOfChar, 0, this.buffer, paramInt, i);
/* 1531 */       this.size += i;
/*      */     }
/* 1533 */     return this;
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
/*      */ 
/*      */   public StrBuilder insert(int paramInt1, char[] paramArrayOfChar, int paramInt2, int paramInt3)
/*      */   {
/* 1548 */     validateIndex(paramInt1);
/* 1549 */     if (paramArrayOfChar == null) {
/* 1550 */       return insert(paramInt1, this.nullText);
/*      */     }
/* 1552 */     if ((paramInt2 < 0) || (paramInt2 > paramArrayOfChar.length)) {
/* 1553 */       throw new StringIndexOutOfBoundsException("Invalid offset: " + paramInt2);
/*      */     }
/* 1555 */     if ((paramInt3 < 0) || (paramInt2 + paramInt3 > paramArrayOfChar.length)) {
/* 1556 */       throw new StringIndexOutOfBoundsException("Invalid length: " + paramInt3);
/*      */     }
/* 1558 */     if (paramInt3 > 0) {
/* 1559 */       ensureCapacity(this.size + paramInt3);
/* 1560 */       System.arraycopy(this.buffer, paramInt1, this.buffer, paramInt1 + paramInt3, this.size - paramInt1);
/* 1561 */       System.arraycopy(paramArrayOfChar, paramInt2, this.buffer, paramInt1, paramInt3);
/* 1562 */       this.size += paramInt3;
/*      */     }
/* 1564 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrBuilder insert(int paramInt, boolean paramBoolean)
/*      */   {
/* 1576 */     validateIndex(paramInt);
/* 1577 */     if (paramBoolean) {
/* 1578 */       ensureCapacity(this.size + 4);
/* 1579 */       System.arraycopy(this.buffer, paramInt, this.buffer, paramInt + 4, this.size - paramInt);
/* 1580 */       this.buffer[(paramInt++)] = 't';
/* 1581 */       this.buffer[(paramInt++)] = 'r';
/* 1582 */       this.buffer[(paramInt++)] = 'u';
/* 1583 */       this.buffer[paramInt] = 'e';
/* 1584 */       this.size += 4;
/*      */     } else {
/* 1586 */       ensureCapacity(this.size + 5);
/* 1587 */       System.arraycopy(this.buffer, paramInt, this.buffer, paramInt + 5, this.size - paramInt);
/* 1588 */       this.buffer[(paramInt++)] = 'f';
/* 1589 */       this.buffer[(paramInt++)] = 'a';
/* 1590 */       this.buffer[(paramInt++)] = 'l';
/* 1591 */       this.buffer[(paramInt++)] = 's';
/* 1592 */       this.buffer[paramInt] = 'e';
/* 1593 */       this.size += 5;
/*      */     }
/* 1595 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrBuilder insert(int paramInt, char paramChar)
/*      */   {
/* 1607 */     validateIndex(paramInt);
/* 1608 */     ensureCapacity(this.size + 1);
/* 1609 */     System.arraycopy(this.buffer, paramInt, this.buffer, paramInt + 1, this.size - paramInt);
/* 1610 */     this.buffer[paramInt] = paramChar;
/* 1611 */     this.size += 1;
/* 1612 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrBuilder insert(int paramInt1, int paramInt2)
/*      */   {
/* 1624 */     return insert(paramInt1, String.valueOf(paramInt2));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrBuilder insert(int paramInt, long paramLong)
/*      */   {
/* 1636 */     return insert(paramInt, String.valueOf(paramLong));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrBuilder insert(int paramInt, float paramFloat)
/*      */   {
/* 1648 */     return insert(paramInt, String.valueOf(paramFloat));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrBuilder insert(int paramInt, double paramDouble)
/*      */   {
/* 1660 */     return insert(paramInt, String.valueOf(paramDouble));
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
/*      */   private void deleteImpl(int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/* 1673 */     System.arraycopy(this.buffer, paramInt2, this.buffer, paramInt1, this.size - paramInt2);
/* 1674 */     this.size -= paramInt3;
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
/*      */   public StrBuilder delete(int paramInt1, int paramInt2)
/*      */   {
/* 1687 */     paramInt2 = validateRange(paramInt1, paramInt2);
/* 1688 */     int i = paramInt2 - paramInt1;
/* 1689 */     if (i > 0) {
/* 1690 */       deleteImpl(paramInt1, paramInt2, i);
/*      */     }
/* 1692 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrBuilder deleteAll(char paramChar)
/*      */   {
/* 1703 */     for (int i = 0; i < this.size; i++) {
/* 1704 */       if (this.buffer[i] == paramChar) {
/* 1705 */         int j = i;
/* 1706 */         for (;;) { i++; if (i < this.size) {
/* 1707 */             if (this.buffer[i] != paramChar)
/*      */               break;
/*      */           }
/*      */         }
/* 1711 */         int k = i - j;
/* 1712 */         deleteImpl(j, i, k);
/* 1713 */         i -= k;
/*      */       }
/*      */     }
/* 1716 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrBuilder deleteFirst(char paramChar)
/*      */   {
/* 1726 */     for (int i = 0; i < this.size; i++) {
/* 1727 */       if (this.buffer[i] == paramChar) {
/* 1728 */         deleteImpl(i, i + 1, 1);
/* 1729 */         break;
/*      */       }
/*      */     }
/* 1732 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrBuilder deleteAll(String paramString)
/*      */   {
/* 1743 */     int i = paramString == null ? 0 : paramString.length();
/* 1744 */     if (i > 0) {
/* 1745 */       int j = indexOf(paramString, 0);
/* 1746 */       while (j >= 0) {
/* 1747 */         deleteImpl(j, j + i, i);
/* 1748 */         j = indexOf(paramString, j);
/*      */       }
/*      */     }
/* 1751 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrBuilder deleteFirst(String paramString)
/*      */   {
/* 1761 */     int i = paramString == null ? 0 : paramString.length();
/* 1762 */     if (i > 0) {
/* 1763 */       int j = indexOf(paramString, 0);
/* 1764 */       if (j >= 0) {
/* 1765 */         deleteImpl(j, j + i, i);
/*      */       }
/*      */     }
/* 1768 */     return this;
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
/*      */ 
/*      */   public StrBuilder deleteAll(StrMatcher paramStrMatcher)
/*      */   {
/* 1783 */     return replace(paramStrMatcher, null, 0, this.size, -1);
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
/*      */   public StrBuilder deleteFirst(StrMatcher paramStrMatcher)
/*      */   {
/* 1797 */     return replace(paramStrMatcher, null, 0, this.size, 1);
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
/*      */ 
/*      */   private void replaceImpl(int paramInt1, int paramInt2, int paramInt3, String paramString, int paramInt4)
/*      */   {
/* 1812 */     int i = this.size - paramInt3 + paramInt4;
/* 1813 */     if (paramInt4 != paramInt3) {
/* 1814 */       ensureCapacity(i);
/* 1815 */       System.arraycopy(this.buffer, paramInt2, this.buffer, paramInt1 + paramInt4, this.size - paramInt2);
/* 1816 */       this.size = i;
/*      */     }
/* 1818 */     if (paramInt4 > 0) {
/* 1819 */       paramString.getChars(0, paramInt4, this.buffer, paramInt1);
/*      */     }
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
/*      */ 
/*      */   public StrBuilder replace(int paramInt1, int paramInt2, String paramString)
/*      */   {
/* 1835 */     paramInt2 = validateRange(paramInt1, paramInt2);
/* 1836 */     int i = paramString == null ? 0 : paramString.length();
/* 1837 */     replaceImpl(paramInt1, paramInt2, paramInt2 - paramInt1, paramString, i);
/* 1838 */     return this;
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
/*      */   public StrBuilder replaceAll(char paramChar1, char paramChar2)
/*      */   {
/* 1851 */     if (paramChar1 != paramChar2) {
/* 1852 */       for (int i = 0; i < this.size; i++) {
/* 1853 */         if (this.buffer[i] == paramChar1) {
/* 1854 */           this.buffer[i] = paramChar2;
/*      */         }
/*      */       }
/*      */     }
/* 1858 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrBuilder replaceFirst(char paramChar1, char paramChar2)
/*      */   {
/* 1870 */     if (paramChar1 != paramChar2) {
/* 1871 */       for (int i = 0; i < this.size; i++) {
/* 1872 */         if (this.buffer[i] == paramChar1) {
/* 1873 */           this.buffer[i] = paramChar2;
/* 1874 */           break;
/*      */         }
/*      */       }
/*      */     }
/* 1878 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrBuilder replaceAll(String paramString1, String paramString2)
/*      */   {
/* 1890 */     int i = paramString1 == null ? 0 : paramString1.length();
/* 1891 */     if (i > 0) {
/* 1892 */       int j = paramString2 == null ? 0 : paramString2.length();
/* 1893 */       int k = indexOf(paramString1, 0);
/* 1894 */       while (k >= 0) {
/* 1895 */         replaceImpl(k, k + i, i, paramString2, j);
/* 1896 */         k = indexOf(paramString1, k + j);
/*      */       }
/*      */     }
/* 1899 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrBuilder replaceFirst(String paramString1, String paramString2)
/*      */   {
/* 1910 */     int i = paramString1 == null ? 0 : paramString1.length();
/* 1911 */     if (i > 0) {
/* 1912 */       int j = indexOf(paramString1, 0);
/* 1913 */       if (j >= 0) {
/* 1914 */         int k = paramString2 == null ? 0 : paramString2.length();
/* 1915 */         replaceImpl(j, j + i, i, paramString2, k);
/*      */       }
/*      */     }
/* 1918 */     return this;
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
/*      */ 
/*      */ 
/*      */   public StrBuilder replaceAll(StrMatcher paramStrMatcher, String paramString)
/*      */   {
/* 1934 */     return replace(paramStrMatcher, paramString, 0, this.size, -1);
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
/*      */ 
/*      */   public StrBuilder replaceFirst(StrMatcher paramStrMatcher, String paramString)
/*      */   {
/* 1949 */     return replace(paramStrMatcher, paramString, 0, this.size, 1);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrBuilder replace(StrMatcher paramStrMatcher, String paramString, int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/* 1972 */     paramInt2 = validateRange(paramInt1, paramInt2);
/* 1973 */     return replaceImpl(paramStrMatcher, paramString, paramInt1, paramInt2, paramInt3);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private StrBuilder replaceImpl(StrMatcher paramStrMatcher, String paramString, int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/* 1994 */     if ((paramStrMatcher == null) || (this.size == 0)) {
/* 1995 */       return this;
/*      */     }
/* 1997 */     int i = paramString == null ? 0 : paramString.length();
/* 1998 */     char[] arrayOfChar = this.buffer;
/* 1999 */     for (int j = paramInt1; (j < paramInt2) && (paramInt3 != 0); j++) {
/* 2000 */       int k = paramStrMatcher.isMatch(arrayOfChar, j, paramInt1, paramInt2);
/* 2001 */       if (k > 0) {
/* 2002 */         replaceImpl(j, j + k, k, paramString, i);
/* 2003 */         paramInt2 = paramInt2 - k + i;
/* 2004 */         j = j + i - 1;
/* 2005 */         if (paramInt3 > 0) {
/* 2006 */           paramInt3--;
/*      */         }
/*      */       }
/*      */     }
/* 2010 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrBuilder reverse()
/*      */   {
/* 2020 */     if (this.size == 0) {
/* 2021 */       return this;
/*      */     }
/*      */     
/* 2024 */     int i = this.size / 2;
/* 2025 */     char[] arrayOfChar = this.buffer;
/* 2026 */     int j = 0; for (int k = this.size - 1; j < i; k--) {
/* 2027 */       int m = arrayOfChar[j];
/* 2028 */       arrayOfChar[j] = arrayOfChar[k];
/* 2029 */       arrayOfChar[k] = m;j++;
/*      */     }
/*      */     
/*      */ 
/* 2031 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrBuilder trim()
/*      */   {
/* 2042 */     if (this.size == 0) {
/* 2043 */       return this;
/*      */     }
/* 2045 */     int i = this.size;
/* 2046 */     char[] arrayOfChar = this.buffer;
/* 2047 */     int j = 0;
/* 2048 */     while ((j < i) && (arrayOfChar[j] <= ' ')) {
/* 2049 */       j++;
/*      */     }
/* 2051 */     while ((j < i) && (arrayOfChar[(i - 1)] <= ' ')) {
/* 2052 */       i--;
/*      */     }
/* 2054 */     if (i < this.size) {
/* 2055 */       delete(i, this.size);
/*      */     }
/* 2057 */     if (j > 0) {
/* 2058 */       delete(0, j);
/*      */     }
/* 2060 */     return this;
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
/*      */   public boolean startsWith(String paramString)
/*      */   {
/* 2073 */     if (paramString == null) {
/* 2074 */       return false;
/*      */     }
/* 2076 */     int i = paramString.length();
/* 2077 */     if (i == 0) {
/* 2078 */       return true;
/*      */     }
/* 2080 */     if (i > this.size) {
/* 2081 */       return false;
/*      */     }
/* 2083 */     for (int j = 0; j < i; j++) {
/* 2084 */       if (this.buffer[j] != paramString.charAt(j)) {
/* 2085 */         return false;
/*      */       }
/*      */     }
/* 2088 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean endsWith(String paramString)
/*      */   {
/* 2100 */     if (paramString == null) {
/* 2101 */       return false;
/*      */     }
/* 2103 */     int i = paramString.length();
/* 2104 */     if (i == 0) {
/* 2105 */       return true;
/*      */     }
/* 2107 */     if (i > this.size) {
/* 2108 */       return false;
/*      */     }
/* 2110 */     int j = this.size - i;
/* 2111 */     for (int k = 0; k < i; j++) {
/* 2112 */       if (this.buffer[j] != paramString.charAt(k)) {
/* 2113 */         return false;
/*      */       }
/* 2111 */       k++;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 2116 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public CharSequence subSequence(int paramInt1, int paramInt2)
/*      */   {
/* 2126 */     if (paramInt1 < 0) {
/* 2127 */       throw new StringIndexOutOfBoundsException(paramInt1);
/*      */     }
/* 2129 */     if (paramInt2 > this.size) {
/* 2130 */       throw new StringIndexOutOfBoundsException(paramInt2);
/*      */     }
/* 2132 */     if (paramInt1 > paramInt2) {
/* 2133 */       throw new StringIndexOutOfBoundsException(paramInt2 - paramInt1);
/*      */     }
/* 2135 */     return substring(paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String substring(int paramInt)
/*      */   {
/* 2146 */     return substring(paramInt, this.size);
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
/*      */ 
/*      */ 
/*      */ 
/*      */   public String substring(int paramInt1, int paramInt2)
/*      */   {
/* 2163 */     paramInt2 = validateRange(paramInt1, paramInt2);
/* 2164 */     return new String(this.buffer, paramInt1, paramInt2 - paramInt1);
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
/*      */ 
/*      */ 
/*      */   public String leftString(int paramInt)
/*      */   {
/* 2180 */     if (paramInt <= 0)
/* 2181 */       return "";
/* 2182 */     if (paramInt >= this.size) {
/* 2183 */       return new String(this.buffer, 0, this.size);
/*      */     }
/* 2185 */     return new String(this.buffer, 0, paramInt);
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
/*      */ 
/*      */ 
/*      */ 
/*      */   public String rightString(int paramInt)
/*      */   {
/* 2202 */     if (paramInt <= 0)
/* 2203 */       return "";
/* 2204 */     if (paramInt >= this.size) {
/* 2205 */       return new String(this.buffer, 0, this.size);
/*      */     }
/* 2207 */     return new String(this.buffer, this.size - paramInt, paramInt);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String midString(int paramInt1, int paramInt2)
/*      */   {
/* 2228 */     if (paramInt1 < 0) {
/* 2229 */       paramInt1 = 0;
/*      */     }
/* 2231 */     if ((paramInt2 <= 0) || (paramInt1 >= this.size)) {
/* 2232 */       return "";
/*      */     }
/* 2234 */     if (this.size <= paramInt1 + paramInt2) {
/* 2235 */       return new String(this.buffer, paramInt1, this.size - paramInt1);
/*      */     }
/* 2237 */     return new String(this.buffer, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean contains(char paramChar)
/*      */   {
/* 2248 */     char[] arrayOfChar = this.buffer;
/* 2249 */     for (int i = 0; i < this.size; i++) {
/* 2250 */       if (arrayOfChar[i] == paramChar) {
/* 2251 */         return true;
/*      */       }
/*      */     }
/* 2254 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean contains(String paramString)
/*      */   {
/* 2264 */     return indexOf(paramString, 0) >= 0;
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
/*      */ 
/*      */   public boolean contains(StrMatcher paramStrMatcher)
/*      */   {
/* 2279 */     return indexOf(paramStrMatcher, 0) >= 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int indexOf(char paramChar)
/*      */   {
/* 2290 */     return indexOf(paramChar, 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int indexOf(char paramChar, int paramInt)
/*      */   {
/* 2301 */     paramInt = paramInt < 0 ? 0 : paramInt;
/* 2302 */     if (paramInt >= this.size) {
/* 2303 */       return -1;
/*      */     }
/* 2305 */     char[] arrayOfChar = this.buffer;
/* 2306 */     for (int i = paramInt; i < this.size; i++) {
/* 2307 */       if (arrayOfChar[i] == paramChar) {
/* 2308 */         return i;
/*      */       }
/*      */     }
/* 2311 */     return -1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int indexOf(String paramString)
/*      */   {
/* 2323 */     return indexOf(paramString, 0);
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
/*      */   public int indexOf(String paramString, int paramInt)
/*      */   {
/* 2337 */     paramInt = paramInt < 0 ? 0 : paramInt;
/* 2338 */     if ((paramString == null) || (paramInt >= this.size)) {
/* 2339 */       return -1;
/*      */     }
/* 2341 */     int i = paramString.length();
/* 2342 */     if (i == 1) {
/* 2343 */       return indexOf(paramString.charAt(0), paramInt);
/*      */     }
/* 2345 */     if (i == 0) {
/* 2346 */       return paramInt;
/*      */     }
/* 2348 */     if (i > this.size) {
/* 2349 */       return -1;
/*      */     }
/* 2351 */     char[] arrayOfChar = this.buffer;
/* 2352 */     int j = this.size - i + 1;
/*      */     label125:
/* 2354 */     for (int k = paramInt; k < j; k++) {
/* 2355 */       for (int m = 0; m < i; m++) {
/* 2356 */         if (paramString.charAt(m) != arrayOfChar[(k + m)]) {
/*      */           break label125;
/*      */         }
/*      */       }
/* 2360 */       return k;
/*      */     }
/* 2362 */     return -1;
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
/*      */   public int indexOf(StrMatcher paramStrMatcher)
/*      */   {
/* 2376 */     return indexOf(paramStrMatcher, 0);
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
/*      */ 
/*      */ 
/*      */   public int indexOf(StrMatcher paramStrMatcher, int paramInt)
/*      */   {
/* 2392 */     paramInt = paramInt < 0 ? 0 : paramInt;
/* 2393 */     if ((paramStrMatcher == null) || (paramInt >= this.size)) {
/* 2394 */       return -1;
/*      */     }
/* 2396 */     int i = this.size;
/* 2397 */     char[] arrayOfChar = this.buffer;
/* 2398 */     for (int j = paramInt; j < i; j++) {
/* 2399 */       if (paramStrMatcher.isMatch(arrayOfChar, j, paramInt, i) > 0) {
/* 2400 */         return j;
/*      */       }
/*      */     }
/* 2403 */     return -1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int lastIndexOf(char paramChar)
/*      */   {
/* 2414 */     return lastIndexOf(paramChar, this.size - 1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int lastIndexOf(char paramChar, int paramInt)
/*      */   {
/* 2425 */     paramInt = paramInt >= this.size ? this.size - 1 : paramInt;
/* 2426 */     if (paramInt < 0) {
/* 2427 */       return -1;
/*      */     }
/* 2429 */     for (int i = paramInt; i >= 0; i--) {
/* 2430 */       if (this.buffer[i] == paramChar) {
/* 2431 */         return i;
/*      */       }
/*      */     }
/* 2434 */     return -1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int lastIndexOf(String paramString)
/*      */   {
/* 2446 */     return lastIndexOf(paramString, this.size - 1);
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
/*      */   public int lastIndexOf(String paramString, int paramInt)
/*      */   {
/* 2460 */     paramInt = paramInt >= this.size ? this.size - 1 : paramInt;
/* 2461 */     if ((paramString == null) || (paramInt < 0)) {
/* 2462 */       return -1;
/*      */     }
/* 2464 */     int i = paramString.length();
/* 2465 */     if ((i > 0) && (i <= this.size)) {
/* 2466 */       if (i == 1) {
/* 2467 */         return lastIndexOf(paramString.charAt(0), paramInt);
/*      */       }
/*      */       
/*      */       label114:
/* 2471 */       for (int j = paramInt - i + 1; j >= 0; j--) {
/* 2472 */         for (int k = 0; k < i; k++) {
/* 2473 */           if (paramString.charAt(k) != this.buffer[(j + k)]) {
/*      */             break label114;
/*      */           }
/*      */         }
/* 2477 */         return j;
/*      */       }
/*      */     }
/* 2480 */     else if (i == 0) {
/* 2481 */       return paramInt;
/*      */     }
/* 2483 */     return -1;
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
/*      */   public int lastIndexOf(StrMatcher paramStrMatcher)
/*      */   {
/* 2497 */     return lastIndexOf(paramStrMatcher, this.size);
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
/*      */ 
/*      */ 
/*      */   public int lastIndexOf(StrMatcher paramStrMatcher, int paramInt)
/*      */   {
/* 2513 */     paramInt = paramInt >= this.size ? this.size - 1 : paramInt;
/* 2514 */     if ((paramStrMatcher == null) || (paramInt < 0)) {
/* 2515 */       return -1;
/*      */     }
/* 2517 */     char[] arrayOfChar = this.buffer;
/* 2518 */     int i = paramInt + 1;
/* 2519 */     for (int j = paramInt; j >= 0; j--) {
/* 2520 */       if (paramStrMatcher.isMatch(arrayOfChar, j, 0, i) > 0) {
/* 2521 */         return j;
/*      */       }
/*      */     }
/* 2524 */     return -1;
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
/*      */   public StrTokenizer asTokenizer()
/*      */   {
/* 2561 */     return new StrBuilderTokenizer();
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
/*      */   public Reader asReader()
/*      */   {
/* 2585 */     return new StrBuilderReader();
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
/*      */   public Writer asWriter()
/*      */   {
/* 2610 */     return new StrBuilderWriter();
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
/*      */   public boolean equalsIgnoreCase(StrBuilder paramStrBuilder)
/*      */   {
/* 2652 */     if (this == paramStrBuilder) {
/* 2653 */       return true;
/*      */     }
/* 2655 */     if (this.size != paramStrBuilder.size) {
/* 2656 */       return false;
/*      */     }
/* 2658 */     char[] arrayOfChar1 = this.buffer;
/* 2659 */     char[] arrayOfChar2 = paramStrBuilder.buffer;
/* 2660 */     for (int i = this.size - 1; i >= 0; i--) {
/* 2661 */       char c1 = arrayOfChar1[i];
/* 2662 */       char c2 = arrayOfChar2[i];
/* 2663 */       if ((c1 != c2) && (Character.toUpperCase(c1) != Character.toUpperCase(c2))) {
/* 2664 */         return false;
/*      */       }
/*      */     }
/* 2667 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean equals(StrBuilder paramStrBuilder)
/*      */   {
/* 2678 */     if (this == paramStrBuilder) {
/* 2679 */       return true;
/*      */     }
/* 2681 */     if (this.size != paramStrBuilder.size) {
/* 2682 */       return false;
/*      */     }
/* 2684 */     char[] arrayOfChar1 = this.buffer;
/* 2685 */     char[] arrayOfChar2 = paramStrBuilder.buffer;
/* 2686 */     for (int i = this.size - 1; i >= 0; i--) {
/* 2687 */       if (arrayOfChar1[i] != arrayOfChar2[i]) {
/* 2688 */         return false;
/*      */       }
/*      */     }
/* 2691 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean equals(Object paramObject)
/*      */   {
/* 2703 */     if ((paramObject instanceof StrBuilder)) {
/* 2704 */       return equals((StrBuilder)paramObject);
/*      */     }
/* 2706 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int hashCode()
/*      */   {
/* 2716 */     char[] arrayOfChar = this.buffer;
/* 2717 */     int i = 0;
/* 2718 */     for (int j = this.size - 1; j >= 0; j--) {
/* 2719 */       i = 31 * i + arrayOfChar[j];
/*      */     }
/* 2721 */     return i;
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
/*      */ 
/*      */   public String toString()
/*      */   {
/* 2736 */     return new String(this.buffer, 0, this.size);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StringBuffer toStringBuffer()
/*      */   {
/* 2746 */     return new StringBuffer(this.size).append(this.buffer, 0, this.size);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StringBuilder toStringBuilder()
/*      */   {
/* 2757 */     return new StringBuilder(this.size).append(this.buffer, 0, this.size);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String build()
/*      */   {
/* 2768 */     return toString();
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
/*      */   protected int validateRange(int paramInt1, int paramInt2)
/*      */   {
/* 2782 */     if (paramInt1 < 0) {
/* 2783 */       throw new StringIndexOutOfBoundsException(paramInt1);
/*      */     }
/* 2785 */     if (paramInt2 > this.size) {
/* 2786 */       paramInt2 = this.size;
/*      */     }
/* 2788 */     if (paramInt1 > paramInt2) {
/* 2789 */       throw new StringIndexOutOfBoundsException("end < start");
/*      */     }
/* 2791 */     return paramInt2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void validateIndex(int paramInt)
/*      */   {
/* 2801 */     if ((paramInt < 0) || (paramInt > this.size)) {
/* 2802 */       throw new StringIndexOutOfBoundsException(paramInt);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   class StrBuilderTokenizer
/*      */     extends StrTokenizer
/*      */   {
/*      */     StrBuilderTokenizer() {}
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     protected List<String> tokenize(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*      */     {
/* 2822 */       if (paramArrayOfChar == null) {
/* 2823 */         return super.tokenize(StrBuilder.this.buffer, 0, StrBuilder.this.size());
/*      */       }
/* 2825 */       return super.tokenize(paramArrayOfChar, paramInt1, paramInt2);
/*      */     }
/*      */     
/*      */ 
/*      */     public String getContent()
/*      */     {
/* 2831 */       String str = super.getContent();
/* 2832 */       if (str == null) {
/* 2833 */         return StrBuilder.this.toString();
/*      */       }
/* 2835 */       return str;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   class StrBuilderReader
/*      */     extends Reader
/*      */   {
/*      */     private int pos;
/*      */     
/*      */ 
/*      */ 
/*      */     private int mark;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     StrBuilderReader() {}
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public void close() {}
/*      */     
/*      */ 
/*      */ 
/*      */     public int read()
/*      */     {
/* 2865 */       if (!ready()) {
/* 2866 */         return -1;
/*      */       }
/* 2868 */       return StrBuilder.this.charAt(this.pos++);
/*      */     }
/*      */     
/*      */ 
/*      */     public int read(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*      */     {
/* 2874 */       if ((paramInt1 < 0) || (paramInt2 < 0) || (paramInt1 > paramArrayOfChar.length) || (paramInt1 + paramInt2 > paramArrayOfChar.length) || (paramInt1 + paramInt2 < 0))
/*      */       {
/* 2876 */         throw new IndexOutOfBoundsException();
/*      */       }
/* 2878 */       if (paramInt2 == 0) {
/* 2879 */         return 0;
/*      */       }
/* 2881 */       if (this.pos >= StrBuilder.this.size()) {
/* 2882 */         return -1;
/*      */       }
/* 2884 */       if (this.pos + paramInt2 > StrBuilder.this.size()) {
/* 2885 */         paramInt2 = StrBuilder.this.size() - this.pos;
/*      */       }
/* 2887 */       StrBuilder.this.getChars(this.pos, this.pos + paramInt2, paramArrayOfChar, paramInt1);
/* 2888 */       this.pos += paramInt2;
/* 2889 */       return paramInt2;
/*      */     }
/*      */     
/*      */ 
/*      */     public long skip(long paramLong)
/*      */     {
/* 2895 */       if (this.pos + paramLong > StrBuilder.this.size()) {
/* 2896 */         paramLong = StrBuilder.this.size() - this.pos;
/*      */       }
/* 2898 */       if (paramLong < 0L) {
/* 2899 */         return 0L;
/*      */       }
/* 2901 */       this.pos = ((int)(this.pos + paramLong));
/* 2902 */       return paramLong;
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean ready()
/*      */     {
/* 2908 */       return this.pos < StrBuilder.this.size();
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean markSupported()
/*      */     {
/* 2914 */       return true;
/*      */     }
/*      */     
/*      */ 
/*      */     public void mark(int paramInt)
/*      */     {
/* 2920 */       this.mark = this.pos;
/*      */     }
/*      */     
/*      */ 
/*      */     public void reset()
/*      */     {
/* 2926 */       this.pos = this.mark;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   class StrBuilderWriter
/*      */     extends Writer
/*      */   {
/*      */     StrBuilderWriter() {}
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public void close() {}
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public void flush() {}
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public void write(int paramInt)
/*      */     {
/* 2958 */       StrBuilder.this.append((char)paramInt);
/*      */     }
/*      */     
/*      */ 
/*      */     public void write(char[] paramArrayOfChar)
/*      */     {
/* 2964 */       StrBuilder.this.append(paramArrayOfChar);
/*      */     }
/*      */     
/*      */ 
/*      */     public void write(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*      */     {
/* 2970 */       StrBuilder.this.append(paramArrayOfChar, paramInt1, paramInt2);
/*      */     }
/*      */     
/*      */ 
/*      */     public void write(String paramString)
/*      */     {
/* 2976 */       StrBuilder.this.append(paramString);
/*      */     }
/*      */     
/*      */ 
/*      */     public void write(String paramString, int paramInt1, int paramInt2)
/*      */     {
/* 2982 */       StrBuilder.this.append(paramString, paramInt1, paramInt2);
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\text\StrBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */