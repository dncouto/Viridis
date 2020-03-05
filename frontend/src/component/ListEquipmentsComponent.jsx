import React, { Component } from 'react'
import SkyLight from 'react-skylight';
import SkyLightStateless from 'react-skylight';
import EquipmentDataService from '../service/EquipmentDataService.js';

class ListEquipmentsComponent extends Component {
    constructor(props) {
        super(props)
        this.state = {
            equipments: [],
            message: null
        }
        this.refreshEquipments = this.refreshEquipments.bind(this)
        this.deleteStudent = this.deleteStudent.bind(this);
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
    
    updateStudent(equipment) {
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
      
    deleteStudent(equipment) {
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
                <button onClick={this.refreshEquipments} style={{'margin':'0px 0px 10px 15px'}}>
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
                                                <StudentUpdateForm updateStudent={this.props.updateStudent} equipment={equipment}/>          
                                            </td>
                                            <td>               
                                                <button className="btn btn-danger btn-xs" onClick={() => this.deleteStudent(equipment)}>Excluir</button>
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

class StudentUpdateForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {name: this.props.student.name, lastname: this.props.student.lastname, email: this.props.student.email};
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
        var updStudent = {link: this.props.student._links.self.href ,firstname: this.state.firstname, lastname: this.state.lastname, email: this.state.email};
        this.props.updateStudent(updStudent);   
        this.refs.editDialog.hide();         
    }
    
    render() {
        return (
          <div>
            <SkyLight hideOnOverlayClicked ref="editDialog">
                <div className="panel panel-default">
                <div className="panel-heading">Edit student</div>
                <div className="panel-body">
                <form className="form">
                    <div className="col-md-4">
                        <input type="text" placeholder="Firstname" className="form-control"  name="firstname" value={this.state.firstname} onChange={this.handleChange}/>    
                    </div>
                    <div className="col-md-4">       
                        <input type="text" placeholder="Lastname" className="form-control" name="lastname" value={this.state.lastname} onChange={this.handleChange}/>
                    </div>
                    <div className="col-md-4">
                        <input type="text" placeholder="Email" className="form-control" name="email" value={this.state.email} onChange={this.handleChange}/>
                    </div>
                    <div className="col-md-2">
                        <button className="btn btn-primary" onClick={this.handleSubmit}>Save</button>   
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
