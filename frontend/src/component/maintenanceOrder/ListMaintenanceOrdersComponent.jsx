import React, { Component } from 'react'
import 'bootstrap/dist/css/bootstrap.min.css';
import MaintenanceOrderDataService from '../../service/MaintenanceOrderDataService.js';
import EquipmentDataService from '../../service/EquipmentDataService.js';
import MaintenanceOrderUpdateForm from './MaintenanceOrderUpdateForm.jsx';
import MaintenanceOrderCreateForm from './MaintenanceOrderCreateForm.jsx';

class ListMaintenanceOrdersComponent extends Component {
    constructor(props) {
        super(props)
        this.state = {
            orders: [],
            equipmentsAvailable: [],
            message: null
        }
        this.refreshOrders = this.refreshOrders.bind(this)
        this.deleteOrder = this.deleteOrder.bind(this);
    }

    componentDidMount() {
        this.refreshOrders();
        EquipmentDataService.retrieveAllEquipments()
            .then(
                response => {
                    var options = [];
                    response.data.forEach(equipment => {
                        options.push({value: equipment.id, label: equipment.name});
                    });
                    this.setState({ equipmentsAvailable: options });
                }
            )
    }

    refreshOrders() {
        MaintenanceOrderDataService.retrieveAllOrders()
            .then(
                response => {
                    this.setState({ orders: response.data })
                }
            )
    }
    
    deleteOrder(order) {
        MaintenanceOrderDataService.deleteOrder(order)
            .then(() => { 
                this.refreshOrders();
                alert('Ordem de manutenção excluída: '+order.id);
            })
            .catch( err => {
                console.error(err);
                alert('Atenção! \n\nHouve um problema conforme detalhes abaixo: \n\n'+err.response.data);
                this.refreshOrders();
            });
    }  

    render() {
        return (
            <div className="container">
                <h3>Ordens de Manutenção</h3>
                <button className="btn btn-primary btn-xs" onClick={this.refreshOrders} style={{'margin':'0px 0px 10px 15px','float':'right'}}>
                    Atualizar
                </button>
                <MaintenanceOrderCreateForm refreshOrders={this.refreshOrders}/>
                <div className="container">
                    <table className="table">
                        <thead>
                            <tr>
                                <th>Id</th>
                                <th>Equipamento</th>
                                <th>Programada para</th>
                                <th style={{width:'10%'}}></th>
                                <th style={{width:'10%'}}></th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                this.state.orders.map(
                                    order =>
                                        <tr key={order.id}>
                                            <td>{order.id}</td>
                                            <td>{order.equipmentName}</td>
                                            <td>{order.scheduledDateFormated}</td>
                                            <td>               
                                                <MaintenanceOrderUpdateForm refreshOrders={this.refreshOrders} equipmentsAvailable={this.state.equipmentsAvailable} order={order}/>      
                                            </td>
                                            <td>               
                                                <button className="btn btn-danger btn-xs" onClick={() => this.deleteOrder(order)}>Excluir</button>
                                            </td>
                                        </tr>
                                )
                            }
                        </tbody>
                    </table>
                </div>
            </div>
        )
    }
}

export default ListMaintenanceOrdersComponent
