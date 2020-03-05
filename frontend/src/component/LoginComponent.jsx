import React, { Component } from 'react'
import AuthenticationService from '../service/AuthenticationService';

class LoginComponent extends Component {

    constructor(props) {
        super(props)

        this.state = {
            username: 'admin',
            password: '',
            hasLoginFailed: false,
            showSuccessMessage: false
        }

        this.handleChange = this.handleChange.bind(this)
        this.loginClicked = this.loginClicked.bind(this)
    }

    handleChange(event) {
        this.setState(
            {
                [event.target.name]
                    : event.target.value
            }
        )
    }

    loginClicked() {
        
        AuthenticationService
            .executeBasicAuthenticationService(this.state.username, this.state.password)
            .then(() => {
                AuthenticationService.registerSuccessfulLogin(this.state.username, this.state.password)
                this.props.history.push(`/home`)
            }).catch(() => {
                this.setState({ showSuccessMessage: false })
                this.setState({ hasLoginFailed: true })
            })

    }

    render() {
        return (
            <div>
                <h1 style={{width:'100%',textAlign:'center'}}>Login</h1>
                <br/>
                <div className="container">
                    <div className="row">
                        <div className="col-md-3"></div>
                        <div className="col-md-6">
                            {/*<ShowInvalidCredentials hasLoginFailed={this.state.hasLoginFailed}/>*/}
                            {this.state.hasLoginFailed && <div className="alert alert-warning">Usuário ou Senha inválidos!</div>}
                            {/*<ShowLoginSuccessMessage showSuccessMessage={this.state.showSuccessMessage}/>*/}
                            <div className="row">
                                <div className="col-md-2"></div>
                                <div className="col-md-2">
                                    Usuário: 
                                </div>
                                <div className="col-md-4">
                                    <input type="text" name="username" value={this.state.username} onChange={this.handleChange} />
                                </div>
                            </div>
                            <br/>
                            <div className="row">
                                <div className="col-md-2"></div>
                                <div className="col-md-2">
                                    Senha: 
                                </div>
                                <div className="col-md-4">
                                    <input type="password" name="password" value={this.state.password} onChange={this.handleChange} />
                                </div>
                            </div>
                            <br/>
                            <div className="row">
                                <div className="col-md-6" style={{textAlign:'right'}}>
                                    <button className="btn btn-success" onClick={this.loginClicked}>Login</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default LoginComponent