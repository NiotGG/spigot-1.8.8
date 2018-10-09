/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.UUID;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.spigotmc.SpigotConfig;
/*     */ 
/*     */ public class GenericAttributes
/*     */ {
/*  11 */   private static final Logger f = ;
/*     */   
/*  13 */   public static final IAttribute maxHealth = new AttributeRanged(null, "generic.maxHealth", 20.0D, 0.1D, SpigotConfig.maxHealth).a("Max Health").a(true);
/*  14 */   public static final IAttribute FOLLOW_RANGE = new AttributeRanged(null, "generic.followRange", 32.0D, 0.0D, 2048.0D).a("Follow Range");
/*  15 */   public static final IAttribute c = new AttributeRanged(null, "generic.knockbackResistance", 0.0D, 0.0D, 1.0D).a("Knockback Resistance");
/*  16 */   public static final IAttribute MOVEMENT_SPEED = new AttributeRanged(null, "generic.movementSpeed", 0.699999988079071D, 0.0D, SpigotConfig.movementSpeed).a("Movement Speed").a(true);
/*  17 */   public static final IAttribute ATTACK_DAMAGE = new AttributeRanged(null, "generic.attackDamage", 2.0D, 0.0D, SpigotConfig.attackDamage);
/*     */   
/*     */   public static NBTTagList a(AttributeMapBase attributemapbase)
/*     */   {
/*  21 */     NBTTagList nbttaglist = new NBTTagList();
/*  22 */     Iterator iterator = attributemapbase.a().iterator();
/*     */     
/*  24 */     while (iterator.hasNext()) {
/*  25 */       AttributeInstance attributeinstance = (AttributeInstance)iterator.next();
/*     */       
/*  27 */       nbttaglist.add(a(attributeinstance));
/*     */     }
/*     */     
/*  30 */     return nbttaglist;
/*     */   }
/*     */   
/*     */   private static NBTTagCompound a(AttributeInstance attributeinstance) {
/*  34 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  35 */     IAttribute iattribute = attributeinstance.getAttribute();
/*     */     
/*  37 */     nbttagcompound.setString("Name", iattribute.getName());
/*  38 */     nbttagcompound.setDouble("Base", attributeinstance.b());
/*  39 */     Collection collection = attributeinstance.c();
/*     */     
/*  41 */     if ((collection != null) && (!collection.isEmpty())) {
/*  42 */       NBTTagList nbttaglist = new NBTTagList();
/*  43 */       Iterator iterator = collection.iterator();
/*     */       
/*  45 */       while (iterator.hasNext()) {
/*  46 */         AttributeModifier attributemodifier = (AttributeModifier)iterator.next();
/*     */         
/*  48 */         if (attributemodifier.e()) {
/*  49 */           nbttaglist.add(a(attributemodifier));
/*     */         }
/*     */       }
/*     */       
/*  53 */       nbttagcompound.set("Modifiers", nbttaglist);
/*     */     }
/*     */     
/*  56 */     return nbttagcompound;
/*     */   }
/*     */   
/*     */   private static NBTTagCompound a(AttributeModifier attributemodifier) {
/*  60 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */     
/*  62 */     nbttagcompound.setString("Name", attributemodifier.b());
/*  63 */     nbttagcompound.setDouble("Amount", attributemodifier.d());
/*  64 */     nbttagcompound.setInt("Operation", attributemodifier.c());
/*  65 */     nbttagcompound.setLong("UUIDMost", attributemodifier.a().getMostSignificantBits());
/*  66 */     nbttagcompound.setLong("UUIDLeast", attributemodifier.a().getLeastSignificantBits());
/*  67 */     return nbttagcompound;
/*     */   }
/*     */   
/*     */   public static void a(AttributeMapBase attributemapbase, NBTTagList nbttaglist) {
/*  71 */     for (int i = 0; i < nbttaglist.size(); i++) {
/*  72 */       NBTTagCompound nbttagcompound = nbttaglist.get(i);
/*  73 */       AttributeInstance attributeinstance = attributemapbase.a(nbttagcompound.getString("Name"));
/*     */       
/*  75 */       if (attributeinstance != null) {
/*  76 */         a(attributeinstance, nbttagcompound);
/*     */       } else {
/*  78 */         f.warn("Ignoring unknown attribute '" + nbttagcompound.getString("Name") + "'");
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static void a(AttributeInstance attributeinstance, NBTTagCompound nbttagcompound)
/*     */   {
/*  85 */     attributeinstance.setValue(nbttagcompound.getDouble("Base"));
/*  86 */     if (nbttagcompound.hasKeyOfType("Modifiers", 9)) {
/*  87 */       NBTTagList nbttaglist = nbttagcompound.getList("Modifiers", 10);
/*     */       
/*  89 */       for (int i = 0; i < nbttaglist.size(); i++) {
/*  90 */         AttributeModifier attributemodifier = a(nbttaglist.get(i));
/*     */         
/*  92 */         if (attributemodifier != null) {
/*  93 */           AttributeModifier attributemodifier1 = attributeinstance.a(attributemodifier.a());
/*     */           
/*  95 */           if (attributemodifier1 != null) {
/*  96 */             attributeinstance.c(attributemodifier1);
/*     */           }
/*     */           
/*  99 */           attributeinstance.b(attributemodifier);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static AttributeModifier a(NBTTagCompound nbttagcompound)
/*     */   {
/* 107 */     UUID uuid = new UUID(nbttagcompound.getLong("UUIDMost"), nbttagcompound.getLong("UUIDLeast"));
/*     */     try
/*     */     {
/* 110 */       return new AttributeModifier(uuid, nbttagcompound.getString("Name"), nbttagcompound.getDouble("Amount"), nbttagcompound.getInt("Operation"));
/*     */     } catch (Exception exception) {
/* 112 */       f.warn("Unable to create attribute: " + exception.getMessage()); }
/* 113 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\GenericAttributes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */