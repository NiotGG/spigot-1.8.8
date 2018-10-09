/*    */ package com.mojang.authlib.properties;
/*    */ 
/*    */ import java.security.Signature;
/*    */ 
/*    */ public class Property
/*    */ {
/*    */   private final String name;
/*    */   private final String value;
/*    */   private final String signature;
/*    */   
/*    */   public Property(String paramString1, String paramString2)
/*    */   {
/* 13 */     this(paramString1, paramString2, null);
/*    */   }
/*    */   
/*    */   public Property(String paramString1, String paramString2, String paramString3) {
/* 17 */     this.name = paramString1;
/* 18 */     this.value = paramString2;
/* 19 */     this.signature = paramString3;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 23 */     return this.name;
/*    */   }
/*    */   
/*    */   public String getValue() {
/* 27 */     return this.value;
/*    */   }
/*    */   
/*    */   public String getSignature() {
/* 31 */     return this.signature;
/*    */   }
/*    */   
/*    */   public boolean hasSignature() {
/* 35 */     return this.signature != null;
/*    */   }
/*    */   
/*    */   public boolean isSignatureValid(java.security.PublicKey paramPublicKey) {
/*    */     try {
/* 40 */       Signature localSignature = Signature.getInstance("SHA1withRSA");
/* 41 */       localSignature.initVerify(paramPublicKey);
/* 42 */       localSignature.update(this.value.getBytes());
/* 43 */       return localSignature.verify(org.apache.commons.codec.binary.Base64.decodeBase64(this.signature));
/*    */     } catch (java.security.NoSuchAlgorithmException localNoSuchAlgorithmException) {
/* 45 */       localNoSuchAlgorithmException.printStackTrace();
/*    */     } catch (java.security.InvalidKeyException localInvalidKeyException) {
/* 47 */       localInvalidKeyException.printStackTrace();
/*    */     } catch (java.security.SignatureException localSignatureException) {
/* 49 */       localSignatureException.printStackTrace();
/*    */     }
/* 51 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\mojang\authlib\properties\Property.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */