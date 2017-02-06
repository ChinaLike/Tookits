package com.like.layout;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.widget.CheckBox;

import com.like.helper.FrameAnimationController;
import com.like.utils.R;

/**
 * @ describe: 自定义CheckBox样式
 * @ author: Like on 2017-02-06.
 * @ email: 572919350@qq.com
 */

public class CheckSwitchButton extends CheckBox {
    /**
     * 画笔
     */
    private Paint mPaint;

    private int mClickTimeout;

    private int mTouchSlop;

    private Bitmap mBottom, mCurBtnPic, mBtnPressed, mBtnNormal;

    /**
     * 整体框架
     */
    private Bitmap mFrame;
    /**
     * 阴影层，不能点击的时候显示
     */
    private Bitmap mMask;

    /**
     * 开关圆形按钮的宽度
     */
    private float mBtnWidth;

    /**
     * 阴影的宽度
     */
    private float mMaskWidth;
    /**
     * 阴影的高度
     */
    private float mMaskHeight;

    /**
     * 开关打开的位置
     */
    private float mBtnOnPos;
    /**
     * 开关关闭的位置
     */
    private float mBtnOffPos;

    /**
     * 图片的绘制位置
     */
    private float mRealPos;
    /**
     * 按钮的位置
     */
    private float mBtnPos;
    /**
     * 开关是否可打开
     */
    private boolean mChecked = false;
    /**
     * 滑动速度
     */
    private float mVelocity;

    private final float VELOCITY = 350;
    /**
     * Y轴方向扩大的区域,增大点击区域
     */
    private float mExtendOffsetY;
    private final float EXTENDED_OFFSET_Y = 15;
    /**
     * 保存布局的矩阵
     */
    private RectF mSaveLayerRectF;
    /**
     * 布局之间叠层，好比背景和背景上的图片效果
     */
    private PorterDuffXfermode mXfermode;

    /**
     * 开关初始坐标
     */
    private float mBtnInitPos;

    /**
     * 最大透明度，就是不透明
     */
    private final int MAX_ALPHA = 255;
    /**
     * 当前透明度，这里主要用于如果控件的enable属性为false时候设置半透明 ，即不可以点击
     */
    private int mAlpha = MAX_ALPHA;

    private boolean mBroadcasting;

    /**
     * 开关状态切换监听接口
     */
    private OnCheckedChangeListener mOnCheckedChangeListener;

    private OnCheckedChangeListener mOnCheckedChangeWidgetListener;
    /**
     * 首次按下的Y
     */
    private float mFirstDownY;
    /**
     * 首次按下的X
     */
    private float mFirstDownX;
    private ViewParent mParent;
    private boolean mTurningOn;
    private PerformClick mPerformClick;
    /**
     * 判断是否在进行动画
     */
    private boolean mAnimating;
    private float mAnimationPosition;

    private float mAnimatedVelocity;

    public CheckSwitchButton(Context context) {
        this(context, null);
    }

