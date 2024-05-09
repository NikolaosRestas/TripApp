import './App.css';
import {Component} from "react";
import { Switch, Route } from 'react-router-dom';
import Navbar from "./navbar/Navbar";
import OfficesPage from "./offices/offices";
import CustomersPage from "./customers/customers";
import StaffsPage from "./staffs/staffs";
import ProgramsPage from "./programs/programs";

class App extends Component {

    render() {
        return (
            <div>
                <Navbar/>
                <Switch>
                    <Route path="/offices" component={OfficesPage} />
                    <Route path="/customers" component={CustomersPage} />
                    <Route path="/staffs" component={StaffsPage} />
                    <Route path="/programs" component={ProgramsPage} />
                </Switch>

            </div>
        );
    }
}

export default App;

