package vn.nguyenquocthai.searchlocation;

public class LocationResult {
    private String display_name;
    private double lat;
    private double lon;

    public LocationResult(String display_name, double lat, double lon) {
        this.display_name = display_name;
        this.lat = lat;
        this.lon = lon;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
