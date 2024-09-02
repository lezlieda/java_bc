package edu.school21.classes;

public class Car {
    private String model;
    private String color;
    private Double speed;

    public Car() {
        this.model = "Default model";
        this.color = "Default color";
        this.speed = 0.;
    }

    public Car(String model, String color, Double speed) {
        this.model = model;
        this.color = color;
        this.speed = speed;
    }

    public Double accelerate(Double acceleration) {
        this.speed += acceleration;
        return this.speed;
    }

    public Double accelerate1(Double acceleration) {
        this.speed -= acceleration;
        return this.speed;
    }

    public void stop() {
        this.speed = 0.;
    }

    @Override
    public String toString() {
        return "Car{" +
                "model='" + model + '\'' +
                ", color=" + color +
                ", speed=" + speed +
                '}';
    }
}
