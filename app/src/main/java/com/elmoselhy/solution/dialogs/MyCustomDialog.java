package com.elmoselhy.solution.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;

import com.elmoselhy.solution.R;
import com.elmoselhy.solution.databinding.DialogCustomBinding;

public class MyCustomDialog extends Dialog {


    DialogCustomBinding binding;
    private Context context;
    private ClickAction clickAction;

    public MyCustomDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        initUi();
    }

    private void initUi() {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_custom, null, false);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        setContentView(binding.getRoot());
        setCancelable(false);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding.setLifecycleOwner((LifecycleOwner) context);
    }

    public void clickAction(ClickAction clickAction) {
        binding.yesBtn.setOnClickListener(view -> {
            clickAction.onButtonClick(true);
            dismiss();
        });
        binding.noBtn.setOnClickListener(view -> {
            clickAction.onButtonClick(false);
            dismiss();
        });
    }

    public interface ClickAction {
        void onButtonClick(boolean isPositive);
    }


    public void setImage(int image) {
        if (image == -1) {
            binding.image.setVisibility(View.GONE);
        } else {
            binding.image.setVisibility(View.VISIBLE);
            binding.image.setImageResource(image);
        }
    }

    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            binding.titleTv.setVisibility(View.VISIBLE);
            binding.titleTv.setText(title);
        }
    }

    public void setMessage(String message) {
        if (!TextUtils.isEmpty(message)) {
            binding.messageTv.setVisibility(View.VISIBLE);
            binding.messageTv.setText(message);
        } else {
            binding.messageTv.setVisibility(View.GONE);
        }
    }

    public void setPositiveBtn(String message) {
        if (!TextUtils.isEmpty(message)) {
            binding.yesBtn.setVisibility(View.VISIBLE);
            binding.yesBtn.setText(message);
        } else {
            binding.yesBtn.setVisibility(View.GONE);
        }
    }

    public void setNegativeBtn(String message) {
        if (!TextUtils.isEmpty(message)) {
            binding.noBtn.setVisibility(View.VISIBLE);
            binding.noBtn.setText(message);
        } else {
            binding.noBtn.setVisibility(View.GONE);
        }
    }

    public void isInfoDialog(boolean flag) {
        if (flag) {
            binding.noBtn.setVisibility(View.GONE);
            binding.image.setVisibility(View.VISIBLE);
            binding.yesBtn.setText(R.string.ok);
            binding.btnContainer.setGravity(Gravity.CENTER);
        }
    }

    public void isBlockDialog(boolean flag) {
        if (flag) {
            binding.noBtn.setVisibility(View.GONE);
            binding.image.setVisibility(View.VISIBLE);
            binding.yesBtn.setVisibility(View.GONE);
            binding.btnContainer.setGravity(Gravity.CENTER);
        }
    }


    public void isConfirmDialog(boolean flag) {
        if (flag) {
            binding.yesBtn.setVisibility(View.VISIBLE);
            binding.noBtn.setVisibility(View.VISIBLE);
            binding.image.setVisibility(View.VISIBLE);
            binding.yesBtn.setText(R.string.yes);
            binding.noBtn.setText(R.string.no);
            binding.btnContainer.setGravity(Gravity.END);
        }
    }

}
