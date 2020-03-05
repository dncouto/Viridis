import React, { Component } from 'react'
import 'bootstrap/dist/css/bootstrap.min.css';
import MaintenanceOrderDataService from '../service/MaintenanceOrderDataService.js';
//import EquipmentUpdateForm from './EquipmentUpdateForm.jsx';
//import EquipmentCreateForm from './EquipmentCreateForm.jsx';

class ListMaintenanceOrdersComponent extends Component {
    constructor(props) {
        super(props)
        this.state = {
            orders: [],
            message: null
        }
        this.refreshOrders = this.refreshOrders.bind(this)
        this.deleteOrder = this.deleteOrder.bind(this);
    }

    componentDidMount() {
        this.refreshOrders();
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
                alert('Atenção! \nOcorreu o seguinte erro: '+err.message);
            });
    }  

    render() {
        return (
            <div className="container">
                <h3>Ordens de Manutenção</h3>
                <button className="btn btn-primary btn-xs" onClick={this.refreshOrders} style={{'margin':'0px 0px 10px 15px','float':'right'}}>
                    Atualizar
                </button>
                
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
                                            <td>{order.scheduledDate}</td>
                                            <td>               
                                                
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
