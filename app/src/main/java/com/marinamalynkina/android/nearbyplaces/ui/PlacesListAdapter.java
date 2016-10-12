package com.marinamalynkina.android.nearbyplaces.ui;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.marinamalynkina.android.nearbyplaces.MyLog;
import com.marinamalynkina.android.nearbyplaces.R;
import com.marinamalynkina.android.nearbyplaces.ui.models.PlaceRow;

import java.util.List;

/**
 * Created by ilmarin on 11.10.16.
 */

public class PlacesListAdapter extends RecyclerView.Adapter<PlacesListAdapter.MyViewHolder> {

    private List<PlaceRow> placesList;
    private PlaceListListener mListener;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView title, distance, street;
        public PlaceRow placeRow;

        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            title = (TextView) view.findViewById(R.id.title);
            distance = (TextView) view.findViewById(R.id.distance);
            street = (TextView) view.findViewById(R.id.street);
        }

        public void setData(PlaceRow place) {
            placeRow = place;
            title.setText(place.getCommonInfo().getName());
            street.setText(place.getCommonInfo().getVicinity());
            distance.setText(place.getDistance());
        }

        @Override
        public void onClick(View v) {
            Log.i(MyLog.TAG, "MyViewHolder onclick ");
            if (mListener != null) {
                mListener.onListRowClick(placeRow);
            }
        }
    }


    public PlacesListAdapter(List<PlaceRow> moviesList, PlaceListListener listener) {
        this.placesList = moviesList;
        this.mListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.place_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.setData(placesList.get(position));
    }

    @Override
    public int getItemCount() {
        return placesList.size();
    }

    public interface PlaceListListener {
        void onListRowClick(PlaceRow placeRow);
    }


}
