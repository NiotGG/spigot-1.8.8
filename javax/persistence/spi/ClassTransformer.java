package javax.persistence.spi;

import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public abstract interface ClassTransformer
{
  public abstract byte[] transform(ClassLoader paramClassLoader, String paramString, Class<?> paramClass, ProtectionDomain paramProtectionDomain, byte[] paramArrayOfByte)
    throws IllegalClassFormatException;
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\javax\persistence\spi\ClassTransformer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */