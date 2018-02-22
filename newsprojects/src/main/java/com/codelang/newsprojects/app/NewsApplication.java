package com.codelang.newsprojects.app;

import android.util.Log;

import com.codelang.newsprojects.imp.NewsFragmentImp;
import com.codelang.servicecomm.service.IApplicationLike;
import com.codelang.servicecomm.service.Router;
import com.codelang.servicecomm.service.news.NewsService;

/**
 * @author wangqi
 * @since 2018/1/21 13:26
 */

public class NewsApplication implements IApplicationLike {
    @Override
    public void onCreate() {
        Log.i("news", "NewsApplication-------onCreate");

        Router.getInstance().addService(NewsService.class.getSimpleName(), new NewsFragmentImp());
    }

    @Override
    public void onStop() {
        Router.getInstance().removeService(NewsService.class.getSimpleName());
    }
}
