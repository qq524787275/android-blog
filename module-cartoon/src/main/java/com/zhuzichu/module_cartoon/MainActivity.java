package com.zhuzichu.module_cartoon;

import com.zhuzichu.module_base.base.BaseActivity;
import com.zhuzichu.module_base.base.BaseFragment;
import com.zhuzichu.module_cartoon.fragment.CartoonFragment;


public class MainActivity extends BaseActivity {

    @Override
    public BaseFragment setRootFragment() {
        return CartoonFragment.newInstance();
    }
}
