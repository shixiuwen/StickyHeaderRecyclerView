package shixia.stickyheaderrecyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import shixia.stickyheaderrecyclerview.bean.CarBrandInfo;
import shixia.stickyheaderrecyclerview.sticky.CarBrandItemDecoration;
import shixia.stickyheaderrecyclerview.sticky.CarBrandRecycleViewAdapter;
import shixia.stickyheaderrecyclerview.view.SlideBarView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView tvSearch;
    private RecyclerView rvCarBrand;

    private List<CarBrandInfo> mCarBrandList = new ArrayList<>();
    private SlideBarView sbvBar;

    private int scrollY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvSearch = (TextView) findViewById(R.id.tv_search);
        rvCarBrand = (RecyclerView) findViewById(R.id.rv_car_brand);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvCarBrand.setLayoutManager(linearLayoutManager);

        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                String id = jsonObject.getString("id");
                String brand_index = jsonObject.getString("brand_index");
                String brand_name = jsonObject.getString("brand_name");
                String brand_id = jsonObject.getString("brand_id");
                String brand_logo = jsonObject.getString("brand_logo");

                CarBrandInfo carBrandInfo = new CarBrandInfo();
                carBrandInfo.setBrand_id(id);
                carBrandInfo.setBrand_index(brand_index);
                carBrandInfo.setBrand_name(brand_name);
                carBrandInfo.setBrand_id(brand_id);
                carBrandInfo.setBrand_logo(brand_logo);

                mCarBrandList.add(carBrandInfo);
            }
            //在第0位置创建一个空对象用于放置最热车型
            CarBrandInfo carBrandInfo = new CarBrandInfo();
            carBrandInfo.setBrand_index("热门车型");
            carBrandInfo.setBrand_name("凯迪拉克A120");
            mCarBrandList.add(0, carBrandInfo);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        rvCarBrand.setAdapter(new CarBrandRecycleViewAdapter(this, mCarBrandList));
        rvCarBrand.addItemDecoration(new CarBrandItemDecoration(this, mCarBrandList));


        sbvBar = (SlideBarView) findViewById(R.id.sbv_bar);
        List<String> indexList = new ArrayList<>();
        for (int i = 'A'; i <= 'Z'; i++) {
            char a = (char) i;
            indexList.add(String.valueOf(a));
        }
        sbvBar.setIndexList(indexList);
        sbvBar.setOnIndexSelectedChangeListener(new SlideBarView.OnIndexSelectedChangeListener() {
            @Override
            public void onIndexSelectedChange(int indexSelected, String strIndex) {
                for (int i = 0; i < mCarBrandList.size(); i++) {
                    if (mCarBrandList.get(i).getBrand_index().equals(strIndex)) {
                        int firstItemHeight = 300;  //定义好的（根据屏幕适配更改）
                        int normalItemHeight = 120; //定义好的（根据屏幕适配更改）
                        int indexDividerHeightSum = (indexSelected + 1) * 66;   //标签高度66是定义好的（根据屏幕适配更改）
                        int normalDividerHeightSum = (i - indexSelected) * 2;
                        rvCarBrand.scrollBy(0, (-scrollY) + (i - 1) * normalItemHeight + firstItemHeight + indexDividerHeightSum + normalDividerHeightSum);
                        return;
                    }
                }
            }
        });

        rvCarBrand.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                scrollY += dy;
            }
        });
    }


    private String json = "[\n" +
            "    {\n" +
            "        \"id\":\"3\",\n" +
            "        \"brand_index\":\"A\",\n" +
            "        \"brand_name\":\"奥迪\",\n" +
            "        \"brand_id\":\"33\",\n" +
            "        \"brand_logo\":\".\\Logo\\33.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"190\",\n" +
            "        \"brand_index\":\"A\",\n" +
            "        \"brand_name\":\"阿尔法罗密欧\",\n" +
            "        \"brand_id\":\"34\",\n" +
            "        \"brand_logo\":\".\\Logo\\34.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"188\",\n" +
            "        \"brand_index\":\"A\",\n" +
            "        \"brand_name\":\"阿斯顿·马丁\",\n" +
            "        \"brand_id\":\"35\",\n" +
            "        \"brand_logo\":\".\\Logo\\35.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"194\",\n" +
            "        \"brand_index\":\"A\",\n" +
            "        \"brand_name\":\"AC Schnitz\",\n" +
            "        \"brand_id\":\"117\",\n" +
            "        \"brand_logo\":\".\\Logo\\117.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"189\",\n" +
            "        \"brand_index\":\"A\",\n" +
            "        \"brand_name\":\"安凯客车\",\n" +
            "        \"brand_id\":\"221\",\n" +
            "        \"brand_logo\":\".\\Logo\\221.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"193\",\n" +
            "        \"brand_index\":\"A\",\n" +
            "        \"brand_name\":\"Arash\",\n" +
            "        \"brand_id\":\"251\",\n" +
            "        \"brand_logo\":\".\\Logo\\251.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"192\",\n" +
            "        \"brand_index\":\"A\",\n" +
            "        \"brand_name\":\"ARCFOX\",\n" +
            "        \"brand_id\":\"272\",\n" +
            "        \"brand_logo\":\".\\Logo\\272.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"191\",\n" +
            "        \"brand_index\":\"A\",\n" +
            "        \"brand_name\":\"ALPINA\",\n" +
            "        \"brand_id\":\"276\",\n" +
            "        \"brand_logo\":\".\\Logo\\276.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"172\",\n" +
            "        \"brand_index\":\"B\",\n" +
            "        \"brand_name\":\"标致\",\n" +
            "        \"brand_id\":\"13\",\n" +
            "        \"brand_logo\":\".\\Logo\\13.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"175\",\n" +
            "        \"brand_index\":\"B\",\n" +
            "        \"brand_name\":\"本田\",\n" +
            "        \"brand_id\":\"14\",\n" +
            "        \"brand_logo\":\".\\Logo\\14.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"185\",\n" +
            "        \"brand_index\":\"B\",\n" +
            "        \"brand_name\":\"宝马\",\n" +
            "        \"brand_id\":\"15\",\n" +
            "        \"brand_logo\":\".\\Logo\\15.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"183\",\n" +
            "        \"brand_index\":\"B\",\n" +
            "        \"brand_name\":\"北京\",\n" +
            "        \"brand_id\":\"27\",\n" +
            "        \"brand_logo\":\".\\Logo\\27.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"177\",\n" +
            "        \"brand_index\":\"B\",\n" +
            "        \"brand_name\":\"奔驰\",\n" +
            "        \"brand_id\":\"36\",\n" +
            "        \"brand_logo\":\".\\Logo\\36.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"168\",\n" +
            "        \"brand_index\":\"B\",\n" +
            "        \"brand_name\":\"布加迪\",\n" +
            "        \"brand_id\":\"37\",\n" +
            "        \"brand_logo\":\".\\Logo\\37.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"171\",\n" +
            "        \"brand_index\":\"B\",\n" +
            "        \"brand_name\":\"别克\",\n" +
            "        \"brand_id\":\"38\",\n" +
            "        \"brand_logo\":\".\\Logo\\38.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"170\",\n" +
            "        \"brand_index\":\"B\",\n" +
            "        \"brand_name\":\"宾利\",\n" +
            "        \"brand_id\":\"39\",\n" +
            "        \"brand_logo\":\".\\Logo\\39.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"184\",\n" +
            "        \"brand_index\":\"B\",\n" +
            "        \"brand_name\":\"保时捷\",\n" +
            "        \"brand_id\":\"40\",\n" +
            "        \"brand_logo\":\".\\Logo\\40.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"173\",\n" +
            "        \"brand_index\":\"B\",\n" +
            "        \"brand_name\":\"比亚迪\",\n" +
            "        \"brand_id\":\"75\",\n" +
            "        \"brand_logo\":\".\\Logo\\75.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"176\",\n" +
            "        \"brand_index\":\"B\",\n" +
            "        \"brand_name\":\"奔腾\",\n" +
            "        \"brand_id\":\"95\",\n" +
            "        \"brand_logo\":\".\\Logo\\95.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"187\",\n" +
            "        \"brand_index\":\"B\",\n" +
            "        \"brand_name\":\"宝骏\",\n" +
            "        \"brand_id\":\"120\",\n" +
            "        \"brand_logo\":\".\\Logo\\120.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"1\",\n" +
            "        \"brand_index\":\"B\",\n" +
            "        \"brand_name\":\"巴博斯\",\n" +
            "        \"brand_id\":\"140\",\n" +
            "        \"brand_logo\":\".\\Logo\\140.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"180\",\n" +
            "        \"brand_index\":\"B\",\n" +
            "        \"brand_name\":\"北汽威旺\",\n" +
            "        \"brand_id\":\"143\",\n" +
            "        \"brand_logo\":\".\\Logo\\143.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"189\",\n" +
            "        \"brand_index\":\"C\",\n" +
            "        \"brand_name\":\"Cabin\",\n" +
            "        \"brand_id\":\"187\",\n" +
            "        \"brand_logo\":\".\\Logo\\140.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"190\",\n" +
            "        \"brand_index\":\"C\",\n" +
            "        \"brand_name\":\"城苏\",\n" +
            "        \"brand_id\":\"188\",\n" +
            "        \"brand_logo\":\".\\Logo\\140.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"192\",\n" +
            "        \"brand_index\":\"C\",\n" +
            "        \"brand_name\":\"车分享\",\n" +
            "        \"brand_id\":\"189\",\n" +
            "        \"brand_logo\":\".\\Logo\\140.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"192\",\n" +
            "        \"brand_index\":\"D\",\n" +
            "        \"brand_name\":\"车分享\",\n" +
            "        \"brand_id\":\"189\",\n" +
            "        \"brand_logo\":\".\\Logo\\140.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"192\",\n" +
            "        \"brand_index\":\"D\",\n" +
            "        \"brand_name\":\"车分享\",\n" +
            "        \"brand_id\":\"189\",\n" +
            "        \"brand_logo\":\".\\Logo\\140.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"192\",\n" +
            "        \"brand_index\":\"D\",\n" +
            "        \"brand_name\":\"车分享\",\n" +
            "        \"brand_id\":\"189\",\n" +
            "        \"brand_logo\":\".\\Logo\\140.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"192\",\n" +
            "        \"brand_index\":\"D\",\n" +
            "        \"brand_name\":\"车分享\",\n" +
            "        \"brand_id\":\"189\",\n" +
            "        \"brand_logo\":\".\\Logo\\140.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"192\",\n" +
            "        \"brand_index\":\"D\",\n" +
            "        \"brand_name\":\"车分享\",\n" +
            "        \"brand_id\":\"189\",\n" +
            "        \"brand_logo\":\".\\Logo\\140.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"192\",\n" +
            "        \"brand_index\":\"D\",\n" +
            "        \"brand_name\":\"车分享\",\n" +
            "        \"brand_id\":\"189\",\n" +
            "        \"brand_logo\":\".\\Logo\\140.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"192\",\n" +
            "        \"brand_index\":\"D\",\n" +
            "        \"brand_name\":\"车分享\",\n" +
            "        \"brand_id\":\"189\",\n" +
            "        \"brand_logo\":\".\\Logo\\140.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"192\",\n" +
            "        \"brand_index\":\"D\",\n" +
            "        \"brand_name\":\"车分享\",\n" +
            "        \"brand_id\":\"189\",\n" +
            "        \"brand_logo\":\".\\Logo\\140.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"192\",\n" +
            "        \"brand_index\":\"E\",\n" +
            "        \"brand_name\":\"车分享\",\n" +
            "        \"brand_id\":\"189\",\n" +
            "        \"brand_logo\":\".\\Logo\\140.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"192\",\n" +
            "        \"brand_index\":\"E\",\n" +
            "        \"brand_name\":\"车分享\",\n" +
            "        \"brand_id\":\"189\",\n" +
            "        \"brand_logo\":\".\\Logo\\140.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"192\",\n" +
            "        \"brand_index\":\"E\",\n" +
            "        \"brand_name\":\"车分享\",\n" +
            "        \"brand_id\":\"189\",\n" +
            "        \"brand_logo\":\".\\Logo\\140.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"192\",\n" +
            "        \"brand_index\":\"E\",\n" +
            "        \"brand_name\":\"车分享\",\n" +
            "        \"brand_id\":\"189\",\n" +
            "        \"brand_logo\":\".\\Logo\\140.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"192\",\n" +
            "        \"brand_index\":\"E\",\n" +
            "        \"brand_name\":\"车分享\",\n" +
            "        \"brand_id\":\"189\",\n" +
            "        \"brand_logo\":\".\\Logo\\140.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"192\",\n" +
            "        \"brand_index\":\"E\",\n" +
            "        \"brand_name\":\"车分享\",\n" +
            "        \"brand_id\":\"189\",\n" +
            "        \"brand_logo\":\".\\Logo\\140.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"192\",\n" +
            "        \"brand_index\":\"E\",\n" +
            "        \"brand_name\":\"车分享\",\n" +
            "        \"brand_id\":\"189\",\n" +
            "        \"brand_logo\":\".\\Logo\\140.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"192\",\n" +
            "        \"brand_index\":\"E\",\n" +
            "        \"brand_name\":\"车分享\",\n" +
            "        \"brand_id\":\"189\",\n" +
            "        \"brand_logo\":\".\\Logo\\140.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"192\",\n" +
            "        \"brand_index\":\"F\",\n" +
            "        \"brand_name\":\"车分享\",\n" +
            "        \"brand_id\":\"189\",\n" +
            "        \"brand_logo\":\".\\Logo\\140.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"192\",\n" +
            "        \"brand_index\":\"G\",\n" +
            "        \"brand_name\":\"车分享\",\n" +
            "        \"brand_id\":\"189\",\n" +
            "        \"brand_logo\":\".\\Logo\\140.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"192\",\n" +
            "        \"brand_index\":\"H\",\n" +
            "        \"brand_name\":\"车分享\",\n" +
            "        \"brand_id\":\"189\",\n" +
            "        \"brand_logo\":\".\\Logo\\140.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"192\",\n" +
            "        \"brand_index\":\"H\",\n" +
            "        \"brand_name\":\"车分享\",\n" +
            "        \"brand_id\":\"189\",\n" +
            "        \"brand_logo\":\".\\Logo\\140.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"192\",\n" +
            "        \"brand_index\":\"H\",\n" +
            "        \"brand_name\":\"车分享\",\n" +
            "        \"brand_id\":\"189\",\n" +
            "        \"brand_logo\":\".\\Logo\\140.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"192\",\n" +
            "        \"brand_index\":\"Z\",\n" +
            "        \"brand_name\":\"车分享\",\n" +
            "        \"brand_id\":\"189\",\n" +
            "        \"brand_logo\":\".\\Logo\\140.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"192\",\n" +
            "        \"brand_index\":\"Z\",\n" +
            "        \"brand_name\":\"车分享\",\n" +
            "        \"brand_id\":\"189\",\n" +
            "        \"brand_logo\":\".\\Logo\\140.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"192\",\n" +
            "        \"brand_index\":\"Z\",\n" +
            "        \"brand_name\":\"车分享\",\n" +
            "        \"brand_id\":\"189\",\n" +
            "        \"brand_logo\":\".\\Logo\\140.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"192\",\n" +
            "        \"brand_index\":\"Z\",\n" +
            "        \"brand_name\":\"车分享\",\n" +
            "        \"brand_id\":\"189\",\n" +
            "        \"brand_logo\":\".\\Logo\\140.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"192\",\n" +
            "        \"brand_index\":\"Z\",\n" +
            "        \"brand_name\":\"车分享\",\n" +
            "        \"brand_id\":\"189\",\n" +
            "        \"brand_logo\":\".\\Logo\\140.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"192\",\n" +
            "        \"brand_index\":\"Z\",\n" +
            "        \"brand_name\":\"车分享\",\n" +
            "        \"brand_id\":\"189\",\n" +
            "        \"brand_logo\":\".\\Logo\\140.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"192\",\n" +
            "        \"brand_index\":\"Z\",\n" +
            "        \"brand_name\":\"车分享\",\n" +
            "        \"brand_id\":\"189\",\n" +
            "        \"brand_logo\":\".\\Logo\\140.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"192\",\n" +
            "        \"brand_index\":\"Z\",\n" +
            "        \"brand_name\":\"车分享\",\n" +
            "        \"brand_id\":\"189\",\n" +
            "        \"brand_logo\":\".\\Logo\\140.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"192\",\n" +
            "        \"brand_index\":\"Z\",\n" +
            "        \"brand_name\":\"车分享\",\n" +
            "        \"brand_id\":\"189\",\n" +
            "        \"brand_logo\":\".\\Logo\\140.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"192\",\n" +
            "        \"brand_index\":\"Z\",\n" +
            "        \"brand_name\":\"车分享\",\n" +
            "        \"brand_id\":\"189\",\n" +
            "        \"brand_logo\":\".\\Logo\\140.jpg\"\n" +
            "    }\n" +
            "]";
}
