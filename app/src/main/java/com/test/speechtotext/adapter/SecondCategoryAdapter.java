package com.test.speechtotext.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.test.speechtotext.ItemDetailPage;
import com.test.speechtotext.R;
import com.test.speechtotext.model.newModel.Child__1;
import com.test.speechtotext.model.newModel.Child__1WithQuantity;
import com.test.speechtotext.utility.ErrorMessage;

import java.util.List;

public class SecondCategoryAdapter extends RecyclerView.Adapter<SecondCategoryAdapter.MyViewHolder> {

    Context context;
    List<Child__1> CustomerLists;


    public SecondCategoryAdapter(Context context, List<Child__1> customerList) {
        this.context = context;
        this.CustomerLists = customerList;


    }

    @Override
    public SecondCategoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.address_list_adapter, parent, false);
        return new SecondCategoryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SecondCategoryAdapter.MyViewHolder holder, final int position) {
        int Position = position + 1;
        Child__1 itemSelected = CustomerLists.get(position);
        if (itemSelected.getName() != null && !itemSelected.getName().equals("")&& itemSelected.getPrice()!=null) {
            if(itemSelected.getQuantity()!=null && itemSelected.getQuantity()>0){
                holder.address_tv.setText("" + Position + ". " + itemSelected.getName() +"  "+itemSelected.getQuantity()+ " * ($" + itemSelected.getPrice()+")");

            }
            else {
                holder.address_tv.setText("" + Position + ". " + itemSelected.getName() + "  $" + itemSelected.getPrice());
            }
        }
        if(itemSelected.getProducts()!=null && itemSelected.getProducts().size()>0){
            holder.right_aarow_img.setVisibility(View.VISIBLE);
        }else {
            holder.right_aarow_img.setVisibility(View.GONE);
        }
        holder.right_aarow_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                  if ((itemSelected.getChildren()!=null && itemSelected.getChildren().size()>0) || (itemSelected.getProducts()!=null && itemSelected.getProducts().size()>0)) {
                      Bundle bundle = new Bundle();
                      bundle.putSerializable("menu_child", itemSelected);
                      ErrorMessage.I(context, ItemDetailPage.class, bundle);

              }
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
        ImageView right_aarow_img;


        public MyViewHolder(View view) {
            super(view);

            address_tv = view.findViewById(R.id.address_tv);
            subcategory_tv = view.findViewById(R.id.subcategory_tv);
            right_aarow_img = view.findViewById(R.id.right_aarow_img);

        }
    }


}
