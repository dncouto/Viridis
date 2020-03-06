import React, { Component } from 'react'
import SkyLight from 'react-skylight';
import 'bootstrap/dist/css/bootstrap.min.css';
import EquipmentDataService from '../../service/EquipmentDataService.js';

class EquipmentUpdateForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {id: this.props.equipment.id, name: this.props.equipment.name};
        this.handleSubmit = this.handleSubmit.bind(this);   
        this.handleChange = this.handleChange.bind(this);
        this.updateEquipment = this.updateEquipment.bind(this);
    }

    updateEquipment(equipment) {
        EquipmentDataService.updateEquipment(equipment)
            .then(() => { 
                this.props.refreshEquipments();
                alert('Equipamento alterado: '+equipment.name);
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
    
    handleSubmit(event) {
        event.preventDefault();
        var updEquipment = {id: this.state.id, name: this.state.name};
        this.updateEquipment(updEquipment);   
        this.refs.editDialog.hide();         
    }
    
    render() {
        return (
          <div>
            <SkyLight hideOnOverlayClicked ref="editDialog">
                <div className="panel panel-default">
                <div className="modal-header">Alteração de Equipamento</div>
                <div className="modal-body">
                <form className="form">
                    <div className="row">
                        <div className="col-md-2">
                            <label>Código: </label>
                        </div>
                        <div className="col-md-3">
                            <input readOnly type="text" placeholder="Código" className="form-control"  name="id" value={this.state.id}/>    
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-md-2">
                            <label>Descrição: </label>
                        </div>
                        <div className="col-md-6">       
                            <input type="text" placeholder="Descrição" className="form-control" name="name" value={this.state.name} onChange={this.handleChange}/>
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
                <button className="btn btn-primary btn-xs" onClick={() => this.refs.editDialog.show()}>Alterar</button>
            </div>
          </div>   
        );
    }
}

export default EquipmentUpdateForm
