import './App.css';
import {Component} from "react";
import { Switch, Route } from 'react-router-dom';
import Navbar from "./navbar/Navbar";
import OfficesPage from "./offices/offices";

class App extends Component {

    render() {
        return (
            <div>
                <Navbar/>
                <Switch>
                    <Route path="/offices" component={OfficesPage} />
                </Switch>

            </div>
        );
    }
}

export default App;

