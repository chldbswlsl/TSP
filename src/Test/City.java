package Test;

public class City {
    public final int id;
    public final double x;
    public final double y;

    public City(int id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public double getDistance(City other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public String toString() {
        return "City(" + id + ')';
    }

    @Override
    public boolean equals(Object o) {                // 동등성 비교: id 기준
        if (this == o) return true;                     // 같은 객체면 true
        if (!(o instanceof City)) return false;          // 타입 다르면 false
        City other = (City) o;                        // 캐스팅
        return this.id == other.id;                     // id가 같으면 동일한 도시로 간주
    }

    @Override
    public int hashCode() {                 // HashSet/HashMap에서 사용
        return Integer.hashCode(id);        // id 기반 해시
    }
}
