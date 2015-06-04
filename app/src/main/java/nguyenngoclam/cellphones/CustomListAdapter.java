package nguyenngoclam.cellphones;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by GIO on 6/3/2015.
 */
public class CustomListAdapter extends BaseAdapter {
    private ArrayList listData;
    private LayoutInflater layoutInflater;

    public CustomListAdapter(Context context, ArrayList listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_row_layout, null);
            holder = new ViewHolder();
            holder.nameView = (TextView) convertView.findViewById(R.id.title);
            holder.priceView = (TextView) convertView.findViewById(R.id.price);
            holder.imageView = (ImageView) convertView.findViewById(R.id.thumbImage);
            holder.avaView = (TextView) convertView.findViewById(R.id.tinhtrang);
            holder.color1View = (ImageView) convertView.findViewById(R.id.Color1);
            holder.color2View = (ImageView) convertView.findViewById(R.id.Color2);
            holder.color3View = (ImageView) convertView.findViewById(R.id.Color3);
            holder.color4View = (ImageView) convertView.findViewById(R.id.Color4);
            holder.color5View = (ImageView) convertView.findViewById(R.id.Color5);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ListItem newsItem = (ListItem) listData.get(position);
        holder.nameView.setText(newsItem.getName());
        holder.priceView.setText(newsItem.getPrice());
        holder.avaView.setText(newsItem.getAva());
        if (newsItem.getColor().contains("Black")) {
            new ImageDownloaderTask(holder.color5View).execute("http://cellphones.com.vn/media//colorselectorplus/swatches/146_cat.jpg");
        }
        if (newsItem.getColor().contains("Red")) {
            new ImageDownloaderTask(holder.color1View).execute("http://cellphones.com.vn/media//colorselectorplus/swatches/159_cat.jpg");
        }
        if (newsItem.getColor().contains("White")) {
            new ImageDownloaderTask(holder.color2View).execute("http://cellphones.com.vn/media//colorselectorplus/swatches/145_cat.jpg");
        }
        if (newsItem.getColor().contains("Gray")) {
            new ImageDownloaderTask(holder.color3View).execute("http://cellphones.com.vn/media//colorselectorplus/swatches/163_cat.jpg");
        }
        if (newsItem.getColor().contains("Blue")) {
            new ImageDownloaderTask(holder.color4View).execute("http://cellphones.com.vn/media//colorselectorplus/swatches/179_cat.jpg");
        }
        if (holder.imageView != null) {
            new ImageDownloaderTask(holder.imageView).execute(newsItem.getUrl());
        }
        return convertView;
    }

    static class ViewHolder {
        TextView nameView;
        TextView priceView;
        ImageView imageView;
        TextView avaView;
        ImageView color1View;
        ImageView color2View;
        ImageView color3View;
        ImageView color4View;
        ImageView color5View;

    }
}