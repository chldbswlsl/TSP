package Test;

import java.util.*;

public class Population {
    private final List<Route> routes;                               // 개체군(여러 경로)

    // 초기 개체군 생성자: heuristicRatio 비율만큼 NN(최근접 이웃) 경로를 섞고 나머지는 랜덤
    public Population(int populationSize, List<City> allCities, Random rng, double heuristicRatio) {
        routes = new ArrayList<>();

        int heuristicCount = Math.max(0, Math.min(populationSize,               // 0..populationSize 범위로 클램프
                (int) Math.round(populationSize * heuristicRatio)));            // NN로 만들 개체 수

        for (int h = 0; h < heuristicCount; h++) {                              // 일부는 NN로 초기화
            routes.add(nearestNeighborRoute(allCities, h % allCities.size()));  // 시작점을 바꿔 다양성 확보
        }

        Route.setRng(rng);                                                      // 이후 랜덤 경로 셔플용 RNG 주입
        for (int i = heuristicCount; i < populationSize; i++) {                 // 나머지는 랜덤 경로
            routes.add(new Route(allCities, true));
        }
    }

    public Population() { routes = new ArrayList<>(); }                         // 빈 개체군(후속 세대 만들 때 사용)

    public List<Route> getRoutes() { return routes; }                           // 개체군 접근자
    public void addRoute(Route route) { routes.add(route); }                    // 개체 추가

    public Route getBest() {                                                    // 거리 기준 최적 개체(최소)
        Route best = routes.get(0);
        for (Route r : routes) {
            if (r.getDistance() < best.getDistance()) best = r;                 // 더 짧으면 교체
        }
        return best;
    }

    public Route getFittest() {                                                 // fitness 기준 최적 개체(최대)
        Route fittest = routes.get(0);
        for (Route r : routes) {
            if (r.getFitness() > fittest.getFitness()) fittest = r;             // 더 크면 교체
        }
        return fittest;
    }

    // 최근접 이웃(Nearest Neighbor) 경로 생성: 주어진 시작 인덱스에서 가장 가까운 도시를 연쇄적으로 선택
    private static Route nearestNeighborRoute(List<City> all, int startIdx) {
        List<City> result = new ArrayList<>();             // NN 경로 누적 리스트
        boolean[] used = new boolean[all.size()];          // 방문 체크 배열
        int cur = startIdx;                                 // 현재 위치(도시 인덱스)
        result.add(all.get(cur));                           // 시작 도시 추가
        used[cur] = true;                                   // 방문 처리

        for (int k = 1; k < all.size(); k++) {              // 남은 도시 수만큼 반복
            int next = -1;                                  // 다음으로 갈 도시 인덱스
            double best = Double.POSITIVE_INFINITY;         // 현재까지의 최소 거리
            for (int j = 0; j < all.size(); j++) {          // 모든 도시 후보를 살핌
                if (!used[j]) {                             // 아직 방문하지 않은 도시만
                    double d = all.get(cur).getDistance(all.get(j)); // 현재 도시→후보 도시 거리
                    if (d < best) {                         // 더 가까우면 갱신
                        best = d;
                        next = j;
                    }
                }
            }
            result.add(all.get(next));                      // 가장 가까운 도시를 경로에 추가
            used[next] = true;                              // 방문 처리
            cur = next;                                     // 현재 위치 이동
        }
        return new Route(result, false);                    // NN는 순서가 의미 있으므로 셔플 금지
    }
}
