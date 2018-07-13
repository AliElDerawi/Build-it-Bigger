package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class GetJokesAsyncTaskTest {

    @Rule
    public ActivityTestRule<MainActivity> mMainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private IdlingResource mIdlingResources;
    private AsyncTask<Context, Void, String> execute;

    @Before
    public void registerIdlingResource() {
        mIdlingResources = mMainActivityActivityTestRule.getActivity().getIdlingResources();;
        IdlingRegistry.getInstance().register(mIdlingResources);
    }

    @Test
    public void onPostExecuteRun(){

        execute = new GetJokesAsyncTask().execute(mMainActivityActivityTestRule.getActivity());
        try {
            assertTrue(execute.get() != null);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

//    @After
//    public void onPostExecuteCheckResult(){
//
//        try {
//            assertTrue(execute.get() != null);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//
//    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResources != null)
            IdlingRegistry.getInstance().unregister(mIdlingResources);
    }

}
