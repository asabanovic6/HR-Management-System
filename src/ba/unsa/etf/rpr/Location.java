package ba.unsa.etf.rpr;

public class Location {
    private int locationId;
    private String city;

    public Location() {
    }

    public Location(int locationId, String city) {
        this.locationId = locationId;
        this.city = city;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
