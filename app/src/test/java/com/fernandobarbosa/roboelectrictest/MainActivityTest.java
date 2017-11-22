package com.fernandobarbosa.roboelectrictest;

import android.content.Intent;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, packageName = "")
public class MainActivityTest {

    private MainActivity mainActivity;

    @Before
    public void setUp() throws Exception {
        mainActivity = Robolectric.buildActivity(MainActivity.class)
                .create()
                .resume()
                .get();
    }

    @Test
    public void shouldHaveDefaultMargin() throws Exception {
        TextView textView = (TextView) mainActivity.findViewById(R.id.hello_textview);
        int bottomMargin = ((LinearLayout.LayoutParams) textView.getLayoutParams()).bottomMargin;
        int topMargin = ((LinearLayout.LayoutParams) textView.getLayoutParams()).topMargin;
        int rightMargin = ((LinearLayout.LayoutParams) textView.getLayoutParams()).rightMargin;
        int leftMargin = ((LinearLayout.LayoutParams) textView.getLayoutParams()).leftMargin;

        assertEquals(5, bottomMargin);
        assertEquals(5, topMargin);
        assertEquals(10, rightMargin);
        assertEquals(10, leftMargin);
    }

    @Test
    public void checkActivityNotNull() throws Exception {
        assertNotNull(mainActivity);
    }

    @Test
    public void shouldHaveCorrectAppName() throws Exception {
        String hello = mainActivity.getResources().getString(R.string.app_name);
        assertThat(hello, equalTo("RoboelectricTest"));
    }

    @Test
    public void buttonClickShouldStartNewActivity() throws Exception {
        Button button = (Button) mainActivity.findViewById(R.id.startNextActivity);
        button.performClick();
        Intent intent = Shadows.shadowOf(mainActivity).peekNextStartedActivity();
        assertEquals(NextActivity.class.getCanonicalName(), intent.getComponent().getClassName());
    }

    @Test
    public void testButtonShouldShowToast() throws Exception {
        MainActivity mainActivity = Robolectric.buildActivity(MainActivity.class).create().get();
        Button view = (Button) mainActivity.findViewById(R.id.showToast);
        assertNotNull(view);
        view.performClick();
        assertThat(ShadowToast.getTextOfLatestToast(), equalTo("Lele"));
    }

}
