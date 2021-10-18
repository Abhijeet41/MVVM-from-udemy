package com.abhi41.mvvmpractice.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.abhi41.mvvmpractice.R;
import com.abhi41.mvvmpractice.ViewModel.ViewmodelUserList;
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


        viewmodel.fetchUsersList(currentPage).observe(this, new Observer<UsersList>() {
            @Override
            public void onChanged(UsersList usersList) {

                if (usersList != null) {
                    int oldcount = dataItems.size();
                    totalAvailablePages = usersList.getMeta().getPagination().getPages();
                    Log.d(TAG, "getPages: " + totalAvailablePages);

                    dataItems.addAll(usersList.getData());

                    adapter.submitList(dataItems);
                    adapter.notifyItemRangeInserted(oldcount, dataItems.size());
                }
            }
        });

        viewmodel.getLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                if (aBoolean)
                {
                    toggleLoading();
                }else {
                    toggleLoading();
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
                binding.setIsLoadingMore(false);
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