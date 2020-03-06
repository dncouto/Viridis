package energy.viridis.exercise.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import energy.viridis.exercise.dto.MaintenanceOrderDTO;
import energy.viridis.exercise.service.MaintenanceOrderService;

@RestController
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@RequestMapping("/api/maintenance-order")
public class MaintenanceOrderController {

	@Autowired
	private MaintenanceOrderService maintenanceOrderService;

	@GetMapping
	public ResponseEntity<List<MaintenanceOrderDTO>> getAll() {
		return ResponseEntity.ok().body(maintenanceOrderService.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<MaintenanceOrderDTO> get(@PathVariable("id") Long id) {
		return ResponseEntity.ok().body(maintenanceOrderService.get(id));
	}
	
	@PostMapping
    public ResponseEntity<MaintenanceOrderDTO> create(@RequestBody MaintenanceOrderDTO order) throws ParseException {
	    return ResponseEntity.ok().body(maintenanceOrderService.create(order));
    }
    
    @PutMapping
    public ResponseEntity<MaintenanceOrderDTO> update(@RequestBody MaintenanceOrderDTO order) throws ParseException {
        return ResponseEntity.ok().body(maintenanceOrderService.update(order));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        maintenanceOrderService.delete(id);
        return ResponseEntity.ok().build();
    }
}
