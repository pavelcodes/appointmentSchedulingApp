package Model;

public class BusinessHours {

    public static String openTime;
    public static String closeTime;

    public static String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public static String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public BusinessHours(String openTime, String closeTime) {
        this.openTime = openTime;
        this.closeTime = closeTime;


    }

}
