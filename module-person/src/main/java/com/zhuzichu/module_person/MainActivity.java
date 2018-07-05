package com.zhuzichu.module_person;

import com.orhanobut.logger.Logger;
import com.zhuzichu.module_base.base.BaseATEActivity;
import com.zhuzichu.module_base.base.BaseFragment;
import com.zhuzichu.module_person.fragment.PersonFragment;

public class MainActivity extends BaseATEActivity {

    @Override
    public BaseFragment setRootFragment() {
        return PersonFragment.newInstance();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.i("Activity销毁了");
    }

}
