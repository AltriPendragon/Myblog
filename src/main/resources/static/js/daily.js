var app = angular.module('articleApp',['tm.pagination']);
app.controller('articleControl',function ($scope,$http) {
    $scope.pageShow = false;
    $scope.articles = [];
    $scope.images = [
        {
            "url":"https://ae01.alicdn.com/kf/H628580d969a144a5aa9a461472e472deR.jpg"
        }
    ];

    $scope.getPageArticles = function (rows, pageNum) {
        var data = {
            rows:rows,
            pageNum:pageNum
        }
        var config={
            headers:{'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'},
            transformRequest: function (data) {
                return $.param(data);
            }
        };
        $http.post('/getPageArticles',data,config).then(
            function success(response) {
                $scope.articles = response.data.result;
                $scope.paginationConf.totalItems = response.data.totalSize;
                $scope.pageShow = true;
            },
            function error(response) {
            }

        )

        $http.get('/getBgAllImages').then(
            function success(response) {
                $scope.images = response.data;

            },
            function error(response) {
            }
        )


    }

    $scope.getImage = function (index) {
        index = ($scope.paginationConf.currentPage-1)*10+index;
        if(index>=$scope.images.length){
            return $scope.images[index%$scope.images.length].url;
        }
        return $scope.images[index].url;
    }

    $scope.paginationConf = {
        currentPage: 1,
        totalItems:10,
        itemsPerPage: 10,
        pagesLength: 4,
        //perPageOptions: [10, 20, 30, 40, 50],
        onChange: function(){
            $scope.getPageArticles(10,$scope.paginationConf.currentPage)
        }
    };

})