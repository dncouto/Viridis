import React, { Component } from 'react'
import SkyLight from 'react-skylight';
import 'bootstrap/dist/css/bootstrap.min.css';
import MaintenanceOrderDataService from '../service/MaintenanceOrderDataService.js';

class MaintenanceOrderCreateForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {id: '*', equipmentId: null, scheduledDate: null};
        this.handleSubmit = this.handleSubmit.bind(this);   
        this.handleChange = this.handleChange.bind(this);
        this.createOrder = this.createOrder.bind(this);
    }

    createOrder(order) {
        MaintenanceOrderDataService.createOrder(order)
            .then(() => { 
                this.props.refreshOrders();
                alert('Nova ordem de manutenção incluída: ');
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
        var insertOrder = {equipmentId: this.state.equipmentId, scheduledDate: this.state.scheduledDate};
        this.createOrder(insertOrder);   
        this.refs.editDialog.hide();         
    }
    
    render() {
        return (
          <div>
            <SkyLight hideOnOverlayClicked ref="editDialog">
                <div className="panel panel-default">
                <div className="modal-header">Inclusão de Ordem de Manutenção</div>
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
                <button style={{float:'right'}} className="btn btn-primary btn-xs" onClick={() => this.refs.editDialog.show()}>Incluir</button>
            </div>
          </div>   
        );
    }
}

export default MaintenanceOrderCreateForm
