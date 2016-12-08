package com.mcjang.mynespresso.db.mysql;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mcjang.mynespresso.R;
import com.mcjang.mynespresso.app.capsule.vo.CapsuleDetail;
import com.mcjang.mynespresso.app.capsule.vo.CapsuleList;
import com.mcjang.mynespresso.app.capsule.vo.Capsules;
import com.mcjang.mynespresso.app.capsule.vo.Review;
import com.mcjang.mynespresso.app.login.UserModel;
import com.mcjang.mynespresso.util.HttpClient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mcjan on 2016-11-28.
 */

public class AsyncJobs {

    private String URL = "http://mcjang.ipdisk.co.kr:8000/apps/mynespresso/index.php";

    private Context context;

    public AsyncJobs(Context context) {
        this.context = context;
    }

    public void getReview(String url, final CallbackReturn callback) {
        new AsyncTask<String, Void, List<Review>>() {

            @Override
            protected List<Review> doInBackground(String... params) {

                List<Review> reviews = new ArrayList<Review>();
                String url = params[0];
                try {
                    Document doc = Jsoup.connect(url).get();
                    Elements articles = doc.select("#bo_vc").select("article");

                    Review review = null;

                    String name = "";
                    String point = "0.0";
                    String comment = "";
                    for (int i = articles.size()-1; i >= 0; i--) {
                        name = articles.get(i).select(".member").text();
                        comment = articles.get(i).select("p").html().trim();
                        point = comment.split(":;:;:")[0].replace("점수-", "");
                        comment = comment.split(":;:;:")[1].replace("내용-", "").replace("<br/>", "\n").replace("<br>", "\n");
                        review = new Review();
                        review.setName(name);
                        review.setPoint(Float.parseFloat(point));
                        review.setComment(comment);
                        review.setDate(articles.get(i).select("time").text());
                        reviews.add(review);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                return reviews;
            }

            @Override
            protected void onPostExecute(List<Review> reviews) {
                callback.callback(context, reviews);
            }
        }.execute(url);
    }

    public void loginGnuBoard(final String id, final String password, final CallbackReturn callback) {

        CookieSyncManager.createInstance(context);
        final CookieManager cookieManager = CookieManager.getInstance();

        final String url = "http://mcjang.ipdisk.co.kr:8000/apps/gnuboard5/bbs/login_check.php";

        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                HttpClient.Builder client = new HttpClient.Builder("POST", params[0]);
                client.addOrReplaceParameter("url", "http://mcjang.ipdisk.co.kr:8000/apps/gnuboard5");
                client.addOrReplaceParameter("mb_id", id);
                client.addOrReplaceParameter("mb_password", password);

                HttpClient post = client.create();
                post.request();
                return post.getBody();
            }

            @Override
            protected void onPostExecute(String s) {
                cookieManager.setCookie("http://mcjang.ipdisk.co.kr:8000", HttpClient.SESSION_ID);
                CookieSyncManager.getInstance().sync();
                callback.callback(context, s);
            }
        }.execute(url);
    }

    public void writeComment(final String id, final String comment, final CallbackReturn callback) {
        final String url = "http://mcjang.ipdisk.co.kr:8000/apps/gnuboard5/bbs/write_comment_update.php";

        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                HttpClient.Builder client = new HttpClient.Builder("POST", params[0]);
                client.addOrReplaceParameter("w", "c");
                client.addOrReplaceParameter("bo_table", "coffee");
                client.addOrReplaceParameter("wr_id", id);
                client.addOrReplaceParameter("comment_id", "");
                client.addOrReplaceParameter("sca", "");
                client.addOrReplaceParameter("sfl", "");
                client.addOrReplaceParameter("stx", "");
                client.addOrReplaceParameter("spt", "");
                client.addOrReplaceParameter("page", "");
                client.addOrReplaceParameter("is_good", "");
                client.addOrReplaceParameter("wr_content", comment);

                HttpClient post = client.create();
                post.request();
                return post.getBody();
            }

