package energy.viridis.exercise.dto;

import energy.viridis.exercise.model.Equipment;
import lombok.Data;

@Data
public class EquipmentDTO {

    private Long id;
    private String name;
    
    public EquipmentDTO() { }
            
    public EquipmentDTO(Equipment entity) {
        
        this.id = entity.getId();
        
        this.name = entity.getName();
    }
}
