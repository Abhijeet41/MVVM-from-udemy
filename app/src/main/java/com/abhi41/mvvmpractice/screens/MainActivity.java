package com.abhi41.mvvmpractice.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.abhi41.mvvmpractice.R;
import com.abhi41.mvvmpractice.view_model.ListViewModel;
import com.abhi41.mvvmpractice.adapter.CountryListAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements CountryListAdapter.CountryListOnClick {

    @BindView(R.id.recy_countryName)
    RecyclerView recy_countryName;

    @BindView(R.id.txt_error)
    TextView txt_error;

    @BindView(R.id.loading_view)
    ProgressBar loading_view;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private ListViewModel viewModel;

    private CountryListAdapter adapter = new CountryListAdapter(new ArrayList<>(), this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        intialization();
        oberverViewModel();
        onClickListener();

        viewModel.refresh();
    }


    private void intialization() {
        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(ListViewModel.class);

        recy_countryName.setLayoutManager(new LinearLayoutManager(this));
        recy_countryName.setAdapter(adapter);
    }

    private void oberverViewModel() {
        // this method attch view to MutableLivewData and retrive Info from there
        viewModel.countries.observe(this, countryModels -> {
            if (countryModels != null) {
                recy_countryName.setVisibility(View.VISIBLE);
                adapter.updateCountries(countryModels);
            }
        });

        viewModel.countryLoadError.observe(this, isError -> {
            if (isError != null) {
                txt_error.setVisibility(isError ? View.VISIBLE : View.GONE);
            }
        });

        viewModel.loading.observe(this, isLoading -> {
            if (isLoading != null) {
                loading_view.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                if (isLoading) {
                    txt_error.setVisibility(View.GONE);
                    recy_countryName.setVisibility(View.GONE);
                }
            }
        });

    }

    private void onClickListener() {

        swipeRefreshLayout.setOnRefreshListener(() -> {
            viewModel.refresh();
            swipeRefreshLayout.setRefreshing(false);
        });
    }


    @Override
    public void onItemClickListener() {
        Intent intent = new Intent(MainActivity.this, UserListScreen.class);
        startActivity(intent);
    }
}