            @Override
            protected void onPostExecute(String s) {
                callback.callback(context, s);
            }
        }.execute(url);
    }

    public void signUp(final UserModel user, final Callback callback) {

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {
                HttpClient.Builder builder = new HttpClient.Builder("POST", params[0]);
                Map<String, String> result = new HashMap<String, String>();
                result.put("mode", "signUp");
                result.put("email", user.getUserEmail());
                result.put("name", user.getUserName());
                result.put("password", user.getUserPassword());
                builder.addAllParameters(result );

                HttpClient post = builder.create();
                post.request();
                return post.getBody();
            }

            @Override
            protected void onPostExecute(String s) {
                if(s.equals("success")) {
                    callback.callback(context);
                }
                else {
                    Toast.makeText(context, "회원가입이 실패했습니다. 잠시후 시도해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute(URL);

    }

    public void signIn(final UserModel user, final Callback callback) {

        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                HttpClient.Builder builder = new HttpClient.Builder("POST", params[0]);
                builder.addOrReplaceParameter("mode", "signIn");
                builder.addOrReplaceParameter("email", user.getUserEmail());
                builder.addOrReplaceParameter("password", user.getUserPassword());

                HttpClient post = builder.create();
                post.request();
                return post.getBody();
            }

            @Override
            protected void onPostExecute(String s) {
                if(s.equals("1")) {
                    callback.callback(context);
                }
                else {
                    Toast.makeText(context, "아이디 혹은 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute(URL);

    }

    public void getItemInfo(final CallbackReturn callback) {
        new AsyncTask<String, Void, Capsules>() {
            @Override
            protected Capsules doInBackground(String... params) {
                HttpClient.Builder builder = new HttpClient.Builder("POST", params[0]);
                builder.addOrReplaceParameter("mode", "getCapsules");

                HttpClient post = builder.create();
                post.request();

                Gson gson = new Gson();
                Capsules capsules = gson.fromJson(post.getBody(), Capsules.class);

                getItemDetailInfo(capsules);
                fileDownloadBigFile(capsules);

                return capsules;
            }

            @Override
            protected void onPostExecute(Capsules capsules) {
                fileDownload(capsules);
                callback.callback(context, capsules);
            }
        }.execute(URL);
    }

    private void getItemDetailInfo(Capsules capsules) {

        HttpClient.Builder builder = new HttpClient.Builder("POST", URL);
        builder.addOrReplaceParameter("mode", "details");

        HttpClient post = builder.create();
        post.request();

        Gson gson = new Gson();
        List<CapsuleDetail> capsuleDetail = Arrays.asList(gson.fromJson(post.getBody(), CapsuleDetail[].class));
        Log.d("NESPRESSO", capsuleDetail.size() + "");

        CapsuleList capsuleRange = null;
        for (int i = 0; i < capsules.getCapsuleRange().size(); i++) {
            capsuleRange = capsules.getCapsuleRange().get(i);
            for (int j = 0; j < capsuleRange.getCapsuleList().size(); j++) {
                for (int k = 0; k < capsuleDetail.size(); k++ ) {
                    if ( capsuleRange.getCapsuleList().get(j).getCode().equals(capsuleDetail.get(k).getCode() )) {
                        capsuleRange.getCapsuleList().get(j).setCapsuleDetail(capsuleDetail.get(k));
                    }
                }
            }
        }
    }

    public void fileDownloadBigFile (Capsules capsules) {

        String ext = Environment.getExternalStorageState();
        final File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), context.getString(R.string.localPath));
        if (ext.equals(Environment.MEDIA_MOUNTED)) {
            if(!f.exists()) {
                boolean result = f.mkdirs();
            }
        }

        CapsuleList capsuleRange = null;
        for (int i = 0; i < capsules.getCapsuleRange().size(); i++) {
            capsuleRange = capsules.getCapsuleRange().get(i);
            for (int j = 0; j < capsuleRange.getCapsuleList().size(); j++) {

                File file = new File(f.getAbsolutePath() + File.separator + capsuleRange.getCapsuleList().get(j).getCapsuleDetail().getFileName());
                if (!file.exists()) {
                    URL imgurl;
                    int Read;
                    try {
                        imgurl = new URL(capsuleRange.getCapsuleList().get(j).getCapsuleDetail().getBigImage());
                        HttpURLConnection conn = (HttpURLConnection) imgurl.openConnection();
                        int len = conn.getContentLength();
                        byte[] tmpByte = new byte[len];
                        InputStream is = conn.getInputStream();

                        FileOutputStream fos = new FileOutputStream(file);
                        for (; ; ) {
                            Read = is.read(tmpByte);
                            if (Read <= 0) {
                                break;
                            }
                            fos.write(tmpByte, 0, Read);
                        }

                        is.close();
                        fos.close();
                        conn.disconnect();
                    } catch (MalformedURLException e) {
                        Log.e("NESPRESSO", e.getMessage());
                    } catch (IOException e) {
                        Log.e("NESPRESSO", e.getMessage());
                    }
                }
            }
        }

    }

    public void fileDownload (Capsules capsules) {

        String ext = Environment.getExternalStorageState();
        final File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), context.getString(R.string.localPath));
        if (ext.equals(Environment.MEDIA_MOUNTED)) {
            if(!f.exists()) {
                boolean result = f.mkdirs();
            }
        }

        CapsuleList capsuleRange = null;
        for (int i = 0; i < capsules.getCapsuleRange().size(); i++) {
            capsuleRange = capsules.getCapsuleRange().get(i);
            for (int j = 0; j < capsuleRange.getCapsuleList().size(); j++) {

                new AsyncTask<String, Void, Void>() {
                    @Override
                    protected Void doInBackground(String... params) {

                        File file = new File(f.getAbsolutePath() + File.separator + params[1]);
                        if (!file.exists()) {
                            URL imgurl;
                            int Read;
                            try {
                                imgurl = new URL(params[0]);
                                HttpURLConnection conn = (HttpURLConnection) imgurl.openConnection();
                                int len = conn.getContentLength();
                                byte[] tmpByte = new byte[len];
                                InputStream is = conn.getInputStream();

                                FileOutputStream fos = new FileOutputStream(file);
                                for (; ; ) {
                                    Read = is.read(tmpByte);
                                    if (Read <= 0) {
                                        break;
                                    }
                                    fos.write(tmpByte, 0, Read);
                                }

                                is.close();
                                fos.close();
                                conn.disconnect();
                            } catch (MalformedURLException e) {
                                Log.e("NESPRESSO", e.getMessage());
                            } catch (IOException e) {
                                Log.e("NESPRESSO", e.getMessage());
                            }
                        }
                        return null;
                    }
                }.execute(
                        "https://www.nespresso.com" + capsuleRange.getCapsuleList().get(j).getMediaQuickOrder().getUrl(),
                        capsuleRange.getCapsuleList().get(j).getMediaQuickOrder().getRealfilename()
                );
            }
        }

    }

    public boolean writeTextFile(String strFileName, String strBuf) {
        strFileName = strFileName.substring(strFileName.lastIndexOf("/") + 1);

        final File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), context.getString(R.string.localPath));
        try {
            File file = new File(f.getAbsolutePath() + File.separator + strFileName);
            FileOutputStream fos = new FileOutputStream(file);
            Writer out = new OutputStreamWriter(fos, "UTF-8");
            out.write(strBuf);
            out.close();

            Log.d("NESPRESSO", "FILE : " + file.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return true;
    }


}
