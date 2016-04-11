package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.read.R;

import java.util.List;

import bean.GVItem;

/**
 * Created by Administrator on 2016/4/11.
 */
public class GridViewAdapter extends BaseAdapter {

    private Context context ;
    private List<GVItem> list;

    public GridViewAdapter(Context context , List<GVItem> list){
        this.context = context;
        this.list = list;

    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GridViewHolder vh;
        if(convertView == null){
            vh = new GridViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.gridviewitem,null);
            vh.tv = (TextView) convertView.findViewById(R.id.textview);
            convertView.setTag(vh);
        }else{
            vh = (GridViewHolder) convertView.getTag();
        }
        vh.tv.setText(list.get(position).getName());


        return convertView;
    }
}
class GridViewHolder{

    TextView tv;

}