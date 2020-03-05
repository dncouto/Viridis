package energy.viridis.exercise.controller;

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

import energy.viridis.exercise.dto.EquipmentDTO;
import energy.viridis.exercise.service.EquipmentService;

@RestController
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@RequestMapping("/api/equipment")
public class EquipmentController {

	@Autowired
	private EquipmentService equipmentService;

	@GetMapping
	public ResponseEntity<List<EquipmentDTO>> getAll() {
		return ResponseEntity.ok().body(equipmentService.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<EquipmentDTO> get(@PathVariable("id") Long id) {
		return ResponseEntity.ok().body(equipmentService.get(id));
	}
	
	@PostMapping
	public ResponseEntity<EquipmentDTO> create(@RequestBody EquipmentDTO equipment) {
	    try {
	        return ResponseEntity.ok().body(equipmentService.create(equipment));
	    } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
	
	@PutMapping
    public ResponseEntity<EquipmentDTO> update(@RequestBody EquipmentDTO equipment) {
        try {
            return ResponseEntity.ok().body(equipmentService.update(equipment));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

	@DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
	    try {
            if (equipmentService.delete(id)) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
