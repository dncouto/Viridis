package energy.viridis.exercise.dto;

import java.text.SimpleDateFormat;

import energy.viridis.exercise.model.MaintenanceOrder;
import lombok.Data;

@Data
public class MaintenanceOrderViewerDTO {

    private Long id;
    private Long equipmentId;
    private String equipmentName;
    private String scheduledDate;
    private String scheduledDateFormated;
    
    public MaintenanceOrderViewerDTO() { }
            
    public MaintenanceOrderViewerDTO(MaintenanceOrder entity) {
        
        this.id = entity.getId();
        this.equipmentId = entity.getEquipment().getId();
        this.equipmentName = entity.getEquipment().getName();
        this.scheduledDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(entity.getScheduledDate());
        this.scheduledDateFormated = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(entity.getScheduledDate()); 
    }
}
