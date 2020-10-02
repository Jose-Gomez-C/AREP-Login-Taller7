app =(function (){
    function login(){
        apiclient.sendLogin($("#userName").val(),$("#psswd").val());
    }
    return{
        login:login
    }
})();