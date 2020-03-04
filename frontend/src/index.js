import React, { Component } from 'react'
import ReactDOM from 'react-dom'
import {BrowserRouter as Router } from 'react-router-dom';
import './index.css'

import Routes from './Routes.js';
import Menu from './Menu.js';

class App extends Component {
  render() {
    return (
    <Router>
        <div>
            <Menu/>
            <Routes/>
        </div>
    </Router>
    );
  }
}

ReactDOM.render(<App />, document.getElementById('root'))