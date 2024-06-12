package com.test.speechtotext.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.test.speechtotext.ItemDetailPage;
import com.test.speechtotext.R;
import com.test.speechtotext.model.ItemSelected;
import com.test.speechtotext.model.newModel.Menu;
import com.test.speechtotext.utility.ErrorMessage;

import java.util.List;

public class MainMenuAdapter extends RecyclerView.Adapter<MainMenuAdapter.MyViewHolder> {

    Context context;
    List<Menu> CustomerLists;


    public MainMenuAdapter(Context context, List<Menu> customerList) {
        this.context = context;
        this.CustomerLists = customerList;


    }

    @Override
    public MainMenuAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.address_list_adapter, parent, false);
        return new MainMenuAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MainMenuAdapter.MyViewHolder holder, final int position) {
        int Position = position + 1;
        Menu itemSelected = CustomerLists.get(position);
        if (itemSelected.getName() != null && !itemSelected.getName().equals("")) {
            holder.address_tv.setText("" + Position + ". " + itemSelected.getName());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putSerializable("main_menu",itemSelected);
                ErrorMessage.I(context, ItemDetailPage.class,bundle);
            }
        });

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
