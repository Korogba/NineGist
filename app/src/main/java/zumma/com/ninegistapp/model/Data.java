package zumma.com.ninegistapp.model;

public class Data {
    private String desc;
    private int image;
    private String title;

    public Data(String paramString, int paramInt) {
        this.title = paramString;
        this.image = paramInt;
    }

    public Data(String paramString1, String paramString2, int paramInt) {
        this.title = paramString1;
        this.desc = paramString2;
        this.image = paramInt;
    }

    public String getDesc() {
        return this.desc;
    }

    public int getImage() {
        return this.image;
    }

    public String getTitle() {
        return this.title;
    }

    public void setDesc(String paramString) {
        this.desc = paramString;
    }

    public void setImage(int paramInt) {
        this.image = paramInt;
    }

    public void setTitle(String paramString) {
        this.title = paramString;
    }
}

/* Location:           C:\Users\Okafor\workspace\imate\iMate_dex2jar.jar
 * Qualified Name:     com.imate.model.Data
 * JD-Core Version:    0.6.0
 */