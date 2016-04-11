package util;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import bean.BookDetail;
import bean.BookDetailList;
import bean.Books;
import bean.GVItem;

/**
 * Created by Administrator on 2016/4/8.
 */
public class ParserJson {

    public static List<Books> parserJson(String Json, String keyWord) {
        List<Books> list = new ArrayList<>();
        try {
            JSONObject jo = new JSONObject(Json);
            JSONArray ja = jo.getJSONArray(keyWord);
            for (int i = 0; i < ja.length(); i++) {
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

    public static BookDetail parserJson(String Json) {
        try {
            BookDetail bd = new BookDetail();
            List<BookDetailList> list = new ArrayList<>();
            JSONObject jo = new JSONObject(Json);
            JSONObject jo_no = jo.getJSONObject("novel");
            bd.setTitle(jo_no.getString("title"));
            bd.setAuthor(jo_no.getString("author"));
            bd.setCover(jo_no.getString("cover"));
            bd.setProcess(jo_no.getString("process"));
            bd.setId(jo_no.getInt("id"));
            bd.setTheme(jo_no.getString("theme"));
            bd.setDepict(jo_no.getString("depict"));
            bd.setWords(jo_no.getInt("words"));
            bd.setChapterCount(jo_no.getInt("chapterCount"));
            JSONArray ja = jo.getJSONArray("chapterList");
            for (int i = 0; i < ja.length(); i++) {
                BookDetailList bsl = new BookDetailList();
                bsl.setId_cha(ja.getJSONObject(i).getInt("id"));
                bsl.setTitle_cha(ja.getJSONObject(i).getString("title"));
                list.add(bsl);
            }
            bd.setBooklist(list);
            return bd;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static List<GVItem> parserGVJson(String Json){
        try {
            List<GVItem> list = new ArrayList<>();
            JSONObject jo = new JSONObject(Json);
            JSONArray ja = jo.getJSONArray("novelTheme");
            for(int i = 0 ; i < ja.length() ;i ++){
                GVItem gv = new GVItem();
                gv.setId(ja.getJSONObject(i).getInt("id"));
                gv.setName(ja.getJSONObject(i).getString("name"));
                list.add(gv);
            }return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }

    public static List<Books> parserClassifyJson(String Json) {
        List<Books> list = new ArrayList<>();
        try {
            JSONObject jo = new JSONObject(Json);
            JSONArray ja = jo.getJSONObject("novelPage").getJSONArray("result");
            for (int i = 0; i < ja.length(); i++) {
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
