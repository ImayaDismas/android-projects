package com.dismas.imaya.d_safari;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by imaya on 1/24/17.
 */

public class Safari extends ListActivity {
    /**
     * This class describes an individual sample (the sample title, and the activity class that
     * demonstrates this sample).
     */
    private class Sample {
        private CharSequence title;
        private Class<? extends Activity> activityClass;

        public Sample(int titleResId, Class<? extends Activity> activityClass) {
            this.activityClass = activityClass;
            this.title = getResources().getString(titleResId);
        }

        @Override
        public String toString() {
            return title.toString();
        }
    }

    /**
     * The collection of all samples in the app. This gets instantiated in {@link
     * #onCreate(android.os.Bundle)} because the {@link Sample} constructor needs access to {@link
     * android.content.res.Resources}.
     */
    private static Sample[] mSamples;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.safari);

        // Instantiate the list of samples.
        mSamples = new Sample[]{
                //new Sample(R.string.title_crossfade, CrossfadeActivity.class),
                //new Sample(R.string.title_card_flip, CardFlipActivity.class),
                // new Sample(R.string.title_screen_slide, ScreenSlideActivity.class),
                new Sample(R.string.title_zoom, ZoomActivity.class),
                new Sample(R.string.title_layout_changes, LayoutChangesActivity.class),
        };

        setListAdapter(new ArrayAdapter<Sample>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                mSamples));
    }

    @Override
    protected void onListItemClick(ListView listView, View view, int position, long id) {
        // Launch the sample associated with this list position.
        startActivity(new Intent(Safari.this, mSamples[position].activityClass));
    }
}