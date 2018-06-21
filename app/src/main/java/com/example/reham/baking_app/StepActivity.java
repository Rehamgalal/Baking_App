package com.example.reham.baking_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.reham.baking_app.Fragments.StepFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StepActivity extends AppCompatActivity implements Strings {
    @BindView(R.id.next_step)
    Button next;
    int position;
    ArrayList<String> description;
    ArrayList<String> videos;
    ArrayList<String> thumnailURL;
    android.support.v4.app.FragmentManager manager;
    StepFragment stepFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        ButterKnife.bind(this);
        final String SIMPLE_FRAGMENT_TAG = "myfragmenttag";
        final Intent i = getIntent();
        if (savedInstanceState!=null){
            stepFragment = (StepFragment)
                    getSupportFragmentManager().findFragmentByTag(SIMPLE_FRAGMENT_TAG);
            if (!stepFragment.isInLayout()) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, stepFragment, SIMPLE_FRAGMENT_TAG)
                        .commit();
            }
        }else{

        position = i.getIntExtra(positionText, 0);
        description = i.getStringArrayListExtra(descriptionText);
        Bundle bundle = new Bundle();
        String mTwoPane = "onePane";
        bundle.putString(mTwoPaneText, mTwoPane);
        bundle.putString(descriptionText, "" + description.get(position));
        if (i.getStringArrayListExtra(VideoUrlText) != null && !i.getStringArrayListExtra(VideoUrlText).equals("")) {
            videos = i.getStringArrayListExtra(VideoUrlText);
            bundle.putString(VideoUrlText, videos.get(position - 1));
        } else if (i.getStringArrayListExtra(thumbnailUrlText) != null && !i.getStringArrayListExtra(thumbnailUrlText).equals("")) {
            thumnailURL = i.getStringArrayListExtra(thumbnailUrlText);
            bundle.putString(thumbnailUrlText, thumnailURL.get(position - 1));
        }

        stepFragment = new StepFragment();
        manager = getSupportFragmentManager();
        stepFragment.setArguments(bundle);
        manager.beginTransaction().add(R.id.fragment_container, stepFragment,SIMPLE_FRAGMENT_TAG).commit();}
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position < description.size() - 1) {
                    position++;
                    ArrayList<String> description = i.getStringArrayListExtra(descriptionText);
                    Bundle b = new Bundle();
                    b.putString(descriptionText, description.get(position));
                    ArrayList<String> videos = i.getStringArrayListExtra(VideoUrlText);
                    b.putString(VideoUrlText, videos.get(position - 1));
                    ArrayList<String> thumnailURL = i.getStringArrayListExtra(thumbnailUrlText);
                    b.putString(thumbnailUrlText, thumnailURL.get(position - 1));
                    StepFragment stepFragment1 = new StepFragment();
                    stepFragment1.setArguments(b);
                    manager.beginTransaction().replace(R.id.fragment_container, stepFragment1,SIMPLE_FRAGMENT_TAG).commit();
                }
            }
        });
    }
}
