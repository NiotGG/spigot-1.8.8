/*     */ package org.apache.logging.log4j.core.config;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginType;
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
/*     */ public class Node
/*     */ {
/*     */   private final Node parent;
/*     */   private final String name;
/*     */   private String value;
/*     */   private final PluginType<?> type;
/*  35 */   private final Map<String, String> attributes = new HashMap();
/*  36 */   private final List<Node> children = new ArrayList();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private Object object;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Node(Node paramNode, String paramString, PluginType<?> paramPluginType)
/*     */   {
/*  49 */     this.parent = paramNode;
/*  50 */     this.name = paramString;
/*  51 */     this.type = paramPluginType;
/*     */   }
/*     */   
/*     */   public Node() {
/*  55 */     this.parent = null;
/*  56 */     this.name = null;
/*  57 */     this.type = null;
/*     */   }
/*     */   
/*     */   public Node(Node paramNode) {
/*  61 */     this.parent = paramNode.parent;
/*  62 */     this.name = paramNode.name;
/*  63 */     this.type = paramNode.type;
/*  64 */     this.attributes.putAll(paramNode.getAttributes());
/*  65 */     this.value = paramNode.getValue();
/*  66 */     for (Node localNode : paramNode.getChildren()) {
/*  67 */       this.children.add(new Node(localNode));
/*     */     }
/*  69 */     this.object = paramNode.object;
/*     */   }
/*     */   
/*     */   public Map<String, String> getAttributes() {
/*  73 */     return this.attributes;
/*     */   }
/*     */   
/*     */   public List<Node> getChildren() {
/*  77 */     return this.children;
/*     */   }
/*     */   
/*     */   public boolean hasChildren() {
/*  81 */     return this.children.size() > 0;
/*     */   }
/*     */   
/*     */   public String getValue() {
/*  85 */     return this.value;
/*     */   }
/*     */   
/*     */   public void setValue(String paramString) {
/*  89 */     this.value = paramString;
/*     */   }
/*     */   
/*     */   public Node getParent() {
/*  93 */     return this.parent;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  97 */     return this.name;
/*     */   }
/*     */   
/*     */   public boolean isRoot() {
/* 101 */     return this.parent == null;
/*     */   }
/*     */   
/*     */   public void setObject(Object paramObject) {
/* 105 */     this.object = paramObject;
/*     */   }
/*     */   
/*     */   public Object getObject() {
/* 109 */     return this.object;
/*     */   }
/*     */   
/*     */   public PluginType<?> getType() {
/* 113 */     return this.type;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 118 */     if (this.object == null) {
/* 119 */       return "null";
/*     */     }
/* 121 */     return this.type.getPluginClass().getName() + " with name " + this.name;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\config\Node.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */