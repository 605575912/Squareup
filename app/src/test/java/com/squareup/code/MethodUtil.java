package com.squareup.code;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/08/09 0009.
 */

public class MethodUtil {
    public static Method getMethod(Object instance, String methodName,Class<?>... parameterTypes)
            throws NoSuchMethodException {
        Method accessMethod = getMethod(instance.getClass(), methodName, parameterTypes);
        //参数值为true，禁用访问控制检查
        accessMethod.setAccessible(true);
        return accessMethod;
    }

    private static Method getMethod(Class thisClass, String methodName, Class<?>... parameterTypes)
            throws NoSuchMethodException {
        if (thisClass == null) {
            throw new NoSuchMethodException("Error method !");
        }

        try {
            return thisClass.getDeclaredMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            return getMethod(thisClass.getSuperclass(), methodName, parameterTypes);
        }
    }
}
