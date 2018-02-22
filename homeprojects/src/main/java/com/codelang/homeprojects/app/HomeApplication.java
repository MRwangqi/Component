package com.codelang.homeprojects.app;

import android.util.Log;

import com.codelang.homeprojects.imp.HomeFragmentImp;
import com.codelang.servicecomm.service.IApplicationLike;
import com.codelang.servicecomm.service.Router;
import com.codelang.servicecomm.service.home.HomeService;

/**
 * @author wangqi
 * @since 2018/1/21 13:24
 */

public class HomeApplication implements IApplicationLike {

    @Override
    public void onCreate() {
        Log.i("home","homeApplication-------onCreate");

        //向服务注册HomeFragment组件
        Router.getInstance().addService(HomeService.class.getSimpleName(),new HomeFragmentImp());

    }

    @Override
    public void onStop() {
        Router.getInstance().removeService(HomeService.class.getSimpleName());
    }
}
