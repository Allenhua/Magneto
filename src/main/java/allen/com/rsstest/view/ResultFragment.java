package allen.com.rsstest.view;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import allen.com.rsstest.R;
import allen.com.rsstest.adapter.CustomAdapter;
import allen.com.rsstest.model.HtmlSenderCallback;
import allen.com.rsstest.model.RecyclerItemClickListner;
import allen.com.rsstest.pojo.MagnetFilePojo;
import allen.com.rsstest.util.HtmlParseUtil;


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
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int VERTICAL_ITEM_SPACE = 32;

    public static final String MAGNET_FILE = "magnet";

    private RecyclerView recyclerView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private int mParam2;

    private CustomAdapter mAdapter;
    private List<MagnetFilePojo> itemList = new ArrayList<>();
    private OnFragmentInteractionListener mListener;

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

    void initTestList(){
        for (int i = 0; i <15 ; i++) {
            itemList.add(new MagnetFilePojo(i+"",i+1+"",i+2+"",i+3+""));
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_result,container,false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        final LinearLayoutManager lmanager = new LinearLayoutManager(getActivity());



        recyclerView.setLayoutManager(lmanager);
        //initTestList();//测试
        mAdapter = new CustomAdapter();
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnclickListner(this);

        final HtmlParseUtil htmlParseUtil = new HtmlParseUtil(mParam1,mParam2);
        htmlParseUtil.setPage(page);
        htmlParseUtil.excuteConnect(this,htmlParseUtil.getUrl());


        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));


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
                    htmlParseUtil.setPage(page);
                    htmlParseUtil.excuteConnect(ResultFragment.this,htmlParseUtil.getUrl());
                }
            }
        });


        return rootView;
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onSuccess(String html) {
        ArrayList<MagnetFilePojo> list = new ArrayList<>();
        Log.d("mParam2",mParam2+"");
        switch (mParam2){
            case 0:
                list = HtmlParseUtil.parseSource1(html);
                break;
            case 1:
                list = HtmlParseUtil.parseSource2(html);
                break;
            case 2:
                list = HtmlParseUtil.parseSource3(html);
                break;
            case 3:
                list = HtmlParseUtil.parseSource4(html);
                break;
        }
        Log.d("itemList",list.size()+"");
        if (getActivity() == null) {
            return;
        }
        if (list.size() == 0) {

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getContext(),"无更多数据",Toast.LENGTH_SHORT).show();
                }
            });

            return;
        }
        itemList.addAll(list);
        mAdapter.setData(itemList);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();

            }
        });
    }

    @Override
    public void OnFailed() {
        if (getActivity() == null) {
            return;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(),"网络连接失败",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClickItem(View view, int position) {
        if (getActivity() == null) {
            return;
        }
        if (view instanceof ImageButton){
            String magnetUrl = itemList.get(position).getFileMagnet();
            ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("magnetUrl",magnetUrl);
            clipboardManager.setPrimaryClip(clipData);

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getContext(),"磁力链已复制到剪贴板",Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }

        Log.d("ArrayList position",position+"");
        final MagnetFilePojo magnet = itemList.get(position);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getContext(),FileDetailActivity.class);
                intent.putExtra(MAGNET_FILE,new String[]
                        {magnet.getFileName(),magnet.getFileUrl(),mParam2+""});
                startActivity(intent);
            }
        });


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

