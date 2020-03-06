package energy.viridis.exercise.dto;

import java.text.SimpleDateFormat;

import energy.viridis.exercise.model.MaintenanceOrder;
import lombok.Data;

@Data
public class MaintenanceOrderDTO {

    private Long id;
    private Long equipmentId;
    private String equipmentName;
    private String scheduledDate;
    
    public MaintenanceOrderDTO() { }
            
    public MaintenanceOrderDTO(MaintenanceOrder entity) {
        
        this.id = entity.getId();
        this.equipmentId = entity.getEquipment().getId();
        this.equipmentName = entity.getEquipment().getName();
        this.scheduledDate = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(entity.getScheduledDate()); 
    }
}
