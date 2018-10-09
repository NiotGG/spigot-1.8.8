/*     */ package org.yaml.snakeyaml.representer;
/*     */ 
/*     */ import java.beans.IntrospectionException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.yaml.snakeyaml.DumperOptions.FlowStyle;
/*     */ import org.yaml.snakeyaml.error.YAMLException;
/*     */ import org.yaml.snakeyaml.introspector.Property;
/*     */ import org.yaml.snakeyaml.introspector.PropertyUtils;
/*     */ import org.yaml.snakeyaml.nodes.MappingNode;
/*     */ import org.yaml.snakeyaml.nodes.Node;
/*     */ import org.yaml.snakeyaml.nodes.NodeId;
/*     */ import org.yaml.snakeyaml.nodes.NodeTuple;
/*     */ import org.yaml.snakeyaml.nodes.ScalarNode;
/*     */ import org.yaml.snakeyaml.nodes.SequenceNode;
/*     */ import org.yaml.snakeyaml.nodes.Tag;
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
/*     */ public class Representer
/*     */   extends SafeRepresenter
/*     */ {
/*  43 */   public Representer() { this.representers.put(null, new RepresentJavaBean()); }
/*     */   
/*     */   protected class RepresentJavaBean implements Represent {
/*     */     protected RepresentJavaBean() {}
/*     */     
/*     */     public Node representData(Object data) {
/*  49 */       try { return Representer.this.representJavaBean(Representer.this.getProperties(data.getClass()), data);
/*     */       } catch (IntrospectionException e) {
/*  51 */         throw new YAMLException(e);
/*     */       }
/*     */     }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected MappingNode representJavaBean(Set<Property> properties, Object javaBean)
/*     */   {
/*  71 */     List<NodeTuple> value = new ArrayList(properties.size());
/*     */     
/*  73 */     Tag customTag = (Tag)this.classTags.get(javaBean.getClass());
/*  74 */     Tag tag = customTag != null ? customTag : new Tag(javaBean.getClass());
/*     */     
/*  76 */     MappingNode node = new MappingNode(tag, value, null);
/*  77 */     this.representedObjects.put(javaBean, node);
/*  78 */     boolean bestStyle = true;
/*  79 */     for (Property property : properties) {
/*  80 */       Object memberValue = property.get(javaBean);
/*  81 */       Tag customPropertyTag = memberValue == null ? null : (Tag)this.classTags.get(memberValue.getClass());
/*     */       
/*  83 */       NodeTuple tuple = representJavaBeanProperty(javaBean, property, memberValue, customPropertyTag);
/*     */       
/*  85 */       if (tuple != null)
/*     */       {
/*     */ 
/*  88 */         if (((ScalarNode)tuple.getKeyNode()).getStyle() != null) {
/*  89 */           bestStyle = false;
/*     */         }
/*  91 */         Node nodeValue = tuple.getValueNode();
/*  92 */         if ((!(nodeValue instanceof ScalarNode)) || (((ScalarNode)nodeValue).getStyle() != null)) {
/*  93 */           bestStyle = false;
/*     */         }
/*  95 */         value.add(tuple);
/*     */       } }
/*  97 */     if (this.defaultFlowStyle != DumperOptions.FlowStyle.AUTO) {
/*  98 */       node.setFlowStyle(this.defaultFlowStyle.getStyleBoolean());
/*     */     } else {
/* 100 */       node.setFlowStyle(Boolean.valueOf(bestStyle));
/*     */     }
/* 102 */     return node;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected NodeTuple representJavaBeanProperty(Object javaBean, Property property, Object propertyValue, Tag customTag)
/*     */   {
/* 121 */     ScalarNode nodeKey = (ScalarNode)representData(property.getName());
/*     */     
/* 123 */     boolean hasAlias = this.representedObjects.containsKey(propertyValue);
/*     */     
/* 125 */     Node nodeValue = representData(propertyValue);
/*     */     
/* 127 */     if ((propertyValue != null) && (!hasAlias)) {
/* 128 */       NodeId nodeId = nodeValue.getNodeId();
/* 129 */       if (customTag == null) {
/* 130 */         if (nodeId == NodeId.scalar) {
/* 131 */           if ((propertyValue instanceof Enum)) {
/* 132 */             nodeValue.setTag(Tag.STR);
/*     */           }
/*     */         } else {
/* 135 */           if ((nodeId == NodeId.mapping) && 
/* 136 */             (property.getType() == propertyValue.getClass()) && 
/* 137 */             (!(propertyValue instanceof Map)) && 
/* 138 */             (!nodeValue.getTag().equals(Tag.SET))) {
/* 139 */             nodeValue.setTag(Tag.MAP);
/*     */           }
/*     */           
/*     */ 
/*     */ 
/* 144 */           checkGlobalTag(property, nodeValue, propertyValue);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 149 */     return new NodeTuple(nodeKey, nodeValue);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void checkGlobalTag(Property property, Node node, Object object)
/*     */   {
/* 166 */     if ((object.getClass().isArray()) && (object.getClass().getComponentType().isPrimitive())) {
/* 167 */       return;
/*     */     }
/*     */     
/* 170 */     Class<?>[] arguments = property.getActualTypeArguments();
/* 171 */     Class<?> keyType; Class<?> valueType; if (arguments != null) { Class<? extends Object> t;
/* 172 */       Iterator<Object> iter; if (node.getNodeId() == NodeId.sequence)
/*     */       {
/* 174 */         t = arguments[0];
/* 175 */         SequenceNode snode = (SequenceNode)node;
/*     */         Iterable<Object> memberList;
/* 177 */         Iterable<Object> memberList; if (object.getClass().isArray()) {
/* 178 */           memberList = Arrays.asList((Object[])object);
/*     */         }
/*     */         else {
/* 181 */           memberList = (Iterable)object;
/*     */         }
/* 183 */         iter = memberList.iterator();
/* 184 */         for (Node childNode : snode.getValue()) {
/* 185 */           Object member = iter.next();
/* 186 */           if ((member != null) && 
/* 187 */             (t.equals(member.getClass())) && 
/* 188 */             (childNode.getNodeId() == NodeId.mapping))
/* 189 */             childNode.setTag(Tag.MAP);
/*     */         }
/*     */       } else { Class<?> t;
/*     */         Iterator<NodeTuple> iter;
/* 193 */         if ((object instanceof Set)) {
/* 194 */           t = arguments[0];
/* 195 */           MappingNode mnode = (MappingNode)node;
/* 196 */           iter = mnode.getValue().iterator();
/* 197 */           Set<?> set = (Set)object;
/* 198 */           for (Object member : set) {
/* 199 */             NodeTuple tuple = (NodeTuple)iter.next();
/* 200 */             Node keyNode = tuple.getKeyNode();
/* 201 */             if ((t.equals(member.getClass())) && 
/* 202 */               (keyNode.getNodeId() == NodeId.mapping)) {
/* 203 */               keyNode.setTag(Tag.MAP);
/*     */             }
/*     */           }
/*     */         }
/* 207 */         else if ((object instanceof Map)) {
/* 208 */           keyType = arguments[0];
/* 209 */           valueType = arguments[1];
/* 210 */           MappingNode mnode = (MappingNode)node;
/* 211 */           for (NodeTuple tuple : mnode.getValue()) {
/* 212 */             resetTag(keyType, tuple.getKeyNode());
/* 213 */             resetTag(valueType, tuple.getValueNode());
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void resetTag(Class<? extends Object> type, Node node)
/*     */   {
/* 223 */     Tag tag = node.getTag();
/* 224 */     if (tag.matches(type)) {
/* 225 */       if (Enum.class.isAssignableFrom(type)) {
/* 226 */         node.setTag(Tag.STR);
/*     */       } else {
/* 228 */         node.setTag(Tag.MAP);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Set<Property> getProperties(Class<? extends Object> type)
/*     */     throws IntrospectionException
/*     */   {
/* 243 */     return getPropertyUtils().getProperties(type);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\yaml\snakeyaml\representer\Representer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */