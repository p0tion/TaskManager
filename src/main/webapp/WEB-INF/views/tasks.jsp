<%--
  User: Acer
  Date: 21.07.2015
  Time: 17:17
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<spring:message code="tasks.pageTitle" var="pageTitle"/>
<spring:message code="tasks.yourTodoListLabel" var="yourTodoListLabel"/>
<spring:message code="tasks.projectName.title" var="projectNameTitle"/>
<spring:message code="tasks.projectNameInput.title"
                var="projectNameInputTitle"/>
<spring:message code="tasks.deleteProjectImg.title"
                var="deleteProjectImgTitle"/>
<spring:message code="tasks.deleteProjectConfirmText"
                var="deleteProjectConfirmText"/>
<spring:message code="tasks.taskName.title" var="taskNameTitle"/>
<spring:message code="tasks.taskName.small" var="taskNamePlaceholder"/>
<spring:message code="tasks.addTaskButton" var="addTaskButton"/>
<spring:message code="tasks.addTaskButton.title" var="addTaskButtonTitle"/>
<spring:message code="tasks.taskDoneCheckbox.title"
                var="taskDoneCheckboxTitle"/>
<spring:message code="tasks.clickToChangeTaskName.title"
                var="clickToChangeTaskNameTitle"/>
<spring:message code="tasks.enterNewTaskName.title"
                var="enterNewTaskNameTitle"/>
<spring:message code="tasks.raisePriority.title" var="raisePriorityTitle"/>
<spring:message code="tasks.lowerPriority.title" var="lowerPriorityTitle"/>
<spring:message code="tasks.deleteTaskImg.title" var="deleteTaskImgTitle"/>
<spring:message code="tasks.addProjectInput.title" var="addProjectInputTitle"/>
<spring:message code="tasks.addProjectInput.small"
                var="addProjectInputPlaceholder"/>
<spring:message code="tasks.addTodoListButton" var="addTodoListButton"/>
<spring:message code="tasks.addTodoListButton.title"
                var="addTodoListButtonTitle"/>
<spring:message code="logOutLink" var="logOutLink"/>
<spring:message code="logOutLink.title" var="logOutLinkTitle"/>
<html>
<head>
    <title>${pageTitle}</title>
    <link href="../../resources/css/main.css" type="text/css" rel="Stylesheet"/>
    <script
     src="http://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js">
    </script>
    <script src="../../resources/js/myApp.js"></script>
</head>
<body ng-app="myApp">
<div class="logOut"><label>{{user.firstName}} {{user.lastName}}</label>
    <a href="/signOut" title="${logOutLinkTitle}">(${logOutLink})</a>
</div>

