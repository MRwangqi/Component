package com.codelang.homeprojects.imp;

import android.support.v4.app.Fragment;

import com.codelang.homeprojects.ui.HomeFragment;
import com.codelang.servicecomm.service.home.HomeService;

/**
 * @author wangqi
 * @since 2018/1/21 15:30
 */

public class HomeFragmentImp implements HomeService {
    @Override
    public Fragment getHomeFragment() {
        return new HomeFragment();
    }
}
