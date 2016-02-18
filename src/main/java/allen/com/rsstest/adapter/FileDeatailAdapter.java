package allen.com.rsstest.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import allen.com.rsstest.R;
import allen.com.rsstest.pojo.FileDetailPojo;

/**
 * Created by Allen on 2016/2/17.
 */
public class FileDeatailAdapter extends RecyclerView.Adapter<FileDeatailAdapter.DetailViewHolder> {

    List<FileDetailPojo> mlist = new ArrayList<>();

    public void setData(List<FileDetailPojo> list){
        mlist = list;
    }

    @Override
    public DetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_item,parent,false);
        return new DetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DetailViewHolder holder, int position) {
        if (mlist.size() > 0){
            holder.fileSize.setText(mlist.get(position).getFileSize());
            holder.fileTitle.setText(mlist.get(position).getFileTile());
        }
    }


    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class DetailViewHolder extends RecyclerView.ViewHolder{

        TextView fileTitle;
        TextView fileSize;

        public DetailViewHolder(View itemView) {
            super(itemView);
            fileTitle = (TextView) itemView.findViewById(R.id.file_title);
            fileSize = (TextView) itemView.findViewById(R.id.file_size);
        }
    }
}
