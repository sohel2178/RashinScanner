package com.linearbd.rashinscanner.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.linearbd.rashinscanner.Listener.ItemClickListener;
import com.linearbd.rashinscanner.R;


/**
 * Created by Genius 03 on 8/26/2017.
 */

public class EncodeItemAdapter extends RecyclerView.Adapter<EncodeItemAdapter.EncodeItemHolder> {

    private int[] iconArray = new int[]{

            R.drawable.ic_card,
            R.drawable.ic_mail,
            R.drawable.ic_message,
            R.drawable.ic_phone

    };

    private String[] nameArray = new String[]{

            "CARD","E-MAIL","MESSAGE","PHONE"

    };

    private Context context;
    private LayoutInflater inflater;

    private ItemClickListener listener;

    public EncodeItemAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public void setItemClickListener(ItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public EncodeItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.single_encoder_item,parent,false);

        return new EncodeItemHolder(view);
    }

    @Override
    public void onBindViewHolder(EncodeItemHolder holder, int position) {

        holder.ivIcon.setImageResource(iconArray[position]);
        holder.tvName.setText(nameArray[position]);

    }

    @Override
    public int getItemCount() {
        return iconArray.length;
    }

    public class EncodeItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView ivIcon;
        TextView tvName;

        public EncodeItemHolder(View itemView) {
            super(itemView);

            ivIcon = itemView.findViewById(R.id.iv_icon);
            tvName = itemView.findViewById(R.id.tv_name);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if(listener!=null){
                listener.onItemClick(getAdapterPosition());
            }

        }
    }


}
