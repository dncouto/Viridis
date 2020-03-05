import React, { Component } from 'react';
import ListEquipmentsComponent from './ListEquipmentsComponent';
import ListMaintenanceOrdersComponent from './ListMaintenanceOrdersComponent';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'
import LoginComponent from './LoginComponent';
import LogoutComponent from './LogoutComponent';
import MenuComponent from './MenuComponent';
import AuthenticatedRoute from './AuthenticatedRoute';
import Home from './Home';
import About from './About';

class InstructorApp extends Component {


    render() {
        return (
            <>
                <Router>
                    <>
                        <MenuComponent />
                        <Switch>
                            <Route path="/" exact component={LoginComponent} />
                            <Route path="/login" exact component={LoginComponent} />
                            <Route path='/home' component={Home}/>
                            <Route path='/about' component={About}/>
                            <AuthenticatedRoute path="/logout" exact component={LogoutComponent} />
                            <AuthenticatedRoute path="/equipments" exact component={ListEquipmentsComponent} />
                            <AuthenticatedRoute path="/orders" exact component={ListMaintenanceOrdersComponent} />
                        </Switch>
                    </>
                </Router>
            </>
        )
    }
}

export default InstructorApp