var module = angular.module('app', []);
module.directive('showErrors', function ($timeout, showErrorsConfig) {
        var getShowSuccess, linkFn;
        getShowSuccess = function (options) {
            var showSuccess;
            showSuccess = showErrorsConfig.showSuccess;
            if (options && options.showSuccess != null) {
                showSuccess = options.showSuccess;
            }
            return showSuccess;
        };
        linkFn = function (scope, el, attrs, formCtrl) {
            var blurred, inputEl, inputName, inputNgEl, options, showSuccess, toggleClasses;
            blurred = false;
            options = scope.$eval(attrs.showErrors);
            showSuccess = getShowSuccess(options);
            inputEl = el[0].querySelector('[name]');
            inputNgEl = angular.element(inputEl);
            inputName = inputNgEl.attr('name');
            if (!inputName) {
                throw 'show-errors element has no child input elements with a \'name\' attribute';
            }
            inputNgEl.bind('blur', function () {
                blurred = true;
                return toggleClasses(formCtrl[inputName].$invalid);
            });
            scope.$watch(function () {
                return formCtrl[inputName] && formCtrl[inputName].$invalid;
            }, function (invalid) {
                if (!blurred) {
                    return;
                }
                return toggleClasses(invalid);
            });
            scope.$on('show-errors-check-validity', function () {
                return toggleClasses(formCtrl[inputName].$invalid);
            });
            scope.$on('show-errors-reset', function () {
                return $timeout(function () {
                    el.removeClass('has-error');
                    el.removeClass('has-success');
                    return blurred = false;
                }, 0, false);
            });
            return toggleClasses = function (invalid) {
                el.toggleClass('has-error', invalid);
                if (showSuccess) {
                    return el.toggleClass('has-success', !invalid);
                }
            };
        };
        return {
            restrict: 'A',
            require: '^form',
            compile: function (elem, attrs) {
                if (!elem.hasClass('form-group')) {
                    throw 'show-errors element does not have the \'form-group\' class';
                }
                return linkFn;
            }
        };
    }
);

