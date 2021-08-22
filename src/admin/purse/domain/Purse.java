package admin.purse.domain;

public class Purse {
    private String purseId;
    private String uid;
    private double rechange;
    private boolean state;

    public String getPurseId() {
        return purseId;
    }

    public void setPurseId(String purseId) {
        this.purseId = purseId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public double getRechange() {
        return rechange;
    }

    public void setRechange(double rechange) {
        this.rechange = rechange;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
