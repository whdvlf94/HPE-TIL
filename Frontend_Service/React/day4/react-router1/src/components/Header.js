import React from 'react';
import { Link, NavLink } from 'react-router-dom';
import './Header.css'

const Header = () => {
    return (
        <div className="Header">

            <NavLink exact to="/" className="item">Home</NavLink>
            <NavLink to="/about/whdvlf" className="item">About</NavLink>
            <NavLink to="/posts" className="item">Posts</NavLink>
            <NavLink to="/search" className="item">Search</NavLink>
            <NavLink to="/myprofile" className="item">Myprofile</NavLink>
            <NavLink to="/login" className="item">Login</NavLink>


        </div>
    );
};

export default Header;