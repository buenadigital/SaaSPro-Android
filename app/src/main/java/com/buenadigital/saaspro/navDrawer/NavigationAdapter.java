package com.buenadigital.saaspro.navDrawer;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.buenadigital.saaspro.Constants;
import com.buenadigital.saaspro.R;

import java.util.Collections;
import java.util.List;


public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.NavigationViewHolder> {

    private LayoutInflater mInflater;
    private Context mContext;
    private DrawerLayout mNavigationDrawer;
    private ClickListener mClickListener;
    List<NavigationItem> navigationData = Collections.emptyList();

    public NavigationAdapter (Context context, List<NavigationItem> navData){
        mInflater = LayoutInflater.from(context);
        this.navigationData = navData;
        mContext = context;
    }

    @Override
    public NavigationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.navigation_drawer_row, parent, false); //TODO: Check input parameters
        NavigationViewHolder holder = new NavigationViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(NavigationViewHolder viewHolder, int position) {
        NavigationItem currentItem = navigationData.get(position);
        viewHolder.title.setTypeface(Constants.TYPE_FACE_FONT_ROBOTO_MEDIUM);
        viewHolder.title.setText(currentItem.title);
        viewHolder.icon.setImageResource(currentItem.iconId);
        viewHolder.title.setTextColor(currentItem.txtColor);
    }

    @Override
    public int getItemCount() {
        return 1;
    }


    public void setClickListener(ClickListener clickListener){
        this.mClickListener = clickListener;
    }

    class NavigationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;
        ImageView icon;

        public NavigationViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.navTitle);
            icon = (ImageView) itemView.findViewById(R.id.navIcon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mClickListener!=null){
                mClickListener.itemClicked(v, getAdapterPosition());
            }
        }
    }
    public interface ClickListener{
        void itemClicked(View view, int position);
    }
}
