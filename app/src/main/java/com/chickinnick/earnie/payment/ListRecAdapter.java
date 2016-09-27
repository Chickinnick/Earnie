package com.chickinnick.earnie.payment;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chickinnick.earnie.R;
import com.chickinnick.earnie.databinding.ListItemCheckBinding;
import com.chickinnick.earnie.model.PaymentMethod;

public class ListRecAdapter extends RecyclerView.Adapter<ListRecAdapter.ViewHolder> {

    PaymentMethod[] items;

    public ListRecAdapter(PaymentMethod[] items) {
        this.items = items;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ListItemCheckBinding binding;

        public ViewHolder(View v) {
            super(v);
            binding = DataBindingUtil.bind(v);
        }
    }

    @Override
    public ListRecAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {

        ListItemCheckBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.list_item_check, parent, false);
        ViewHolder vh = new ViewHolder(viewDataBinding.getRoot());
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.setPaymentMethod(items[position]);
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

}
