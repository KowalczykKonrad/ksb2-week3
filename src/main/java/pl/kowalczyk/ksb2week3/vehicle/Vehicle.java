package pl.kowalczyk.ksb2week3.vehicle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@AllArgsConstructor
public class Vehicle extends RepresentationModel<Vehicle> {
    private long id;
    private String brand;
    private String model;
    private String color;
}
