import React, { Component } from 'react'
import SkyLight from 'react-skylight';
import 'bootstrap/dist/css/bootstrap.min.css';
import EquipmentDataService from '../service/EquipmentDataService.js';

class ListEquipmentsComponent extends Component {
    constructor(props) {
        super(props)
        this.state = {
            equipments: [],
            message: null
        }
        this.refreshEquipments = this.refreshEquipments.bind(this)
        this.deleteEquipment = this.deleteEquipment.bind(this);
        //this.createStudent = this.createStudent.bind(this);
        //this.updateStudent = this.updateStudent.bind(this);
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
    
    updateEquipment(equipment) {
        alert('teste');
        /*
        fetch(student.link, 
        {   method: 'PUT', 
            credentials: 'same-origin',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify(student)
        })
        .then( 
            res => this.loadStudentsFromServer()
        )
        .catch( err => console.error(err))
        */
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
            })
    }  

    render() {
        return (
            <div className="container">
                <h3>Equipamentos</h3>
                <button className="btn btn-primary btn-xs" onClick={this.refreshEquipments} style={{'margin':'0px 0px 10px 15px','float':'right'}}>
                    Atualizar
                </button>
                <div className="container">
                    <table className="table">
                        <thead>
                            <tr>
                                <th>Id</th>
                                <th>Descrição</th>
                                <th></th>
                                <th></th>
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
                                                <EquipmentUpdateForm updateEquipment={this.props.updateEquipment} equipment={equipment}/>          
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

class EquipmentUpdateForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {id: this.props.equipment.id, name: this.props.equipment.name};
        this.handleSubmit = this.handleSubmit.bind(this);   
        this.handleChange = this.handleChange.bind(this);     
    }

    handleChange(event) {
        this.setState(
            {[event.target.name]: event.target.value}
        );
    }    
    
    handleSubmit(event) {
        event.preventDefault();
        var updStudent = {link: this.props.student._links.self.href, id: this.state.id, name: this.state.name};
        this.props.updateStudent(updStudent);   
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
                        <div className="col-md-4">
                            <input readOnly type="text" placeholder="Código" className="form-control"  name="id" value={this.state.id} onChange={this.handleChange}/>    
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-md-2">
                            <label>Descrição: </label>
                        </div>
                        <div className="col-md-4">       
                            <input type="text" placeholder="Descrição" className="form-control" name="name" value={this.state.name} onChange={this.handleChange}/>
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-md-2"></div>
                        <div className="col-md-2">
                            <button className="btn btn-primary" onClick={this.handleSubmit}>Save</button>   
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

export default ListEquipmentsComponent
