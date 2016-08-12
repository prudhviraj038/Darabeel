package in.yellowsoft.darabeel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by sriven on 5/3/2016.
 */
public class GridElementAdapter extends RecyclerView.Adapter<GridElementAdapter.SimpleViewHolder>{

    private Context context;
    ArrayList<Restaurants> artists;
    HomeFragment artistListActivity;

    public GridElementAdapter(Context context, ArrayList<Restaurants> artists, HomeFragment artistListActivity){
        this.context = context;
        this.artists = artists;
        this.artistListActivity = artistListActivity;
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        ImageView img;
        LinearLayout artist_select_view,rating;

        public SimpleViewHolder(View view) {
            super(view);

            tv=(TextView) view.findViewById(R.id.artist_name);
            img=(ImageView) view.findViewById(R.id.artist_image);
            artist_select_view = (LinearLayout) view.findViewById(R.id.artist_select_view);
            rating = (LinearLayout) view.findViewById(R.id.rating_home);

        }
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(this.context).inflate(R.layout.home_page_restaurant_item, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, final int position) {
        holder.tv.setText(artists.get(position).getTitle(context));
        Settings.set_rating(context,artists.get(position).rating,holder.rating);
        Picasso.with(context).load(artists.get(position).image).into(holder.img);
        holder.artist_select_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(context, "Position =" + position, Toast.LENGTH_SHORT).show();
                artistListActivity.res_selected(position);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return this.artists.size();
    }
}
