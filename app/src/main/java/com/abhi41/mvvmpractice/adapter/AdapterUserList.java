package com.abhi41.mvvmpractice.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.abhi41.mvvmpractice.databinding.SingleUserItemBinding;
import com.abhi41.mvvmpractice.response.DataItem;

public class AdapterUserList extends ListAdapter<DataItem, AdapterUserList.ViewHolder> {

    private OnItemUserClickListener clickListener;

    public AdapterUserList(OnItemUserClickListener onItemUserClickListener) {
        super(DataItem.itemCallback);
        clickListener = onItemUserClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        SingleUserItemBinding binding =SingleUserItemBinding.inflate(layoutInflater,parent,false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterUserList.ViewHolder holder, int position) {

        holder.binding.setUsers(getItem(position));
        holder.binding.executePendingBindings();

        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClickListener(getItem(position).getName());
            }
        });

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        SingleUserItemBinding binding;

        public ViewHolder(@NonNull SingleUserItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;



        }
    }

    public interface OnItemUserClickListener{
        void onItemClickListener(String name);
    }
}
