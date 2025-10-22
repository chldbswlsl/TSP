import java.util.*;

public class Population {
    private List<Route> routes;

    public Population(int populationSize, List<City> allCities) {
        routes = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            routes.add(new Route(allCities)); // 랜덤 경로 생성
        }
    }

    public Population() {
        routes = new ArrayList<>();
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void addRoute(Route route) {
        routes.add(route);
    }

    public Route getBest() {
        Route bestRoute = routes.get(0);

        for (int i = 1; i < routes.size(); i++) {
            Route current = routes.get(i);
            if (current.getDistance() < bestRoute.getDistance()) {
                bestRoute = current;
            }
        }

        return bestRoute;
    }
}
