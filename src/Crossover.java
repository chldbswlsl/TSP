import java.util.*;

public class Crossover {
    public static Route crossover(Route parent1, Route parent2) {
        List<City> p1 = parent1.getCities();
        List<City> p2 = parent2.getCities();

        int size = p1.size();
        List<City> childCities = new ArrayList<>(Collections.nCopies(size, null));

        int a = (int) (Math.random() * size);
        int b = (int) (Math.random() * size);
        int start = Math.min(a, b);
        int end = Math.max(a, b); // inclusive

        for (int i = start; i <= end; i++) {
            childCities.set(i, p1.get(i));
        }

        Set<City> inChild = new HashSet<>(childCities);

        int currentIndex = (end + 1) % size;
        for (City city : p2) {
            if (city == null || inChild.contains(city)) continue;

            while (childCities.get(currentIndex) != null) {
                currentIndex = (currentIndex + 1) % size;
            }
            childCities.set(currentIndex, city);
            inChild.add(city);
            currentIndex = (currentIndex + 1) % size;
        }

        return new Route(childCities);
    }
}
