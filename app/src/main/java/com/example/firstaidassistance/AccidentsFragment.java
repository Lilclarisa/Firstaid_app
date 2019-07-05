package com.example.firstaidassistance;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;

public class AccidentsFragment extends Fragment {
   private static final String TAG = "AcccidentFragment";
   //vars
    private ArrayList<String> mNames  = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    AccidentRecyclerAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accident, container, false);
        getActivity().setTitle("Accidents and Emergencies");

        Log.d(TAG,"initRecyclerView: init recyclerview.");
        RecyclerView recyclerView =  (RecyclerView)view.findViewById(R.id.recyclerView);
        AccidentRecyclerAdapter adapter = new AccidentRecyclerAdapter(this,mNames,mImageUrls);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        EditText editText = (EditText)view.findViewById(R.id.search);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());

            }
        });

        initImageBitmaps();
        return view;

    }

    private void filter(String text){

     ArrayList<String>filteredList = new ArrayList<>();


     for(String item:mNames){
         int i = mNames.indexOf(item);
         if((mNames.get(i)).toLowerCase().contains(text.toLowerCase())){
             filteredList.add(item);
         }
     }
    mAdapter.filterList(filteredList);


    }


    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }*/

    private void initImageBitmaps(){
        Log.d(TAG,"initImageBitmaps: preparing bitmaps");

        mImageUrls.add("http://quickcareorer.com/wp-content/uploads/2016/11/1_woman-suffering-from-food-poisoning.jpg");
        mNames.add("Food Poisoning");

        mImageUrls.add("https://foundation.chestnet.org/wp-content/uploads/2016/09/Asthma_TBuck_042516.jpg");
        mNames.add("Asthma");

        mImageUrls.add("http://img.medscapestatic.com/pi/features/slideshow-slide/heat-illness/fig1.jpg?resize=645:439");
        mNames.add("HeatStrokes");

        mImageUrls.add("https://www.omicsgroup.org/articles-admin/disease-images/acute-sinusitis-1015.jpg");
        mNames.add("NoseBleeds");

        mImageUrls.add("http://www.snake-removal.com/snakebite.jpg");
        mNames.add("Snakebites");

        mImageUrls.add("https://lermagazine.com/wp-content/uploads/2013/06/6stroke-iStock20360282lr.jpg");
        mNames.add("Stroke");

        mImageUrls.add("https://healthjade.com/wp-content/uploads/2018/03/tachycardia.jpg");
        mNames.add("Heart Attack");

        mImageUrls.add("https://i0.wp.com/www.healthcareatoz.com/wp-content/uploads/2013/08/seizures.jpg");
        mNames.add("Epilepsy");

        mImageUrls.add("https://i.dailymail.co.uk/i/pix/2012/10/31/article-2225696-002793A1000004B0-959_233x364.jpg");
        mNames.add("Labor Signs");

        mImageUrls.add("https://www.convergencetraining.com/Images/Courses/FA_SprainsStrains_Image_02.jpg");
        mNames.add("Strains and Sprains");


    }


}