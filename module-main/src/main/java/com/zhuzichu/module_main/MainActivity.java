package com.zhuzichu.module_main;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zhuzichu.module_base.base.BaseActivity;
import com.zhuzichu.module_base.base.BaseFragment;
import com.zhuzichu.module_main.fragment.MainFragment;

@Route(path = "/main/index")
public class MainActivity extends BaseActivity {


    @Override
    public BaseFragment setRootFragment() {
        return MainFragment.newInstance();
    }
}
