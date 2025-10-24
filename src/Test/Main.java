package Test;

import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;

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
        final int selectionSize = 5;
        final int generations = 500;

        final double heuristicRatio = 0.2;
        final double baseMutation = 0.05;
        final double decay = 0.995;
        final double floor = 0.005;
        final int patience = 150;

        Random rng = new Random(42);
        Route.setRng(rng);

        Population pop = new Population(populationSize, cities, rng, heuristicRatio);

        System.out.printf("초기 거리=%.6f | 초기 fitness=%.9f%n",
                pop.getBest().getDistance(), pop.getFittest().getFitness());

        Route bestEver = new Route(pop.getBest().getCities(), false);
        double bestDist = bestEver.getDistance();
        int noImprove = 0;

        double mutationRate = baseMutation;  // mutationRate를 루프 밖에서 선언

        // CSV 파일 쓰기 준비 (시각화 데이터 저장)
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("generation_data.csv"))) {
            writer.write("Generation,City Order,Distance,Fitness\n");

            for (int gen = 0; gen < generations; gen++) {
                mutationRate = Math.max(floor, baseMutation * Math.pow(decay, gen)); // 매 세대마다 변이율 감소

                Population next = new Population();

                List<Route> sorted = new ArrayList<>(pop.getRoutes());
                sorted.sort(Comparator.comparingDouble(Route::getDistance));
                for (int e = 0; e < eliteCount; e++) {
                    next.addRoute(new Route(sorted.get(e).getCities(), false));
                }

                for (int i = eliteCount; i < populationSize; i++) {
                    Route p1 = Selection.selection(pop, selectionSize, rng);
                    Route p2 = Selection.selection(pop, selectionSize, rng);
                    Route child = Crossover.crossover(p1, p2, rng);
                    Mutation.mutate(child, mutationRate, rng);
                    next.addRoute(child);
                }

                pop = next;

                Route genBest = pop.getBest();
                Route genFit  = pop.getFittest();

                if (genBest.getDistance() + 1e-9 < bestDist) {
                    bestEver = new Route(genBest.getCities(), false);
                    bestDist = bestEver.getDistance();
                    noImprove = 0;
                } else {
                    noImprove++;
                }

                if (gen % 50 == 0 || gen == generations - 1) {
                    System.out.printf("%3d세대 | dist=%.6f | fit=%.9f | mut=%.4f%n",
                            gen, genBest.getDistance(), genFit.getFitness(), mutationRate);
                }

                if (noImprove >= patience) {
                    System.out.printf("조기 종료: %d세대 이후 개선 없음%n", gen);
                    break;
                }

                // 시각화를 위한 데이터 저장
                writer.write(String.format("%d,%s,%.6f,%.9f\n",
                        gen, genBest.getCities().toString(), genBest.getDistance(), genFit.getFitness()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 최종 결과 출력
        System.out.println("=== 최종 결과 ===");
        System.out.println("최적 경로 : " + bestEver.getCities());
        System.out.printf("최단 거리 : %.6f%n", bestEver.getDistance());
        System.out.printf("최대 fitness : %.9f%n", 1.0 / (bestEver.getDistance() + 1e-9));
        System.out.println("개체군 크기 : " + pop.getRoutes().size());
        System.out.println("진화 세대 : " + generations);
        System.out.println("변이 확률 : " + mutationRate);
    }
}
