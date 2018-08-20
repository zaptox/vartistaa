package com.vartista.www.vartista;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.MarkerView;
import com.mapbox.mapboxsdk.annotations.MarkerViewManager;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapquest.mapping.MapQuest;
import com.mapquest.mapping.maps.MapView;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, MarkerViewManager.OnMarkerViewAddedListener {


    private final LatLng MAPQUEST_HEADQUARTERS_LOCATION = null;
    private final LatLng MAPQUEST_HEADQUARTERS_LOCATION2 = new LatLng(35.369554, 68.359048);
    private final LatLng MAPQUEST_HEADQUARTERS_LOCATION3 = new LatLng(17.385044, 78.486671);
    private final LatLng MAPQUEST_HEADQUARTERS_LOCATION4 = new LatLng(55.369554, 68.359048);
    private final LatLng CAMERA_LOC = new LatLng(39.745391, -105.00653);
    private Marker zaptox1;
    private ArrayList<Marker> markers;
    int user_id;
    private Marker zaptox2;
    private Marker zaptox3;
    private Marker zaptox4;
    private MapView mMapView;
    private MapboxMap mMapboxMap;
    public static ApiInterface apiInterface;
    ArrayList<GetServiceProviders> sp_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp_list= new ArrayList<>();
        markers=new ArrayList<>();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Intent i = getIntent();
        sp_list=i.getParcelableArrayListExtra("service_providers");
        user_id=i.getIntExtra("user_id",0);

   //   Toast.makeText(this, "MAP ACTIVTY"+sp_list.get(0).getLatitude(), Toast.LENGTH_SHORT).show();








        setContentView(R.layout.activity_map);
        MapQuest.start(getApplicationContext());

        setContentView(R.layout.activity_map);

        mMapView = (MapView) findViewById(R.id.mapquestMapView);


        //Intent  i = getIntent();
      //  sp_list=i.getParcelableArrayListExtra("service_providers");

        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {

                mMapboxMap = mapboxMap;
                mMapView.setStreetMode();

               List<Marker> mlist=new ArrayList<Marker>();

                mMapView.setStreetMode();

                for (int i=0 ; i< sp_list.size();i++) {

                    markers.add(mMapboxMap.addMarker(new MarkerOptions().
                            position(new LatLng(sp_list.get(i).getLatitude(), sp_list.get(i).getLongitude()))
                            .title(sp_list.get(i).getUser_id()+"").snippet(sp_list.get(i).getCategory_id()+"")));


                    //mlist.add(zaptox1);
                }

                for (int j=0 ; j< sp_list.size();j++) {




                    mlist.add(markers.get(j));
                }


                mMapboxMap.setOnMarkerClickListener(new MapboxMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(@NonNull Marker marker) {
                        Toast.makeText(MapActivity.this, ""+marker.getTitle()+"is click", Toast.LENGTH_SHORT).show();
                        int s_provider_id= Integer.parseInt(marker.getTitle());
                        int cat_id= Integer.parseInt(marker.getSnippet());
                       // Toast.makeText(MapActivity.this, ""+cat_id , Toast.LENGTH_SHORT).show();

                        Intent intent=new Intent(MapActivity.this,ServiceProviderDetail.class);
                        intent.putExtra("s_provider_id",s_provider_id);
                        intent.putExtra("cat_id",cat_id);
                        intent.putExtra("user_id",user_id);

                         startActivity(intent);
                        return true;
                    }
                });

                for(Marker marker: mlist){

                    LatLng lt= new LatLng(marker.getPosition().getLatitude(),marker.getPosition().getLongitude());
                    mMapboxMap.addMarker(new MarkerOptions().position(lt));
                    mMapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lt, 42));
                    mMapboxMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lt, 12));


                }



            }
        });
    }
    @Override
    public void onResume()
    { super.onResume(); mMapView.onResume(); }

    @Override
    public void onPause()
    { super.onPause(); mMapView.onPause(); }

    @Override
    protected void onDestroy()
    { super.onDestroy(); mMapView.onDestroy(); }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    { super.onSaveInstanceState(outState); mMapView.onSaveInstanceState(outState); }
  /*  private void setPolyline(MapboxMap mapboxMap) {
        List<LatLng> coordinates = new ArrayList<>();
        coordinates.add(new LatLng(39.74335,-105.01234));
        coordinates.add(new LatLng(39.74667,-105.01135));
        coordinates.add(new LatLng(39.7468,-105.00709));
        coordinates.add(new LatLng(39.74391,-105.00794));
        coordinates.add(new LatLng(39.7425,-105.0047));
        coordinates.add(new LatLng(39.74634,-105.00478));
        coordinates.add(new LatLng(39.74734,-104.99984));

        PolylineOptions polyline = new PolylineOptions();
        polyline.addAll(coordinates);
        polyline.width(3);
        polyline.color(Color.BLUE);
        mapboxMap.addPolyline(polyline);
    }
*/


    @Override
    public void onViewAdded(@NonNull MarkerView markerView) {
        String title= markerView.getTitle();
       // Toast.makeText(this, "OKK"+title, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMapReady(MapboxMap mapboxMap) {
       // Toast.makeText(this, "OKK", Toast.LENGTH_SHORT).show();
    }





}