(function ($) {
    "use strict"; // Start of use strict

    // Configure tooltips for collapsed side navigation
    $('.navbar-sidenav [data-toggle="tooltip"]').tooltip({
        template: '<div class="tooltip navbar-sidenav-tooltip" role="tooltip"><div class="arrow"></div><div class="tooltip-inner"></div></div>'
    });

    // Toggle the side navigation
    $("#sidenavToggler").click(function (e) {
        e.preventDefault();
        $("body").toggleClass("sidenav-toggled");
        $(".navbar-sidenav .nav-link-collapse").addClass("collapsed");
        $(".navbar-sidenav .sidenav-second-level, .navbar-sidenav .sidenav-third-level").removeClass("show");
    });

    // Force the toggled class to be removed when a collapsible nav link is clicked
    $(".navbar-sidenav .nav-link-collapse").click(function (e) {
        e.preventDefault();
        $("body").removeClass("sidenav-toggled");
    });

    // Prevent the content wrapper from scrolling when the fixed side navigation hovered over
    $('body.fixed-nav .navbar-sidenav, body.fixed-nav .sidenav-toggler, body.fixed-nav .navbar-collapse').on('mousewheel DOMMouseScroll', function (e) {
        var e0 = e.originalEvent,
            delta = e0.wheelDelta || -e0.detail;
        this.scrollTop += (delta < 0 ? 1 : -1) * 30;
        e.preventDefault();
    });

    // Scroll to top button appear
    $(document).scroll(function () {
        var scrollDistance = $(this).scrollTop();
        if (scrollDistance > 100) {
            $('.scroll-to-top').fadeIn();
        } else {
            $('.scroll-to-top').fadeOut();
        }
    });

    // Configure tooltips globally
    $('[data-toggle="tooltip"]').tooltip();

    // Smooth scrolling using jQuery easing
    $(document).on('click', 'a.scroll-to-top', function (event) {
        var $anchor = $(this);
        $('html, body').stop().animate({
            scrollTop: ($($anchor.attr('href')).offset().top)
        }, 1000, 'easeInOutExpo');
        event.preventDefault();
    });

    // Call the dataTables jQuery plugin
    $(document).ready(function () {
        $('#copyright').html('Copyright &copy; Dilwar Mandal ' + new Date().getFullYear());
        var start = moment().subtract(365, 'days');
        var end = moment();
        $.fn.dataTableExt.afnFiltering.push(
            function (oSettings, aData, iDataIndex) {
                var date = moment(aData[1], "DD/MM/YYYY");
                var dateMin = moment(start, "DD/MM/YYYY");
                var dateMax = moment(end, "DD/MM/YYYY");

                if (date.isBetween(dateMin, dateMax, null, '[')) {
                    return true;
                }
                return false;
            });
        var accountTable = $('#accountDataTable').DataTable({
            dom: 'Bfrtip',
            searching: true,
            // ordering: true,
            // "order": [[0, "desc"]],
            // processing: true,
            // serverSide: true,
            ajax: {
                url: "/fetchTransactions",
            },
            sAjaxDataProp: "",
            aaSorting: [],
            columns: [
                {data: "transactionId"},
                {data: "transDate"},
                {data: "amount"},
                {data: "transRef"},
                {data: "transType"},
                {data: "transSelect"},
                {data: "transMode"},
                {data: "description"},
                {data: null}
            ],
            responsive: true,
            pageLength: 25,
            fnRowCallback: function (nRow, aData, iDisplayIndex) {
                if (aData.transType === "Credit") {
                    $('td:eq(3)', nRow).html('<button type="button" class="btn btn-sm btn-success" style="width:80%" aria-disabled="true" disabled>' + aData.transType + '</button>\n');
                }
                if (aData.transType === "Debit") {
                    $('td:eq(3)', nRow).html('<a href="#" class="btn btn-danger btn-sm disabled" style="width:80%" role="button" aria-disabled="true" disabled>' + aData.transType + '</a>\n');
                }
                $('td:eq(1)', nRow).html('₹' + aData.amount);
            },
            columnDefs: [
                {
                    targets: 0,
                    bVisible: false
                },
                {
                    targets: 1,
                    orderable: false
                },
                {
                    "width": "10%",
                    "targets": 2,
                    "orderable": false
                },
                {
                    "targets": 3,
                    "orderable": false
                },
                {
                    "targets": 7,
                    "orderable": false
                },
                {
                    "targets": 8,
                    "data": null,
                    "orderable": false,
                    "width": "10%",
                    "className": "dt-center",
                    "defaultContent":
                    '<a class="dt-button btn btn-outline-info btn-sm edit-item-transaction" id="edit-item-transaction" href="#"  title="Edit Transaction" data-toggle="modal" data-target="#addTransactionModal" ><span><i class="fa fa-pencil" aria-hidden="true"></i></span></a>' +
                    ' <a class="dt-button btn btn-outline-danger btn-sm delete-item-transaction" id="delete-item-transaction" href="#" data-toggle="modal" data-target="#delete-transaction" title="Delete Transaction"><span><i class="fa fa-trash-o" aria-hidden="true"></i></span></a>' +
                    ' <a class="dt-button btn btn-outline-warning btn-sm view-item-transaction" id="view-item-transaction" href="#"  data-toggle="modal" data-target="#addTransactionModal" title="View Transaction""><span><i class="fa fa-eye" aria-hidden="true"></i></span></a>'
                }
            ],
            buttons: [
                {
                    className: 'btn btn-success btn-sm btn-add-transaction',
                    text: '<i class="fa fa-plus" aria-hidden="true"></i> Add Transaction',
                    action: function (e, dt, node, config) {
                        $("#addTransactionModal").modal();
                        $('.transaction-message').html("");
                        var scope = angular.element("#page-top").scope();
                        scope.NG_MODE = "Add New";
                        scope.fetchDropDownDataTransaction();
                        scope.transaction = {};
                        scope.ShowSave = true;
                        scope.ShowReset = true;
                        scope.ShowCancel = true;
                        $("div.alert-transaction-message").remove();
                    }
                },
                {
                    extend: 'copy',
                    className: 'btn btn-primary btn-sm',
                    text: '<i class="fa fa-files-o"></i> Copy',
                    exportOptions: {
                        columns: [0, 1, 2, 3, 4, 5]
                    }
                },
                {
                    extend: 'excel',
                    className: 'btn btn-primary btn-sm',
                    text: '<i class="fa fa-file-excel-o"></i> Excel',
                    exportOptions: {
                        columns: [0, 1, 2, 3, 4, 5]
                    }
                },
                {
                    extend: 'pdf',
                    className: 'btn btn-primary btn-sm',
                    text: '<i class="fa fa-file-pdf-o"></i> PDF',
                    exportOptions: {
                        columns: [0, 1, 2, 3, 4, 5]
                    },
                    title: function () {
                        return "Transaction Details - " + $('#reportrange')[0].innerText;
                    },
                    pageSize: 'A4',
                    customize: function (doc) {
                        doc.styles.title = {
                            color: 'green',
                            fontSize: '23',
                            //background: 'blue',
                            alignment: 'left'
                        };
                        doc.pageMargins = [30, 30, 30, 30];
                        doc.defaultStyle.fontSize = 8;
                        doc.styles.tableHeader.fontSize = 7;
                        doc.styles.title.fontSize = 10;
                        // Remove spaces around page title
                        doc.content[0].text = doc.content[0].text.trim();
                        // Create a header
                        doc['header'] = (function (page, pages) {
                            return {
                                columns: [
                                    '***** Confidential *****',
                                    {
                                        // This is the right column
                                        alignment: 'right',
                                        text: 'Generated on ' + new Date()
                                    }
                                ],
                                margin: [10, 0, 10, 0]
                            }
                        });
                        // Create a footer
                        doc['footer'] = (function (page, pages) {
                            return {
                                columns: [
                                    {
                                        // This is the right column
                                        alignment: 'right',
                                        text: ['Page ', {text: page.toString()}, ' of ', {text: pages.toString()}]
                                    }
                                ],
                                margin: [10, 0]
                            }
                        });
                        // Styling the table: create style object
                        var objLayout = {};
                        // Horizontal line thickness
                        objLayout['hLineWidth'] = function (i) {
                            return .5;
                        };
                        // Vertikal line thickness
                        objLayout['vLineWidth'] = function (i) {
                            return .5;
                        };
                        // Horizontal line color
                        objLayout['hLineColor'] = function (i) {
                            return '#aaa';
                        };
                        // Vertical line color
                        objLayout['vLineColor'] = function (i) {
                            return '#aaa';
                        };
                        // Left padding of the cell
                        objLayout['paddingLeft'] = function (i) {
                            return 4;
                        };
                        // Right padding of the cell
                        objLayout['paddingRight'] = function (i) {
                            return 4;
                        };
                        // Inject the object in the document
                        doc.content[1].layout = objLayout;
                    }
                },
                {
                    extend: 'print',
                    className: 'btn btn-primary btn-sm',
                    text: '<i class="fa fa-print"></i> Print',
                    exportOptions: {
                        columns: [0, 1, 2, 3, 4, 5]
                    }
                },
                {
                    className: 'btn btn-info btn-sm',
                    text: '<div class="g-savetodrive"\n' +
                    '         data-src="//example.com/path/to/myfile.pdf"\n' +
                    '         data-filename="My Statement.pdf"\n' +
                    '         data-sitename="My Company Name">\n' +
                    '    </div>',
                    exportOptions: {
                        columns: [0, 1, 2, 3, 4, 5]
                    }
                }
            ]
        });

        $('#accountDataTable tbody').on('click', 'a', function () {
            var data = accountTable.row($(this).parents('tr')).data();
            var scope = angular.element("#page-top").scope();
            if (this.id == "view-item-transaction") {
                scope.fetchTransactionById(data.transactionId);
                $("#addTransactionModal input,textarea,select").attr("disabled", true);
                scope.ShowSave = false;
                scope.ShowReset = false;
                scope.ShowCancel = true;
                scope.NG_MODE = "View";
            }
            if (this.id == "edit-item-transaction") {
                scope.fetchTransactionById(data.transactionId);
                scope.NG_MODE = "Edit";
                scope.ShowSave = true;
                scope.ShowReset = false;
                scope.ShowCancel = true;
                $("#addTransactionModal input,textarea,select ").attr("disabled", false);
            }
            scope.$apply(function () {
                scope.transaction = {};
                scope.removeTrnsactionId = data.transactionId;
                scope.transaction.transactionId = data.transactionId;
            });
            $("div.alert-transaction-message").remove();
        });

        var dateRanger = '<div id="reportrange" class="pull-right report-range">' +
            '<i class="glyphicon glyphicon-calendar fa fa-calendar"></i>&nbsp;<span></span> <b class="caret"></b></div>';

        $(".dt-buttons").append(dateRanger).append('<div class="col-md-2 pull-right"><input type="text" id="myInputTextField" class="form-control form-control-sm search-range" placeholder="Search"/></div>');

        function cb(start, end) {
            $('#reportrange span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
            start = start.format('DD/MM/YYYY');
            end = end.format('DD/MM/YYYY');
        }

        $('#reportrange').on('apply.daterangepicker', function (ev, picker) {
            start = picker.startDate.format('DD/MM/YYYY');
            end = picker.endDate.format('DD/MM/YYYY');
            accountTable.draw();
        });
        $('#reportrange').daterangepicker({
            startDate: start,
            endDate: end,
            ranges: {
                'Today': [moment(), moment()],
                'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
                'Last 7 Days': [moment().subtract(6, 'days'), moment()],
                'Last 30 Days': [moment().subtract(29, 'days'), moment()],
                'This Month': [moment().startOf('month'), moment().endOf('month')],
                'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
            },
            locale: {
                format: 'DD/MM/YYYY'
            }
        }, cb);
        cb(start, end);

        $('#accountDataTable_filter').hide();

        $('#myInputTextField').keyup(function () {
            accountTable.search($(this).val()).draw();
        });

        $('[data-toggle="tooltip"]').tooltip();
        var current = location.pathname;
        $('#mainNav ul li a').each(function () {
            var $this = $(this);
            // if the current path is like this link, make it active
            if ($this.attr('href') != undefined) {
                if ($this.attr('href').indexOf(current) !== -1) {
                    $this.parent().addClass('active');
                }
            }
        });
        var memberTable = $('#memberDataTable').DataTable({
            dom: 'Bfrtip',
            searching: false,
            sAjaxSource: "/fetchMembers",
            sAjaxDataProp: "",
            order: [[1, "asc"]],
            columns: [
                {data: "memberId"},
                {data: "name"},
                {data: "roomNo"},
                {data: "email"},
                {data: "mobileNo"},
                {data: "occupation"},
                {data: "dateOfBirth"},
                {data: null}
            ],
            responsive: true,
            pageLength: 25,
            columnDefs: [
                {
                    "targets": 0,
                    "bVisible": false
                },
                {
                    "targets": 7,
                    "orderable": false,
                    "className": "dt-center",
                    "width": "10%",
                    "data": null,
                    "defaultContent": '<a class="dt-button btn  btn-outline-info btn-sm edit-item-member" id="edit-item-member" href="#" title="Edit Member" data-toggle="modal" data-target="#addMemberModal"><span><i class="fa fa-pencil" aria-hidden="true"></i></span></a>' +
                    ' <a class="dt-button btn  btn-outline-danger btn-sm delete-item-member" href="#" id="delete-item-member" title="Delete Member" data-toggle="modal" data-target="#delete-member"><span><i class="fa fa-trash-o" aria-hidden="true"></i></span></a>' +
                    ' <a class="dt-button btn btn-outline-warning btn-sm info-item-member" href="#" id="view-item-member" title="View Member" data-toggle="modal" data-target="#addMemberModal"><span><i class="fa fa-eye" aria-hidden="true"></i></span></a>'
                }
            ],
            buttons: [
                {
                    className: 'btn btn-success btn-sm',
                    text: '<i class="fa fa-plus" aria-hidden="true"></i> Add Member',
                    action: function (e, dt, node, config) {
                        $("#addMemberModal").modal();
                        var scope = angular.element("#page-top").scope();
                        scope.NG_MODE = "Add New";
                        scope.fetchDropDownData();
                        $("#addMemberModal input,textarea,select").attr("disabled", false);
                        scope.member = {};
                        scope.ShowSave = true;
                        scope.ShowReset = true;
                        scope.ShowCancel = true;
                        $("div.alert-member-message").remove();
                    }
                },
                {
                    extend: 'copy',
                    className: 'btn btn-primary btn-sm',
                    text: '<i class="fa fa-files-o"></i> Copy',
                    exportOptions: {
                        columns: [0, 1, 2, 3, 4, 5]
                    }
                },
                {
                    extend: 'excel',
                    className: 'btn btn-primary btn-sm',
                    text: '<i class="fa fa-file-excel-o"></i> Excel',
                    exportOptions: {
                        columns: [0, 1, 2, 3, 4, 5]
                    }
                },
                {
                    extend: 'pdf',
                    className: 'btn btn-primary btn-sm',
                    text: '<i class="fa fa-file-pdf-o"></i> PDF',
                    exportOptions: {
                        columns: [0, 1, 2, 3, 4, 5]
                    }
                },
                {
                    extend: 'print',
                    className: 'btn btn-primary btn-sm',
                    text: '<i class="fa fa-print"></i> Print',
                    exportOptions: {
                        columns: [0, 1, 2, 3, 4, 5]
                    }
                }
            ]
        });
        $('input[name="birthDate"],input[name="transDate"]').daterangepicker({
            singleDatePicker: true,
            autoUpdateInput: true,
            showDropdowns: true,
            locale: {
                format: 'DD/MM/YYYY'
            }
        });
        $('#memberDataTable tbody').on('click', 'a', function () {
            var data = memberTable.row($(this).parents('tr')).data();
            var scope = angular.element("#page-top").scope();
            if (this.id == "view-item-member") {
                scope.NG_MODE = "View";
                scope.fetchMemberDataById(data.memberId);
                $("#addMemberModal input,textarea,select").attr("disabled", true);
                scope.ShowSave = false;
                scope.ShowReset = false;
                scope.ShowCancel = true;
            }
            if (this.id == "edit-item-member") {
                scope.NG_MODE = "Edit";
                scope.fetchMemberDataById(data.memberId);
                scope.ShowSave = true;
                scope.ShowReset = false;
                scope.ShowCancel = true;
                $("#addMemberModal input,textarea,select ").attr("disabled", false);
            }
            scope.$apply(function () {
                scope.member = {};
                scope.removeMemberId = data.memberId;
                scope.member.memberId = data.memberId;
            });
            $("div.alert-member-message").remove();
        });
        $('#addMemberModal').on('hidden.bs.modal', function () {
            var table = $('#memberDataTable').dataTable();
            table.api().ajax.reload();
        });
        $('#addTransactionModal').on('hidden.bs.modal', function () {
            var table = $('#transactionDataTable').dataTable();
            table.api().ajax.reload();
        })
    });
})
(jQuery); // End of use strict

module.provider('showErrorsConfig', function () {
    var _showSuccess;
    _showSuccess = false;
    this.showSuccess = function (showSuccess) {
        return _showSuccess = showSuccess;
    };
    this.$get = function () {
        return {showSuccess: _showSuccess};
    };
});
module.controller('HomeController', function ($scope, $window, $http) {
    var MONTHS = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
    var earningData = [];
    var expenditureData = [];
    var monthlyTxnPerform = [];

    function add(a, b) {
        return a + b;
    }

    $scope.ytdYear = 0000;
    $scope.ytdEarning = 0;
    $scope.ytdExpenditure = 0;
    $scope.totalEarnings = 0;//earningData.reduce(add, 0);
    $scope.totalExpenditure = 0;// expenditureData.reduce(add, 0);
    $scope.totalMargin = 0;
    $scope.users = 0;
    $scope.lastTransactionAmount = 155;
    $scope.cashInHand = 0;
    $scope.cashInBank = 0;
    $scope.lastUpdatedDate = new Date();
    $http.get('/getTotalMembers')
        .then(
            function (response) {
                $scope.users = response.data;
            },
            function (error) {
                console.log(error);
            }
        );
    $http.get('/getAccountInfo')
        .then(
            function (response) {
                $scope.ytdYear = response.data.currentFiscal;
                $scope.cashInBank = response.data.totalCashInBank || 0;
                $scope.cashInHand = response.data.totalCashInHand || 0;
                $scope.totalEarnings = response.data.totalCreditAmount || 0;
                $scope.totalExpenditure = response.data.totalDebitAmount || 0;
                earningData = response.data.monthlyCreditFlow || 0;
                expenditureData = response.data.monthlyDebitFlow || 0;
                monthlyTxnPerform = response.data.monthlyCashFlow || 0;
                $scope.ytdEarning = earningData instanceof Array ? earningData.reduce(add, 0) : 0;
                $scope.ytdExpenditure = expenditureData instanceof Array ? expenditureData.reduce(add, 0) : 0;
                var value = monthlyTxnPerform instanceof Array ? Math.ceil(monthlyTxnPerform.reduce(max, 0)) : 0;
                console.log(value);
                var n = value.toString().length - 1;
                n = Math.pow(10, n);
                var maxY = 50000;//Math.ceil(monthlyTxnPerform.reduce(max, 0) / n) * n;
                var maxChartY = 50000;

                var ctx = $('#myBarChart')[0];
                var myMonthlyTransaction = new Chart(ctx, {
                    type: 'bar',
                    data: {
                        labels: MONTHS,
                        datasets: [{
                            label: "Earnings",
                            backgroundColor: "#007bff",
                            data: earningData,
                        }, {
                            label: "Expenditure",
                            backgroundColor: "#ffc107",
                            data: expenditureData,
                        }],
                    },
                    options: {
                        scales: {
                            xAxes: [{
                                time: {
                                    unit: 'month'
                                },
                                gridLines: {
                                    display: false
                                },
                                ticks: {
                                    minTicksLimit: 12
                                }
                            }],
                            yAxes: [{
                                ticks: {
                                    min: 0,
                                    max: maxY,
                                    maxTicksLimit: 5
                                },
                                gridLines: {
                                    display: true
                                }
                            }],
                        },
                        legend: {
                            display: true
                        },
                        tooltips: {
                            mode: 'nearest',
                            axis: 'y'
                        }
                    }
                });
                var ctxAreaChart = document.getElementById("myAreaChart");
                var myMonthlyPerformance = new Chart(ctxAreaChart, {
                    type: 'line',
                    data: {
                        labels: MONTHS,
                        datasets: [{
                            label: " ₹",
                            lineTension: 0.3,
                            backgroundColor: "rgba(2,117,216,0.2)",
                            borderColor: "rgba(2,117,216,1)",
                            pointRadius: 5,
                            pointBackgroundColor: "rgba(2,117,216,1)",
                            pointBorderColor: "rgba(255,255,255,0.8)",
                            pointHoverRadius: 5,
                            pointHoverBackgroundColor: "rgba(2,117,216,1)",
                            pointHitRadius: 20,
                            pointBorderWidth: 2,
                            data: monthlyTxnPerform,
                        }],
                    },
                    options: {
                        scales: {
                            xAxes: [{
                                time: {
                                    unit: 'date'
                                },
                                gridLines: {
                                    display: false
                                },
                                ticks: {
                                    maxTicksLimit: 12
                                }
                            }],
                            yAxes: [{
                                ticks: {
                                    min: 0,
                                    max: maxChartY,
                                    maxTicksLimit: Math.ceil(maxChartY / 5)
                                },
                                gridLines: {
                                    color: "rgba(0, 0, 0, .125)",
                                }
                            }],
                        },
                        legend: {
                            display: false
                        }
                    }
                });

            },
            function (error) {
                console.log(error);
            }
        );

    function max(a, b) {
        return Math.max(a, b);
    }
});
module.controller('MemberController', function ($scope, UserService, $window, $http) {
    var BASE_API = '/api/v1/';
    var status;
    $scope.NG_MODE = "Add New";
    $scope.lastUpdatedDate = new Date();
    $scope.ShowSave = true;
    $scope.ShowReset = true;
    $scope.ShowCancel = true;
    $scope.removeMemberId;
    $scope.member = {
        memberId: '',
        title: '',
        firstName: '',
        middleName: '',
        lastName: '',
        gender: '',
        birthDate: '',
        roomNo: '',
        email: '',
        mobileNo: '',
        address1: '',
        address2: '',
        country: '',
        state: '',
        city: '',
        zip: '',
        occupation: '',
        subscribe: ''
    };
    $scope.countries = [];
    $scope.states = [];
    $scope.titles = [];
    $scope.genders = [];
    $scope.save = function () {
        $scope.$broadcast('show-errors-check-validity');
        if ($scope.memberForm.$valid) {
            UserService.doLogin($scope.member)
                .then(
                    function (response) {
                        console.log(response);
                    },
                    function (errResponse) {
                        console.error('Error while creating User');
                    }
                );
            console.log(status);
        }
    };
    $scope.reset = function () {
        $scope.$broadcast('show-errors-reset');
        $scope.member = "";
    };
    $scope.lastUpdatedDate = new Date();
    $scope.fetchDropDownData = function () {
        $http.get(BASE_API + 'countries')
            .then(
                function (response) {
                    $scope.countries = response.data;
                },
                function (error) {
                    console.log(error);
                }
            );
        $http.get(BASE_API + 'titles')
            .then(
                function (response) {
                    $scope.titles = response.data;
                },
                function (error) {
                    console.log(error);
                }
            );
        $http.get(BASE_API + 'genders')
            .then(
                function (response) {
                    $scope.genders = response.data;
                },
                function (error) {
                    console.log(error);
                }
            );
    };
    $scope.fetchMemberDataById = function (memberId) {
        $scope.fetchDropDownData();
        $http.get('/fetchMembers/' + memberId)
            .then(
                function (response) {
                    $scope.member.memberId = response.data.memberId;
                    $scope.member.title = {code: Number(response.data.titleVal)};
                    $scope.member.firstName = response.data.firstName;
                    $scope.member.middleName = response.data.middleName;
                    $scope.member.lastName = response.data.lastName;
                    $scope.member.gender = {code: Number(response.data.genderVal)};
                    $scope.member.birthDate = response.data.birthDate;
                    $scope.member.roomNo = response.data.roomNo;
                    $scope.member.email = response.data.emailId;
                    $scope.member.mobileNo = Number(response.data.phoneNo);
                    $scope.member.subscribe = response.data.emailSubscribe;
                    $scope.member.occupation = response.data.occupation;
                    var alternateAddress = response.data.memberAddress[0];
                    $scope.member.address1 = alternateAddress.address1;
                    $scope.member.address2 = alternateAddress.address2;
                    $scope.member.country = {code: Number(alternateAddress.countryVal)};
                    $scope.onChangeCountry($scope.member.country.code);
                    $scope.member.state = {code: Number(alternateAddress.stateVal)};
                    $scope.member.city = alternateAddress.city;
                    $scope.member.zip = alternateAddress.pinCode;
                },
                function (error) {
                    console.log(error);
                }
            );
    };
    $scope.onChangeCountry = function (country) {
        if (country == undefined) {
            $scope.states = [];
            return;
        }
        $http.get(BASE_API + 'countries/' + country + '/regions')
            .then(
                function (response) {
                    $scope.states = response.data;
                },
                function (error) {
                    console.log(error);
                }
            );
    };
    $scope.submitMember = function () {
        $scope.$broadcast('show-errors-check-validity');
        if ($scope.memberForm.$valid) {
            $.ajax({
                type: "POST",
                url: "/addMember",
                data: $('#memberForm').serialize(),
                success: function (response) {
                    console.log(response);
                    var seconds = 5;
                    var interval = setInterval(function () {
                        seconds--;
                        $('.member-message').html('<div class="alert alert-success alert-dismissible fade show alert-member-message" role="alert">' +
                            '<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a><strong>' +
                            'Success!</strong> Closing the window in ' + seconds + ' seconds</div>');
                        if (seconds == 0) {
                            clearInterval(interval);
                            $("div.alert-member-message").remove();
                            $('#addMemberModal').modal('hide');
                            var table = $('#memberDataTable').dataTable();
                            table.api().ajax.reload();
                        }
                    }, 1000);
                },
                error: function (error) {
                    $("div.alert-member-message").remove();
                    if (error.responseJSON.status == 500) {
                        $('.member-message').append('<div class="alert alert-danger alert-dismissible fade show" role="alert">' +
                            '<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>' +
                            '<strong>Error!</strong> ' + error.responseJSON.error + '</div>')
                    } else {
                        angular.forEach(error.responseJSON.message, function (value, key) {
                            $('.member-message').append('<div class="alert alert-danger alert-dismissible fade show" role="alert">' +
                                '<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>' +
                                '<strong>Error!</strong> ' + value + '</div>')
                        });
                    }
                }
            });
        }
    };
    $scope.removeMember = function () {
        $.ajax({
            type: "POST",
            url: "/removeMember",
            data: $('#removeMemberForm').serialize(),
            // headers: {'X-CSRF-TOKEN': csrfToken},
            success: function (response) {
                console.log(response);
                var seconds = 5;
                var interval = setInterval(function () {
                    seconds--;
                    $('.member-message').html('<div class="alert alert-success alert-dismissible fade show alert-member-message" role="alert">' +
                        '<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a><strong>' +
                        'Success!</strong> Closing the window in ' + seconds + ' seconds</div>');
                    if (seconds == 0) {
                        $("div.alert-member-message").remove();
                        clearInterval(interval);
                        $('#delete-member').modal('toggle');
                        var table = $('#memberDataTable').dataTable();
                        table.api().ajax.reload();

                    }
                }, 1000);
            },
            error: function (error) {
                angular.forEach(error.responseJSON.message, function (value, key) {
                    $('.member-message div').append('<div class="alert alert-danger alert-dismissible fade show" role="alert">' +
                        '<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>' +
                        '<strong>Error!</strong> ' + value + '</div>')
                });
            }
        });
    }
});
module.controller('AccountController', function ($scope, UserService, $window, $http) {
    var BASE_API = '/api/v1/';
    $scope.NG_MODE = "Add New";
    $scope.lastUpdatedDate = new Date();
    $scope.removeTrnsactionId;
    $scope.transaction = {
        transMode: '',
        transSelect: '',
        transType: '',
        transDate: '',
        amount: '',
        description: '',
        transactionId: ''
    };
    $scope.transModes = [];
    $scope.transSelects = [];
    $scope.transTypes = [];
    $scope.reset = function () {
        $scope.$broadcast('show-errors-reset');
        $scope.transaction = "";
    };
    $scope.fetchDropDownDataTransaction = function () {
        $http.get(BASE_API + 'trans-modes')
            .then(
                function (response) {
                    $scope.transModes = response.data;
                },
                function (error) {
                    console.log(error);
                }
            );
        $http.get(BASE_API + 'trans-types')
            .then(
                function (response) {
                    $scope.transTypes = response.data;
                },
                function (error) {
                    console.log(error);
                }
            );
    };
    $scope.onChangeTransactionType = function (selection) {
        if (selection == undefined) {
            $scope.transSelects = [];
            return;
        }
        $http.get(BASE_API + 'trans-selects/selection/' + selection)
            .then(
                function (response) {
                    $scope.transSelects = response.data;
                },
                function (error) {
                    console.log(error);
                }
            );
    };
    $scope.submitTransaction = function () {
        $('.transaction-message').html("");
        console.log($('#transactionForm').serialize());
        $scope.$broadcast('show-errors-check-validity');
        if ($scope.transactionForm.$valid) {
            $.ajax({
                type: "POST",
                url: "/addTransaction",
                data: $('#transactionForm').serialize(),
                success: function (response) {
                    console.log(response);
                    var seconds = 5;
                    var interval = setInterval(function () {
                        seconds--;
                        $('.transaction-message').html('<div class="alert alert-success alert-dismissible fade show alert-transaction-message" role="alert">' +
                            '<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a><strong>' +
                            'Success!</strong> Closing the window in ' + seconds + ' seconds</div>');
                        if (seconds == 0) {
                            $("div.alert-transaction-message").remove();
                            clearInterval(interval);
                            $('#addTransactionModal').modal('hide');
                            var table = $('#accountDataTable').dataTable();
                            table.api().ajax.reload();
                        }
                    }, 1000);
                },
                error: function (error) {
                    angular.forEach(error.responseJSON.message, function (value, key) {
                        $('.transaction-message').append('<div class="alert alert-danger alert-dismissible fade show" role="alert">' +
                            '<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>' +
                            '<strong>Error!</strong> ' + value + '</div>')
                    });
                }
            });
        }
    };
    $scope.fetchTransactionById = function (transactionId) {
        $scope.fetchDropDownDataTransaction();
        $http.get('/fetchTransactions/' + transactionId)
            .then(
                function (response) {
                    var data = response.data;
                    $scope.transaction.transMode = {code: Number(data.transModeVal)};
                    $scope.transaction.transType = {code: Number(data.transTypeVal)};
                    $scope.onChangeTransactionType(data.transTypeVal);
                    $scope.transaction.transSelect = {code: Number(data.transSelectVal)};
                    $scope.transaction.transDate = data.transDate;
                    $scope.transaction.amount = data.transAmount;
                    $scope.transaction.description = data.transDesc;
                    $scope.transaction.transactionId = transactionId;
                },
                function (error) {
                    console.log(error);
                }
            );
    };
    $scope.removeTransaction = function () {
        $.ajax({
            type: "POST",
            url: "/removeTransaction",
            data: $('#transactionForm').serialize(),
            // headers: {'X-CSRF-TOKEN': csrfToken},
            success: function (response) {
                console.log(response);
                var seconds = 5;
                var interval = setInterval(function () {
                    seconds--;
                    $('.transaction-message').html('<div class="alert alert-success alert-dismissible fade show alert-transaction-message" role="alert">' +
                        '<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a><strong>' +
                        'Success!</strong> Closing the window in ' + seconds + ' seconds</div>');
                    if (seconds == 0) {
                        $("div.alert-transaction-message").remove();
                        clearInterval(interval);
                        $('#delete-transaction').modal('toggle');
                        var table = $('#accountDataTable').dataTable();
                        table.api().ajax.reload();

                    }
                }, 1000);
            },
            error: function (error) {
                angular.forEach(error.responseJSON.message, function (value, key) {
                    $('.transaction-message div').append('<div class="alert alert-danger alert-dismissible fade show" role="alert">' +
                        '<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>' +
                        '<strong>Error!</strong> ' + value + '</div>')
                });
            }
        });
    }
});
module.controller('RegisterController', function ($scope, UserService, $window, $http) {
    $scope.user = {
        userName: '',
        password: '',
        confirmPassword: '',
        email: ''
    };
    $scope.registerUser = function () {
        $scope.$broadcast('show-errors-check-validity');
        if ($scope.registerForm.$valid) {
            $.ajax({
                type: "POST",
                url: "/doRegister",
                data: $('#registerForm').serialize(),
                success: function (response) {
                    console.log(response);
                    var seconds = 5;
                    setInterval(function () {
                        seconds--;
                        $('#register-message').html('<div class="alert alert-success alert-dismissible fade show" role="alert">' +
                            '<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a><strong>' +
                            'Success!</strong> redirecting to login page in ' + seconds + ' seconds</div>');
                        if (seconds == 0) {
                            window.location.replace("/login");
                        }
                    }, 1000);
                },
                error: function (error) {
                    $('#register-message').html("");
                    angular.forEach(error.responseJSON.message, function (value, key) {
                        console.log(value);
                        $('#register-message').append('<div class="alert alert-danger alert-dismissible fade show" role="alert">' +
                            '<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>' +
                            '<strong>Error!</strong> ' + value + '</div>')
                    });
                }
            });
        }
    }

});
module.controller('LoginController', function ($scope, UserService, $window, $http) {
    $scope.user = {
        userName: '',
        password: ''
    };
});
module.controller('PasswordController', function ($scope, UserService, $window, $http) {
    $scope.user = {
        email: ''
    };
    $scope.forgotPassword = function () {
        $scope.$broadcast('show-errors-check-validity');
        if ($scope.forgotPasswordForm.$valid) {
            $.ajax({
                type: "POST",
                url: "/forgotPassword",
                data: $('#forgotPasswordForm').serialize(),
                success: function (response) {
                    console.log(response);
                    var seconds = 5;
                    setInterval(function () {
                        seconds--;
                        $('#forgot-message').html('<div class="alert alert-success alert-dismissible fade show" role="alert">' +
                            '<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a><strong>' +
                            'Success!</strong> redirecting to login page in ' + seconds + ' seconds</div>');
                        if (seconds == 0) {
                            window.location.replace("/login");
                        }
                    }, 1000);
                },
                error: function (error) {
                    $('#forgot-message').html("");
                    if (error.responseJSON.message instanceof Array) {
                        angular.forEach(error.responseJSON.message, function (value, key) {
                            $('#forgot-message').append('<div class="alert alert-danger alert-dismissible fade show" role="alert">' +
                                '<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>' +
                                '<strong>Error!</strong> ' + value + '</div>')
                        });
                    } else {
                        $('#forgot-message').append('<div class="alert alert-danger alert-dismissible fade show" role="alert">' +
                            '<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>' +
                            '<strong>Error!</strong> ' + error.responseJSON.message + '</div>')
                    }

                }
            });
        }
    }
});


module.factory('UserService', function ($http, $q) {
    var URI_DO_LOGIN = '/doLogin';
    var URI_DO_REGISTER = '/doRegister';
    var factory = {
        doLogin: doLogin,
        doRegister: doRegister
    };
    return factory;

    function doLogin(user) {
        var deferred = $q.defer();
        $http.post(URI_DO_LOGIN, user)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function (errResponse) {
                    console.error('Error while creating User');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function doRegister(user) {
        var deferred = $q.defer();
        $http.post(URI_DO_REGISTER, user)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function (errResponse) {
                    console.error('Error while creating User');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }
});
