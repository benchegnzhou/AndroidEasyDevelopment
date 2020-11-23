package com.ztsc.commonuimoudle.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

import com.ztsc.commonuimoudle.R;
import com.ztsc.commonuimoudle.action.ClickAction;
import com.ztsc.commonuimoudle.base.BaseDialog;

public class SubmitSuccessDialog {


    public static final class Builder
            extends UIDialog.Builder<SubmitSuccessDialog.Builder> implements ClickAction {

        private final TextView mMessageView;
        private final TextView mTitleView;
        private final ImageView mIconView;

        private SubmitSuccessDialog.OnListener mListener;
        private final Button mBtnAffirm;

        public Builder(Context context) {
            super(context);
            setContentView(R.layout.submit_success_dialog);
            setAnimStyle(BaseDialog.ANIM_SCALE);
//            setBackgroundDimEnabled(false);
            setCancelable(false);

            mMessageView = findViewById(R.id.tv_hint_message);
            mTitleView = findViewById(R.id.tv_hint_title);
            mIconView = findViewById(R.id.iv_hint_icon);
            mBtnAffirm = findViewById(R.id.btn_affirm);
            setOnClickListener(R.id.btn_affirm);
        }

        public SubmitSuccessDialog.Builder setIcon(@DrawableRes int id) {
            mIconView.setImageResource(id);
            return this;
        }

        public SubmitSuccessDialog.Builder setListener(SubmitSuccessDialog.OnListener listener) {
            mListener = listener;
            return this;
        }

        public SubmitSuccessDialog.Builder setMessage(@StringRes int id) {
            return setMessage(getString(id));
        }

        public SubmitSuccessDialog.Builder setMessage(CharSequence text) {
            mMessageView.setText(text);
            return this;
        }

        public SubmitSuccessDialog.Builder setBtnText(@StringRes int id) {
            return setBtnText(getString(id));
        }

        public SubmitSuccessDialog.Builder setBtnText(CharSequence text) {
            mBtnAffirm.setText(text);
            return this;
        }

        public SubmitSuccessDialog.Builder setTitle(@StringRes int id) {
            return setTitle(getString(id));
        }

        public SubmitSuccessDialog.Builder setTitle(CharSequence text) {
            mTitleView.setText(text);
            mTitleView.setVisibility((text != null && text.length() > 0) ? View.VISIBLE : View.GONE);
            return this;
        }

        @Override
        public BaseDialog create() {
            // 如果显示的图标为空就抛出异常
            if (mIconView.getDrawable() == null) {
                throw new IllegalArgumentException("The display type must be specified");
            }
            // 如果内容为空就抛出异常
            if (TextUtils.isEmpty(mMessageView.getText().toString())) {
                throw new IllegalArgumentException("Dialog message not null");
            }

            return super.create();
        }

        @Override
        public void onClick(View v) {

            int id = v.getId();
            if (id == R.id.btn_affirm) {
                autoDismiss();
                if (mListener != null) {
                    mListener.onConfirm(getDialog());
                }
            }
        }

    }


    public interface OnListener {

        /**
         * 点击确定时回调
         */
        void onConfirm(BaseDialog dialog);

    }

}
