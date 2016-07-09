package com.smartgateapps.logic.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.smartgateapps.logic.MyApplication;
import com.smartgateapps.logic.R;
import com.smartgateapps.logic.model.FeulRequest;
import com.smartgateapps.logic.model.RequestType;

import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import info.hoang8f.android.segmented.SegmentedGroup;

public class SendRequestActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_request);

        TextView introTxtV = (TextView) findViewById(R.id.introTxtV);
        final EditText fullNameEdTxt = (EditText) findViewById(R.id.fullNameEdTx);
        final EditText phoneEdTxt = (EditText) findViewById(R.id.phoneNumberEdTx);
        final EditText emailEdTxt = (EditText) findViewById(R.id.emailEdTx);
        final EditText areaEdTxt = (EditText) findViewById(R.id.areaEdTx);
        final EditText streetEdTxt = (EditText) findViewById(R.id.streetEdTx);
        final EditText buildingEdTxt = (EditText) findViewById(R.id.buildingEdTx);
        final EditText infoEdTxt = (EditText) findViewById(R.id.infoEdTx);
        final EditText quantityEdTxt = (EditText) findViewById(R.id.quantityEdTx);
        final EditText notesEdTxt = (EditText) findViewById(R.id.notesEdTx);
        Button sendRqstBtn = (Button) findViewById(R.id.sendRequestBtn);
        final SegmentedGroup typeRadBtn = (SegmentedGroup) findViewById(R.id.typeRadBtn);

        String html = MyApplication.intro.getReturnData().getHTML();
        try {
            html = URLDecoder.decode(html, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        introTxtV.setText(Html.fromHtml(html));


        sendRqstBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean valid = true;

                if (TextUtils.isEmpty(fullNameEdTxt.getText())) {
                    fullNameEdTxt.setError("Full Name Is Required");
                    ((TextInputLayout)fullNameEdTxt.getParent()).requestFocus();
                    valid = false;
                }
                if (TextUtils.isEmpty(phoneEdTxt.getText())) {
                    phoneEdTxt.setError("Phone Number Is Required");
                    if (valid)
                        ((TextInputLayout)phoneEdTxt.getParent()).requestFocus();
                    valid = false;
                }
                if (!MyApplication.isValidEmail(emailEdTxt.getText())) {
                    emailEdTxt.setError("Please Enter A Valid Email Address");
                    if (valid)
                        ((TextInputLayout)emailEdTxt.getParent()).requestFocus();
                    valid = false;
                }
                if (TextUtils.isEmpty(areaEdTxt.getText())) {
                    areaEdTxt.setError("Area Is Required");
                    if (valid)
                        ((TextInputLayout)areaEdTxt.getParent()).requestFocus();
                    valid = false;
                }
                if (TextUtils.isEmpty(streetEdTxt.getText())) {
                    streetEdTxt.setError("Street Is Required");
                    if (valid)
                        ((TextInputLayout)streetEdTxt.getParent()).requestFocus();
                    valid = false;
                }
                if (TextUtils.isEmpty(buildingEdTxt.getText())) {
                    buildingEdTxt.setError("Building Is Required");
                    if (valid)
                        ((TextInputLayout)buildingEdTxt.getParent()).requestFocus();
                    valid = false;
                }
                if (TextUtils.isEmpty(quantityEdTxt.getText())) {
                    quantityEdTxt.setError("Quantity Is Required");
                    if (valid)
                        ((TextInputLayout)quantityEdTxt.getParent()).requestFocus();
                    valid = false;
                }

                if (!valid)
                    return;
                FeulRequest request = new FeulRequest();
                request.setArea(areaEdTxt.getText().toString());
                request.setBuilding(buildingEdTxt.getText().toString());
                request.setEmail(emailEdTxt.getText().toString());
                request.setInfo(infoEdTxt.getText().toString());
                request.setName(fullNameEdTxt.getText().toString());
                request.setNotes(notesEdTxt.getText().toString());
                request.setPhone(phoneEdTxt.getText().toString());
                request.setQuantity(quantityEdTxt.getText().toString());
                request.setStreet(streetEdTxt.getText().toString());

                int typeId = typeRadBtn.getCheckedRadioButtonId();
                if (typeId == R.id.radBtn1)
                    request.setType(RequestType.GREEN);
                else
                    request.setType(RequestType.RED);

                Intent intent = new Intent(SendRequestActivity.this, ConfirmActivity.class);
                intent.putExtra("REQUST", request);
                startActivityForResult(intent, 1);

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                finish();
            }
        }
    }
}
