package energy.viridis.exercise.service;

import java.util.List;

import energy.viridis.exercise.dto.MaintenanceOrderDTO;

public interface MaintenanceOrderService {

    MaintenanceOrderDTO get(Long id);

	List<MaintenanceOrderDTO> getAll();
	
	MaintenanceOrderDTO create(MaintenanceOrderDTO createEquipment) throws Exception;
    
	MaintenanceOrderDTO update(MaintenanceOrderDTO updateEquipment) throws Exception;
    
    boolean delete(Long id) throws Exception;

}
