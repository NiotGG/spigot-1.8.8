/*    */ package io.netty.handler.ssl.util;
/*    */ 
/*    */ import java.math.BigInteger;
/*    */ import java.security.KeyPair;
/*    */ import java.security.PrivateKey;
/*    */ import java.security.Provider;
/*    */ import java.security.SecureRandom;
/*    */ import java.security.cert.X509Certificate;
/*    */ import org.bouncycastle.asn1.x500.X500Name;
/*    */ import org.bouncycastle.cert.X509CertificateHolder;
/*    */ import org.bouncycastle.cert.X509v3CertificateBuilder;
/*    */ import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
/*    */ import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
/*    */ import org.bouncycastle.jce.provider.BouncyCastleProvider;
/*    */ import org.bouncycastle.operator.ContentSigner;
/*    */ import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
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
/*    */ 
/*    */ 
/*    */ final class BouncyCastleSelfSignedCertGenerator
/*    */ {
/* 42 */   private static final Provider PROVIDER = new BouncyCastleProvider();
/*    */   
/*    */   static String[] generate(String paramString, KeyPair paramKeyPair, SecureRandom paramSecureRandom) throws Exception {
/* 45 */     PrivateKey localPrivateKey = paramKeyPair.getPrivate();
/*    */     
/*    */ 
/* 48 */     X500Name localX500Name = new X500Name("CN=" + paramString);
/* 49 */     JcaX509v3CertificateBuilder localJcaX509v3CertificateBuilder = new JcaX509v3CertificateBuilder(localX500Name, new BigInteger(64, paramSecureRandom), SelfSignedCertificate.NOT_BEFORE, SelfSignedCertificate.NOT_AFTER, localX500Name, paramKeyPair.getPublic());
/*    */     
/*    */ 
/* 52 */     ContentSigner localContentSigner = new JcaContentSignerBuilder("SHA256WithRSAEncryption").build(localPrivateKey);
/* 53 */     X509CertificateHolder localX509CertificateHolder = localJcaX509v3CertificateBuilder.build(localContentSigner);
/* 54 */     X509Certificate localX509Certificate = new JcaX509CertificateConverter().setProvider(PROVIDER).getCertificate(localX509CertificateHolder);
/* 55 */     localX509Certificate.verify(paramKeyPair.getPublic());
/*    */     
/* 57 */     return SelfSignedCertificate.newSelfSignedCertificate(paramString, localPrivateKey, localX509Certificate);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\ssl\util\BouncyCastleSelfSignedCertGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */