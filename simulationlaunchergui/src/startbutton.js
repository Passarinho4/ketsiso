import React from 'react';
import {Button} from 'react-bootstrap';
import {client, serverAddress} from './config';

export class StartButton extends React.Component {
    constructor(props) {
        super(props);
    }

    static startTest() {
        client({method: "POST", path: `${serverAddress}gatling/test`})
            .then(
                res => alert("Done!")
            )
    }

    render() {
        return (<Button bsStyle="success" onClick={StartButton.startTest}>Start test</Button>);
    }
}