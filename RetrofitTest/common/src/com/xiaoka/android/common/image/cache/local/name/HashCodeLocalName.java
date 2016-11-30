/**
 * Copyright © 2014 XiaoKa. All Rights Reserved
 */
package com.xiaoka.android.common.image.cache.local.name;

/**
 * 哈希Code做为文件名存储本地
 * 
 * @version 1.0
 * @since 1.0
 * @author Shun
 * 
 */
public class HashCodeLocalName implements LocalName {
	@Override
	public String getFileName(String url) {
		return String.valueOf(url.hashCode());
	}

}
