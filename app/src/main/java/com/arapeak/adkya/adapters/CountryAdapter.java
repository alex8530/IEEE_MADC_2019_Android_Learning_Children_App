package com.arapeak.adkya.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arapeak.adkya.R;
import com.arapeak.adkya.listener.MyItemListener;
import com.arapeak.adkya.model.country.Country;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CountryAdapter  extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder>  {

    private MyItemListener myItemListener;
    private Context mContext;
    int index = -1;


    private List<Country> mListCountry  ;


    public CountryAdapter(Context context ) {
        this.mContext = context;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_country_layout, parent, false);
        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, final int position) {
        holder.textCounteryName.setText(mListCountry.get(position).getName());

        if (mListCountry.get(position).getCode()!=null){
            holder.txt_code.setText(mListCountry.get(position).getCode());
        }
        //todo still i need the image


        /******************************************************************/
//        //this is for change the item after slected
//        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                index = position;
//                notifyDataSetChanged();
//            }
//        });
//        if(index==position){
//            holder.view.setBackgroundColor(Color.parseColor("#FF4081"));
//            holder.textCounteryName.setTextColor(Color.parseColor("#FFFFFF"));
//            holder.txt_code.setTextColor(Color.parseColor("#FFFFFF"));
//        }else{
//            holder.view.setBackgroundColor(Color.parseColor("#FFFFFF"));
//            holder.textCounteryName.setTextColor(Color.parseColor("#000000"));
//            holder.txt_code.setTextColor(Color.parseColor("#000000"));
//        }
        /***************************************************************************/
    }

    public class CountryViewHolder   extends RecyclerView.ViewHolder {
        TextView textCounteryName;
        ImageView img_flag;
        TextView txt_code;
        LinearLayout linearLayout;
        View view;

        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);
            view=itemView;
            textCounteryName=itemView.findViewById(R.id.tv_name_country);
            img_flag=itemView.findViewById(R.id.img_flag);
            txt_code=itemView.findViewById(R.id.txt_code);
            linearLayout=itemView.findViewById(R.id.linearLayout);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myItemListener.onClickItem(getAdapterPosition());
                }
            });
        }
    }

    public List<Country> getmListCountry() {
        return mListCountry;
    }

    public void setmListCountry(List<Country> mListCountry) {
        if (mListCountry!=null){
            this.mListCountry = mListCountry;

        }else {
            this.mListCountry = new ArrayList<>();

        }
    }

    public void setMyItemListener(MyItemListener myItemListener) {
        this.myItemListener = myItemListener;
    }

    @Override
    public int getItemCount() {
        return mListCountry.size();
    }

}
