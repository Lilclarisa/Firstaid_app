package com.example.firstaidassistance;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.firstaidassistance.PopUpKits.AdhesiveActivity;
import com.example.firstaidassistance.PopUpKits.AdhesiveTapeActivity;
import com.example.firstaidassistance.PopUpKits.AntibioticOitmentActivity;
import com.example.firstaidassistance.PopUpKits.BlanketActivity;
import com.example.firstaidassistance.PopUpKits.ColdPackActivity;
import com.example.firstaidassistance.PopUpKits.ElasticBandageActivity;
import com.example.firstaidassistance.PopUpKits.FlashlightActivity;
import com.example.firstaidassistance.PopUpKits.GauzeActivity;
import com.example.firstaidassistance.PopUpKits.GloveActivity;
import com.example.firstaidassistance.PopUpKits.LotionActivity;
import com.example.firstaidassistance.PopUpKits.MedicationActivity;
import com.example.firstaidassistance.PopUpKits.MouthpieceCprActivity;
import com.example.firstaidassistance.PopUpKits.SafetyPinsActivity;
import com.example.firstaidassistance.PopUpKits.ScissorsActivity;
import com.example.firstaidassistance.PopUpKits.SoapActivity;
import com.example.firstaidassistance.PopUpKits.ThermometerActivity;
import com.example.firstaidassistance.PopUpKits.TweezersActivity;
import com.example.firstaidassistance.PopUpKits.WipesActivity;

public class KitRecyclerAdapter extends RecyclerView.Adapter<KitRecyclerAdapter.ImageViewHolder> {

    private int[]images;
    private String [] imageTitle;
    private FirstAidKitActivity mContext;
    public KitRecyclerAdapter(FirstAidKitActivity mContext,int[] images, String[] imageTitle){
        this.images = images;
        this.imageTitle = imageTitle;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kit_item_layout,parent,false);

      final ImageViewHolder holder = new ImageViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, final int position) {

      int image_id = images[position];
      holder.kit.setImageResource(image_id);
      holder.kitName.setText(imageTitle[position]);
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getAdapterPosition();
                switch(pos) {

                    case 0:
                        Intent intent = new Intent(view.getContext(), AdhesiveActivity.class);
                        mContext.startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(view.getContext(), AdhesiveTapeActivity.class);
                        mContext.startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(view.getContext(), AntibioticOitmentActivity.class);
                        mContext.startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(view.getContext(), BlanketActivity.class);
                        mContext.startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(view.getContext(), LotionActivity.class);
                        mContext.startActivity(intent);
                        break;
                    case 5:
                        intent = new Intent(view.getContext(), ColdPackActivity.class);
                        mContext.startActivity(intent);
                        break;
                    case 6:
                        intent = new Intent(view.getContext(), ElasticBandageActivity.class);
                        mContext.startActivity(intent);
                        break;
                    case 7:
                        intent = new Intent(view.getContext(), FlashlightActivity.class);
                        mContext.startActivity(intent);
                        break;
                    case 8:
                        intent = new Intent(view.getContext(), GauzeActivity.class);
                        mContext.startActivity(intent);
                        break;
                    case 9:
                        intent = new Intent(view.getContext(), GloveActivity.class);
                        mContext.startActivity(intent);
                        break;
                    case 10:
                        intent = new Intent(view.getContext(), MedicationActivity.class);
                        mContext.startActivity(intent);
                        break;
                    case 11:
                        intent = new Intent(view.getContext(), MouthpieceCprActivity.class);
                        mContext.startActivity(intent);
                        break;
                    case 12:
                        intent = new Intent(view.getContext(), SafetyPinsActivity.class);
                        mContext.startActivity(intent);
                        break;
                    case 13:
                        intent = new Intent(view.getContext(), ScissorsActivity.class);
                        mContext.startActivity(intent);
                        break;
                    case 14:
                        intent = new Intent(view.getContext(), SoapActivity.class);
                        mContext.startActivity(intent);
                        break;
                    case 15:
                        intent = new Intent(view.getContext(), ThermometerActivity.class);
                        mContext.startActivity(intent);
                        break;
                    case 16:
                        intent = new Intent(view.getContext(), TweezersActivity.class);
                        mContext.startActivity(intent);
                        break;
                    case 17:
                        intent = new Intent(view.getContext(), WipesActivity.class);
                        mContext.startActivity(intent);
                        break;
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder{

    ImageView kit;
    TextView kitName;
    LinearLayout parentLayout;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            kit = itemView.findViewById(R.id.kit);
            kitName = itemView.findViewById(R.id.kit_name);
            parentLayout = itemView.findViewById(R.id.kit_layout);

        }
    }
}
