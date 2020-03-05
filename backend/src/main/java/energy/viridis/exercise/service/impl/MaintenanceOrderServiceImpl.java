package energy.viridis.exercise.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import energy.viridis.exercise.dto.MaintenanceOrderDTO;
import energy.viridis.exercise.model.Equipment;
import energy.viridis.exercise.model.MaintenanceOrder;
import energy.viridis.exercise.repository.MaintenanceOrderRepository;
import energy.viridis.exercise.service.EquipmentService;
import energy.viridis.exercise.service.MaintenanceOrderService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MaintenanceOrderServiceImpl implements MaintenanceOrderService {

	@Autowired
	private MaintenanceOrderRepository maintenanceOrderRepository;

	@Autowired
    private EquipmentService equipmentService;
	
	@Override
	@Cacheable("equipment#get")
	public MaintenanceOrderDTO get(Long id) {

		log.info("Retrieving Maintenance Order - id: {}", id);
		MaintenanceOrder maintenanceOrder = maintenanceOrderRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Maintenance Order not found."));
		MaintenanceOrderDTO result = new MaintenanceOrderDTO(maintenanceOrder);
		result.setEquipmentsAvailable(equipmentService.getAll());
		return result; 
	}

	@Override
	public List<MaintenanceOrderDTO> getAll() {

		log.info("Listing all Maintenance Orders...");
		List<MaintenanceOrderDTO> resultList = new ArrayList<MaintenanceOrderDTO>();
		maintenanceOrderRepository.findAll().forEach((item) -> {
		    resultList.add(new MaintenanceOrderDTO(item));
		});
		return resultList;
	}

    @Override
    public MaintenanceOrderDTO create(MaintenanceOrderDTO dto) throws Exception {
        
        log.info("Insert new Maintence Order");
        MaintenanceOrder order = new MaintenanceOrder();
        order.setEquipment(new Equipment().withId(dto.getEquipmentId()));
        order.setScheduledDate(dto.getScheduledDate());
        order = maintenanceOrderRepository.save(order);
        MaintenanceOrderDTO objInserted = new MaintenanceOrderDTO(order);
        return objInserted;
    }

    @Override
    public MaintenanceOrderDTO update(MaintenanceOrderDTO dto) throws Exception {
        
        log.info("Update one Maintence Order");
        MaintenanceOrder order = new MaintenanceOrder();
        order.setId(dto.getId());
        order.setEquipment(new Equipment().withId(dto.getEquipmentId()));
        order.setScheduledDate(dto.getScheduledDate());
        order = maintenanceOrderRepository.save(order);
        MaintenanceOrderDTO objUpdated = new MaintenanceOrderDTO(order);
        return objUpdated;
    }

    @Override
    public boolean delete(Long id) throws Exception {

        log.info("Delete a Maintence Order - id: {}", id);
        
        MaintenanceOrder order = maintenanceOrderRepository.findById(id).orElse(null);
        if (order == null) {
            log.info("Delete Equipment not found - id: {}", id);
        } else {
            maintenanceOrderRepository.delete(order);
            return true;
        }
        
        return false;
    }

}
