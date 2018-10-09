/*     */ package org.apache.commons.codec.language.bm;
/*     */ 
/*     */ import org.apache.commons.codec.EncoderException;
/*     */ import org.apache.commons.codec.StringEncoder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BeiderMorseEncoder
/*     */   implements StringEncoder
/*     */ {
/*  76 */   private PhoneticEngine engine = new PhoneticEngine(NameType.GENERIC, RuleType.APPROX, true);
/*     */   
/*     */   public Object encode(Object paramObject) throws EncoderException
/*     */   {
/*  80 */     if (!(paramObject instanceof String)) {
/*  81 */       throw new EncoderException("BeiderMorseEncoder encode parameter is not of type String");
/*     */     }
/*  83 */     return encode((String)paramObject);
/*     */   }
/*     */   
/*     */   public String encode(String paramString) throws EncoderException
/*     */   {
/*  88 */     if (paramString == null) {
/*  89 */       return null;
/*     */     }
/*  91 */     return this.engine.encode(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public NameType getNameType()
/*     */   {
/* 100 */     return this.engine.getNameType();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public RuleType getRuleType()
/*     */   {
/* 109 */     return this.engine.getRuleType();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isConcat()
/*     */   {
/* 118 */     return this.engine.isConcat();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setConcat(boolean paramBoolean)
/*     */   {
/* 129 */     this.engine = new PhoneticEngine(this.engine.getNameType(), this.engine.getRuleType(), paramBoolean, this.engine.getMaxPhonemes());
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
/*     */   public void setNameType(NameType paramNameType)
/*     */   {
/* 143 */     this.engine = new PhoneticEngine(paramNameType, this.engine.getRuleType(), this.engine.isConcat(), this.engine.getMaxPhonemes());
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
/*     */   public void setRuleType(RuleType paramRuleType)
/*     */   {
/* 156 */     this.engine = new PhoneticEngine(this.engine.getNameType(), paramRuleType, this.engine.isConcat(), this.engine.getMaxPhonemes());
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
/*     */   public void setMaxPhonemes(int paramInt)
/*     */   {
/* 170 */     this.engine = new PhoneticEngine(this.engine.getNameType(), this.engine.getRuleType(), this.engine.isConcat(), paramInt);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\codec\language\bm\BeiderMorseEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */