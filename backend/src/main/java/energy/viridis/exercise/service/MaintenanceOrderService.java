package energy.viridis.exercise.service;

import java.text.ParseException;
import java.util.List;

import energy.viridis.exercise.dto.MaintenanceOrderDTO;

public interface MaintenanceOrderService {

    MaintenanceOrderDTO get(Long id);

	List<MaintenanceOrderDTO> getAll();
	
	MaintenanceOrderDTO create(MaintenanceOrderDTO createEquipment) throws ParseException;
    
	MaintenanceOrderDTO update(MaintenanceOrderDTO updateEquipment) throws ParseException;
    
    void delete(Long id);

}
