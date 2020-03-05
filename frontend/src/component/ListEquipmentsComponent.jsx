import React, { Component } from 'react'
import EquipmentDataService from '../service/EquipmentDataService.js';

class ListEquipmentsComponent extends Component {
    constructor(props) {
        super(props)
        this.state = {
            equipments: [],
            message: null
        }
        this.refreshEquipments = this.refreshEquipments.bind(this)
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
    
    render() {
        return (
            <div className="container">
                <h3>Equipamentos</h3>
                <button onClick={this.refreshEquipments} style={{'margin':'0px 0px 10px 15px'}}>
                    Atualizar
                </button>
                <div className="container">
                    <table className="table">
                        <thead>
                            <tr>
                                <th>Id</th>
                                <th>Descrição</th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                this.state.equipments.map(
                                    equipment =>
                                        <tr key={equipment.id}>
                                            <td>{equipment.id}</td>
                                            <td>{equipment.name}</td>
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
