package allen.com.rsstest.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import allen.com.rsstest.R;
import allen.com.rsstest.model.RecyclerItemClickListner;
import allen.com.rsstest.pojo.MagnetFilePojo;

/**
 * Created by Allen on 2016/2/15.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ListItemViewHolder> {

    List<MagnetFilePojo> mList = new ArrayList<>();
    private RecyclerItemClickListner mClicks;


    public void setData(List<MagnetFilePojo> list) {
        mList = list;
    }

    @Override
    public CustomAdapter.ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item,parent,false);
        return new CustomAdapter.ListItemViewHolder(view,mClicks);
    }

    @Override
    public void onBindViewHolder(CustomAdapter.ListItemViewHolder holder, int position) {
        if (mList.size() > 0){
            MagnetFilePojo listItem = mList.get(position);
            holder.fileTitle.setText(listItem.getFileName());
            holder.fileInfo.setText(listItem.getFileSize());
        }
    }


    public void setOnclickListner(RecyclerItemClickListner listner){
        mClicks = listner;
    }
    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ListItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView fileTitle;
        TextView fileInfo;
        ImageButton down;
        RecyclerItemClickListner mClick;
        public ListItemViewHolder(View itemView, RecyclerItemClickListner itemClick) {
            super(itemView);
            fileTitle = (TextView) itemView.findViewById(R.id.text_t);
            fileInfo = (TextView) itemView.findViewById(R.id.text_info);
            down = (ImageButton) itemView.findViewById(R.id.button_down);
            mClick = itemClick;

            down.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

                mClick.onClickItem(v,getAdapterPosition());
        }
    }

}
