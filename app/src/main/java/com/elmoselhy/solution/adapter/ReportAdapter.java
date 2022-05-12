package com.elmoselhy.solution.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.elmoselhy.solution.R;
import com.elmoselhy.solution.databinding.ItemUserReportBinding;
import com.elmoselhy.solution.model.response.Report;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.CouponVH> {
    List<Report> list;
    Context context;
    public ReportAdapter(Context context, List<Report> list) {
        this.list = list;
        this.context = context;
    }
    @NonNull
    @Override
    public CouponVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemUserReportBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_user_report, parent, false);
        return new CouponVH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CouponVH holder, int position) {
        holder.binding.setReport(list.get(position));
        holder.itemView.setOnClickListener(view -> {
//            context.startActivity(new Intent(context, ConfirmLocationActivity.class));
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class CouponVH extends RecyclerView.ViewHolder {
        ItemUserReportBinding binding;

        public CouponVH(@NonNull ItemUserReportBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

}