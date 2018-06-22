package com.zhuzichu.module_live;

import com.zhuzichu.module_base.base.BaseActivity;
import com.zhuzichu.module_base.base.BaseFragment;
import com.zhuzichu.module_live.fragment.LiveFragment;

public class MainActivity extends BaseActivity {


    @Override
    public BaseFragment setRootFragment() {
        return LiveFragment.newInstance();
    }
}
