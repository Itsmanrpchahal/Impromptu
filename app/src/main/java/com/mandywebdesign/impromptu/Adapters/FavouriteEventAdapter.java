package com.mandywebdesign.impromptu.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mandywebdesign.impromptu.BusinessRegisterLogin.BusinessEventDetailAcitvity;
import com.mandywebdesign.impromptu.Home_Screen_Fragments.favouriteTab.EventsFrag;
import com.mandywebdesign.impromptu.R;
import com.mandywebdesign.impromptu.Utils.Constants;

public class FavouriteEventAdapter extends RecyclerView.Adapter<FavouriteEventAdapter.ViewHolder> {

    Context context;
    FragmentManager manager;
    CardAdapterHelper cardAdapterHelper= new CardAdapterHelper();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    public FavouriteEventAdapter(Context context, FragmentManager manager) {
        this.context = context;
        this.manager = manager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.custom_events,viewGroup,false);



        return new FavouriteEventAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

       // cardAdapterHelper.onBindViewHolder(viewHolder.itemView,i,getItemCount());
        sharedPreferences = context.getSharedPreferences("ItemPosition", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        viewHolder.eventName.setText(EventsFrag.title_fav.get(i));
        viewHolder.date.setText(EventsFrag.time_fav.get(i));
        viewHolder.eventAddress.setText(EventsFrag.addres_fav.get(i));

        if (EventsFrag.prices_fav.get(i).equals("0"))
        {
            viewHolder.evetPrice.setText("Free");
        }else {
            viewHolder.evetPrice.setText("£ "+EventsFrag.prices_fav.get(i));
        }

        String s = EventsFrag.addres_fav.get(i);
        Log.e("addre",s );

        if (s.contains(" NearBy "))
        {
            String[] arrayString = s.split(" NearBy ");

            String add1 = arrayString[1];

            Log.e("add1",add1);
            viewHolder.eventAddress.setText("Landmark "+add1);

        }else {
            viewHolder.eventAddress.setText(EventsFrag.addres_fav.get(i));
        }

        viewHolder.category.setText(EventsFrag.categois_fav.get(i));
        Glide.with(context).load(EventsFrag.images_fav.get(i)).into(viewHolder.eventImage);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = EventsFrag.event_id_fav.get(i);
                Intent intent = new Intent(context, BusinessEventDetailAcitvity.class);
                intent.putExtra("event_id",value);
                intent.putExtra("eventType","fav");
                intent.putExtra("other_events","other_events");
                editor.putString(Constants.itemPosition, String.valueOf(i));
                editor.commit();
                context.startActivity(intent);

//                Bundle bundle = new Bundle();
//                String value = EventsFrag.event_id_fav.get(i);
//                bundle.putString("event_id", value);
//                bundle.putString("eventType","fav");
//                bundle.putString("other_events","other_events");
//                editor.putString(Constants.itemPosition, String.valueOf(i));
//                editor.commit();
//
//                BusinessEvent_detailsFragment businessEvent_detailsFragment = new BusinessEvent_detailsFragment();
//                businessEvent_detailsFragment.setArguments(bundle);
//
//                manager.beginTransaction().replace(R.id.home_frame_layout, businessEvent_detailsFragment).addToBackStack(null).commit();
            }
        });
    }


    @Override
    public int getItemCount() {
        return EventsFrag.name1_fav.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        ImageView eventImage;
        TextView eventName,eventAddress;
        TextView evetPrice,category,date;

        public ViewHolder(final View itemView) {
            super(itemView);

            eventImage = itemView.findViewById(R.id.event_image);
            eventName = itemView.findViewById(R.id.event_name);
            evetPrice = itemView.findViewById(R.id.event_price);
            eventAddress = itemView.findViewById(R.id.custom_text1);
            category = itemView.findViewById(R.id.custom_category_name);
            date = itemView.findViewById(R.id.date);
        }
    }
}
