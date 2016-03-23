package allen.com.rsstest.view;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import allen.com.rsstest.R;
import allen.com.rsstest.adapter.CustomAdapter;
import allen.com.rsstest.model.HtmlSenderCallback;
import allen.com.rsstest.model.RecyclerItemClickListner;
import allen.com.rsstest.pojo.MagnetFilePojo;
import allen.com.rsstest.util.OkhttpUtil;
import allen.com.rsstest.util.html.HtmlParseFactory;
import allen.com.rsstest.util.html.HtmlParser;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ResultFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultFragment extends Fragment implements HtmlSenderCallback,RecyclerItemClickListner{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";//keywords
    private static final String ARG_PARAM2 = "param2";//position

    public static final String MAGNET_FILE = "magnet";

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private ProgressBar mProgress;
    private PopupWindow popupWindow;
    private TextView popButtonCopy;
    private TextView popButtonLike;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private int mParam2;
    BasicActivity activity;


    private CustomAdapter mAdapter;
    private List<MagnetFilePojo> itemList = new ArrayList<>();
    private OnFragmentInteractionListener mListener;

    private HtmlParser htmlParser;
    private int page = 1;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment ResultFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResultFragment newInstance(String param1,int param2) {
        ResultFragment fragment = new ResultFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getInt(ARG_PARAM2);
        }
    }

    private void setSwipeRefresh(){
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light
                ,android.R.color.holo_green_light,android.R.color.holo_red_light,android.R.color.holo_orange_light);
        swipeRefreshLayout.setProgressViewOffset(false, 0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
    }

    private void initView(View rootView){
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_layout);
        setSwipeRefresh();

        mProgress = (ProgressBar) rootView.findViewById(R.id.mprogress_bar);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        final LinearLayoutManager lmanager = new LinearLayoutManager(activity);


        recyclerView.setLayoutManager(lmanager);

        mAdapter = new CustomAdapter();
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnclickListner(this);

        htmlParser = HtmlParseFactory.getHtmlParser(mParam2);
        activity.basicOkhttp.excuteConnect(this,htmlParser.getUrl(mParam1,page),activity);


        recyclerView.setItemAnimator(new DefaultItemAnimator());

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                activity.basicOkhttp.excuteConnect(ResultFragment.this,htmlParser.getUrl(mParam1,1),activity);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItem;
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = lmanager.findLastCompletelyVisibleItemPosition();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && (lastVisibleItem +1 == mAdapter.getItemCount())){
                    page++;
                    Log.d("Page",page+"");
                    activity.basicOkhttp.excuteConnect(ResultFragment.this,htmlParser.getUrl(mParam1,page),activity);
                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_result,container,false);
        initView(rootView);
        setProgress();
        return rootView;
    }


    private void setProgress(){
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }


    // TODO: Rename method, update argument and hook method into UI event

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        if (context instanceof BasicActivity){
            activity = (BasicActivity)context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        activity.cancelThis(activity);
    }

    @Override
    public void onSuccess(String html) {
        ArrayList<MagnetFilePojo> list;
        Log.d("mParam2",mParam2+"");

        list = htmlParser.parseSource(html);
        Log.d("itemList",list.size()+"");
        if (activity == null) {
            return;
        }
        if (list.size() == 0 ) {

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getContext(),"无更多数据",Toast.LENGTH_SHORT).show();
                }
            });

            if (itemList.size() == 0){
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProgress.setVisibility(View.GONE);
                    }
                });
            }
            return;
        }

        if (swipeRefreshLayout.isRefreshing()){
            setSwipeRefresh(list);
            return;
        }
        itemList.addAll(list);
        mAdapter.setData(itemList);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
                mProgress.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setSwipeRefresh(ArrayList list){
        itemList.clear();
        itemList.addAll(list);
        mAdapter.setData(itemList);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                mAdapter.notifyDataSetChanged();
                mProgress.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void OnFailed() {
        if (activity == null) {
            return;
        }
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                mProgress.setVisibility(View.GONE);
                Toast.makeText(activity,"网络连接失败",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClickItem(View view, int position) {
        if (activity == null) {
            return;
        }
        final MagnetFilePojo magnet = itemList.get(position);
        if (view instanceof ImageButton){

            initPopupWindow(magnet);
            showPopupWindow(recyclerView,0,0,0);
            return;
        }

        Log.d("ArrayList position",position+"");

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getContext(),FileDetailActivity.class);
                intent.putExtra(MAGNET_FILE,new String[]
                        {magnet.getFileName(),magnet.getFileUrl(),mParam2+""});
                startActivity(intent);
            }
        });

    }

    private void initPopupWindow(final MagnetFilePojo magnet){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popView = inflater.inflate(R.layout.pupup_layout,null);
        popButtonCopy = (TextView) popView.findViewById(R.id.copy_magnet);
        popButtonLike = (TextView) popView.findViewById(R.id.like_magnet);

        popButtonCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("magnetUrl",magnet.getFileMagnet());
                clipboardManager.setPrimaryClip(clipData);
                ResultFragment.this.activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        popupWindow.dismiss();
                        Toast.makeText(activity,"磁力链已复制到剪贴板", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        popButtonLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        popupWindow =  new PopupWindow(popView,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.alpha = 0.6f;
        activity.getWindow().setAttributes(params);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = activity.getWindow().getAttributes();
                params.alpha = 1.0f;//不透明
                activity.getWindow().setAttributes(params);
            }
        });
    }

    private void showPopupWindow(View parent,int x,int y,int pos){
        popupWindow.showAtLocation(parent, Gravity.BOTTOM,0,0);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.update();
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

