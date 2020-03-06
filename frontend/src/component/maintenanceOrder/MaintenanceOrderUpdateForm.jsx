import React, { Component } from 'react'
import Select from 'react-select';
import SkyLight from 'react-skylight';
import 'bootstrap/dist/css/bootstrap.min.css';
import MaintenanceOrderDataService from '../../service/MaintenanceOrderDataService.js';
import EquipmentDataService from '../../service/EquipmentDataService.js';

class MaintenanceOrderUpdateForm extends React.Component {
    constructor(props) {
        debugger;
        super(props);
        this.state = {id: this.props.order.id, equipmentId: this.props.order.equipmentId, scheduledDate: this.props.order.scheduledDate};
        this.equipmentsAvailable = [];
        this.handleSubmit = this.handleSubmit.bind(this);   
        this.handleChange = this.handleChange.bind(this);
        this.handleSelectChange = this.handleSelectChange.bind(this);
        this.updateOrder = this.updateOrder.bind(this);
    }

    componentDidMount() {
        EquipmentDataService.retrieveAllEquipments()
            .then(
                response => {
                    response.data.forEach(equipment => {
                        this.equipmentsAvailable.push({value: equipment.id, label: equipment.name});
                    });
                }
            )
    }

    updateOrder(order) {
        MaintenanceOrderDataService.updateOrder(order)
            .then(() => { 
                this.props.refreshOrders();
                alert('Ordem de manutenção alterada! Código: '+order.id);
            })
            .catch( err => {
                console.error(err);
                alert('Atenção! \nOcorreu o seguinte erro: '+err.message);
            });
    }
    
    handleChange(event) {
        this.setState(
            {[event.target.name]: event.target.value}
        );
    }    

    handleSelectChange = (newValue) => {
        this.setState({ equipmentId : newValue.value });
    };
    
    handleSubmit(event) {
        event.preventDefault();
        var updOrder = {id: this.state.id, equipmentId: this.state.equipmentId, scheduledDate: this.state.scheduledDate};
        this.updateOrder(updOrder);   
        this.refs.editDialog.hide();         
    }
    
    render() {
        return (
          <div>
            <SkyLight hideOnOverlayClicked ref="editDialog">
                <div className="panel panel-default">
                <div className="modal-header">Alteração de Ordem de Manutenção</div>
                <div className="modal-body">
                <form className="form">
                    <div className="row">
                        <div className="col-md-2">
                            <label>Código: </label>
                        </div>
                        <div className="col-md-3">
                            <input readOnly type="text" placeholder="Código" className="form-control" name="id" value={this.state.id}/>
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-md-2">
                            <label>Equipamento: </label>
                        </div>
                        <div className="col-md-8">
                            <Select placeholder="Selecione o Equipamento" name="equipmentId" 
                                    value={this.equipmentsAvailable.filter(option => option.value === this.state.equipmentId)}
                                    options={this.equipmentsAvailable} 
                                    onChange={this.handleSelectChange} />
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-md-2">
                            <label>Quando: </label>
                        </div>
                        <div className="col-md-5">
                            <input type="datetime-local" placeholder="Data/Hora" className="form-control" name="scheduledDate" value={this.state.scheduledDate} onChange={this.handleChange}/>
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-md-2"></div>
                        <div className="col-md-2">
                            <button className="btn btn-primary" onClick={this.handleSubmit}>Gravar</button>   
                        </div>     
                    </div>  
                </form>
                </div>      
                </div>
            </SkyLight>
            <div>
                <button style={{float:'right'}} className="btn btn-primary btn-xs" onClick={() => this.refs.editDialog.show()}>Alterar</button>
            </div>
          </div>   
        );
    }
}

export default MaintenanceOrderUpdateForm
