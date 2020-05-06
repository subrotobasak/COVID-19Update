package com.example.covid19news.Fragment;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covid19news.Adapter.CountryRecyclerAdapter;
import com.example.covid19news.ApiInterface.ApiInterface;
import com.example.covid19news.ConnectionCheck.InternetCheck;
import com.example.covid19news.Model.CountryModel;
import com.example.covid19news.R;
import com.example.covid19news.Retrofit.ApiClient;

import java.util.ArrayList;
import java.util.List;


public class Country extends Fragment {

    private RecyclerView recyclerView;
    private List<CountryModel> list;
    private CountryRecyclerAdapter countryRecyclerAdapter;
    private ProgressBar progressBar;
    private TextView count;


    public Country() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_country, container, false);

        count = view.findViewById(R.id.cases_count);
        list = new ArrayList<>();
        recyclerView = view.findViewById(R.id.rc_view);
        progressBar = view.findViewById(R.id.main_progressBar);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        countryRecyclerAdapter = new CountryRecyclerAdapter(getContext(), list);
        recyclerView.setAdapter(countryRecyclerAdapter);

        InternetCheck check = new InternetCheck();
        if (check.isInternetOn(getActivity()) == false) {
            Toast.makeText(getContext(), "Please Check Your Internet!", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);

        } else {

            getCountriesData();
        }


        return view;
    }

    private void getCountriesData() {

        ApiInterface apiInterface = ApiClient.getApiInterface();
        Call<List<CountryModel>> call = apiInterface.getCountryDetails();

        call.enqueue(new Callback<List<CountryModel>>() {
            @Override
            public void onResponse(Call<List<CountryModel>> call, Response<List<CountryModel>> response) {

                int sum = 0;
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);

                    for (CountryModel model : response.body()) {

                        for (int i = 0; i < list.size(); i++) {
                            sum += list.get(i).getCases();
                        }


                        count.setText("" + sum);

                        list.add(model);
                        countryRecyclerAdapter.notifyDataSetChanged();
                    }

                } else {
                    Toast.makeText(getContext(), "below response", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<List<CountryModel>> call, Throwable t) {
                Toast.makeText(getContext(), "Slow Internet or Server Error!", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);

            }
        });

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        getActivity().getMenuInflater().inflate(R.menu.search, menu);
        MenuItem mSearchMenuItem = menu.findItem(R.id.search);

//        SearchView searchView = (SearchView) mSearchMenuItem.getActionView();

        SearchView searchView = null;
        if (mSearchMenuItem != null) {
            searchView = (SearchView) mSearchMenuItem.getActionView();
        }


        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
//        searchView.setIconifiedByDefault(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                countryRecyclerAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
