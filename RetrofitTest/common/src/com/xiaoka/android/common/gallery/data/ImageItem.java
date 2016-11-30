/**
 * Copyright © 2014 XiaoKa. All Rights Reserved
 */
package com.xiaoka.android.common.gallery.data;

import java.io.Serializable;

/**
 * 一个图片对象
 * 
 * @version 1.0
 * @since 1.0
 * @author Shun
 * 
 */
public class ImageItem implements Serializable {
	private static final long serialVersionUID = -5991321565426828487L;
	public String imageId;
	public String thumbnailPath;
	public String imagePath;
}
