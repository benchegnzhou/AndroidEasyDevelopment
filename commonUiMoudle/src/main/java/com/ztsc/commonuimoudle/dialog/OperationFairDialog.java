package com.ztsc.commonuimoudle.dialog;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.StringRes;

import com.ztsc.commonuimoudle.R;
import com.ztsc.commonuimoudle.base.BaseDialog;


/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/12/2
 *    desc   : 操作失败
 */
public final class OperationFairDialog {

    public static final class Builder
            extends UIDialog.Builder<Builder> {

        private OnListener mListener;

        private final TextView mMessageView;
        private final RelativeLayout rlMessageView;

        public Builder(Context context) {
            super(context);
            setCustomView(R.layout.dialog_operation_fail_layout);
            rlMessageView = findViewById(R.id.rl_message_view);
            mMessageView = findViewById(R.id.tv_message_message);
            setConfirmTvColor(context.getResources().getColor(R.color.common_ui_colorConfirm_delete));
        }

        public Builder setMessage(@StringRes int id) {
            return setMessage(getString(id));
        }
        public Builder setMessage(CharSequence text) {
            mMessageView.setText(text);
            return this;
        }

        public Builder setListener(OnListener listener) {
            mListener = listener;
            return this;
        }

        @Override
        public BaseDialog create() {
            // 如果内容为空就抛出异常
            if ("".equals(mMessageView.getText().toString())) {
                throw new IllegalArgumentException("Dialog message not null");
            }
            return super.create();
        }


        @Override
        public void onClick(View v) {

            int id = v.getId();
            if (id == R.id.tv_ui_confirm) {
                autoDismiss();
                if (mListener != null) {
                    mListener.onConfirm(getDialog());
                }
            } else if (id == R.id.tv_ui_cancel) {
                autoDismiss();
                if (mListener != null) {
                    mListener.onCancel(getDialog());
                }
            }
        }
    }

    public interface OnListener {

        /**
         * 点击确定时回调
         */
        void onConfirm(BaseDialog dialog);

        /**
         * 点击取消时回调
         */
        default void onCancel(BaseDialog dialog) {}
    }
}