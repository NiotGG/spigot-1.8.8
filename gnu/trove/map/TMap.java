package gnu.trove.map;

import gnu.trove.function.TObjectFunction;
import gnu.trove.procedure.TObjectObjectProcedure;
import gnu.trove.procedure.TObjectProcedure;
import java.util.Map;

public abstract interface TMap<K, V>
  extends Map<K, V>
{
  public abstract V putIfAbsent(K paramK, V paramV);
  
  public abstract boolean forEachKey(TObjectProcedure<? super K> paramTObjectProcedure);
  
  public abstract boolean forEachValue(TObjectProcedure<? super V> paramTObjectProcedure);
  
  public abstract boolean forEachEntry(TObjectObjectProcedure<? super K, ? super V> paramTObjectObjectProcedure);
  
  public abstract boolean retainEntries(TObjectObjectProcedure<? super K, ? super V> paramTObjectObjectProcedure);
  
  public abstract void transformValues(TObjectFunction<V, V> paramTObjectFunction);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\map\TMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */