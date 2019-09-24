package com.mandywebdesign.impromptu.Adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mandywebdesign.impromptu.BusinessRegisterLogin.BusinessEventDetailAcitvity;
import com.mandywebdesign.impromptu.BusinessRegisterLogin.BusinessUserProfile;
import com.mandywebdesign.impromptu.R;

public class BusinessuserProfileLiveEvents extends RecyclerView.Adapter<BusinessuserProfileLiveEvents.ViewHolder> {

    Context context;
    FragmentManager manager;

    public BusinessuserProfileLiveEvents(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.business_vustom_event_thumbs,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.category.setText(BusinessUserProfile.profileliveevents.get(i));
        Glide.with(context).load(BusinessUserProfile.images.get(i)).apply(new RequestOptions().override(200,200)).into(viewHolder.imageView);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BusinessEventDetailAcitvity.class);
                intent.putExtra("eventType","live");
                intent.putExtra("event_id",BusinessUserProfile.profileliveevents_id.get(i));
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return BusinessUserProfile.profileliveevents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {


        TextView category;
        ImageView imageView;

        public ViewHolder(final View itemView) {
            super(itemView);

            category = itemView.findViewById(R.id.business_event_name_thumb);
            imageView = itemView.findViewById(R.id.business_event_thumb);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //manager.beginTransaction().replace(R.id.home_frame_layout,new BusinessEvent_detailsFragment()).addToBackStack("Live").commit();
                }
            });
        }
    }
}
