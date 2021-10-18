package com.abhi41.mvvmpractice.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abhi41.mvvmpractice.Model.CountryModel;
import com.abhi41.mvvmpractice.R;
import com.abhi41.mvvmpractice.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CountryListAdapter extends RecyclerView.Adapter<CountryListAdapter.ViewHolder> {

    private List<CountryModel> countryModelList;
    CountryListOnClick listOnClick;

    public CountryListAdapter(List<CountryModel> countryModelList ,CountryListOnClick countryListOnClick ) {
        this.countryModelList = countryModelList;
        this.listOnClick = countryListOnClick;
    }

    public void updateCountries(List<CountryModel> newCountries ) {
        countryModelList.clear();
        countryModelList.addAll(newCountries);
        notifyDataSetChanged();


    }

    @NonNull
    @Override
    public CountryListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_country, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryListAdapter.ViewHolder holder, int position) {

        holder.bind(countryModelList.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listOnClick.onItemClickListener();
            }
        });

    }

    @Override
    public int getItemCount() {
        return countryModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img)
        ImageView img;

        @BindView(R.id.txtCountry)
        TextView txtCountry;

        @BindView(R.id.txtCapital)
        TextView txtCapital;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(CountryModel countryModel) {
            txtCountry.setText(countryModel.getCountryName());
            txtCapital.setText(countryModel.getCapital());

            Utils.loadImage(img, countryModel.getFlag(), Utils.getProgressCircularDrawable(img.getContext()));

        }

    }

    public interface CountryListOnClick {
        void onItemClickListener();
    }

}
