package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.administrator.read.R;

import java.util.List;

import bean.Books;
import util.BitmapCache;

/**
 * Created by Administrator on 2016/4/8.
 */
public class MainBookListAdapter extends BaseAdapter {

    private Context context;
    private List<Books> list;
    private RequestQueue mQueue;
    private ImageLoader mLoader;


    public MainBookListAdapter(Context context,List<Books> list){
        this.context = context;
        this.list = list;
        mQueue = Volley.newRequestQueue(context);
        mLoader = new ImageLoader(mQueue,BitmapCache.getInstance());
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
        ViewHolder vh;
        if(convertView == null){
            vh = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.mainlistviewitem,null);
            vh.iv = (ImageView) convertView.findViewById(R.id.imageview);
            vh.tv_author = (TextView) convertView.findViewById(R.id.item_author);
            vh.tv_title = (TextView) convertView.findViewById(R.id.title);
            vh.tv_chapter = (TextView) convertView.findViewById(R.id.item_chapter);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        vh.tv_author.setText(list.get(position).getAuthor());
        vh.tv_title.setText(list.get(position).getTitle());
        vh.tv_chapter.setText("更新至" + list.get(position).getChapterCount() + "章");
       if( vh.iv.getDrawable() != null){
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(vh.iv,
                0, R.drawable.default_big_icon);
        mLoader.get(list.get(position).getCover(), listener);}

        return convertView;
    }

}
class ViewHolder{
    ImageView iv;
    TextView tv_title,tv_author,tv_chapter;
}