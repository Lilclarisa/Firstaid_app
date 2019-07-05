package com.example.firstaidassistance;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccidentRecyclerAdapter extends RecyclerView.Adapter<AccidentRecyclerAdapter.ViewHolder> {
    private static final String TAG = "AccidentRecyclerAdapter";

   private ArrayList<String> mImageNames  = new ArrayList<>();
    private ArrayList<String> mImages  = new ArrayList<>();
    private AccidentsFragment mContext;

    public AccidentRecyclerAdapter(AccidentsFragment mContext, ArrayList<String> mImageNames, ArrayList<String> mImages) {
        this.mImageNames = mImageNames;
        this.mImages = mImages;
        this.mContext = mContext;

    }

     @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem,parent,false);
         final ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
       // Log.d(TAG, "onBindViewHolder: called.");
        Glide.with(mContext)
                .asBitmap()
                .load(mImages.get(position))
                .into(holder.image);

                holder.imageName.setText(mImageNames.get(position));
             holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                switch(position) {

                    case 0 :
                        Intent intent = new Intent(view.getContext(), FragmentFoodpoison.class);
                        intent.putExtra("image_url", mImages.get(0));
                        intent.putExtra("image_name",mImageNames.get(0));
                        mContext.startActivity(intent);
                        break;
                    case 1: // Open second activity
                         intent = new Intent(view.getContext(), AsthmaActivity.class);
                        intent.putExtra("image_url", mImages.get(1));
                        intent.putExtra("image_name",mImageNames.get(1));
                        mContext.startActivity(intent);
                        break;
                    case 2: // Open second activity
                        intent = new Intent(view.getContext(), HeatStrokeActivity.class);
                        intent.putExtra("image_url", mImages.get(2));
                        intent.putExtra("image_name",mImageNames.get(2));
                        mContext.startActivity(intent);
                        break;
                    case 3: // Open second activity
                        intent = new Intent(view.getContext(), NoseBleedActivity.class);
                        intent.putExtra("image_url", mImages.get(3));
                        intent.putExtra("image_name",mImageNames.get(3));
                        mContext.startActivity(intent);
                        break;
                    case 4: // Open second activity
                        intent = new Intent(view.getContext(), SnakeBitesActivity.class);
                        intent.putExtra("image_url", mImages.get(4));
                        intent.putExtra("image_name",mImageNames.get(4));
                        mContext.startActivity(intent);
                        break;
                    case 5: // Open second activity
                        intent = new Intent(view.getContext(), StrokeActivity.class);
                        intent.putExtra("image_url", mImages.get(5));
                        intent.putExtra("image_name",mImageNames.get(5));
                        mContext.startActivity(intent);
                        break;
                    case 6: // Open second activity
                        intent = new Intent(view.getContext(), HeartAttackActivity.class);
                        intent.putExtra("image_url", mImages.get(6));
                        intent.putExtra("image_name",mImageNames.get(6));
                        mContext.startActivity(intent);
                        break;
                    case 7: // Open second activity
                        intent = new Intent(view.getContext(), EpilepsyActivity.class);
                        intent.putExtra("image_url", mImages.get(7));
                        intent.putExtra("image_name",mImageNames.get(7));
                        mContext.startActivity(intent);
                        break;
                    case 8: // Open second activity
                        intent = new Intent(view.getContext(), LaborSignsActivity.class);
                        intent.putExtra("image_url", mImages.get(8));
                        intent.putExtra("image_name",mImageNames.get(8));
                        mContext.startActivity(intent);
                        break;
                    case 9: // Open second activity
                        intent = new Intent(view.getContext(), StrainsActivity.class);
                        intent.putExtra("image_url", mImages.get(9));
                        intent.putExtra("image_name",mImageNames.get(9));
                        mContext.startActivity(intent);
                        break;



                };
                //Toast.makeText(mContext,mImageNames.get(position),Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return mImageNames.size();
    }

    public void filterList(ArrayList<String> filteredList){

        mImages = filteredList;
        notifyDataSetChanged();


    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView image;
        TextView imageName;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            imageName = itemView.findViewById(R.id.image_name);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
