package energy.viridis.exercise.service;

import java.util.List;

import energy.viridis.exercise.dto.EquipmentDTO;

public interface EquipmentService {

    EquipmentDTO get(Long id);

	List<EquipmentDTO> getAll();

	EquipmentDTO create(EquipmentDTO createEquipment) throws Exception;
	
	EquipmentDTO update(EquipmentDTO updateEquipment) throws Exception;
    
    boolean delete(Long id) throws Exception;

}
