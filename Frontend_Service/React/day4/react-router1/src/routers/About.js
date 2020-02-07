import React from 'react';

const About = ({match}) => {
    return (
        <div>
            {match.params.userid}'s Profiles
        </div>
    );
};

export default About;