<div id="todo_label">${yourTodoListLabel}</div>

    <div ng-controller="projectController as projects"
         ng-init="load()">
        <div class="todoDiv"
             ng-repeat="project in items"
             ng-init="initProjectValues()">
            <table class="table"
                   border="0"
                   cellpadding="3"
                   cellspacing="1">
                <tr class="highTr"
                    ng-mouseenter="setShowDeleteProjectIcon($index, true)"
                    ng-mouseleave="setShowDeleteProjectIcon($index, false)">
                    <th colspan="2"
                        class="projectNameTh">
                        <img src="../../resources/images/tasks.png"
                             class="taskImg"
                             alt="project_icon">
                        <span
                              ng-show="showChangeProjectNameInput[$index].
                                                                value === false"
                              ng-click="setShowChangeProjectNameInput(
                                                                  $index, true)"
                              title="${projectNameTitle}">
                            {{project.project_name}}
                        </span>
                        <input
                               ng-show="showChangeProjectNameInput[$index].
                                                                 value === true"
                               ng-model="project.project_name"
                               ng-keyup="changeProjectName($index,
                                                           project.project_name,
                                                           project.project_id)"
                               type="text"
                               title="${projectNameInputTitle}">
                    </th>
                    <th class="deleteProjectTh">
                        <a href=""
                           class="deleteProject"
                           ng-show="showDeleteProjectIcon[$index].
                                                                value === true"
                           ng-click="
                           deleteProject($index,
                                         project,
                                         '${deleteProjectConfirmText}')">
                           <img src="../../resources/images/Trash_Can-32.png"
                                alt="Delete project"
                                title="${deleteProjectImgTitle}">
                        </a>
                    </th>
                </tr>
                <tr id="addTaskRow"
                    class="addTaskTr">
                    <td class="firstTd">
                    </td>
                    <td colspan="2">
                        <form ng-submit="addTask($index,
                                                 project,
                                                 newTask[$index].taskName)">
                        <input type="text"
                               name="taskName"
                               class="taskName"
                               size="90"
                               title="${taskNameTitle}"
                               placeholder="${taskNamePlaceholder}"
                               required
                               ng-model="newTask[$index].taskName"
                               value=""/>
                        <input type="submit"
                               class="button addTaskButton"
                               name="addTask"
                               title="${addTaskButtonTitle}"
                               value="${addTaskButton}"/>
                        </form>
                    </td>
                </tr>
                <tr ng-repeat="task in project.tasks"
                    ng-init="showChangeTaskNameInput[$parent.$index][$index]
                                                                 .value = false"
                    id="taskRow"
                    ng-class="item"
                    ng-mouseenter="showTaskIcons = true; item = 'focus'"
                    ng-mouseleave="showTaskIcons = false; item = 'noFocus'">
                    <td>
                        <input type="checkbox"
                               name="child"
                               id="checkBox"
                               title="${taskDoneCheckboxTitle}"
                               tabindex="-1"
                               ng-model="task.isDone"
                               ng-change="changeTaskStatus(task.task_id)"/>
                    </td>
                    <td class="taskCompleted-{{task.isDone}}">
                       <label id="taskLabel"
                              title="${clickToChangeTaskNameTitle}"
                              ng-show="
                              showChangeTaskNameInput[$parent.$index][$index]
                                                               .value === false"
                              ng-click="
                              setShowChangeTaskNameInput($parent.$index,
                                                         $index,
                                                         true)">
                               {{task.task_name}}</label>
                        <input
                             ng-show="
                             showChangeTaskNameInput[$parent.$index][$index]
                                                               .value === true"
                             ng-model="task.task_name"
                             ng-keyup="changeTaskName($parent.$index,
                                                      $index,
                                                      task.task_name,
                                                      task.task_id)"
                             type="text"
                             title="${enterNewTaskNameTitle}">
                    </td>
                    <td class="taskIconsTd">
                        <div class="taskIcons"
                             ng-show="showTaskIcons === true">
                            <a href=""
                               class="upPriority">
                                <img src="../../resources/images/up.png"
                                     alt="Raise priority"
                                     title="${raisePriorityTitle}"
                                     ng-click="raisePriority($parent.$index,
                                                             $index,
                                                             task.task_id)">
                            </a><a href=""
                                   class="downPriority">
                                <img src="../../resources/images/down.png"
                                     alt="Lower priority"
                                     title="${lowerPriorityTitle}"
                                     ng-click="lowerPriority($parent.$index,
                                                             $index,
                                                             task.task_id)">
                            </a><a href=""
                                   class="deleteTask"
                                   ng-click="deleteTask($parent.$index,
                                                        $index,
                                                        task.task_id)">
                                <img
                               src="../../resources/images/Trash_Can-32.png"
                               alt="Delete task"
                               title="${deleteTaskImgTitle}">
                            </a>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
        <form class="addProjectForm"
              ng-show="isShowAddProjectForm(true)"
              ng-submit="addProject(newProject.name)">
            <table border="0"
                   cellpadding="3"
                   cellspacing="1">
                <tr>
                    <td>
                        <input type="text"
                               name="newProjectName"
                               class="newProjectName"
                               autofocus="true"
                               size="40"
                               title="${addProjectInputTitle}"
                               placeholder="${addProjectInputPlaceholder}"
                               required
                               value=""
                               ng-model="newProject.name"/>
                        <input type="submit"
                               class="button addTaskButton"
                               name="addProject"
                               title="${addTodoListButtonTitle}"
                               value="${addTodoListButton}"/>
                    </td>
                </tr>
            </table>
        </form>
        <div id="addProjectButtonDiv"
             ng-show="isShowAddProjectForm(false)">
            <button class="button"
                    name="addProject"
                    id="addProjectButton"
                    title="${addTodoListButtonTitle}"
                    ng-click="setShowAddProjectForm(true)">
                <span id="addTodoTxt">${addTodoListButton}</span>
            </button>
        </div>
</div>
<div class="localeUrls">
    <a href="?lang=en">en</a> |
    <a href="?lang=ru">ru</a>
</div>
</body>
</html>
