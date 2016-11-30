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
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 *
 * AdapterDelegate的管理者
 * @author bingo
 */
public class XKAdapterDelegatesManager<T> {

  SparseArrayCompat<AdapterDelegate<T>> delegates = new SparseArrayCompat();

  public XKAdapterDelegatesManager<T> addDelegate(@NonNull AdapterDelegate<T> delegate) {
    return addDelegate(delegate, false);
  }

  public XKAdapterDelegatesManager<T> addDelegate(@NonNull AdapterDelegate<T> delegate,
      boolean allowReplacingDelegate) {

    if (delegate == null) {
      throw new NullPointerException("AdapterDelegate is null!");
    }

    int viewType = delegate.getItemViewType();
    if (!allowReplacingDelegate && delegates.get(viewType) != null) {
      throw new IllegalArgumentException(
          "An AdapterDelegate is already registered for the viewType = " + viewType
              + ". Already registered AdapterDelegate is " + delegates.get(viewType));
    }

    delegates.put(viewType, delegate);

    return this;
  }

  public XKAdapterDelegatesManager<T> removeDelegate(@NonNull AdapterDelegate<T> delegate) {

    if (delegate == null) {
      throw new NullPointerException("AdapterDelegate is null");
    }

    AdapterDelegate<T> queried = delegates.get(delegate.getItemViewType());
    if (queried != null && queried == delegate) {
      delegates.remove(delegate.getItemViewType());
    }
    return this;
  }

  public XKAdapterDelegatesManager<T> removeDelegate(int viewType) {
    delegates.remove(viewType);
    return this;
  }

  public int getItemViewType(@NonNull T items, int position) {

    if (items == null) {
      throw new NullPointerException("Items datasource is null!");
    }

    int delegatesCount = delegates.size();
    for (int i = 0; i < delegatesCount; i++) {
      AdapterDelegate<T> delegate = delegates.valueAt(i);
      if (delegate.isForViewType(items, position)) {
        return delegate.getItemViewType();
      }
    }

    throw new IllegalArgumentException(
        "No AdapterDelegate added that matches position=" + position + " in data source");
  }

  @NonNull public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    AdapterDelegate<T> delegate = delegates.get(viewType);
    if (delegate == null) {
      throw new NullPointerException("No AdapterDelegate added for ViewType " + viewType);
    }

    RecyclerView.ViewHolder vh = delegate.onCreateViewHolder(parent);
    if (vh == null) {
      throw new NullPointerException(
          "ViewHolder returned from AdapterDelegate " + delegate + " for ViewType =" + viewType
              + " is null!");
    }
    return vh;
  }

  public void onBindViewHolder(@NonNull T items, int position, @NonNull RecyclerView.ViewHolder viewHolder) {

    AdapterDelegate<T> delegate = delegates.get(viewHolder.getItemViewType());
    if (delegate == null) {
      throw new NullPointerException(
          "No AdapterDelegate added for ViewType " + viewHolder.getItemViewType());
    }

    delegate.onBindViewHolder(items, position, viewHolder);
  }
}
