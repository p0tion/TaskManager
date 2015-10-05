var app = angular.module('myApp', []);
app.controller('projectController', function($scope, $http, $rootScope) {
    $scope.showAddProjectForm = false;
    $scope.showChangeProjectNameInput = [];
    $scope.showChangeTaskNameInput = [[],[],[],[],[],[],[],[],[],[],[],[],[],[],
        [],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],
        [],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[]];
    $scope.showDeleteProjectIcon = [];
    $scope.newProject = [];
    $scope.newTask = {};

    $scope.initProjectValues = function() {
        $scope.showDeleteProjectIcon.push({'value':false});
        $scope.showChangeProjectNameInput.push({'value':false});
    };

    $scope.addTask = function (index, project, taskName) {
        var taskPriority;
        if (taskName) {
            console.log(project.project_id + " " + taskName);
            if($rootScope.items[index].tasks.length > 0){
                taskPriority = $rootScope.items[index].tasks[
                $rootScope.items[index].tasks.length - 1].task_priority + 1;
            } else {
                taskPriority = 0;
            }
            $scope.addTaskHttp(index, project, taskName, taskPriority);
        }
    };

    $scope.addTaskHttp = function (index, project, taskName, taskPriority) {
        $http({
            url: "/addTask",
            method: "POST",
            headers: {'Content-Type': 'application/json'},
            data: {'project_of_task': project,
                   'task_name': taskName,
                   'task_priority': taskPriority}
        }).success(function (data, status, headers, config) {
            $rootScope.items[index].tasks.push(data);
            $scope.newTask[index].taskName = "";
        }).error(function (data, status, headers, config) {
            alert(status);
        });
    };

    $scope.setShowChangeTaskNameInput = function(parentIndex, index, status) {
        console.log(status);
        if(status == true) {
            for (var i = 0; i < $scope.showChangeTaskNameInput.length; i++) {
                for (var j = 0; j < $scope.showChangeTaskNameInput[i].length; j++) {
                    $scope.showChangeTaskNameInput[i][j].value = false;
                }
            }
        }
        $scope.showChangeTaskNameInput[parentIndex][index].value = status;
    };

    $scope.changeTaskName = function(parentIndex, index, taskName,taskId) {
        if(event.keyCode == 13 && taskName) {
            $http({
                url: "/editTask",
                method: "POST",
                headers: {'Content-Type': 'application/json'},
                data: {'task_id':taskId,'task_name':taskName}
            }).success(function (data, status, headers, config) {
                $scope.setShowChangeTaskNameInput(parentIndex, index, false);
            }).error(function (data, status, headers, config) {
                alert(status);
            });
        } else if (event.keyCode == 27) {
            $http({
                url: "/getTaskName",
                method: "POST",
                headers: {'Content-Type': 'application/json'},
                data: {'task_id':taskId}
            }).success(function (data, status, headers, config) {
                $rootScope.items[parentIndex].tasks[index] = data.task;
                $scope.setShowChangeTaskNameInput(parentIndex, index, false);
            }).error(function (data, status, headers, config) {
                alert(status);
            });
        }
    }

    $scope.raisePriority = function(parentIndex, index, taskId) {
        var upperTask = $rootScope.items[parentIndex].tasks[index-1];
        if(upperTask != undefined){
            var index2 = index-1;
            var tmp = $rootScope.items[parentIndex].tasks[index].status;
            $rootScope.items[parentIndex].tasks[index].status =
                $rootScope.items[parentIndex].tasks[index2].status;
            $rootScope.items[parentIndex].tasks[index2].status = tmp;
            $scope.swapStatusHttp(taskId, index, upperTask.task_id, index2,
                                  parentIndex);
        }
    };

    $scope.lowerPriority = function(parentIndex, index, taskId) {
        var bottomTask = $rootScope.items[parentIndex].tasks[index+1];
        if(bottomTask != undefined){
            var index2 = index+1;
            var tmp = $rootScope.items[parentIndex].tasks[index].status;
            $rootScope.items[parentIndex].tasks[index].status =
                $rootScope.items[parentIndex].tasks[index2].status;
            $rootScope.items[parentIndex].tasks[index2].status = tmp;
            $scope.swapStatusHttp(taskId, index, bottomTask.task_id, index2,
                                  parentIndex);
        }
    };

    $scope.swapStatusHttp =
        function(taskId, index, taskId2, index2, parentIndex) {
            $http({
                url: "/swapStatus",
                method: "POST",
                headers: {'Content-Type': 'application/json'},
                data: {'id1':taskId,'id2':taskId2}
            }).success(function (data, status, headers, config) {
                var tmp = $rootScope.items[parentIndex].tasks[index];
                $rootScope.items[parentIndex].tasks[index] =
                    $rootScope.items[parentIndex].tasks[index2];
                $rootScope.items[parentIndex].tasks[index2] = tmp;
            }).error(function (data, status, headers, config) {
                alert(status);
            });
    };

    $scope.load = function() {
        $http({
            url: "/projects",
            method: "POST",
            headers: {'Content-Type': 'application/json'}
        }).success(function (data, status, headers, config) {
            $rootScope.items = [];
            for (var i = 0; i < data.items.length; i++) {
                $rootScope.items.push(data.items[i]);
            }
            $rootScope.user = data.user;
        }).error(function (data, status, headers, config) {
            alert(status);
        });
    };

    $scope.addProject = function(newProjectName) {
        $http({
            url: "/addProject",
            method: "POST",
            headers: {'Content-Type': 'application/json'},
            data: {'project_name':newProjectName}
        }).success(function (data, status, headers, config) {
            $rootScope.items.push(data);
            $rootScope.items[$rootScope.items.length-1].tasks = [];
            $scope.newProject.name = "";
            $scope.setShowAddProjectForm(false);
        }).error(function (data, status, headers, config) {
            alert(status);
        });
    };

    $scope.changeProjectName = function(index, projectName, projectId) {
        if(event.keyCode == 13 && projectName) {
            $http({
                url: "/editProject",
                method: "POST",
                headers: {'Content-Type': 'application/json'},
                data: {'project_id':projectId,'project_name':projectName}
            }).success(function (data, status, headers, config) {
                $scope.setShowChangeProjectNameInput(index, false);
            }).error(function (data, status, headers, config) {
                alert(status);
            });
        } else if (event.keyCode == 27) {
            $http({
                url: "/getProjectName",
                method: "POST",
                headers: {'Content-Type': 'application/json'},
                data: {'project_id':projectId}
            }).success(function (data, status, headers, config) {
                $rootScope.items[index] =  data.project;
                $scope.setShowChangeProjectNameInput(index, false);
            }).error(function (data, status, headers, config) {
                alert(status);
            });
        }
    };

    $scope.deleteProject = function(index, project, confirmText) {
        var choice = confirm(confirmText + " \""+ project.project_name +"\"?");
        if(choice) {
            $http({
                url: "/deleteProject",
                method: "POST",
                headers: {'Content-Type': 'application/json'},
                data: {'project_id': project.project_id}
            }).success(function (data, status, headers, config) {
                $scope.items.splice(index, 1);
            }).error(function (data, status, headers, config) {
                alert(status);
            });
        }
    };

    $scope.deleteTask = function(parentIndex, index, taskId) {
        $http({
            url: "/deleteTask",
            method: "POST",
            headers: {'Content-Type': 'application/json'},
            data: {'task_id': taskId}
        }).success(function (data, status, headers, config) {
            $scope.items[parentIndex].tasks.splice(index, 1);
        }).error(function (data, status, headers, config) {
            alert(status);
        });
    };

    $scope.changeTaskStatus = function(taskId) {
        $http({
            url: "/changeTaskStatus",
            method: "POST",
            headers: {'Content-Type': 'application/json'},
            data: {'task_id':taskId}
        }).success(function (data, status, headers, config) {
        }).error(function (data, status, headers, config) {
            alert(status);
        });
    };

    $scope.setShowChangeProjectNameInput = function(index, status) {
        if(status == true) {
            for (var i = 0; i < $scope.showChangeProjectNameInput.length; i++) {
                $scope.showChangeProjectNameInput[i].value = false;
            }
        }
        $scope.showChangeProjectNameInput[index].value = status;
    };

    $scope.setShowDeleteProjectIcon = function(index, status) {
        $scope.showDeleteProjectIcon[index].value = status;
    };

    $scope.setShowTaskIcons = function(parentIndex, index, status) {
        $scope.showTaskIcons[parentIndex][index].value = status;
        console.log(parentIndex + " " + index + " " + $scope.showTaskIcons[parentIndex][index].value);
    };

    $scope.setShowAddProjectForm = function(status) {
        $scope.showAddProjectForm = status;
    };

    $scope.isShowAddProjectForm = function(status) {
        return $scope.showAddProjectForm === status;
    }
});