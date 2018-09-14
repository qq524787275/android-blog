
/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 daimajia
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.zhuzichu.module_base.widget.animate;


import com.zhuzichu.module_base.widget.animate.attention.BounceAnimator;
import com.zhuzichu.module_base.widget.animate.attention.FlashAnimator;
import com.zhuzichu.module_base.widget.animate.attention.PulseAnimator;
import com.zhuzichu.module_base.widget.animate.attention.RubberBandAnimator;
import com.zhuzichu.module_base.widget.animate.attention.ShakeAnimator;
import com.zhuzichu.module_base.widget.animate.attention.StandUpAnimator;
import com.zhuzichu.module_base.widget.animate.attention.SwingAnimator;
import com.zhuzichu.module_base.widget.animate.attention.TadaAnimator;
import com.zhuzichu.module_base.widget.animate.attention.WaveAnimator;
import com.zhuzichu.module_base.widget.animate.attention.WobbleAnimator;
import com.zhuzichu.module_base.widget.animate.bouncing_entrances.BounceInAnimator;
import com.zhuzichu.module_base.widget.animate.bouncing_entrances.BounceInDownAnimator;
import com.zhuzichu.module_base.widget.animate.bouncing_entrances.BounceInLeftAnimator;
import com.zhuzichu.module_base.widget.animate.bouncing_entrances.BounceInRightAnimator;
import com.zhuzichu.module_base.widget.animate.bouncing_entrances.BounceInUpAnimator;
import com.zhuzichu.module_base.widget.animate.fading_entrances.FadeInAnimator;
import com.zhuzichu.module_base.widget.animate.fading_entrances.FadeInDownAnimator;
import com.zhuzichu.module_base.widget.animate.fading_entrances.FadeInLeftAnimator;
import com.zhuzichu.module_base.widget.animate.fading_entrances.FadeInRightAnimator;
import com.zhuzichu.module_base.widget.animate.fading_entrances.FadeInUpAnimator;
import com.zhuzichu.module_base.widget.animate.fading_exits.FadeOutAnimator;
import com.zhuzichu.module_base.widget.animate.fading_exits.FadeOutDownAnimator;
import com.zhuzichu.module_base.widget.animate.fading_exits.FadeOutLeftAnimator;
import com.zhuzichu.module_base.widget.animate.fading_exits.FadeOutRightAnimator;
import com.zhuzichu.module_base.widget.animate.fading_exits.FadeOutUpAnimator;
import com.zhuzichu.module_base.widget.animate.flippers.FlipInXAnimator;
import com.zhuzichu.module_base.widget.animate.flippers.FlipInYAnimator;
import com.zhuzichu.module_base.widget.animate.flippers.FlipOutXAnimator;
import com.zhuzichu.module_base.widget.animate.flippers.FlipOutYAnimator;
import com.zhuzichu.module_base.widget.animate.rotating_entrances.RotateInAnimator;
import com.zhuzichu.module_base.widget.animate.rotating_entrances.RotateInDownLeftAnimator;
import com.zhuzichu.module_base.widget.animate.rotating_entrances.RotateInDownRightAnimator;
import com.zhuzichu.module_base.widget.animate.rotating_entrances.RotateInUpLeftAnimator;
import com.zhuzichu.module_base.widget.animate.rotating_entrances.RotateInUpRightAnimator;
import com.zhuzichu.module_base.widget.animate.rotating_exits.RotateOutAnimator;
import com.zhuzichu.module_base.widget.animate.rotating_exits.RotateOutDownLeftAnimator;
import com.zhuzichu.module_base.widget.animate.rotating_exits.RotateOutDownRightAnimator;
import com.zhuzichu.module_base.widget.animate.rotating_exits.RotateOutUpLeftAnimator;
import com.zhuzichu.module_base.widget.animate.rotating_exits.RotateOutUpRightAnimator;
import com.zhuzichu.module_base.widget.animate.sliders.SlideInDownAnimator;
import com.zhuzichu.module_base.widget.animate.sliders.SlideInLeftAnimator;
import com.zhuzichu.module_base.widget.animate.sliders.SlideInRightAnimator;
import com.zhuzichu.module_base.widget.animate.sliders.SlideInUpAnimator;
import com.zhuzichu.module_base.widget.animate.sliders.SlideOutDownAnimator;
import com.zhuzichu.module_base.widget.animate.sliders.SlideOutLeftAnimator;
import com.zhuzichu.module_base.widget.animate.sliders.SlideOutRightAnimator;
import com.zhuzichu.module_base.widget.animate.sliders.SlideOutUpAnimator;
import com.zhuzichu.module_base.widget.animate.specials.RollInAnimator;
import com.zhuzichu.module_base.widget.animate.specials.RollOutAnimator;
import com.zhuzichu.module_base.widget.animate.zooming_entrances.ZoomInAnimator;
import com.zhuzichu.module_base.widget.animate.zooming_entrances.ZoomInDownAnimator;
import com.zhuzichu.module_base.widget.animate.zooming_entrances.ZoomInLeftAnimator;
import com.zhuzichu.module_base.widget.animate.zooming_entrances.ZoomInRightAnimator;
import com.zhuzichu.module_base.widget.animate.zooming_entrances.ZoomInUpAnimator;
import com.zhuzichu.module_base.widget.animate.zooming_exits.ZoomOutAnimator;
import com.zhuzichu.module_base.widget.animate.zooming_exits.ZoomOutDownAnimator;
import com.zhuzichu.module_base.widget.animate.zooming_exits.ZoomOutLeftAnimator;
import com.zhuzichu.module_base.widget.animate.zooming_exits.ZoomOutRightAnimator;
import com.zhuzichu.module_base.widget.animate.zooming_exits.ZoomOutUpAnimator;

