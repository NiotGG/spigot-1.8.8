/*     */ package com.avaje.ebeaninternal.server.expression;
/*     */ 
/*     */ import com.avaje.ebean.ExampleExpression;
/*     */ import com.avaje.ebean.Expression;
/*     */ import com.avaje.ebean.ExpressionFactory;
/*     */ import com.avaje.ebean.ExpressionList;
/*     */ import com.avaje.ebean.Junction;
/*     */ import com.avaje.ebean.LikeType;
/*     */ import com.avaje.ebean.Query;
/*     */ import com.avaje.ebeaninternal.api.SpiExpressionFactory;
/*     */ import com.avaje.ebeaninternal.api.SpiQuery;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultExpressionFactory
/*     */   implements SpiExpressionFactory
/*     */ {
/*  22 */   private static final Object[] EMPTY_ARRAY = new Object[0];
/*     */   private final FilterExprPath prefix;
/*     */   
/*     */   public DefaultExpressionFactory()
/*     */   {
/*  27 */     this(null);
/*     */   }
/*     */   
/*     */   public DefaultExpressionFactory(FilterExprPath prefix) {
/*  31 */     this.prefix = prefix;
/*     */   }
/*     */   
/*     */   public ExpressionFactory createExpressionFactory(FilterExprPath prefix) {
/*  35 */     return new DefaultExpressionFactory(prefix);
/*     */   }
/*     */   
/*     */   public String getLang() {
/*  39 */     return "sql";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Expression eq(String propertyName, Object value)
/*     */   {
/*  46 */     if (value == null) {
/*  47 */       return isNull(propertyName);
/*     */     }
/*  49 */     return new SimpleExpression(this.prefix, propertyName, SimpleExpression.Op.EQ, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Expression ne(String propertyName, Object value)
/*     */   {
/*  56 */     if (value == null) {
/*  57 */       return isNotNull(propertyName);
/*     */     }
/*  59 */     return new SimpleExpression(this.prefix, propertyName, SimpleExpression.Op.NOT_EQ, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Expression ieq(String propertyName, String value)
/*     */   {
/*  67 */     if (value == null) {
/*  68 */       return isNull(propertyName);
/*     */     }
/*  70 */     return new CaseInsensitiveEqualExpression(this.prefix, propertyName, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Expression between(String propertyName, Object value1, Object value2)
/*     */   {
/*  78 */     return new BetweenExpression(this.prefix, propertyName, value1, value2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Expression betweenProperties(String lowProperty, String highProperty, Object value)
/*     */   {
/*  86 */     return new BetweenPropertyExpression(this.prefix, lowProperty, highProperty, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Expression gt(String propertyName, Object value)
/*     */   {
/*  94 */     return new SimpleExpression(this.prefix, propertyName, SimpleExpression.Op.GT, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Expression ge(String propertyName, Object value)
/*     */   {
/* 103 */     return new SimpleExpression(this.prefix, propertyName, SimpleExpression.Op.GT_EQ, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Expression lt(String propertyName, Object value)
/*     */   {
/* 111 */     return new SimpleExpression(this.prefix, propertyName, SimpleExpression.Op.LT, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Expression le(String propertyName, Object value)
/*     */   {
/* 119 */     return new SimpleExpression(this.prefix, propertyName, SimpleExpression.Op.LT_EQ, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Expression isNull(String propertyName)
/*     */   {
/* 127 */     return new NullExpression(this.prefix, propertyName, false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Expression isNotNull(String propertyName)
/*     */   {
/* 135 */     return new NullExpression(this.prefix, propertyName, true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ExampleExpression iexampleLike(Object example)
/*     */   {
/* 142 */     return new DefaultExampleExpression(this.prefix, example, true, LikeType.RAW);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ExampleExpression exampleLike(Object example)
/*     */   {
/* 150 */     return new DefaultExampleExpression(this.prefix, example, false, LikeType.RAW);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ExampleExpression exampleLike(Object example, boolean caseInsensitive, LikeType likeType)
/*     */   {
/* 157 */     return new DefaultExampleExpression(this.prefix, example, caseInsensitive, likeType);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Expression like(String propertyName, String value)
/*     */   {
/* 165 */     return new LikeExpression(this.prefix, propertyName, value, false, LikeType.RAW);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Expression ilike(String propertyName, String value)
/*     */   {
/* 174 */     return new LikeExpression(this.prefix, propertyName, value, true, LikeType.RAW);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Expression startsWith(String propertyName, String value)
/*     */   {
/* 181 */     return new LikeExpression(this.prefix, propertyName, value, false, LikeType.STARTS_WITH);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Expression istartsWith(String propertyName, String value)
/*     */   {
/* 189 */     return new LikeExpression(this.prefix, propertyName, value, true, LikeType.STARTS_WITH);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Expression endsWith(String propertyName, String value)
/*     */   {
/* 196 */     return new LikeExpression(this.prefix, propertyName, value, false, LikeType.ENDS_WITH);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Expression iendsWith(String propertyName, String value)
/*     */   {
/* 204 */     return new LikeExpression(this.prefix, propertyName, value, true, LikeType.ENDS_WITH);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Expression contains(String propertyName, String value)
/*     */   {
/* 211 */     return new LikeExpression(this.prefix, propertyName, value, false, LikeType.CONTAINS);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Expression icontains(String propertyName, String value)
/*     */   {
/* 219 */     return new LikeExpression(this.prefix, propertyName, value, true, LikeType.CONTAINS);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Expression in(String propertyName, Object[] values)
/*     */   {
/* 226 */     return new InExpression(this.prefix, propertyName, values);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Expression in(String propertyName, Query<?> subQuery)
/*     */   {
/* 233 */     return new InQueryExpression(this.prefix, propertyName, (SpiQuery)subQuery);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Expression in(String propertyName, Collection<?> values)
/*     */   {
/* 240 */     return new InExpression(this.prefix, propertyName, values);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Expression idEq(Object value)
/*     */   {
/* 247 */     return new IdExpression(value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Expression idIn(List<?> idList)
/*     */   {
/* 254 */     return new IdInExpression(idList);
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
/*     */   public Expression allEq(Map<String, Object> propertyMap)
/*     */   {
/* 268 */     return new AllEqualsExpression(this.prefix, propertyMap);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Expression raw(String raw, Object value)
/*     */   {
/* 279 */     return new RawExpression(raw, new Object[] { value });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Expression raw(String raw, Object[] values)
/*     */   {
/* 290 */     return new RawExpression(raw, values);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Expression raw(String raw)
/*     */   {
/* 297 */     return new RawExpression(raw, EMPTY_ARRAY);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Expression and(Expression expOne, Expression expTwo)
/*     */   {
/* 305 */     return new LogicExpression.And(expOne, expTwo);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Expression or(Expression expOne, Expression expTwo)
/*     */   {
/* 313 */     return new LogicExpression.Or(expOne, expTwo);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Expression not(Expression exp)
/*     */   {
/* 321 */     return new NotExpression(exp);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public <T> Junction<T> conjunction(Query<T> query)
/*     */   {
/* 329 */     return new JunctionExpression.Conjunction(query, query.where());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public <T> Junction<T> disjunction(Query<T> query)
/*     */   {
/* 337 */     return new JunctionExpression.Disjunction(query, query.where());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public <T> Junction<T> conjunction(Query<T> query, ExpressionList<T> parent)
/*     */   {
/* 345 */     return new JunctionExpression.Conjunction(query, parent);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public <T> Junction<T> disjunction(Query<T> query, ExpressionList<T> parent)
/*     */   {
/* 353 */     return new JunctionExpression.Disjunction(query, parent);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\expression\DefaultExpressionFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */