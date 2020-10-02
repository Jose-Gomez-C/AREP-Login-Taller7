apiclient = (function () {
    return{
        sendLogin: function(username, password){
            var promise = $.post({
                url: "/login",
                type: 'POST',
                data: JSON.stringify({name:username, password:password}),
                contentType: "application/json"
            });
            promise.then(
                function () {
                    console.info("OK");
                },
                function () {
                    console.info("ERROR");
            });
        }
    }
})();