public enum Techniques {



    Flash(FlashAnimator.class),
    Pulse(PulseAnimator.class),
    RubberBand(RubberBandAnimator.class),
    Shake(ShakeAnimator.class),
    Swing(SwingAnimator.class),
    Wobble(WobbleAnimator.class),
    Bounce(BounceAnimator.class),
    Tada(TadaAnimator.class),
    StandUp(StandUpAnimator.class),
    Wave(WaveAnimator.class),

    RollIn(RollInAnimator.class),
    RollOut(RollOutAnimator.class),

    BounceIn(BounceInAnimator.class),
    BounceInDown(BounceInDownAnimator.class),
    BounceInLeft(BounceInLeftAnimator.class),
    BounceInRight(BounceInRightAnimator.class),
    BounceInUp(BounceInUpAnimator.class),

    FadeIn(FadeInAnimator.class),
    FadeInUp(FadeInUpAnimator.class),
    FadeInDown(FadeInDownAnimator.class),
    FadeInLeft(FadeInLeftAnimator.class),
    FadeInRight(FadeInRightAnimator.class),

    FadeOut(FadeOutAnimator.class),
    FadeOutDown(FadeOutDownAnimator.class),
    FadeOutLeft(FadeOutLeftAnimator.class),
    FadeOutRight(FadeOutRightAnimator.class),
    FadeOutUp(FadeOutUpAnimator.class),

    FlipInX(FlipInXAnimator.class),
    FlipOutX(FlipOutXAnimator.class),
    FlipInY(FlipInYAnimator.class),
    FlipOutY(FlipOutYAnimator.class),
    RotateIn(RotateInAnimator.class),
    RotateInDownLeft(RotateInDownLeftAnimator.class),
    RotateInDownRight(RotateInDownRightAnimator.class),
    RotateInUpLeft(RotateInUpLeftAnimator.class),
    RotateInUpRight(RotateInUpRightAnimator.class),

    RotateOut(RotateOutAnimator.class),
    RotateOutDownLeft(RotateOutDownLeftAnimator.class),
    RotateOutDownRight(RotateOutDownRightAnimator.class),
    RotateOutUpLeft(RotateOutUpLeftAnimator.class),
    RotateOutUpRight(RotateOutUpRightAnimator.class),

    SlideInLeft(SlideInLeftAnimator.class),
    SlideInRight(SlideInRightAnimator.class),
    SlideInUp(SlideInUpAnimator.class),
    SlideInDown(SlideInDownAnimator.class),

    SlideOutLeft(SlideOutLeftAnimator.class),
    SlideOutRight(SlideOutRightAnimator.class),
    SlideOutUp(SlideOutUpAnimator.class),
    SlideOutDown(SlideOutDownAnimator.class),

    ZoomIn(ZoomInAnimator.class),
    ZoomInDown(ZoomInDownAnimator.class),
    ZoomInLeft(ZoomInLeftAnimator.class),
    ZoomInRight(ZoomInRightAnimator.class),
    ZoomInUp(ZoomInUpAnimator.class),

    ZoomOut(ZoomOutAnimator.class),
    ZoomOutDown(ZoomOutDownAnimator.class),
    ZoomOutLeft(ZoomOutLeftAnimator.class),
    ZoomOutRight(ZoomOutRightAnimator.class),
    ZoomOutUp(ZoomOutUpAnimator.class);



    private Class animatorClazz;

    private Techniques(Class clazz) {
        animatorClazz = clazz;
    }

    public BaseViewAnimator getAnimator() {
        try {
            return (BaseViewAnimator) animatorClazz.newInstance();
        } catch (Exception e) {
            throw new Error("Can not init animatorClazz instance");
        }
    }
}
