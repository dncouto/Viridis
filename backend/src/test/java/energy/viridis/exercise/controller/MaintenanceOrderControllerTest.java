package energy.viridis.exercise.controller;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import energy.viridis.exercise.dto.MaintenanceOrderDTO;
import energy.viridis.exercise.dto.MaintenanceOrderViewerDTO;


@RunWith(SpringRunner.class)
@WebMvcTest(MaintenanceOrderController.class)
public class MaintenanceOrderControllerTest {

    @MockBean
    MaintenanceOrderController maintenanceOrderController;
    
    @Autowired
    private MockMvc mvc;
    
    private static List<MaintenanceOrderViewerDTO> list = new ArrayList<>();
    private String url = "/api/maintenance-order";
    
    @BeforeAll
    public static void setUp() {
        
        for (int i = 0; i < 10; i++) {
            MaintenanceOrderViewerDTO order = new MaintenanceOrderViewerDTO();
            order.setId(Long.valueOf(i));
            list.add(order);    
        }
    }
    
    private String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }  
    
    @Test
    public void testGetAll() throws Exception {
        ResponseEntity<List<MaintenanceOrderViewerDTO>> response = ResponseEntity.ok().body(list);
        given(maintenanceOrderController.getAll()).willReturn(response);
        
        mvc.perform(get(url)
                .with(user("admin").password("123").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(10)))
                .andExpect(jsonPath("$[0].id", is(list.get(0).getId().intValue())));
    }

    @Test
    public void testGet() throws Exception {
        MaintenanceOrderViewerDTO order = new MaintenanceOrderViewerDTO();
        order.setId(Long.valueOf(1L));
        
        ResponseEntity<MaintenanceOrderViewerDTO> response = ResponseEntity.ok().body(order);
        given(maintenanceOrderController.get(1L)).willReturn(response);
        
        mvc.perform(get(url+"/1")
                .with(user("admin").password("123").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(order.getId().intValue())));
    }

    @Test
    public void testCreate() throws Exception {
        MaintenanceOrderDTO order = new MaintenanceOrderDTO();
        order.setId(Long.valueOf(1L));
        
        ResponseEntity<MaintenanceOrderDTO> response = ResponseEntity.ok().body(order);
        given(maintenanceOrderController.create(order)).willReturn(response);
        
        mvc.perform(post(url)
                .with(user("admin").password("123").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(order))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(order.getId().intValue())));
    }
    
    @Test
    public void testUpate() throws Exception {
        MaintenanceOrderDTO order = new MaintenanceOrderDTO();
        order.setId(Long.valueOf(1L));
        
        ResponseEntity<MaintenanceOrderDTO> response = ResponseEntity.ok().body(order);
        given(maintenanceOrderController.update(order)).willReturn(response);
        
        mvc.perform(put(url)
                .with(user("admin").password("123").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(order))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(order.getId().intValue())));
    }
    
    @Test
    public void testDelete() throws Exception {
        ResponseEntity<Void> response = ResponseEntity.ok().build();
        given(maintenanceOrderController.delete(1L)).willReturn(response);
        
        mvc.perform(delete(url+"/1")
                .with(user("admin").password("123").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
