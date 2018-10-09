/*    */ package com.avaje.ebeaninternal.api;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.querydefn.OrmQueryDetail;
/*    */ 
/*    */ public abstract interface SpiQuery<T> extends com.avaje.ebean.Query<T> {
/*    */   public abstract void setTotalHits(int paramInt);
/*    */   
/*    */   public abstract boolean selectAllForLazyLoadProperty();
/*    */   
/*    */   public abstract void setMode(Mode paramMode);
/*    */   
/*    */   public abstract Mode getMode();
/*    */   
/*    */   public abstract com.avaje.ebean.bean.BeanCollectionTouched getBeanCollectionTouched();
/*    */   
/*    */   public abstract void setBeanCollectionTouched(com.avaje.ebean.bean.BeanCollectionTouched paramBeanCollectionTouched);
/*    */   
/*    */   public abstract void setIdList(java.util.List<Object> paramList);
/*    */   
/*    */   public abstract java.util.List<Object> getIdList();
/*    */   
/*    */   public abstract SpiQuery<T> copy();
/*    */   
/*    */   public abstract Type getType();
/*    */   
/*    */   public abstract void setType(Type paramType);
/*    */   
/*    */   public abstract String getLoadDescription();
/*    */   
/*    */   public abstract String getLoadMode();
/*    */   
/*    */   public abstract void setLoadDescription(String paramString1, String paramString2);
/*    */   
/*    */   public abstract void setBeanDescriptor(com.avaje.ebeaninternal.server.deploy.BeanDescriptor<?> paramBeanDescriptor);
/*    */   
/*    */   public abstract boolean initManyWhereJoins();
/*    */   
/*    */   public abstract ManyWhereJoins getManyWhereJoins();
/*    */   
/*    */   public abstract void convertWhereNaturalKeyToId(Object paramObject);
/*    */   
/*    */   public abstract com.avaje.ebeaninternal.server.querydefn.NaturalKeyBindParam getNaturalKeyBindParam();
/*    */   
/*    */   public abstract void setSelectId();
/*    */   
/*    */   public abstract void setFilterMany(String paramString, com.avaje.ebean.ExpressionList<?> paramExpressionList);
/*    */   
/*    */   public static enum Mode {
/* 49 */     NORMAL(false),  LAZYLOAD_MANY(false),  LAZYLOAD_BEAN(true),  REFRESH_BEAN(true);
/*    */     
/* 51 */     private Mode(boolean loadContextBean) { this.loadContextBean = loadContextBean; }
/*    */     
/*    */ 
/*    */     private final boolean loadContextBean;
/*    */     public boolean isLoadContextBean()
/*    */     {
/* 57 */       return this.loadContextBean; }
/*    */   }
/*    */   
/*    */   public abstract java.util.List<com.avaje.ebeaninternal.server.querydefn.OrmQueryProperties> removeQueryJoins();
/*    */   
/*    */   public abstract java.util.List<com.avaje.ebeaninternal.server.querydefn.OrmQueryProperties> removeLazyJoins();
/*    */   
/*    */   public abstract void setLazyLoadManyPath(String paramString);
/*    */   
/*    */   public abstract void convertManyFetchJoinsToQueryJoins(boolean paramBoolean, int paramInt);
/*    */   
/*    */   public static enum Type {
/* 69 */     BEAN, 
/*    */     
/*    */ 
/*    */ 
/*    */ 
/* 74 */     LIST, 
/*    */     
/*    */ 
/*    */ 
/*    */ 
/* 79 */     SET, 
/*    */     
/*    */ 
/*    */ 
/*    */ 
/* 84 */     MAP, 
/*    */     
/*    */ 
/*    */ 
/*    */ 
/* 89 */     ID_LIST, 
/*    */     
/*    */ 
/*    */ 
/*    */ 
/* 94 */     ROWCOUNT, 
/*    */     
/*    */ 
/*    */ 
/*    */ 
/* 99 */     SUBQUERY;
/*    */     
/*    */     private Type() {}
/*    */   }
/*    */   
/*    */   public abstract com.avaje.ebean.bean.PersistenceContext getPersistenceContext();
/*    */   
/*    */   public abstract void setPersistenceContext(com.avaje.ebean.bean.PersistenceContext paramPersistenceContext);
/*    */   
/*    */   public abstract boolean isDetailEmpty();
/*    */   
/*    */   public abstract Boolean isAutofetch();
/*    */   
/*    */   public abstract Boolean isForUpdate();
/*    */   
/*    */   public abstract com.avaje.ebeaninternal.server.autofetch.AutoFetchManager getAutoFetchManager();
/*    */   
/*    */   public abstract void setAutoFetchManager(com.avaje.ebeaninternal.server.autofetch.AutoFetchManager paramAutoFetchManager);
/*    */   
/*    */   public abstract com.avaje.ebean.bean.ObjectGraphNode setOrigin(com.avaje.ebean.bean.CallStack paramCallStack);
/*    */   
/*    */   public abstract void setParentNode(com.avaje.ebean.bean.ObjectGraphNode paramObjectGraphNode);
/*    */   
/*    */   public abstract void setLazyLoadProperty(String paramString);
/*    */   
/*    */   public abstract String getLazyLoadProperty();
/*    */   
/*    */   public abstract String getLazyLoadManyPath();
/*    */   
/*    */   public abstract com.avaje.ebean.bean.ObjectGraphNode getParentNode();
/*    */   
/*    */   public abstract boolean isUsageProfiling();
/*    */   
/*    */   public abstract void setUsageProfiling(boolean paramBoolean);
/*    */   
/*    */   public abstract String getName();
/*    */   
/*    */   public abstract int queryAutofetchHash();
/*    */   
/*    */   public abstract int queryPlanHash(com.avaje.ebean.event.BeanQueryRequest<?> paramBeanQueryRequest);
/*    */   
/*    */   public abstract int queryBindHash();
/*    */   
/*    */   public abstract int queryHash();
/*    */   
/*    */   public abstract boolean isSqlSelect();
/*    */   
/*    */   public abstract boolean isRawSql();
/*    */   
/*    */   public abstract com.avaje.ebean.OrderBy<T> getOrderBy();
/*    */   
/*    */   public abstract String getAdditionalWhere();
/*    */   
/*    */   public abstract SpiExpressionList<T> getWhereExpressions();
/*    */   
/*    */   public abstract SpiExpressionList<T> getHavingExpressions();
/*    */   
/*    */   public abstract String getAdditionalHaving();
/*    */   
/*    */   public abstract boolean hasMaxRowsOrFirstRow();
/*    */   
/*    */   public abstract Boolean isUseBeanCache();
/*    */   
/*    */   public abstract boolean isUseQueryCache();
/*    */   
/*    */   public abstract boolean isLoadBeanCache();
/*    */   
/*    */   public abstract Boolean isReadOnly();
/*    */   
/*    */   public abstract void contextAdd(com.avaje.ebean.bean.EntityBean paramEntityBean);
/*    */   
/*    */   public abstract Class<T> getBeanType();
/*    */   
/*    */   public abstract int getTimeout();
/*    */   
/*    */   public abstract java.util.ArrayList<com.avaje.ebean.bean.EntityBean> getContextAdditions();
/*    */   
/*    */   public abstract BindParams getBindParams();
/*    */   
/*    */   public abstract String getQuery();
/*    */   
/*    */   public abstract void setDetail(OrmQueryDetail paramOrmQueryDetail);
/*    */   
/*    */   public abstract boolean tuneFetchProperties(OrmQueryDetail paramOrmQueryDetail);
/*    */   
/*    */   public abstract void setAutoFetchTuned(boolean paramBoolean);
/*    */   
/*    */   public abstract OrmQueryDetail getDetail();
/*    */   
/*    */   public abstract com.avaje.ebeaninternal.server.deploy.TableJoin getIncludeTableJoin();
/*    */   
/*    */   public abstract void setIncludeTableJoin(com.avaje.ebeaninternal.server.deploy.TableJoin paramTableJoin);
/*    */   
/*    */   public abstract String getMapKey();
/*    */   
/*    */   public abstract int getBackgroundFetchAfter();
/*    */   
/*    */   public abstract int getMaxRows();
/*    */   
/*    */   public abstract int getFirstRow();
/*    */   
/*    */   public abstract boolean isDistinct();
/*    */   
/*    */   public abstract boolean isVanillaMode(boolean paramBoolean);
/*    */   
/*    */   public abstract void setDefaultSelectClause();
/*    */   
/*    */   public abstract String getRawWhereClause();
/*    */   
/*    */   public abstract Object getId();
/*    */   
/*    */   public abstract com.avaje.ebean.QueryListener<T> getListener();
/*    */   
/*    */   public abstract boolean createOwnTransaction();
/*    */   
/*    */   public abstract void setGeneratedSql(String paramString);
/*    */   
/*    */   public abstract int getBufferFetchSizeHint();
/*    */   
/*    */   public abstract boolean isFutureFetch();
/*    */   
/*    */   public abstract void setFutureFetch(boolean paramBoolean);
/*    */   
/*    */   public abstract void setCancelableQuery(com.avaje.ebeaninternal.server.query.CancelableQuery paramCancelableQuery);
/*    */   
/*    */   public abstract boolean isCancelled();
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\api\SpiQuery.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */