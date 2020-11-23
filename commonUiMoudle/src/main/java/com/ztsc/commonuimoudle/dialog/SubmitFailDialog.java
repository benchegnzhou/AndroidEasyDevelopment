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

public class SubmitFailDialog {


    public static final class Builder
            extends UIDialog.Builder<SubmitFailDialog.Builder> implements ClickAction {

        private final TextView mMessageView;
        private final ImageView mIconView;

        private SubmitFailDialog.OnListener mListener;
        private final Button mBtnAffirm;

        public Builder(Context context) {
            super(context);
            setContentView(R.layout.submit_fail_dialog);
            setAnimStyle(BaseDialog.ANIM_SCALE);
//            setBackgroundDimEnabled(false);
            setCancelable(false);

            mMessageView = findViewById(R.id.tv_hint_message);
            mIconView = findViewById(R.id.iv_hint_icon);
            mBtnAffirm = findViewById(R.id.btn_affirm);
            setOnClickListener(R.id.btn_affirm);
        }

        public SubmitFailDialog.Builder setIcon(@DrawableRes int id) {
            mIconView.setImageResource(id);
            return this;
        }

        public SubmitFailDialog.Builder setListener(SubmitFailDialog.OnListener listener) {
            mListener = listener;
            return this;
        }

        public SubmitFailDialog.Builder setMessage(@StringRes int id) {
            return setMessage(getString(id));
        }

        public SubmitFailDialog.Builder setMessage(CharSequence text) {
            mMessageView.setText(text);
            return this;
        }

        public SubmitFailDialog.Builder setBtnText(@StringRes int id) {
            return setBtnText(getString(id));
        }

        public SubmitFailDialog.Builder setBtnText(CharSequence text) {
            mBtnAffirm.setText(text);
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
