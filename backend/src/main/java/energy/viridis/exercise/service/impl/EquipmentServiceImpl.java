package energy.viridis.exercise.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import energy.viridis.exercise.dto.EquipmentDTO;
import energy.viridis.exercise.model.Equipment;
import energy.viridis.exercise.repository.EquipmentRepository;
import energy.viridis.exercise.service.EquipmentService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EquipmentServiceImpl implements EquipmentService {

	@Autowired
	private EquipmentRepository equipmentRepository;

	@Override
	@Cacheable("equipment#get")
	public EquipmentDTO get(Long id) {

		log.info("Retrieving Equipment - id: {}", id);
		Equipment equipment = equipmentRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Equipment not found."));
		return new EquipmentDTO(equipment);
	}

	@Override
	@Cacheable("equipment#getAll")
	public List<EquipmentDTO> getAll() {

		log.info("Listing all Equipment");
		List<EquipmentDTO> resultList = new ArrayList<EquipmentDTO>();
		equipmentRepository.findAll().forEach((item) -> {
            resultList.add(new EquipmentDTO(item));
        });
		return resultList;
	}
	
	@Override
	@CacheEvict(cacheNames= {"equipment#get","equipment#getAll"}, allEntries=true)
	public EquipmentDTO create(EquipmentDTO dto) throws Exception {
	    
	    log.info("Insert new Equipment");
	    
	    validate(dto);
	    
	    Equipment equipment = new Equipment().withName(dto.getName());
	    equipment = equipmentRepository.save(equipment);
	    EquipmentDTO objInserted = new EquipmentDTO(equipment);
        return objInserted;
	}
	
	@Override
	@CacheEvict(cacheNames= {"equipment#get","equipment#getAll"}, allEntries=true)
    public EquipmentDTO update(EquipmentDTO dto) throws Exception {
        
        log.info("Update one Equipment");
        
        validate(dto);
        
        Equipment equipment = new Equipment().withId(dto.getId()).withName(dto.getName());
        equipment = equipmentRepository.save(equipment);
        EquipmentDTO objUpdated = new EquipmentDTO(equipment);
        return objUpdated;
    }

	@Override
	@CacheEvict(cacheNames= {"equipment#get","equipment#getAll"}, allEntries=true)
    public boolean delete(Long id) throws Exception {

        log.info("Delete a Equipment - id: {}", id);
        
        Equipment equipment = equipmentRepository.findById(id).orElse(null);
        if (equipment == null) {
            log.info("Delete Equipment not found - id: {}", id);
        } else {
            equipmentRepository.delete(equipment);
            return true;
        }
        
        return false;
    }
	
	private void validate(EquipmentDTO dto) throws Exception {
        if (dto.getName().isEmpty()) {
            throw new Exception("Nome do equipamento deve ser informado!");
        }
    }
}
