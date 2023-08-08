package pl.kowalczyk.ksb2week3.vehicle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<Vehicle>> getVehicles(@RequestParam(required = false) String color) {
        CollectionModel<Vehicle> resource;
        if(color == null) {
            vehicleService.getVehicles().forEach(vehicle -> {
                if(!vehicle.hasLink("self")) {
                    vehicle.add(WebMvcLinkBuilder.linkTo(VehicleController.class).slash(vehicle.getId()).withSelfRel());
                }
            });
            resource = CollectionModel.of(vehicleService.getVehicles());
            resource.add(WebMvcLinkBuilder.linkTo(VehicleController.class).withSelfRel());
            return new ResponseEntity<>(resource, HttpStatus.OK);
        }

        vehicleService.getVehiclesByColor(color).forEach(vehicle -> {
            if(!vehicle.hasLink("self")) {
                vehicle.add(WebMvcLinkBuilder.linkTo(VehicleController.class).slash(vehicle.getId()).withSelfRel());
            }
        });
        resource = CollectionModel.of(vehicleService.getVehiclesByColor(color));
        resource.add(WebMvcLinkBuilder.linkTo(VehicleController.class).withSelfRel());
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Vehicle>> getVehicleById(@PathVariable long id) {
        Optional<Vehicle> vehicle = vehicleService.getVehicleById(id);
        if(vehicle.isPresent()) {
            EntityModel<Vehicle> resource = EntityModel.of(vehicle.get());
            resource.add(WebMvcLinkBuilder.linkTo(VehicleController.class).slash(vehicle.get().getId()).withSelfRel());
            return new ResponseEntity<>(resource, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> addVehicle(@RequestBody Vehicle newVehicle) {
        if (vehicleService.addVehicle(newVehicle)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping
    public ResponseEntity<HttpStatus> updateVehicle(@RequestBody Vehicle newVehicle) {
        Optional<Vehicle> vehicle = vehicleService.getVehicleById(newVehicle.getId());

        if (vehicle.isPresent()) {
            if (vehicleService.updateVehicle(newVehicle)) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> removeVehicle(@PathVariable long id) {
        if (vehicleService.deleteVehicle(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
