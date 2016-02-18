package allen.com.rsstest.view;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import allen.com.rsstest.R;
import allen.com.rsstest.adapter.FileDeatailAdapter;
import allen.com.rsstest.model.HtmlSenderCallback;
import allen.com.rsstest.pojo.FileDetailPojo;
import allen.com.rsstest.util.HtmlParseUtil;

public class FileDetailActivity extends AppCompatActivity implements HtmlSenderCallback{

    private RecyclerView recyclerView;
    private FileDeatailAdapter mAdapter;
    private int sourceId;
    private ArrayList<FileDetailPojo> mlist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_detail);

        Intent intent = getIntent();
        String[] magnetFile = intent.getStringArrayExtra(ResultFragment.MAGNET_FILE);
        String filename = magnetFile[0];
        String fileurl = magnetFile[1];
        sourceId = Integer.parseInt(magnetFile[2]);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);

        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(filename);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.detail_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new FileDeatailAdapter();

        recyclerView.setAdapter(mAdapter);

        HtmlParseUtil htmlParseUtil = new HtmlParseUtil();
        htmlParseUtil.excuteConnect(this,fileurl);

    }

    @Override
    public void onSuccess(String html) {
        ArrayList<FileDetailPojo> list = new ArrayList<>();
        switch (sourceId){
            case 0:
                list = HtmlParseUtil.getFileFromSource1(html);
                break;
            case 1:
                list = HtmlParseUtil.getFileFromSource2(html);
                break;
            case 2:
                list = HtmlParseUtil.getFileFromSource3(html);
                break;
            case 3:
                list = HtmlParseUtil.getFileFromSource4(html);
                break;
        }
        Log.d("listSize",list.size()+"");
        if (list.size() > 0){
            mlist.addAll(list);
            mAdapter.setData(mlist);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public void OnFailed() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(),"网络连接失败",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
