package com.godream;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MKEvent;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.mapapi.map.RouteOverlay;
import com.baidu.mapapi.map.TransitOverlay;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPlanNode;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKRoute;
import com.baidu.mapapi.search.MKRoutePlan;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRoutePlan;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.godream.adapter.DrivingRouteAdapter;
import com.godream.adapter.TransitRouteAdapter;
import com.godream.adapter.WalkingRouteAdapter;
import com.godream.bean.DistrictInfo;
import com.godream.service.Rotate3dAnimation;
import com.godream.util.BMapUtil;
import com.godream.util.UtilTools;

/**
*
* 类名称：RouteMapActivity
* 
* 类描述：线路查找,地图显示
* 
* 创建人：lipeng
* 
* 创建时间：2013-10-18 下午5:21:09
* 
* 备注：
*
*/
public class RouteMapActivity extends SuperActivity implements OnClickListener{
	/**地图相关*/
	private MapView mBMapView;
	private MapController mController;
	private LocationClient mLocationClient;
	private BDLocationListener myListener;
	private MKSearch mSearch = null;// 搜索模块，也可去掉地图模块独立使用
	
	/**图层相关*/
	private MyLocationOverlay myLocationOverlay;
	private MyOverlay mCustomerOverlay = null;
	private MyOverlay mSearchOverlay = null;
	private RouteOverlay routeOverlay = null;
	private TransitOverlay transitOverlay = null;//公交图层
	private PopupOverlay popUpOverlay  = null;

	/**PopupView相关*/
	private TextView  popupText = null;
	private View viewCache = null;
	private View popupInfo = null;
	private View popupLeft = null;
	private View popupRight = null;
	
	/**我的位置经纬度*/
	private double myLatitude;
	private double myLongitude;
	
	private ViewGroup mContainer;//总的container
	
	/**地图视图中的控件*/
	private RelativeLayout mRouteMapLayout;
	private AutoCompleteTextView mSearchLocation;
	private List<OverlayItem> customerOverlayList;//客户图层集合
	
	/**路径查询视图中的控件*/
	private RelativeLayout mRouteLayout;
	private EditText mStartPostion;
	private EditText mEndPostion;
	private Button mSearchRoute; 
	private ViewPager mRouteViewPager;
	private PagerTabStrip  mRouteViewPagerTab;
	private List<String> titleList;
	private List<View> routeViewList;
	
	private ListView mDrivingRouteList;//驾驶路线列表
	private ListView mTransitRouteList;//公交路线列表
	private ListView mWalkingRouteList;//步行路线列表
	
	/**标题栏控件*/
	private TextView mTopCenterTxt;
	private ImageView mTopRightImageView;
	
	private LayoutInflater inflater;
	private int currPagerIndex;
	private Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseView.setContentView(this,0,R.layout.base_view_titlecenter, 
				R.layout.route_topright,R.layout.routemap);
		setContentView(baseView.getContentView());
		
		context = getApplicationContext();
		inflater = LayoutInflater.from(context);
		
