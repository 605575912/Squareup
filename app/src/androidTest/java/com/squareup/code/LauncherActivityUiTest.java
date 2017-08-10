package com.squareup.code;

/**
 * Created by Administrator on 2017/08/10 0010.
 */


import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LauncherActivityUiTest {

    private Intent mLoginIntent;



    public LauncherActivityUiTest() {
    }

    @Before
    public void setUp() throws Exception {
        mLoginIntent = new Intent(getInstrumentation().getTargetContext(), LauncherActivity.class);
    }

    @Rule
    public ActivityTestRule<LauncherActivity> mActivityRule = new ActivityTestRule<>(
            LauncherActivity.class);

//    @Before
//    public void initValidString() {
//        // Specify a valid string.
//        mStringToBetyped = "Espresso";
//    }

    @Test
    public void changeText_sameActivity() {
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.squareup.code", appContext.getPackageName());
        // Type text and then press the button.
//        onView(withId(R.id.editTextUserInput))
//                .perform(typeText(mStringToBetyped), closeSoftKeyboard());
        onView(allOf(withId(R.id.test_tv_radio))).perform(click());
//
//        // Check that the text was changed.
//        onView(withId(R.id.textToBeChanged))
//                .check(matches(withText(mStringToBetyped)));
    }
}
