/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ public class PersistentCollection
/*     */ {
/*     */   private IDataManager b;
/*  20 */   protected Map<String, PersistentBase> a = Maps.newHashMap();
/*  21 */   public List<PersistentBase> c = Lists.newArrayList();
/*  22 */   private Map<String, Short> d = Maps.newHashMap();
/*     */   
/*     */   public PersistentCollection(IDataManager idatamanager) {
/*  25 */     this.b = idatamanager;
/*  26 */     b();
/*     */   }
/*     */   
/*     */   public PersistentBase get(Class<? extends PersistentBase> oclass, String s) {
/*  30 */     PersistentBase persistentbase = (PersistentBase)this.a.get(s);
/*     */     
/*  32 */     if (persistentbase != null) {
/*  33 */       return persistentbase;
/*     */     }
/*  35 */     if (this.b != null) {
/*     */       try {
/*  37 */         File file = this.b.getDataFile(s);
/*     */         
/*  39 */         if ((file != null) && (file.exists())) {
/*     */           try {
/*  41 */             persistentbase = (PersistentBase)oclass.getConstructor(new Class[] { String.class }).newInstance(new Object[] { s });
/*     */           } catch (Exception exception) {
/*  43 */             throw new RuntimeException("Failed to instantiate " + oclass.toString(), exception);
/*     */           }
/*     */           
/*  46 */           FileInputStream fileinputstream = new FileInputStream(file);
/*  47 */           NBTTagCompound nbttagcompound = NBTCompressedStreamTools.a(fileinputstream);
/*     */           
/*  49 */           fileinputstream.close();
/*  50 */           persistentbase.a(nbttagcompound.getCompound("data"));
/*     */         }
/*     */       } catch (Exception exception1) {
/*  53 */         exception1.printStackTrace();
/*     */       }
/*     */     }
/*     */     
/*  57 */     if (persistentbase != null) {
/*  58 */       this.a.put(s, persistentbase);
/*  59 */       this.c.add(persistentbase);
/*     */     }
/*     */     
/*  62 */     return persistentbase;
/*     */   }
/*     */   
/*     */   public void a(String s, PersistentBase persistentbase)
/*     */   {
/*  67 */     if (this.a.containsKey(s)) {
/*  68 */       this.c.remove(this.a.remove(s));
/*     */     }
/*     */     
/*  71 */     this.a.put(s, persistentbase);
/*  72 */     this.c.add(persistentbase);
/*     */   }
/*     */   
/*     */   public void a() {
/*  76 */     for (int i = 0; i < this.c.size(); i++) {
/*  77 */       PersistentBase persistentbase = (PersistentBase)this.c.get(i);
/*     */       
/*  79 */       if (persistentbase.d()) {
/*  80 */         a(persistentbase);
/*  81 */         persistentbase.a(false);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void a(PersistentBase persistentbase)
/*     */   {
/*  88 */     if (this.b != null) {
/*     */       try {
/*  90 */         File file = this.b.getDataFile(persistentbase.id);
/*     */         
/*  92 */         if (file != null) {
/*  93 */           NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */           
/*  95 */           persistentbase.b(nbttagcompound);
/*  96 */           NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*     */           
/*  98 */           nbttagcompound1.set("data", nbttagcompound);
/*  99 */           FileOutputStream fileoutputstream = new FileOutputStream(file);
/*     */           
/* 101 */           NBTCompressedStreamTools.a(nbttagcompound1, fileoutputstream);
/* 102 */           fileoutputstream.close();
/*     */         }
/*     */       } catch (Exception exception) {
/* 105 */         exception.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void b()
/*     */   {
/*     */     try {
/* 113 */       this.d.clear();
/* 114 */       if (this.b == null) {
/* 115 */         return;
/*     */       }
/*     */       
/* 118 */       File file = this.b.getDataFile("idcounts");
/*     */       
/* 120 */       if ((file != null) && (file.exists())) {
/* 121 */         DataInputStream datainputstream = new DataInputStream(new FileInputStream(file));
/* 122 */         NBTTagCompound nbttagcompound = NBTCompressedStreamTools.a(datainputstream);
/*     */         
/* 124 */         datainputstream.close();
/* 125 */         Iterator iterator = nbttagcompound.c().iterator();
/*     */         
/* 127 */         while (iterator.hasNext()) {
/* 128 */           String s = (String)iterator.next();
/* 129 */           NBTBase nbtbase = nbttagcompound.get(s);
/*     */           
/* 131 */           if ((nbtbase instanceof NBTTagShort)) {
/* 132 */             NBTTagShort nbttagshort = (NBTTagShort)nbtbase;
/* 133 */             short short0 = nbttagshort.e();
/*     */             
/* 135 */             this.d.put(s, Short.valueOf(short0));
/*     */           }
/*     */         }
/*     */       }
/*     */     } catch (Exception exception) {
/* 140 */       exception.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public int a(String s)
/*     */   {
/* 146 */     Short oshort = (Short)this.d.get(s);
/*     */     
/* 148 */     if (oshort == null) {
/* 149 */       oshort = Short.valueOf((short)0);
/*     */     } else {
/* 151 */       oshort = Short.valueOf((short)(oshort.shortValue() + 1));
/*     */     }
/*     */     
/* 154 */     this.d.put(s, oshort);
/* 155 */     if (this.b == null) {
/* 156 */       return oshort.shortValue();
/*     */     }
/*     */     try {
/* 159 */       File file = this.b.getDataFile("idcounts");
/*     */       
/* 161 */       if (file != null) {
/* 162 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 163 */         Iterator iterator = this.d.keySet().iterator();
/*     */         
/* 165 */         while (iterator.hasNext()) {
/* 166 */           String s1 = (String)iterator.next();
/* 167 */           short short0 = ((Short)this.d.get(s1)).shortValue();
/*     */           
/* 169 */           nbttagcompound.setShort(s1, short0);
/*     */         }
/*     */         
/* 172 */         DataOutputStream dataoutputstream = new DataOutputStream(new FileOutputStream(file));
/*     */         
/* 174 */         NBTCompressedStreamTools.a(nbttagcompound, dataoutputstream);
/* 175 */         dataoutputstream.close();
/*     */       }
/*     */     } catch (Exception exception) {
/* 178 */       exception.printStackTrace();
/*     */     }
/*     */     
/* 181 */     return oshort.shortValue();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PersistentCollection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */