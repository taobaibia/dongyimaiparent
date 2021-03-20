app.service('searchservice',function ($http) {
    this.search = function (searchMap) {
        // alert("key : "+searchMap.keywords);
        return $http.post('../itemsearch/search.do',searchMap);
    }
})