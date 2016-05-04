package com.example.horacechan.parking;

import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView.OnQRCodeReadListener;
import com.example.horacechan.parking.api.LocalHost;
import com.example.horacechan.parking.api.http.base.BaseResponse;
import com.example.horacechan.parking.api.http.base.BaseResponseListener;
import com.example.horacechan.parking.api.http.request.ScanQRRequest;

public class scanQRCodeActivity extends ActionBarActivity implements OnQRCodeReadListener, BaseResponseListener {

    private QRCodeReaderView mydecoderview;
    private ScanQRRequest scanQRRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qrcode);

        mydecoderview = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
        mydecoderview.setOnQRCodeReadListener(this);

        scanQRRequest = new ScanQRRequest();
        scanQRRequest.setOnResponseListener(this);
        scanQRRequest.setRequestType(0);
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {

        mydecoderview.getCameraManager().stopPreview();
        Toast.makeText(scanQRCodeActivity.this, text, Toast.LENGTH_LONG).show();
        String logid = text.split(",")[0];
        String markerid = text.split(",")[1];

        scanQRRequest.logid = logid;
        scanQRRequest.userid = LocalHost.INSTANCE.getUserid();
        scanQRRequest.carid = LocalHost.INSTANCE.getUserCar();
        scanQRRequest.markerid = markerid;

        scanQRRequest.post();
    }

    @Override
    public void cameraNotFound() {

    }

    @Override
    public void QRCodeNotFoundOnCamImage() {
        //Toast.makeText(scanQRCodeActivity.this,"图像未识别",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mydecoderview.getCameraManager().startPreview();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mydecoderview.getCameraManager().stopPreview();
    }

    @Override
    public void onStart(BaseResponse response) {

    }

    @Override
    public void onFailure(BaseResponse response) {
        Toast.makeText(scanQRCodeActivity.this,"请求失败",Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void onSuccess(BaseResponse response) {

        if (response.getStatus()==200){
            switch (response.getRequestType()){
                case 0:
                    Toast.makeText(scanQRCodeActivity.this,response.getMsg(),Toast.LENGTH_LONG).show();
                    finish();
                    break;
            }
        }else{
            Toast.makeText(scanQRCodeActivity.this,response.getMsg(),Toast.LENGTH_LONG).show();
            finish();
        }

    }
}
