package view.darren.com.dailylife.ui.home.model;

import org.json.JSONObject;

public class GalleryBean {
    private String imageUrl;
    private String title;

    public GalleryBean(String imageUrl,String title){
        this.imageUrl = imageUrl;
        this.title = title;
    }

    public GalleryBean(JSONObject jsonObject){
        this.imageUrl = jsonObject.optString("imageUrl");
        this.title = jsonObject.optString("title");
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
