public class GeoStatisticItem {
    private double latitude;
    private double longitude;
    private double number;

    public GeoStatisticItem() {
    }

    public GeoStatisticItem(double latitude, double longitude, double number) {
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

    public double getNumber() {
        return number;
    }

    public void setNumber(double number) {
        this.number = number;
    }
}
