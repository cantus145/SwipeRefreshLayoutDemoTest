/**
 * Android 使用 SwipeRefreshLayout 製作下拉更新 (Pull to Refresh)
 * http://blog.tonycube.com/2014/09/android-swiperefreshlayout-pull-to.html
 */
package example.com.swiperefreshlayoutdemotest;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.os.Handler;

import android.widget.AbsListView.OnScrollListener;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;


public class SwipeActivity extends AppCompatActivity {
    String TAG = "SwipeActivity";
    String TAG_Swipe = "Swipe";

    private Context context;
    private SwipeRefreshLayout laySwipe;

    ArrayAdapter<String> stringAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_swipe);

        initView();
    }

    private void initView() {
        laySwipe = (SwipeRefreshLayout) findViewById(R.id.laySwipe);
        laySwipe.setOnRefreshListener(onSwipeToRefresh);
        laySwipe.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.tertiary_text_dark
        );

        ListView lstData = (ListView) findViewById(R.id.lstData);

        stringAdapter = getAdapter();
        lstData.setAdapter(stringAdapter);
        lstData.setOnScrollListener(onListScroll);
    }

    private OnRefreshListener onSwipeToRefresh = new OnRefreshListener() {
        @Override
        public void onRefresh() {
            laySwipe.setRefreshing(true);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    laySwipe.setRefreshing(false);
                    generateRandom();
                    stringAdapter.notifyDataSetChanged();
                }
            }, 2500);
        }
    };

    private String[] data = new String[20];

    private void generateRandom() {
        int len = data.length;
        for (int i = 0; i < len; i++) {
            data[i] = Double.toString(Math.random() * 1000);
        }
    }

    private ArrayAdapter<String> getAdapter() {
        generateRandom();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1 , data);
        return adapter;
    }

    private OnScrollListener onListScroll = new OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {

            if (firstVisibleItem == 0) {
                Log.d(TAG, "firstVisibleItem == 0");
                Log.d(TAG_Swipe, "firstVisibleItem == 0");

                laySwipe.setEnabled(true);
            }else{
                laySwipe.setEnabled(false);
            }
        }
    };

}
