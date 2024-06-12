package com.test.speechtotext.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.test.speechtotext.R;
import com.test.speechtotext.model.newModel.Child;
import com.test.speechtotext.model.newModel.Child__1;
import com.test.speechtotext.model.newModel.Child__1WithQuantity;

import java.util.List;

public class SecondSubCategoryAdapter extends RecyclerView.Adapter<SecondSubCategoryAdapter.MyViewHolder> {

    Context context;
    List<Child__1WithQuantity> CustomerLists;


    public SecondSubCategoryAdapter(Context context, List<Child__1WithQuantity> customerList) {
        this.context = context;
        this.CustomerLists = customerList;


    }

    @Override
    public SecondSubCategoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.address_list_adapter, parent, false);
        return new SecondSubCategoryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SecondSubCategoryAdapter.MyViewHolder holder, final int position) {
        int Position = position + 1;
        Child__1WithQuantity itemSelected = CustomerLists.get(position);
        if (itemSelected.getChild__1().getName() != null && !itemSelected.getChild__1().getName().equals("")&& itemSelected.getChild__1().getPrice()!=null) {
           if(itemSelected.getQuantity()>0){
               holder.address_tv.setText("" + Position + ". " + itemSelected.getChild__1().getName() +"  "+itemSelected.getQuantity()+ " * ($" + itemSelected.getChild__1().getPrice()+")");

           }
           else {
               holder.address_tv.setText("" + Position + ". " + itemSelected.getChild__1().getName() + "  $" + itemSelected.getChild__1().getPrice());
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


        public MyViewHolder(View view) {
            super(view);

            address_tv = view.findViewById(R.id.address_tv);
            subcategory_tv = view.findViewById(R.id.subcategory_tv);

        }
    }


}

