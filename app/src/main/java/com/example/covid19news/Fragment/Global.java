package com.example.covid19news.Fragment;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covid19news.ApiInterface.ApiInterface;
import com.example.covid19news.ConnectionCheck.InternetCheck;
import com.example.covid19news.Model.GlobalModel;
import com.example.covid19news.R;
import com.example.covid19news.Retrofit.ApiClient;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DecimalFormat;


public class Global extends Fragment {

    private TextView global_tv_cases, global_tv_deaths, global_tv_recovered;
    //active cases

    private TextView tv_currently_patient, tv_condition, tv_condition_ratio, tv_critical, tv_critical_ratio;
    //closed cases
    private TextView tv_closed_patient, tv_recovered, tv_recovered_ratio, tv_death, tv_death_ratio;

    //private LinearLayout _firstLayout, _secondLayout, _thirdLayout;
    private LinearLayout layout_cases, linear_layout_top;

    private ProgressBar progressBar;


    public Global() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_global, container, false);

        initViews(view);

        InternetCheck internetCheck = new InternetCheck();

        if (internetCheck.isInternetOn(getActivity()) == false) {
            Toast.makeText(getContext(), "Please Check Your Internet!", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        } else {
            new globalRatioJSOUP().execute();
            getGlobalData();

        }
        return view;
    }

    private void initViews(View view) {

        //Global cases
        global_tv_cases = view.findViewById(R.id.cases);
        global_tv_deaths = view.findViewById(R.id.deaths);
        global_tv_recovered = view.findViewById(R.id.recovered);
        progressBar = view.findViewById(R.id.main_progressBar);

        //private LinearLayout _firstLayout, _secondLayout, _thirdLayout;
        layout_cases = view.findViewById(R.id.layout_cases);
        linear_layout_top = view.findViewById(R.id.linear_layout_top);

        //active cases
        tv_currently_patient = view.findViewById(R.id.active_case_patient);
        tv_condition = view.findViewById(R.id.active_case_condition);
        tv_condition_ratio = view.findViewById(R.id.active_case_condition_percentage);
        tv_critical = view.findViewById(R.id.active_case_critical);
        tv_critical_ratio = view.findViewById(R.id.active_case_critical_percentage);

        //closed cases
        tv_closed_patient = view.findViewById(R.id.closed_case_outcome);
        tv_recovered = view.findViewById(R.id.closed_case_recovered);
        tv_recovered_ratio = view.findViewById(R.id.closed_case_recovered_percentage);
        tv_death = view.findViewById(R.id.closed_case_death);
        tv_death_ratio = view.findViewById(R.id.closed_case_death_percentage);


    }

    private void getGlobalData() {

        ApiInterface apiInterface = ApiClient.getApiInterface();

        Call<GlobalModel> call = apiInterface.getGlobalDetails();
        call.enqueue(new Callback<GlobalModel>() {
            @Override
            public void onResponse(Call<GlobalModel> call, Response<GlobalModel> response) {

                if (response.isSuccessful()) {


                    DecimalFormat decimalFormat = new DecimalFormat("###,###");
                    String cases = decimalFormat.format(response.body().getCases());
                    String deaths = decimalFormat.format(response.body().getDeaths());
                    String recovered = decimalFormat.format(response.body().getRecovered());

                    global_tv_cases.setText(cases);
                    global_tv_deaths.setText(deaths);
                    global_tv_recovered.setText(recovered);


                } else {
                    Toast.makeText(getContext(), "below response", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);

                }


            }

            @Override
            public void onFailure(Call<GlobalModel> call, Throwable t) {
                Toast.makeText(getContext(), "Slow Internet or Server Error!", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);

            }
        });


    }

    private class globalRatioJSOUP extends AsyncTask<Void, Void, Boolean> {
        @SuppressLint("WrongThread")
        @Override
        protected Boolean doInBackground(Void... voids) {
            Document document;
            Elements elements;


            try {

                document = Jsoup.connect("https://www.worldometers.info/coronavirus/").get();

                elements = document.select("div.content-inner");
                //getting Currently Infected Patients and Cases which had an outcome values
                Elements _firstRow = elements.select("div.number-table-main");
                //getting in Mild Condition,Serious or Critical,Recovered / Discharged,Deaths
                Elements _2ndRow = elements.select("span.number-table");
                //percentage on both data
                Elements _percentage = elements.select("strong");

                String mFirstRow = _firstRow.text();
                String m2ndRow = _2ndRow.text();
                String mPercentage = _percentage.text();

                //first row active patient, closed patient data
                final String mActivePatient = mFirstRow.substring(0, 9);
                final String mClosedPatient = mFirstRow.substring(9, mFirstRow.length());

                //active ratio
                final String mActiveConditionRatio = mPercentage.substring(0, 2);
                final String mActiveCriticalRatio = mPercentage.substring(2, 5);

                //closed ratio
                final String mClosedRecoveredRatio = mPercentage.substring(5, 7);
                final String mClosedDeathRatio = mPercentage.substring(7, 9);

                //2nd Row-> condition,critical,recovered,death data
                final String mCondition = m2ndRow.substring(0, 9);
                final String mCritical = m2ndRow.substring(9, 16);
                final String mRecovered = m2ndRow.substring(16, 24);
                final String mDeath = m2ndRow.substring(24, 32);


                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        //active case setText
                        tv_currently_patient.setText(mActivePatient);
                        tv_condition.setText(mCondition);
                        tv_condition_ratio.setText("(" + mActiveConditionRatio + "%)");
                        tv_critical.setText(mCritical);
                        tv_critical_ratio.setText("(" + mActiveCriticalRatio + "%)");

                        //closed case setText
                        tv_closed_patient.setText(mClosedPatient);
                        tv_recovered.setText(mRecovered);
                        tv_recovered_ratio.setText("(" + mClosedRecoveredRatio + "%)");
                        tv_death.setText(mDeath);
                        tv_death_ratio.setText("(" + mClosedDeathRatio + "%)");
                    }
                });


            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            layout_cases.setVisibility(View.VISIBLE);
            linear_layout_top.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}

