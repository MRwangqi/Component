package com.codelang.mainprojects;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.codelang.servicecomm.service.Router;
import com.codelang.servicecomm.service.home.HomeService;
import com.codelang.servicecomm.service.news.NewsService;


public class MainActivity extends FragmentActivity {

    Fragment homeFragment;
    Fragment newsFragment;

    FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = getSupportFragmentManager();


        homeFragment = createHomeFragment();
        newsFragment = createNewsFragment();


        manager.beginTransaction()
                .add(R.id.main_layout, homeFragment)
                .add(R.id.main_layout, newsFragment)
                .hide(newsFragment)
                .show(homeFragment)
                .commitAllowingStateLoss();

    }


    public void tabClick(View view) {
        FragmentTransaction transaction = manager.beginTransaction();

        switch (view.getId()) {
            case R.id.main_home:
                transaction.show(homeFragment);
                transaction.hide(newsFragment);
                break;
            case R.id.main_news:
                transaction.show(newsFragment);
                transaction.hide(homeFragment);
                break;
            default:
        }
        transaction.commitAllowingStateLoss();
    }


    /**
     * 创建HomeFragment
     *
     * @return
     */
    public Fragment createHomeFragment() {
        Router router = Router.getInstance();
        if (router.getService(HomeService.class.getSimpleName()) != null) {
            HomeService homeService = (HomeService) router.getService(HomeService.class.getSimpleName());
            return homeService.getHomeFragment();
        }
        return null;
    }

    public Fragment createNewsFragment() {
        Router router = Router.getInstance();
        if (router.getService(NewsService.class.getSimpleName()) != null) {
            NewsService newsService = (NewsService) router.getService(NewsService.class.getSimpleName());
            return newsService.getNewsFragment();
        }
        return null;
    }
}
