/*
 * Copyright (c) 2016. Dilwar Mandal - SocialAPP
 */

/**
 * Created by Dilwar on 03-08-2016.
 */

'use strict';
angular.module('app').controller('LoginController', ['$scope', 'UserService', function ($scope, UserService) {
    var self = this;
    self.user = {userName: '', password: ''};
    self.users = [];
    self.userName = "mandal";
    self.password = "dilwar";
    self.submit = submit;
    self.reset = reset;

    function createUser(user) {
        UserService.createUser(user)
            .then(
                alert("Hello")
            );
    }

    function submit() {
        if (self.user.userName === null) {
            console.log('Saving New User', self.user);
            createUser(self.user);
        } else {
            updateUser(self.user, self.user.userName);
            console.log('User updated with id ', self.user.id);
        }
        reset();
    }

    function reset() {
        self.user = {userName: null, password: ''};
        $scope.myForm.$setPristine(); //reset Form
    }
}]);