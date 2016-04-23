public class GeoStatisticItem {
    private double latitude;
    private double longitude;
    private int number;

    public GeoStatisticItem() {
    }

    public GeoStatisticItem(double latitude, double longitude, int number) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.number = number;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
