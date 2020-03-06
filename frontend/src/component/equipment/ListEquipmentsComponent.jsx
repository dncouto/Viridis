import React, { Component } from 'react'
import 'bootstrap/dist/css/bootstrap.min.css';
import EquipmentDataService from '../../service/EquipmentDataService.js';
import EquipmentUpdateForm from './EquipmentUpdateForm.jsx';
import EquipmentCreateForm from './EquipmentCreateForm.jsx';

class ListEquipmentsComponent extends Component {
    constructor(props) {
        super(props)
        this.state = {
            equipments: [],
            message: null
        }
        this.refreshEquipments = this.refreshEquipments.bind(this)
        this.deleteEquipment = this.deleteEquipment.bind(this);
    }

    componentDidMount() {
        this.refreshEquipments();
    }

    refreshEquipments() {
        EquipmentDataService.retrieveAllEquipments()
            .then(
                response => {
                    this.setState({ equipments: response.data })
                }
            )
    }
    
    deleteEquipment(equipment) {
        EquipmentDataService.deleteEquipment(equipment)
            .then(() => { 
                this.refreshEquipments();
                alert('Equipamento excluído: '+equipment.name);
            })
            .catch( err => {
                console.error(err);
                alert('Atenção! \nOcorreu o seguinte erro: '+err.message);
            });
    }  

    render() {
        return (
            <div className="container">
                <h3>Equipamentos</h3>
                <button className="btn btn-primary btn-xs" onClick={this.refreshEquipments} style={{'margin':'0px 0px 10px 15px','float':'right'}}>
                    Atualizar
                </button>
                <EquipmentCreateForm refreshEquipments={this.refreshEquipments}/>
                <div className="container">
                    <table className="table">
                        <thead>
                            <tr>
                                <th>Id</th>
                                <th>Descrição</th>
                                <th style={{width:'10%'}}></th>
                                <th style={{width:'10%'}}></th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                this.state.equipments.map(
                                    equipment =>
                                        <tr key={equipment.id}>
                                            <td>{equipment.id}</td>
                                            <td>{equipment.name}</td>
                                            <td>               
                                                <EquipmentUpdateForm refreshEquipments={this.refreshEquipments} equipment={equipment}/>
                                            </td>
                                            <td>               
                                                <button className="btn btn-danger btn-xs" onClick={() => this.deleteEquipment(equipment)}>Excluir</button>
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

export default ListEquipmentsComponent
