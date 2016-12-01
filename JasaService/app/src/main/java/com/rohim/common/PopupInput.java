package com.rohim.common;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.DialogFragment;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rohim.enumeration.EnumInputService;
import com.rohim.jasaservice.R;
import com.rohim.modal.RequestDetail;

/**
 * Created by Nurochim on 22/10/2016.
 */

public class PopupInput extends DialogFragment {
    public Utils.OnSubmitListener mListener;
    Context context;
    String title = "";
    TextView textItemValue, textInputNumber;
    String inputClass;
    Button min, add;
    RequestDetail requestDetail;


    public void setParam (Context context, TextView textItemValue, RequestDetail requestDetail) {
        this.context = context;
        this.title = requestDetail.getServiceItemName();
        this.textItemValue = textItemValue;
        this.inputClass = requestDetail.getJenisInput();
        this.requestDetail = requestDetail;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.popup_field_input);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        TextView textTitle= (TextView) dialog.findViewById(R.id.text_input_prompt);
        final EditText textValue = (EditText) dialog.findViewById(R.id.text_input_value);
        LinearLayout layoutAutomaticNumber = (LinearLayout) dialog.findViewById(R.id.layout_automatic_number);
        min = (Button) dialog.findViewById(R.id.button_min);
        add = (Button) dialog.findViewById(R.id.button_add);
        textInputNumber = (TextView) dialog.findViewById(R.id.text_input_number);

        // trigger input class
        if(inputClass.equalsIgnoreCase(EnumInputService.TextLong.getVal())){
            layoutAutomaticNumber.setVisibility(View.GONE);
            if(textItemValue.getText()!= null && !textItemValue.getText().toString().isEmpty()){
                textValue.setText(textItemValue.getText().toString());
            }
        }else if(inputClass.equalsIgnoreCase(EnumInputService.TextAutomatic.getVal())){
            textValue.setVisibility(View.GONE);
            if(textItemValue.getText()!= null && !textItemValue.getText().toString().isEmpty()){
                textInputNumber.setText(textItemValue.getText().toString());
            }else{
                textItemValue.setText("1");
            }
        }


        // set text
        textTitle.setText(title);




        Button btn = (Button) dialog.findViewById(R.id.btnDone);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(inputClass.equalsIgnoreCase(EnumInputService.TextLong.getVal())){
                    textItemValue.setText(textValue.getText().toString());
                    requestDetail.setServiceItemValue(textValue.getText().toString());
                }else if(inputClass.equalsIgnoreCase(EnumInputService.TextAutomatic.getVal())){
                    textItemValue.setText(textInputNumber.getText().toString());
                    requestDetail.setServiceItemValue(textInputNumber.getText().toString());
                }

                dismiss();
            }
        });

        Button btnCancel = (Button
                ) dialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.valueOf(textInputNumber.getText().toString());
                if(number>1){
                    number--;
                }
                textInputNumber.setText(String.valueOf(number));
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.valueOf(textInputNumber.getText().toString());
                if(number<50){
                    number++;
                }
                textInputNumber.setText(String.valueOf(number));
            }
        });
//        (new Handler()).postDelayed(new Runnable() {
//
//            public void run() {
//                value.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN , 0, 0, 0));
//                value.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP , 0, 0, 0));
//            }
//        }, 200);
        return dialog;
    }


}
