import React from 'react';
import ReactDOM from 'react-dom';

import rest from 'rest';

class TestsTable extends React.Component {

    constructor(props) {
        super(props);
        this.state = {};
        this.state.en = [];
        rest('http://localhost:8080/gatling/results')
            .then(res => {
                console.log(res.entity);
                this.setState(prev => ({
                        en: res.entity
                    }
                ))
            });
    }

    render() {
        return (<h1>Links: {this.state.en}</h1>);
    }


}

ReactDOM.render(
    <TestsTable/>,
    document.getElementById('root')
);
