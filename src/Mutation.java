import java.util.*;

public class Mutation {
    public static void mutate(Route route, double mutationRate) {
        List<City> cities = route.getCities();
        for (int i = 0; i < cities.size(); i++) {
            if (Math.random() < mutationRate) {
                int j = (int) (Math.random() * cities.size());
                City tmp = cities.get(i);
                cities.set(i, cities.get(j));
                cities.set(j, tmp);
            }
        }
        route.calculateDistance();
    }
}
