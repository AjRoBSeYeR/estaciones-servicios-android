package android.training.demoapp.pojo.mapas;

import android.content.Context;
import android.graphics.Bitmap;
import android.training.demoapp.adapters.mapsAdapters.MapsInfoWindowAdapter;
import android.training.demoapp.R;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

public class MarkerClusterRenderer extends DefaultClusterRenderer<MapCluster>
        implements
        ClusterManager.OnClusterClickListener<MapCluster>,
        ClusterManager.OnClusterItemClickListener<MapCluster>,
        GoogleMap.OnInfoWindowClickListener
{

    private static final double MINIMUM_NUMBER_OF_MARKERS_IN_CLUSTER = 6;
    private static final double MAX_CLUSTERING_ZOOM_LEVEL = 10;
    private static final int MARKER_DIMENSION = 100;
    private final IconGenerator iconGenerator;
    private final ImageView markerImageView;
    private GoogleMap map;
    private ClusterManager<MapCluster> clusterManager;
    private Context context;
    public MarkerClusterRenderer(Context context, GoogleMap map, ClusterManager<MapCluster> clusterManager) {

        super(context, map, clusterManager);
        this.context = context;
        this.map = map;
        this.iconGenerator = new IconGenerator(context);
        this.markerImageView = new ImageView(context);
        this.markerImageView.setLayoutParams(new ViewGroup.LayoutParams(MARKER_DIMENSION, MARKER_DIMENSION));
        this.clusterManager = clusterManager;

        this.iconGenerator.setContentView(markerImageView);
        this.clusterManager.setOnClusterClickListener(this);
        this.clusterManager.setOnClusterItemClickListener(this);
        this.map.setOnInfoWindowClickListener(this);
        this.clusterManager.getMarkerCollection().setOnInfoWindowAdapter(new MapsInfoWindowAdapter(LayoutInflater.from(context)));
        this.map.setOnCameraIdleListener(clusterManager);
        this.map.setOnMarkerClickListener(clusterManager);

    }

    @Override
    protected void onBeforeClusterItemRendered(MapCluster item, MarkerOptions markerOptions) { // 5
        markerImageView.setImageResource(R.drawable.icon);
        Bitmap icon = iconGenerator.makeIcon();
       markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
       markerOptions.title(item.getTitle());
    }

    @Override
    protected void onClusterItemRendered(MapCluster clusterItem, Marker marker) {
        marker.setTag(clusterItem);
    }
//    @Override
//    protected boolean shouldRenderAsCluster(Cluster cluster) {
//        Float zoom = null;
//        try {
//            this.getClass().getSuperclass().getSuperclass().getDeclaredField("mZoom").setAccessible(true);
//            Field field = this.getClass().getSuperclass().getSuperclass().getDeclaredField("mZoom");
//            field.setAccessible(true);
//            zoom = (Float) field.get(this);
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//
//            return cluster.getSize() >= MINIMUM_NUMBER_OF_MARKERS_IN_CLUSTER;
//        }
//        return cluster.getSize() >= MINIMUM_NUMBER_OF_MARKERS_IN_CLUSTER && (zoom != null ? zoom < MAX_CLUSTERING_ZOOM_LEVEL : true);
//    }


    @Override
    protected boolean shouldRenderAsCluster(Cluster cluster) {
        return cluster.getSize() > MINIMUM_NUMBER_OF_MARKERS_IN_CLUSTER; // if markers <=* then not clustering

    }


//    @Override
//    protected int getColor(int clusterSize) {
//        return Color.parseColor("#034AA6");
//    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(context, "onInfoWindowClick", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onClusterClick(Cluster<MapCluster> cluster) {
        Toast.makeText(context, "Abriendo grupo", Toast.LENGTH_SHORT).show();
        float zoom =   this.map.getCameraPosition().zoom +1;
        this.map.animateCamera(CameraUpdateFactory.newLatLngZoom(cluster.getPosition(), zoom), 300, null);
        return true;
    }

    @Override
    public boolean onClusterItemClick(MapCluster mapCluster) {
        Toast.makeText(context, "onClusterItemClick", Toast.LENGTH_SHORT).show();
        return false;
    }
}