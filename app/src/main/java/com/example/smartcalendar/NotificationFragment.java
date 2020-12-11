package com.example.smartcalendar;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

public class NotificationFragment extends DialogFragment implements View.OnClickListener {

    public TextView min5;
    public TextView min10;
    public TextView min30;
    public EditText minCustom;
    public Button doneBtn;

    public NotificationFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_notifications, container);
        min5 =  v.findViewById(R.id.min5);
        min10 =  v.findViewById(R.id.min10);
        min30 =  v.findViewById(R.id.min30);
        minCustom = v.findViewById(R.id.minCustom);
        minCustom.setInputType(InputType.TYPE_CLASS_NUMBER);
        doneBtn = v.findViewById(R.id.doneBtn);

        doneBtn.setOnClickListener(this);
        min5.setOnClickListener(this);
        min10.setOnClickListener(this);
        min30.setOnClickListener(this);

        return v;
    };

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.doneBtn:
                if (TextUtils.isEmpty(minCustom.getText().toString())) {
                    return;
                }
                else {
                    NotificationDialogPasser(minCustom.getText().toString());
                }
                break;

            case R.id.min5:
                NotificationDialogPasser("5");
                break;

            case R.id.min10:
                NotificationDialogPasser("10");
                break;

            case R.id.min30:
                NotificationDialogPasser("30");
                break;

            default:
                break;
        }
    }

    // this passes data from the DialogFragment back to the EditActivity. In EditActivity, look for "onFinishedEditDialog" function as receiver.
    public void NotificationDialogPasser(String inputText) {
        DialogListener dialogListener = (DialogListener) getActivity();
        dialogListener.onFinishEditDialog(inputText);
        dismiss();
    }

    public interface DialogListener {
        void onFinishEditDialog(String inputText);
    }
}
