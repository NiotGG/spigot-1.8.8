/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.common.base.Joiner;
/*    */ import com.google.common.base.Predicate;
/*    */ import com.google.common.base.Predicates;
/*    */ import com.google.common.collect.Lists;
/*    */ import com.google.common.collect.Maps;
/*    */ import java.lang.reflect.Array;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Map.Entry;
/*    */ 
/*    */ public class ShapeDetectorBuilder
/*    */ {
/* 16 */   private static final Joiner a = Joiner.on(",");
/*    */   
/* 18 */   private final List<String[]> b = Lists.newArrayList();
/* 19 */   private final Map<Character, Predicate<ShapeDetectorBlock>> c = Maps.newHashMap();
/*    */   private int d;
/*    */   private int e;
/*    */   
/*    */   private ShapeDetectorBuilder() {
/* 24 */     this.c.put(Character.valueOf(' '), Predicates.alwaysTrue());
/*    */   }
/*    */   
/*    */   public ShapeDetectorBuilder a(String... paramVarArgs) {
/* 28 */     if ((org.apache.commons.lang3.ArrayUtils.isEmpty(paramVarArgs)) || (org.apache.commons.lang3.StringUtils.isEmpty(paramVarArgs[0]))) {
/* 29 */       throw new IllegalArgumentException("Empty pattern for aisle");
/*    */     }
/*    */     
/* 32 */     if (this.b.isEmpty()) {
/* 33 */       this.d = paramVarArgs.length;
/* 34 */       this.e = paramVarArgs[0].length();
/*    */     }
/*    */     
/* 37 */     if (paramVarArgs.length != this.d) {
/* 38 */       throw new IllegalArgumentException("Expected aisle with height of " + this.d + ", but was given one with a height of " + paramVarArgs.length + ")");
/*    */     }
/*    */     
/* 41 */     for (String str : paramVarArgs) {
/* 42 */       if (str.length() != this.e) {
/* 43 */         throw new IllegalArgumentException("Not all rows in the given aisle are the correct width (expected " + this.e + ", found one with " + str.length() + ")");
/*    */       }
/* 45 */       for (char c1 : str.toCharArray()) {
/* 46 */         if (!this.c.containsKey(Character.valueOf(c1))) {
/* 47 */           this.c.put(Character.valueOf(c1), null);
/*    */         }
/*    */       }
/*    */     }
/*    */     
/* 52 */     this.b.add(paramVarArgs);
/*    */     
/* 54 */     return this;
/*    */   }
/*    */   
/*    */   public static ShapeDetectorBuilder a() {
/* 58 */     return new ShapeDetectorBuilder();
/*    */   }
/*    */   
/*    */   public ShapeDetectorBuilder a(char paramChar, Predicate<ShapeDetectorBlock> paramPredicate) {
/* 62 */     this.c.put(Character.valueOf(paramChar), paramPredicate);
/*    */     
/* 64 */     return this;
/*    */   }
/*    */   
/*    */   public ShapeDetector b() {
/* 68 */     return new ShapeDetector(c());
/*    */   }
/*    */   
/*    */   private Predicate<ShapeDetectorBlock>[][][] c()
/*    */   {
/* 73 */     d();
/*    */     
/* 75 */     Predicate[][][] arrayOfPredicate = (Predicate[][][])Array.newInstance(Predicate.class, new int[] { this.b.size(), this.d, this.e });
/*    */     
/* 77 */     for (int i = 0; i < this.b.size(); i++) {
/* 78 */       for (int j = 0; j < this.d; j++) {
/* 79 */         for (int k = 0; k < this.e; k++) {
/* 80 */           arrayOfPredicate[i][j][k] = ((Predicate)this.c.get(Character.valueOf(((String[])this.b.get(i))[j].charAt(k))));
/*    */         }
/*    */       }
/*    */     }
/*    */     
/* 85 */     return arrayOfPredicate;
/*    */   }
/*    */   
/*    */   private void d() {
/* 89 */     ArrayList localArrayList = Lists.newArrayList();
/*    */     
/* 91 */     for (Map.Entry localEntry : this.c.entrySet()) {
/* 92 */       if (localEntry.getValue() == null) {
/* 93 */         localArrayList.add(localEntry.getKey());
/*    */       }
/*    */     }
/*    */     
/* 97 */     if (!localArrayList.isEmpty()) {
/* 98 */       throw new IllegalStateException("Predicates for character(s) " + a.join(localArrayList) + " are missing");
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ShapeDetectorBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */