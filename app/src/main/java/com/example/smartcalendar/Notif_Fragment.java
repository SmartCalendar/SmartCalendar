package com.example.smartcalendar;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class Notif_Fragment extends DialogFragment implements View.OnClickListener {

    public TextView min5;
    public TextView min10;
    public TextView min30;
    public EditText mincustom;
    public Button donebtn;

    public Notif_Fragment() {
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
        mincustom = v.findViewById(R.id.mincustom);
        mincustom.setInputType(InputType.TYPE_CLASS_NUMBER);
        donebtn = v.findViewById(R.id.donebtn);

        donebtn.setOnClickListener(this);
        min5.setOnClickListener(this);
        min10.setOnClickListener(this);
        min30.setOnClickListener(this);

        return v;

    };


    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.donebtn:
                if (TextUtils.isEmpty(mincustom.getText().toString())) {
                    Toast.makeText(getContext(), "You did not enter a number for custom minutes", Toast.LENGTH_SHORT).show();
                }
                else {
                    NotifDialogPasser(mincustom.getText().toString());
                }
                break;

            case R.id.min5:
                NotifDialogPasser("5");
                break;

            case R.id.min10:
                NotifDialogPasser("10");
                break;

            case R.id.min30:
                NotifDialogPasser("30");
                break;

            default:
                break;
        }
    }

    // this passes data from the DialogFragment back to the EditActivity. In EditActivity, look for "onFinishedEditDialog" function as receiver.
    public void NotifDialogPasser(String inputText) {
        DialogListener dialogListener = (DialogListener) getActivity();
        dialogListener.onFinishEditDialog(inputText);
        dismiss();
    }

    public interface DialogListener {
        void onFinishEditDialog(String inputText);
    }


}