		initView();
		initViewPager();
		initMapView();
		initPopUpView();
	}
	
	private void initView(){
		mContainer = (ViewGroup) findViewById(R.id.container);
		
		//标题控件
		mTopCenterTxt = (TextView) findViewById(R.id.top_title); 
		mTopCenterTxt.setText(getString(R.string.route_map_title));
		mTopRightImageView = (ImageView) findViewById(R.id.route_map_topright);
		mTopRightImageView.setOnClickListener(this);
		
		//地图视图中的控件
		mSearchLocation = (AutoCompleteTextView) findViewById(R.id.autoSearch);
		mRouteMapLayout = (RelativeLayout) findViewById(R.id.route_map_layout);
		
		//路径查询视图中的控件
		mRouteLayout  = (RelativeLayout) findViewById(R.id.route_layout);
		mStartPostion = (EditText) findViewById(R.id.startpostion);
		mEndPostion = (EditText) findViewById(R.id.endpostion);
		mSearchRoute  = (Button) findViewById(R.id.searchRoute);
		mSearchRoute.setOnClickListener(this);
		
		mContainer.setPersistentDrawingCache(ViewGroup.PERSISTENT_ANIMATION_CACHE);
	}
	
	private void initViewPager(){
		mRouteViewPager = (ViewPager) findViewById(R.id.routeViewPagers);
		mRouteViewPagerTab = (PagerTabStrip) findViewById(R.id.routeViewPagerTab);
		
		mRouteViewPagerTab.setTabIndicatorColor(getResources().getColor(R.color.item_press));
		mRouteViewPagerTab.setDrawFullUnderline(true); 
		mRouteViewPagerTab.setBackgroundColor(getResources().getColor(R.color.white));
		mRouteViewPagerTab.setTextSpacing(50); 
		
		View drivingView = inflater.inflate(R.layout.route_list, null);//驾车
		View transitView = inflater.inflate(R.layout.route_list, null);//公交
		View walkingView = inflater.inflate(R.layout.route_list, null);//步行
		
		mDrivingRouteList = (ListView) drivingView.findViewById(R.id.routeList);
		mTransitRouteList = (ListView) transitView.findViewById(R.id.routeList);
		mWalkingRouteList = (ListView) walkingView.findViewById(R.id.routeList);
		
		routeViewList = new ArrayList<View>();
		routeViewList.add(drivingView);
		routeViewList.add(transitView);
		routeViewList.add(walkingView);
		
		titleList = new ArrayList<String>();
		titleList.add("驾车");
		titleList.add("公交");
		titleList.add("步行"); 
		
		mRouteViewPager.setAdapter(new RouteViewPagerAdapter());
		mRouteViewPager.setOnPageChangeListener(new RoutePageChangeListener());
	}
	
	private void initMapView(){
		MainApplication app = (MainApplication)this.getApplication();
		mBMapView = (MapView) findViewById(R.id.bmapView);
		mController = mBMapView.getController();
		mController.setZoom(14);
		mController.enableClick(true);
		mBMapView.setBuiltInZoomControls(true);
		
		mLocationClient = new LocationClient(context);
		setLocOption(mLocationClient);//定位相关设置        
		myListener = new MyLocationListener();//定位回调函数
		mLocationClient.registerLocationListener(myListener);
		mLocationClient.start();
		
		mSearch = new MKSearch();//路线
		mSearch.init(app.mBMapManager, new MyMKSearchListener());
	}
	
	private void setLocOption(LocationClient mLocationClient){
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);//是否打开gps
		option.setAddrType("all");//设置是否要返回地址信息，String 值为 all时，表示返回地址信息。其他值都表示不返回地址信息。
		option.setCoorType("bd09ll");//返回的定位结果是百度经纬度,默认值gcj02

		//当所设的整数值大于等于1000（ms）时，定位SDK内部使用定时定位模式。调用requestLocation( )后，每隔设定的时间，定位SDK就会进行一次定位。
		//当不设此项，或者所设的整数值小于1000（ms）时，采用一次定位模式。
		option.setScanSpan(1000);//设置发起定位请求的间隔时间为5000ms
		option.disableCache(false);//true表示禁用缓存定位，false表示启用缓存定位。
		option.setPoiNumber(5);//最多返回POI个数	
		option.setPoiDistance(1000);//poi查询距离		
		option.setPoiExtraInfo(true);//是否需要POI的电话和地址等详细信息		
		
		mLocationClient.setLocOption(option);
	}
	
	private void initPopUpView(){
		viewCache = getLayoutInflater().inflate(R.layout.custom_text_view, null);
        popupInfo = (View) viewCache.findViewById(R.id.popinfo);
        popupLeft = (View) viewCache.findViewById(R.id.popleft);
        popupRight = (View) viewCache.findViewById(R.id.popright);
        popupText =(TextView) viewCache.findViewById(R.id.textcache);
        
		PopupClickListener popListener = new PopupClickListener() {
			@Override
			public void onClickedPopup(int arg0) {
				
			}
		};
		popUpOverlay = new PopupOverlay(mBMapView, popListener);
	}
	
	/**
	 *描述：根据返回的数据集，加载到地图上每个点
	 *@param districts
	 *创建人：lipeng
	 *创建时间：2013-10-17 上午11:10:54
	 *备注：
	 */
	private void addCustomerOverlay(List<DistrictInfo> districts){
		if(mCustomerOverlay != null)
			mBMapView.getOverlays().remove(mCustomerOverlay);
		
		customerOverlayList = new ArrayList<OverlayItem>();
		
		for (DistrictInfo district : districts) {
			double mLat = Double.valueOf(district.getLng());
			double mLon = Double.valueOf(district.getLat());
			GeoPoint point = new GeoPoint((int) (mLat * 1E6),
										  (int) (mLon * 1E6));
			customerOverlayList.add(
					new OverlayItem(point,
									district.getName(),
									district.getAddress()));
		}
		Drawable market = getResources().getDrawable(R.drawable.marker_pin_orange);
		mCustomerOverlay = new MyOverlay(market,mBMapView);
		mCustomerOverlay.addItem(customerOverlayList);
		mBMapView.getOverlays().add(mCustomerOverlay);
		mBMapView.refresh();
		GeoPoint point = new GeoPoint((int) ( 22.54423* 1E6),(int) ( 114.05779 * 1E6));
		mController.animateTo(point);
	}
	
	/**
	 *描述：根据坐标值，显示在地图上
	 *@param gp
	 *@param name
	 *@param addr
	 *创建人：lipeng
	 *创建时间：2013-10-17 上午11:14:36
	 *备注：
	 */
	private void addGeoPointOverlay(GeoPoint gp,String name,String addr){
		addPopUpOverlay(gp,addr);
		if(mSearchOverlay != null){
			mBMapView.getOverlays().remove(mSearchOverlay);
		}
		Drawable market = getResources().getDrawable(R.drawable.marker_pin_orange);
		mSearchOverlay = new MyOverlay(market,mBMapView);
		mSearchOverlay.addItem(new OverlayItem(gp,name,addr));
		mBMapView.getOverlays().add(mSearchOverlay);
		mController.animateTo(gp);
		mBMapView.refresh();
	}
	
	/**
	 *描述：添加路径图层
	 *@param gp
	 *@param route
	 *创建人：lipeng
	 *创建时间：2013-10-17 下午1:22:17
	 *备注：
	 */
	private void addRouteOverlay(GeoPoint gp,MKRoute route){
		if(routeOverlay != null){
			mBMapView.getOverlays().remove(routeOverlay);
		}
		if(transitOverlay != null){
			mBMapView.getOverlays().remove(transitOverlay);
		}
		routeOverlay = new RouteOverlay(RouteMapActivity.this, mBMapView);
		routeOverlay.setData(route);
		mBMapView.getOverlays().add(routeOverlay);
		mBMapView.refresh();
		mBMapView.getController().zoomToSpan(routeOverlay.getLatSpanE6(), routeOverlay.getLonSpanE6());
		mBMapView.getController().animateTo(gp);
	}
	
	/**
	 *描述：添加公交路径图层
	 *@param gp
	 *@param route
	 *创建人：lipeng
	 *创建时间：2013-10-18 下午3:51:34
	 *备注：
	 */
	private void addTransitRouteOverlay(GeoPoint gp,MKTransitRoutePlan plan){
		if(routeOverlay != null){
			mBMapView.getOverlays().remove(routeOverlay);
		}
		if(transitOverlay != null){
			mBMapView.getOverlays().remove(transitOverlay);
		}
		transitOverlay = new TransitOverlay(RouteMapActivity.this, mBMapView);
		transitOverlay.setData(plan);
		mBMapView.getOverlays().add(transitOverlay);
		mBMapView.refresh();
		mBMapView.getController().zoomToSpan(transitOverlay.getLatSpanE6(), transitOverlay.getLonSpanE6());
		mBMapView.getController().animateTo(gp);
	}
	
	/**
	 *描述：根据传入的经纬度在地图上显示
	 *创建人：lipeng
	 *创建时间：2013-10-16 上午11:20:55
	 *备注：
	 */
	private void showMyLocationOnMap(double latitude,double longitude){
		if(myLocationOverlay != null){
			mBMapView.getOverlays().remove(myLocationOverlay);
		}
		myLocationOverlay = new MyLocationOverlay(mBMapView);
		LocationData locData = new LocationData();
		locData.latitude = latitude;
		locData.longitude = longitude;
		locData.direction = 2.0f;
		myLocationOverlay.setData(locData);
		myLocationOverlay.setMarker(getResources().getDrawable(R.drawable.marker_pin_blue));
		mBMapView.getOverlays().add(myLocationOverlay);
		mBMapView.refresh();
		mController.animateTo(new GeoPoint((int)(locData.latitude * 1e6), (int)(locData.longitude * 1e6)));
		mLocationClient.stop();
	}
	
	private void hiddenPopupview(){
		popUpOverlay.hidePop();
	}
	
	/**
	 *描述：添加popupview图层
	 *@param gp
	 *@param name
	 *创建人：lipeng
	 *创建时间：2013-10-17 下午1:23:45
	 *备注：
	 */
	private void addPopUpOverlay(GeoPoint gp,String name){
		popupText.setText(name);
	    Bitmap[] bitMaps={
		    BMapUtil.getBitmapFromView(popupLeft), 		
		    BMapUtil.getBitmapFromView(popupInfo), 		
		    BMapUtil.getBitmapFromView(popupRight) 		
	    };
	    popUpOverlay.showPopup(bitMaps,gp,32);
	}
	
	public void findLocation(View v){
		mLocationClient.start();
	}
	
	public void findCustomer(View v){
		new ShowOverlaysTask().execute();
	}
	
    /**
     *描述：点击搜索按钮，查找搜索的位置
     *@param v
     *创建人：lipeng
     *创建时间：2013-10-17 上午11:09:26
     *备注：
     */
    public void searchLocation(View v){
    	mSearch.geocode(mSearchLocation.getText().toString(),null);
    }
    
    /**
    *
    * 类名称：RouteViewPagerAdapter
    * 
    * 类描述：路径列表ViewPager
    * 
    * 创建人：lipeng
    * 
    * 创建时间：2013-10-18 下午4:19:15
    * 
    * 备注：
    *
    */
    public class RouteViewPagerAdapter extends PagerAdapter{
		@Override
		public int getCount() {
			return routeViewList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(routeViewList.get(position)); 
		}
		
		@Override
		public int getItemPosition(Object object) {
			return super.getItemPosition(object); 
		}
    	
		@Override
		public CharSequence getPageTitle(int position) {
			return titleList.get(position);
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			 container.addView(routeViewList.get(position)); 
			 
			return routeViewList.get(position);
		}
    }
    
    /**
    *
    * 类名称：RoutePageChangeListener
    * 
    * 类描述：RouteViewPager滑动监听
    * 
    * 创建人：lipeng
    * 
    * 创建时间：2013-10-18 下午4:20:46
    * 
    * 备注：
    *
    */
    public class RoutePageChangeListener implements OnPageChangeListener{

		@Override
		public void onPageScrollStateChanged(int arg0) {
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			
		}

		@Override
		public void onPageSelected(int position) {
			currPagerIndex = position;
		}
    }
    
	class MyOverlay extends ItemizedOverlay<OverlayItem> {
		private List<OverlayItem> geoList = new ArrayList<OverlayItem>();
		public MyOverlay(Drawable marker,MapView mapView) {
			super(marker,mapView);
		}
		
		@Override
		public void addItem(List<OverlayItem> geoList) {
			super.addItem(geoList);
			this.geoList.addAll(geoList);
		}

		@Override
		public void addItem(OverlayItem item) {
			super.addItem(item);
			geoList.add(item);
		}

		// 覆盖物点击事件处理
		@Override
		protected boolean onTap(int i) {
			GeoPoint startPoint = new GeoPoint((int)(myLatitude * 1e6), (int)(myLongitude * 1e6));
			MKPlanNode startNode = new MKPlanNode();
			startNode.name = "我的位置";
			startNode.pt = startPoint;
			
			OverlayItem item = geoList.get(i);
			GeoPoint endPoint = item.getPoint();
			MKPlanNode endNode = new MKPlanNode();
			endNode.name = item.getTitle();
			endNode.pt = endPoint;
			
			mSearch.drivingSearch(null, startNode, null, endNode);
			return true;
		}

	}
    public class MyLocationListener implements BDLocationListener{
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location==null) {
				Toast.makeText(getApplicationContext(), "获取位置信息失败", Toast.LENGTH_LONG).show();
				return;
			}
			
			if (location.getLocType()==BDLocation.TypeOffLineLocation ) {
				//离线定位请求返回的定位结果
			}
			myLatitude = location.getLatitude();
			myLongitude = location.getLongitude();
			Toast.makeText(getApplicationContext(), "定位成功："+location.getAddrStr(), Toast.LENGTH_LONG).show();
			showMyLocationOnMap(location.getLatitude(), location.getLongitude());
		}

		@Override
		public void onReceivePoi(BDLocation arg0) {
			
		}
	}
	class ShowOverlaysTask extends AsyncTask<Void, Void, List<DistrictInfo>> {
		@Override
		protected List<DistrictInfo> doInBackground(Void... arg0) {
			if(mBMapView==null) return null;
			try {
				//实际项目中我们一般都是请求一个api，然后从json或xml中解析出小区信息
				//这里演示从文件中读取
				return UtilTools.parserJson(UtilTools.readFile(context,"project.json"));
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(List<DistrictInfo> districts) {
			if(mBMapView==null) return;
			
			if(districts != null && districts.size() != 0){
				addCustomerOverlay(districts);
			}
		}
	}
    public class MyMKSearchListener implements MKSearchListener{
    	@Override
    	public void onGetAddrResult(MKAddrInfo res, int error) {
    		if (error == MKEvent.ERROR_ROUTE_ADDR){
    			return;
    		}
    		if (error != 0 || res == null) {
    			Toast.makeText(context, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
    			return;
    		}
    		addGeoPointOverlay(res.geoPt,res.strBusiness,res.strAddr);
    	}

    	@Override
    	public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) {
    		
    	}

    	@Override
    	public void onGetDrivingRouteResult(MKDrivingRouteResult res, int error) {
    		if (error == MKEvent.ERROR_ROUTE_ADDR){
    			Toast.makeText(context, "抱歉，出错了", Toast.LENGTH_SHORT).show();
    			return;
    		}
    		if (error != 0 || res == null) {
    			Toast.makeText(context, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
    			return;
    		}
    		if(isRouteSearch){
    			//线路查询模式
    			setDrivingRouteAdapter(res);
    		}else{
    			//地图模式
    			addRouteOverlay(res.getStart().pt,res.getPlan(0).getRoute(0));
    		}
    	}

    	@Override
    	public void onGetTransitRouteResult(MKTransitRouteResult res, int error) {
    		if (error == MKEvent.ERROR_ROUTE_ADDR){
    			Toast.makeText(context, "抱歉，出错了", Toast.LENGTH_SHORT).show();
    			return;
    		}
    		if (error != 0 || res == null) {
    			Toast.makeText(context, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
    			return;
    		}
    		if(isRouteSearch){
    			//线路查询模式
    			setTransitRouteAdapter(res);
    		}else{
    			//地图模式
    			addTransitRouteOverlay(res.getStart().pt,res.getPlan(0));
    		}
    	}

    	@Override
    	public void onGetWalkingRouteResult(MKWalkingRouteResult res, int error) {
    		if (error == MKEvent.ERROR_ROUTE_ADDR){
    			Toast.makeText(context, "抱歉，出错了", Toast.LENGTH_SHORT).show();
    			return;
    		}
    		if (error != 0 || res == null) {
    			Toast.makeText(context, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
    			return;
    		}
    		if(isRouteSearch){
    			//线路查询模式
    			setWalkingRouteAdapter(res);
    		}else{
    			//地图模式
    			addRouteOverlay(res.getStart().pt,res.getPlan(0).getRoute(0));
    		}
    	}
    	
    	public void onGetPoiDetailSearchResult(int arg0, int arg1) {}
    	public void onGetPoiResult(MKPoiResult arg0, int arg1, int arg2) {}
    	public void onGetShareUrlResult(MKShareUrlResult arg0, int arg1,int arg2) {}
    	public void onGetSuggestionResult(MKSuggestionResult arg0, int arg1) {}
    }
    
    private boolean isRouteSearch = true;
    
	@Override
	public void onClick(View v) {
		if(v.equals(mTopRightImageView)){
			changeView();
		}else if(v.equals(mSearchRoute)){
			//点击查询按钮
			String startPostion = mStartPostion.getText().toString().trim();
			String endPostion = mEndPostion.getText().toString().trim();
			if("".equals(startPostion) || "".equals(endPostion)){
				Toast.makeText(context, getString(R.string.missSearchMsg),Toast.LENGTH_SHORT).show();
				return;
			}
			
			MKPlanNode startNode = new MKPlanNode();
			startNode.name = startPostion;
			MKPlanNode endNode = new MKPlanNode();
			endNode.name = endPostion;

			switch (currPagerIndex) {
			case 0:
				mSearch.drivingSearch("苏州", startNode, "苏州", endNode);
				break;
			case 1:
				mSearch.transitSearch("苏州", startNode, endNode);
				break;
			case 2:
				mSearch.walkingSearch("苏州", startNode, "苏州", endNode);
				break;
			default:
				break;
			}
		}
	}
	
	/**
	 *描述：切换地图和列表模式
	 *创建人：lipeng
	 *创建时间：2013-10-18 下午5:54:48
	 *备注：
	 */
	private void changeView(){
		AlphaAnimation fadeOutAnimation = new AlphaAnimation(1, 0);
		fadeOutAnimation.setDuration(300);
		fadeOutAnimation.setStartOffset(0);
		
		AlphaAnimation fadeInAnimation = new AlphaAnimation(0, 1);
		fadeInAnimation.setDuration(300);;
		fadeInAnimation.setStartOffset(0);
		if(isRouteSearch){
//			//当前为搜索模式,切换为地图模式
//			applyRotation(isRouteSearch, 0, 30);
			isRouteSearch = false;
			mRouteLayout.startAnimation(fadeOutAnimation);
			mRouteLayout.setVisibility(View.GONE);
			mRouteMapLayout.setVisibility(View.VISIBLE);
			mRouteMapLayout.startAnimation(fadeInAnimation);
			
			mTopRightImageView.startAnimation(fadeOutAnimation);
			mTopRightImageView.setBackgroundResource(R.drawable.top_list);
			mTopRightImageView.startAnimation(fadeInAnimation);
		}else{
			mRouteMapLayout.startAnimation(fadeOutAnimation);
			mRouteMapLayout.setVisibility(View.GONE);
			mRouteLayout.setVisibility(View.VISIBLE);
			mRouteLayout.startAnimation(fadeInAnimation);
//			//当前为地图模式,切换为搜索模式
//			applyRotation(isRouteSearch, 0, -30);
			isRouteSearch = true;
			
			mTopRightImageView.startAnimation(fadeOutAnimation);
			mTopRightImageView.setBackgroundResource(R.drawable.top_map);
			mTopRightImageView.startAnimation(fadeInAnimation);
		}
	}
	
	/**
	 *描述：设置驾驶路线列表
	 *@param res
	 *创建人：lipeng
	 *创建时间：2013-10-18 上午11:17:35
	 *备注：
	 */
	private void setDrivingRouteAdapter(final MKDrivingRouteResult res){
		final DrivingRouteAdapter adapter = new DrivingRouteAdapter(context, res);
		mDrivingRouteList.setAdapter(adapter);
		mDrivingRouteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				MKRoutePlan result = (MKRoutePlan)adapter.getItem(position);
				addRouteOverlay(res.getStart().pt,result.getRoute(0));
				changeView();
			}
		});
	}
	
	/**
	 *描述：设置公交路线列表
	 *@param res
	 *创建人：lipeng
	 *创建时间：2013-10-18 下午3:17:19
	 *备注：
	 */
	private void setTransitRouteAdapter(final MKTransitRouteResult res){
		final TransitRouteAdapter adapter = new TransitRouteAdapter(context, res);
		mTransitRouteList.setAdapter(adapter);
		mTransitRouteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				MKTransitRoutePlan result = (MKTransitRoutePlan)adapter.getItem(position);
				addTransitRouteOverlay(res.getStart().pt,result);
				changeView();
			}
		});
	}
	
	/**
	 *描述：设置步行路线列表
	 *@param res
	 *创建人：lipeng
	 *创建时间：2013-10-18 下午5:55:10
	 *备注：
	 */
	private void setWalkingRouteAdapter(final MKWalkingRouteResult res){
		final WalkingRouteAdapter adapter = new WalkingRouteAdapter(context, res);
		mWalkingRouteList.setAdapter(adapter);
		mWalkingRouteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				MKRoutePlan result = (MKRoutePlan)adapter.getItem(position);
				addRouteOverlay(res.getStart().pt,result.getRoute(0));
				changeView();
			}
		});
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode ==  KeyEvent.KEYCODE_BACK){
			if(isRouteSearch){
				return super.onKeyDown(keyCode, event);
			}else{
				changeView();
				return true;
			}
				
		}
		return super.onKeyDown(keyCode, event);
	}
	
	/**---------------------------------------------------------------------------------------------------------------------------*/
	
	/**
	 *描述：Y轴旋转，由于180度后页面反了，没找到好的办法，暂时不用了
	 *@param isRouteSearch
	 *@param start
	 *@param end
	 *创建人：lipeng
	 *创建时间：2013-10-18 上午10:51:51
	 *备注：
	 */
	private void applyRotation(boolean isRouteSearch,float start, float end){
		 final float centerX = mContainer.getWidth() / 2.0f;
	     final float centerY = mContainer.getHeight() / 2.0f;
	     
	     final Rotate3dAnimation rotation = new Rotate3dAnimation(start, end, centerX, centerY, 310.0f, false);
	     rotation.setDuration(500);
	     rotation.setFillAfter(true);
	     rotation.setInterpolator(new AccelerateInterpolator());
	     rotation.setAnimationListener(new DisplayNextView(isRouteSearch));
	     mContainer.startAnimation(rotation);
	}
	
	private final class DisplayNextView implements Animation.AnimationListener{
		boolean isRouteSearch;
		public DisplayNextView(boolean isRouteSearch) {
			this.isRouteSearch = isRouteSearch;
		}
		@Override
		public void onAnimationEnd(Animation animation) {
			mContainer.post(new SwapViews(isRouteSearch));
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			
		}

		@Override
		public void onAnimationStart(Animation animation) {
			
		}
	}
	
	private final class SwapViews implements Runnable{
		boolean isRouteSearch;
		public SwapViews(boolean isRouteSearch) {
			this.isRouteSearch = isRouteSearch;
		}
		@Override
		public void run() {
			final float centerX = mContainer.getWidth() / 2.0f;
			final float centerY = mContainer.getHeight() / 2.0f;
			final Rotate3dAnimation rotation;
			if(isRouteSearch){
				mRouteMapLayout.setVisibility(View.VISIBLE);
				mRouteLayout.setVisibility(View.GONE);
				mRouteMapLayout.requestFocus();
				rotation = new Rotate3dAnimation(30, 0, centerX, centerY, 310.0f, false);	
			}else{
				mRouteMapLayout.setVisibility(View.GONE);
				mRouteLayout.setVisibility(View.VISIBLE);
				mRouteLayout.requestFocus();
				rotation = new Rotate3dAnimation(-30, 0, centerX, centerY, 310.0f, false);
			}
			rotation.setDuration(500);
		    rotation.setFillAfter(true);
		    rotation.setInterpolator(new DecelerateInterpolator());
		    mContainer.startAnimation(rotation);
		}
	}
	
  @Override
    protected void onPause() {
        mBMapView.onPause();
        super.onPause();
    }
    
    @Override
    protected void onResume() {
        mBMapView.onResume();
        super.onResume();
    }
    
    @Override
    protected void onDestroy() {
    	//退出时销毁定位
        if (mLocationClient != null)
        	mLocationClient.stop();
        mBMapView.destroy();
        super.onDestroy();
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	mBMapView.onSaveInstanceState(outState);
    	
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
    	super.onRestoreInstanceState(savedInstanceState);
    	mBMapView.onRestoreInstanceState(savedInstanceState);
    }
	
}

















