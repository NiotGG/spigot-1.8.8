package net.minecraft.server.v1_8_R3;

import java.util.Collection;
import java.util.UUID;

public abstract interface AttributeInstance
{
  public abstract IAttribute getAttribute();
  
  public abstract double b();
  
  public abstract void setValue(double paramDouble);
  
  public abstract Collection<AttributeModifier> a(int paramInt);
  
  public abstract Collection<AttributeModifier> c();
  
  public abstract boolean a(AttributeModifier paramAttributeModifier);
  
  public abstract AttributeModifier a(UUID paramUUID);
  
  public abstract void b(AttributeModifier paramAttributeModifier);
  
  public abstract void c(AttributeModifier paramAttributeModifier);
  
  public abstract double getValue();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\AttributeInstance.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */