package com.abhi41.mvvmpractice.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.abhi41.mvvmpractice.R;
import com.abhi41.mvvmpractice.utils.Resource;
import com.abhi41.mvvmpractice.view_model.ViewmodelUserList;
import com.abhi41.mvvmpractice.databinding.ActivityUserDetailScreenBinding;
import com.abhi41.mvvmpractice.response.DataItem;

import java.util.List;

public class UserDetailScreen extends AppCompatActivity {

    private ActivityUserDetailScreenBinding binding;
    private ViewmodelUserList viewmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_detail_screen);

        intialization();
        observers();
    }


    private void intialization() {
        String name = null;
        try {
            Intent intent = getIntent();

            name = intent.getStringExtra("name");
        } catch (Exception e) {
            e.printStackTrace();
        }

        viewmodel = ViewModelProviders.of(this).get(ViewmodelUserList.class);

        viewmodel.fetchUserDetail(name);
    }

    private void observers() {

        viewmodel.getUserDetails().observe(this, new Observer<Resource<List<DataItem>>>() {
            @Override
            public void onChanged(Resource<List<DataItem>> dataItems) {


                switch (dataItems.status){

                    case LOADING:{
                        //show loading stuff
                    }
                    break;

                    case SUCCESS:{

                        if (dataItems.data == null) {
                            return;
                        }
                        binding.setUserData(dataItems.data.get(0));
                    }
                    break;

                    case ERROR:{
                        Toast.makeText(getApplicationContext(), ""+dataItems.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                }


            }
        });

    }
}