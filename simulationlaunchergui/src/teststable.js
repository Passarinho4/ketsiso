import React from 'react';
import {client, serverAddress} from './config';
import {Button} from 'react-bootstrap';

export class TestsTable extends React.Component {
    constructor(props) {
        super(props);
        this.state = {};
        this.state.en = [];
    }

    static remove(id) {
        client({path: `${serverAddress}gatling/results/${id}`, method: "DELETE"})
            .then(res => render())
    }

    render() {
        client({path: `${serverAddress}gatling/results`})
            .then(res => {
                this.setState(prev => ({
                        en: res.entity
                    }
                ));
            });
        let links = this.state.en.map(id => {
            return <p key={id}>
                <a href={serverAddress + "results/" + id + "/index.html"}>{id}</a>
                <Button bsStyle="danger" bsSize="xsmall" onClick={t => TestsTable.remove(id)}>Remove</Button>
            </p>
        });
        return (<h2>Links: {links}</h2>);
    }
}