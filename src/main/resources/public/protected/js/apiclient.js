apiclient = (function () {
    return{
        conexion: function(callback){
            var promise = $.get({
                url: "/conexion",
            });
            promise.then(
                function (data) {
                    console.info("OK");
                    callback(data);
                },
                function (data) {
                    console.info("ERROR");
            });
        }
    }
})();