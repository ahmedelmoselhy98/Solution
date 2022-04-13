package com.elmoselhy.solution.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.elmoselhy.solution.R;
import com.elmoselhy.solution.databinding.ItemCorporationBinding;
import com.elmoselhy.solution.model.response.Corporation;
import com.elmoselhy.solution.ui.user.ConfirmLocationActivity;

import java.util.List;

public class CorporationAdapter  extends RecyclerView.Adapter<CorporationAdapter.CouponVH> {
    List<Corporation> list;
    Context context;
    public CorporationAdapter(Context context,List<Corporation> list) {
        this.list = list;
        this.context = context;
    }
    @NonNull
    @Override
    public CouponVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCorporationBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_corporation, parent, false);
        return new CouponVH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CouponVH holder, int position) {
        holder.itemView.setOnClickListener(view -> {
            context.startActivity(new Intent(context, ConfirmLocationActivity.class));
        });
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    class CouponVH extends RecyclerView.ViewHolder {
        ItemCorporationBinding binding;

        public CouponVH(@NonNull ItemCorporationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

}