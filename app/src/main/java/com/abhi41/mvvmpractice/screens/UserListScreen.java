package com.abhi41.mvvmpractice.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.abhi41.mvvmpractice.R;
import com.abhi41.mvvmpractice.utils.Resource;
import com.abhi41.mvvmpractice.view_model.ViewmodelUserList;
import com.abhi41.mvvmpractice.adapter.AdapterUserList;
import com.abhi41.mvvmpractice.databinding.ActivityUserListBinding;
import com.abhi41.mvvmpractice.response.DataItem;
import com.abhi41.mvvmpractice.response.UsersList;

import java.util.ArrayList;
import java.util.List;

public class UserListScreen extends AppCompatActivity implements AdapterUserList.OnItemUserClickListener {

    private ActivityUserListBinding binding;
    private ViewmodelUserList viewmodel;
    private AdapterUserList adapter;
    private static final String TAG = "UserListScreen";
    private List<DataItem> dataItems = new ArrayList<>();
    private int currentPage = 1;
    private int totalAvailablePages = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_list);
        viewmodel = ViewModelProviders.of(this).get(ViewmodelUserList.class);

        intialization();

    }

    private void intialization() {


        adapter = new AdapterUserList(this::onItemClickListener);
        binding.rvUserList.setHasFixedSize(true);
        binding.rvUserList.setAdapter(adapter);
        binding.rvUserList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!binding.rvUserList.canScrollVertically(1)) {
                    if (currentPage <= totalAvailablePages) {
                        currentPage = currentPage + 1;
                        observers();
                    }
                }
            }
        });
        observers();
    }

    private void observers() {


        viewmodel.fetchUsersList(currentPage).observe(this, new Observer<Resource<UsersList>>() {
            @Override
            public void onChanged(Resource<UsersList> liveDataResource) {
                switch (liveDataResource.status) {
                    case LOADING: {
                        toggleLoading();
                    }
                    break;

                    case SUCCESS: {
                        int oldcount = dataItems.size();
                        totalAvailablePages = liveDataResource.data.getMeta().getPagination().getPages();
                        Log.d(TAG, "getPages: " + totalAvailablePages);

                        dataItems.addAll(liveDataResource.data.getData());
                        adapter.submitList(dataItems);
                        adapter.notifyItemRangeInserted(oldcount, dataItems.size());
                        toggleLoading();
                    }
                    break;

                    case ERROR: {
                        toggleLoading();
                        Toast.makeText(getApplicationContext(), ""+liveDataResource.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            }


        });


    }

    private void toggleLoading() {
        if (currentPage == 1) {

            if (binding.getIsLoading() != null && binding.getIsLoading()) {
                binding.setIsLoading(false);
            } else {
                binding.setIsLoading(true);
            }

        } else {

            if (binding.getIsLoadingMore() != null && binding.getIsLoadingMore() == true) {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.setIsLoadingMore(false);
                    }
                }, 1000);
            } else {

                binding.setIsLoadingMore(true);
            }
        }
    }

    @Override
    public void onItemClickListener(String name) {

        Intent intent = new Intent(UserListScreen.this, UserDetailScreen.class);
        intent.putExtra("name", name);
        startActivity(intent);

    }
}