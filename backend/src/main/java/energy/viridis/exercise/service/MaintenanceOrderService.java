package energy.viridis.exercise.service;

import java.text.ParseException;
import java.util.List;

import energy.viridis.exercise.dto.MaintenanceOrderDTO;
import energy.viridis.exercise.dto.MaintenanceOrderViewerDTO;

public interface MaintenanceOrderService {

    MaintenanceOrderViewerDTO get(Long id);

	List<MaintenanceOrderViewerDTO> getAll();
	
	MaintenanceOrderDTO create(MaintenanceOrderDTO createEquipment) throws ParseException;
    
	MaintenanceOrderDTO update(MaintenanceOrderDTO updateEquipment) throws ParseException;
    
    void delete(Long id);

}
