package Test;

import java.util.*;

public class Crossover {
    public static Route crossover(Route parent1, Route parent2, Random rng) {
        List<City> p1 = parent1.getCities();
        List<City> p2 = parent2.getCities();
        int size = p1.size();                               // 도시 수(유전자 길이)

        List<City> child = new ArrayList<>(Collections.nCopies(size, (City) null));
        // 자식 리스트(초기 null)

        int a = rng.nextInt(size);                          // 랜덤 컷 포인트 a
        int b = rng.nextInt(size);                          // 랜덤 컷 포인트 b
        int start = Math.min(a, b);                         // 구간 시작(작은 값)
        int end   = Math.max(a, b);                         // 구간 끝(큰 값, inclusive)

        for (int i = start; i <= end; i++) {                // 부모1 구간을 자식에 그대로 복사
            child.set(i, p1.get(i));
        }

        Set<City> inChild = new HashSet<>(child);          // 자식에 이미 들어간 도시 집합
        int idx = (end + 1) % size;                        // 채우기 시작 위치(구간 다음 칸)

        for (City c : p2) {                                  // 부모2의 순서를 따라가며
            if (c == null || inChild.contains(c)) continue;  // 중복 도시는 건너뜀
            while (child.get(idx) != null)                   // 비어있는 칸을 찾아
                idx = (idx + 1) % size;
            child.set(idx, c);                               // 해당 칸에 채움
            inChild.add(c);                                  // 집합에도 추가
            idx = (idx + 1) % size;                          // 다음 칸으로 이동
        }

        return new Route(child, false);                    // 자식은 섞지 않음(순서 유지)
    }
}
