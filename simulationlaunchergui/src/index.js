import React from 'react';
import ReactDOM from 'react-dom';
import {Button} from 'react-bootstrap';
import {client, serverAddress} from './config';


class TestsTable extends React.Component {
    constructor(props) {
        super(props);
        this.state = {};
        this.state.en = [];

        client({path: `${serverAddress}gatling/results`})
            .then(res => {
                this.setState(prev => ({
                        en: res.entity
                    }
                ));
            });
    }

    render() {
        let links = this.state.en.map(id => {
            return <p key={id}><a href={serverAddress + "results/" + id + "/index.html"}>{id}</a></p>
        });
        return (<h2>Links: {links}</h2>);
    }
}

class StartButton extends React.Component {
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

ReactDOM.render(
    <div>
        <StartButton/>
        <TestsTable/>
    </div>,
    document.getElementById('root')
);

