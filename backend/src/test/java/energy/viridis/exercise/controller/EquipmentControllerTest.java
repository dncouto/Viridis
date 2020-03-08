package energy.viridis.exercise.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.*;

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

import energy.viridis.exercise.dto.EquipmentDTO;
import energy.viridis.exercise.model.Equipment;


@RunWith(SpringRunner.class)
@WebMvcTest(EquipmentController.class)
public class EquipmentControllerTest {

    @MockBean
    EquipmentController employeeController;
    
    @Autowired
    private MockMvc mvc;
    
    private static List<EquipmentDTO> list = new ArrayList<>();
    private String url = "/api/equipment";
    
    @BeforeAll
    public static void setUp() {
        for (int i = 0; i < 10; i++) {
            EquipmentDTO equip = new EquipmentDTO(new Equipment().withId(Long.valueOf(i)).withName("Equipamento stub"));
            list.add(equip);    
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
        ResponseEntity<List<EquipmentDTO>> response = ResponseEntity.ok().body(list);
        given(employeeController.getAll()).willReturn(response);
        
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
        ResponseEntity<EquipmentDTO> response = ResponseEntity.ok().body(list.get(0));
        given(employeeController.get(1L)).willReturn(response);
        
        mvc.perform(get(url+"/1")
                .with(user("admin").password("123").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(list.get(0).getId().intValue())));
    }

    @Test
    public void testCreate() throws Exception {
        ResponseEntity<EquipmentDTO> response = ResponseEntity.ok().body(list.get(0));
        given(employeeController.create(list.get(0))).willReturn(response);
        
        mvc.perform(post(url)
                .with(user("admin").password("123").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(list.get(0)))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(list.get(0).getId().intValue())));
    }
    
    @Test
    public void testUpate() throws Exception {
        ResponseEntity<EquipmentDTO> response = ResponseEntity.ok().body(list.get(0));
        given(employeeController.update(list.get(0))).willReturn(response);
        
        mvc.perform(put(url)
                .with(user("admin").password("123").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(list.get(0)))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(list.get(0).getId().intValue())));
    }
    
    @Test
    public void testDelete() throws Exception {
        ResponseEntity<Void> response = ResponseEntity.ok().build();
        given(employeeController.delete(1L)).willReturn(response);
        
        mvc.perform(delete(url+"/1")
                .with(user("admin").password("123").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
