/**
 * Copyright © 2014 XiaoKa. All Rights Reserved
 */
package com.xiaoka.android.common.annotation.db;

/**
 * <p>
 * <b>数据库字段注解</b>
 * </p>
 * 
 * @version 1.0
 * @since 1.0
 * @author Shun
 *
 */
public @interface XKColumn {
	/**
	 * 
	 * <p>
	 * <b>字段名称</b>
	 * </p>
	 * 
	 * @return
	 */
	String name();

	/**
	 * 
	 * <p>
	 * <b>字段类型</b>
	 * </p>
	 * 
	 * @return
	 */
	String columnType();

	/**
	 * 
	 * <p>
	 * <b>是否是主键</b>
	 * </p>
	 * 
	 * @return
	 */
	boolean primaryKey() default false;
}
