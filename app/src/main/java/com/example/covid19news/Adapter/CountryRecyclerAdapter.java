package com.example.covid19news.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.covid19news.Model.CountryModel;
import com.example.covid19news.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CountryRecyclerAdapter extends RecyclerView.Adapter<CountryRecyclerAdapter.ViewHolder> implements Filterable {
    private Context mContext;
    private List<CountryModel> list = new ArrayList<>();
    private List<CountryModel> filteredDataList;

    public CountryRecyclerAdapter(Context mContext, List<CountryModel> list) {
        this.mContext = mContext;
        this.filteredDataList = list;
        this.list = list;
    }

    @NonNull
    @Override
    public CountryRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_custom_design, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryRecyclerAdapter.ViewHolder holder, int position) {

        DecimalFormat decimalFormat = new DecimalFormat("###,###");

        holder.country_country.setText("" + filteredDataList.get(position).getCountry());

        String cases = decimalFormat.format(filteredDataList.get(position).getCases());
        holder.country_cases.setText(cases);

        String cases_today = decimalFormat.format(filteredDataList.get(position).getTodayCases());
        holder.country_cases_today.setText(cases_today);

        String cases_active = decimalFormat.format(filteredDataList.get(position).getActive());
        holder.country_cases_active.setText(cases_active);

        String total_death = decimalFormat.format(filteredDataList.get(position).getDeaths());
        holder.country_death.setText(total_death);

        String today_death = decimalFormat.format(filteredDataList.get(position).getTodayDeaths());
        holder.country_death_today.setText(today_death);

        String recovered = decimalFormat.format(filteredDataList.get(position).getRecovered());
        holder.country_recovered.setText(recovered);

        String critical = decimalFormat.format(filteredDataList.get(position).getCritical());
        holder.country_critical.setText(critical);


    }

    @Override
    public int getItemCount() {
        return filteredDataList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String searchString = charSequence.toString();

                if (searchString.isEmpty()) {
                    filteredDataList = list;
                } else {
                    ArrayList<CountryModel> tempFilteredList = new ArrayList<>();

                    for (CountryModel country : list) {
                        // search for user title
                        if (country.getCountry().toLowerCase().contains(searchString.toString().toLowerCase())) {
                            tempFilteredList.add(country);
                        }

                    }

                    filteredDataList = tempFilteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredDataList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredDataList = (ArrayList<CountryModel>) filterResults.values;
                notifyDataSetChanged();

            }
        };
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView country_cases, country_cases_today, country_cases_active;
        public TextView country_death, country_death_today;
        public TextView country_recovered, country_critical;
        public TextView country_country;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            country_country = itemView.findViewById(R.id.country);
            country_cases = itemView.findViewById(R.id.tv_cases);
            country_cases_today = itemView.findViewById(R.id.tv_cases_today);
            country_cases_active = itemView.findViewById(R.id.tv_cases_active);
            country_death = itemView.findViewById(R.id.tv_deaths);
            country_death_today = itemView.findViewById(R.id.tv_deaths_today);
            country_recovered = itemView.findViewById(R.id.tv_recovered);
            country_critical = itemView.findViewById(R.id.tv_critical);
        }
    }
}
