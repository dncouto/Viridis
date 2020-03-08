package energy.viridis.exercise.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import energy.viridis.exercise.dto.MaintenanceOrderDTO;
import energy.viridis.exercise.dto.MaintenanceOrderViewerDTO;
import energy.viridis.exercise.model.Equipment;
import energy.viridis.exercise.model.MaintenanceOrder;
import energy.viridis.exercise.repository.MaintenanceOrderRepository;
import energy.viridis.exercise.service.impl.MaintenanceOrderServiceImpl;


@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
class MaintenanceOrderServiceTest {

    @InjectMocks
    private MaintenanceOrderService maintenanceOrderService = new MaintenanceOrderServiceImpl();
    
    @Mock
    private MaintenanceOrderRepository maintenanceOrderRepository;
    @Mock
    private CacheManager cacheManager;
        
    
    @BeforeAll
    public static void setUp() {
        
    }
    
    @Test
    final void testGet() {
        // encontrado
        MaintenanceOrder entity = new MaintenanceOrder();
        entity.setId(1L);
        Equipment equip = new Equipment().withId(1L).withName("Equipamento stub");
        entity.setEquipment(equip);
        Calendar now = Calendar.getInstance();
        entity.setScheduledDate(now.getTime());
        Optional<MaintenanceOrder> order = Optional.of(entity);
        when(maintenanceOrderRepository.findById(any())).thenReturn(order);
        
        MaintenanceOrderViewerDTO result = maintenanceOrderService.get(any());
        
        assertNotNull(result);
        assertTrue(result.getId().equals(1L));
        assertTrue(result.getEquipmentId().equals(equip.getId()));
        assertTrue(result.getEquipmentName().equals(equip.getName()));
        assertTrue(result.getScheduledDate().equals(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(now.getTime())));
        assertTrue(result.getScheduledDateFormated().equals(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(now.getTime())));
        
        // não encontrado
        when(maintenanceOrderRepository.findById(any())).thenReturn(Optional.empty());
        
        assertThrows(NoSuchElementException.class, ()->{ maintenanceOrderService.get(any()); });
    }

    @Test
    final void testGetAll() {
        List<MaintenanceOrder> list = new ArrayList<>();
        when(maintenanceOrderRepository.findAll()).thenReturn(list);
        for (int i = 1; i <= 10; i++) {
            MaintenanceOrder entity = new MaintenanceOrder();
            entity.setId(Long.valueOf(i));
            Equipment equip = new Equipment().withId(1L).withName("Equipamento stub");
            entity.setEquipment(equip);
            Calendar now = Calendar.getInstance();
            entity.setScheduledDate(now.getTime());
            list.add(entity);    
        }
        
        List<MaintenanceOrderViewerDTO> result = maintenanceOrderService.getAll();
        
        assertFalse(result.isEmpty());
        assertTrue(result.size() == 10);
        assertTrue(result.get(1).getId().equals(2L));
        
        // resultado vazio
        list.clear();
        when(maintenanceOrderRepository.findAll()).thenReturn(list);
        
        result = maintenanceOrderService.getAll();
        
        assertTrue(result.isEmpty());
    }

    @Test
    final void testCreate() throws ParseException {
        // com todos os campos preenchidos
        MaintenanceOrder order = new MaintenanceOrder();
        order.setId(1L);
        Equipment equip = new Equipment().withId(1L).withName("Equipamento stub");
        order.setEquipment(equip);
        Calendar now = Calendar.getInstance();
        order.setScheduledDate(now.getTime());
        when(maintenanceOrderRepository.save(any())).thenReturn(order);
        
        MaintenanceOrderDTO result = maintenanceOrderService.create(new MaintenanceOrderDTO(order));
        
        assertNotNull(result);
        assertTrue(result.getId().equals(1L));
        assertTrue(result.getEquipmentId().equals(equip.getId()));
        assertTrue(result.getScheduledDate().equals(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(now.getTime())));
        
        // com campos obrigatórios vazios
        assertThrows(NullPointerException.class, ()->{ 
            MaintenanceOrder orderEmpty = new MaintenanceOrder();
            maintenanceOrderService.create(new MaintenanceOrderDTO(orderEmpty)); 
        });
    }

    @Test
    final void testUpdate() throws ParseException {
        // com todos os campos preenchidos
        MaintenanceOrder order = new MaintenanceOrder();
        order.setId(1L);
        Equipment equip = new Equipment().withId(1L).withName("Equipamento stub");
        order.setEquipment(equip);
        Calendar now = Calendar.getInstance();
        order.setScheduledDate(now.getTime());
        Optional<MaintenanceOrder> orderFinded = Optional.of(order);
        when(maintenanceOrderRepository.findById(any())).thenReturn(orderFinded);
        when(maintenanceOrderRepository.save(any())).thenReturn(order);
        
        MaintenanceOrderDTO result = maintenanceOrderService.update(new MaintenanceOrderDTO(order));
        
        assertNotNull(result);
        assertTrue(result.getId().equals(1L));
        assertTrue(result.getEquipmentId().equals(equip.getId()));
        assertTrue(result.getScheduledDate().equals(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(now.getTime())));
        
        // com campos obrigatórios vazios
        assertThrows(NullPointerException.class, ()->{ 
            MaintenanceOrder orderEmpty = new MaintenanceOrder();
            maintenanceOrderService.update(new MaintenanceOrderDTO(orderEmpty)); 
        });
        
        // ID não encontrado
        assertThrows(NoSuchElementException.class, ()->{  
            when(maintenanceOrderRepository.findById(any())).thenReturn(Optional.empty());
            when(cacheManager.getCache(any())).thenReturn(null);
            maintenanceOrderService.update(new MaintenanceOrderDTO(order)); 
        });
    }

    @Test
    final void testDelete() {
        // encontrado
        assertDoesNotThrow(()->{ 
            Optional<MaintenanceOrder> order = Optional.of(new MaintenanceOrder());
            when(maintenanceOrderRepository.findById(any())).thenReturn(order);
            doNothing().when(maintenanceOrderRepository).delete(any());
            maintenanceOrderService.delete(any()); 
        });
        
        // não encontrado
        assertThrows(NoSuchElementException.class, ()->{  
            when(maintenanceOrderRepository.findById(any())).thenReturn(Optional.empty());
            when(cacheManager.getCache(any())).thenReturn(null);
            maintenanceOrderService.delete(any()); 
        });
    }

}
