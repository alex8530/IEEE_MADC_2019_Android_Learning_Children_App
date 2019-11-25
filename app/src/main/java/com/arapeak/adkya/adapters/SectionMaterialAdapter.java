package com.arapeak.adkya.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arapeak.adkya.R;
import com.arapeak.adkya.listener.MyItemListener_nested;
import com.arapeak.adkya.model.getSecondMaterial.Section;
import com.arapeak.adkya.utils.Common;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SectionMaterialAdapter extends RecyclerView.Adapter<SectionMaterialAdapter.SectionViewHolder> {


    public ArrayList<Section> getmList() {
        return mSectionsList;
    }




    //    boolean isCompleteQuestion=false;

    public void setmList(ArrayList<Section> mList) {
        if (mList!=null){
            this.mSectionsList = mList;

        }else {
            this.mSectionsList = new ArrayList<>();

        }
        this.mSectionsList = mList;
    }

    ArrayList<Section> mSectionsList= new ArrayList<>();


    private MyItemListener_nested myItemListenerNested;

    public MyItemListener_nested getMyItemListenerNested() {
        return myItemListenerNested;
    }

    public void setMyItemListenerNested(MyItemListener_nested myItemListenerNested) {
        this.myItemListenerNested = myItemListenerNested;
    }

    private Context mContext;

    public SectionMaterialAdapter(Context mContext) {
        this.mContext = mContext;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public SectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizantal_section_item_rec_layout, parent, false);
        return new SectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SectionViewHolder holder, int position) {


         holder.tv_description.setText(mSectionsList.get(position).getName());

        if (mSectionsList.get(position).getIs_complete()){
            //show check icon
         holder.img_check.setVisibility(View.VISIBLE);

        }else {
        //  hide icon check
            holder.img_check.setVisibility(View.GONE);

        }


    }

    @Override
    public int getItemCount() {
      return(null != mSectionsList ? mSectionsList.size() : 0);
    }

    public class SectionViewHolder  extends RecyclerView.ViewHolder{
        TextView tv_description;
        ImageView img_check;
        ImageView img_item;
        LinearLayout constraintLayout;

        public SectionViewHolder(@NonNull View itemView) {
            super(itemView);
            img_check=itemView.findViewById(R.id.imageView19);
            img_item=itemView.findViewById(R.id.imageView18);
            tv_description=itemView.findViewById(R.id.textView29);
            constraintLayout=itemView.findViewById(R.id.container_constrain);
            constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (Common.IS_VISITOR){
                        //not allwoed to inter and see the data
                         myItemListenerNested.onClickItemNested(null,true);

                    }else {
                        //if the section is complete>>
                        if (mSectionsList.get(getAdapterPosition()).getIs_complete()){
                            Toast.makeText(mContext, "لقد تم حل جميع المسائل في هذا المساق", Toast.LENGTH_SHORT).show();
                        }else {
                            //SAVE ADAPTER POSITION TO KEEP TRACK
                            Common.CURRENT_ADAPTER_SECTION_POSITION=getAdapterPosition();
                            Common.CURRENT_ADAPTER_SECTION_PARENT_POSITION=mSectionsList.get(getAdapterPosition()).getParent_position();
                            myItemListenerNested.onClickItemNested(mSectionsList.get(getAdapterPosition()),false);
                        }
                    }

                }
            });


        }
    }
}
