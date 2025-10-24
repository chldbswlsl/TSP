package Test;

import java.util.*;

public class Selection {
    // 토너먼트 선택: 무작위 K개 경로 중 fitness(적합도)가 가장 큰 개체를 부모로 선택
    public static Route selection(Population pop, int k, Random rng) {
        List<Route> group = new ArrayList<>();             // 토너먼트 후보군
        for (int i = 0; i < k; i++) {                      // K번 뽑기
            group.add(pop.getRoutes().get(rng.nextInt(pop.getRoutes().size())));
        }

        Route best = group.get(0);                         // 현재 최고 적합도 후보
        for (int i = 1; i < group.size(); i++) {           // 나머지 후보와 비교
            if (group.get(i).getFitness() > best.getFitness()) {
                best = group.get(i);                       // fitness 더 크면 교체
            }
        }
        return best;                                       // 가장 적합한 부모 반환
    }

    // (옵션) 룰렛휠 선택: fitness에 비례한 확률로 부모 1개 선택
    public static Route roulette(Population pop, Random rng) {
        double total = 0;                                  // 전체 적합도 합
        for (Route r : pop.getRoutes()) total += r.getFitness();
        double pick = rng.nextDouble() * total;            // 0..total 중 임의 지점
        double acc = 0;                                    // 누적합
        for (Route r : pop.getRoutes()) {
            acc += r.getFitness();                         // 누적
            if (acc >= pick) return r;                     // 누적이 지점을 넘으면 선택
        }
        return pop.getRoutes().get(pop.getRoutes().size() - 1); // 안전장치(마지막 반환)
    }
}
