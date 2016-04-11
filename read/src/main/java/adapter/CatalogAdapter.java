package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.read.R;

import java.util.List;

import bean.BookDetailList;

/**
 * Created by Administrator on 2016/4/11.
 */
public class CatalogAdapter extends BaseAdapter {

    private List<BookDetailList> list;

    private Context context;

    public CatalogAdapter(List<BookDetailList> list, Context context){
        this.list = list;
        this.context = context;
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
        CatalogViewHolder vh;
        if(convertView == null){
            vh = new CatalogViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.catalogitem,null);
            vh.tv_count = (TextView) convertView.findViewById(R.id.count);
            vh.tv_title = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(vh);
        }else{
           vh = (CatalogViewHolder) convertView.getTag();
        }
        vh.tv_count.setText("第"  + position + "章");
        vh.tv_title.setText(list.get(position).getTitle_cha());

        return convertView;
    }


}

class CatalogViewHolder{
    TextView tv_count,tv_title;

}
