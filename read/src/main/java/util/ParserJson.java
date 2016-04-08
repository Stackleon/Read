package util;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import bean.Books;

/**
 * Created by Administrator on 2016/4/8.
 */
public class ParserJson {

    public static List<Books> parserJson(String Json,String keyWord){
        List<Books> list = new ArrayList<>();
        try {
            JSONObject jo = new JSONObject(Json);
            JSONArray ja = jo.getJSONArray(keyWord);
            for(int i = 0 ; i < ja.length() ; i ++){
                Books books = new Books();
                JSONObject item = ja.getJSONObject(i);
                books.setAuthor(item.getString("author"));
                books.setChapterCount(item.getInt("chapterCount"));
                books.setCover(item.getString("cover"));
                books.setId(item.getInt("id"));
                books.setTitle(item.getString("title"));
                list.add(books);
            }

        return list;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
