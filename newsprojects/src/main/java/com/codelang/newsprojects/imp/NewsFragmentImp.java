package com.codelang.newsprojects.imp;

import android.support.v4.app.Fragment;

import com.codelang.newsprojects.ui.NewsFragment;
import com.codelang.servicecomm.service.news.NewsService;

/**
 * @author wangqi
 * @since 2018/1/21 15:56
 */

public class NewsFragmentImp implements NewsService {
    @Override
    public Fragment getNewsFragment() {
        return new NewsFragment();
    }
}
