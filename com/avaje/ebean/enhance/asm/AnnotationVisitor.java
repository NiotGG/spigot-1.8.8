package com.avaje.ebean.enhance.asm;

public abstract interface AnnotationVisitor
{
  public abstract void visit(String paramString, Object paramObject);
  
  public abstract void visitEnum(String paramString1, String paramString2, String paramString3);
  
  public abstract AnnotationVisitor visitAnnotation(String paramString1, String paramString2);
  
  public abstract AnnotationVisitor visitArray(String paramString);
  
  public abstract void visitEnd();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\enhance\asm\AnnotationVisitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */