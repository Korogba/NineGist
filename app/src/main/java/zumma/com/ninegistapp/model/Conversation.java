package zumma.com.ninegistapp.model;

import zumma.com.ninegistapp.ui.helpers.GDate;

public class Conversation {

    private String fromId;
    private String toId;
    private String date;
    private boolean isSent;
    private String msg;
    private int pflag;
    private int report;
    private long created_at;
    private String uniqkey;

    public Conversation() {

    }

    public Conversation(String fromId, String toId, String date, boolean isSent, String msg) {
        this.fromId = fromId;
        this.toId = toId;
        this.date = date;
        this.isSent = isSent;
        this.msg = msg;
        GDate gDate = new GDate();
        created_at = gDate.getTimeStamp();
        uniqkey = null;
    }

    public Conversation(String fromId, String toId, String date, boolean isSent, String msg, int pflag, int report, long created_at, String uniqkey) {
        this.fromId = fromId;
        this.toId = toId;
        this.date = date;
        this.isSent = isSent;
        this.msg = msg;
        this.pflag = pflag;
        this.report = report;
        this.created_at = created_at;
        this.uniqkey = uniqkey;
    }

    public String getUniqkey() {
        return uniqkey;
    }

    public void setUniqkey(String uniqkey) {
        this.uniqkey = uniqkey;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public int getReport() {
        return report;
    }

    public void setReport(int report) {
        this.report = report;
    }

    public int getPflag() {
        return pflag;
    }

    public void setPflag(int pflag) {
        this.pflag = pflag;
    }

    public String getDate() {
        return this.date;
    }

    public String getMsg() {
        return this.msg;
    }

    public boolean isSent() {
        return this.isSent;
    }

    public void setDate(String paramString) {
        this.date = paramString;
    }

    public void setMsg(String paramString) {
        this.msg = paramString;
    }

    public void setSent(boolean paramBoolean) {
        this.isSent = paramBoolean;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public void save(){

    }

    @Override
    public String toString() {
        return "Conversation{" +
                "fromId='" + fromId + '\'' +
                ", toId='" + toId + '\'' +
                ", date='" + date + '\'' +
                ", isSent=" + isSent +
                ", msg='" + msg + '\'' +
                ", pflag=" + pflag +
                ", report=" + report +
                ", created_at=" + created_at +
                '}';
    }
}
