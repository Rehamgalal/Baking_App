package com.example.reham.baking_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.reham.baking_app.Fragments.StepFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepActivity extends AppCompatActivity {
@BindView(R.id.next_step)
    Button next;
int position;
ArrayList<String> description;
    ArrayList<String> videos;
    ArrayList<String> thumnailURL;
android.support.v4.app.FragmentManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        ButterKnife.bind(this);
        final Intent i = getIntent();
        position= i.getIntExtra("position",0);
       description=i.getStringArrayListExtra("description");
       Bundle bundle= new Bundle();
       String mTwoPane="onePane";
       bundle.putString("mTwoPane",mTwoPane);
       bundle.putString("description",""+description.get(position));
        if (i.getStringArrayListExtra("videoURL")!=null&& !i.getStringArrayListExtra("videoURL").equals("")){
            videos= i.getStringArrayListExtra("videoURL");
            bundle.putString("videoURL",videos.get(position));
        }else if (i.getStringArrayListExtra("thumbnailURL")!=null&& !i.getStringArrayListExtra("thumbnailURL").equals("")){
            thumnailURL=i.getStringArrayListExtra("thumbnailURL");
            bundle.putString("thumbnail",thumnailURL.get(position));
        }

        StepFragment stepFragment= new StepFragment();
        manager= getSupportFragmentManager();
       stepFragment.setArguments(bundle);
       manager.beginTransaction().add(R.id.fragment_container,stepFragment).commit();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position < description.size()-1) {
                    position++;
                    ArrayList<String> description = i.getStringArrayListExtra("description");
                    Bundle b = new Bundle();
                    b.putString("description", description.get(position));
                    ArrayList<String> videos = i.getStringArrayListExtra("videoURL");
                    b.putString("video", videos.get(position));
                    ArrayList<String> thumnailURL = i.getStringArrayListExtra("thumbnailURL");
                    b.putString("thumbnail", thumnailURL.get(position));
                    StepFragment stepFragment = new StepFragment();
                    stepFragment.setArguments(b);
                    manager.beginTransaction().replace(R.id.fragment_container, stepFragment).commit();
                }
            }
        });
    }
}
