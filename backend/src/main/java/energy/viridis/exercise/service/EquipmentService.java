package energy.viridis.exercise.service;

import java.util.List;

import energy.viridis.exercise.dto.EquipmentDTO;
import energy.viridis.exercise.model.Equipment;

public interface EquipmentService {

	Equipment get(Long id);

	List<Equipment> getAll();

	EquipmentDTO create(EquipmentDTO createEquipment) throws Exception;
	
	EquipmentDTO update(EquipmentDTO updateEquipment) throws Exception;
    
    boolean delete(Long id) throws Exception;

}