    public CheckSwitchButton(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.checkboxStyle);
    }

    public CheckSwitchButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /**
     * 初始化参数
     * @param context
     */
    private void initView(Context context) {
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        Resources resources = context.getResources();
        mClickTimeout = ViewConfiguration.getPressedStateDuration() + ViewConfiguration.getTapTimeout();
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        /**获取位图*/

        mBottom = BitmapFactory.decodeResource(resources, R.drawable.checkswitch_bottom);
        mBtnPressed = BitmapFactory.decodeResource(resources,R.drawable.checkswitch_btn_pressed);
        mBtnNormal = BitmapFactory.decodeResource(resources,R.drawable.checkswitch_btn_unpressed);
        mFrame = BitmapFactory.decodeResource(resources, R.drawable.checkswitch_frame);
        mMask = BitmapFactory.decodeResource(resources, R.drawable.checkswitch_mask);

        mCurBtnPic = mBtnNormal;
        /**获取控件宽度*/
        mBtnWidth = mBtnPressed.getWidth();
        /**获取阴影的宽高*/
        mMaskWidth = mMask.getWidth();
        mMaskHeight = mMask.getHeight();
        /**获取开关  打开与关闭的位置*/
        mBtnOffPos = mBtnWidth / 2;
        mBtnOnPos = mMaskWidth - mBtnWidth / 2;
        /**判断起始位置,如果设定了mChecked为true，起始位置为 mBtnOnPos*/
        mBtnPos = mChecked ? mBtnOnPos : mBtnOffPos;
        mRealPos = getRealPos(mBtnPos);
        /**获取资源密度*/
        final float density = getResources().getDisplayMetrics().density;
        /**获取滑动速度*/
        mVelocity = (int) (VELOCITY * density + 0.5F);
        /**Y轴扩大区域*/
        mExtendOffsetY = (int) (EXTENDED_OFFSET_Y * density + 0.5F);
        /** 创建一个新的矩形与指定的坐标*/
        mSaveLayerRectF = new RectF(0, mExtendOffsetY, mMask.getWidth(), mMask.getHeight() + mExtendOffsetY);
        /**重叠区  PorterDuff.Mode.SRC_IN的意思：取两层绘制交集。显示上层*/
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    }

    /**
     * 将btnPos转换成RealPos
     *
     * @param mBtnPos
     * @return
     */
    private float getRealPos(float mBtnPos) {
        return mBtnPos - mBtnWidth / 2;
    }

    @Override
    public void setEnabled(boolean enabled) {
        mAlpha = enabled ? MAX_ALPHA : MAX_ALPHA / 2;
        super.setEnabled(enabled);
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    /**
     * 自动判断切换至相反的属性 : true -->false ;false -->true
     */
    public void toggle() {
        setChecked(!mChecked);
    }

    /**
     * 内部调用此方法设置checked状态，此方法会延迟执行各种回调函数，保证动画的流畅度
     *
     * @param checked
     */
    private void setCheckedDelayed(final boolean checked) {
        this.postDelayed(new Runnable() {
            @Override
            public void run() {
                setChecked(checked);
            }
        }, 10);
    }

    @Override
    public void setChecked(boolean checked) {
        if (mChecked != checked) {
            mChecked = checked;
            mBtnPos = checked ? mBtnOnPos : mBtnOffPos;
            mRealPos = getRealPos(mBtnPos);
            invalidate();
            if (mBroadcasting) {
                return;
            }
            mBroadcasting = true;
            if (mOnCheckedChangeListener != null) {
                mOnCheckedChangeListener.onCheckedChanged(CheckSwitchButton.this, mChecked);
            }

            if (mOnCheckedChangeWidgetListener != null) {
                mOnCheckedChangeWidgetListener.onCheckedChanged(CheckSwitchButton.this, mChecked);
            }
            mBroadcasting = false;
        }
    }

    /**
     * 设置状态改变监听
     */
    public void setOnCheckedChangeListenner(OnCheckedChangeListener listenner) {
        mOnCheckedChangeListener = listenner;
    }

    /**
     * 设置监听
     */
    void setOnCheckedChangeWidgetListener(OnCheckedChangeListener listener) {
        mOnCheckedChangeWidgetListener = listener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();
        float deltaX = Math.abs(x - mFirstDownX);
        float deltaY = Math.abs(y - mFirstDownY);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                attemptClaimDrag();
                mFirstDownX = x;
                mFirstDownY = y;
                mCurBtnPic = mBtnPressed;
                mBtnInitPos = mChecked ? mBtnOnPos : mBtnOffPos;
                break;
            case MotionEvent.ACTION_MOVE:
                /**拖动时间*/
                float time = event.getEventTime() - event.getDownTime();
                /**当前按钮位置*/
                mBtnPos = mBtnInitPos + event.getX() - mFirstDownX;
                if (mBtnPos >= mBtnOffPos) {
                    mBtnPos = mBtnOffPos;
                }
                if (mBtnPos <= mBtnOnPos) {
                    mBtnPos = mBtnOnPos;
                }
                mTurningOn = mBtnPos > (mBtnOffPos - mBtnOnPos) / 2 + mBtnOnPos;
                mRealPos = getRealPos(mBtnPos);
                break;
            case MotionEvent.ACTION_UP:
                mCurBtnPic = mBtnNormal;
                time = event.getEventTime() - event.getDownTime();
                if (deltaY < mTouchSlop && deltaX < mTouchSlop
                        && time < mClickTimeout) {
                    if (mPerformClick == null) {
                        mPerformClick = new PerformClick();
                    }
                    if (!post(mPerformClick)) {
                        performClick();
                    }
                } else {
                    startAnimation(!mTurningOn);
                }
                break;
        }
        invalidate();
        return isEnabled();
    }

    /**
     * 通知父类不要拦截touch事件 Tries to claim the user's drag motion, and requests
     * disallowing any ancestors from stealing events in the drag.
     */
    private void attemptClaimDrag() {
        mParent = getParent();
        if (mParent != null) {
            // 通知父类不要拦截touch事件
            mParent.requestDisallowInterceptTouchEvent(true);
        }
    }

    private final class PerformClick implements Runnable {
        public void run() {
            performClick();
        }
    }

    @Override
    public boolean performClick() {
        startAnimation(!mChecked);
        return true;
    }

    private void startAnimation(boolean turnOn) {
        mAnimating = true;
        mAnimatedVelocity = turnOn ? -mVelocity : mVelocity;
        mAnimationPosition = mBtnPos;

        new SwitchAnimation().run();
    }

    /**
     * 切换动画
     */
    private final class SwitchAnimation implements Runnable {

        @Override
        public void run() {
            if (!mAnimating) {
                return;
            }
            doAnimation();
            FrameAnimationController.requestAnimationFrame(this);
        }
    }

    private void doAnimation() {
        mAnimationPosition += mAnimatedVelocity * FrameAnimationController.ANIMATION_FRAME_DURATION / 1000;
        if (mAnimationPosition <= mBtnOnPos) {
            stopAnimation();
            mAnimationPosition = mBtnOnPos;
            setCheckedDelayed(true);
        } else if (mAnimationPosition >= mBtnOffPos) {
            stopAnimation();
            mAnimationPosition = mBtnOffPos;
            setCheckedDelayed(false);
        }
        moveView(mAnimationPosition);
    }

    private void stopAnimation() {
        mAnimating = false;
    }

    private void moveView(float position) {
        mBtnPos = position;
        mRealPos = getRealPos(mBtnPos);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.saveLayerAlpha(mSaveLayerRectF, mAlpha, Canvas.MATRIX_SAVE_FLAG
                | Canvas.CLIP_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG
                | Canvas.FULL_COLOR_LAYER_SAVE_FLAG
                | Canvas.CLIP_TO_LAYER_SAVE_FLAG);
        // 绘制蒙板
        canvas.drawBitmap(mMask, 0, mExtendOffsetY, mPaint);
        mPaint.setXfermode(mXfermode);

        // 绘制底部图片
        canvas.drawBitmap(mBottom, mRealPos, mExtendOffsetY, mPaint);
        mPaint.setXfermode(null);
        // 绘制边框
        canvas.drawBitmap(mFrame, 0, mExtendOffsetY, mPaint);

        // 绘制按钮
        canvas.drawBitmap(mCurBtnPic, mRealPos, mExtendOffsetY, mPaint);
        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((int) mMaskWidth,
                (int) (mMaskHeight + 2 * mExtendOffsetY));
    }

}
