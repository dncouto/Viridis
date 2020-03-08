package energy.viridis.exercise.controller;

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

import energy.viridis.exercise.dto.EquipmentDTO;
import energy.viridis.exercise.service.EquipmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value="Operações do cadastro de Equipamentos")
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@RequestMapping("/api/equipment")
public class EquipmentController {

	@Autowired
	private EquipmentService equipmentService;

	
	@ApiOperation(value = "Busca todos os equipamentos cadastrados", response = EquipmentDTO.class, responseContainer="List")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Lista retornada com sucesso"),
        @ApiResponse(code = 500, message = "Ocorreu um erro na consulta, e a causa é retornada na mensagem")
	})
	@GetMapping
	public ResponseEntity<List<EquipmentDTO>> getAll() {
		return ResponseEntity.ok().body(equipmentService.getAll());
	}

	
	@ApiOperation(value = "Busca um único equipamento pelo código", response = EquipmentDTO.class)
    @ApiResponses({
        @ApiResponse(code = 200, message = "Equipamento retornado com sucesso"),
        @ApiResponse(code = 500, message = "Ocorreu um erro na consulta, e a causa é retornada na mensagem")
    })
	@GetMapping("/{id}")
	public ResponseEntity<EquipmentDTO> get(@ApiParam(value="Código do equipamento", required=true) @PathVariable("id") Long id) {
		return ResponseEntity.ok().body(equipmentService.get(id));
	}
	
	
	@ApiOperation(value = "Cria um novo equipamento", response = EquipmentDTO.class)
    @ApiResponses({
        @ApiResponse(code = 200, message = "Novo equipamento gravado com sucesso"),
        @ApiResponse(code = 500, message = "Ocorreu um erro na gravação, e a causa é retornada na mensagem")
    })
	@PostMapping
	public ResponseEntity<EquipmentDTO> create(@ApiParam(value="Dados do novo equipamento", required=true, type="json") @RequestBody EquipmentDTO equipment) {
	    return ResponseEntity.ok().body(equipmentService.create(equipment));
    }
	
	
	@ApiOperation(value = "Altera um equipamento existente", response = EquipmentDTO.class)
    @ApiResponses({
        @ApiResponse(code = 200, message = "Alterações gravadas com sucesso"),
        @ApiResponse(code = 500, message = "Ocorreu um erro na gravação, e a causa é retornada na mensagem")
    })
	@PutMapping
    public ResponseEntity<EquipmentDTO> update(@ApiParam(value="Alterações do equipamento", required=true, type="json") @RequestBody EquipmentDTO equipment) {
	    return ResponseEntity.ok().body(equipmentService.update(equipment));
    }

	
	@ApiOperation(value = "Exclui um único equipamento pelo código", response = HttpStatus.class)
    @ApiResponses({
        @ApiResponse(code = 200, message = "Equipamento excluído com sucesso"),
        @ApiResponse(code = 500, message = "Ocorreu um erro na exclusão, e a causa é retornada na mensagem")
    })
	@DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@ApiParam(value="Código do equipamento", required=true) @PathVariable("id") Long id) {
	    equipmentService.delete(id);
        return ResponseEntity.ok().build();
    }
}
