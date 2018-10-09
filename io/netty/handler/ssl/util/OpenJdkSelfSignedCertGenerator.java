/*    */ package io.netty.handler.ssl.util;
/*    */ 
/*    */ import java.math.BigInteger;
/*    */ import java.security.KeyPair;
/*    */ import java.security.PrivateKey;
/*    */ import java.security.SecureRandom;
/*    */ import java.security.cert.CertificateException;
/*    */ import sun.security.x509.AlgorithmId;
/*    */ import sun.security.x509.CertificateAlgorithmId;
/*    */ import sun.security.x509.CertificateIssuerName;
/*    */ import sun.security.x509.CertificateSerialNumber;
/*    */ import sun.security.x509.CertificateSubjectName;
/*    */ import sun.security.x509.CertificateValidity;
/*    */ import sun.security.x509.CertificateVersion;
/*    */ import sun.security.x509.CertificateX509Key;
/*    */ import sun.security.x509.X500Name;
/*    */ import sun.security.x509.X509CertImpl;
/*    */ import sun.security.x509.X509CertInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class OpenJdkSelfSignedCertGenerator
/*    */ {
/*    */   static String[] generate(String paramString, KeyPair paramKeyPair, SecureRandom paramSecureRandom)
/*    */     throws Exception
/*    */   {
/* 45 */     PrivateKey localPrivateKey = paramKeyPair.getPrivate();
/*    */     
/*    */ 
/* 48 */     X509CertInfo localX509CertInfo = new X509CertInfo();
/* 49 */     X500Name localX500Name = new X500Name("CN=" + paramString);
/* 50 */     localX509CertInfo.set("version", new CertificateVersion(2));
/* 51 */     localX509CertInfo.set("serialNumber", new CertificateSerialNumber(new BigInteger(64, paramSecureRandom)));
/*    */     try {
/* 53 */       localX509CertInfo.set("subject", new CertificateSubjectName(localX500Name));
/*    */     } catch (CertificateException localCertificateException1) {
/* 55 */       localX509CertInfo.set("subject", localX500Name);
/*    */     }
/*    */     try {
/* 58 */       localX509CertInfo.set("issuer", new CertificateIssuerName(localX500Name));
/*    */     } catch (CertificateException localCertificateException2) {
/* 60 */       localX509CertInfo.set("issuer", localX500Name);
/*    */     }
/* 62 */     localX509CertInfo.set("validity", new CertificateValidity(SelfSignedCertificate.NOT_BEFORE, SelfSignedCertificate.NOT_AFTER));
/* 63 */     localX509CertInfo.set("key", new CertificateX509Key(paramKeyPair.getPublic()));
/* 64 */     localX509CertInfo.set("algorithmID", new CertificateAlgorithmId(new AlgorithmId(AlgorithmId.sha1WithRSAEncryption_oid)));
/*    */     
/*    */ 
/*    */ 
/* 68 */     X509CertImpl localX509CertImpl = new X509CertImpl(localX509CertInfo);
/* 69 */     localX509CertImpl.sign(localPrivateKey, "SHA1withRSA");
/*    */     
/*    */ 
/* 72 */     localX509CertInfo.set("algorithmID.algorithm", localX509CertImpl.get("x509.algorithm"));
/* 73 */     localX509CertImpl = new X509CertImpl(localX509CertInfo);
/* 74 */     localX509CertImpl.sign(localPrivateKey, "SHA1withRSA");
/* 75 */     localX509CertImpl.verify(paramKeyPair.getPublic());
/*    */     
/* 77 */     return SelfSignedCertificate.newSelfSignedCertificate(paramString, localPrivateKey, localX509CertImpl);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\ssl\util\OpenJdkSelfSignedCertGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */