package energy.viridis.exercise.dto;

import java.util.Date;
import java.util.List;

import energy.viridis.exercise.model.MaintenanceOrder;
import lombok.Data;

@Data
public class MaintenanceOrderDTO {

    private Long id;
    private Long equipmentId;
    private String equipmentName;
    private Date scheduledDate;
    private List<EquipmentDTO> equipmentsAvailable;
    
    public MaintenanceOrderDTO() { }
            
    public MaintenanceOrderDTO(MaintenanceOrder entity) {
        
        this.id = entity.getId();
        this.equipmentId = entity.getEquipment().getId();
        this.equipmentName = entity.getEquipment().getName();
        this.scheduledDate = entity.getScheduledDate(); 
    }
}
