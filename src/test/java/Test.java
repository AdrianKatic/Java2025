

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.dronesim.model.Drone;
import com.dronesim.model.DroneDynamics;
import com.dronesim.model.DroneType;
import com.dronesim.parser.ManualJsonParser;

public class Test {
    public static void main(String[] args) throws Exception {
        
        ManualJsonParser parser = new ManualJsonParser();

        String typesJson  = Files.readString(Path.of("dronetypes.json"), StandardCharsets.UTF_8);
        String dronesJson = Files.readString(Path.of("drones.json"    ), StandardCharsets.UTF_8);
        String dynJson    = Files.readString(Path.of("dynamics.json"  ), StandardCharsets.UTF_8);

        List<DroneType> types = parser.parseDroneTypes(typesJson);
        System.out.println("Loaded DroneTypes: " + types.size());

        List<Drone> drones   = parser.parseDrones(dronesJson);
        System.out.println("Loaded Drones: "     + drones.size());

        List<DroneDynamics> dyn = parser.parseDynamics(dynJson);
        System.out.println("Loaded Dynamics: "   + dyn.size());

        // extract all labels and print
        System.out.println("labels in dronetypes.json:");
        parser.extractLabels(typesJson)
              .forEach(name -> System.out.println(" • " + name));

        System.out.println("\nlabels in drones.json:");
        parser.extractLabels(dronesJson)
              .forEach(name -> System.out.println(" • " + name));

        System.out.println("\nlabels in dynamics.json:");
        parser.extractLabels(dynJson)
              .forEach(name -> System.out.println(" • " + name));

        System.out.printf("\nLoaded: %d DroneTypes, %d Drones, %d Dynamics\n",
        types.size(), drones.size(), dyn.size());


        // extract first object from each json
        System.out.println("first DroneType-Object:");
        parser.extractFirstObject(typesJson)
            .forEach((key, val) -> System.out.println(key + " = " + val));

        System.out.println("\nfirst Drone-Object:");
        parser.extractFirstObject(dronesJson)
            .forEach((key, val) -> System.out.println(key + " = " + val));

        System.out.println("\nfirst Dynamics-Object:");
        parser.extractFirstObject(dynJson)
            .forEach((key, val) -> System.out.println(key + " = " + val));


    }
}
