package com.powergroup.unite.app;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.powergroup.unite.R;
import com.powergroup.unite.main.MainPagerAdapter;

/**
 * To be used with ViewPager to provide a tab indicator component which give constant feedback as to
 * the user's scroll progress.
 * <p>
 * To use the component, simply add it to your view hierarchy. Then in your
 * {@link android.app.Activity} or {@link android.support.v4.app.Fragment} call
 * {@link #setViewPager(ViewPager)} providing it the ViewPager this layout is being used for.
 * <p>
 * The colors can be customized in two ways. The first and simplest is to provide an array of colors
 * via {@link #setSelectedIndicatorColors(int...)}. The
 * alternative is via the {@link TabColorizer} interface which provides you complete control over
 * which color is used for any individual position.
 * <p>
 * The views used as tabs can be customized by calling {@link #setCustomTabView(int, int)},
 * providing the layout ID of your custom layout.
 */

/*
Imported from Google's SlidingTabLayout, but edited to allow for option of image icon
 */
public class SlidingTabLayout extends HorizontalScrollView {
    /**
     * Allows complete control over the colors drawn in the tab layout. Set with
     * {@link #setCustomTabColorizer(TabColorizer)}.
     */
    public interface TabColorizer {

        /**
         * @return return the color of the indicator used when {@code position} is selected.
         */
        int getIndicatorColor(int position);

    }

    private static final int TITLE_OFFSET_DIPS = 24;
    private static final int TAB_VIEW_PADDING_DIPS = 16;
    private static final int TAB_VIEW_TEXT_SIZE_SP = 14;

    private int mTitleOffset;

    private int mTabViewLayoutId;
    private int mTabViewTextViewId;
    private boolean mDistributeEvenly;

    //number of tabs you want in a particular strip, default to four
    private int tabs = 4;

    //which selector you want to use for text tabs
    private int selector = R.color.selector;

    //this determines whether or not to use text or images
    private boolean images = false;

    //this specifies whether or not you want that margin in the middle to make room for a menu_button
    private boolean split = false;

    private ViewPager mViewPager;
    private SparseArray<String> mContentDescriptions = new SparseArray<String>();
    private ViewPager.OnPageChangeListener mViewPagerPageChangeListener;

    private final SlidingTabStrip mTabStrip;

    public SlidingTabLayout(Context context) {
        this(context, null);
    }

    public SlidingTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingTabLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        // Disable the Scroll Bar
        setHorizontalScrollBarEnabled(false);
        // Make sure that the Tab Strips fills this View
        setFillViewport(true);

        mTitleOffset = (int) (TITLE_OFFSET_DIPS * getResources().getDisplayMetrics().density);

        mTabStrip = new SlidingTabStrip(context);
        addView(mTabStrip, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }

    /**
     * Set the custom {@link TabColorizer} to be used.
     *
     * If you only require simple custmisation then you can use
     * {@link #setSelectedIndicatorColors(int...)} to achieve
     * similar effects.
     */
    public void setCustomTabColorizer(TabColorizer tabColorizer) {
        mTabStrip.setCustomTabColorizer(tabColorizer);
    }

    public void setDistributeEvenly(boolean distributeEvenly) {
        mDistributeEvenly = distributeEvenly;
    }

    /**
     * Sets the colors to be used for indicating the selected tab. These colors are treated as a
     * circular array. Providing one color will mean that all tabs are indicated with the same color.
     */
    public void setSelectedIndicatorColors(int... colors) {
        mTabStrip.setSelectedIndicatorColors(colors);
    }

