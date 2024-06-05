package com.test.speechtotext.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.test.speechtotext.R;
import com.test.speechtotext.model.newModel.Child;
import com.test.speechtotext.model.newModel.ChildWithQuantity;
import com.test.speechtotext.model.newModel.Menu;
import com.test.speechtotext.model.newModel.Product.Modifier;
import com.test.speechtotext.model.newModel.Product.Size;

import java.util.List;

public class MainMeuChildrenAdapter extends RecyclerView.Adapter<MainMeuChildrenAdapter.MyViewHolder> {

    Context context;
    List<ChildWithQuantity> CustomerLists;


    public MainMeuChildrenAdapter(Context context, List<ChildWithQuantity> customerList) {
        this.context = context;
        this.CustomerLists = customerList;


    }

    @Override
    public MainMeuChildrenAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.address_list_adapter, parent, false);
        return new MainMeuChildrenAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MainMeuChildrenAdapter.MyViewHolder holder, final int position) {
        int Position = position + 1;
        ChildWithQuantity itemSelected = CustomerLists.get(position);
        if (itemSelected.getChild().getName() != null && !itemSelected.getChild().getName().equals("")) {
            if(itemSelected.getQuantity()>0){
                holder.address_tv.setText("" + Position + ". " + itemSelected.getChild().getName()+"  "+itemSelected.getQuantity()+"* ($"+itemSelected.getChild().getPrice()+")");

            }
            else {
                holder.address_tv.setText("" + Position + ". " + itemSelected.getChild().getName());
            }
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

/*
        public MyViewHolder(View view) {
            super(view);

            address_tv = view.findViewById(R.id.address_tv);
            subcategory_tv = view.findViewById(R.id.subcategory_tv);

        }*/
        LinearLayout modifier_layout;
        Button modifier_btn;
        Button sizes_btn;


        public MyViewHolder(View view) {
            super(view);

            address_tv = view.findViewById(R.id.address_tv);
            subcategory_tv = view.findViewById(R.id.subcategory_tv);
            modifier_layout = view.findViewById(R.id.modifier_layout);
            modifier_btn = view.findViewById(R.id.modifier_btn);
            sizes_btn = view.findViewById(R.id.sizes_btn);

        }
    }


}
