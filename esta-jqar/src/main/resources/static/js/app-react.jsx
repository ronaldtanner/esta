'use strict';

import React from 'react';
import ReactDOM from 'react-dom';
import $ from 'jquery';
import DatePicker from "react-bootstrap-date-picker";


class Student extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            display: true
        }

        this.onClick = this.onClick.bind(this)
    }

    onClick() {
        this.props.handleEditClick(this.props.student);
    }

    render() {
        let date = (this.props.student.birthdate != null) ? new Date(this.props.student.birthdate).toDateString() : "";
        return(
                <tr>
                <td>{this.props.student.firstname}</td>
                <td>{this.props.student.lastname}</td>
                <td>{date}</td>
                <td><a href='#' onClick={this.onClick}>Edit</a></td>
                </tr>
        );
    }

}

class StudentTable extends React.Component {

    render() {
        return(<div>
               <h2>Students</h2>
                <table className="table table-striped">
                <thead>
                <tr>
                <th>Firstname</th>
                <th>Lastname</th>
                <th>Birthdate</th>
                <th/>
                </tr>
                </thead>
                <tbody>
                {this.props.students.map((s, i) => <Student key={s._links.self.href} student={s} handleEditClick={this.props.handleEditClick}/>)}
            </tbody>
               </table>
               </div>
        )
    }

}



class StudentForm extends React.Component {   

    constructor(props) {
        super(props);
        this.handleChange = this.handleChange.bind(this);
        this.onDateChange = this.onDateChange.bind(this);
    }


    handleChange(event) {
        this.props.handleChange(event.target.id, event.target.value);
    }
    
    // Special handler for datepicker cause we get no event.target.id and event.target.value
    onDateChange(value) {
        this.props.handleChange('birthdate', value);
    }

    render() {
        return(
                <form onSubmit={this.props.handleSubmitClick} id="studentForm" className="form-horizontal">
                  <fieldset>

                <div className="form-group">
                  <label className="col-md-2 control-label" htmlFor="firstname">Firstname</label>
                  <div className="col-md-4">
                    <input id="firstname" name="firstname" type="text" value={this.props.student.firstname} onChange={this.handleChange} className="form-control input-md" required="" />
                  </div>
                </div>

                <div className="form-group">
                  <label className="col-md-2 control-label" htmlFor="lastname">Lastname</label>
                  <div className="col-md-4">
                    <input id="lastname" name="lastname" type="text" value={this.props.student.lastname} onChange={this.handleChange} className="form-control input-md" required="" />
                  </div>
                </div>

              <div className="form-group">
                <label className="col-md-2 control-label" htmlFor="birthdate">Birthdate</label>
                <div className="col-md-4">
                  <DatePicker id="birthdate" value={this.props.student.birthdate} onChange={this.onDateChange} />
                </div>
              </div>

              <div className="form-group">
                <div className="col-md-2 col-md-offset-2 ">
                  <button id="submit" name="submit" className="btn btn-success">Save</button>
               </div>
              </div>

            </fieldset>
          </form>
        );


    }


}
class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            students: [],
            editStudent: {
                firstname: "",
                lastname: "",
                href: "",
            }
        };
        this.handleEditClick = this.handleEditClick.bind(this);
        this.handleSubmitClick = this.handleSubmitClick.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.updateStudent = this.updateStudent.bind(this);
        this.saveNewStudent = this.saveNewStudent.bind(this);
        this.getAllStudents = this.getAllStudents.bind(this);
    }


    handleEditClick(student) {
        this.setState({editStudent: student})
        
    }

    getAllStudents() {
        $.ajax({
            url: "http://localhost:8080/api/students"
        }).done(data => {
            this.setState({ students: data._embedded.students });
        }).catch(error => {
            console.log("ERROR; ", error);
        });
    }


    updateStudent(student) {
        var request = new Request(student._links.self.href, { 
            method: 'PUT',
            headers: new Headers({
                'Content-Type': 'application/json'
            }),
            body: JSON.stringify(student)
        });
        fetch(request)
            .then(response => {
                this.getAllStudents();
            })
            .catch(error => {
                console.error("Error: ", error);
            });

    }


    saveNewStudent(student) {
        var request = new Request("http://localhost:8080/api/students", { 
            method: 'POST',
            headers: new Headers({
                'Content-Type': 'application/json'
            }),
            body: JSON.stringify(student)
        });
        fetch(request)
            .then(response => {
                this.getAllStudents()
            })
            .catch(error => {
                console.error("Error: ", error);
            });
    }

    handleSubmitClick(e) {
        e.preventDefault();
        let student = null;
        
        // Check if update or new student
        if(this.state.editStudent._links == null) {
            this.saveNewStudent(this.state.editStudent);
        } else {
            student = this.state.students.filter(s => s._links.self.href == this.state.editStudent._links.self.href)[0];
            student.firstname = this.state.editStudent.firstname;
            student.lastname = this.state.editStudent.lastname;
            this.updateStudent(student);
        }

        // Clear form
        this.setState({
            editStudent: {
                firstname: "",
                lastname: "",
                birthdate: "",
                href: ""
            }
        });

    }

    handleChange(key, value) {
        let student = this.state.editStudent;
        student[key] = value;
        this.setState({editStudent: student});


    }

    componentDidMount() {
        $.ajax({
            url: "http://localhost:8080/api/students"
        }).done(data => {
            this.setState({ students: data._embedded.students });
        }).fail(error => {
            console.log("Error on getting data", error);
        });
    }

    render() {
        return(<div className="container">
               <div className="row">
               <h1>Esta student administration</h1>

               <StudentForm student={this.state.editStudent} handleSubmitClick={this.handleSubmitClick} handleChange={this.handleChange}/>
               {(this.state.students.length > 0) ? (
                       <StudentTable students={this.state.students} handleEditClick={this.handleEditClick} />
               ) : (
                       <h1>Loading</h1>
               ) }
               </div>
               </div>
              );
    }
}

ReactDOM.render(<App />,  document.getElementById('root'))
