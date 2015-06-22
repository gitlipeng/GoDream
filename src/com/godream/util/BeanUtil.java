package com.godream.util;

import java.lang.reflect.Method;

public class BeanUtil {
	public static Object getValue(Object paramObject, String paramString) {
		Class localClass = paramObject.getClass();
		Method[] arrayOfMethod = null;
		if (localClass.getClassLoader() != null) {
			arrayOfMethod = localClass.getMethods();
		}else{
			return null;
		}
		for (int j = 0; j < arrayOfMethod.length; j++) {
			Method localMethod = arrayOfMethod[j];
			String str = localMethod.getName();
			if ((!("get" + paramString).equalsIgnoreCase(str))
					&& (!paramString.equalsIgnoreCase(str))) {
				continue;
			}
			try {
				Object localObject = localMethod.invoke(paramObject, null);
				return localObject;
			} catch (Exception localException) {
				throw new RuntimeException(localException);
			}
		}
		return null;
	}
}
