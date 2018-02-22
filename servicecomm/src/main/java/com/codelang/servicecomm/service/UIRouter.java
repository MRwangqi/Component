package com.codelang.servicecomm.service;

/**
 * @author wangqi
 * @since 2018/1/21 16:05
 */

public class UIRouter {

    private UIRouter() {
    }

    private static class InnerRouterUI {
        static UIRouter UIRouter = new UIRouter();
    }

    public static UIRouter getInstance() {
        return InnerRouterUI.UIRouter;
    }
}
