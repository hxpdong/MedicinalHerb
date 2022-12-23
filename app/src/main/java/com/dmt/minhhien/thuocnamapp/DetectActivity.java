package com.dmt.minhhien.thuocnamapp;


import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.dmt.minhhien.thuocnamapp.Database.Database;
import com.dmt.minhhien.thuocnamapp.Model.ThuocNam;
import com.dmt.minhhien.thuocnamapp.tflite.Classifier;
import com.dmt.minhhien.thuocnamapp.tflite.YoloV5Classifier;
import com.dmt.minhhien.thuocnamapp.Adapter.SearchAdapter;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class DetectActivity extends AppCompatActivity {

    public static final float MINIMUM_CONFIDENCE_TF_OD_API = 0.1f;
    public static final int TF_OD_API_INPUT_SIZE = 640;
    private static final boolean TF_OD_API_IS_QUANTIZED = false;
    private static final String TF_OD_API_MODEL_FILE = "yolov5sv2.tflite";
    private static final String TF_OD_API_LABELS_FILE = "file:///android_asset/classes.txt";

    ImageView imgHinh;
    ImageView btnCamera, btnChoose;
    TextView txtResult;
    TextView txtTitle;
    TextView txtTutor2;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SearchAdapter adapter;
    ProgressDialog noti;
    ActionBar ab;

    Database database;
    private Classifier detector;
    int imgSize = TF_OD_API_INPUT_SIZE;


    int id_ho;
    int id_cay;
    int id_nhom;
    String tenHo, tenNhom;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //connect with objects in xml
        setContentView(R.layout.activity_detect);
        imgHinh = (ImageView) findViewById(R.id.imageViewHinh);
        btnCamera= (ImageView) findViewById(R.id.btnOpenCam);
        btnChoose= (ImageView) findViewById(R.id.btnChooseImg);
        txtResult = (TextView) findViewById(R.id.result);
        txtTitle = (TextView) findViewById(R.id.textViewTitle);
        txtTutor2 = (TextView) findViewById(R.id.tutorial2);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_detect);
        //
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        ab = getSupportActionBar();
        ab.setTitle("Tra cứu hình ảnh");
        imgHinh.setClickable(false);
        noti = new ProgressDialog(this);
        noti.setTitle("Đang nhận diện");
        noti.setMessage("Vui lòng đợi...");
        noti.setCancelable(false);
        //
        database = new Database(this);

        //
        hiddenWhenDetect(0);
        //set on click listener for button
        btnCamera.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) { openCamera(); }
        });
        btnChoose.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {openGallery();}
        });
        //connect to model to detect
        try {
            detector =
                    YoloV5Classifier.create(
                            getAssets(),
                            TF_OD_API_MODEL_FILE,
                            TF_OD_API_LABELS_FILE,
                            TF_OD_API_IS_QUANTIZED,
                            TF_OD_API_INPUT_SIZE);
        } catch (final IOException e) {
            e.printStackTrace();
            //LOGGER.e(e, "Exception initializing classifier!");
            Toast toast =
                    Toast.makeText(
                            getApplicationContext(), "Classifier could not be initialized", Toast.LENGTH_SHORT);
            toast.show();
            finish();
        }


    }


    public void openCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        startActivityForResult(intent, 2);


    }
    public void openGallery(){
        //Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 3);
    }

    public void upnewimage(View view) {
        Intent intent = new Intent(this, DetectActivity.class);
        startActivity(intent);
        finish();
    }

    public double caldis(int x1, int y1, int x2,int y2){
        return (Math.sqrt(Math.pow((x1-x2), 2) + Math.pow((y1-y2), 2)));
    }
    public void classifyImage(Bitmap img){
        imgHinh.setVisibility(View.VISIBLE);
        txtResult.setVisibility(View.GONE);

        final List<Classifier.Recognition> results = detector.recognizeImage(img);
        final Canvas canvas = new Canvas(img);

        String x = "";
        int maxpos = -1;
        float maxconf = 0;
        String maxclass = "";
        RectF location = null;
        RectF locationReal = null;
        int ypos = 0;
        int xpos = 0;
        int xmid = TF_OD_API_INPUT_SIZE/2;
        int ymid = TF_OD_API_INPUT_SIZE/2;
        double dis =0.0f;

        for (final Classifier.Recognition result : results) {
            //locationReal = result.getLocation();
            int x1 = (int) (result.getLocation().right+result.getLocation().left)/2;
            int y1 = (int) (result.getLocation().bottom+result.getLocation().top)/2;
            System.out.println("class: " + result.getTitle() + " x1: " + x1 +" y1: " + y1);
            double dist = caldis(x1, y1, xmid, ymid);
            System.out.println("distant: "+ dist);
//            final Paint paint = new Paint();
//            paint.setColor(Color.GREEN);
//            paint.setStyle(Paint.Style.STROKE);
//            paint.setStrokeWidth(7.0f);
//            canvas.drawRect(result.getLocation(), paint);

            if (result.getConfidence() > MINIMUM_CONFIDENCE_TF_OD_API){
                //Lay doi tuong co conf cao nhat
                /*if (result.getConfidence() > maxconf){
                    maxconf = result.getConfidence();
                    maxclass = result.getTitle();
                    maxpos = result.getDetectedClass();
                    location = result.getLocation();
                }*/

                //Lay doi tuong o gan tam hinh nhat
                if(caldis(x1, y1, xmid, ymid) < caldis(xpos, ypos, xmid, ymid)){
                    maxconf = result.getConfidence();
                    maxclass = result.getTitle();
                    maxpos = result.getDetectedClass();
                    location = result.getLocation();
                    xpos = x1;
                    ypos = y1;
                    dis = caldis(xpos, ypos, xmid, ymid);
                }
            }
        }

        if (maxpos == -1){
            txtResult.setVisibility(View.VISIBLE);
            txtResult.setText("Không nhận dạng được");
        }
        else {
            txtResult.setVisibility(View.VISIBLE);
            x = String.format("%.2f%%", maxconf*100);
            txtResult.setText("Tỷ lệ trùng khớp: "+ x);
            maxpos = maxpos + 1;
            getDetect(maxpos);
            System.out.println("xpos: " + xpos +" ypos: " + ypos);
            System.out.println("distantpos: " + dis);


            final Paint paint2 = new Paint();
            paint2.setColor(Color.GREEN);
            paint2.setStyle(Paint.Style.STROKE);
            paint2.setStrokeWidth(7.0f);
            canvas.drawRect(location, paint2);

            System.out.println("rect:" + location.toString());

        }
    }

    private void getDetect(Integer cay_id) {
        List<ThuocNam> thuocNam = database.getCayThuocById(cay_id);
        adapter = new SearchAdapter(this, thuocNam);
        recyclerView.setAdapter(adapter);
        tenHo = thuocNam.get(0).getTenHo();
        id_ho = Integer.parseInt(thuocNam.get(0).getIdHo());
        id_cay = Integer.parseInt(thuocNam.get(0).getIdCay());
        id_nhom = Integer.parseInt(thuocNam.get(0).getIdNhom());
        tenNhom = thuocNam.get(0).getTenNhom();
    }

    private void hiddenWhenDetect(Integer sign){
        if (sign == 1) {
            btnCamera.setVisibility(View.GONE);
            btnChoose.setVisibility(View.GONE);
            txtTutor2.setVisibility(View.GONE);

            txtResult.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        else if (sign == 0){
            btnCamera.setVisibility(View.VISIBLE);
            btnChoose.setVisibility(View.VISIBLE);
            txtTutor2.setVisibility(View.VISIBLE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Bitmap image = null;
        if(resultCode == RESULT_OK){
            if (requestCode == 2){
                image = (Bitmap) data.getExtras().get("data");
//                File f = new File(Environment.getExternalStorageDirectory().toString());
//                for (File temp: f.listFiles()){
//                    if(temp.getName().equals("temp.jpg")){
//                        f = temp;
//                        break;
//                    }
//                }
//                try {
//                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
//                    image = BitmapFactory.decodeFile(f.getAbsolutePath(), bitmapOptions);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                int dimension = Math.min(image.getWidth(), image.getHeight());
                image = ThumbnailUtils.extractThumbnail(image, dimension,dimension);
                image = Bitmap.createScaledBitmap(image, imgSize, imgSize, false);
            }
            else if (requestCode == 3){
                Uri dat = data.getData();
                try {
                    image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), dat);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                int dimension = Math.min(image.getWidth(), image.getHeight());
                image = ThumbnailUtils.extractThumbnail(image, dimension,dimension);
                image = Bitmap.createScaledBitmap(image, imgSize, imgSize, false);
            }
        }
        if (image != null){
            Bitmap mutableBitmap = image.copy(Bitmap.Config.ARGB_8888, true);
            imgHinh.setImageBitmap(mutableBitmap);
            hiddenWhenDetect(1);
            imgHinh.setClickable(true);
            txtTitle.setText("Chạm hình ảnh để thay đổi");
            ab.setTitle("Kết quả nhận diện");
            txtResult.setText("");
            adapter = null;
            recyclerView.setAdapter(adapter);
            noti.show();
            new CountDownTimer(1000, 1000) {
                public void onFinish() {
                    classifyImage(mutableBitmap);
                    noti.dismiss();
                }
                public void onTick(long millisUntilFinished) {

                }
            }.start();
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(adapter != null)
            adapter.release();
    }
}