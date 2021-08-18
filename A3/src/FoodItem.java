public  class FoodItem {
    private String name;

    public FoodItem(String name, double weight, double volume) {
        this.name = name;
        this.weight = weight;
        this.volume = volume;
    }

    private double weight, volume;

    public synchronized String getName() {
        return name;
    }
    public synchronized double getWeight() {
        return weight;
    }
    public synchronized double getVolume() {
        return volume;
    }
}