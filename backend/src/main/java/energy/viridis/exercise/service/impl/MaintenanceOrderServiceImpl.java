package energy.viridis.exercise.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import energy.viridis.exercise.dto.MaintenanceOrderDTO;
import energy.viridis.exercise.model.Equipment;
import energy.viridis.exercise.model.MaintenanceOrder;
import energy.viridis.exercise.repository.MaintenanceOrderRepository;
import energy.viridis.exercise.service.MaintenanceOrderService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MaintenanceOrderServiceImpl implements MaintenanceOrderService {

	@Autowired
	private MaintenanceOrderRepository maintenanceOrderRepository;
    
    @Autowired
    CacheManager cacheManager;
    

	@Override
	@Cacheable("maintenanceorder#get")
	public MaintenanceOrderDTO get(Long id) {

		log.info("Retrieving Maintenance Order - id: {}", id);
		MaintenanceOrder maintenanceOrder = maintenanceOrderRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Maintenance Order not found."));
		return new MaintenanceOrderDTO(maintenanceOrder); 
	}

	@Override
	@Cacheable("maintenanceorder#getAll")
	public List<MaintenanceOrderDTO> getAll() {

		log.info("Listing all Maintenance Orders...");
		List<MaintenanceOrderDTO> resultList = new ArrayList<MaintenanceOrderDTO>();
		maintenanceOrderRepository.findAll().forEach((item) -> {
		    resultList.add(new MaintenanceOrderDTO(item));
		});
		return resultList;
	}

    @Override
    @CacheEvict(cacheNames= {"maintenanceorder#get","maintenanceorder#getAll"}, allEntries=true)
    public MaintenanceOrderDTO create(MaintenanceOrderDTO dto) throws ParseException {
        
        log.info("Insert new Maintence Order");
        
        validate(dto);
        
        MaintenanceOrder order = new MaintenanceOrder();
        order.setEquipment(new Equipment().withId(dto.getEquipmentId()));
        order.setScheduledDate(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(dto.getScheduledDate()));
        order = maintenanceOrderRepository.save(order);
        MaintenanceOrderDTO objInserted = new MaintenanceOrderDTO(order);
        return objInserted;
    }

    @Override
    @CacheEvict(cacheNames= {"maintenanceorder#get","maintenanceorder#getAll"}, allEntries=true)
    public MaintenanceOrderDTO update(MaintenanceOrderDTO dto) throws ParseException {
        
        log.info("Update one Maintence Order");
        
        validate(dto);
        
        MaintenanceOrder order = maintenanceOrderRepository.findById(dto.getId()).orElse(null);
        if (order == null) {
            log.info("Delete Maintence Order not found - id: {}", dto.getId());
            clearCaches();
            throw new NoSuchElementException("Esta ordem de manutenção não existe mais na base.");
        } else {
            order.setEquipment(new Equipment().withId(dto.getEquipmentId()));
            order.setScheduledDate(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(dto.getScheduledDate()));
            order = maintenanceOrderRepository.save(order);
            MaintenanceOrderDTO objUpdated = new MaintenanceOrderDTO(order);
            return objUpdated;
        }
    }

    @Override
    @CacheEvict(cacheNames= {"maintenanceorder#get","maintenanceorder#getAll"}, allEntries=true)
    public void delete(Long id) {

        log.info("Delete a Maintence Order - id: {}", id);
        
        MaintenanceOrder order = maintenanceOrderRepository.findById(id).orElse(null);
        if (order == null) {
            log.info("Delete Maintence Order not found - id: {}", id);
            clearCaches();
            throw new NoSuchElementException("Esta ordem de manutenção não existe mais na base.");
        } else {
            maintenanceOrderRepository.delete(order);
        }
    }
    
    private void validate(MaintenanceOrderDTO dto) {
        if (dto.getEquipmentId() == null) {
            throw new NullPointerException("Equipamento deve ser informado!");
        }
        
        if (dto.getScheduledDate().isEmpty()) {
            throw new NullPointerException("Data/Hora programada deve ser informada!");
        }
    }
    
    private void clearCaches() {
        cacheManager.getCache("maintenanceorder#getAll").clear();
        cacheManager.getCache("maintenanceorder#get").clear();
    }

}
