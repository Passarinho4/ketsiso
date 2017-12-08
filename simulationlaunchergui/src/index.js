import React from 'react';
import ReactDOM from 'react-dom';
import {Button} from 'react-bootstrap';


function f() {
    const elem = new Date().getMilliseconds();
    ReactDOM.render(
        <h1>{elem}</h1>,
        document.getElementById('root')
    );
}

ReactDOM.render(
    <Button bsStyle="success" bsSize="small" onClick={function () {
        alert("ok")
    }}>
        Something
    </Button>,
    document.getElementById('root')
)

//setInterval(f, 10);