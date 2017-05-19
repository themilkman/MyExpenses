package org.totschnig.myexpenses.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;

import org.totschnig.myexpenses.MyApplication;
import org.totschnig.myexpenses.R;
import org.totschnig.myexpenses.fragment.OnboardingDataFragment;
import org.totschnig.myexpenses.fragment.OnboardingUiFragment;
import org.totschnig.myexpenses.ui.FragmentPagerAdapter;

public class OnboardingActivity extends ProtectedFragmentActivity {

  private ViewPager pager;
  private MyPagerAdapter pagerAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    setTheme(MyApplication.getThemeId());
    super.onCreate(savedInstanceState);
    setContentView(R.layout.onboarding);
    setupToolbar(false);
    pager = (ViewPager) findViewById(R.id.viewpager);
    pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
    pager.setAdapter(pagerAdapter);
    if (MyApplication.isInstrumentationTest()) {
      PreferenceManager.setDefaultValues(this, MyApplication.getTestId(), Context.MODE_PRIVATE,
          R.xml.preferences, true);
    } else {
      PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    //skip Help
    return true;
  }

  public void navigate_next(View view) {
    pager.setCurrentItem(1, true);
  }

  @Override
  public void onBackPressed() {
    if (pager.getCurrentItem() == 1) {
      pager.setCurrentItem(0);
    } else {
      super.onBackPressed();
    }
  }

  public void showMoreOptions(View view) {
    ((OnboardingDataFragment) getSupportFragmentManager().findFragmentByTag(
        pagerAdapter.getFragmentName(1))).showMoreOptions(view);
  }

  private class MyPagerAdapter extends FragmentPagerAdapter {

    public MyPagerAdapter(FragmentManager fm) {
      super(fm);
    }

    public String getFragmentName(int currentPosition) {
      return FragmentPagerAdapter.makeFragmentName(R.id.viewpager, getItemId(currentPosition));
    }

    @Override
    public Fragment getItem(int pos) {
      switch(pos) {
        case 0: return OnboardingUiFragment.newInstance();
        case 1:
        default: return OnboardingDataFragment.newInstance();
      }
    }

    @Override
    public int getCount() {
      return 2;
    }
  }
}
