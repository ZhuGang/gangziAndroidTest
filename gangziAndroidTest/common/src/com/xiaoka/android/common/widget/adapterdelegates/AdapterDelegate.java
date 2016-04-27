/*
 * Copyright (c) 2015 Hannes Dorfmann.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.xiaoka.android.common.widget.adapterdelegates;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * 这个代理提供Adapter需要的每种viewType的对应实现.
 * @author bingo
 */
public interface AdapterDelegate<T> {

  public int getItemViewType();

  /**
   * 用来判断对应的数据源是否适用于该AdapterDelegate
   * @param items
   * @param position
   * @return
   */
  public boolean isForViewType(@NonNull T items, int position);


  /**
   * 用来创建ViewHolder
   * @param parent
   * @return
   */
  @NonNull public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent);

  /**
   * 用来绑定数据
   * @param items
   * @param position
   * @param holder
   */
  public void onBindViewHolder(@NonNull T items, int position, @NonNull RecyclerView.ViewHolder holder);
}
