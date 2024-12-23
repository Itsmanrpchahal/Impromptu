package com.mandywebdesign.impromptu.Home_Screen_Fragments.AttendingTab;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mandywebdesign.impromptu.Adapters.Normal_upcoming_events_adpater;
import com.mandywebdesign.impromptu.Interfaces.WebAPI;
import com.mandywebdesign.impromptu.R;
import com.mandywebdesign.impromptu.Retrofit.Normal_past_booked;
import com.mandywebdesign.impromptu.Utils.Constants;
import com.mandywebdesign.impromptu.Utils.Util;
import com.mandywebdesign.impromptu.ui.DiscreteScrollViewOptions;
import com.mandywebdesign.impromptu.ui.Home_Screen;
import com.mandywebdesign.impromptu.ui.NoInternet;
import com.mandywebdesign.impromptu.ui.NoInternetScreen;
import com.mandywebdesign.impromptu.ui.ProgressBarClass;
//import com.yarolegovich.discretescrollview.DSVOrientation;
import com.yarolegovich.discretescrollview.DSVOrientation;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Upcoming extends Fragment implements DiscreteScrollView.OnItemChangedListener {

    public static DiscreteScrollView recyclerView;
    FragmentManager fragmentManager;
    SharedPreferences sharedPreferences, itemPositionPref;
    View view;
    TextView noEvnets;
    Normal_upcoming_events_adpater adapter;
    private InfiniteScrollAdapter infiniteAdapter;

    public static String user, S_Token, itemPosition, formattedDate, getFormattedDate, timeFrom;
    Dialog progressDialog;

    public static ArrayList<String> name1 = new ArrayList<>();
    public static ArrayList<String> title = new ArrayList<>();
    public static ArrayList<String> prices = new ArrayList<>();
    public static ArrayList<String> addres = new ArrayList<>();
    public static ArrayList<String> time = new ArrayList<>();
    public static ArrayList<String> categois = new ArrayList<>();
    public static ArrayList<String> images = new ArrayList<>();
    public static ArrayList<String> userID = new ArrayList<>();
    public static ArrayList<String> event_id = new ArrayList<>();
    public static ArrayList<String> book_tickets = new ArrayList<>();
    public static ArrayList<String> total_book_tickets = new ArrayList<>();
    public static ArrayList<String> usertype = new ArrayList<>();
    public static ArrayList<String> hostname = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_upcoming, container, false);

        fragmentManager = getFragmentManager();

        progressDialog = ProgressBarClass.showProgressDialog(getContext());
        progressDialog.dismiss();

        sharedPreferences = getContext().getSharedPreferences("UserToken", Context.MODE_PRIVATE);
        itemPositionPref = getContext().getSharedPreferences("ItemPosition", Context.MODE_PRIVATE);
        S_Token = sharedPreferences.getString("Socailtoken", "");
        itemPosition = itemPositionPref.getString(Constants.itemPosition, String.valueOf(0));
        init();


        recyclerView = view.findViewById(R.id.upcoming_booked_recycler_view);
        recyclerView.setOrientation(DSVOrientation.HORIZONTAL);
        recyclerView.addOnItemChangedListener(this);
        infiniteAdapter = InfiniteScrollAdapter.wrap(new Normal_upcoming_events_adpater(getContext(), fragmentManager));
        recyclerView.setAdapter(infiniteAdapter);
        recyclerView.setItemTransitionTimeMillis(DiscreteScrollViewOptions.getTransitionTime());
        recyclerView.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)

                .build());

        upcoming_events(S_Token);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void upcoming_events(String s_token) {
        name1.clear();
        title.clear();
        prices.clear();
        addres.clear();
        time.clear();
        categois.clear();
        images.clear();
        event_id.clear();
        userID.clear();
        book_tickets.clear();
        total_book_tickets.clear();
        usertype.clear();
        hostname.clear();
        progressDialog.show();
        Call<Normal_past_booked> call = WebAPI.getInstance().getApi().upcoming_booked("Bearer " + s_token, "application/json");
        call.enqueue(new Callback<Normal_past_booked>() {
            @Override
            public void onResponse(Call<Normal_past_booked> call, Response<Normal_past_booked> response) {

                Log.d("+++++++++", "++ response ++" + S_Token);

                progressDialog.dismiss();
                if (response.body()!=null) {
                    if (response.body().getStatus().equals("200")) {

                        Normal_past_booked data = response.body();
                        List<Normal_past_booked.Datum> datumArrayList = data.getData();

                        for (Normal_past_booked.Datum datum : datumArrayList) {

                            name1.add(datum.getBEventHostname());
                            addres.add(datum.getAddressline1());
                            title.add(datum.getTitle());
                            book_tickets.add(datum.getBook_tickets().toString());
                            total_book_tickets.add(datum.getTotal_book_tickets().toString());
                            usertype.add(datum.getUser_type());
                            hostname.add(datum.getBEventHostname());
                            if (datum.getPrice()!=null)
                            {
                                if (datum.getPrice().equals("")) {

                                    prices.add("Free");
                                } else {
                                    prices.add(datum.getPrice());
                                }
                            }else {
                                prices.add("Paid");
                            }

                            Log.d("cates", "" + datum.getCategory());

                            String time_t = Util.convertTimeStampToTime(Long.parseLong(datum.getEventStartDt())).replaceFirst("a.m.","am").replaceFirst("p.m.","pm").replaceFirst("AM","am").replaceFirst("PM","pm");
                            String start_date = Util.convertTimeStampDate(Long.parseLong(datum.getEventStartDt()));
                            String end_date = Util.convertTimeStampDate(Long.parseLong(datum.getEventEndDt()));

                            timeFrom = removeLeadingZeroes(time_t);
                            if (timeFrom.contains(":00"))
                            {
                                timeFrom = removeLeadingZeroes(time_t).replace(":00","");
                            }else {
                                timeFrom = removeLeadingZeroes(time_t);
                            }

                                Calendar c = Calendar.getInstance();

                                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                                formattedDate = df.format(c.getTime());
                                c.add(Calendar.DATE, 1);

                                getFormattedDate = df.format(c.getTime());

                                System.out.println("Current time ==> " + c.getTime());

                                if (formattedDate.matches(Util.convertTimeStampDate(Long.parseLong(datum.getEventStartDt())))) {
                                    time.add("Today at " + timeFrom);
                                } else if (getFormattedDate.matches(Util.convertTimeStampDate(Long.parseLong(datum.getEventStartDt())))) {
                                    time.add("Tomorrow at " + timeFrom);
                                } else {
                                    String date =Util.convertTimeStampDate(Long.parseLong(datum.getEventStartDt()));
                                    /*to change server date formate*/
                                    String s1 = date;
                                    String[] str = s1.split("/");
                                    String str1 = str[0];
                                    String str2 = str[1];
                                    String str3 = str[2];
                                    time.add(str1 + "/" + str2 + "/" + str3 + " at " + timeFrom);
                                }


                            categois.add(datum.getCategory());
                            images.add(datum.getFile().get(0));
                            event_id.add(datum.getEventId().toString());
                            userID.add(datum.getUserId().toString());
                            reverse();

                            adapter = new Normal_upcoming_events_adpater(getContext(), fragmentManager);
                            recyclerView.setAdapter(adapter);
                            if (itemPosition!=null)
                            {
                                recyclerView.getLayoutManager().scrollToPosition(Integer.parseInt(itemPosition));
                            }

                            SharedPreferences.Editor editor = itemPositionPref.edit();
                            editor.clear();
                            editor.commit();
                        }
                    } else if (response.body().getStatus().equals("400")) {
                        noEvnets.setVisibility(View.VISIBLE);
                    }
                } else {
                    Intent intent = new Intent(getContext(), NoInternetScreen.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }

            }

            @Override
            public void onFailure(Call<Normal_past_booked> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("++++", "t" + t);
                if (NoInternet.isOnline(getContext()) == false) {
                    NoInternet.dialog(getContext());
                }else {
                    Toast.makeText(getContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void init() {
        Home_Screen.bottomNavigationView.setVisibility(View.VISIBLE);
        recyclerView = view.findViewById(R.id.upcoming_booked_recycler_view);
        noEvnets = view.findViewById(R.id.upcoming_no_events);
    }

    @Override
    public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int adapterPosition) {

    }

    private void reverse() {

        Collections.reverse(name1);
        Collections.reverse(title);
        Collections.reverse(prices);
        Collections.reverse(addres);
        Collections.reverse(time);
        Collections.reverse(categois);
        Collections.reverse(images);
        Collections.reverse(event_id);
        Collections.reverse(userID);
        Collections.reverse(book_tickets);
        Collections.reverse(total_book_tickets);
    }

    String removeLeadingZeroes(String s) {
        StringBuilder sb = new StringBuilder(s);
        while (sb.length() > 0 && sb.charAt(0) == '0') {
            sb.deleteCharAt(0);
        }

        return sb.toString();
    }

}
