package com.codelang.servicecomm.service;

import android.text.TextUtils;

import java.util.HashMap;

/**
 * @author wangqi
 * @since 2018/1/21 13:27
 */

public class Router {
    HashMap<String, IApplicationLike> apps = new HashMap<>();

    HashMap<String, Object> services = new HashMap<>();

    private Router() {
    }

    private static class InnerRouter {
        static Router router = new Router();
    }

    public static Router getInstance() {
        return InnerRouter.router;
    }


    public void registerApplication(String path) {
        if (TextUtils.isEmpty(path)) return;

        if (apps.keySet().contains(path)) return;

        try {
            IApplicationLike like = (IApplicationLike) Class.forName(path).newInstance();
            like.onCreate();
            apps.put(path, like);
        } catch (Exception e) {
            throw new RuntimeException("注册的application路径不正确" + path);
        }
    }

    public void unRegisterApplication(String path) {
        if (TextUtils.isEmpty(path)) return;

        if (apps.keySet().contains(path)) {
            apps.get(path).onStop();
            apps.remove(path);
        }
    }

    public synchronized void addService(String serviceName, Object serviceImpl) {
        if (serviceName == null || serviceImpl == null) {
            return;
        }
        services.put(serviceName, serviceImpl);
    }

    public synchronized Object getService(String serviceName) {
        if (serviceName == null) {
            return null;
        }
        return services.get(serviceName);
    }

    public synchronized void removeService(String serviceName) {
        if (serviceName == null) {
            return;
        }
        services.remove(serviceName);
    }


}
