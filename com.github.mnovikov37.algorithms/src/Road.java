public class Road implements Measurable{
    private final int length;
    private int fare;

    public Road(int length, int fare) {
        this.length = length;
        this.fare = fare;
    }

    @Override
    public int getMetric(int i) {
        switch (i) {
            case 1 -> { return length; }
            case 2 -> { return fare; }
            default -> throw new IllegalStateException("Unexpected value: " + i);
        }
    }

    public String toString() {
        return "Road:{length=" + length + ", fare=" + fare + "}";
    }
}
