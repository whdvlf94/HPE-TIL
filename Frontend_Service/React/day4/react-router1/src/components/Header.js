import React from 'react';
import { Link, NavLink } from 'react-router-dom';
import './Header.css'

const Header = () => {
    return (
        <div className="Header">

            <NavLink to="/Blog" className="item">Blog</NavLink>
            <NavLink to="/youtube" className="item">Youtube</NavLink>
            <NavLink to="/Weather" className="item">Weather</NavLink>
            <NavLink to="/Bitcoin" className="item">Bitcoin</NavLink>
            {/* <NavLink to="/myprofile" className="item">Myprofile</NavLink>
            <NavLink to="/login" className="item">Login</NavLink> */}


        </div>
    );
};

export default Header;