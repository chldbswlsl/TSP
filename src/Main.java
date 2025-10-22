import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<City> cities = new ArrayList<>(Arrays.asList(
                new City(0, 12.4, 45.7),
                new City(1, 34.8, 67.1),
                new City(2, 89.5, 12.6),
                new City(3, 54.2, 88.4),
                new City(4, 22.1, 19.5),
                new City(5, 73.3, 52.9),
                new City(6, 16.8, 80.0),
                new City(7, 62.5, 25.4),
                new City(8, 45.1, 39.0),
                new City(9, 90.3, 75.6),
                new City(10, 11.7, 61.2),
                new City(11, 78.2, 32.5),
                new City(12, 24.3, 12.7),
                new City(13, 31.6, 54.1),
                new City(14, 50.5, 16.9),
                new City(15, 69.0, 69.3),
                new City(16, 80.4, 45.2),
                new City(17, 60.8, 10.1),
                new City(18, 14.5, 33.3),
                new City(19, 47.9, 78.0),
                new City(20, 28.0, 64.8),
                new City(21, 83.7, 20.9),
                new City(22, 18.2, 49.6),
                new City(23, 77.5, 59.4),
                new City(24, 35.2, 27.7),
                new City(25, 67.1, 81.5)
        ));

        final int populationSize = 100;
        final int eliteCount = 5;

        Population population = new Population(populationSize, cities);

        double mutationRate = 0.02;
        int selectionSize = 5;
        int generations = 500;

        System.out.println("=== 초기 최적 거리 ===");
        System.out.println(population.getBest().getDistance());

        Route bestEver = new Route(population.getBest().getCities(), false);

        for (int generation = 0; generation < generations; generation++) {
            Population newPop = new Population();
            List<Route> sortedRoutes = new ArrayList<>(population.getRoutes());
            sortedRoutes.sort(Comparator.comparingDouble(Route::getDistance));

            for (int e = 0; e < eliteCount; e++) {
                newPop.addRoute(new Route(sortedRoutes.get(e).getCities(), false));
            }

            for (int i = eliteCount; i < populationSize; i++) {
                Route parent1 = Selection.selection(population, selectionSize);
                Route parent2 = Selection.selection(population, selectionSize);

                Route child = Crossover.crossover(parent1, parent2);
                Mutation.mutate(child, mutationRate);
                newPop.addRoute(child);
            }

            population = newPop;

            Route genBest = population.getBest();
            if (genBest.getDistance() < bestEver.getDistance()) {
                bestEver = new Route(genBest.getCities(), false);
            }

            if (generation % 50 == 0 || generation == generations - 1) {
                System.out.println(generation + "세대 : " + population.getBest().getDistance());
            }
        }

        System.out.println("=== 최종 결과 ===");
        System.out.println("최적 경로 : " + bestEver.getCities());
        System.out.println("최단 거리 : " + bestEver.getDistance());
        System.out.println("개체군 크기 : " + population.getRoutes().size());
        System.out.println("진화 세대 : " + generations);
        System.out.println("변이 확률 : " + mutationRate);
    }
}
