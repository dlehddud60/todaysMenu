package syworks.lib.mybatis;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
public class Util{
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object o){
		try{
			if(o==null) return true;
			if(o instanceof String){
				if(((String)o).length()==0){
					return true;
				}
			}else if(o instanceof Collection){
				if(((Collection)o).isEmpty()){
					return true;
				}
			}else if(o.getClass().isArray()){
				if(Array.getLength(o)==0){
					return true;
				}
			}else if(o instanceof Map){
				if(((Map)o).isEmpty()){
					return true;
				}
			}else{
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	public static boolean isNotEmpty(Object o){
		return !isEmpty(o);
	}
}
