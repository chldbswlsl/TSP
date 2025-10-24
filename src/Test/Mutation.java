package Test;

import java.util.*;

public class Mutation {
    // 스왑 변이: 확률적으로 두 위치의 도시를 교환(탐색 다양성 유지)
    public static void mutate(Route route, double mutationRate, Random rng) {
        List<City> cities = route.getCities();             // 현재 경로
        for (int i = 0; i < cities.size(); i++) {          // 각 위치 i에 대해
            if (rng.nextDouble() < mutationRate) {         // 변이 확률에 따라
                int j = rng.nextInt(cities.size());        // 임의 위치 j 선택
                City tmp = cities.get(i);                  // swap i ↔ j
                cities.set(i, cities.get(j));
                cities.set(j, tmp);
            }
        }
        route.calculateDistance();                         // 거리/적합도 재계산
    }
}
