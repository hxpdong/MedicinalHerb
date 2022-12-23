package com.dmt.minhhien.thuocnamapp.Adapter;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dmt.minhhien.thuocnamapp.Custom.ImageConverter;
import com.dmt.minhhien.thuocnamapp.DetailHerbTree;
import com.dmt.minhhien.thuocnamapp.Model.ThuocNam;
import com.dmt.minhhien.thuocnamapp.R;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

class SearchViewHolder extends RecyclerView.ViewHolder{

    public TextView ten_cay, ten_goikhac;
    public RelativeLayout layoutItem;
    public CircleImageView imgCay;
    public SearchViewHolder(View itemView) {
        super(itemView);
        layoutItem = itemView.findViewById(R.id.layout_item);
        ten_cay = (TextView) itemView.findViewById(R.id.tencay);
        ten_goikhac = (TextView) itemView.findViewById(R.id.tengoikhac);
        imgCay = (CircleImageView) itemView.findViewById(R.id.iconcay);
    }
}

public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder> {

    private Context context;
    private List<ThuocNam> thuocNam;

    public SearchAdapter(Context context, List<ThuocNam> thuocNam) {
        this.context = context;
        this.thuocNam = thuocNam;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView  = inflater.inflate(R.layout.layout_item, parent, false);
        return new SearchViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        ThuocNam thuocnam = thuocNam.get(position);
        if(thuocNam == null){
            return;
        }
        holder.ten_cay.setText(thuocNam.get(position).getTenCay());
        holder.ten_goikhac.setText(thuocNam.get(position).getTenGoiKhac());
        byte[] data = thuocNam.get(position).getHinhAnh();
        //fit size of image with ImageView

        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        //bo tron class
        Bitmap circularBitmap = ImageConverter.getRoundedCornerBitmap(bitmap, 100);
        holder.imgCay.setImageBitmap(circularBitmap);
        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickGoToDetail(thuocnam);
            }
        });

    }
    private void onClickGoToDetail(ThuocNam thuocnam){
        Intent intent = new Intent(context, DetailHerbTree.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_user", thuocnam);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    public void release(){
        context = null;
    }
    @Override
    public int getItemCount() {
        return thuocNam.size();
    }

    public void setFilter(ArrayList<ThuocNam> newList){
        thuocNam = newList;
        notifyDataSetChanged();
    }
}
