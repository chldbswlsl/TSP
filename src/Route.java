import java.util.*;

public class Route {
    private List<City> cities;
    private double distance;

    public Route(List<City> cities) {
        this(cities, true);
    }

    public Route(List<City> cities, boolean shuffle) {
        this.cities = new ArrayList<>(cities);

        if (shuffle) {
            Random rand = new Random();
            for (int i = this.cities.size() - 1; i > 0; i--) {
                int j = rand.nextInt(i + 1);
                City temp = this.cities.get(i);
                this.cities.set(i, this.cities.get(j));
                this.cities.set(j, temp);
            }
        }

        calculateDistance();
    }

    public void calculateDistance() {
        double total = 0;
        for (int i = 0; i < cities.size() - 1; i++) {
            total += cities.get(i).getDistance(cities.get(i + 1));
        }
        total += cities.get(cities.size() - 1).getDistance(cities.get(0));
        this.distance = total;
    }

    public double getDistance() {
        return distance;
    }
    public List<City> getCities() {
        return cities;
    }
}
