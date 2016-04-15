package pe.servosa.android.model;

/**
 * Created by ucweb02 on 07/04/2016.
 */
public class NavDrawerItem {
    private boolean showNotify;
    private String title;

    private Integer icon;


    public NavDrawerItem() {

    }

    public NavDrawerItem(String title, Integer icon) {
        this.title = title;
        this.icon = icon;
    }

    public boolean isShowNotify() {
        return showNotify;
    }

    public void setShowNotify(boolean showNotify) {
        this.showNotify = showNotify;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getIcon() {
        return icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }
}
