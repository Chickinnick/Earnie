package com.chickinnick.earnie.tutorial;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chickinnick.earnie.EarineApp;
import com.chickinnick.earnie.R;
import com.chickinnick.earnie.databinding.ActivityTutorBinding;
import com.chickinnick.earnie.databinding.FragmentTutorBinding;
import com.chickinnick.earnie.home.WalletActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TutorActivity extends AppCompatActivity implements View.OnClickListener {

    private static final long WELCOME_DELAY = 1500;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    private ActivityTutorBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tutor);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        binding.doneBtn.setTypeface(Typeface.createFromAsset(getAssets(), "Quicksand-Regular.ttf"));
        binding.doneBtn.setOnClickListener(this);

        binding.viewPager.setAdapter(mSectionsPagerAdapter);
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                binding.indicatorPager.setSelectedItem(position, true);
                if (position == 4) {
                    binding.doneBtn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.welcomeIv.setVisibility(View.GONE);
                    }
                });
            }
        }, WELCOME_DELAY);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.done_btn:
                startActivity(new Intent(TutorActivity.this, WalletActivity.class));
                break;
        }
    }

    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";
        private FragmentTutorBinding pageBinding;
        private String[] stringArray;
        private Typeface typefaceRegular;
        private Typeface typefaceBold;

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            pageBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_tutor, container, false);
            typefaceRegular = EarineApp.getRegularTypeface();
            typefaceBold = EarineApp.getBoldTypeface();
            int selectedIndex = getArguments().getInt(ARG_SECTION_NUMBER);
            stringArray = getResources().getStringArray(R.array.pager_items);
            initPage(selectedIndex);
            return pageBinding.getRoot();
        }

        private void initPage(int selectedIndex) {
            String text = stringArray[selectedIndex];
            switch (selectedIndex) {
                case 0:
                case 2: {
                    pageBinding.textMain.setText(parseBoldText(text));
                    break;
                }
                case 1: {
                    pageBinding.textMain.setText(parseBoldText(text));
                    pageBinding.tutorialImage.setImageResource(R.drawable.learnrules_image_0);
                    break;
                }
                case 3:
                    pageBinding.tutorialImage.setImageResource(R.drawable.learnrules_image_1);
                    parsePair(text);
                    break;
                case 4:
                    parsePair(text);
                    pageBinding.tutorialImage.setImageResource(R.drawable.learnrules_image_2);
                    break;
            }
        }

        private void parsePair(String text) {
            String[] pair = text.split("\\+");
            pageBinding.textMain.setText(pair[0]);
            pageBinding.textMain.setTypeface(typefaceRegular);
            pageBinding.textSub.setText(pair[1]);
            pageBinding.textSub.setTypeface(typefaceRegular);
        }


        private SpannableString parseBoldText(String t) {
            int len = 0;
            for (char aChar : t.toCharArray()) {
                if (aChar == '{') {
                    len++;
                }
            }
            String text = t;
            List<Pair<Integer, Integer>> pairList = new ArrayList<>();
            SpannableString spannableString = null;
            for (int i = 0; i < len; i++) {
                int indexOfStart = text.indexOf("{");
                int indexOfEnd = text.indexOf("}");
                pairList.add(new Pair<Integer, Integer>(indexOfStart, indexOfEnd));
                text = text.replaceFirst("\\{", " ");
                text = text.replaceFirst("\\}", " ");
            }
            spannableString = new SpannableString(text);

            for (Pair pair : pairList) {
                spannableString.setSpan(new CustomTypefaceSpan("", typefaceRegular),
                        0, (Integer) pair.first, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                spannableString.setSpan(new CustomTypefaceSpan("", typefaceBold),
                        (Integer) pair.first, (Integer) pair.second, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                spannableString.setSpan(new CustomTypefaceSpan("", typefaceRegular),
                        (Integer) pair.second, text.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

            }
            return spannableString;
        }

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position);
        }
        @Override
        public int getCount() {
            return 5;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + String.valueOf(position);
        }
    }
}
