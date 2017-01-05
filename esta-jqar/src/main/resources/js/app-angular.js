  var appModule = angular.module('EstaApp', []);

appModule.controller('studentController', function ($scope,$http) {

 $scope.toggle=true;
 $scope.selection = [];
 $http.defaults.headers.post["Content-Type"] = "application/json";

    function findAllStudents() {
        //get all students and display initially
        $http.get(getContextPath() + 'api/students').
            success(function (data) {
            	$scope.students = data._embedded.students;
            	// reset form values
            	$scope.id = "";
                $scope.firstname="";
                $scope.lastname="";
                $scope.birthdate="";
                $scope.toggle='!toggle';
            });
    }

    findAllStudents();

 //save student
 $scope.saveStudent = function saveStudent() {
  if($scope.firstname=="" || $scope.lastname=="" || $scope.birthdate == "" ){
   alert("Insufficient Data! Please provide values for firstname, lastname and birthdate");
  }
  else{
   console.log("PUT First " + $scope.firstname + " last " + $scope.lastname);
      $http.post(getContextPath() + 'api/students', {
             id: $scope.id,
             firstname: $scope.firstname,
             lastname: $scope.lastname,
             birthdate: $scope.birthdate
         }).
    success(function(data, status, headers) {
      // Refetching EVERYTHING every time can get expensive over time
      findAllStudents();
    });
  }
 };

  //edit student
    $scope.edit = function edit(student) {
	console.log("edit student " + student.id );
        $scope.id = student.id;
        $scope.firstname = student.firstname;
        $scope.lastname = student.lastname;
        $scope.birthdate = student.birthdate;
  }

});

appModule.directive('datepicker', function(){
    return {
      restrict: 'A',
      require: 'ngModel',
      link: function( scope, element, attrs, ngModelCtrl ){
        $(function(){
          element.datepicker({
            changeMonth: true,
            changeYear: true,
            dateFormat: "yy-mm-dd",
            maxDate:"-1Y",
            onSelect:function( date ){
              scope.$apply(function(){
                ngModelCtrl.$setViewValue( date );
              });
            }
          });
          element.datepicker('setDate', ngModelCtrl.$viewValue);
        });
      }
    };
  });
