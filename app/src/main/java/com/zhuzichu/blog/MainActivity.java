package com.zhuzichu.blog;

import com.zhuzichu.module_base.base.BaseActivity;
import com.zhuzichu.module_base.base.BaseFragment;
import com.zhuzichu.module_main.fragment.MainFragment;

public class MainActivity extends BaseActivity {

    @Override
    public BaseFragment setRootFragment() {
        return MainFragment.newInstance();
    }
}
