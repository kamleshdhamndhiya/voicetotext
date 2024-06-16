package com.test.speechtotext.adapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.test.speechtotext.ItemDetailPage;
import com.test.speechtotext.R;
import com.test.speechtotext.model.newModel.Child;
import com.test.speechtotext.model.newModel.Child__1;
import com.test.speechtotext.model.newModel.Product.Product;
import com.test.speechtotext.model.newModel.Product.Size;
import com.test.speechtotext.utility.ErrorMessage;

import java.util.List;

public class InnerProductItemAdapter extends RecyclerView.Adapter<InnerProductItemAdapter.MyViewHolder> {

    Context context;
    List<Child> CustomerLists;


    public InnerProductItemAdapter(Context context, List<Child> customerList) {
        this.context = context;
        this.CustomerLists = customerList;


    }

    @Override
    public InnerProductItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.address_list_adapter, parent, false);
        return new InnerProductItemAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final InnerProductItemAdapter.MyViewHolder holder, final int position) {
        int Position = position + 1;
        Child itemSelected = CustomerLists.get(position);
        holder.right_aarow_img.setVisibility(View.GONE);
        if (itemSelected.getName() != null && !itemSelected.getName().equals("")) {
            holder.address_tv.setText("" + Position + ". " + itemSelected.getName());
        }if (itemSelected.getPrice() != null && !itemSelected.getPrice().equals("")) {
            holder.address_tv.setText("" + holder.address_tv.getText().toString()+" $"+itemSelected.getPrice());
        }

        if((itemSelected.getChildren()!=null && itemSelected.getChildren().size()>0)|| (itemSelected.getProducts()!=null && itemSelected.getProducts().size()>0)){
            holder.right_aarow_img.setVisibility(View.VISIBLE);
        }else {
            holder.right_aarow_img.setVisibility(View.GONE);
        }
      holder.itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              if(itemSelected.getChildren()!=null && itemSelected.getChildren().size()>0){
                  showChildren(itemSelected.getChildren());
              }else if(itemSelected.getChildren()!=null && itemSelected.getProducts().size()>0){
                  Bundle bundle = new Bundle();
                  bundle.putSerializable("products", itemSelected);
                  ErrorMessage.I(context, ItemDetailPage.class, bundle);
              }
          }
      });



    }

    public void showChildren(List<Child__1> getChildren) {

        Dialog updateDialog = new Dialog(context);
        updateDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        updateDialog.setContentView(R.layout.modifiers_category_popup);
        updateDialog.setCanceledOnTouchOutside(false);
        /*WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(updateDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        updateDialog.getWindow().setAttributes(lp);*/


        TextView title_tv = updateDialog.findViewById(R.id.title_tv);
        RecyclerView modifier_rcv = updateDialog.findViewById(R.id.modifier_rcv);

        title_tv.setText("Sizes" );


        SecondCategoryAdapter   modifierAdapter = new SecondCategoryAdapter(context, getChildren);

        modifier_rcv.setLayoutManager(new LinearLayoutManager(context));
        modifier_rcv.setItemAnimator(new DefaultItemAnimator());
        modifier_rcv.scheduleLayoutAnimation();
        modifier_rcv.setNestedScrollingEnabled(false);
        modifier_rcv.setAdapter(modifierAdapter);
        modifier_rcv.setHasFixedSize(true);
        modifier_rcv.setItemViewCacheSize(10);
        modifierAdapter.notifyDataSetChanged();


        // updateDialog.setCancelable(false);
        if (updateDialog != null)
            updateDialog.show();

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