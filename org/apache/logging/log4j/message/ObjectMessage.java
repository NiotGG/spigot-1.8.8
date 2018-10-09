/*     */ package org.apache.logging.log4j.message;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
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
/*     */ public class ObjectMessage
/*     */   implements Message
/*     */ {
/*     */   private static final long serialVersionUID = -5903272448334166185L;
/*     */   private transient Object obj;
/*     */   
/*     */   public ObjectMessage(Object paramObject)
/*     */   {
/*  38 */     if (paramObject == null) {
/*  39 */       paramObject = "null";
/*     */     }
/*  41 */     this.obj = paramObject;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getFormattedMessage()
/*     */   {
/*  50 */     return this.obj.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getFormat()
/*     */   {
/*  59 */     return this.obj.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object[] getParameters()
/*     */   {
/*  68 */     return new Object[] { this.obj };
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject)
/*     */   {
/*  73 */     if (this == paramObject) {
/*  74 */       return true;
/*     */     }
/*  76 */     if ((paramObject == null) || (getClass() != paramObject.getClass())) {
/*  77 */       return false;
/*     */     }
/*     */     
/*  80 */     ObjectMessage localObjectMessage = (ObjectMessage)paramObject;
/*     */     
/*  82 */     return this.obj != null ? this.obj.equals(localObjectMessage.obj) : localObjectMessage.obj == null;
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/*  87 */     return this.obj != null ? this.obj.hashCode() : 0;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/*  92 */     return "ObjectMessage[obj=" + this.obj.toString() + "]";
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/*  96 */     paramObjectOutputStream.defaultWriteObject();
/*  97 */     if ((this.obj instanceof Serializable)) {
/*  98 */       paramObjectOutputStream.writeObject(this.obj);
/*     */     } else {
/* 100 */       paramObjectOutputStream.writeObject(this.obj.toString());
/*     */     }
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 105 */     paramObjectInputStream.defaultReadObject();
/* 106 */     this.obj = paramObjectInputStream.readObject();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Throwable getThrowable()
/*     */   {
/* 116 */     return (this.obj instanceof Throwable) ? (Throwable)this.obj : null;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\message\ObjectMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */