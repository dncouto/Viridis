import React, {Component} from 'react';
import {
    Route, Switch
} from 'react-router-dom';

import About from './components/About';
import Home from './components/Home';
import Equipment from './components/Equipment';
import NoMatch from './components/NoMatch';

export class Routes extends Component {
    render() {
        return (
            <main className="container">
            <Switch>
                <Route exact path='/' component={Home}/>
                <Route path='/about' component={About}/>
                <Route path='/equipment' component={Equipment}/>
                <Route component={NoMatch}/>
                </Switch>
            </main>
        )
    }
};

export default Routes;