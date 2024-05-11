package com.test.speechtotext.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;


import com.test.speechtotext.R;
import com.test.speechtotext.model.ItemSelected;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.MyViewHolder> {

    Context context;
    List<ItemSelected> CustomerLists;


    public ItemListAdapter(Context context, List<ItemSelected> customerList) {
        this.context = context;
        this.CustomerLists = customerList;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.address_list_adapter, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        int Position = position + 1;
        ItemSelected itemSelected = CustomerLists.get(position);
        if (itemSelected.getItem_name() != null && !itemSelected.getItem_name().equals("")) {
            holder.address_tv.setText("" + Position + ". " + itemSelected.getItem_name());
        }

        if (itemSelected.getSubcategory_name() != null && !itemSelected.getSubcategory_name().equals("")) {

            holder.subcategory_tv.setText(" (" + itemSelected.getSubcategory_name() + ")");
            holder.subcategory_tv.setTextColor(context.getResources().getColor(R.color.grey));
        }
        if (itemSelected.getError_message() != null && !itemSelected.getError_message().equals("")) {

            holder.subcategory_tv.setText(" (" + itemSelected.getError_message() + ")");
            holder.subcategory_tv.setTextColor(context.getResources().getColor(R.color.red));
        }


    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return CustomerLists.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView address_tv;
        TextView subcategory_tv;


        public MyViewHolder(View view) {
            super(view);

            address_tv = view.findViewById(R.id.address_tv);
            subcategory_tv = view.findViewById(R.id.subcategory_tv);

        }
    }


}
