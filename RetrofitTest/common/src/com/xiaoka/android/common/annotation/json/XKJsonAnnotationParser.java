/**
 * Copyright © 2014 XiaoKa. All Rights Reserved
 */
package com.xiaoka.android.common.annotation.json;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

public class XKJsonAnnotationParser {

    private static void set(Field[] fields, JSONObject json, Object obj) {
        try {
            String key;
            for (Field field : fields) {
                if (!field.isAccessible())
                    field.setAccessible(true);
                // 如果是值注解,直接赋值
                if (field.isAnnotationPresent(XKJsonValue.class)) {
                    key = field.getAnnotation(XKJsonValue.class).value();
                    if (json.has(key)) {
                        //System.out.println("---------------- key " + key);
                        field.set(obj, json.get(key));
                    }
                    // JsonObject注解的话,进行递归赋值
                } else if (field.isAnnotationPresent(XKJsonObject.class)) {
                    key = field.getAnnotation(XKJsonObject.class).value();
                    if (json.has(key)) {
                        Object object = parser(field.getType(),
                                json.getJSONObject(key), obj);
                        field.set(obj, object);
                    }
                } else if (field.isAnnotationPresent(XKJsonArray.class)) {

                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T parser(Class<T> cl, JSONObject json, Object obj) {
        T newObject = null;
        try {
            if (null == json) {
                return cl.newInstance();
            }
            if (Modifier.isStatic(cl.getModifiers())) {// 静态内部类的处理
                //System.out.println("--------------静态类,..." + cl.getName());
                //newObject = (T) Class.forName(cl.getName()).newInstance();
                newObject = cl.getConstructor().newInstance();
            } else {
                // 实例内部类的处理
                if (null != obj) {
                    //System.out.println("--------------普通内部类,...");
                    newObject = cl.getConstructor(obj.getClass()).newInstance(
                            obj);
                } else {
                    //System.out.println("--------------普通类,...");
                    newObject = (T) cl.getConstructors()[0].newInstance();
                }
            }
            if (null != newObject) {
                Class<T> newClass = (Class<T>) newObject.getClass();
                set(newClass.getDeclaredFields(), json, newObject);
                Class<? super T> superClass = newClass.getSuperclass();
                set(superClass.getDeclaredFields(), json, newObject);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return newObject;
    }
}
