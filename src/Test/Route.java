package Test;

import java.util.*;

public class Route {
    private static Random r = new Random();        // 경로 셔플 등에 사용할 공용 난수기
    public static void setRng(Random rng) {          // 외부에서 RNG를 주입(재현성 보장)
        if (rng != null) r = rng;
    }

    private final List<City> cities;                 // 경로(순서가 중요)
    private double distance;                         // 해당 경로의 총 거리
    private double fitness;                          // 적합도(클수록 좋은 값, 여기선 1/(distance+ε))

    public Route(List<City> cities) {                // 기본 생성자: 경로를 받아 셔플해서 초기화
        this(cities, true);
    }

    public Route(List<City> cities, boolean shuffle) {// 셔플 여부를 선택할 수 있는 생성자
        this.cities = new ArrayList<>(cities);        // 내부 리스트로 복사(원본 보호)
        if (shuffle) {                                // 초기 개체군 랜덤화를 위해 셔플
            for (int i = this.cities.size() - 1; i > 0; i--) { // Fisher–Yates
                int j = r.nextInt(i + 1);           // 0..i 중 무작위
                City t = this.cities.get(i);          // swap: i와 j 교환
                this.cities.set(i, this.cities.get(j));
                this.cities.set(j, t);
            }
        }
        calculateDistance();                          // 거리 및 fitness 계산
    }

    public List<City> getCities() { return cities; }  // 경로(도시 순서) 접근자
    public double getDistance()    { return distance; } // 거리 접근자
    public double getFitness()     { return fitness; }  // 적합도 접근자

    public void calculateDistance() {                               // 경로 거리를 다시 계산(변이 후 호출)
        double total = 0;                                                       // 누적 거리
        for (int i = 0; i < cities.size() - 1; i++) {                                  // 인접 도시 간 거리 합
            total += cities.get(i).getDistance(cities.get(i + 1));
        }
        total += cities.get(cities.size() - 1)                                              // 마지막 도시에서
                .getDistance(cities.get(0));                                        // 첫 도시로 돌아오는 거리(순회 조건)
        this.distance = total;                                                  // 거리 갱신

        this.fitness = 1.0 / (this.distance + 1e-9);                        // 거리 → 적합도 변환(0분모 방지)
    }
}
