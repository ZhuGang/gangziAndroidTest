package com.test.gangzi.gangziandroidtest.protocol.helper;

import com.chediandian.business.app.XKApplication;
import com.chediandian.business.protocol.service.MyService;
import com.chediandian.business.protocol.service.OrderService;
import com.chediandian.business.protocol.service.PushService;
import com.chediandian.business.protocol.service.QuotationService;
import com.chediandian.business.protocol.service.UserService;

/**
 * @author bingo
 * @version 1.2.0
 * @date 15/9/30
 */
public class RetrofitHelper {

    /**
     * 根据restful接口得到请求网络的实例
     *
     * @param service
     * @param <T>
     * @return
     */
    public <T> T getService(Class<T> service) {
        return XKApplication.getRestClient().getService(service);
    }

    public UserService getUserService() {
        return getService(UserService.class);
    }

    public PushService getPushService() {
        return getService(PushService.class);
    }

    public QuotationService getQuotationService() {
        return getService(QuotationService.class);
    }

    public MyService getMyService() {
        return getService(MyService.class);
    }

    public OrderService getOrderService() {
        return getService(OrderService.class);
    }
}
