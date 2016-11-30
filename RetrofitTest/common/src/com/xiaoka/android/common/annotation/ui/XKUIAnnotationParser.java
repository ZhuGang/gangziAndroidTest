/**
 * Copyright © 2014 XiaoKa. All Rights Reserved
 */
package com.xiaoka.android.common.annotation.ui;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * UI注解解析器
 *
 * @author Shun
 * @version 1.0
 * @since 1.0
 */
public final class XKUIAnnotationParser {

    /**
     * 解析Activity<br/>
     * 如果设置了{@link XKLayout}注解的话,会自动设置setContentView.没有的话,需在setContentView之后调用此接口
     *
     * @param act Activity
     */
    public static View parserActivity(Activity act) {
        if (null == act)
            return null;
        Class<?> cl = act.getClass();
        View root = null;
        if (isXKLayout(cl)) {
            XKLayout layout = cl.getAnnotation(XKLayout.class);
            root = LayoutInflater.from(act).inflate(layout.value(), null);
            act.setContentView(root);
        }
        View decorView = act.getWindow().getDecorView();
        initViews(cl.getDeclaredFields(), decorView, act);
        initOnClick(cl.getDeclaredMethods(), decorView, act);

        return root;
    }

    /**
     * 解析包含title的Actiivty，此时不会自动给设置setContentView。需要用返回的View手动设置。
     * @param act
     * @return
     */
    public static View parserTitleActivity(Activity act) {
        if (null == act) return null;
        Class<?> cl = act.getClass();
        View root = null;
        if (isXKLayout(cl)) {
            XKLayout layout = cl.getAnnotation(XKLayout.class);
            root = LayoutInflater.from(act).inflate(layout.value(), null);
        }
        initViews(cl.getDeclaredFields(), root, act);
        initOnClick(cl.getDeclaredMethods(), root, act);

        return root;
    }

    /**
     * 解析Class<br/>
     * 解析指定Class中所有的UI注解
     *
     * @param obj  任意对象
     * @param root UI注解的根View
     */
    public static void parserClassByView(Object obj, View root) {
        if (null == obj || null == root)
            return;
        Class<?> cl = obj.getClass();
        initViews(cl.getDeclaredFields(), root, obj);
        initOnClick(cl.getDeclaredMethods(), root, obj);
    }

    /**
     * 解析Fragment<br/>
     * 解析Fragment中所有的UI注解
     *
     * @param fragment
     * @param group
     * @return
     */
    public static View parserFragment(Fragment fragment, ViewGroup group) {
        Class<?> cl = fragment.getClass();
        View view = null;
        if (isXKLayout(cl)) {
            XKLayout layout = cl.getAnnotation(XKLayout.class);
            view = fragment.getActivity().getLayoutInflater().inflate(layout.value(), group, false);
        }
        if (null != view) {
            initViews(cl.getDeclaredFields(), view, fragment);
            initOnClick(cl.getDeclaredMethods(), view, fragment);
        }
        return view;
    }

    /**
     * 解析View
     *
     * @param view
     */
    public static void parserView(View view) {
        Class<?> cl = view.getClass();
        initViews(cl.getDeclaredFields(), view, view);
        initOnClick(cl.getDeclaredMethods(), view, view);
    }

    /**
     * 初始化注解View
     *
     * @param allField
     * @param root
     * @param object
     */
    static void initViews(Field[] allField, View root, Object object) {
        View v;
        for (Field field : allField) {
            // View注解
            if (isXKView(field)) {
                XKView xkView = field.getAnnotation(XKView.class);
                v = root.findViewById(xkView.value());
                if (null != v) {
                    try {
                        field.setAccessible(true);
                        field.set(object, v);
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 初始化注解Onclick
     *
     * @param allMethod
     * @param root
     * @param object
     */
    static void initOnClick(Method[] allMethod, View root, Object object) {
        for (Method method : allMethod) {
            if (isXKOnClick(method)) {
                XKOnClick onClick = method.getAnnotation(XKOnClick.class);
                ProxyClick click = new ProxyClick(method, object);
                int[] ids = onClick.value();
                for (int id : ids) {
                    root.findViewById(id).setOnClickListener(click);
                }
            }
        }
    }

    /**
     * 是否是<code>XKLayout</code>注解
     *
     * @param c
     * @return
     */
    static boolean isXKLayout(Class<?> c) {
        return c.isAnnotationPresent(XKLayout.class);
    }

    /**
     * 是否是<code>XKView</code>注解
     *
     * @param field
     * @return
     */
    static boolean isXKView(Field field) {
        return field.isAnnotationPresent(XKView.class);
    }

    /**
     * 是否是<code>XKOnClick</code>注解
     *
     * @param method
     * @return
     */
    static boolean isXKOnClick(Method method) {
        return method.isAnnotationPresent(XKOnClick.class);
    }

    /**
     * 代理的Click
     *
     * @author Shun
     * @version 1.0
     * @since 1.0
     */
    static class ProxyClick implements View.OnClickListener {

        private Method mMethod;
        private Object mReceiver;

        public ProxyClick(Method method, Object receiver) {
            mMethod = method;
            mReceiver = receiver;
        }

        @Override
        public void onClick(View v) {
            try {
                mMethod.setAccessible(true);
                mMethod.invoke(mReceiver, v);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

}
