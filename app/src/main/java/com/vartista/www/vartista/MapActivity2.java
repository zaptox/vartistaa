package com.vartista.www.vartista;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapquest.mapping.MapQuest;
import com.mapquest.mapping.maps.MapView;

import java.util.ArrayList;
import java.util.List;

public class MapActivity2 extends AppCompatActivity {


    private ArrayList<Marker> markers;


    private MapView mMapView;
    private MapboxMap mMapboxMap;
    ArrayList<GetServiceProviders> sp_list;
    public static ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map2);

        sp_list= new ArrayList<GetServiceProviders>();
        markers=new ArrayList<Marker>();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);



        MapQuest.start(getApplicationContext());



        mMapView = (MapView) findViewById(R.id.mapquestMapView);




        mMapView.onCreate(savedInstanceState);

//        mMapView.getMapAsync(new OnMapReadyCallback() {
//            @Override
//            public void onMapReady(MapboxMap mapboxMap) {
//
//                mMapboxMap = mapboxMap;
//                mMapView.setStreetMode();
//
//               List<Marker> mlist=new ArrayList<Marker>();
//
//                mMapView.setStreetMode();
//
//                for (int i=0 ; i< sp_list.size();i++) {
//
//                    markers.add(mMapboxMap.addMarker(new MarkerOptions().
//                            position(new LatLng(sp_list.get(i).getLatitude(), sp_list.get(i).getLongitude()))
//                            .title(sp_list.get(i).getName()).snippet("OK")));
//
//
//                    //mlist.add(zaptox1);
//                }
//
//                for (int j=0 ; j< sp_list.size();j++) {
//
//
//
//
//                    mlist.add(markers.get(j));
//                }
//
//
//                mMapboxMap.setOnMarkerClickListener(new MapboxMap.OnMarkerClickListener() {
//                    @Override
//                    public boolean onMarkerClick(@NonNull Marker marker) {
//
//                        Toast.makeText(getApplicationContext(), marker.getTitle()+" is click", Toast.LENGTH_SHORT).show();
//
//                        return true;
//                    }
//                });
//                for(Marker marker: mlist){
//
//                    LatLng lt= new LatLng(marker.getPosition().getLatitude(),marker.getPosition().getLongitude());
//                    mMapboxMap.addMarker(new MarkerOptions().position(lt));
//                    mMapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lt, 42));
//                    mMapboxMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lt, 2));
//
//                }
//
//
//
//            }
//        });


    }
}
