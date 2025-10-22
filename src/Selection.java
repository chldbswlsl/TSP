import java.util.*;

public class Selection {
    public static Route selection(Population population, int selectedSize) {
        Random rand = new Random();
        List<Route> tournament = new ArrayList<>();

        for (int i = 0; i < selectedSize; i++) {
            int randomIndex = rand.nextInt(population.getRoutes().size());
            tournament.add(population.getRoutes().get(randomIndex));
        }

        Route bestRoute = tournament.get(0);
        for (int i = 1; i < tournament.size(); i++) {
            Route current = tournament.get(i);
            if (current.getDistance() < bestRoute.getDistance()) {
                bestRoute = current;
            }
        }

        return bestRoute;
    }
}