    /**
     * Set the {@link ViewPager.OnPageChangeListener}. When using {@link SlidingTabLayout} you are
     * required to set any {@link ViewPager.OnPageChangeListener} through this method. This is so
     * that the layout can update it's scroll position correctly.
     *
     * @see ViewPager#setOnPageChangeListener(ViewPager.OnPageChangeListener)
     */
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mViewPagerPageChangeListener = listener;
    }

    /**
     * Set the custom layout to be inflated for the tab views.
     *
     * @param layoutResId Layout id to be inflated
     * @param textViewId id of the {@link TextView} in the inflated view
     */
    public void setCustomTabView(int layoutResId, int textViewId) {
        mTabViewLayoutId = layoutResId;
        mTabViewTextViewId = textViewId;
    }

    /**
     * Sets the associated view pager. Note that the assumption here is that the pager content
     * (number of tabs and tab titles) does not change after this call has been made.
     */
    public void setViewPager(ViewPager viewPager) {
        mTabStrip.removeAllViews();

        mViewPager = viewPager;
        if (viewPager != null) {
            viewPager.setOnPageChangeListener(new InternalViewPagerListener());
            populateTabStrip();
        }
    }

    /**
     * Create a default view to be used for tabs. This is called if a custom tab view is not set via
     * {@link #setCustomTabView(int, int)}.
     */

    /*
    This sets the number of tabs you want to have in your layout
    */
    public void setTabs(int tabs) {
        this.tabs = tabs;
    }

    /*
    This lets you choose whether you want to use images or text as the tabs
    */
    public void setTabSetting(boolean images) {
        this.images = images;
    }

    /*
    This lets you choose how you want the thing to be highlighted.
    */
    public void setSelector(int selector) {
        this.selector = selector;
    }

    /*
    This lets you choose if you want there to be a split in the middle of your tabs (for a center menu_button)
     */
    public void setSplit(boolean split) {
        this.split = split;
    }

    protected ImageView createDefaultImageView(Context context) {
        ImageView imageView = new ImageView(context);

        return imageView;
    }

    protected TextView createDefaultTabView(Context context) {
        TextView textView = new TextView(context);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int tabWidth = size.x/tabs;
        textView.setWidth(tabWidth);
        textView.setHeight(size.y);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, TAB_VIEW_TEXT_SIZE_SP);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TypedValue outValue = new TypedValue();
        getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground,
                outValue, true);
        textView.setBackgroundResource(outValue.resourceId);
        textView.setAllCaps(true);

        int padding = (int) (TAB_VIEW_PADDING_DIPS * getResources().getDisplayMetrics().density);
        textView.setPadding(padding, padding, padding, padding);

        return textView;
    }

    private void populateTabStrip() {
        final PagerAdapter adapter = mViewPager.getAdapter();
        final OnClickListener tabClickListener = new TabClickListener();

        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int tabWidth = size.x/tabs;

        for (int i = 0; i < adapter.getCount(); i++) {
            View tabView = null;
            if(!images) {
                TextView tabTitleView = null;

                if (mTabViewLayoutId != 0) {
                    // If there is a custom tab view layout id set, try and inflate it
                    tabView = LayoutInflater.from(getContext()).inflate(mTabViewLayoutId, mTabStrip,
                            false);
                    tabTitleView = (TextView) tabView.findViewById(mTabViewTextViewId);
                }

                if (tabView == null) {
                    tabView = createDefaultTabView(getContext());
                }

                if (tabTitleView == null && TextView.class.isInstance(tabView)) {
                    tabTitleView = (TextView) tabView;
                    tabTitleView.setTextColor(getResources().getColorStateList(selector));
                }

                if (mDistributeEvenly) {
                    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                    lp.width = 0;
                    lp.weight = 1;
                }

                if (split) {
                    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                    if (i == 1) {
                        lp.setMargins(0, 0, tabWidth / 2, 0);
                    } else if (i == 2) {
                        lp.setMargins(tabWidth / 2, 0, 0, 0);
                    }
                }


                tabTitleView.setText(adapter.getPageTitle(i));
                tabView.setOnClickListener(tabClickListener);
                String desc = mContentDescriptions.get(i, null);
                if (desc != null) {
                    tabView.setContentDescription(desc);
                }

                mTabStrip.addView(tabView);
                if (i == mViewPager.getCurrentItem()) {
                    tabView.setSelected(true);
                }
            } else {
                ImageView tabIconView = null;

                if (tabView == null) {
                    tabView = createDefaultImageView(getContext());
                }

                if (tabIconView == null && ImageView.class.isInstance(tabView)) {
                    tabIconView = (ImageView) tabView;
                }

                tabIconView.setImageDrawable(getResources().getDrawable(((MainPagerAdapter)adapter).getDrawableID(i)));
                if (mViewPager.getCurrentItem() == i) {
                    tabIconView.setSelected(true);
                }

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(new LayoutParams(tabWidth, ViewGroup.LayoutParams.MATCH_PARENT));

                if (split) {
                    if (i == 1) {
                        lp.setMargins(0, 0, tabWidth / 2, 0);
                    } else if (i == 2) {
                        lp.setMargins(tabWidth / 2, 0, 0, 0);
                    }

                }

                tabView.setLayoutParams(lp);

                tabView.setOnClickListener(tabClickListener);

                mTabStrip.addView(tabView);
                if (i == mViewPager.getCurrentItem()) {
                    tabView.setSelected(true);
                }
            }
        }
    }

    public void setContentDescription(int i, String desc) {
        mContentDescriptions.put(i, desc);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (mViewPager != null) {
            scrollToTab(mViewPager.getCurrentItem(), 0);
        }
    }

    private void scrollToTab(int tabIndex, int positionOffset) {
        final int tabStripChildCount = mTabStrip.getChildCount();
        if (tabStripChildCount == 0 || tabIndex < 0 || tabIndex >= tabStripChildCount) {
            return;
        }

        View selectedChild = mTabStrip.getChildAt(tabIndex);
        if (selectedChild != null) {
            int targetScrollX = selectedChild.getLeft() + positionOffset;

            if (tabIndex > 0 || positionOffset > 0) {
                // If we're not at the first child and are mid-scroll, make sure we obey the offset
                targetScrollX -= mTitleOffset;
            }

            scrollTo(targetScrollX, 0);
        }
    }

    private class InternalViewPagerListener implements ViewPager.OnPageChangeListener {
        private int mScrollState;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            int tabStripChildCount = mTabStrip.getChildCount();
            if ((tabStripChildCount == 0) || (position < 0) || (position >= tabStripChildCount)) {
                return;
            }

            mTabStrip.onViewPagerPageChanged(position, positionOffset);

            View selectedTitle = mTabStrip.getChildAt(position);
            int extraOffset = (selectedTitle != null)
                    ? (int) (positionOffset * selectedTitle.getWidth())
                    : 0;
            scrollToTab(position, extraOffset);

            if (mViewPagerPageChangeListener != null) {
                mViewPagerPageChangeListener.onPageScrolled(position, positionOffset,
                        positionOffsetPixels);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            mScrollState = state;

            if (mViewPagerPageChangeListener != null) {
                mViewPagerPageChangeListener.onPageScrollStateChanged(state);
            }
        }

        @Override
        public void onPageSelected(int position) {
            if (mScrollState == ViewPager.SCROLL_STATE_IDLE) {
                mTabStrip.onViewPagerPageChanged(position, 0f);
                scrollToTab(position, 0);
            }
            for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                mTabStrip.getChildAt(i).setSelected(position == i);
            }
            if (mViewPagerPageChangeListener != null) {
                mViewPagerPageChangeListener.onPageSelected(position);
            }
        }

    }

    private class TabClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                if (v == mTabStrip.getChildAt(i)) {
                    mViewPager.setCurrentItem(i);
                    return;
                }
            }
        }
    }
}
