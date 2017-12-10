import React from 'react';
import ReactDOM from 'react-dom';
import {TestsTable} from "./teststable"
import {StartButton} from "./startbutton"

ReactDOM.render(
    <div>
        <StartButton/>
        <TestsTable/>
    </div>,
    document.getElementById('root')
);

