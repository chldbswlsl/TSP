public class City {
    public final int id;
    public final double x;
    public final double y;

    public City(int id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public double getDistance(City other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance;
    }

    @Override
    public String toString() {
        return "City(" + id + ')';
    }
}
