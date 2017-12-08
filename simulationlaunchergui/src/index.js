import React from 'react';
import ReactDOM from 'react-dom';

import rest from 'rest';

class TestsTable extends React.Component {

    constructor(props) {
        super(props);
        rest('http://localhost:8080/gatling/results')
            .then(res => console.log(res.entity))
    }

    render() {
        return "<h1>DUPA JASIA</h1>";
    }

}

ReactDOM.render(
    <TestsTable>Lol</TestsTable>,
    document.getElementById('root')
);
