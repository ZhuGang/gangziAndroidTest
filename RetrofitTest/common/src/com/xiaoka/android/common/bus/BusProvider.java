package com.xiaoka.android.common.bus;

import com.squareup.otto.Bus;

/**
 * @author bingo
 * @version 1.2.0
 * @date 15/8/31
 */
public final class BusProvider {
    private static final Bus BUS = new Bus();

    public static Bus getInstance() {
        return BUS;
    }

    private BusProvider() {
        // No instances.
    }
}
