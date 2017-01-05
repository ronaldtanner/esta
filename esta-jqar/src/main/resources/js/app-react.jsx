/* https://spapas.github.io/2015/06/05/comprehensive-react-flux-tutorial/ */
  var Student = React.createClass({
    getInitialState: function(){
	return {display: true };
    },
    handleDelete() {
      var self = this;
      $.ajax({
        url: self.props.student._links.self.href,
        type: 'DELETE',
        success: function(result) {
          self.setState({display: false});
        },
        error: function(xhr, ajaxOptions, thrownError) {
          toastr.error(xhr.responseJSON.message);
        }
      });
    },
    render: function(){
      return (
        <tr>
          <td>{this.props.student.firstname}</td>
          <td>{this.props.student.lastname}</td>
          <td>{this.props.student.birthdate}</td>
          <td><a href='#' onClick={this.onClick}>Edit</a></td>
          <td> <button className="btn btn-info"
                       onClick={this.handleDelete}>Delete</button>
	  </td>
	</tr>);
     },
     onClick: function(id){
            this.props.handleEditClickPanel(this.props.student.id);
     }
   });
      
   var StudentTable = React.createClass({
        render: function(){
          var rows = [];
          this.props.students.forEach(function(student) {
             rows.push(<Student key={student._links.self.href} student={student}
                       handleEditClickPanel={this.props.handleEditClickPanel}/>).bind(this);
          });
          return (
           <table className="table table-striped">
             <thead>
               <tr>
                 <th>Firstname</th><th>Lastname</th><th>Birthdate</th><th/>
               </tr>
             </thead>
             <tbody>{rows}</tbody>
           </table>);
        }
    });

var StudentForm = Rect.createClass({
  render: function(){
    return(
       <form onSubmit={this.props.handleSubmitClick}>
         <label forHtml='firstname'>Firtsname</label>
         <input ref='firstname' name='firstname' type='text value={this.props.student.firstame}
                onChange={this.onChange} />
       </form>
  }
});
    var App = React.createClass({

	 getInitialState: function () {
	   return ({students: []});
	 },

	 componentDidMount: function () {
	     var self = this;
	     $.ajax({
            url: "http://localhost:8080/api/students"
         }).then(function (data) {
            self.setState({students: data._embedded.students});
         });
     },

	 render() {
	   return ( <StudentTable students={this.state.students}/> );
	 }
    });

ReactDOM.render(
           <App />, document.getElementById('root') );
