package energy.viridis.exercise.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import energy.viridis.exercise.dto.MaintenanceOrderDTO;
import energy.viridis.exercise.dto.MaintenanceOrderViewerDTO;
import energy.viridis.exercise.service.MaintenanceOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value="Operações do cadastro de Ordens de Manutenção")
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@RequestMapping("/api/maintenance-order")
public class MaintenanceOrderController {

	@Autowired
	private MaintenanceOrderService maintenanceOrderService;

	
	@ApiOperation(value = "Busca todos as ordens de manutenções cadastradas", response = MaintenanceOrderViewerDTO.class, responseContainer="List")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Lista retornada com sucesso"),
        @ApiResponse(code = 500, message = "Ocorreu um erro na consulta, e a causa é retornada na mensagem")
    })
	@GetMapping
	public ResponseEntity<List<MaintenanceOrderViewerDTO>> getAll() {
		return ResponseEntity.ok().body(maintenanceOrderService.getAll());
	}

	
	@ApiOperation(value = "Busca uma única ordem de manutenção pelo código", response = MaintenanceOrderViewerDTO.class)
    @ApiResponses({
        @ApiResponse(code = 200, message = "Equipamento retornado com sucesso"),
        @ApiResponse(code = 500, message = "Ocorreu um erro na consulta, e a causa é retornada na mensagem")
    })
	@GetMapping("/{id}")
	public ResponseEntity<MaintenanceOrderViewerDTO> get(@ApiParam(value="Código da ordem de manutenção", required=true) @PathVariable("id") Long id) {
		return ResponseEntity.ok().body(maintenanceOrderService.get(id));
	}
	
	
	@ApiOperation(value = "Cria uma nova ordem de manutenção", response = MaintenanceOrderDTO.class)
    @ApiResponses({
        @ApiResponse(code = 200, message = "Nova ordem de manutenção gravada com sucesso"),
        @ApiResponse(code = 500, message = "Ocorreu um erro na gravação, e a causa é retornada na mensagem")
    })
	@PostMapping
    public ResponseEntity<MaintenanceOrderDTO> create(@ApiParam(value="Dados do nova ordem de manutenção", required=true, type="json") @RequestBody MaintenanceOrderDTO order) throws ParseException {
	    return ResponseEntity.ok().body(maintenanceOrderService.create(order));
    }
    
	
	@ApiOperation(value = "Altera uma ordem de manutenção existente", response = MaintenanceOrderDTO.class)
    @ApiResponses({
        @ApiResponse(code = 200, message = "Alterações gravadas com sucesso"),
        @ApiResponse(code = 500, message = "Ocorreu um erro na gravação, e a causa é retornada na mensagem")
    })
    @PutMapping
    public ResponseEntity<MaintenanceOrderDTO> update(@ApiParam(value="Alterações da ordem de manutenção", required=true, type="json") @RequestBody MaintenanceOrderDTO order) throws ParseException {
        return ResponseEntity.ok().body(maintenanceOrderService.update(order));
    }

	
	@ApiOperation(value = "Exclui uma ordem de manutenção pelo código", response = HttpStatus.class)
    @ApiResponses({
        @ApiResponse(code = 200, message = "Ordem de manutenção excluída com sucesso"),
        @ApiResponse(code = 500, message = "Ocorreu um erro na exclusão, e a causa é retornada na mensagem")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@ApiParam(value="Código da ordem de manutenção", required=true) @PathVariable("id") Long id) {
        maintenanceOrderService.delete(id);
        return ResponseEntity.ok().build();
    }
}
