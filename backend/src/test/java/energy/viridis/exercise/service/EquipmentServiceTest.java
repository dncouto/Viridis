package energy.viridis.exercise.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringRunner;

import energy.viridis.exercise.dto.EquipmentDTO;
import energy.viridis.exercise.model.Equipment;
import energy.viridis.exercise.repository.EquipmentRepository;
import energy.viridis.exercise.service.impl.EquipmentServiceImpl;


@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
class EquipmentServiceTest {

    @InjectMocks
    private EquipmentService equipmentService = new EquipmentServiceImpl();
    
    @Mock
    private EquipmentRepository equipmentRepository;
    @Mock
    private CacheManager cacheManager;
        
    
    @BeforeAll
    public static void setUp() {
        
    }
    
    @Test
    final void testGet() {
        // encontrado
        Optional<Equipment> equip = Optional.of(new Equipment().withId(1L).withName("Equipamento stub"));
        when(equipmentRepository.findById(any())).thenReturn(equip);
        
        EquipmentDTO result = equipmentService.get(any());
        
        assertNotNull(result);
        assertTrue(result.getId().equals(1L));
        assertTrue(result.getName().equals("Equipamento stub"));
        
        // não encontrado
        when(equipmentRepository.findById(any())).thenReturn(Optional.empty());
        
        assertThrows(NoSuchElementException.class, ()->{ equipmentService.get(any()); });
    }

    @Test
    final void testGetAll() {
        List<Equipment> list = new ArrayList<>();
        when(equipmentRepository.findAll()).thenReturn(list);
        for (int i = 1; i <= 10; i++) {
            Equipment equip = new Equipment().withId(Long.valueOf(i)).withName("Equipamento stub");
            list.add(equip);    
        }
        
        List<EquipmentDTO> result = equipmentService.getAll();
        
        assertFalse(result.isEmpty());
        assertTrue(result.size() == 10);
        assertTrue(result.get(1).getId().equals(2L));
        assertTrue(result.get(1).getName().equals("Equipamento stub"));
        
        // resultado vazio
        list.clear();
        when(equipmentRepository.findAll()).thenReturn(list);
        
        result = equipmentService.getAll();
        
        assertTrue(result.isEmpty());
    }

    @Test
    final void testCreate() {
        // com todos os campos preenchidos
        Equipment equip = new Equipment().withId(1L).withName("Equipamento stub");
        when(equipmentRepository.save(any())).thenReturn(equip);
        
        EquipmentDTO result = equipmentService.create(new EquipmentDTO(equip));
        
        assertNotNull(result);
        assertTrue(result.getId().equals(1L));
        assertTrue(result.getName().equals("Equipamento stub"));
        
        // com campos obrigatórios vazios
        assertThrows(NullPointerException.class, ()->{ 
            Equipment equipEmpty = new Equipment();
            equipmentService.create(new EquipmentDTO(equipEmpty)); 
        });
    }

    @Test
    final void testUpdate() {
        // com todos os campos preenchidos
        Equipment equip = new Equipment().withId(1L).withName("Equipamento stub");
        when(equipmentRepository.save(any())).thenReturn(equip);
        
        EquipmentDTO result = equipmentService.update(new EquipmentDTO(equip));
        
        assertNotNull(result);
        assertTrue(result.getId().equals(1L));
        assertTrue(result.getName().equals("Equipamento stub"));
        
        // com campos obrigatórios vazios
        assertThrows(NullPointerException.class, ()->{ 
            Equipment equipEmpty = new Equipment();
            equipmentService.update(new EquipmentDTO(equipEmpty)); 
        });
    }

    @Test
    final void testDelete() {
        // encontrado
        assertDoesNotThrow(()->{ 
            Optional<Equipment> equip = Optional.of(new Equipment());
            when(equipmentRepository.findById(any())).thenReturn(equip);
            doNothing().when(equipmentRepository).delete(any());
            equipmentService.delete(any()); 
        });
        
        // não encontrado
        assertThrows(NoSuchElementException.class, ()->{  
            when(equipmentRepository.findById(any())).thenReturn(Optional.empty());
            when(cacheManager.getCache(any())).thenReturn(null);
            equipmentService.delete(any()); 
        });
    }

}
