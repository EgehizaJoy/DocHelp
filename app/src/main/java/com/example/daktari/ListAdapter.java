package com.example.daktari;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    ImageView iv;
    private Context context;
    private ArrayList<DataModel> dataModelArrayList;

    public ListAdapter(Context context, ArrayList<DataModel> dataModelArrayList) {

        this.context = context;
        this.dataModelArrayList = dataModelArrayList;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return dataModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.lv_player, null, true);

            holder.iv = (ImageView) convertView.findViewById(R.id.iv);
            holder.tvcaption = (TextView) convertView.findViewById(R.id.captions);
            holder.tvusername=(TextView)convertView.findViewById(R.id.homeusernames);
            iv=(ImageView)convertView.findViewById(R.id.iv);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }
        Picasso.with(context).
                load(dataModelArrayList.get(position).getImgURL()).into(iv);
        holder.tvcaption.setText(dataModelArrayList.get(position).getCaption());
        holder.tvusername.setText(dataModelArrayList.get(position).getusername());
        Picasso.with(context).setLoggingEnabled(true);

       // Picasso.with(get())
               // .load(galleryItem.getUrl())
              //  .placeholder(R.drawable.placeholder)
              //  .into(mImageView);

        return convertView;
    }

    private class ViewHolder {

        protected TextView tvcaption;
        protected TextView tvusername;
        protected ImageView iv;
    }
// Picasso.with(this).load(mimageuri).into(minageview);

}
