package allen.com.rsstest.view;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.Explode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import allen.com.rsstest.R;

/**
 * Created by Allen on 2016/3/6.
 */
public class SearchIndexFragment extends Fragment implements View.OnClickListener{
    private static SearchIndexFragment fragment;

    private EditText text;
    private ImageButton image;

    public static SearchIndexFragment getNewInstance(){
        if (fragment == null) {
            fragment = new SearchIndexFragment();
        }
        Log.d("Fragment","new");
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sarch_index,container,false);
        text = (EditText) view.findViewById(R.id.search_text);
        image = (ImageButton) view.findViewById(R.id.search_button);
        setAnimation();
        image.setOnClickListener(this);
        Log.d("Fragment","onCreateView");
        //监听回车键
        text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                    onClick(null);
                }
                return false;
            }
        });
        return view;
    }

    private void setAnimation(){
        int[] attrs = new int[]{R.attr.selectableItemBackground};
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs);
        int backgroundResource = typedArray.getResourceId(0, 0);
        image.setBackgroundResource(backgroundResource);
        typedArray.recycle();
    }

    @Override
    public void onClick(View v) {
        if (text.getText().toString().trim().equals("")){
            Toast.makeText(getContext(),"请输入关键词",Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(getActivity(),SearchResultTabActivity.class);
        intent.putExtra("key",text.getText().toString());

        Log.d("intent",text.getText().toString());
        startActivity(intent);
    }
}
