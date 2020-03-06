package energy.viridis.exercise.service;

import java.util.List;

import energy.viridis.exercise.dto.EquipmentDTO;

public interface EquipmentService {

    EquipmentDTO get(Long id);

	List<EquipmentDTO> getAll();

	EquipmentDTO create(EquipmentDTO createEquipment);
	
	EquipmentDTO update(EquipmentDTO updateEquipment);
    
    void delete(Long id);

}
