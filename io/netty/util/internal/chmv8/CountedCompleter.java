/*     */ package io.netty.util.internal.chmv8;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import sun.misc.Unsafe;
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
/*     */ public abstract class CountedCompleter<T>
/*     */   extends ForkJoinTask<T>
/*     */ {
/*     */   private static final long serialVersionUID = 5232453752276485070L;
/*     */   final CountedCompleter<?> completer;
/*     */   volatile int pending;
/*     */   private static final Unsafe U;
/*     */   private static final long PENDING;
/*     */   
/*     */   protected CountedCompleter(CountedCompleter<?> paramCountedCompleter, int paramInt)
/*     */   {
/* 418 */     this.completer = paramCountedCompleter;
/* 419 */     this.pending = paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected CountedCompleter(CountedCompleter<?> paramCountedCompleter)
/*     */   {
/* 429 */     this.completer = paramCountedCompleter;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected CountedCompleter()
/*     */   {
/* 437 */     this.completer = null;
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
/*     */   public boolean onExceptionalCompletion(Throwable paramThrowable, CountedCompleter<?> paramCountedCompleter)
/*     */   {
/* 479 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final CountedCompleter<?> getCompleter()
/*     */   {
/* 489 */     return this.completer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final int getPendingCount()
/*     */   {
/* 498 */     return this.pending;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void setPendingCount(int paramInt)
/*     */   {
/* 507 */     this.pending = paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public final void addToPendingCount(int paramInt)
/*     */   {
/*     */     int i;
/*     */     
/*     */ 
/* 517 */     while (!U.compareAndSwapInt(this, PENDING, i = this.pending, i + paramInt)) {}
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final boolean compareAndSetPendingCount(int paramInt1, int paramInt2)
/*     */   {
/* 529 */     return U.compareAndSwapInt(this, PENDING, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public final int decrementPendingCountUnlessZero()
/*     */   {
/*     */     int i;
/*     */     
/*     */ 
/* 540 */     while (((i = this.pending) != 0) && (!U.compareAndSwapInt(this, PENDING, i, i - 1))) {}
/*     */     
/* 542 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final CountedCompleter<?> getRoot()
/*     */   {
/* 552 */     Object localObject = this;
/* 553 */     CountedCompleter localCountedCompleter; while ((localCountedCompleter = ((CountedCompleter)localObject).completer) != null)
/* 554 */       localObject = localCountedCompleter;
/* 555 */     return (CountedCompleter<?>)localObject;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void tryComplete()
/*     */   {
/* 565 */     CountedCompleter localCountedCompleter1 = this;CountedCompleter localCountedCompleter2 = localCountedCompleter1;
/*     */     int i;
/* 567 */     do { while ((i = localCountedCompleter1.pending) == 0) {
/* 568 */         localCountedCompleter1.onCompletion(localCountedCompleter2);
/* 569 */         if ((localCountedCompleter1 = (localCountedCompleter2 = localCountedCompleter1).completer) == null) {
/* 570 */           localCountedCompleter2.quietlyComplete();
/* 571 */           return;
/*     */         }
/*     */       }
/* 574 */     } while (!U.compareAndSwapInt(localCountedCompleter1, PENDING, i, i - 1));
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
/*     */   public final void propagateCompletion()
/*     */   {
/* 589 */     CountedCompleter localCountedCompleter1 = this;CountedCompleter localCountedCompleter2 = localCountedCompleter1;
/*     */     int i;
/* 591 */     do { while ((i = localCountedCompleter1.pending) == 0) {
/* 592 */         if ((localCountedCompleter1 = (localCountedCompleter2 = localCountedCompleter1).completer) == null) {
/* 593 */           localCountedCompleter2.quietlyComplete();
/* 594 */           return;
/*     */         }
/*     */       }
/* 597 */     } while (!U.compareAndSwapInt(localCountedCompleter1, PENDING, i, i - 1));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void complete(T paramT)
/*     */   {
/* 623 */     setRawResult(paramT);
/* 624 */     onCompletion(this);
/* 625 */     quietlyComplete();
/* 626 */     CountedCompleter localCountedCompleter; if ((localCountedCompleter = this.completer) != null) {
/* 627 */       localCountedCompleter.tryComplete();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public final CountedCompleter<?> firstComplete()
/*     */   {
/*     */     int i;
/*     */     
/*     */ 
/*     */     do
/*     */     {
/* 641 */       if ((i = this.pending) == 0)
/* 642 */         return this;
/* 643 */     } while (!U.compareAndSwapInt(this, PENDING, i, i - 1));
/* 644 */     return null;
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
/*     */   public final CountedCompleter<?> nextComplete()
/*     */   {
/*     */     CountedCompleter localCountedCompleter;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 667 */     if ((localCountedCompleter = this.completer) != null) {
/* 668 */       return localCountedCompleter.firstComplete();
/*     */     }
/* 670 */     quietlyComplete();
/* 671 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void quietlyCompleteRoot()
/*     */   {
/* 679 */     Object localObject = this;
/* 680 */     for (;;) { CountedCompleter localCountedCompleter; if ((localCountedCompleter = ((CountedCompleter)localObject).completer) == null) {
/* 681 */         ((CountedCompleter)localObject).quietlyComplete();
/* 682 */         return;
/*     */       }
/* 684 */       localObject = localCountedCompleter;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void internalPropagateException(Throwable paramThrowable)
/*     */   {
/* 692 */     CountedCompleter localCountedCompleter1 = this;CountedCompleter localCountedCompleter2 = localCountedCompleter1;
/*     */     
/* 694 */     while ((localCountedCompleter1.onExceptionalCompletion(paramThrowable, localCountedCompleter2)) && ((localCountedCompleter1 = (localCountedCompleter2 = localCountedCompleter1).completer) != null) && (localCountedCompleter1.status >= 0) && (localCountedCompleter1.recordExceptionalCompletion(paramThrowable) == Integer.MIN_VALUE)) {}
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final boolean exec()
/*     */   {
/* 703 */     compute();
/* 704 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public T getRawResult()
/*     */   {
/* 716 */     return null;
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
/*     */   static
/*     */   {
/*     */     try
/*     */     {
/* 732 */       U = getUnsafe();
/* 733 */       PENDING = U.objectFieldOffset(CountedCompleter.class.getDeclaredField("pending"));
/*     */     }
/*     */     catch (Exception localException) {
/* 736 */       throw new Error(localException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static Unsafe getUnsafe()
/*     */   {
/*     */     try
/*     */     {
/* 749 */       return Unsafe.getUnsafe();
/*     */     } catch (SecurityException localSecurityException) {
/*     */       try {
/* 752 */         (Unsafe)AccessController.doPrivileged(new PrivilegedExceptionAction()
/*     */         {
/*     */           public Unsafe run() throws Exception {
/* 755 */             Class localClass = Unsafe.class;
/* 756 */             for (Field localField : localClass.getDeclaredFields()) {
/* 757 */               localField.setAccessible(true);
/* 758 */               Object localObject = localField.get(null);
/* 759 */               if (localClass.isInstance(localObject))
/* 760 */                 return (Unsafe)localClass.cast(localObject);
/*     */             }
/* 762 */             throw new NoSuchFieldError("the Unsafe");
/*     */           }
/*     */         });
/* 765 */       } catch (PrivilegedActionException localPrivilegedActionException) { throw new RuntimeException("Could not initialize intrinsics", localPrivilegedActionException.getCause());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public abstract void compute();
/*     */   
/*     */   public void onCompletion(CountedCompleter<?> paramCountedCompleter) {}
/*     */   
/*     */   protected void setRawResult(T paramT) {}
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\internal\chmv8\CountedCompleter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */