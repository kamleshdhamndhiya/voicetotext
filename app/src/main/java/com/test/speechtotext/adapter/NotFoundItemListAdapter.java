package com.test.speechtotext.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.test.speechtotext.R;
import com.test.speechtotext.model.ItemSelected;

import java.util.List;


public class NotFoundItemListAdapter extends RecyclerView.Adapter<NotFoundItemListAdapter.MyViewHolder> {

    Context context;
    List<String> CustomerLists;


    public NotFoundItemListAdapter(Context context, List<String> customerList) {
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
        String itemSelected = CustomerLists.get(position);

        if (itemSelected!= null && !itemSelected.equals("")) {

            holder.subcategory_tv.setText("* " + itemSelected + " not found in our database.");
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
