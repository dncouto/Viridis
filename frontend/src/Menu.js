import React, {Component} from 'react';
import {Link} from 'react-router-dom';

const links = [
    { route: "/", label: "Home"},
    { route: "/equipment", label: "Equipamentos"},
    { route: "/about", label: "Sobre"},
    { route: "/xxx", label: "Demo", toolTip: "Link com erro intencional para demonstrar tratamento"}    
];

export class Menu extends Component {

    renderLink = () => {
        return links.map( link => 
            <Link key={link.route} className="nav-link" to={link.route} title={link.toolTip}>
                {link.label}
            </Link>
        )
    }
    
    render() {
        return (
            <nav className="navbar navbar-expand-lg navbar-light bg-light">
                <div className="collapse navbar-collapse" id="navbarNav">
                    <ul className="navbar-nav">
                        { this.renderLink() }
                    </ul>
                </div>
            </nav>
        )
    }
};

export default Menu;