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

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;


/**
 * 包含有DelegatesManager的Adapter的简单实现
 * @author bingo
 */
public abstract class AbsDelegationAdapter<T> extends RecyclerView.Adapter {

  protected XKAdapterDelegatesManager<T> mDelegatesManager = new XKAdapterDelegatesManager<T>();
  protected T items;

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return mDelegatesManager.onCreateViewHolder(parent, viewType);
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    mDelegatesManager.onBindViewHolder(items, position, holder);
  }

  @Override public int getItemViewType(int position) {
    return mDelegatesManager.getItemViewType(items, position);
  }

  /**
   * 得到对应的数据源
   * @return
   */
  public T getItems() {
    return items;
  }

  /**
   * 设置对应的数据源
   * @param items
   */
  public void setItems(T items) {
    this.items = items;
  }
}
