package pl.kowalczyk.ksb2week3.vehicle;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {
    private final List<Vehicle> vehicleList;

    public VehicleService() {
        vehicleList = new ArrayList<>();
        vehicleList.add(new Vehicle(1L, "Ford", "Fiesta", "blue"));
        vehicleList.add(new Vehicle(2L, "Opel", "Astra", "red"));
        vehicleList.add(new Vehicle(3L, "Citroen", "Berlingo", "green"));
    }

    public List<Vehicle> getVehicles() {
        return vehicleList;
    }

    public Optional<Vehicle> getVehicleById(long id) {
        return vehicleList.stream().filter(vehicle -> vehicle.getId() == id).findFirst();
    }

    public List<Vehicle> getVehiclesByColor(String color) {
        return vehicleList.stream().filter((vehicle -> vehicle.getColor().equals(color))).toList();
    }

    public boolean addVehicle(Vehicle newVehicle) {
        return vehicleList.add(newVehicle);
    }

    public boolean deleteVehicle(long id) {
        return vehicleList.removeIf(vehicle -> vehicle.getId() == id);
    }

    public boolean updateVehicle(Vehicle newVehicle) {
        if (deleteVehicle(newVehicle.getId())) {
            return addVehicle(newVehicle);
        }
        return false;
    }

}
