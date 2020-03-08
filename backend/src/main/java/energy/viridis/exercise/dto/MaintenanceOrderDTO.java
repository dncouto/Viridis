package energy.viridis.exercise.dto;

import java.text.SimpleDateFormat;

import energy.viridis.exercise.model.MaintenanceOrder;
import lombok.Data;

@Data
public class MaintenanceOrderDTO {

    private Long id;
    private Long equipmentId;
    private String scheduledDate;
    
    public MaintenanceOrderDTO() { }
            
    public MaintenanceOrderDTO(MaintenanceOrder entity) {
        
        this.id = entity.getId();
        this.equipmentId = (entity.getEquipment() == null ? null : entity.getEquipment().getId());
        this.scheduledDate = (entity.getScheduledDate() == null ? null : new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(entity.getScheduledDate()));
    }
}
